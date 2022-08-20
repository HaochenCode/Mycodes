import java.util.LinkedList;
import java.util.Vector;

public class Lab14Graph implements GraphADT {
    private int totalNodes;
    private int totalEdges;
    private Vector<String> nodeList;


    // TODO:
    // implement the graph using adjacency lists:
    private Vector<LinkedList<String>>  adjList;

    // constructor
    public Lab14Graph() {
        init();
    }


    @Override
    public void init() {
        adjList = new Vector<>();
        nodeList = new Vector<>();
    }

    @Override
    public int length() {
        return adjList.size();
    }

    @Override
    public void setVertices(String[] nodes) {
        for(String i : nodes){
            nodeList.add(i);
            adjList.add(new LinkedList<String>());
        }
        totalNodes += nodes.length;

    }

    @Override
    public void addVertex(String node) {
        nodeList.add(node);
        totalNodes++;
    }

    @Override
    public void setEdge(int v1, int v2, int weight) {

    }

    @Override
    public void setEdge(String v1, String v2, int weight) {

        if(!nodeList.contains(v1) || !nodeList.contains(v2)){
            System.out.println("Vertex not found!");
        }
        else{
            int pos = nodeList.indexOf(v1);
            adjList.get(pos).add(v2);
            int pos2 = nodeList.indexOf(v2);
            adjList.get(pos2).add(v1);
            totalEdges++;
        }

    }

    @Override
    public void displayInfo() {
        System.out.println("Total vertex : " + totalNodes);
        System.out.println("Total Edges : " + totalEdges);
        // Display all vertices
        System.out.println("All the nodes are : ");
        for(String str : nodeList){
            System.out.print(str + " ");
        }
    }

    @Override
    public void displayGraph() {
        System.out.println();
        for (int i = 0; i < nodeList.size(); i++) {
            System.out.print("Vertex " + nodeList.get(i) + ":");
            for (int j = 0; j < adjList.get(i).size(); j++) {
                System.out.print(" -> " + adjList.get(i).get(j));
            }
            System.out.println();

        }
    }



    public static void main(String argv[]) {
        // Test 1
        Lab14Graph g = new Lab14Graph();
        String[] s = {"V", "W", "X", "Y", "Z"};
        g.setVertices(s);
        g.setEdge("W",  "V", 1);
        g.setEdge("W",  "X", 10);
        g.displayInfo();
        g.displayGraph();
        System.out.println();
        // Test 2
        Lab14Graph g2 = new Lab14Graph();
        String[] s2 = {"A", "B", "E", "Y", "Z"};
        g2.setVertices(s2);
        g2.setEdge("B",  "A", 1);
        g2.setEdge("Y",  "Z", 10);
        g2.setEdge("Y",  "B", 10);
        g2.displayInfo();
        g2.displayGraph();
        System.out.println();

        // Test 3
        Lab14Graph g3 = new Lab14Graph();
        String[] s3 = {"0", "1", "2", "3", "5"};
        g3.setVertices(s3);
        g3.setEdge("1",  "3", 1);
        g3.setEdge("3",  "2", 10);
        g3.setEdge("2",  "5", 10);
        g3.displayInfo();
        g3.displayGraph();
        System.out.println();

        // Test 4
        Lab14Graph g4 = new Lab14Graph();
        String[] s4 = {"E", "Y", "T", "R", "Q"};
        g4.setVertices(s4);
        g4.setEdge("Y",  "E", 1);
        g4.setEdge("Y",  "Q", 10);
        g4.setEdge("Q",  "T", 10);
        g4.setEdge("R",  "Y", 10);
        g4.displayInfo();
        g4.displayGraph();
        System.out.println();

        // Test 5
        Lab14Graph g5 = new Lab14Graph();
        String[] s5 = {"c", "v", "b", "n", "m","o"};
        g5.setVertices(s5);
        g5.setEdge("b",  "n", 1);
        g5.setEdge("c",  "v", 10);
        g5.setEdge("v",  "n", 10);
        g5.setEdge("n",  "o", 10);
        g5.displayInfo();
        g5.displayGraph();
        System.out.println();
    }


}