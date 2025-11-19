package trafficWrapper;
import java.util.List;

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

        for (int i = 0; i <= 1000; i++)
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
            }
        }
        // stop the simulation
        System.out.println("Stopping the simulation");
        sim.stopSimulation();
        isRunning = sim.isRunning();
        System.out.println("The state of the simulation is: " + isRunning);
        System.out.println("The test is done");
    }
}