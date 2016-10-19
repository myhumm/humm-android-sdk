package humm.android.api.Model;

/**
 * Created by josealonsogarcia on 27/9/16.
 */

public class ChatResult<Channel> {

    private String status;
    private Channel channel;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
