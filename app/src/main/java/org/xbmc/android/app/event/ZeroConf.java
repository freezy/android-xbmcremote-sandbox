/*
 *      Copyright (C) 2005-2015 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.android.app.event;

import org.xbmc.android.zeroconf.XBMCHost;

public class ZeroConf {

	public static final int STATUS_RESOLVED = 0x1;
	public static final int STATUS_FINISHED = 0x2;
	public static final int STATUS_ERROR = 0xff;

	private final int status;
	private final XBMCHost host;

	public ZeroConf(int status) {
		this(status, null);
	}

	public ZeroConf(int status, XBMCHost host) {
		this.status = status;
		this.host = host;
	}

	public boolean isFinished() {
		return status == STATUS_FINISHED;
	}

	public boolean isResolved() {
		return status == STATUS_RESOLVED;
	}

	public boolean hasError() {
		return status == STATUS_ERROR;
	}

	public int getStatus() {
		return status;
	}

	public XBMCHost getHost() {
		return host;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		switch (status) {
			case STATUS_RESOLVED: sb.append("Resolved"); break;
			case STATUS_FINISHED: sb.append("Finished"); break;
			case STATUS_ERROR: sb.append("Error"); break;
		}
		if (host != null) {
			sb.append(": ");
			sb.append(host.toString());
		} else {
			sb.append(".");
		}
		return sb.toString();
	}
}
