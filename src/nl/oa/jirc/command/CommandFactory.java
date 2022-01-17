/**
 * 
 */
package nl.oa.jirc.command;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import nl.oa.jirc.Log;
import nl.oa.jirc.LogFactory;

import nl.oa.jirc.core.message.ICoreConstruct;



/**
 * This class it's primary function is to retrieve a certain command through
 * reflection. If the command is not found then an Exception is thrown
 * 
 * @author Marc de Kwant
 * 
 */
public class CommandFactory {

	private final static ResourceBundle resource;

	private static final Log log = LogFactory.getLog(CommandFactory.class);

	static {
		/*
		 * This resourcebundle contains all references from the IRC code to the
		 * java command class that is required.
		 */
		resource = ResourceBundle.getBundle("command");
	}

	/**
	 * This is a static method which facilitates the retrieval of a IRC core
	 * construct. In Essence it is a IRC command.
	 * 
	 * @param command
	 *            The command that is needed
	 * @return an IRC command in the form of a {@link ICoreConstruct}
	 * @throws CommandNotFoundException
	 *             In cae of an incorrect command this exception is thrown
	 */
	public static ICoreConstruct getCommand(final String command)
			throws CommandNotFoundException {
		log.debug("trying to retrieve the class for command => " + command);
		Object obj = null;
		try {
			String clazzName = resource.getString("command.name."
					+ command.toLowerCase());
			if (clazzName == null) {
				throw new CommandNotFoundException();
			}
			Class<?> cls = Class.forName(clazzName);
			obj = cls.newInstance();
			log.debug("Retrieval succesfull");
		} catch (MissingResourceException e) {
			obj = new InvalidCommand();
		} catch (InstantiationException e) {
			obj = new InvalidCommand();
		} catch (IllegalAccessException e) {
			throw new CommandNotFoundException();
		} catch (ClassNotFoundException e) {
			throw new CommandNotFoundException();
		}
		return (ICoreConstruct) obj;
	}

}
