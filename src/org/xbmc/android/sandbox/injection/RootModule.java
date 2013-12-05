package org.xbmc.android.sandbox.injection;

import dagger.Module;

/**
 * Add all the other modules to this one.
 */
@Module(includes = { AndroidModule.class, AppModule.class })
public class RootModule {

}