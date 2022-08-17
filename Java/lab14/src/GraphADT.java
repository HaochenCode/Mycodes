public interface GraphADT {
    public void init();
    public int length();
    public void setVertices(String[] nodes);
    public void addVertex(String node);
    public void setEdge(int v1, int v2, int weight);
    public void setEdge(String v1, String v2, int weight);
    public void displayInfo();  // print basic info about graph
    public void displayGraph(); // print out adjacency array
}