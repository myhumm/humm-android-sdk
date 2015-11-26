package humm.android.api.Model;

/**
 * Created by josealonsogarcia on 15/9/15.
 */
public class HummSingleResult<H extends Humm> extends HummResult {

    public H data_response;

    public H getData_response() {
        return data_response;
    }

    public void setData_response(H data_response) {
        this.data_response = data_response;
    }

}
