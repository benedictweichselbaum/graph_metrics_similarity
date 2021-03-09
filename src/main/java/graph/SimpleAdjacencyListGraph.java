package graph;

import graph.adjacencylist.Adjacency;
import graph.adjacencylist.Node;
import graph.exceptions.ChangeEdgeMarkingException;
import graph.exceptions.DeleteEdgeException;
import graph.exceptions.DeleteNodeException;
import graph.exceptions.GraphAddEdgeException;
import graph.exceptions.GraphAddNodeException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SimpleAdjacencyListGraph extends Graph {

    private final List<Node> nodes;
    private final Map<String, List<Adjacency>> adjacencyLists;

    public SimpleAdjacencyListGraph() {
        super();
        this.nodes = new ArrayList<>();
        this.adjacencyLists = new HashMap<>();
    }

    @Override
    public Graph addEdge(String fromNode, String toNode, String marking) throws GraphAddEdgeException {
        List<Adjacency> fromNodeAdjacencyList = this.adjacencyLists.get(fromNode);
        List<Adjacency> toNodeAdjacencyList = this.adjacencyLists.get(toNode);
        if ((!(hasNode(fromNode) || hasNode(toNode))) || hasNodeTo(fromNodeAdjacencyList, toNode) || hasNodeTo(toNodeAdjacencyList, fromNode)) {
            throw new GraphAddEdgeException();
        }
        fromNodeAdjacencyList.add(new Adjacency(toNode, marking));
        toNodeAdjacencyList.add(new Adjacency(fromNode, marking));
        size++;
        return this;
    }

    @Override
    public Graph addNode(String name) throws GraphAddNodeException {
        if (hasNode(name)) {
            throw new GraphAddNodeException();
        }
        Node newNode = new Node(name);
        this.nodes.add(newNode);
        this.adjacencyLists.put(name, new ArrayList<>());
        order++;
        return this;
    }

    @Override
    public Graph changeEdgeMarking(String fromNode, String toNode, String newMarking) throws ChangeEdgeMarkingException {
        if (!(hasNode(fromNode) && hasNode(toNode))) {
            throw new ChangeEdgeMarkingException();
        }
        List<Adjacency> fromNodeAdjacencyList = this.adjacencyLists.get(fromNode);
        List<Adjacency> toNodeAdjacencyList = this.adjacencyLists.get(toNode);

        changeEdgeMarking(fromNodeAdjacencyList, toNode, newMarking);
        changeEdgeMarking(toNodeAdjacencyList, fromNode, newMarking);

        return this;
    }

    @Override
    public Graph deleteEdge(String fromNode, String toNode) throws DeleteEdgeException {
        if (!(hasNode(fromNode) && hasNode(toNode))) {
            throw new DeleteEdgeException();
        }
        List<Adjacency> fromNodeAdjacencyList = this.adjacencyLists.get(fromNode);
        List<Adjacency> toNodeAdjacencyList = this.adjacencyLists.get(toNode);

        deleteFromAdjacencyList(fromNodeAdjacencyList, toNode);
        deleteFromAdjacencyList(toNodeAdjacencyList, fromNode);
        size--;
        return this;
    }

    @Override
    public Graph deleteNode(String nodeName) throws DeleteNodeException {
        if (!hasNode(nodeName)) {
            throw new DeleteNodeException();
        }
        this.nodes.removeIf(node -> node.getName().equals(nodeName));
        this.adjacencyLists.remove(nodeName);
        this.adjacencyLists.values().forEach(adjacencies -> deleteFromAdjacencyList(adjacencies, nodeName));
        order--;
        return this;
    }

    @Override
    public void printOutGraph() {
        StringBuilder builder = new StringBuilder();
        this.adjacencyLists.forEach((node, neighbours) -> {
            builder.append(node).append(": ");
            neighbours.forEach(neighbour -> builder.append(neighbour.getAdjacentNode()).append("; "));
            builder.append("\n");
        });
        System.out.println(builder.toString());
    }

    private void changeEdgeMarking (List<Adjacency> adjacencies, String toNode, String newMarking) {
        adjacencies.forEach(adjacency -> {
            if (adjacency.getAdjacentNode().equals(toNode)) {
                adjacency.setMarking(newMarking);
            }
        });
    }

    private void deleteFromAdjacencyList (List<Adjacency> adjacencyList, String toNode) {
        adjacencyList.removeIf(adjacency -> adjacency.getAdjacentNode().equals(toNode));
    }

    private boolean hasNode (String nodeName) {
        return this.nodes.stream().anyMatch(node -> node.getName().equals(nodeName));
    }

    private boolean hasNodeTo (List<Adjacency> adjacencyList, String node) {
        return adjacencyList.stream().anyMatch(adjacency -> adjacency.getAdjacentNode().equals(node));
    }
}
