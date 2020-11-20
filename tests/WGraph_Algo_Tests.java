package ex1.tests;
import org.junit.jupiter.api.Test;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_Algo_Tests {
    @Test
    void copyGraph() {
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight + 1);
        graph.connect(secondKey, thirdKey, weight + 2);
        WGraph_Algo graphAlgo = new WGraph_Algo();
        graphAlgo.init(graph);
        weighted_graph copy = graphAlgo.copy();
        assertEquals(3, copy.edgeSize());
        assertEquals(weight, copy.getEdge(firstKey, secondKey));
        assertEquals(weight + 1, copy.getEdge(firstKey, thirdKey));
        assertEquals(weight + 2, copy.getEdge(secondKey, thirdKey));
    }

    @Test
    void connectedGraph() {
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight + 1);
        graph.connect(secondKey, thirdKey, weight + 2);
        WGraph_Algo graphAlgo = new WGraph_Algo();
        graphAlgo.init(graph);
        assertTrue(graphAlgo.isConnected());
    }

    @Test
    void notConnectedGraph() {
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.addNode(4);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight + 1);
        graph.connect(secondKey, thirdKey, weight + 2);
        WGraph_Algo graphAlgo = new WGraph_Algo();
        graphAlgo.init(graph);
        assertFalse(graphAlgo.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.addNode(4);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight + 1);
        graph.connect(secondKey, thirdKey, weight + 20);
        WGraph_Algo graphAlgo = new WGraph_Algo();
        graphAlgo.init(graph);
        assertEquals(17, graphAlgo.shortestPathDist(secondKey, thirdKey));
    }

    @Test
    void shortestPath() {
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.addNode(4);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight + 1);
        graph.connect(secondKey, thirdKey, weight + 20);
        WGraph_Algo graphAlgo = new WGraph_Algo();
        graphAlgo.init(graph);
        List<node_info> shortestPath = graphAlgo.shortestPath(secondKey, thirdKey);
        node_info[] expectedPath = new node_info[2];
        expectedPath[0] = graph.getNode(firstKey);
        expectedPath[1] = graph.getNode(thirdKey);
        assertArrayEquals(expectedPath, shortestPath.toArray());
    }


    @Test
    void saveToFile() {
        weighted_graph graph = new WGraph_DS();
        int firstKey = 1;
        int secondKey = 2;
        int thirdKey = 3;
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.addNode(4);
        graph.connect(firstKey, secondKey, weight);
        graph.connect(firstKey, thirdKey, weight + 1);
        graph.connect(secondKey, thirdKey, weight + 20);
        WGraph_Algo graphAlgo = new WGraph_Algo();
        graphAlgo.init(graph);
        graphAlgo.save("C:\\Users\\דביר\\Desktop\\files\\my_file.txt");
        WGraph_Algo graphAlgo2 = new WGraph_Algo();
        graphAlgo2.load("C:\\Users\\דביר\\Desktop\\files\\my_file.txt");
        weighted_graph copy = graphAlgo2.copy();
        assertEquals(4, copy.nodeSize());
        assertEquals(3, copy.edgeSize());
        assertEquals(weight, copy.getEdge(firstKey, secondKey));
        assertEquals(weight + 1, copy.getEdge(firstKey, thirdKey));
        assertEquals(weight + 20, copy.getEdge(secondKey, thirdKey));
    }
}
