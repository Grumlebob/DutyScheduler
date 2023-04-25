import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] firstLine = br.readLine().split(" ");

        int numberOfRA = Integer.parseInt(firstLine[0]);
        int numberOfDays = Integer.parseInt(firstLine[1]);

        HashMap<String, Integer> RAtoVertex = new HashMap<String, Integer>();
        HashMap<Integer, String> VertexToRA = new HashMap<Integer, String>();

        int totalNumberOfVertices = numberOfRA + numberOfDays + 2; //2 for source and sink

        final int source = totalNumberOfVertices - 1;
        final int sink = totalNumberOfVertices - 2;

        System.out.println("source:" + source);
        System.out.println("sink:" + sink);

        FlowNetwork G = new FlowNetwork(totalNumberOfVertices);

        //Example:
        //3 4
        //RA 0,1,2 Days 3,4,5,6 and s = 7 t = 8.

        // SOURCE to RA  - vertices are 0 to numberOfRA
        for (int i = 0; i < numberOfRA; i++) {
            G.addEdge(new FlowEdge(source, i, numberOfDays));
        }
        // DAYS to Sink - Day vertices are numberOfRA to numberOfRA + numberOfDays
        for (int i = numberOfRA-1; i < numberOfRA + numberOfDays; i++) {
            // Add edges from each day to the sink, weight 2
            G.addEdge(new FlowEdge(i, sink, 2));
        }

        //// Add edge from each RA to their days they want
        for (int i = 0; i < numberOfRA; i++) {
            String[] line = br.readLine().split(" ");
            String nameOfRA = line[0];
            RAtoVertex.put(nameOfRA, i);
            VertexToRA.put(i, nameOfRA);
            for (int j = 1; j < line.length - 1; j++) {
                int day = Integer.parseInt(line[j]);
                //i: 0
                //numberOfRA + day: 11 + 3 = 14
                G.addEdge(new FlowEdge(i, numberOfRA + day-1, 1));
            }
        }

        //Compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(G, source, sink);
        System.out.println("Max flow value = " + maxflow.value());

        //Print graph
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                //if ((v == e.from()) && e.flow() > 0)
                //    System.out.println(" " + e);
            }
        }

        if (numberOfDays*2 == maxflow.value()) {
            System.out.println("YES");
        }
        else
        {
            System.out.println("NO");
        }
    }
}