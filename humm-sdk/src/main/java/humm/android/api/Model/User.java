package humm.android.api.Model;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by josealonsogarcia on 28/9/15.
 */
public class User extends Humm {

    protected HashMap account;
    protected String signup;
    protected String last_visit;
    protected boolean featured;
    protected List<Song> played;
    protected List<String> genres;
    protected List<HashMap<String, String>> favourites;
    protected List<HashMap<String, String>> following;
    protected List<HashMap<String, String>> subscriptions;
    protected HashMap<String, List<String>> preferences;
    protected List<User> similar;

    public User() {
    }

    public HashMap getAccount() {
        return account;
    }

    public void setAccount(HashMap account) {
        this.account = account;
    }

    public String getUserAvatar() {
        return (String) this.account.get("uavatar");
    }

    public String getUsername() {
        return (String) this.account.get("uname");
    }

    public List<HashMap<String, String>> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<HashMap<String, String>> favourites) {
        this.favourites = favourites;
    }

    public List<HashMap<String, String>> getFollowing() {
        return following;
    }

    public void setFollowing(List<HashMap<String, String>> following) {
        this.following = following;
    }

    public List<HashMap<String, String>> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<HashMap<String, String>> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getSignup() {
        return signup;
    }

    public void setSignup(String signup) {
        this.signup = signup;
    }

    public String getLast_visit() {
        return last_visit;
    }

    public void setLast_visit(String last_visit) {
        this.last_visit = last_visit;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public List<Song> getPlayed() {
        return played;
    }

    public void setPlayed(List<Song> played) {
        this.played = played;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public HashMap<String, List<String>> getPreferences() {
        return preferences;
    }

    public void setPreferences(HashMap<String, List<String>> preferences) {
        this.preferences = preferences;
    }

    public List<User> getSimilar() {
        return similar;
    }

    public void setSimilar(List<User> similar) {
        this.similar = similar;
    }

    public ArrayList<String> getFavouritesIds() {
        ArrayList<String> result = new ArrayList<>();

        for (HashMap<String, String> favourite : this.favourites) {
            result.add(favourite.get("sid"));
        }

        return result;
    }

    public ArrayList<String> getFollowingIds() {
        ArrayList<String> result = new ArrayList<>();

        for (HashMap<String, String> favourite : this.following) {
            result.add(favourite.get("uid"));
        }

        return result;

    }

    public ArrayList<String> getSubscriptionsIds() {
        ArrayList<String> result = new ArrayList<>();

        for (HashMap<String, String> favourite : this.subscriptions) {
            result.add(favourite.get("pid"));
        }

        return result;

    }

    public String getFirstName() {
        if (this.account != null && this.account.get("name") != null) {
            LinkedTreeMap<String, String> name = (LinkedTreeMap<String, String>) this.account.get("name");

            return name.get("first");
        }

        return "";
    }

    public String getLastName() {
        if (this.account != null && this.account.get("name") != null) {
            LinkedTreeMap<String, String> name = (LinkedTreeMap<String, String>) this.account.get("name");

            return name.get("last");
        }

        return "";
    }

    public String getEmail() {
        if ((this.account != null) && this.account.get("email") != null) {
            return (String) this.account.get("email");
        }

        return "";
    }

    public String getUavatar() {
        if ((this.account != null) && this.account.get("uavatar") != null) {
            return (String) this.account.get("uavatar");
        }

        return "";
    }

    public List<String> getPreferencesLike() {
        if ((this.preferences != null) && (this.preferences.get("like") != null)) {
            return this.preferences.get("like");
        }

        return null;
    }

    public List<String> getPreferencesDislike() {
        if ((this.preferences != null) && (this.preferences.get("dislike") != null)) {
            return this.preferences.get("dislike");
        }

        return null;
    }

}
