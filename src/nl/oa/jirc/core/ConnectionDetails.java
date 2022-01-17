/**
 * 
 */
package nl.oa.jirc.core;

/**
 * @author marc de kwant
 *
 */
public class ConnectionDetails {
	
	public ConnectionDetails(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	private String host;
	
	private int port;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

}
