/******************************************************************************
 *  Compilation:  javac FlowEdge.java
 *  Execution:    java FlowEdge
 *  Dependencies: StdOut.java
 *
 *  Capacitated edge with a flow in a flow network.
 *
 ******************************************************************************/

public class FlowEdge {
    // to deal with floating-point roundoff errors
    private static final double FLOATING_POINT_EPSILON = 1.0E-10;

    private final int v;             // from
    private final int w;             // to
    public double capacity;   // capacity
    private double flow;             // flow


    /**
     * Initializes an edge from vertex {@code v} to vertex {@code w} with
     * the given {@code capacity} and zero flow.
     * @param tailVertex the tail vertex
     * @param headVertex the head vertex
     * @param capacity the capacity of the edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *    is a negative integer
     * @throws IllegalArgumentException if {@code capacity < 0.0}
     */
    public FlowEdge(int tailVertex, int headVertex, double capacity) {
        if (tailVertex < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (headVertex < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (!(capacity >= 0.0)) throw new IllegalArgumentException("Edge capacity must be non-negative");
        this.v         = tailVertex;
        this.w         = headVertex;
        this.capacity  = capacity;
        this.flow      = 0.0;
    }

    /**
     * Initializes an edge from vertex {@code v} to vertex {@code w} with
     * the given {@code capacity} and {@code flow}.
     * @param tailVertex the tail vertex
     * @param headVertex the head vertex
     * @param capacity the capacity of the edge
     * @param flow the flow on the edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *    is a negative integer
     * @throws IllegalArgumentException if {@code capacity} is negative
     * @throws IllegalArgumentException unless {@code flow} is between
     *    {@code 0.0} and {@code capacity}.
     */
    public FlowEdge(int tailVertex, int headVertex, double capacity, double flow) {
        if (tailVertex < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (headVertex < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (!(capacity >= 0.0))  throw new IllegalArgumentException("edge capacity must be non-negative");
        if (!(flow <= capacity)) throw new IllegalArgumentException("flow exceeds capacity");
        if (!(flow >= 0.0))      throw new IllegalArgumentException("flow must be non-negative");
        this.v         = tailVertex;
        this.w         = headVertex;
        this.capacity  = capacity;
        this.flow      = flow;
    }

    /**
     * Initializes a flow edge from another flow edge.
     * @param e the edge to copy
     */
    public FlowEdge(FlowEdge e) {
        this.v         = e.v;
        this.w         = e.w;
        this.capacity  = e.capacity;
        this.flow      = e.flow;
    }

    /**
     * Returns the tail vertex of the edge.
     * @return the tail vertex of the edge
     */
    public int from() {
        return v;
    }

    /**
     * Returns the head vertex of the edge.
     * @return the head vertex of the edge
     */
    public int to() {
        return w;
    }

    /**
     * Returns the capacity of the edge.
     * @return the capacity of the edge
     */
    public double capacity() {
        return capacity;
    }

    /**
     * Returns the flow on the edge.
     * @return the flow on the edge
     */
    public double flow() {
        return flow;
    }


    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("invalid endpoint");
    }

    public double residualCapacityTo(int vertex) {
        if      (vertex == v) return flow;              // backward edge
        else if (vertex == w) return capacity - flow;   // forward edge
        else throw new IllegalArgumentException("invalid endpoint");
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (!(delta >= 0.0)) throw new IllegalArgumentException("Delta must be non-negative");

        if      (vertex == v) flow -= delta;           // backward edge
        else if (vertex == w) flow += delta;           // forward edge
        else throw new IllegalArgumentException("invalid endpoint");

        // round flow to 0 or capacity if within floating-point precision
        if (Math.abs(flow) <= FLOATING_POINT_EPSILON)
            flow = 0;
        if (Math.abs(flow - capacity) <= FLOATING_POINT_EPSILON)
            flow = capacity;

        if (!(flow >= 0.0))      throw new IllegalArgumentException("Flow is negative");
        if (!(flow <= capacity)) throw new IllegalArgumentException("Flow exceeds capacity");
    }


    /**
     * Returns a string representation of the edge.
     * @return a string representation of the edge
     */
    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }


}
