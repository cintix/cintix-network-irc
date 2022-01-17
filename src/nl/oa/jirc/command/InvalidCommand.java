/**
 * 
 */
package nl.oa.jirc.command;

import nl.oa.jirc.core.message.ICoreConstruct;

/**
 * @author marc de kwant
 * 
 */
public class InvalidCommand implements ICoreConstruct {

	private String rawMessage = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.message.ICoreConstruct#parse(java.lang.String)
	 */
	public void parse(String line) {
		this.rawMessage = line;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InvalidCommand => " + this.rawMessage;
	}

}
