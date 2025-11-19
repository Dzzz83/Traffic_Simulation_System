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

import de.tudresden.sumo.cmd.Edge;
import it.polito.appeal.traci.SumoTraciConnection;
import java.util.List;
import java.util.ArrayList;

// TO DO:
// get number of Edges
// get all the Edge's ids
// get the Lane number

public class EdgeWrapper
{
    // initialize the connection
    private final SumoTraciConnection connection;

    // Constructor
    public EdgeWrapper(SumoTraciConnection connection)
    {
        this.connection = connection;
    }

    // getEdgeCount wrapper
    public int getEdgeCount()
    {
        try
        {
            return (Integer) connection.do_job_get(Edge.getIDCount());
        }
        catch (Exception e)
        {
            System.out.println("Failed to get number of Edges");
            e.printStackTrace(); // Print the history of function calls for debugging
        }
        return 0;
    }

    // getLaneNumber wrapper
    public List<String> getEdgeIDs()
    {
        try
        {
            return (List<String>) connection.do_job_get(Edge.getIDList());
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the list of Edge IDs");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // get the Edge's speed
    public int getLaneNumber(String EdgeId)
    {
        try
        {
            return (Integer) connection.do_job_get(Edge.getLaneNumber(EdgeId));
        }
        catch (Exception e)
        {
            System.out.println("Failed to get the speed of the Edge " + EdgeId);
            e.printStackTrace();
        }
        return 0;
    }

}