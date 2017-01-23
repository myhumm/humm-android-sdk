package humm.android.api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by josealonsogarcia on 15/9/15.
 */
public class Artist extends Humm implements Parcelable {

    protected List<String> moods;
    protected List<HashMap> similar;
    protected HashMap account;
    protected HashMap<String, String> urls;
    //    private List<String> images;
//    private List<String> following;
    protected HashMap<String, String> stats;

    public Artist() {
    }

    public List<String> getMoods() {
        return moods;
    }

    public void setMoods(List<String> moods) {
        this.moods = moods;
    }

    public HashMap getAccount() {
        return account;
    }

    public void setAccount(HashMap account) {
        this.account = account;
    }

    public List<HashMap> getSimilar() {
        return similar;
    }

    public void setSimilar(List<HashMap> similar) {
        this.similar = similar;
    }


    public HashMap<String, String> getUrls() {
        return urls;
    }

    public void setUrls(HashMap<String, String> urls) {
        this.urls = urls;
    }

//    public List<String> getImages() {
//        return images;
//    }
//
//    public void setImages(List<String> images) {
//        this.images = images;
//    }

//    public List<String> getFollowing() {
//        return following;
//    }
//
//    public void setFollowing(List<String> following) {
//        this.following = following;
//    }

    public HashMap<String, String> getStats() {
        return stats;
    }

    public void setStats(HashMap<String, String> stats) {
        this.stats = stats;
    }

    public String getAvatar() {
        if (this.account != null && this.account.get("avatar") != null) {
            return (String) this.account.get("avatar");
        }
        return null;
    }

    public String getBio() {
        if (this.account != null && this.account.get("bio") != null) {
            return (String) this.account.get("bio");
        }
        return null;
    }

    public String getName() {
        if (this.account != null && this.account.get("name") != null) {
            LinkedTreeMap<String, String> artist = (LinkedTreeMap) this.account.get("name");
            return artist.get("first");
        }

        return null;
    }

    public String getHashtag() {
        if (this.account != null && this.account.get("name") != null) {
            LinkedTreeMap<String, String> artist = (LinkedTreeMap) this.account.get("name");
            return artist.get("hashtag");
        }

        return null;
    }


    public String getYoutubeURL() {
        if ((this.urls != null) && (this.urls.get("youtube") != null)) {
            return this.urls.get("youtube");
        }

        return null;
    }

    public String getSpotifyURL() {
        if ((this.urls != null) && (this.urls.get("spotify_url") != null)) {
            return this.urls.get("spotify_url");
        }

        return null;
    }

    public String getPopularity() {
        if ((this.stats != null) && (this.stats.get("popularity") != null)) {
            return this.stats.get("popularity");
        }

        return null;
    }

    public String getPlaylists() {
        if ((this.stats != null) && (this.stats.get("playlists") != null)) {
            return this.stats.get("playlists");
        }

        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Artist> CREATOR
            = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(get_id());
        dest.writeList(moods);
        dest.writeList(similar);
        dest.writeMap(account);
        dest.writeMap(urls);
        dest.writeMap(stats);
    }

    private Artist(Parcel in) {
        set_id(in.readString());
        moods = new ArrayList<>();
        in.readList(moods, null);
        similar = new ArrayList<>();
        in.readList(similar, null);
        account = new HashMap();
        in.readMap(account, null);
        urls = new HashMap<String, String>();
        in.readMap(urls, null);
        stats = new HashMap<String, String>();
        in.readMap(stats, null);

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Artist))
        {
            return false;
        }

        Artist artistToCompare = (Artist) o;

        return this._id.equals(artistToCompare.get_id());
    }
}
