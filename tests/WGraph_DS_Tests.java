package ex1.tests;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;

import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

public class WGraph_DS_Tests {
    @Test
    void addNodeToGraph() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        assertEquals(1, graph.getNode(1).getKey());
        assertEquals(1, graph.getMC());
    }

    @Test
    void addNodeToGraphTwice() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(1);
        assertEquals(graph.getNode(1).getKey(), 1);
        assertEquals(1, graph.getMC());
    }

    @Test
    void getNonExistingNode() {
        weighted_graph graph = new WGraph_DS();
        assertNull(graph.getNode(1));
    }

    @Test
    void hasNonExistingEdge() {
        weighted_graph graph = new WGraph_DS();
        assertFalse(graph.hasEdge(1, 2));
    }

    @Test
    void hasExistingEdge() {
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.connect(firstKey, secondKey, weight);
        assertTrue(graph.hasEdge(1, 2));
    }

    @Test
    void getNonExistingEdge() {
        weighted_graph graph = new WGraph_DS();
        assertEquals(-1, graph.getEdge(1, 2));
    }

    @Test
    void getExistingEdge() {
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.connect(firstKey, secondKey, weight);
        assertEquals(weight, graph.getEdge(firstKey, secondKey));
    }

    @Test
    void connectNodes(){
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.connect(firstKey, secondKey, weight);
        assertEquals(3, graph.getMC());
        assertEquals(1, graph.edgeSize());
    }

    @Test
    void connectMultipleNodes(){
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight);
        graph.connect(secondKey, thirdKey, weight);
        assertEquals(6, graph.getMC());
        assertEquals(3, graph.edgeSize());
    }
    
    @Test
    void connectNodesTwice(){
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, secondKey, weight);
        assertEquals(3, graph.getMC());
        assertEquals(1, graph.edgeSize());
    }

    @Test
    void getConnectedNodes(){
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight);
        graph.connect(secondKey, thirdKey, weight);
        Collection<node_info> connectedNodes = graph.getV(firstKey);
        assertTrue(connectedNodes.contains(graph.getNode(secondKey)));
        assertTrue(connectedNodes.contains(graph.getNode(thirdKey)));
    }

    @Test
    void removeNode(){
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight);
        graph.connect(secondKey, thirdKey, weight);
        graph.removeNode(firstKey);
        assertNull(graph.getNode(firstKey));
        assertEquals(1, graph.edgeSize());
        assertFalse(graph.hasEdge(firstKey, thirdKey));
        assertFalse(graph.hasEdge(firstKey, secondKey));
        assertTrue(graph.hasEdge(thirdKey, secondKey));
    }

    @Test
    void removeEdge(){
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight);
        graph.connect(secondKey, thirdKey, weight);
        graph.removeEdge(firstKey, secondKey);
        assertEquals(2, graph.edgeSize());
        assertFalse(graph.hasEdge(firstKey, secondKey));
        assertTrue(graph.hasEdge(firstKey, thirdKey));
        assertTrue(graph.hasEdge(secondKey, thirdKey));
    }

}
