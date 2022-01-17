/**
 * 
 */
package nl.oa.jirc.event;

import java.util.EventObject;

import nl.oa.jirc.core.message.IRCMessage;

/**
 * @author Marc de kwant
 *
 */
public class MessageEvent extends EventObject {

    private static final long serialVersionUID = -5738519216495775054L;
    
    private IRCMessage message;

    public MessageEvent(Object source, IRCMessage message) {
        super(source);
        this.message = message;
    }
    
    public IRCMessage getMessage() {
        return this.message;
    }

}
