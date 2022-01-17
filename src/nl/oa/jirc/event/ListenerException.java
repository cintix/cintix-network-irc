/**
 * 
 */
package nl.oa.jirc.event;

/**
 * @author Marc de Kwant
 *
 */
public class ListenerException extends RuntimeException {

    private static final long serialVersionUID = 6751621360221873591L;

    public ListenerException() {
        super();
    }

    public ListenerException(String arg0) {
        super(arg0);
    }

    public ListenerException(Throwable arg0) {
        super(arg0);
    }

    public ListenerException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
