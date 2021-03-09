package graph.adjacencylist;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Node {

    @Setter
    private String name;

    public Node(String name) {
        this.name = name;
    }
}
