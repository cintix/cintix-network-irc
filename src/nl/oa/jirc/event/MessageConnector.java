/**
 * 
 */
package nl.oa.jirc.event;



/**
 * @author Marc de Kwant
 *
 */
public interface MessageConnector {

    /**
     * Objecten that want to recieve the messages can join as a listener
     *
     * @param messageListener listener
     */
    public void addMessageListener( MessageListener messageListener );
    
    /**
     * Objecten that want to stop recieving the messages can leave as a listener
     *
     * @param messageListener listener
     */
    public void removeMessageListener( MessageListener messageListener );
    
    /**
     * Fire the event into the world of listeners
     * @param event the message event
     */
    public void fireMessageEvent(MessageEvent event);
    
}
