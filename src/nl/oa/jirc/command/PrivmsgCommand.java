/**
 * 
 */
package nl.oa.jirc.command;

import nl.oa.jirc.core.ReservedWords;
import nl.oa.jirc.core.message.ICoreConstruct;

/**
 * This is one of the main commands of the IRC protocol. With this you can send
 * messages to each other on IRC in private as wwell in public channels.
 * 
 * @author Marc de Kwant
 * 
 */
public class PrivmsgCommand implements ICoreConstruct {

	private String commandOptions = null;

	
	public PrivmsgCommand() { }
	
	public PrivmsgCommand(String message) {
	    parse(message);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.message.ICoreConstruct#parse(java.lang.String)
	 */
	public final void parse(final String line) {
		this.commandOptions = line;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		return ReservedWords.PRIVMSG + ReservedWords.SPACE
				+ this.commandOptions;
	}

}
