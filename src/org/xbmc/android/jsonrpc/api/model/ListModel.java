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

import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.remotesandbox.notsurewheretoput.OneLabelledNavigation;

/**
 * Defines all types in the <code>List.*</code> namespace.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public final class ListModel {
	
	public static class SourceItem extends ItemModel.BaseDetails implements OneLabelledNavigation {
		public final static String TYPE = "List.Items.Sources";
		public String file;
		public SourceItem()  {
			mType = TYPE;
		}
		public SourceItem(JSONObject obj) throws JSONException {
			this();
			setData(obj);
		}
		@Override
		public void setData(JSONObject obj) throws JSONException {
			super.setData(obj);
			file = obj.getString("file");
		}
		@Override
		public String getLabel() {
			return label;
		}
		@Override
		public int getType() {
			return FileItem.FileType.SOURCE;
		}
		@Override
		public String getPath() {
			return file;
		}
	}
	
	public static class FileItem extends ItemModel.BaseDetails {
		public final static String TYPE = "List.Items.Sources";
		public String file;
		public int filetype;
		public FileItem()  {
			mType = TYPE;
		}
		public FileItem(JSONObject obj) throws JSONException {
			this();
			setData(obj);
		}
		@Override
		public void setData(JSONObject obj) throws JSONException {
			super.setData(obj);
			file = obj.getString("file");
			filetype = parseFileType(obj.getString("file"));
		}
		public interface FileType {
			public final static int FILE = 1;
			public final static int DIRECTORY = 2;
			public final static int UNKNOWN = -1;
			public final static int SOURCE = -2;
		}
		private static int parseFileType(String filetype) {
			if (filetype.equalsIgnoreCase("file")) {
				return FileType.FILE;
			}
			if (filetype.equalsIgnoreCase("directory")) {
				return FileType.DIRECTORY;
			}
			return FileType.UNKNOWN;
		}
	}
	
	
	/*========================================================================* 
	 *  FIELDS 
	 *========================================================================*/

}
