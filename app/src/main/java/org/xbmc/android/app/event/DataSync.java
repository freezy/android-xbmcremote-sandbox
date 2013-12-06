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

/**
 * Event that describes the global life cycle of a sync process.
 *
 * This basically describes if a sync process has started or ended. If you need to know status info
 * about individual sections, subscribe to {@link org.xbmc.android.app.event.DataItemSynced}.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class DataSync {

	public static int STARTED = 0x01;
	public static int FINISHED = 0x02;
	public static int FAILED = 0x03;

	private final int status;
	private final String errorMessage;
	private final String errorHint;

	public DataSync(int status) {
		this(status, null, null);
	}

	public DataSync(int status, String errorMessage, String errorHint) {
		this.status = status;
		this.errorMessage = errorMessage;
		this.errorHint = errorHint;
	}

	/**
	 * If failed, retrieve the error message and hint with {@link #getErrorMessage()}
	 * and {@link #getErrorHint()}.
	 */
	public boolean hasFailed() {
		return status == FAILED;
	}

	public boolean hasStarted() {
		return status == STARTED;
	}

	public boolean hasFinished() {
		return status == FINISHED;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorHint() {
		return errorHint;
	}
}
