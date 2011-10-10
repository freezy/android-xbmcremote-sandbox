package org.xbmc.android.jsonrpc.client;

import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class ListLoader<T> extends AsyncTaskLoader<List<T>> {
	
	private List<T> mItems;
	private final Worker<T> mWorker;

	public ListLoader(Context context, Worker<T> worker) {
		super(context);
		mWorker = worker;
	}

	/**
	 * This is where the bulk of our work is done. This function is called in a
	 * background thread and should generate a new set of data to be published
	 * by the loader.
	 */
	@Override
	public List<T> loadInBackground() {
		return mWorker.doWork();
	}

	/**
	 * Called when there is new data to deliver to the client. The super class
	 * will take care of delivering it; the implementation here just adds a
	 * little more logic.
	 */
	@Override
	public void deliverResult(List<T> items) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (items != null) {
				onReleaseResources(items);
			}
		}
		List<T> oldItems = items;
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
	public void onCanceled(List<T> items) {
		super.onCanceled(items);

		// At this point we can release the resources associated with 'items' if needed.
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
	protected void onReleaseResources(List<T> items) {
		// For a simple List<> there is nothing to do. For something
		// like a Cursor, we would close it here.
	}
	
	
	public interface Worker<T> {
		public List<T> doWork();
	}
}