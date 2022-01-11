package dk.cintix.network.irc;

import dk.cintix.network.irc.handler.IRCStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Martinsen
 */
public class IRCClient {
    private final List<IRCStream> handlers = new ArrayList<>();
    
    public void addHandler(IRCStream stream){
        handlers.add(stream);
    }
    
}
