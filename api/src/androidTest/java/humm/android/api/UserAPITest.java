package humm.android.api;

import java.util.Arrays;
import java.util.List;

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

    cd 

    public void testMe() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        HummSingleResult<User> me = humm.getUser().me();

        assertEquals("ok", me.getStatus_response());

        assertEquals("deleteme100", me.getData_response().getUsername());
        assertEquals("delete", me.getData_response().getFirstName());
        assertEquals("me", me.getData_response().getLastName());
        assertEquals("deleteme100@deleteme.com", me.getData_response().getEmail());

//        List<String> likes = Arrays.asList("rock", "pop");
//        assertThat(likes, is(me.getData_response().getPreferencesLike()));

    }

    public void testDiscoverReleases() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();

        doLogin();
        int limit = 5;
        int offset = 0;

        HummMultipleResult<PlaylistOwnerList> releases = humm.getUser().discoverRealeses(limit, offset);

        assertEquals("ok", releases.getStatus_response());
        assertEquals(5, releases.getData_response().size());


    }

    public void testDiscoverArtists() throws Throwable {


        final HummAPI humm = HummAPI.getInstance();

        doLogin();
        int limit = 5;
        int offset = 0;

        HummMultipleResult<Artist> releases = humm.getUser().discoverArtists(limit, offset);

        assertEquals("ok", releases.getStatus_response());
        assertEquals(5, releases.getData_response().size());


    }


    public void testDiscoverPlaylists() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        int limit = 5;
        int offset = 0;

        HummMultipleResult<PlaylistOwnerList> result = humm.getUser().discoverPlaylists(limit, offset);

        assertEquals("ok", result.getStatus_response());

        assertEquals(5, result.getData_response().size());
    }

    public void testAddFavourites() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idSong = "557ecbf86a64fc1b8bed533f";

        HummSingleResult<Song> result = humm.getUser().addFavourites(idSong);

        assertEquals("ok", result.getStatus_response());

    }

    public void testAddFollower() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idUser = "5649c572ae8c502824a46a99"; //Blur

        HummSingleResult<User> result = humm.getUser().addFollows(idUser);

        assertEquals("ok", result.getStatus_response());

    }

    //FIXME: returns
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

        HummMultipleResult result = humm.getUser().removeFollows(idUser);

        assertEquals("ok", result.getStatus_response());

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

        HummSingleResult<User> result = humm.getUser().get(idUser);

        assertEquals("ok", result.getStatus_response());
        assertEquals("user1", result.getData_response().getUsername());
        assertEquals("user1", result.getData_response().getFirstName());
        assertEquals("test", result.getData_response().getLastName());

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
        int limit = 0;
        int offset = 0;

        HummMultipleResult<User> result = humm.getUser().getFollows(idUser, limit, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
        } else {
            assertNull(result); //no content
        }
    }

    public void testGetPlaylists() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idUser = "564dc3630c7d72187b8908f0"; //deleteme
        int limit = 0;
        int offset = 0;

        HummMultipleResult<PlaylistOwnerInt> result = humm.getUser().getPlaylists(idUser, limit, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
        } else {
            assertNull(result); //no content
        }
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
