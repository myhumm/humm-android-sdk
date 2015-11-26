package humm.android.api.Model;

import java.util.List;

/**
 * Created by josealonsogarcia on 15/9/15.
 */
public class HummMultipleResult<H extends Humm> extends HummResult {

    public List<H> data_response;

    public List<H> getData_response() {
        return data_response;
    }

    public void setData_response(List<H> data_response) {
        this.data_response = data_response;
    }

}
