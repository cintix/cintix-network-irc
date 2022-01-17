/**
 * 
 */
package nl.oa.jirc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.oa.jirc.command.PrivmsgCommand;
import nl.oa.jirc.core.ConnectionDetails;
import nl.oa.jirc.core.IRCConnection;
import nl.oa.jirc.core.message.IRCMessage;
import nl.oa.jirc.event.AbstractMessageEventHandler;
import nl.oa.jirc.event.MessageEvent;
import nl.oa.jirc.security.Identity;

/**
 * @author Marc de Kwant
 *
 */
public class SimpleClient extends AbstractMessageEventHandler {
    
    
    private IRCConnection connection;
    private JFrame frame;
    private JLabel label, recievedText;
    private JTextField textfield;
    private Container content;
    private JPanel panel;
    private JButton sendButton;
    
    @Override
    public void messageRecieved(MessageEvent event) {
        if (frame!=null) {
            recievedText.setText(event.getMessage().toString());
        }
    }
    
    public void disconnect() {
        connection.logoff("stop messaging..");
        connection.disconnect();
    }

    public SimpleClient() {
        // Setup your identity
        Identity identity = new Identity("cintixcix", "irc.twitch.tv", "",
                "cintix");
        ConnectionDetails connectionDetails = new ConnectionDetails("irc.twitch.tv", 6667);
        connection = new IRCConnection(identity,connectionDetails);
        this.addMessageListener(connection);
        connection.addMessageListener(this);
        connection.connect();
        connection.logon("oauth:mo12nj6d5p5qxhd2bg973lkb95r1hs");
        (new Thread( connection)).start();
    }
    
    public void buildInterface(final SimpleClient client) {
        frame = new JFrame("Simple IRC Client");
        frame.setSize(400, 100);
        content = frame.getContentPane();
        panel = new JPanel(new BorderLayout());
        sendButton = new JButton("send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                IRCMessage message = new IRCMessage(new PrivmsgCommand(textfield.getText()));
                client.fireMessageEvent(new MessageEvent(client,message));
            }
        });
        label = new JLabel("Enter: ");
        recievedText = new JLabel("recieved text.....");
        textfield = new JTextField();
        label.setLabelFor(textfield);
        panel.add(label, BorderLayout.WEST);
        panel.add(textfield, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        panel.add(recievedText, BorderLayout.SOUTH);
        content.add(panel, BorderLayout.NORTH);        
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we) {
              disconnect();
              System.exit(0);
            }
          });
    }
    
    public static void main(String[] args){
        final SimpleClient client = new SimpleClient();
        client.buildInterface(client);
    }

}


