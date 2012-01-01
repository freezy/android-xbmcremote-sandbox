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

package org.xbmc.android.jsonrpc.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Creates request objects for the audio library.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class AudioLibraryAPI extends AbstractAPI {
	
	private final static String PREFIX = "AudioLibrary.";
	
	/**
	 * Retrieve all albums.
	 * 
	 * @param artistId If not null, only fetch albums for this artist.
	 * @param genreId If not null, only fetch albums of this genre.
	 * @param fields Additional fields to return, see constants at {@link AlbumFields}.
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getAlbums(Long artistId, Long genreId, String[] fields) throws JSONException {
		final JSONObject request = createRequest("GetAlbums");
		if (artistId != null) {
			getParameters(request).put("artistid", artistId);
		}
		if (genreId != null) {
			getParameters(request).put("genreid", genreId);
		}
		if (fields != null && fields.length > 0) {
			getParameters(request).put("properties", toJSONArray(fields));
		}
		return request;
	}
	
	/**
	 * Retrieve all artists.
	 * 
	 * @param albumartistsOnly Whether or not to include artists only appearing in compilations. If the parameter is not passed or is passed as null the GUI setting will be used
	 * @param fields Additional fields to return, see constants at {@link ArtistFields}.
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getArtists(Boolean albumartistsOnly, String[] fields) throws JSONException {
		final JSONObject request = createRequest("GetArtists");
		if (albumartistsOnly != null) {
			getParameters(request).put("albumartistsonly", albumartistsOnly);
		}
		if (fields != null && fields.length > 0) {
			getParameters(request).put("properties", toJSONArray(fields));
		}
		return request;
	}
	
	/**
	 * Field definitions for artists.
	 * @author freezy <freezy@xbmc.org>
	 */
	public interface ArtistFields {
		final String INSTRUMENT = "instrument";
		final String STYLE = "style";
		final String MOOD = "mood";
		final String BORN = "born";
		final String FORMED = "formed";
		final String DESCRIPTION = "description";
		final String GENRE = "genre";
		final String DIED = "died";
		final String DISBANDED = "disbanded";
		final String YEARSACTIVE = "yearsactive";
		final String MUSICBRAINZARTISTID = "musicbrainzartistid";
		final String FANART = "fanart";
		final String THUMBNAIL = "thumbnail";
	}
	
	/**
	 * Field definitions for albums.
	 * @author freezy <freezy@xbmc.org>
	 */
	public interface AlbumFields {
		final String TITLE = "title";
		final String DESCRIPTION = "description";
		final String ARTIST = "artist";
		final String GENRE = "genre";
		final String THEME = "theme";
		final String MOOD = "mood";
		final String STYLE = "style";
		final String TYPE = "type";
		final String LABEL = "label";
		final String RATING = "rating";
		final String YEAR = "year";
		final String MUSICBRAINZALBUMID = "musicbrainzalbumid";
		final String MUSICBRAINZALBUMARTISTID = "musicbrainzalbumartistid";
		final String FANART = "fanart";
		final String THUMBNAIL = "thumbnail";
		final String ARTISTID = "artistid";
	}

	@Override
	protected String getPrefix() {
		return PREFIX;
	}

}
