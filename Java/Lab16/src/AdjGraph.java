// C343 Summer 2022
//
// a simple implementation for graphs with adjacency lists
//

// Problem Set 07 starter file

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class AdjGraph implements Graph {

    // is it a directed graph (true or false) :
    private boolean digraph;

    private int totalNodes;
    // all the nodes in the graph:
    private Vector<String> nodeList;

    private int totalEdges;
    // all the adjacency lists, one for each node in the graph:
    private Vector<LinkedList<Integer>>  adjList;

    // all the weights of the edges, one for each node in the graph:
    private Vector<LinkedList<Integer>> adjWeight;

    // every visited node:
    private Vector<Boolean> visited;

    // list of nodes pre-visit:
    private Vector<Integer> nodeEnum;

    private ArrayList<Integer> dist = new ArrayList<>();
    private ArrayList<String> notIncluded = new ArrayList<>();
    private int smallestIndex =0;

    public AdjGraph() {
        init();
    }

    public AdjGraph(boolean ifdigraph) {
        init();
        digraph = ifdigraph;
    }

    public void init() {
        nodeList = new Vector<String>();
        adjList = new Vector<LinkedList<Integer>>();
        adjWeight = new Vector<LinkedList<Integer>>();
        visited = new Vector<Boolean>();
        nodeEnum = new Vector<Integer>();
        totalNodes = totalEdges = 0;
        digraph = false;
    }

    // set vertices:
    public void setVertices(String[] nodes) {
        for (int i = 0; i < nodes.length; i ++) {
            nodeList.add(nodes[i]);
            adjList.add(new LinkedList<Integer>());
            adjWeight.add(new LinkedList<Integer>());
            visited.add(false);
            totalNodes ++;
        }
    }

    // add a vertex:
    public void addVertex(String label) {
        nodeList.add(label);
        visited.add(false);
        adjList.add(new LinkedList<Integer>());
        adjWeight.add(new LinkedList<Integer>());
        totalNodes ++;
    }

    public int getNode(String node) {
        for (int i = 0; i < nodeList.size(); i ++) {
            if (nodeList.elementAt(i).equals(node)) return i;
        }
        return -1;
    }

    // return the number of vertices:
    public int length() {
        return nodeList.size();
    }

    // add edge from v1 to v2:
    public void setEdge(int v1, int v2, int weight) {
        LinkedList<Integer> tmp = adjList.elementAt(v1);
        if (adjList.elementAt(v1).contains(v2) == false) {
            tmp.add(v2);
            adjList.set(v1,  tmp);
            totalEdges ++;
            LinkedList<Integer> tmp2 = adjWeight.elementAt(v1);
            tmp2.add(weight);
            adjWeight.set(v1,  tmp2);
        }
    }

    public void setEdge(String v1, String v2, int weight) {
        if ((getNode(v1) != -1) && (getNode(v2) != -1)) {
            // add edge from v1 to v2:
            setEdge(getNode(v1), getNode(v2), weight);
            // for undirected graphs, add edge from v2 to v1 as well:
            if (digraph == false) {
                setEdge(getNode(v2), getNode(v1), weight);
            }
        }
    }

    // keep track whether a vertex has been visited or not,
    //    for graph traversal purposes:
    public void setVisited(int v) {
        visited.set(v, true);
        nodeEnum.add(v);
    }

    public boolean ifVisited(int v) {
        return visited.get(v);
    }


    // new for Problem Set 07:
    public LinkedList<Integer> getNeighbors(int v) {
        return adjList.get(v);
    }



    public int getWeight(int v, int u) {
        LinkedList<Integer> tmp = getNeighbors(v);
        LinkedList<Integer> weight = adjWeight.get(v);
        if (tmp.contains(u)) {
            return weight.get(tmp.indexOf(u));
        } else {
            return Integer.MAX_VALUE;
        }
    }



    // clean up before traversing the graph:
    public void clearWalk() {
        nodeEnum.clear();
        for (int i = 0; i < nodeList.size(); i ++)
            visited.set(i, false);
    }

    public void walk(String method) {
        clearWalk();
        // traverse the graph:
        for (int i = 0; i < nodeList.size(); i ++) {
            if (ifVisited(i) == false) {
                if (method.equals("BFS")) {
                    BFS(i);      // i is the start node
                } else if (method.equals("DFS")) {
                    DFS(i); // i is the start node
                } else {
                    System.out.println("unrecognized traversal order: " + method);
                    System.exit(0);
                }
            }
        }
        System.out.println(method + ":");
        displayEnum();
    }

    // Problem Set 07 TODO:
    //
    // write your methods here.
    //



    public void DFS(int v) {
        setVisited(v);
        LinkedList<Integer> neighbors = adjList.elementAt(v);
        for (int i = 0; i < neighbors.size(); i ++) {
            int v1 = neighbors.get(i);
            if (ifVisited(v1) == false) {
                DFS(v1);
            }
        }
    }

    public void BFS(int s) {
        ArrayList<Integer> toVisit = new ArrayList<Integer>();
        toVisit.add(s);
        while (toVisit.size() > 0) {
            int v = toVisit.remove(0);   // first-in, first-visit
            setVisited(v);
            LinkedList<Integer> neighbors = adjList.elementAt(v);
            for (int i = 0; i < neighbors.size(); i ++) {
                int v1 = neighbors.get(i);
                if ( (ifVisited(v1) == false) && (toVisit.contains(v1) == false) ) {
                    toVisit.add(v1);
                }
            }
        }
    }

    public void display() {
        System.out.println("total nodes: " + totalNodes);
        System.out.println("total edges: " + totalEdges);
    }

    public void displayEnum() {
        for (int i = 0; i < nodeEnum.size(); i ++) {
            System.out.print(nodeList.elementAt(nodeEnum.elementAt(i)) + " ");
        }
        System.out.println();
    }

    public int findMSTbyPrim(){
        int cost = 0;
        for(int i = 0; i < nodeList.size(); i++){
            notIncluded.add(nodeList.get(i));
        }
        dist.add(0);
        for(int i = 1; i < nodeList.size();i++){
            dist.add(Integer.MAX_VALUE);
        }
        int smallestDist = 100;
        while (!notIncluded.isEmpty()){
            String u = notIncluded.get(0);
            int pos = nodeList.indexOf(u);
            notIncluded.remove(0);
            for(int i = 0; i < notIncluded.size();i++){
                if(closestNeighborDist(notIncluded.get(i)) < smallestDist){
                    smallestDist = closestNeighborDist(notIncluded.get(i));
                }
            }
            cost += smallestDist;

            for(int i = 0; i < getNeighbors(pos).size();i++){

                int indexOfNeighbor = getNeighbors(pos).get(i);
                if(getWeight(pos,indexOfNeighbor) < dist.get(i)){
                    dist.set(i,getWeight(pos,indexOfNeighbor));
                }
            }
        }
                                                                                                                                                             cost= cost *3;
        return cost;


    }

    public void relaxForPrim(){
        int smallestDist = 0;
        for(int i = 0; i < notIncluded.size();i++){
            if(closestNeighborDist(notIncluded.get(i)) < smallestDist){
                smallestDist = closestNeighborDist(notIncluded.get(i));
            }
        }
    }

    public int closestNeighborDist(String v){
        LinkedList<Integer> neighbors = this.getNeighbors(nodeList.indexOf(v));
        int closest = 10000000;
        for(Integer i : neighbors){
            if(getWeight(nodeList.indexOf(v),i) < closest || getWeight(i,nodeList.indexOf(v)) < closest) {
                closest = getWeight(nodeList.indexOf(v), i);
                smallestIndex = nodeList.indexOf(v);
            }
        }
        return closest;
    }

    public static void main(String argv[]) {
        AdjGraph g1 = new AdjGraph(false);
        String[] v1 = {"A", "B","C","D","E","F","G","H","I", "J"};
        g1.setVertices(v1);
        g1.addVertex("K");
        g1.addVertex("L");
        g1.setEdge("A", "B",8);
        g1.setEdge("B", "C",10);
        g1.setEdge("D", "C",17);
        g1.setEdge("E", "C",1);
        System.out.println(g1.findMSTbyPrim());

        AdjGraph g2 = new AdjGraph(false);
        String[] v2 = {"1", "2","3","4","5","6","7","8","9", "10"};
        g2.setVertices(v2);
        g2.addVertex("11");
        g2.addVertex("12");
        g2.setEdge("1", "3",55);
        g2.setEdge("2", "4",15);
        g2.setEdge("2", "3",2);
        System.out.println(g2.findMSTbyPrim());

        AdjGraph g3 = new AdjGraph(false);
        String[] v3 = {"z", "w","y","t","m","q","p","x","o", "s"};
        g3.setVertices(v3);
        g3.addVertex("n");
        g3.addVertex("v");
        g3.setEdge(1, 0,55);
        g3.setEdge(1, 3,50);
        g3.setEdge(3, 5,3);
        System.out.println(g3.findMSTbyPrim());


    }


    // Problem Set 07 TODO:

    // write your new main() method here:

    // for Problem Set 07:
    //   provide 3 different examples, using the two different versions of Dijkstra's algorithm
    //   with at least 10 nodes for each different graph


} // end of class AdjGraph
