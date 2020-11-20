Ex1 - Dvir Hakmon
--------

In Ex1 we were required to work with an unintentionally weighted graph, and were required to exercise three classes.
The first was to implement the node_info class as an internal class.
Within this class are the properties of the nodes.

Next, we implemented the weighted graph class which is basically all the features we have in the graph, some of the functions are - connecting nodes, whether there is an edge, deleting an edge and more.
The data structure we used to implement our graph is one HashMap that stores all the vertices in the graph and another HashMap to give us a list of all the connections that connect to that specific node, so in the second HashMap we did get an Integer and a list that is basically all the connections it is connected to.
And in fact this is how we realize our graph.

I will move on to the last implementation and it is weighted graph algorithms where we will need to implement 6 functions. In the copy function we create a new graph into which we will put all the nodes and after that again on everything a key, with the help of which we can connect the sides of all the nodes with their neighbors.
I will move to the isConnected function where we used the BFS algorithm, go through each key and its neighbors, mark them with 1, and go over the other nodes and as soon as we have marked 1 with the setTag we will put it in a queue, and do so until the queue is empty. Once we are done we will clear the graph and return it is correct otherwise it will return a negative answer after we stick if there is one node with getTag with the value 0.
We will move to the shortestPathDist function where we will consider the shortest route
In this function we will use the Dijkstra algorithm where we will go over the vertex code we will initialize them with infinity, and the initial value we will initialize with 0 We will use the HashMap data structure to store and update the lowest values ​​we went through until we get to dest, we used the findMinimum On the neighbors officially and when we saw the lowest neighbor we will add it to the list of nodes we passed and of course we will connect the distance with the distance that was before until we reach the dest and as soon as we reach we will stop and return the distance of the dest. We will clear the graph and return the value.

We will move to the function <node_info> shortestPath where we used two data structures which are HashMap
One like the previous function, which each time checks the minimum node, and one in which gets Iinteger and list, start the values ​​of each node to 0 and the list of each to empty, and what we do is each time we check and update whether the connection is smaller or larger than the previous one, The shortest way and we will progress with the help of our list of neighbors, and each time HashMap we add the organ we found we went through and to the second HashMap the organ in which the HashMap maze we add it to the list to return a list do so until the first HashMap it nodesMap is empty
 shortestPathMap We get the list of neighbors of the vertex dest at the end.

I will move to the save function that comes we want to save the data on the graph we have. In the first part we will store the nodes and then the nodes and also the edges of the same 2 nodes, with the help of the StringBuilder structure where we will store what we want, and PrintWriter is basically to produce a new file, close the file, and return true.

I will move to the load function where we actually need to read from the file, use split to get String of the numbers first and then we will go over them and convert them to Integer and insert them and then we will use a structure called String stream and continue reading what we wrote we will use iterator which will go line by line.
And for each line there we do a split and then we insert the two nodes and then their distance in the last place, close the file and return true.
