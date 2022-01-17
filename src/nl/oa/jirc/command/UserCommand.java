/**
 * 
 */
package nl.oa.jirc.command;

import nl.oa.jirc.core.ReservedWords;
import nl.oa.jirc.core.message.ICoreConstruct;
import nl.oa.jirc.security.Identity;

/**
 * @author Marc de Kwant
 * 
 */
public class UserCommand implements ICoreConstruct {

	private Identity identity;

	public UserCommand(final Identity identity) {
		this.identity = identity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		return ReservedWords.USER + ReservedWords.SPACE + identity.getNick()
				+ ReservedWords.SPACE + identity.getHostname()
				+ ReservedWords.SPACE + identity.getServername()
				+ ReservedWords.SPACE + ReservedWords.DD
				+ identity.getRealname() + ReservedWords.CR + ReservedWords.LF;
	}

	public final void parse(final String line) {
		// TODO Auto-generated method stub

	}

}
