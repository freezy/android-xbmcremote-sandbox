package org.xbmc.android.zeroconf;

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

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return host + " - " + address + ":" + port;
	}

}
