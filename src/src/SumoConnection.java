import it.polito.appeal.traci.SumoTraciConnection;

public class SumoConnection {

    private SumoTraciConnection conn;

    public void startSUMO() throws Exception {
        String sumoBinary = "C:\\sumo-1.25.0\\bin\\sumo-gui.exe";
        String config = "sumo/demo.sumocfg";  // relative path

        conn = new SumoTraciConnection(sumoBinary, config);
        conn.addOption("start", "true");
        conn.runServer();
    }

    public SumoTraciConnection getConn() {
        return conn;
    }
}
