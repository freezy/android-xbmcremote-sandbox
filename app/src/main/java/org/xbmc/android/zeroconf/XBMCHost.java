package org.xbmc.android.zeroconf;

import android.os.Parcel;
import android.os.Parcelable;
import org.xbmc.android.jsonrpc.config.HostConfig;

import java.util.ArrayList;

/**
 * A container that stores IP address, host and port.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class XBMCHost implements Parcelable {

	private int id;
	private String address;
	private String host;
	private String name;
	private int port;

	private String username;
	private String password;

	private boolean active = false;

	public XBMCHost(String address, String host, int port, String name) {
		this(-1, address, host, port, name);
	}
	public XBMCHost(int id, String address, String host, int port, String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name of host must not be null.");
		}
		this.id = id;
		this.address = address;
		this.host = host;
		this.port = port;
		this.name = name.trim();
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

	public String getUser() {
		return username;
	}

	public String getPass() {
		return password;
	}

	public boolean isActive() {
		return active;
	}

	/**
	 * Returns the URI of the host without trailing slash.
	 * @return URI, e.g "http://127.0.0.1:8080".
	 */
	public String getUri() {
		return "http://" + address + ":" + port;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return host + " - " + address + ":" + port;
	}

	//<editor-fold desc="Parcelization">

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(id);
		parcel.writeString(address);
		parcel.writeString(host);
		parcel.writeInt(port);
		parcel.writeString(name);
		parcel.writeString(username);
		parcel.writeString(password);
		parcel.writeInt(active ? 1 : 0);
	}

	/**
	 * Generates instances of this Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<XBMCHost> CREATOR = new Parcelable.Creator<XBMCHost>() {

		@Override
		public XBMCHost createFromParcel(Parcel parcel) {
			final int id = parcel.readInt();
			final String address = parcel.readString();
			final String host = parcel.readString();
			final int port = parcel.readInt();
			final String name = parcel.readString();
			final String username = parcel.readString();
			final String password = parcel.readString();
			final boolean active = parcel.readInt() == 1;

			final XBMCHost h = new XBMCHost(id, address, host, port, name);
			h.setCredentials(username, password);
			h.setActive(active);
			return h;
		}

		@Override
		public XBMCHost[] newArray(int n) {
			return new XBMCHost[n];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	public static ArrayList<XBMCHost> fromParcel(Parcel parcel) {
		final int s = parcel.readInt();
		final ArrayList<XBMCHost> hosts = new ArrayList<XBMCHost>(s);
		for (int i = 0; i < s; i++) {
			hosts.add(parcel.<XBMCHost>readParcelable(XBMCHost.class.getClassLoader()));
		}
		return hosts;
	}

	public static Parcel toParcel(ArrayList<XBMCHost> hosts, Parcel parcel, int flags) {
		parcel.writeInt(hosts.size());
		for (XBMCHost host : hosts) {
			parcel.writeParcelable(host, flags);
		}
		return parcel;
	}

	public void setId(int id) {
		this.id = id;
	}

	//</editor-fold>
}
