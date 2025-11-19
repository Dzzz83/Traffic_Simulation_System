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

import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.ws.container.SumoPosition2D;
import it.polito.appeal.traci.SumoTraciConnection;
import java.util.List;
import java.util.ArrayList;

// TO DO:
// get number of vehicles
// get all the vehicle's ids
// get the vehicle speed

public class VehicleWrapper
{
    // initialize the connection
    private final SumoTraciConnection connection;

    // Constructor
    public VehicleWrapper(SumoTraciConnection connection)
    {
        this.connection = connection;
    }

    // getVehicleCount wrapper
    public int getVehicleCount()
    {
        try
        {
            return (Integer) connection.do_job_get(Vehicle.getIDCount());
        }
        catch (Exception e)
        {
            System.out.println("Failed to get number of vehicles");
            e.printStackTrace();
        }
        return 0;
    }

    // getVehicleID wrapper
    public List<String> getVehicleIDs()
    {
        try
        {
            return (List<String>) connection.do_job_get(Vehicle.getIDList());
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the list of vehicle IDs");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // get the vehicle's speed
    public double getVehicleSpeed(String vehicleId)
    {
        try
        {
            return (double) connection.do_job_get(Vehicle.getSpeed(vehicleId));
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the speed of the vehicle " + vehicleId);
            e.printStackTrace();
        }
        return 0.0;
    }

    public SumoPosition2D getPosition(String vehicleId) {
        try {
            // Returns an object with x and y coordinates
            return (SumoPosition2D) connection.do_job_get(Vehicle.getPosition(vehicleId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SumoPosition2D(0, 0);
    }

    // Helper to get the vehicle Angle (so you can rotate the rectangle/triangle)
    public double getAngle(String vehicleId) {
        try {
            return (Double) connection.do_job_get(Vehicle.getAngle(vehicleId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

}