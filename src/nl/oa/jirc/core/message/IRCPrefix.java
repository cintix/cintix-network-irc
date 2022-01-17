/**
 * 
 */
package nl.oa.jirc.core.message;

import nl.oa.jirc.core.ReservedWords;

/**
 * This class represents the prefix from a single irc message string.
 * 
 * @author kwantm
 * 
 */
public class IRCPrefix implements ICoreConstruct {

	private String prefix = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.message.ICoreConstruct#parse(java.lang.String)
	 */
	public final void parse(final String line) {
		this.prefix = line;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		if (prefix == null) {
			return "";
		} else {
			return ReservedWords.DD + this.prefix;
		}
	}

}
