/**
 *
 */
package nl.oa.jirc.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import nl.oa.jirc.Log;
import nl.oa.jirc.LogFactory;

import nl.oa.jirc.command.PingCommand;
import nl.oa.jirc.core.message.IRCMessage;
import nl.oa.jirc.event.AbstractMessageEventHandler;
import nl.oa.jirc.event.MessageEvent;
import nl.oa.jirc.security.Identity;

/**
 * This class represents a connection object and contains all methods to
 * exercise the basic operations on a connection.
 *
 * @author Marc de Kwant
 *
 */
public class IRCConnection extends AbstractMessageEventHandler implements IConnection, Runnable {

    private static Log log = LogFactory.getLog(IRCConnection.class);

    private BufferedReader IRCMessageRecieverChannel;

    private BufferedWriter IRCMessageSenderChannel;

    private Socket IRCServerSocket;

    private Identity identity = null;

    private ConnectionDetails connectionDetails = null;

    private IRCMessage ircMessage = null;

    private volatile boolean isRunning = true;

    /**
     * Constructor. There is no default constructor This is the only means to
     * instantiate the con- nection class.
     *
     * @param identity The identity which will be used to connect to a IRC
     * server
     */
    public IRCConnection(final Identity identity, ConnectionDetails connectionDetails) {
        this.identity = identity;
        this.connectionDetails = connectionDetails;
    }

    /* (non-Javadoc)
	 * @see nl.oa.jirc.core.IConnection#connect()
     */
    public final void connect()
            throws ConnectionException {
        if ((this.connectionDetails.getHost() != null) && (!this.connectionDetails.getHost().equalsIgnoreCase(""))
                && (this.connectionDetails.getPort() > 0)) {
            if (log.isDebugEnabled()) {
                log.debug("Connecting to " + this.connectionDetails.getHost() + ":" + this.connectionDetails.getPort());
            }
            // connect a socket to the IRC server
            try {
                IRCServerSocket = new Socket(this.connectionDetails.getHost(), this.connectionDetails.getPort());
            } catch (UnknownHostException e) {
                throw new ConnectionException(e);
            } catch (IOException e) {
                throw new ConnectionException(e);
            }
            // get input and output streams from the IRC server
            InputStream IRCis = null;
            OutputStream IRCos = null;
            try {
                IRCis = IRCServerSocket.getInputStream();
                IRCos = IRCServerSocket.getOutputStream();
            } catch (IOException e) {
                throw new ConnectionException(e);
            }
            // Create the channels for sending and recieving messages
            IRCMessageRecieverChannel = new BufferedReader(
                    new InputStreamReader(IRCis));
            IRCMessageSenderChannel = new BufferedWriter(
                    new OutputStreamWriter(IRCos));

            try {
                if (IRCMessageRecieverChannel.ready()) {
                    log.info("Ready to recieve messages...");
                }
            } catch (IOException e) {
                throw new ConnectionException(e);
            }

        } else {
            log.error("No servername and/or port defined.");
            throw new ConnectionException("No servername and/or port defined.");
        }
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.IConnection#disconnect()
     */
    public final void disconnect() throws ConnectionException {
        if (log.isDebugEnabled()) {
            log.debug("Disconnecting...");
        }
        if (IRCMessageRecieverChannel != null) {
            try {
                IRCMessageRecieverChannel.close();
            } catch (IOException e) {
                throw new ConnectionException(e);
            }
        } else {
            log.info("Trying to disconnect from a non-exsistent connection.");
        }
        if (IRCMessageSenderChannel != null) {
            try {
                IRCMessageSenderChannel.close();
            } catch (IOException e) {
                throw new ConnectionException(e);
            }
        } else {
            log.info("Trying to disconnect from a non-exsistent connection");
        }
        // We must stop the thread also from running to prevent some nasty exceptions :)
        this.isRunning = false;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.IConnection#logoff(java.lang.String)
     */
    public final void logoff(final String message) throws ConnectionException {
        send(ReservedWords.QUIT + ReservedWords.SPACE
                + ((message != null) ? message : "Ive had it.."), true);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.IConnection#logon()
     */
    public final void logon() throws ConnectionException {
        send(identity.getUSERcommand(), false);
        send(identity.getNICKcommand(), true);
    }

    public final void logon(String pwd) throws ConnectionException {
        send("PASS " + pwd, true);
        send(identity.getUSERcommand(), false);
        send(identity.getNICKcommand(), true);
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {

        this.isRunning = true;
        this.ircMessage = null;

        while (this.isRunning) {
            this.rawMessageProcessor();
            if (this.ircMessage != null && this.ircMessage.toString().indexOf("halt") >= 0) {
                this.isRunning = false;
                if (log.isDebugEnabled()) {
                    log.debug("Exit thread...");
                }
            } else {
//                if (log.isTraceEnabled()) {
//                    log.trace("Last recieved message.");
//                    log.trace((this.ircMessage != null) ? this.ircMessage.toString() : "");
//                }
            }
        }

    }

    /* (non-Javadoc)
     * @see nl.oa.jirc.event.MessageListener#messageRecieved(nl.oa.jirc.event.MessageEvent)
     */
    public void messageRecieved(MessageEvent event) {
        if (!(event.getSource() instanceof IConnection)) {
            if (log.isDebugEnabled()) {
                log.debug("Message recieved by IRCConnection. Sending to server");
            }
            send(event.getMessage().toString(), true);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Ignoring my own messages.");
            }
        }

    }

    /**
     * The PING message is used to test the presence of an active client at the
     * other end of the connection. A PING message is sent at regular intervals
     * if no other activity detected coming from a connection. If a connection
     * fails to respond to a PING command within a set amount of time, that
     * connection is closed.
     *
     * Any client which receives a PING message must respond to <server1>
     * (server which sent the PING message out) as quickly as possible with an
     * appropriate PONG message to indicate it is still there and alive. Servers
     * should not respond to PING commands but rely on PINGs from the other end
     * of the connection to indicate the connection is alive. If the <server2>
     * parameter is specified, the PING message gets forwarded there. *
     *
     * @throws ConnectionException If a communication disruption is detected an
     * exception of this kind is thrown.
     */
    private final void pingReply(PingCommand pingCommand)
            throws ConnectionException {
        log.debug("Ping reply send to the IRC server...");
        System.out.println(pingCommand.pong());
        send(pingCommand.pong(), true);
    }

    /**
     * This method gets a line from the incomming channel and makes it availible
     * for processing.
     *
     * @return The raw message recieved from the server or peer to peer client
     * connection.
     * @throws ConnectionException If a communication disruption is detected an
     * exception of this kind is thrown.
     */
    private final String recieve() throws ConnectionException {
        try {
            final String message = IRCMessageRecieverChannel.readLine();
            if (log.isDebugEnabled()) {
                log.debug("The message => " + message);
            }
            return message;
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * This method puts a line into the sender channel.
     *
     * @throws ConnectionException If a communication disruption is detected an
     * exception of this kind is thrown.
     */
    private final void send(final String message, final boolean flush)
            throws ConnectionException {
        if (log.isDebugEnabled()) {
            log.debug("sending message. " + message);
        }
        try {
            IRCMessageSenderChannel.write(message);
            IRCMessageSenderChannel.newLine();
            if (flush) {
                IRCMessageSenderChannel.flush();
            }
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Verwerken van messages die ontvangen worden
     */
    private void rawMessageProcessor() {
        try {
            /* Check for any input to be parsed */
            if (IRCMessageRecieverChannel.ready()) {
                ircMessage = new IRCMessage();
                ircMessage.parse(recieve());
                /* Check for ping messages that only need a pong reply */
                if (((IRCMessage) ircMessage).getCommand() instanceof PingCommand) {
                    // write a pong message in reply to PING from the server
                    if (log.isDebugEnabled()) {
                        log.debug("Ping detected...");
                    }
                    pingReply((PingCommand) (((IRCMessage) ircMessage)
                            .getCommand()));
                    /*
					 * In case of a ping reply no ircMessage needs to be
					 * returned outside the connection class.
                     */
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("firering event..");
                    }
                    fireMessageEvent(new MessageEvent(this, ircMessage));
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    if (log.isErrorEnabled()) {
                        log.error("We are interrupted...", e);
                    }
                }

            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("One of the sockets was not reachable...", e);
            }
        }
    }

    /**
     * @return the isRunning
     */
    public final boolean isRunning() {
        return isRunning;
    }

}
