# humm Android SDK

Use our API to build your own innovative -- and 100% legal -- music solutions or build music into your services.

#### Dependencies

The sdk uses Gson library.

#### Installation

1) First of all, add the Gson library dependency adding this line into your gradle file:

	 compile 'com.google.code.gson:gson:2.3.1'
 
2) Download the skd library aar from https://cdn.rawgit.com/myhumm/humm-android-sdk/master/binaries/myhumm-android-sdk.aar

and include it into your /libs directory on your project.

3) Don't forget include the dependency into the gradle file. For this, we need to add the /libs repository outside the dependencies block

    repositories {
       flatDir {
        dirs 'libs'
       }
    }

and compile the library (inside the dependencies block)

    compile(name:'myhumm-android-sdk', ext:'aar')


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