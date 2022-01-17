/**
 * 
 */
package nl.oa.jirc.core;

/**
 * Simple typed runtimeexception designed to be a wrapper to all exceptions
 * concerning any communication failure.
 * 
 * @author Marc de Kwant
 * 
 */
public class ConnectionException extends RuntimeException {

	private static final long serialVersionUID = -4849467688830195492L;

	public ConnectionException() {
		super();
	}

	public ConnectionException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public ConnectionException(final String arg0) {
		super(arg0);
	}

	public ConnectionException(final Throwable arg0) {
		super(arg0);
	}

}
