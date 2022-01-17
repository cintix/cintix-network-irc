/**
 * 
 */
package nl.oa.jirc.core;

/**
 * @author Marc de Kwant
 * 
 */
public interface IConnection {

	/**
	 * This method connects to a specific server on a specific port.
	 * 
	 * @throws ConnectionException
	 *             If a communication disruption is detected an exception of
	 *             this kind is thrown.
	 */
	public void connect()
			throws ConnectionException;

	/**
	 * This method disconnects the core from the server
	 * 
	 * @throws ConnectionException
	 *             If a communication disruption is detected an exception of
	 *             this kind is thrown.
	 */
	public void disconnect() throws ConnectionException;

	/**
	 * This method enables the actual logon to the specified IRC server.
	 * 
	 * @throws ConnectionException
	 *             If a communication disruption is detected an exception of
	 *             this kind is thrown.
	 */
	public void logon() throws ConnectionException;

	/**
	 * This method enables the actual logoff of the specified IRC server.
	 * 
	 * @param Message
	 *            the message to send as to the reason of the logoff
	 * @throws ConnectionException
	 *             If a communication disruption is detected an exception of
	 *             this kind is thrown.
	 */
	public void logoff(String message) throws ConnectionException;
	
}
