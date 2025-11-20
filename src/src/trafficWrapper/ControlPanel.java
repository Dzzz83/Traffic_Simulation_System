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
import de.tudresden.sumo.objects.SumoPosition2D;


public class ControlPanel {
    // create the connection
    private SumoTraciConnection connection;
    // initialize the vehicleWrapper
    private VehicleWrapper vehicleWrapper;
    // initialize the boolean value isRunning
    private boolean isRunning = false;
    // default Constructor
    public ControlPanel()
    {
        System.out.println("ControlPanel created - call startSimulation() to begin");
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

    // create function getPosition
    public SumoPosition2D getPosition(String vehicleId)
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
            return new SumoPosition2D(0.0, 0.0);
        }
        try
        {
            // use the method from vehicleWrapper
            return vehicleWrapper.getPosition(vehicleId);
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the position of the vehicle " + vehicleId);
            e.printStackTrace();
        }
        return new SumoPosition2D(0.0, 0.0);
    }

    // create function getLaneID
    public String getLaneID(String vehicleId)
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
            return "";
        }
        try
        {
            // use the method from vehicleWrapper
            return vehicleWrapper.getLaneID(vehicleId);
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the laneID of the vehicle " + vehicleId);
            e.printStackTrace();
        }
        return "";
    }

    // create function getRoadID
    public String getRoadID(String vehicleId)
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
            return "";
        }
        try
        {
            // use the method from vehicleWrapper
            return vehicleWrapper.getRoadID(vehicleId);
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the roadId of the vehicle " + vehicleId);
            e.printStackTrace();
        }
        return "";
    }

    // create function addVehicle
    public void addVehicle(String vehicleId, String typeId, String routeId, int depart, double position, double speed, byte lane)
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
        }
        try
        {
            // use the method from vehicleWrapper
            vehicleWrapper.addVehicle(vehicleId, typeId, routeId, depart, position, speed, lane);
        }
        catch (Exception e)
        {
            System.out.println("Failed to add the vehicle " + vehicleId);
            e.printStackTrace();
        }
    }
    // create function setRouteID
    public void setRouteID(String vehicleId, String routeId)
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
        }
        try
        {
            // use the method from vehicleWrapper
            vehicleWrapper.setRouteID(vehicleId, routeId);
        }
        catch (Exception e)
        {
            System.out.println("Failed to set the route of the vehicle:  " + vehicleId);
            e.printStackTrace();
        }
    }

    // create function removeVehicle
    public void removeVehicle(String vehicleId, byte reason)
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
        }
        try
        {
            // use the method from vehicleWrapper
            vehicleWrapper.removeVehicle(vehicleId, reason);
        }
        catch (Exception e)
        {
            System.out.println("Failed to remove the vehicle:  " + vehicleId);
            e.printStackTrace();
        }
    }

    // create function setSpeed
    public void setSpeed(String vehicleId, double speed)
    {
        // check if running
        if (!isRunning)
        {
            System.out.println("The simulation is not running");
        }
        try
        {
            // use the method from vehicleWrapper
            vehicleWrapper.setSpeed(vehicleId, speed);
        }
        catch (Exception e)
        {
            System.out.println("Failed to set the speed of the vehicle:  " + vehicleId);
            e.printStackTrace();
        }
    }

    // create function getDistance
    public double getDistance(String vehicleId)
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
            return vehicleWrapper.getDistance(vehicleId);
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the distance of the vehicle:  " + vehicleId);
            e.printStackTrace();
        }
        return 0.0;
    }

    // create function getCo2
    public double getCO2Emission(String vehicleId)
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
            return vehicleWrapper.getCO2Emission(vehicleId);
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the CO2 Emission:  " + vehicleId);
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