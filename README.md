# humm Android SDK

Use our API to build your own innovative -- and 100% legal -- music solutions or build music into your services.

#### Dependencies

The sdk uses Gson library.

#### Installation

Download aar from https://cdn.rawgit.com/myhumm/humm-android-sdk/master/binaries/myhumm-android-sdk.aar

and include it into your lib directory on your project.

Don't forget include the dependency into the gradle file
 
    compile files('libs/myhumm-android-sdk.aar')

#### Usage

*First step.*You have to get an instance for API

        final HummAPI humm = HummAPI.getInstance();
        
*Second step.* you have to **signup** or **login** into humm:
        
        humm.login(USERNAME, PASSWORD, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
				//all ok
            }

            @Override
            public void onError(Exception e) {
				//error
            }
        });

	 public void signup( username,  password,  email,  firstname,  lastname, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
				//all ok
            }

            @Override
            public void onError(Exception e) {
				//error
            }
        });

Humm API uses password grant type authentication. You don't care about refresh the token. The library does it internally.

*Next steps.* Once you have logged in, you can access any method of humm. For example:

        humm.radio(limit, genres, moods, discovery, own, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<Song> songList = (List) result;
                assertEquals(limit, songList.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });

#### Final considerations

The **success** and **error** blocks appears in all methods and they are executed in the main thread, so you can access to your UI elements inside them.