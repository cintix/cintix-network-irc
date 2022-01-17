/**
 * 
 */
package nl.oa.jirc.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.oa.jirc.core.ReservedWords;
import nl.oa.jirc.core.message.ICoreConstruct;

/**
 * This command is responsible for generating a PING on the RC channel. The
 * required response to this is an PONG by IRC protocol.
 * 
 * @author Marc de Kwant
 * 
 */
public class PingCommand implements ICoreConstruct {

    /*
     * Since we get the raw message string we need to provide
     * a parsing mechanism to extract the options of the ping
     * so we can resend it back to the server.
     */
	private final Pattern ircPingPattern = Pattern.compile("(.*?) :(.*?)",
			Pattern.CASE_INSENSITIVE);

	private String commandOptions = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		return ReservedWords.PING + ReservedWords.SPACE + this.commandOptions;
	}

	public final String pong() {
		return ReservedWords.PONG + ReservedWords.SPACE + ReservedWords.DD
				+ this.commandOptions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.message.ICoreConstruct#parse(java.lang.String)
	 */
	public final void parse(final String line) {
		Matcher matcher = ircPingPattern.matcher(line);
		if (matcher.matches()) {
			this.commandOptions = matcher.group(2);
		}
	}

}
