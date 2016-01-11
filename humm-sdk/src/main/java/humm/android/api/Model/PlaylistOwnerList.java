package humm.android.api.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by josealonsogarcia on 27/11/15.
 */
public class PlaylistOwnerList extends Playlist {

    protected List<HashMap<String, String>> owner;

    public PlaylistOwnerList(List<HashMap<String, String>> owner) {
        this.owner = owner;
    }

    public List<HashMap<String, String>> getOwner() {
        return owner;
    }

    public void setOwner(List<HashMap<String, String>> owner) {
        this.owner = owner;
    }

    @Override
    public String getOwnerAvatar() {
        if (this.owner != null && this.owner.get(0) != null && this.owner.get(0).get("avatar") != null) {
            return this.owner.get(0).get("avatar");
        }
        return null;

    }

    @Override
    public String getOwnerName() {
        if (this.owner != null && this.owner.get(0) != null && this.owner.get(0).get("name") != null) {
            return this.owner.get(0).get("name");
        }
        return null;
    }

    @Override
    public String getOwnerUid() {
        if (this.owner != null && this.owner.get(0) != null && this.owner.get(0).get("uid") != null) {
            return this.owner.get(0).get("uid");
        }
        return null;
    }
}
