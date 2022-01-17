package dk.cintix.network.irc.test;

import dk.cintix.network.irc.IRCClient;
import dk.cintix.network.irc.handler.IRCHandler;
import dk.cintix.network.irc.models.Message;

/**
 *
 * @author Michael Martinsen
 */
public class SampleClient implements IRCHandler {

    IRCClient ircc = new IRCClient("irc.twitch.tv", 6667, "cintixcix", "cintix");

    public SampleClient() {
        ircc.addHandler(this);

        System.out.println("Connection to IRC");
        if (ircc.connect()) {
            System.out.println("We are now online ");
        } else {
            System.out.println("Failed to connect to IRC");
        }
        
        ircc.disconnect();
        
    }

    @Override
    public void onConnected() {
        System.out.println(" ---> Connnected to host");
    }

    @Override
    public void onDisconnected() {
        System.out.println(" <--- Disconnected");
    }

    @Override
    public void onMessage(Message message) {
        System.out.println(message);
    }
    
    public static void main(String[] args) {
        new SampleClient();
    }
}
