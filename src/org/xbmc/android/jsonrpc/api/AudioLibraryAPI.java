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
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.api.model.AudioModel.AlbumFields;

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
	public JSONObject getAlbums(Long artistId, Long genreId, String... fields) throws JSONException {
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
	 * @param fields Additional fields to return, see constants at {@link AudioModel.ArtistFields}.
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getArtists(Boolean albumartistsOnly, String... fields) throws JSONException {
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
	 * Retrieve details about a specific song.
	 * 
	 * @param songid ID of the song
	 * @param fields Fields to return, see constants at {@link AudioModel.SongFields}.
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getSongDetails(int songid, String... fields) throws JSONException {
		final JSONObject request = createRequest("GetArtists");
		getParameters(request).put("songid", songid);
		if (fields != null && fields.length > 0) {
			getParameters(request).put("properties", toJSONArray(fields));
		}
		return request;
	}
	
	@Override
	protected String getPrefix() {
		return PREFIX;
	}

}
