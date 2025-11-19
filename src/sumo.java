import de.tudresden.sumo.cmd.Vehicle;
import it.polito.appeal.traci.SumoTraciConnection;

public class sumo {

    public static void main(String[] args) {
        String sumoBinary = "C:\\sumo-1.25.0\\bin\\sumo-gui.exe";
        String configFile = "C:\\sumo-1.25.0\\docs\\tutorial\\quickstart\\data\\quickstart.sumocfg";

        SumoTraciConnection conn = new SumoTraciConnection(sumoBinary, configFile);
        conn.addOption("start", "true");
        try {
            conn.runServer();

            for (int i = 0; i < 1000; i++) {
                conn.do_timestep();
                int vehicleCount = (int) conn.do_job_get(Vehicle.getIDCount());
                System.out.println("Step " + i + ": Vehicles = " + vehicleCount);
            }

            conn.close();
            System.out.println("Simulation finished.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
