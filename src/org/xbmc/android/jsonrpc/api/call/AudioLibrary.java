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

package org.xbmc.android.jsonrpc.api.call;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.model.AudioModel;

/**
 * Implements JSON-RPC API methods for the "AudioLibrary" namespace.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public final class AudioLibrary {
	
	private final static String PREFIX = "AudioLibrary.";
	
	/**
	 * Retrieve all albums from specified artist or genre
	 *  <p/>
	 *  Curl example:
	 *    <code>curl -i -X POST -d '{"jsonrpc": "2.0", "method": "AudioLibrary.GetAlbums", "params": { "properties": [ "title" ]}, "id": 1}' http://localhost:8080/jsonrpc</code>
	 */
	public static class GetAlbums extends AbstractCall<AudioModel.AlbumDetails> {
		private static final String NAME = "GetAlbums";
		/**
		 * Retrieve all albums from specified artist or genre.
		 * @param artistid If not null, filter by artists of this ID. 
		 * @param genreid If not null, filter by genre of this ID.
		 * @param fields Fields to return, see {@link AudioModel.AlbumFields}.
		 * @throws JSONException
		 */
		public GetAlbums(Integer artistid, Integer genreid, String... fields) throws JSONException {
			super();
			addParameter("artistid", artistid);
			addParameter("genreid", genreid);
			addParameter("properties", fields);
		}
		@Override
		protected ArrayList<AudioModel.AlbumDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray albums = obj.getJSONArray("albums");
			final ArrayList<AudioModel.AlbumDetails> ret = new ArrayList<AudioModel.AlbumDetails>(albums.length());
			for (int i = 0; i < albums.length(); i++) {
				final JSONObject album = albums.getJSONObject(i);
				ret.add(new AudioModel.AlbumDetails(album));
			}
			return ret;
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
	}
	
	/**
	 * Retrieve all artists.
	 *  <p/>
	 *  Curl example:
	 *    <code>curl -i -X POST -d '{"jsonrpc": "2.0", "method": "AudioLibrary.GetArtists", "params": { "properties": [ "musicbrainzartistid" ]}, "id": 1}' http://localhost:8080/jsonrpc</code>
	 */
	public static class GetArtists extends AbstractCall<AudioModel.ArtistDetails> {
		private static final String NAME = "GetArtists";
		/**
		 * Retrieve all artists.
		 * @param albumartistsonly Whether or not to include artists only appearing in compilations. If the parameter is not passed or is passed as null the GUI setting will be used
		 * @param genreid If not null, filter by genre of this ID.
		 * @param fields Fields to return, see {@link AudioModel.ArtistFields}.
		 * @throws JSONException
		 */
		public GetArtists(Boolean albumartistsonly, Integer genreid, String... fields) throws JSONException {
			super();
			addParameter("albumartistsonly", albumartistsonly);
			addParameter("genreid", genreid);
			addParameter("properties", fields);
		}
		@Override
		protected ArrayList<AudioModel.ArtistDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray artists = obj.getJSONArray("artists");
			final ArrayList<AudioModel.ArtistDetails> ret = new ArrayList<AudioModel.ArtistDetails>(artists.length());
			for (int i = 0; i < artists.length(); i++) {
				final JSONObject artist = artists.getJSONObject(i);
				ret.add(new AudioModel.ArtistDetails(artist));
			}
			return ret;
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
	}
	
	/**
	 * Retrieve details about a specific song
	 *  <p/>
	 *  Curl example:
	 *    <code>curl -i -X POST -d '{"jsonrpc": "2.0", "method": "AudioLibrary.GetSongDetails", "params": { "songid": 1 }, "id": 1}' http://localhost:8080/jsonrpc</code>
	 */
	public static class GetSongDetails extends AbstractCall<AudioModel.SongDetails> {
		private static final String NAME = "GetSongDetails";
		/**
		 * Retrieve details about a specific song.
		 * @param songid ID of the song
		 * @param fields Fields to return, see {@link AudioModel.SongFields}.
		 * @throws JSONException
		 */
		public GetSongDetails(int songid, String... fields) throws JSONException {
			super();
			addParameter("songid", songid);
			addParameter("properties", fields);
		}
		@Override
		protected AudioModel.SongDetails parseOne(JSONObject obj) throws JSONException {
			return new AudioModel.SongDetails(obj.getJSONObject("songdetails"));
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
	
	
	/**
	 * Exports all items from the audio library
	 * <p/>
	 * API Name: <code>AudioLibrary.Export</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Export extends AbstractCall<String> { 
		private static final String NAME = "Export";
		/**
		 * Exports all items from the audio library
		 * @param options 
		 * @throws JSONException
		 */
		public Export(Path options) throws JSONException {
			super();
			addParameter("options", options);
		}
		/**
		 * Exports all items from the audio library
		 * @param options 
		 * @throws JSONException
		 */
		public Export(ImagesOverwrite options) throws JSONException {
			super();
			addParameter("options", options);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Path extends AbstractModel {
			public final String path;
			public Path(String path) {
				this.path = path;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put("path", path);
				return obj;
			}
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class ImagesOverwrite extends AbstractModel {
			public final boolean images;
			public final boolean overwrite;
			public ImagesOverwrite(boolean images, boolean overwrite) {
				this.images = images;
				this.overwrite = overwrite;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				// TODO Auto-generated method stub
				return null;
			}
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
	
}
