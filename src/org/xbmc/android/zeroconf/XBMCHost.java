package org.xbmc.android.zeroconf;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A container that stores IP address, host and port in a parcelable way.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class XBMCHost implements Parcelable {
	
	private String mAddress;
	private String mHost;
	private int mPort;
	
	public XBMCHost(String address, String host, int port) {
		mAddress = address;
		mHost = host;
		mPort = port;
	}

	public String getAddress() {
		return mAddress;
	}

	public String getHost() {
		return mHost;
	}

	public int getPort() {
		return mPort;
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

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(mAddress);
		parcel.writeString(mHost);
		parcel.writeInt(mPort);
	}
	
	/**
	 * Generates instances of this Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<XBMCHost> CREATOR = new Parcelable.Creator<XBMCHost>() {

		@Override
		public XBMCHost createFromParcel(Parcel parcel) {
			final String address = parcel.readString();
			final String host = parcel.readString();
			final int port = parcel.readInt();
			return new XBMCHost(address, host, port);
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
	
	@Override
	public String toString() {
		return mHost + " - " + mAddress + ":" + mPort;
	}

}
