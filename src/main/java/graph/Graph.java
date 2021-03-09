package graph;

import graph.exceptions.ChangeEdgeMarkingException;
import graph.exceptions.DeleteEdgeException;
import graph.exceptions.DeleteNodeException;
import graph.exceptions.GraphAddEdgeException;
import graph.exceptions.GraphAddNodeException;
import lombok.Getter;

@Getter
public abstract class Graph {

    protected Long order;
    protected Long size;

    protected Graph() {
        this.order = 0L;
        this.size = 0L;
    }

    public abstract Graph addEdge (String fromNode, String toNode, String marking) throws GraphAddEdgeException;
    public abstract Graph addNode (String name) throws GraphAddNodeException;
    public abstract Graph changeEdgeMarking (String fromNode, String toNode, String newMarking) throws ChangeEdgeMarkingException;
    public abstract Graph deleteEdge (String fromNode, String toNode) throws DeleteEdgeException;
    public abstract Graph deleteNode (String nodeName) throws DeleteNodeException;
    public abstract void printOutGraph ();
}
