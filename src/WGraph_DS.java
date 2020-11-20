package ex1.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class WGraph_DS implements weighted_graph {
	private HashMap<Integer, node_info> nodes = new HashMap(); //HashMap to the nodes
	private HashMap<Integer,HashMap<Integer , Double>  > edegMap = new HashMap() ; //HashMap to find node ,and in HashMap to get the neighbors of that node
	private int ModeCount; //Operation on the graph
	private int edegCount; //edegCount in the graph
    
	/**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
	@Override
	public node_info getNode(int key) {
		return (node_info) nodes.get(key); //return the node
	}

	/**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
	@Override
	public boolean hasEdge(int node1, int node2) { //return if this two nodes is connected 
		if(nodes.get(node1)==null || nodes.get(node2)==null) { //if one of them null, so false
			return false;
		}
		return this.edegMap.get(node1).containsKey(node2); //take one of nodes, we will chack with containsKey that tell as true or false if he in the neighbor's HashMap
	}

	 /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
	@Override
	public double getEdge(int node1, int node2) { 
		if(nodes.get(node1)==null || nodes.get(node2)==null) {//if one of them null, so false
			return -1;
		}
		
			if(this.edegMap.get(node1).get(node2) == null) { //if the edge is null ,return -1
				return -1;
			}
			return this.edegMap.get(node1).get(node2); //else return the value of edge

	}

	/**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
	@Override
	public void addNode(int key) {
		node_info Node1 = new NodeInfo(key) ;
		if(!nodes.containsKey(key)) {
			this.nodes.put(key, Node1);
			this.edegMap.put(key, new HashMap<Integer,Double>());
			this.ModeCount++;
		}
		
	}

	/**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
	@Override
	public void connect(int node1, int node2, double w) {
			
			if(node1==node2) { // if it already exists , nothing
			return;
			}
			
			if(hasEdge(node1,node2) && w>=0){ // if it already exists the edge , we update this edge with w
				if(w!=getEdge(node1,node2)) {
					this.edegMap.get(node1).replace(node2, w);
					this.edegMap.get(node2).replace(node1, w);
					this.ModeCount++;
				}
			}
			
			if(!hasEdge(node1,node2) && w>=0) { //if not exists the edge
				this.edegMap.get(node1).put(node2, w);
				this.edegMap.get(node2).put(node1, w);
				this.edegCount++;
				this.ModeCount++;
			}
		
	}

	/**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     * @return Collection<node_data>
     */
	@Override
	public Collection<node_info> getV() { //return all nodes in the HashMap nodes
		return nodes.values();
	}

	/**
    *
    * This method returns a Collection containing all the
    * nodes connected to node_id
    * Note: this method can run in O(k) time, k - being the degree of node_id.
    * @return Collection<node_data>
    */
	@Override
	public Collection<node_info> getV(int node_id) { //with the key we get all the neighbors of this node
		node_info Node1 = this.getNode(node_id);
		Collection<node_info> saves = new ArrayList<>(); //we used in Collection from type ArrayList to save them
		Collection<Integer> v = this.edegMap.get(node_id).keySet(); //and used Collection to pass all the neighbors of that node
		for ( Integer Keys: v) {
			saves.add(this.nodes.get(Keys));
		}
		return saves; //return the saves
		
	}

	/**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
	@Override
	public node_info removeNode(int key) { 
		if (!this.nodes.containsKey(key)) { //if the node not founf in nodes's HashMap , we dont delete
			return null;
		}
		node_info nodeR = this.nodes.get(key);
		this.nodes.remove(key); //remove from the nodes's HashMap
		Collection<Integer> v = this.edegMap.get(key).keySet();
		int edegs = this.edegMap.get(key).keySet().size();
		this.edegMap.remove(key);//remove from edegMap because we deleted
		 
		for( Integer Keys: v) { //and delete from all map that was
			this.edegMap.get(Keys).remove(key);
		
		}
		this.edegCount-=edegs; //update the edegCount
		this.ModeCount++; //update the ModeCount
		return nodeR;
	}

	/**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     */
	@Override
	public void removeEdge(int node1, int node2) {
		if(hasEdge(node1,node2)) {
			edegMap.get(node1).remove(node2);
			edegMap.get(node2).remove(node1);
			this.edegCount--;
			this.ModeCount++;
		}
		
	}
	
	//this to your's test if want to equals two Object
	@Override
    public boolean equals(Object o) { //here we equals between two object from time graph and here we need to see that also we have same nodes,edges,ModeCount
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return ModeCount == wGraph_ds.ModeCount &&     // and here we chack the parameters
                edegCount == wGraph_ds.edegCount &&
                this.nodeSize() == wGraph_ds.nodeSize() &&
                isNodesEquals(wGraph_ds.nodes) &&
                isEdgesEquals(wGraph_ds);
    }

    private boolean isEdgesEquals(WGraph_DS wGraph_ds) {  //we got wGraph_ds we chack with the all key and the keys that connected with him
        for (int key1 : this.edegMap.keySet()){
            HashMap<Integer, Double> currentEdgeMap = this.edegMap.get(key1);
            for (int key2 : currentEdgeMap.keySet()){
                Double weight = currentEdgeMap.get(key2);
                double edgeWeightInTheSecondGraph = wGraph_ds.getEdge(key1, key2);
                if (weight != edgeWeightInTheSecondGraph){ //once the weights are different we will get a negative answer
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isNodesEquals(HashMap<Integer, node_info> nodes){//here we chack the nodes
        for (int key : nodes.keySet()){
            if (!this.nodes.containsKey(key)){ //if this key not found return false
                return false;
            }
        }

        return true;
    }

    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
	@Override
	public int nodeSize() {
		return nodes.size();
	}

	/**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
	@Override
	public int edgeSize() {
		return this.edegCount;
	}

	/**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
	@Override
	public int getMC() {
		return this.ModeCount;
	}
	
	
	private class NodeInfo implements node_info{ 
		private int key;      //the key
	    private String info; //the info
	    private double tag;  //the value of edge
	   
		public NodeInfo(int key) {
			this.key=key;
		}
	    
		/**
	     * Return the key (id) associated with this node.
	     * Note: each node_data should have a unique key.
	     * @return
	     */
	    @Override
		public int getKey() {	
	    	return this.key;
		}
	    
	    /**
	     * return the remark (meta data) associated with this node.
	     * @return
	     */
		@Override
		public String getInfo() {
			return this.info;
		}

		/**
	     * Allows changing the remark (meta data) associated with this node.
	     * @param s
	     */
		@Override
		public void setInfo(String s) {
			this.info = s;
		}

		/**
	     * Temporal data (aka distance, color, or state)
	     * which can be used be algorithms
	     * @return
	     */
		@Override
		public double getTag() {
			return this.tag;
		}

		/**
	     * Allow setting the "tag" value for temporal marking an node - common
	     * practice for marking by algorithms.
	     * @param t - the new value of the tag
	     */

		@Override
		public void setTag(double t) {
			this.tag=t;
			
		}
		 
	}
	
}
