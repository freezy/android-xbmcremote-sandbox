
Sandbox for Next-Generation XBMC-Remote
=======================================

Don't worry, I won't rewrite *everything*. However, there is still some very old code in the current XBMC Remote for Android that is hard to get out. So why not start from a blank slate.

The three major changes of this playground are:

1. Use `JSON-RPC` instead of old, buggy and soon-to-be-gone `HTTPAPI`.
2. Keep a local database for offline use as well as faster browsing.
3. Use a fragment-based design for easy tablet adaption.

## Installation
Apart from checking out the code, you'll need to include [Google's compatibility package](http://android-developers.blogspot.com/2011/03/fragments-for-all.html) `android-support-v4.jar`, which is included in the SDK. Place it into the `libs` folder of the project and reference it in Eclipse.

Oh, and you probably want to update the IP for your XBMC, that's hard coded at `AudioSyncService`.


## Status Quo

Currently, there is a home screen and a screen for the music library, which contains three tabs the user can fling through. All three tabs still list the same (albums). Clicking on the refresh button will trigger the service getting artists and albums from XBMC. These are the only entities implemented so far.


## Next Steps

There are a few things I'd like to have working until we start integrating the code with the real thing (feel free to request more):

* Fix autoupdating lists after a refresh (currently, `cursor.requery()` results in an ANR).
* Implement the file browse tab which will *not* query the database but XBMC directly - of course not *everything* is fetched via the local database.
* Make action bar buttons dynamic depending on selected page (e.g. there should be no refresh button when the file browse page is selected).
* Create a layout that looks good on Honeycomb
* Figure out a faster way to manage updates. Right now, everything is re-inserted, with a `ON CONFLICT REPLACE` constraint on the ID. Importing 1.200 artists and 500 albums takes about 10 seconds. Seems pretty slow to me.

The goal of this project is really to create a nice architecture that can be easily extended later. The idea is not to start adding all media types XBMC has to offer right now, but keep it simple and extend architecture-wise until we have a good base.

## Help needed!

Now that the code base is still very small, everybody is welcome to fiddle with it and extend it. I'm looking forward to pull-requests and hints, comments and suggestions. Please use GitHub ticket system for bugs or features that are in relation with this sandbox, not the Google Code page of the original project.

## Code Credits

A big thank you goes to Google for open-sourcing their very clean [IOSched](http://code.google.com/p/iosched/) app. There is some borrowed code and design patterns in here.

### Oh, and by the way,

I'll be off to my first holidays this year, until 7 October 2011. So bear with my response time on this one. :)

