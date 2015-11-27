package humm.android.api.Model;

import java.util.HashMap;

/**
 * Created by josealonsogarcia on 27/11/15.
 */
public class PlaylistOwnerInt extends Playlist {

    private int owner;

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Override
    String getOwnerAvatar() {
        return null;
    }

    @Override
    String getOwnerName() {
        return null;
    }

    @Override
    String getOwnerUid() {
        return null;
    }
}
