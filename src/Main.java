import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] firstLine = br.readLine().split(" ");


        int numberOfRA = Integer.parseInt(firstLine[0]);
        int numberOfDays = Integer.parseInt(firstLine[1]);
        int maxNumberOfDaysForRA = 1;

        HashMap<Integer, String> VertexToRA = new HashMap<>();

        int totalNumberOfVertices = numberOfRA + numberOfDays + 2; //2 for source and sink

        final int source = totalNumberOfVertices - 2;
        final int sink = totalNumberOfVertices - 1;

        FlowNetwork G = new FlowNetwork(totalNumberOfVertices);

        //Example:
        //3 4
        //RA 0,1,2 Days 3,4,5,6 and s = 7 t = 8.

        // DAYS to Sink - Day vertices are numberOfRA to numberOfRA + numberOfDays
        for (int i = numberOfRA; i < totalNumberOfVertices - 2; i++) {
            // Add edges from each day to the sink, weight 2
            G.addEdge(new FlowEdge(i, sink, 2));
        }

        // Add edge from each RA to their days they want
        for (int i = 0; i < numberOfRA; i++) {
            String[] line = br.readLine().split(" ");
            String nameOfRA = line[0];
            VertexToRA.put(i, nameOfRA);
            for (int j = 1; j < line.length; j++) {
                int day = Integer.parseInt(line[j]);
                //i: 0
                //numberOfRA + day: 11 + 3 = 14
                G.addEdge(new FlowEdge(i, numberOfRA + day - 1, 1));
            }
        }

        while (true) {

            // SOURCE to RA  - vertices are 0 to numberOfRA
            for (int i = 0; i < numberOfRA; i++) {
                G.addEdge(new FlowEdge(source, i, maxNumberOfDaysForRA));
            }


            //Compute maximum flow and minimum cut
            FordFulkerson fordFulkerson = new FordFulkerson(G, source, sink);


            /*Print graph
            for (int v = 0; v < G.V(); v++) {
                for (FlowEdge e : G.adj(v)) {
                    //if ((v == e.from()))
                       //System.out.println(" " + e);

                }
            }*/


            

            if (numberOfDays * 2 == fordFulkerson.value()) {
                break;
            }
            maxNumberOfDaysForRA++;

        }
        System.out.println(maxNumberOfDaysForRA);
        @SuppressWarnings("unchecked")
        ArrayList<String>[] days = new ArrayList[numberOfDays];
        for (var i = 0; i < days.length; i++) {
            days[i] = new ArrayList<>();
        }
        for (var i = 0; i < numberOfRA; i++) {
            for (var edge : G.adj(i)) {
                if (edge.from() != i) continue;
                if (edge.flow() > 0) {
                    days[edge.to() - numberOfRA].add(VertexToRA.get(i));
                }
            }
        }
        for (var i = 0; i < days.length; i++) {
            System.out.print("Day " + (i + 1) + ":");
            for (var name : days[i]) {
                System.out.print(" " + name);
            }
            System.out.println();
        }
    }
}