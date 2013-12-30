package org.xbmc.android.zeroconf;

import org.xbmc.android.jsonrpc.config.HostConfig;

/**
 * A container that stores IP address, host and port.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class XBMCHost {

	private String address;
	private String host;
	private String name;
	private int port;

	private String username;
	private String password;

	public XBMCHost(String address, String host, int port, String name) {
		this.address = address;
		this.host = host;
		this.port = port;
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void setCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public HostConfig toHostConfig() {
		if (username != null && password != null) {
			return new HostConfig(address, port, username, password);
		} else {
			return new HostConfig(address, port);
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return host + " - " + address + ":" + port;
	}

}
