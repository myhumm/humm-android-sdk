package humm.android.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.Artist;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.Playlist;
import humm.android.api.Model.PlaylistOwnerInt;
import humm.android.api.Model.PlaylistOwnerList;
import humm.android.api.Model.Song;
import humm.android.api.Model.User;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by josealonsogarcia on 24/11/15.
 */
public class UserAPITest extends HummTest {

    public void testMe() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getUser().me(new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                User user = (User) result;

                assertEquals("deleteme100", user.getUsername());
                assertEquals("delete", user.getFirstName());
                assertEquals("me", user.getLastName());
                assertEquals("deleteme100@deleteme.com", user.getEmail());

                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });

        signal.await(30, TimeUnit.SECONDS);

    }

    public void testDiscoverReleases() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();

        doLogin();
        final int limit = 5;
        int offset = 0;

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getUser().discoverRealeses(limit, offset, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<PlaylistOwnerList> playlistList = (List) result;
                assertEquals(limit, playlistList.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testDiscoverArtists() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();

        doLogin();
        final int limit = 5;
        int offset = 0;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getUser().discoverArtists(limit, offset, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<Artist> artistList = (List) result;
                assertEquals(limit, artistList.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }


    public void testDiscoverPlaylists() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        final int limit = 1;
        int offset = 0;

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getUser().discoverPlaylists(limit, offset, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<PlaylistOwnerList> playlist = (List) result;
                assertEquals(limit, playlist.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testAddFavourites() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idSong = "557ecbf86a64fc1b8bed533f";

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getUser().addFavourites(idSong, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                Song song = (Song) result;
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testAddFollower() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idUser = "5649c572ae8c502824a46a99"; //Blur

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getUser().addFollows(idUser, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                User user = (User) result;
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }


    /*
    "status_response": "ok",
  "data_response": [
    {
      "uid": "5511686ff9c816a0d639e644"
    }
  ]
     */
    public void testRemoveFollower() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idUser = "5649c572ae8c502824a46a99"; //user 1


        final CountDownLatch signal = new CountDownLatch(1);

        humm.getUser().removeFollows(idUser, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List hashmapList = (List) result;
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

//    public void testAddPlays() throws Throwable {
//
//        final HummAPI humm = HummAPI.getInstance();
//        doLogin();
//        String idSong = "557ecbf86a64fc1b8bed533f"; //song2 (blur)
//
//        HummSingleResult<Song> result = humm.getUser().addPlays(idSong);
//
//        assertEquals("ok", result.getStatus_response());
//
//    }

//    public void testAddSubscriptions() throws Throwable {
//
//        final HummAPI humm = HummAPI.getInstance();
//        doLogin();
//        String idPlaylist = "558c339c6a64fc1b8bf81ef3";
//
//        HummSingleResult<Playlist> result = humm.getUser().addSubscriptions(idPlaylist);
//
//        assertEquals("ok", result.getStatus_response());
//
//    }

//    public void testRemoveSubscriptions() throws Throwable {
//
//        final HummAPI humm = HummAPI.getInstance();
//        doLogin();
//        String idPlaylist = "558c339c6a64fc1b8bf81ef3";
//
//        HummSingleResult<Playlist> result = humm.getUser().removeSubscriptions(idPlaylist);
//
//        assertEquals("ok", result.getStatus_response());
//
//    }

    public void testGet() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idUser = "5649c572ae8c502824a46a99"; //user1

//        HummSingleResult<User> result = humm.getUser().get(idUser);
//
//        assertEquals("ok", result.getStatus_response());
//        assertEquals("user1", result.getData_response().getUsername());
//        assertEquals("user1", result.getData_response().getFirstName());
//        assertEquals("test", result.getData_response().getLastName());

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getUser().get(idUser, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                User user = (User) result;
                assertEquals("user1", user.getUsername());
                assertEquals("user1", user.getFirstName());
                assertEquals("test", user.getLastName());

                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

//    public void testGetFavourites() throws Throwable {
//
//        final HummAPI humm = HummAPI.getInstance();
//        doLogin();
//        String idUser = "5649c572ae8c502824a46a99"; //user1
//        int limit = 0;
//        int offset = 0;
//
//        HummMultipleResult<Song> result = humm.getUser().getFavourites(idUser, limit, offset);
//
//        assertEquals("ok", result.getStatus_response());
//
//    }

    public void testGetFollows() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idUser = "5649c572ae8c502824a46a99"; //user1
        final int limit = 2;
        int offset = 0;

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getUser().getFollows(idUser, limit, offset, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<Artist> userList = (List) result;
                assertEquals(limit, userList.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testGetPlaylists() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idUser = "5661783496a4f4e521fe3f65"; //deletemet
        final int limit = 2;
        int offset = 0;

//        HummMultipleResult<PlaylistOwnerInt> result = humm.getUser().getPlaylists(idUser, limit, offset);
//
//        if (result != null) {
//            assertEquals("ok", result.getStatus_response());
//        } else {
//            assertNull(result); //no content
//        }

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getUser().getPlaylists(idUser, limit, offset, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<PlaylistOwnerInt> playlistList = (List) result;

                if (result != null ) { //no content
                    assertEquals(limit, playlistList.size());
                }
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

//    public void testPlays() throws Throwable {
//
//        final HummAPI humm = HummAPI.getInstance();
//        doLogin();
//        String idUser = "5649c572ae8c502824a46a99"; //user1
//        int limit = 0;
//        int offset = 0;
//
//        HummMultipleResult<Song> result = humm.getUser().getPlays(idUser, limit, offset);
//
//        if (result != null) {
//            assertEquals("ok", result.getStatus_response());
//        } else {
//            assertNull(result); //no content
//        }
//    }

}
