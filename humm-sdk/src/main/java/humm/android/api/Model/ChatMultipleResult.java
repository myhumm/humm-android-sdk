package humm.android.api.Model;

import java.util.ArrayList;

/**
 * Created by josealonsogarcia on 27/9/16.
 */

public class ChatMultipleResult<Channel> {

    private String status;
    private ArrayList<Channel> channel;

    public ArrayList<Channel> getChannel() {
        return channel;
    }

    public void setChannel(ArrayList<Channel> channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
