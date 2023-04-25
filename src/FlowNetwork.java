import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlowNetwork {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;

    private List<List<FlowEdge>> adj2D = new ArrayList<List<FlowEdge>>();

    //private final HashMap<Integer, ArrayList<FlowEdge>> adjMap = new HashMap<Integer, ArrayList<FlowEdge>>();

    public FlowNetwork(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Graph must be non-negative");
        this.V = V;
        this.E = 0;
        adj2D = new ArrayList<List<FlowEdge>>(V);
        for (int v = 0; v < V; v++)
            adj2D.add(new ArrayList<FlowEdge>());
            //adjMap.put(v, new ArrayList<FlowEdge>());
    }

    /**
     * Returns the number of vertices in the edge-weighted graph.
     * @return the number of vertices in the edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the edge-weighted graph.
     * @return the number of edges in the edge-weighted graph
     */
    public int E() {
        return E;
    }


    /**
     * Adds the edge {@code e} to the network.
     * @param e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between
     *         {@code 0} and {@code V-1}
     */
    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj2D.get(v).add(e);
        adj2D.get(w).add(e);
        E++;
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj2D.get(v);
    }

    public Iterable<FlowEdge> edges() {
        ArrayList<FlowEdge> list = new ArrayList<>();
        for (int v = 0; v < V; v++)
            for (FlowEdge e : adj(v)) {
                if (e.to() != v)
                    list.add(e);
            }
        return list;
    }


    /**
     * Returns a string representation of the flow network.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *    followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ":  ");
            for (FlowEdge e : adj(v)) {
                if (e.to() != v) s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


}
