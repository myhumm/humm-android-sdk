package humm.android.api.Model;

import java.util.ArrayList;

/**
 * Created by josealonsogarcia on 18/10/16.
 */

public class DMConversationsMultipleResult<HashMap> {

    private String status;
    private ArrayList<HashMap> messages;

    public ArrayList<HashMap> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<HashMap> messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
