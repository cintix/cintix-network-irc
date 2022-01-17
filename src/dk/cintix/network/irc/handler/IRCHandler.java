package dk.cintix.network.irc.handler;

import dk.cintix.network.irc.models.Message;

/**
 *
 * @author Michael Martinsen
 */
public interface IRCHandler {
    public void  onConnected();
    public void  onDisconnected();
    public void onMessage(Message message);
}
