/**
 * 
 */
package nl.oa.jirc.security;

import nl.oa.jirc.command.NickCommand;
import nl.oa.jirc.command.UserCommand;

/**
 * This class contains the indentity information about a current user in
 * relation to a given connection.
 * 
 * @author Marc de Kwant
 * 
 */
public class Identity {

	private String nick;

	// private String username;
	// private String password;
	private String hostname;

	private String servername;

	private String realname;

	public Identity(final String nick, final String hostname,
			final String servername, String realname) {
		this.nick = nick;
		// this.username = username;
		// this.password = password;
		this.hostname = hostname;
		this.servername = servername;
		this.realname = realname;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the nick.
	 */
	public final String getNick() {
		return nick;
	}

	/**
	 * Getter
	 * 
	 * @return the hostname
	 */
	public final String getHostname() {
		return hostname;
	}

	/**
	 * Setter
	 * 
	 * @param hostname
	 *            the hostname to set
	 */
	public final void setHostname(final String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Getter
	 * 
	 * @return the realname
	 */
	public final String getRealname() {
		return realname;
	}

	/**
	 * Setter
	 * 
	 * @param realname
	 *            the realname to set
	 */
	public final void setRealname(final String realname) {
		this.realname = realname;
	}

	/**
	 * Getter
	 * 
	 * @return the servername
	 */
	public final String getServername() {
		return servername;
	}

	/**
	 * Setter
	 * 
	 * @param servername
	 *            the servername to set
	 */
	public final void setServername(final String servername) {
		this.servername = servername;
	}

	/**
	 * This method returns a NICK command fully formatted.
	 * 
	 * @return {@link NickCommand} of the current user
	 */
	public final String getNICKcommand() {
		return new NickCommand(this).toString();
	}

	/**
	 * This meethod returns an USER command fully formated.
	 * 
	 * @return {@link UserCommand} of the current user
	 */
	public final String getUSERcommand() {
		return new UserCommand(this).toString();
	}

}
