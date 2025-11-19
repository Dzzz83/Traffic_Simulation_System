package trafficWrapper;

import de.tudresden.sumo.cmd.Lane;
import de.tudresden.ws.container.SumoGeometry;
import it.polito.appeal.traci.SumoTraciConnection;
import java.util.List;
import java.util.ArrayList;

public class MapWrapper {
    private final SumoTraciConnection connection;

    public MapWrapper(SumoTraciConnection connection) {
        this.connection = connection;
    }

    public List<String> getAllLaneIDs() {
        try {
            return (List<String>) connection.do_job_get(Lane.getIDList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public SumoGeometry getLaneShape(String laneId) {
        try {
            // SumoGeometry contains a list of points (x,y) describing the lane
            return (SumoGeometry) connection.do_job_get(Lane.getShape(laneId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}