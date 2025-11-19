package trafficWrapper;
import java.awt.geom.Point2D;
import java.util.List;
import de.tudresden.sumo.objects.SumoPosition2D;

public class Main
{
    public static void main(String[] args) {
        // create the Simulation Manager instance
        SimulationManager sim = new SimulationManager();
        // start the simulation
        System.out.println("Starting the simulation");
        sim.startSimulation();
        // check isRunning
        boolean isRunning = sim.isRunning();
        System.out.println("The simulation is: " + isRunning);
        // step a few times
        for (int i = 0; i < 5; i++)
        {
            System.out.println();
            System.out.println("Advancing " + (i+1) + " steps");
            sim.step();
            // show current time
            double currentTime = sim.getCurrentTime();
            System.out.println("The current time is: " + currentTime);
            // show vehicle count
            int vehicleCount = sim.getVehicleCount();
            System.out.println("The number of vehicles are: " + vehicleCount);
            // if vehicles > 0, show IDs and speed of the first vehicle
            if (vehicleCount > 0)
            {
                List<String> vehicleIDs = sim.getVehicleIDs();
                System.out.println("The list of all of vehicle's IDs: " + vehicleIDs);
                String vehicleID = vehicleIDs.get(0);
                double speed = sim.getVehicleSpeed(vehicleID);
                System.out.println("The speed of the first vehicle is: " + speed);
                // show position
                SumoPosition2D position = sim.getPosition(vehicleID);
                System.out.println("The position of the first vehicle is: " + position);
                // get lane id
                String laneID = sim.getLaneID(vehicleID);
                System.out.println("The lane id of the first vehicle is: " + laneID);
            }
        }
        // add vehicle
        sim.addVehicle(
                "myNewCar",     // vehicleId - unique ID for the new vehicle
                "DEFAULT_VEHTYPE", // typeId - vehicle type
                "D2 1i",       // routeId - predefined route in SUMO
                0,              // depart - departure time (0 = immediately)
                0.0,            // position - starting position on lane
                0.0,            // speed - initial speed
                (byte)0         // lane - starting lane (0 = first lane)
        );
        System.out.println("The new vehicle has been added ");
        List<String> vehicleIDs = sim.getVehicleIDs();
        String vehicleID = vehicleIDs.get(1);
        // get road id
        String roadID = sim.getRoadID(vehicleID);
        System.out.println("The road id of the second vehicle is: " + roadID);

        // set route id
        sim.setRouteID(vehicleID, "route1");
        System.out.println("The route id of the second vehicle is: " + roadID);
        // set route id
        double speed = 58.8;
        sim.setSpeed(vehicleID, speed);
        System.out.println("The speed of the second vehicle is: " + speed);

        double distance = sim.getDistance(vehicleID);
        System.out.println("The distance of the second vehicle is: " + distance);

        double co2 = sim.getCO2Emission(vehicleID);
        System.out.println("The CO2 of the second vehicle is: " + co2);

        sim.removeVehicle(vehicleID, (byte) 0);
        System.out.println("The second vehicle is removed");

        // stop the simulation
        System.out.println("Stopping the simulation");
        sim.stopSimulation();
        isRunning = sim.isRunning();
        System.out.println("The state of the simulation is: " + isRunning);
        System.out.println("The test is done");
    }
}