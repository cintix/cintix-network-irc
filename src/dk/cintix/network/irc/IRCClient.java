package dk.cintix.network.irc;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import dk.cintix.network.irc.handler.IRCHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Michael Martinsen
 */
public class IRCClient {

    private final List<IRCHandler> handlers = new ArrayList<>();

    private final String host;
    private final String nick;
    private final String login;
    private final String pass = "oauth:mo12nj6d5p5qxhd2bg973lkb95r1hs";
    private int port = 6667;
    private Socket connection;
    private BufferedReader reader;
    private PrintWriter writer;

    public IRCClient(String host, String nick, String login) {
        this.host = host;
        this.nick = nick;
        this.login = login;
    }

    public IRCClient(String host, int port, String nick, String login) {
        this.host = host;
        this.nick = nick;
        this.login = login;
        this.port = port;
    }

    public boolean connect() {
        try {
            connection = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));

            for (IRCHandler handler : handlers) {
                handler.onConnected();
            }
            writeToHost("PASS " + pass);            
            
            writeToHost("NICK " + nick);
            readFromHost();
            
            writeToHost("USER " + login + " 8 * :Cintix Bot");

            writeToHost("JOIN #cintixcix");
            readFromHost();
            writeToHost("PRIVMSG #cintixcix :Welcome now you are online....");
            writeToHost("LIST");
            readFromHost();
            readFromHost();
            
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void writeToHost(String msg) {
        writer.println(msg);
        writer.flush();
        System.out.println(msg);
    }

    private String readFromHost() {
        try {
            String message = "";
            while (connection.getInputStream().available() == 0) {
                TimeUnit.NANOSECONDS.sleep(100);
            }
            System.out.println("connection.getInputStream().available() - " + connection.getInputStream().available());
            byte[] buf = new byte[2048 * 2];
            int read = connection.getInputStream().read(buf);
            message = new String(buf, 0, read);
            System.out.println(message);

            if (message.startsWith("PING")) {
                writeToHost("PONG " + message.substring(5));
            }

            return message;
        } catch (Exception e) {
        }
        return null;
    }

    public void addHandler(IRCHandler stream) {
        handlers.add(stream);
    }

    public void disconnect() {
        try {
            if (connection != null && connection.isConnected()) {
                connection.close();
                for (IRCHandler handler : handlers) {
                    handler.onDisconnected();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
