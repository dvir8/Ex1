package ex1.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;



public class WGraph_Algo implements weighted_graph_algorithms{

	private weighted_graph WeightedGraph;

	/**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
	@Override
	public void init(weighted_graph g) { //init the graph
		this.WeightedGraph=g;

	}

	/**
     * Return the underlying graph of which this class works.
     * @return
     */
	@Override
	public weighted_graph getGraph() {
		return this.WeightedGraph;
	}

	 /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
	@Override
	public weighted_graph copy() {
		weighted_graph newGraph = new WGraph_DS();  //new graph	
		for (node_info nodeToCopy : this.WeightedGraph.getV()) {
			int NodeKey = nodeToCopy.getKey(); //we take the key
			newGraph.addNode(NodeKey); //and put in the new praph
			String nodeInfo = nodeToCopy.getInfo(); //take the Info 
			newGraph.getNode(NodeKey).setInfo(nodeInfo);  //and again put in the node of new graph
		}
		// now we need to pass on the Neighbors of all node
		for (node_info nodeToCopy : this.WeightedGraph.getV()) {
			int NodeKey = nodeToCopy.getKey(); // we take the key and pass with all node's Neighbors
			for (node_info nodeToCopy2 : this.WeightedGraph.getV(NodeKey)) {
				int NodeNeighborKey = nodeToCopy2.getKey();//take key of Neighbor 
				newGraph.connect(NodeKey, NodeNeighborKey, this.WeightedGraph.getEdge(NodeKey,NodeNeighborKey)); //put edeg in the new graph
			}

		}

		return newGraph;
	}	

	/**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return
     */
	@Override
	public boolean isConnected() {
		if(this.WeightedGraph.nodeSize()==0 || this.WeightedGraph.nodeSize()==1) {//if the graph is null or onr he isConnected
			return true;
		}
		Queue<node_info> nodeDataQueue = new LinkedList<node_info>(); // we need to BFS algorithms
		node_info start = this.WeightedGraph.getV().iterator().next();//we get with iterator the node's start
		nodeDataQueue.add(start); //put in the queue
		while (!nodeDataQueue.isEmpty()) { 
			node_info currentNode = nodeDataQueue.poll(); //out the node and we get start to pass on the neighbors with his key
			int Keys = currentNode.getKey();
			Collection<node_info> AllNodesThatConttect = WeightedGraph.getV(Keys);
			for (node_info neighbor2 : AllNodesThatConttect) { //all nodes that we passed, remark they with setTag , with the num 1
				if (neighbor2.getTag() != 1) {
					neighbor2.setTag(1); 		
					nodeDataQueue.add(neighbor2); //and put the neighbor in the queue
				}
			}		
		}

		for (node_info nodeData : this.WeightedGraph.getV()) {  // if one node in the graph 0 so isNotConnected
			if (nodeData.getTag() == 0) {
				return false;
			}
		}

		this.clearTags();  //If there are any changes, we will clear the graph
		return true;
	}


	private void clearTags() {
		for (node_info nodeData : this.WeightedGraph.getV()) {
			nodeData.setTag(0);
		}
	}

	/**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
	@Override
    public double shortestPathDist(int src, int dest) { // now we use the Dijkstra algorithms idea to shortest path dist
        // init distance map
        HashMap<Integer, node_info> nodesMap = new HashMap<Integer, node_info>();
        for (node_info nodeData : this.WeightedGraph.getV()) {
            nodeData.setTag(Integer.MAX_VALUE); // we update the distance of all nodes
        }

        node_info start = this.WeightedGraph.getNode(src); //node start
        start.setTag(0); // update in the beginning 
        nodesMap.put(src, start); // put in nodesMap
        while (!nodesMap.isEmpty()) { //as long as the nodeMap is not empty continue to pass until we reach the dest
            node_info currentNode = this.findMinimumDistanceNode(nodesMap); // we will look for the minimum value using another function we have built
            if (currentNode.getKey() == dest) { //if we get to dest we will stop
            	break;
            }
            int currentNodeKey = currentNode.getKey(); //the node that we found that the minimum from the neighbor
            double currentNodeDistance = currentNode.getTag(); //the distance to this node
            nodesMap.remove(currentNodeKey); //and now we delete this node from nodesMap

            for (node_info neighbor : this.WeightedGraph.getV(currentNodeKey)) { //now we pass on the neighbors of the node and take the lowest value and update it in the fire, so that it is preserved for us and continue until we reach the dest
                double distanceToNeighbor = this.WeightedGraph.getEdge(currentNodeKey, neighbor.getKey()) + currentNodeDistance;
                if (distanceToNeighbor < neighbor.getTag()) {
                    neighbor.setTag(distanceToNeighbor); //update the the short distance whenever we need to
                    nodesMap.put(neighbor.getKey(), neighbor);
                }
            }
        }

        double distance = this.WeightedGraph.getNode(dest).getTag(); //and here return the value of distance
        this.clearTags(); //and clear the graph of him value's
        return distance;
    }

    private node_info findMinimumDistanceNode(HashMap<Integer, node_info> nodesMap) { //run on the HashMap and take the Node's minimum   that we need to minimum distance 
        node_info minimumDistanceNode = null;
        double minimumDistance = Integer.MAX_VALUE; //defult
        for (node_info node : nodesMap.values()) {
            if (node.getTag() <= minimumDistance) {
                minimumDistanceNode = node; 
                minimumDistance = node.getTag(); // and update the node of the minimum value
            }
        }

        return minimumDistanceNode;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) { //now we use the Dijkstra algorithms idea to shortest path to take list with nodes with the shortest path
        // init distance map
        HashMap<Integer, node_info> nodesMap = new HashMap<>();
        for (node_info nodeData : this.WeightedGraph.getV()) {
            nodeData.setTag(Double.MAX_VALUE);
        }

        // init shortest path map
        HashMap<Integer, ArrayList<node_info>> shortestPathMap = new HashMap<>();
        for (node_info nodeData : this.WeightedGraph.getV()) {
            shortestPathMap.put(nodeData.getKey(), null);
        }

        node_info start = this.WeightedGraph.getNode(src); //strat with start
        nodesMap.put(src, start); //put in nodesMap and init setTag 0
        start.setTag(0);
        ArrayList<node_info> path = new ArrayList<>();
        path.add(start);
        shortestPathMap.put(src, path); // put in shortestPathMap that we save the shortest path
        while (!nodesMap.isEmpty()) { //as long as the nodeMap is not empty continue to pass until we reach the dest
            node_info currentNode = this.findMinimumDistanceNode(nodesMap); //again find the Node's minimum and and break that the key = dest
            if (currentNode.getKey() == dest) {
            	break;
            }
            int currentNodeKey = currentNode.getKey(); //get the key of node
            double currentNodeDistance = currentNode.getTag(); //get the double of node
            nodesMap.remove(currentNodeKey);
            ArrayList<node_info> currentNodePath = shortestPathMap.get(currentNodeKey);

            for (node_info neighbor : this.WeightedGraph.getV(currentNodeKey)) { //now we pass on the neighbor's node and when we found low way we put this in list
                double distanceToNeighbor = this.WeightedGraph.getEdge(currentNodeKey, neighbor.getKey()) + currentNodeDistance;
                if (distanceToNeighbor < neighbor.getTag()) {
                    ArrayList<node_info> currentNeighborPath = new ArrayList<>(currentNodePath);
                    currentNeighborPath.add(neighbor);
                    shortestPathMap.put(neighbor.getKey(), currentNeighborPath);
                    neighbor.setTag(distanceToNeighbor); // update the minimum distace ot the node
                    nodesMap.put(neighbor.getKey(), neighbor); //put in nodesMap and from there we will continue
                }
            }
        }

        this.clearTags(); //and clear the graph of him value's
        return shortestPathMap.get(dest); 
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
	@Override
    public boolean save(String file) { //save in file txt
        try {
            PrintWriter pw = new PrintWriter(new File(file));//put the string in the new file
            StringBuilder sb = new StringBuilder(); // we will perform the actions we want to be added to the text

            for (node_info nodeInfo : this.WeightedGraph.getV()) {
                sb.append(nodeInfo.getKey()); //pass all the nodes and put in line 1
                sb.append(",");
            }

            sb.append("\n");

            for (node_info nodeInfo : this.WeightedGraph.getV()) { // and now we put in file all line need to contain two nodes and the Edeg of them
                int nodeInfoKey = nodeInfo.getKey();
                for (node_info Ni : this.WeightedGraph.getV(nodeInfoKey)) {
                    sb.append(nodeInfo.getKey());
                    sb.append(",");
                    sb.append(Ni.getKey());
                    sb.append(",");
                    sb.append(this.WeightedGraph.getEdge(nodeInfoKey, Ni.getKey()));
                    sb.append("\n");
                }
            }

            pw.write(sb.toString()); 
            pw.close(); // we ended
            return true;
        } catch (FileNotFoundException e) { //catch if we have bug

            e.printStackTrace();
        }
        return false;
    }

	/**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) { //we need to read the file txt that we build and put on the new graph
        weighted_graph wGraph_ds = new WGraph_DS();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine(); //read line from the txt
            String[] split = line.split(",");
            for (String key : split) {//hew we pas on line 1 and change the string to int
                wGraph_ds.addNode(Integer.parseInt(key));
            }
            Stream<String> lines = in.lines(); //Returns a Stream, the elements of which are lines read fromthis BufferedReader
            Iterator<String> iterator = lines.iterator(); //we use in iterator to all line
            while (iterator.hasNext()) {
                String edgeLine = iterator.next();
                String[] edgeParts = edgeLine.split(",");//use in split , and we pas to two key and the value of the edge 
                int key1 = Integer.parseInt(edgeParts[0]);
                int key2 = Integer.parseInt(edgeParts[1]);
                double weight = Double.parseDouble(edgeParts[2]);
                wGraph_ds.connect(key1, key2, weight); //and put in the new graph
            }
            in.close(); //close the in , we ended
            this.WeightedGraph = wGraph_ds;
            return true;      
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
