/*
SUMO commands follow this protocol:

GET Commands:
ReturnType result = (ReturnType) conn.do_job_get(Command.getSomething());
ReturnType result = (ReturnType) conn.do_job_get(Command.getSomething(parameter));

SET Commands:
conn.do_job_set(Command.setSomething(parameter, newValue));
conn.do_job_set(Command.setSomething(parameter1, parameter2, ...));
 */
package trafficWrapper;
import it.polito.appeal.traci.SumoTraciConnection;
import java.util.List;
import java.util.ArrayList;
import de.tudresden.sumo.cmd.Simulation;

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
    public void startSimulation()
    {
        try {
            // the first parameter tells TraaS which GUI to use
            // the second parameter tells TraaS what map file to load
            // initialize the connection with "C:\\Program Files (x86)\\Eclipse\\Sumo\\bin\\sumo-gui.exe",
            // "C:\\Program Files (x86)\\Eclipse\\Sumo\\doc\\tutorial\\quickstart\\data\\quickstart.sumocfg"
            connection = new SumoTraciConnection("C:\\Program Files (x86)\\Eclipse\\Sumo\\bin\\sumo-gui.exe",
                    "C:\\Program Files (x86)\\Eclipse\\Sumo\\doc\\tutorial\\quickstart\\data\\quickstart.sumocfg");
            // start the simulation
            connection.runServer();
            // initialize the wrapper
            vehicleWrapper = new VehicleWrapper(connection);
            // set the isRunning to true
            isRunning = true;
        }
        catch (Exception e)
        {
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
}
