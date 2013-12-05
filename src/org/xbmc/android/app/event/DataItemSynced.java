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
 * Event that describes successful synchronization of an item.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class DataItemSynced {

	public final static int VIDEO = 1000;
	public final static int MOVIES = 1001;

	public final static int MUSIC = 2000;
	public final static int ALBUMS = 2001;
	public final static int ARTISTS = 2002;

	private final int what;

	public DataItemSynced(int what) {
		this.what = what;
	}

	public boolean videoSynced() {
		return what == MOVIES;
	}

	public boolean audioSynced() {
		return what == ALBUMS;
	}

}