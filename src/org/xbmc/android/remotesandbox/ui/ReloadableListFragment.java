package org.xbmc.android.remotesandbox.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import org.xbmc.android.remotesandbox.R;

/**
 *  Abstract Superclass for reloadable Lists.
 *  provides the options menu reload button.
 *
 *  TODO: Click handling if action does not handle it.
 */
abstract class ReloadableListFragment extends ListFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.refresh_menu_items, menu);

    }
}
