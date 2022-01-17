/**
 * 
 */
package nl.oa.jirc.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Marc de Kwant
 *
 */
public abstract class AbstractMessageEventHandler implements MessageConnector,
        MessageListener {
    
    private List<MessageListener> listeners = new ArrayList<MessageListener>();

    /* (non-Javadoc)
     * @see nl.oa.jirc.core.MessageConnector#addMessageListener(nl.oa.jirc.event.MessageListener)
     */
    public final synchronized void addMessageListener( MessageListener messageListener ) {
        listeners.add( messageListener );
    }
    
    /* (non-Javadoc)
     * @see nl.oa.jirc.core.MessageConnector#removeMessageListener(nl.oa.jirc.event.MessageListener)
     */
    public final synchronized void removeMessageListener( MessageListener messageListener ) {
        listeners.remove( messageListener );
    }

    /* (non-Javadoc)
     * @see nl.oa.jirc.event.MessageListener#messageRecieved(nl.oa.jirc.event.MessageEvent)
     */
    public abstract void messageRecieved(MessageEvent event);
    
    public final synchronized void fireMessageEvent(MessageEvent event) {
        Iterator<MessageListener> it = listeners.iterator();
        while( it.hasNext() ) {
            ( (MessageListener) it.next() ).messageRecieved( event );
        }
    }
    

}
