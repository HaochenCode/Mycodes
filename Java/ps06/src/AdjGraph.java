// C343 Summer 2022
//
// a simple implementation for graphs with adjacency lists

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
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

    // every visited node:
    private Vector<Boolean> visited;

    // list of nodes pre-visit:
    private Vector<Integer> nodeEnum;

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
            visited.add(false);
            totalNodes ++;
        }
    }

    // add a vertex:
    public void addVertex(String label) {
        nodeList.add(label);
        visited.add(false);
        adjList.add(new LinkedList<Integer>());
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


    public void componentsAndSizes(){
        ArrayList<Integer> component = new ArrayList<>();
        ArrayList<Integer> componentSize = new ArrayList<>();
        ArrayList<Integer> indexOfVertexes = new ArrayList<>();
        int count = 0;
        for(int i = 0; i < nodeList.size();i++){
            indexOfVertexes.add(i);
        }

        for(int j = 0 ; j < visited.size(); j+= count){
            if(visited.get(j) == false){
                DFS(indexOfVertexes.get(j));
            }

            count = 0;
            for(int i = 0 ; i < visited.size();i++){
                if(visited.get(i) == true){
                    count++;
                    //indexOfVertexes.remove(i);
                }
            }
            component.add(0);
            componentSize.add(count);
            clearWalk();
        }

        System.out.println("total components: " + component.size());
        for(int i = 0; i < component.size();i++){
            System.out.println("component " + i + "contains " + componentSize.get(i) + "nodes");
        }
    }



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

    public void floydSimple() {
        // set all counters to 0 where we know they are 0:
        int[][] NofE = new int[nodeList.size()][nodeList.size()];
        for(int i = 0; i < totalNodes;i++){
            for(int j = 0; j < totalNodes; j++){
                NofE[i][j] =Integer.MAX_VALUE;
            }
        }
        for (int i = 1; i < nodeList.size(); i++){
            NofE[i][i] = 0;
        }
        for(int i = 0; i < totalNodes; i++) {
            LinkedList<Integer> llist = adjList.get(i);
            while(!llist.isEmpty()){
                int u = llist.poll();
                if(llist.isEmpty())
                    break;
                int v = llist.peek();
                NofE[u][v] = 1;
            }

        }
        for (int k = 1; k< totalNodes; k++) {
            for (int i = 1; i< totalNodes; i++){
                for(int j = 1; j< totalNodes; j++){
                    // "relaxation" step:
                    if ((NofE[i][k] != Integer.MAX_VALUE)
                        &&(NofE[k][j] != Integer.MAX_VALUE)
                        &&(NofE[i][j] > (NofE[i][k] + NofE[k][j]))){
                        NofE[i][j] = NofE[i][k] + NofE[k][j];
                    }
                }
            }
        }
        for(int i = 0; i < totalNodes;i++){
            for(int j = 0; j < totalNodes;j++){
                System.out.print(NofE[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Problem Set 06 Task D TODO:

    // --- write a main() method here ---

    // Provide 3 different graph examples,
    //   with at least 10 nodes for each different graph,
    //   following these steps 1) ... 4) for each graph:

    // 1) instantiate a new graph,
    // 2) assign2 vertices and edges to the graph,
    // 3) then display2 the graph's content (use display() )
    // 4) finally call your componentsAndSizes() method to provide
    //    output results as from Problem Set 06 Task D instructions


    public static void main(String[] args) {
        AdjGraph g1 = new AdjGraph(false);
        String[] v1 = {"A", "B","C","D","E","F","G","H","I", "J"};
        g1.setVertices(v1);
        g1.addVertex("K");
        g1.addVertex("L");
        g1.setEdge("A", "B",1);
        g1.setEdge("B", "C",1);
        g1.display();
        g1.componentsAndSizes();

        AdjGraph g2 = new AdjGraph(false);
        String[] v2 = {"1", "2","3","4","5","6","7","8","9", "10"};
        g2.setVertices(v2);
        g2.addVertex("11");
        g2.addVertex("12");
        g2.setEdge("1", "3",1);
        g2.setEdge("2", "4",1);
        g2.display();
        g2.componentsAndSizes();

        AdjGraph g3 = new AdjGraph(false);
        String[] v3 = {"z", "w","y","t","m","q","p","x","o", "s"};
        g3.setVertices(v3);
        g3.addVertex("n");
        g3.addVertex("v");
        g3.setEdge(1, 0,1);
        g3.setEdge(1, 3,1);
        g3.setEdge(3, 5,1);
        g3.display();
        g3.componentsAndSizes();

        AdjGraph g4 = new AdjGraph(false);
        String[] v4 = {"z", "w","y","t","m","k"};
        g4.setVertices(v4);
        g4.setEdge(1, 0,1);
        g4.setEdge(1, 3,1);
        g4.setEdge(3, 4,1);
        g4.setEdge(2, 4,1);
        g4.setEdge(2, 3,1);
        g4.setEdge(1, 4,1);
        g4.setEdge(1, 5,1);
        g4.floydSimple();

        System.out.println();
        AdjGraph g5 = new AdjGraph(false);
        String[] v5 = {"1", "2","3","4","6","5"};
        g5.setVertices(v4);
        g5.setEdge(3, 4,1);
        g5.setEdge(2, 4,1);
        g5.setEdge(2, 3,1);
        g5.setEdge(5, 0,1);
        g5.setEdge(5, 3,1);
        g5.setEdge(1, 4,1);
        g5.setEdge(1, 5,1);
        g5.floydSimple();
        System.out.println();

        System.out.println();
        AdjGraph g6 = new AdjGraph(false);
        String[] v6 = {"11", "21","13","14","61","15"};
        g6.setVertices(v6);
        g6.setEdge(3, 4,1);
        g6.setEdge(2, 5,1);
        g6.setEdge(2, 5,1);
        g6.setEdge(5, 0,1);
        g6.setEdge(5, 3,1);
        g6.setEdge(1, 4,1);
        g6.setEdge(1, 5,1);
        g6.floydSimple();
        System.out.println();
    }
} // end of class AdjGraph

