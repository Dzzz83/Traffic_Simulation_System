/*
SUMO commands follow this protocol:

GET Commands:
ReturnType result = (ReturnType) conn.do_job_get(Command.getSomething());
ReturnType result = (ReturnType) conn.do_job_get(Command.getSomething(parameter));

SET Commands:
conn.do_job_set(Command.setSomething(parameter, newValue));
conn.do_job_set(Command.setSomething(parameter1, parameter2, ...));
 */
package trafficWrapper.model;

import it.polito.appeal.traci.SumoTraciConnection;
import java.util.List;
import java.util.ArrayList;
import de.tudresden.sumo.cmd.Trafficlight;
import de.tudresden.sumo.cmd.Simulation;
import trafficWrapper.VehicleWrapper;

public class SimulationManager {
    // create the connection
    private SumoTraciConnection connection;
    // initialize the vehicleWrapper
    private VehicleWrapper vehicleWrapper;
    // initialize the boolean value isRunning
    private boolean isRunning = false;
    // default Constructor
    public SimulationManager()
    {
        System.out.println("SimulationManager created - call startSimulation() to begin");
    }
    // create function startSimulation

    public void startSimulation() {
        try {
            connection = new SumoTraciConnection(
                    "C:\\sumo-1.25.0\\bin\\sumo.exe",
                    "C:\\Users\\XUAN NGAN\\IdeaProjects\\Traffic_Simulation_System\\src\\sumo\\demo.sumocfg");

            // Keep this for auto-start, but REMOVE the remote-port line to avoid duplicate
            connection.addOption("start", "true");
            // Do NOT add: connection.addOption("remote-port", "9999");  // This causes the duplicate!

            connection.runServer();  // TraaS will auto-pick a port (e.g., 61203)

            vehicleWrapper = new VehicleWrapper(connection);
            isRunning = true;

            System.out.println("SUMO started successfully in headless mode (auto-port)");

        } catch (Exception e) {
            System.out.println("Failed to run the simulation");
            e.printStackTrace();
        }
    }
    // function getCurrentTime()
    public double getCurrentTime()
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
            return 0.0;
        }
        try
        {
            // use the SUMO command
           return (Double) connection.do_job_get(Simulation.getTime());
        }
        catch (Exception e)
        {
            System.out.println("Failed to get current time");
            e.printStackTrace();
        }
        return 0.0;
    }
    // function step
    public void step()
    {
        // check if the simulation is running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
            return;
        }
        try
        {
            // do timestep
            connection.do_timestep();
            // get current time
            double time = getCurrentTime();
            System.out.println("Step to: " + time);
        }
        catch (Exception e)
        {
            System.out.println("Failed to time step the simulation");
            e.printStackTrace();
        }
    }


    // create function getVehicleCount()
    public int getVehicleCount()
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
            return 0;
        }
        try
        {
            // use method getVehicleCount in VehicleWrapper
            return vehicleWrapper.getVehicleCount();
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the number of vehicles");
            e.printStackTrace();
        }
        return 0;
    }

      // create function getVehicleIDs
    public List<String> getVehicleIDs()
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
            return new ArrayList<>();
        }
        try
        {
            // use method from wrapper class
            return vehicleWrapper.getVehicleIDs();
        }
        catch (Exception e)
        {
            System.out.println("Failed to get lists of vehicle ids");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // create function getVehicleSpeed()
    public double getVehicleSpeed(String vehicleId)
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
            return 0.0;
        }
        try
        {
            // use the method from vehicleWrapper
            return vehicleWrapper.getVehicleSpeed(vehicleId);
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the speed");
            e.printStackTrace();
        }
        return 0.0;
    }
    // create function stopSimulation
    public void stopSimulation()
    {
        // check if connection exists
        if (connection == null)
        {
            System.out.println("The simulation is not running");
            return;
        }
        try
        {
            // close the connection
            connection.close();
            isRunning = false;
        }
        catch (Exception e)
        {
            System.out.println("Can't stop the simulation");
            e.printStackTrace();
        }
    }

    // create function isRunning()
    public boolean isRunning()
    {
        return isRunning;
    }

    public void setTrafficLightPhase(String tlId, int phaseIndex) {
        if (connection == null || connection.isClosed()) {
            System.out.println("Not connected to SUMO!");
            return;
        }

        // Define your 4 standard phases (NS-EW intersection)
        String[] phases = {
                "GGGGgrrrrrGGGGgrrrrr",  // 0: North-South green
                "yyyyyrrrrryyyyyrrrrr",  // 1: North-South yellow
                "rrrrrGGGGgrrrrrGGGGg",  // 2: East-West green
                "rrrrryyyyrrrrryyyyy"   // 3: East-West yellow
        };

        try {
            String state = phases[phaseIndex];

            // This works on EVERY TraCI4J version — official command 0xc2
            connection.do_job_set(
                    Trafficlight.setRedYellowGreenState(tlId, state)
            );

            System.out.println("SUCCESS: Traffic light '" + tlId + "' → phase " + phaseIndex);
            System.out.println("        State: " + state);

        } catch (Exception e) {
            System.err.println("FAILED to change traffic light " + tlId);
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connection != null && connection.isClosed() == false;
    }
}
