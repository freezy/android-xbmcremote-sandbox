package org.xbmc.android.jsonrpc.client;

import java.util.ArrayList;
import java.util.List;

import org.xbmc.android.jsonrpc.api.FilesAPI;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class ListLoader extends AsyncTaskLoader<List<FilesAPI.Source>> {
	
	List<FilesAPI.Source> mItems;

	public ListLoader(Context context) {
		super(context);
	}

	/**
	 * This is where the bulk of our work is done. This function is called in a
	 * background thread and should generate a new set of data to be published
	 * by the loader.
	 */
	@Override
	public List<FilesAPI.Source> loadInBackground() {
		
		// Create corresponding array of entries and load their labels.
		List<FilesAPI.Source> entries = new ArrayList<FilesAPI.Source>(3);
		entries.add(new FilesAPI.Source("MP3s Winona", "V:\\mp3\\archive\\"));
		entries.add(new FilesAPI.Source("Last.fm", "lastfm://"));
		entries.add(new FilesAPI.Source("Music Add-ons", "addons://sources/audio/"));
		
		return entries;
	}

	/**
	 * Called when there is new data to deliver to the client. The super class
	 * will take care of delivering it; the implementation here just adds a
	 * little more logic.
	 */
	@Override
	public void deliverResult(List<FilesAPI.Source> items) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (items != null) {
				onReleaseResources(items);
			}
		}
		List<FilesAPI.Source> oldItems = items;
		mItems = items;

		if (isStarted()) {
			// If the Loader is currently started, we can immediately
			// deliver its results.
			super.deliverResult(items);
		}

		// At this point we can release the resources associated with
		// 'oldItems' if needed; now that the new result is delivered we
		// know that it is no longer in use.
		if (oldItems != null) {
			onReleaseResources(oldItems);
		}
	}

	/**
	 * Handles a request to start the Loader.
	 */
	@Override
	protected void onStartLoading() {
		if (mItems != null) {
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(mItems);
		}


		if (takeContentChanged() || mItems == null) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
		}
	}

	/**
	 * Handles a request to stop the Loader.
	 */
	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	/**
	 * Handles a request to cancel a load.
	 */
	@Override
	public void onCanceled(List<FilesAPI.Source> items) {
		super.onCanceled(items);

		// At this point we can release the resources associated with 'items'
		// if needed.
		onReleaseResources(items);
	}

	/**
	 * Handles a request to completely reset the Loader.
	 */
	@Override
	protected void onReset() {
		super.onReset();

		// Ensure the loader is stopped
		onStopLoading();

		// At this point we can release the resources associated with 'items'
		// if needed.
		if (mItems != null) {
			onReleaseResources(mItems);
			mItems = null;
		}
		
	}

	/**
	 * Helper function to take care of releasing resources associated with an
	 * actively loaded data set.
	 */
	protected void onReleaseResources(List<FilesAPI.Source> items) {
		// For a simple List<> there is nothing to do. For something
		// like a Cursor, we would close it here.
	}
}