import de.tudresden.sumo.cmd.Vehicle;
import it.polito.appeal.traci.SumoTraciConnection;

import java.util.List;

public class VehicleService {

    private final SumoTraciConnection conn;

    public VehicleService(SumoTraciConnection conn) {
        this.conn = conn;
    }

    public List<String> getVehicleIDs() throws Exception {
        return (List<String>) conn.do_job_get(Vehicle.getIDList());
    }

    public double[] getVehiclePosition(String id) throws Exception {
        return (double[]) conn.do_job_get(Vehicle.getPosition(id));
    }

    public void setSpeed(String id, double speed) throws Exception {
        conn.do_job_set(Vehicle.setSpeed(id, speed));
    }
}
