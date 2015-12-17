package humm.android.api.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by josealonsogarcia on 16/9/15.
 */
public abstract class Playlist extends Humm {

    public static String ENDPOINT_COVER = "http://wave.livingindietv.com/images/playlist?id=%s&size=thumb";

    private String title;
    private String description;
    private HashMap stats;
    private List contributors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getContributors() {
        return contributors;
    }

    public void setContributors(List contributors) {
        this.contributors = contributors;
    }

    public HashMap getStats() {
        return stats;
    }

    public void setStats(HashMap stats) {
        this.stats = stats;
    }

    public abstract String getOwnerAvatar();
    public abstract String getOwnerName();
    public abstract String getOwnerUid();

//    public  String getOwnerAvatar() {
//        if (this.owner != null && this.owner.get("avatar") != null) {
//            return this.owner.get("avatar");
//        }
//        return null;
//    }
//
//    public String getOwnerName() {
//        if (this.getOwner() != null && this.getOwner().get("name") != null) {
//            return this.getOwner().get("name");
//        }
//        return null;
//    }
//
//    public String getOwnerUid() {
//        if (this.getOwner() != null && this.getOwner().get("uid") != null) {
//            return this.getOwner().get("uid");
//        }
//        return null;
//    }

    public String getCover() {
//        return "http://wave.livingindietv.com/images/playlist?id=" + this.get_id() + "&size=thumb";
        return String.format(ENDPOINT_COVER, this.get_id());
    }
}
