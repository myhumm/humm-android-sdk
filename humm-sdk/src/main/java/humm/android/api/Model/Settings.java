package humm.android.api.Model;


import java.util.HashMap;
import java.util.List;

/**
 * Created by josealonsogarcia on 23/3/16.
 */
public class Settings extends Humm {

    protected HashMap stats;
    protected HashMap account;
    protected List<HashMap> services;

    public HashMap getStats() {
        return stats;
    }

    public void setStats(HashMap stats) {
        this.stats = stats;
    }

    public HashMap<String, String> getAccount() {
        return account;
    }

    public void setAccount(HashMap<String, String> account) {
        this.account = account;
    }

    public List<HashMap> getServices() {
        return services;
    }

    public void setServices(List<HashMap> services) {
        this.services = services;
    }
}
