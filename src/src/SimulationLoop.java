import it.polito.appeal.traci.SumoTraciConnection;

public class SimulationLoop implements Runnable {

    private final SumoTraciConnection conn;
    private boolean running = true;
    private final Runnable uiCallback;

    public SimulationLoop(SumoTraciConnection conn, Runnable uiCallback) {
        this.conn = conn;
        this.uiCallback = uiCallback;
    }

    @Override
    public void run() {
        while (running) {
            try {
                conn.do_timestep();
                uiCallback.run();
                Thread.sleep(100); // 0.1 sec
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() { running = false; }
}
