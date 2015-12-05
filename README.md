# humm Android SDK

Use our API to build your own innovative -- and 100% legal -- music solutions or build music into your services.

#### Dependencies

The sdk uses Gson library.

#### Installation

1) First of all, add the Gson library dependency adding this line into your gradle file:

	 compile 'com.google.code.gson:gson:2.3.1'
 
2) Download the skd library aar from https://cdn.rawgit.com/myhumm/humm-android-sdk/master/binaries/myhumm-android-sdk-001.aar

and include it into your /libs directory on your project.

3) Don't forget include the dependency into the gradle file. For this, we need to add the /libs repository outside the dependencies block

    repositories {
       flatDir {
        dirs 'libs'
       }
    }

and compile the library (inside the dependencies block)

    compile(name:'myhumm-android-sdk-001', ext:'aar')


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
        
#### API Methods

#### Artists

Accessed by **humm.getArtist()**

    public void get(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener);
    public void addFollowers(final String idArtist, final OnActionFinishedListener listener);
    public void removeFollowers(final String idArtist, final OnActionFinishedListener listener);
    public void getPlaylists(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener);
    public void search(final String keyword, final int limit, final int offset, final OnActionFinishedListener listener);
    public void getTopSongs(final String idArtist, final int limit, final int offset, final String songType, final OnActionFinishedListener listener);
    public HummMultipleResult<Artist> getFeatured(int limit, int offset, String genre);
    public HummMultipleResult<Artist> getPopular(int limit, int offset);
    public HummMultipleResult<Artist> getRecent(int limit, int offset);

#### Playlists

Accessed by **humm.getPlaylists()**

    public void get(final String idPlaylist, final OnActionFinishedListener listener);
    public void create(final String title, final String description, final boolean isPrivate, final OnActionFinishedListener listener);
    public void getFeatured(final int limit, final int offset, final OnActionFinishedListener listener);
    public void update(final String idPlaylist, final String title, final String description, final boolean isPrivate, final OnActionFinishedListener listener);
    public void order(final String idPlaylist, final List<HashMap<String, String>> order, final OnActionFinishedListener listener);
    public void getSongs(final String idPlaylist, final int limit, final int offset, final OnActionFinishedListener listener);
    public void addSong(final String idPlaylist, final String idSong, final int position, final OnActionFinishedListener listener);
    public void deleteSong(final String idPlaylist, final String idSong, final OnActionFinishedListener listener);
    public void addSubscriber(final String idPlaylist, final OnActionFinishedListener listener);
    public void removeSubscriber(final String idPlaylist, final OnActionFinishedListener listener);
    public void getPopular(final int limit, final int offset, final int section, final String idUser, final OnActionFinishedListener listener);
    public void getRecent(final int limit, final int offset, final OnActionFinishedListener listener);
    public void search(final String keyword, final int limit, final int offset, final int album, final OnActionFinishedListener listener);

#### Songs

Accessed by **humm.getSongs()**

    public void getFeatured(final int limit, final int offset, final String genre, final OnActionFinishedListener listener);
    public void getPopular(final int limit, final int offset, final String genre, final OnActionFinishedListener listener);
    public void getRecent(final int limit, final int offset, final String genre, final OnActionFinishedListener listener);
    public void search(final String keyword, final int limit, final int offset, final String songtype, final OnActionFinishedListener listener);
    public void appearsIn(final String idSong, final int limit, final OnActionFinishedListener listener);
    public void getSimilar(final String idSong, final int offset, final OnActionFinishedListener listener);

#### User

Accessed by **humm.getUsers()**

    public void doSignup(final String username, final String password, final String email, final String firstname, final String lastname, final OnActionFinishedListener listener);
    public void doLogin(final String username, final String password, final OnActionFinishedListener listener);
    public void me(final OnActionFinishedListener listener);
    public void discoverRealeses(final int limit, final int offset, final OnActionFinishedListener listener);
    public void discoverArtists(final int limit, final int offset, final OnActionFinishedListener listener);
    public void discoverPlaylists(final int limit, final int offset, final OnActionFinishedListener listener);
    public void addFavourites(final String idSong, final OnActionFinishedListener listener);
    public void addFollows(final String idUser, final OnActionFinishedListener listener);
    public void removeFollows(final String idUser, final OnActionFinishedListener listener);
    public void get(final String idUser, final OnActionFinishedListener listener);
    public void getFollows(final String idUser, final int limit, final int offset, final OnActionFinishedListener listener);
    public void getPlaylists(final String idUser, final int limit, final int offset, final OnActionFinishedListener listener);


#### Documentation

For test endpoints and complete documentation you can access http://developers.myhumm.com/web/api/endpoint

#### Final considerations

The **success** and **error** blocks appears in all methods and they are executed in the main thread, so you can access to your UI elements inside them.