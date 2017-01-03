package humm.android.api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by josealonsogarcia on 28/9/15.
 */
public class User extends Humm implements Parcelable {

    public static final String SERVICE_TWITTER = "twitter";
    public static final String SERVICE_FACEBOOK = "facebook";

    protected HashMap account;
    protected String signup;
    protected String last_visit;
    protected boolean featured;
    protected List<Song> played;
    protected List<String> genres;
    protected List<HashMap<String, String>> favourites;
    protected List<HashMap<String, String>> following;
    protected List<HashMap<String, String>> subscriptions;
    protected List<HashMap<String, HashMap<String, String>>> services;
    protected HashMap<String, List<String>> preferences;
    protected List<User> similar;
    protected List<Channel> channels;
    protected List<HashMap<String, String>> messagesNotReaded;

    public User() {
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
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

    public List<HashMap<String, String>> getMessagesNotReaded() {
        return messagesNotReaded;
    }

    public void setMessagesNotReaded(List<HashMap<String, String>> messagesNotReaded) {
        this.messagesNotReaded = messagesNotReaded;
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

    public List<HashMap<String, HashMap<String, String>>> getServices() {
        return services;
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

    public HashMap<String, String> getTwitterService() {
        if (this.services == null) {
            return null;
        }
        for (HashMap service : this.services) {
            if (service.get(SERVICE_TWITTER) != null) {
                return (HashMap<String, String>) service.get(SERVICE_TWITTER);
            }
        }
        return null;
    }

    public HashMap<String, String> getFacebookService() {
        if (this.services == null) {
            return null;
        }

        for (HashMap service : this.services) {
            if (service.get(SERVICE_FACEBOOK) != null) {
                return (HashMap<String, String>) service.get(SERVICE_FACEBOOK);
            }
        }
        return null;
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(get_id());
        dest.writeMap(account);
        dest.writeString(signup);
        dest.writeString(last_visit);
        dest.writeInt(featured ? 0 : 1);
        dest.writeList(played);
        dest.writeList(genres);
        dest.writeList(favourites);
        dest.writeList(following);
        dest.writeList(subscriptions);
        dest.writeList(services);
        dest.writeMap(preferences);
        dest.writeList(similar);

    }

    private User(Parcel in) {
        set_id(in.readString());
        account = new HashMap();
        in.readMap(account, null);
        signup = in.readString();
        last_visit = in.readString();
        if (in.readInt() == 0) {
            featured = true;
        } else {
            featured = false;
        }

        played = new ArrayList<>();
        in.readList(played, null);
        genres = new ArrayList<>();
        in.readList(genres, null);
        favourites = new ArrayList<>();
        in.readList(favourites, null);
        following = new ArrayList<>();
        in.readList(following, null);
        subscriptions = new ArrayList<>();
        in.readList(subscriptions, null);
        services = new ArrayList<>();
        in.readList(services, null);
        preferences = new HashMap<>();
        in.readMap(preferences, null);
        similar = new ArrayList<>();
        in.readList(similar, null);

    }

}
