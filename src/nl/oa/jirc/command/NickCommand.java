/**
 * 
 */
package nl.oa.jirc.command;

import nl.oa.jirc.core.ReservedWords;
import nl.oa.jirc.core.message.ICoreConstruct;
import nl.oa.jirc.security.Identity;

/**
 * Specific command to facilitate the nick command on IRC. Command class
 * generates an NICK command with the information of the current
 * {@link Identity} information.
 * 
 * @author Marc de Kwant
 * 
 */
public class NickCommand implements ICoreConstruct {

	private Identity identity;

	/**
	 * Constructor which sets the identity when its called. It is not possible
	 * to instantiate a NICK command without the current identity.
	 * 
	 * @param identity
	 */
	public NickCommand(final Identity identity) {
		this.identity = identity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		return ReservedWords.NICK + ReservedWords.SPACE + identity.getNick()
				+ ReservedWords.CR + ReservedWords.LF;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.message.ICoreConstruct#parse(java.lang.String)
	 */
	public final void parse(final String line) {
		// TODO Auto-generated method stub

	}

}
