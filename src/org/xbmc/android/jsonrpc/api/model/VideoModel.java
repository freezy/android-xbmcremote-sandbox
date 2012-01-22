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

package org.xbmc.android.jsonrpc.api.model;


/**
 * Defines all types in the <code>Video.*</code> namespace.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public final class VideoModel {
	

	
	
	/*========================================================================* 
	 *  FIELDS 
	 *========================================================================*/
	
	/**
	 * Video.Fields.Movie
	 */
	public interface MovieFields extends ItemModel.BaseFields {
		final String TITLE = "title";
		final String GENRE = "genre";
		final String YEAR = "year";
		final String RATING = "rating";
		final String DIRECTOR = "director";
		final String TRAILER = "trailer";
		final String TAGLINE = "tagline";
		final String PLOT = "plot";
		final String PLOTOUTLINE = "plotoutline";
		final String ORIGINALTITLE = "originaltitle";
		final String LASTPLAYED = "lastplayed";
		final String PLAYCOUNT = "playcount";
		final String WRITER = "writer";
		final String STUDIO = "studio";
		final String MPAA = "mpaa";
		final String CAST = "cast";
		final String COUNTRY = "country";
		final String IMDBNUMBER = "imdbnumber";
		final String PREMIERED = "premiered";
		final String PRODUCTIONCODE = "productioncode";
		final String RUNTIME = "runtime";
		final String SET = "set";
		final String SHOWLINKE = "showlink";
		final String STREAMDETAILS = "streamdetails";
		final String TOP250 = "top250";
		final String VOTES = "votes";
		final String FANART = "fanart";
		final String THUMBNAIL = "thumbnail";
		final String FILE = "file";
		final String SORTTITLE = "sorttitle";
		final String RESUME = "resume";
		final String SETTID = "setid";
	}
}
