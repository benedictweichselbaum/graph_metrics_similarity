package main;

import graph.Graph;
import graph.SimpleAdjacencyListGraph;

public class Main {

    public static void main(String[] args) throws Exception {
        Graph graph = new SimpleAdjacencyListGraph();
        graph.addNode("München");
        graph.addNode("Nürnberg");
        graph.addNode("Bamberg");

        graph.addEdge("München", "Nürnberg", null);
        graph.addEdge("Bamberg", "Nürnberg", null);
        graph.addEdge("Bamberg", "München", null);


        graph.printOutGraph();

        graph.deleteEdge("München", "Nürnberg");

        graph.printOutGraph();

        graph.deleteNode("Bamberg");

        graph.printOutGraph();
    }
}
