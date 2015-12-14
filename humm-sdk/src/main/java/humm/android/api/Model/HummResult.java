package humm.android.api.Model;

/**
 * Created by josealonsogarcia on 23/11/15.
 */
public class HummResult {

    public String status_response;
    public String error_response;

    public String getStatus_response() {
        return status_response;
    }

    public void setStatus_response(String status_response) {
        this.status_response = status_response;
    }

    public String getError_response() {
        return error_response;
    }

    public void setError_response(String error_response) {
        this.error_response = error_response;
    }
}
