package trafficWrapper;
import java.awt.geom.Point2D;
import java.util.List;
import de.tudresden.sumo.objects.SumoPosition2D;

public class Main
{
    public static void main(String[] args) {
        // create the Simulation Manager instance
        ControlPanel panel = new ControlPanel();
        // start the simulation
        System.out.println("Starting the simulation");
        panel.startSimulation();
        // check isRunning
        boolean isRunning = panel.isRunning();
        System.out.println("The simulation is: " + isRunning);
        // step a few times
        for (int i = 0; i < 5; i++)
        {
            System.out.println();
            System.out.println("Advancing " + (i+1) + " steps");
            panel.step();
            // show current time
            double currentTime = panel.getCurrentTime();
            System.out.println("The current time is: " + currentTime);
            // show vehicle count
            int vehicleCount = panel.getVehicleCount();
            System.out.println("The number of vehicles are: " + vehicleCount);
            // if vehicles > 0, show IDs and speed of the first vehicle
            if (vehicleCount > 0)
            {
                // get the list of vehicle ids
                List<String> vehicleIDs = panel.getVehicleIDs();
                System.out.println("The list of all of vehicle's IDs: " + vehicleIDs);
                // get the ID of the first vehicle
                String firstVehicleID = vehicleIDs.get(0);
                // get the speed of the first vehicle
                double speed = panel.getVehicleSpeed(firstVehicleID);
                System.out.println("The speed of the first vehicle is: " + speed);
                // show position
                SumoPosition2D position = panel.getPosition(firstVehicleID);
                System.out.println("The position of the first vehicle is: " + position);
                // get lane id
                String laneID = panel.getLaneID(firstVehicleID);
                System.out.println("The lane id of the first vehicle is: " + laneID);
            }
        }
        // add vehicle
        panel.addVehicle(
                "myNewCar",     // vehicleId - unique ID for the new vehicle
                "DEFAULT_VEHTYPE", // typeId - vehicle type
                "route01",       // routeId - predefined route in SUMO
                0,              // depart - departure time (0 = immediately)
                0.0,            // position - starting position on lane
                0.0,            // speed - initial speed
                (byte)0         // lane - starting lane (0 = first lane)
        );
        System.out.println("The new vehicle has been added ");
        // get the list of vehicle's ids
        List<String> vehicleIDs = panel.getVehicleIDs();
        String vehicleID = vehicleIDs.get(1);
        // get road id
        String roadID = panel.getRoadID(vehicleID);
        System.out.println("The road id of the second vehicle is: " + roadID);
        // set the route id
        panel.setRouteID(vehicleID, "route02");
        System.out.println("The route id of the second vehicle is: " + roadID);
        // set speed
        double speed = 58.8;
        panel.setSpeed(vehicleID, speed);
        System.out.println("The speed of the second vehicle is: " + speed);
        // get distance
        double distance = panel.getDistance(vehicleID);
        System.out.println("The distance of the second vehicle is: " + distance);
        // get co2
        double co2 = panel.getCO2Emission(vehicleID);
        System.out.println("The CO2 of the second vehicle is: " + co2);
        // remove the vehicle
        panel.removeVehicle(vehicleID, (byte) 0);
        System.out.println("The second vehicle is removed");

        // stop the simulation
        System.out.println("Stopping the simulation");
        panel.stopSimulation();
        isRunning = panel.isRunning();

        System.out.println("The state of the simulation is: " + isRunning);
        System.out.println("The test is done");
    }
}