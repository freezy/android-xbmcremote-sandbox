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

package org.xbmc.android.jsonrpc.client;

import java.util.ArrayList;

import org.xbmc.android.jsonrpc.api.call.Files;
import org.xbmc.android.jsonrpc.api.model.FilesModel;
import org.xbmc.android.jsonrpc.api.model.ListModel;

/**
 * Real time access to the Files API.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class FilesClient extends AbstractClient {

	private final static String TAG = FilesClient.class.getSimpleName();

	/**
	 * Returns all music sources.
	 *
	 * @param errorHandler Error handler
	 * @return List of all music sources
	 */
	public ArrayList<ListModel.SourcesItem> getMusicSources(ErrorHandler errorHandler) {
		final Files.GetSources apicall = new Files.GetSources(FilesModel.Media.MUSIC);
		execute(apicall, errorHandler);
		return apicall.getResults();
	}
	
	/**
	 * Returns all files of a specific folder. If nothing found, an empty
	 * list is returned.
	 *
	 * @param directory Folder to fetch items for
	 * @param errorHandler Error handler
	 * @return Folder contents or empty list if nothing found.
	 */
	public ArrayList<ListModel.FileItem> getDirectory(String directory, ErrorHandler errorHandler) {
		final Files.GetDirectory apicall = new Files.GetDirectory(directory, null);
		execute(apicall, errorHandler);
		return apicall.getResults();
	}

}
