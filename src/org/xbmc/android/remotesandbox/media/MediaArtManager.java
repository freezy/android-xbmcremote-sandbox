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

package org.xbmc.android.remotesandbox.media;

import android.graphics.BitmapFactory;

/**
 * Manages XBMC's media art.
 * 
 * <p/>
 * There are different formats for media art. The only really defined 
 * format is fanart, apart from movie posters, which are assumed to be in 
 * portrait format. There are the following format types:
 * <ul><li><b>[P]OSTER</b>: A portrait poster, mainly used for movies, but can
 *          be also available for TV shows and seasons. The aspect ratio is 
 *          1:1.48.</li>
 *     <li><b>[C]OVER</b>: Typically a CD cover, but can also be an artist 
 *          thumb. It's a square image with aspect ratio 1:1.</li>
 *     <li><b>[F]ANART</b>: Background image at 1:0.5625.</li>
 *     <li><b>[B]ANNER</b>: Wide banner image at 758x140px, which makes an 
 *          aspect ratio of 1:0.1846965699208443.</li>
 *     <li><b>[S]CREENSHOT</b>: A screenshot of a TV episode, normally 16:9 but
 *          can also be 4:3.</li>
 *     <li><b>[U]NDEFINED</b> Can be an image of kind of size.</li>
 *     <li><b>[L]OGO</b>: Logo for TV shows, with transparent background. 
 *          Undefined AR.</li>
 *     <li><b>[C]LEAR[A]RT</b>:Art with transparent background for TV shows,
 *          transparent background and undefined AR.</li></ul>
 *          
 * <i>We don't really care about the last two.</i>
 * <p/>
 * Here is a list of which media supports which art format:
 * <ul><li>Movie [P,F]</li>
 *     <li>TV serie [P,F,B,L,CA]</li>
 *     <li>Serie season [P,B]</li>
 *     <li>Serie episode [S]</li>        
 *     <li>Music album [C]</li>
 *     <li>Music artist [F,U]</li></ul>
 * 
 * The goal is to support fanart where possible and banners and posters for TV 
 * series. Which media art to cache in which dimension(s) is still up to 
 * decide.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MediaArtManager {
	
	public MediaArtManager() {
	}
}
