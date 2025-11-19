import de.tudresden.sumo.cmd.Trafficlight;
import it.polito.appeal.traci.SumoTraciConnection;

public class TrafficLightService {

    private final SumoTraciConnection conn;

    public TrafficLightService(SumoTraciConnection conn) {
        this.conn = conn;
    }

    public String getState(String tlId) throws Exception {
        return (String) conn.do_job_get(
                Trafficlight.getRedYellowGreenState(tlId)
        );
    }

    public void setState(String tlId, String newState) throws Exception {
        conn.do_job_set(
                Trafficlight.setRedYellowGreenState(tlId, newState)
        );
    }
}
