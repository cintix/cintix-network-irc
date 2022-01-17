/**
 * 
 */
package nl.oa.jirc.command;

/**
 * When a given command is not found by the {@link CommandFactory} then this
 * class is used to throw a command not found exception.
 * 
 * @author Marc de Kwant
 * 
 */
public class CommandNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -8671446446954743258L;

	public CommandNotFoundException() {
		super();
	}

	public CommandNotFoundException(final Throwable t) {
		super(t);
	}

	public CommandNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}
}
