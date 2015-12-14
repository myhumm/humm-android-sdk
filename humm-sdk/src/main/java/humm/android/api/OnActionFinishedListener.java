package humm.android.api;


/**
 * Created by josealonsogarcia on 27/10/15.
 */
public interface OnActionFinishedListener {
    void actionFinished(Object result);
    void onError(Exception e);
}
