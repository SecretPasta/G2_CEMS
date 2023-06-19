package Config;

import ocsf.server.ConnectionToClient;

public class ConnectedClient {
	private String ip;
	private String clientname;
	private ConnectionToClient client;
	private String role;

	/**
	 * Constructor for creating a ConnectedClient object with the specified IP and
	 * client name. for the connected clients table
	 *
	 * @param ip         The IP address of the connected client.
	 * @param clientname The client name of the connected client.
	 */
	public ConnectedClient(String ip, String clientname, ConnectionToClient client, String role) {
		this.ip = ip;
		this.clientname = clientname;
		this.client = client;
		this.role = role;
	}

	/**
	 * Retrieves the IP address of the connected client.
	 *
	 * @return The IP address of the connected client.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the IP address of the connected client.
	 *
	 * @param ip The IP address to set.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Retrieves the client name of the connected client.
	 *
	 * @return The client name of the connected client.
	 */
	public String getClientname() {
		return clientname;
	}

	/**
	 * Sets the client name of the connected client.
	 *
	 * @param username The client name to set.
	 */
	public void setClientname(String username) {
		this.clientname = username;
	}
	/**
	 * Returns the client's connection.
	 *
	 * @return the client's connection
	 */
	public ConnectionToClient getClient() {
		return client;
	}
	/**
	 * Sets the client's connection.
	 *
	 * @param client the client's connection to set
	 */
	public void setClient(ConnectionToClient client) {
		this.client = client;
	}

	/**
	 * Returns the role of the client.
	 *
	 * @return the role of the client
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role of the client.
	 *
	 * @param role the role of the client to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
}
