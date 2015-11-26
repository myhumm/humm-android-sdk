package humm.android.api;

import android.test.InstrumentationTestCase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.Artist;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.Playlist;
import humm.android.api.Model.Song;
import humm.android.api.Model.User;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by josealonsogarcia on 24/11/15.
 */
public class UserAPITest extends InstrumentationTestCase {

    public void testMe() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();

        final CountDownLatch signal = new CountDownLatch(1);

        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        HummSingleResult<User> me = humm.getUser().me();

        assertEquals("ok", me.getStatus_response());

        assertEquals("deleteme", me.getData_response().getUsername());
        assertEquals("Delete Me", me.getData_response().getFirstName());
        assertEquals("Me delete", me.getData_response().getLastName());
        assertEquals("deleteme@deleteme.com", me.getData_response().getEmail());

        List<String> likes = Arrays.asList("rock", "pop");
        assertThat(likes, is(me.getData_response().getPreferencesLike()));

        //TODO: assert similar, following, preferences dislike, played, suscriptions, favourites, genres

    }

    public void testDiscoverReleases() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();

        final CountDownLatch signal = new CountDownLatch(1);

        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);


        int limit = 5;
        int offset = 0;

        HummMultipleResult<Playlist> releases = humm.getUser().discoverRealeses(limit, offset);

        assertEquals("ok", releases.getStatus_response());
        assertEquals(5, releases.getData_response().size());


    }

    public void testDiscoverArtists() throws Throwable {


        final HummAPI humm = HummAPI.getInstance();

        final CountDownLatch signal = new CountDownLatch(1);

        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        int limit = 5;
        int offset = 0;

        HummMultipleResult<Artist> releases = humm.getUser().discoverArtists(limit, offset);

        assertEquals("ok", releases.getStatus_response());
        assertEquals(5, releases.getData_response().size());


    }


    public void testDiscoverPlaylists() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        int limit = 5;
        int offset = 0;

        HummMultipleResult<Playlist> result = humm.getUser().discoverPlaylists(limit, offset);

        assertEquals("ok", result.getStatus_response());

        assertEquals(5, result.getData_response().size());
    }

    public void testAddFavourites() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idSong = "557ecbf86a64fc1b8bed533f";

        HummSingleResult<Song> result = humm.getUser().addFavourites(idSong);

        assertEquals("ok", result.getStatus_response());

    }

    public void testAddFollowing() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idUser = "5649c572ae8c502824a46a99"; //user 1

        HummSingleResult<User> result = humm.getUser().addFollowing(idUser);

        assertEquals("ok", result.getStatus_response());

    }

    public void testRemoveFollowing() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idUser = "5649c572ae8c502824a46a99"; //user 1

        HummSingleResult<User> result = humm.getUser().removeFollowing(idUser);

        assertEquals("ok", result.getStatus_response());

    }

    public void testAddPlays() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idSong = "557ecbf86a64fc1b8bed533f"; //song2 (blur)

        HummSingleResult<Song> result = humm.getUser().addPlays(idSong);

        assertEquals("ok", result.getStatus_response());

    }

    public void testAddSubscriptions() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idPlaylist = "558c339c6a64fc1b8bf81ef3";

        HummSingleResult<Playlist> result = humm.getUser().addSubscriptions(idPlaylist);

        assertEquals("ok", result.getStatus_response());

    }

    public void testRemoveSubscriptions() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idPlaylist = "558c339c6a64fc1b8bf81ef3";

        HummSingleResult<Playlist> result = humm.getUser().removeSubscriptions(idPlaylist);

        assertEquals("ok", result.getStatus_response());

    }

    public void testGet() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idUser = "5649c572ae8c502824a46a99"; //user1

        HummSingleResult<User> result = humm.getUser().get(idUser);

        assertEquals("ok", result.getStatus_response());
        assertEquals("user1", result.getData_response().getUsername());
        assertEquals("user1", result.getData_response().getFirstName());
        assertEquals("test", result.getData_response().getLastName());

    }

    public void testGetFavourites() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idUser = "5649c572ae8c502824a46a99"; //user1
        int limit = 0;
        int offset = 0;

        HummMultipleResult<Song> result = humm.getUser().getFavourites(idUser, limit, offset);

        assertEquals("ok", result.getStatus_response());

    }

    public void testGetFollowing() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idUser = "5649c572ae8c502824a46a99"; //user1
        int limit = 0;
        int offset = 0;

        HummMultipleResult<User> result = humm.getUser().getFollowing(idUser, limit, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
        } else {
            assertNull(result); //no content
        }
    }

    public void testGetPlaylists() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idUser = "5649c572ae8c502824a46a99"; //user1
        int limit = 0;
        int offset = 0;

        HummMultipleResult<Playlist> result = humm.getUser().getPlaylists(idUser, limit, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
        } else {
            assertNull(result); //no content
        }
    }

    public void testPlays() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        String idUser = "5649c572ae8c502824a46a99"; //user1
        int limit = 0;
        int offset = 0;

        HummMultipleResult<Song> result = humm.getUser().getPlays(idUser, limit, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
        } else {
            assertNull(result); //no content
        }
    }

}
