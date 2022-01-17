/**
 * 
 */
package nl.oa.jirc.core.message;

/**
 * This is the core interface which defines the most basic operation of the
 * commands. definition of the toString is not needed since this is a default
 * method from the object class.
 * 
 * @author Marc de Kwant
 * 
 */
public interface ICoreConstruct {

	/**
	 * If a given command has parsable content, then this method must be used o
	 * parse the content into meaningfull content for the Jirc classes to
	 * handle.
	 * 
	 * @param line
	 *            The content to be parsed by the implementing subclass
	 */
	void parse(final String line);

	/**
	 * Parse the construct to a irc message string
	 * @return the irc message
	 */
	String toString();
	
}
