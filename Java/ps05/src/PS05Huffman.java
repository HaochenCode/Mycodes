import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class PS05Huffman<Key, E> {
    private KVMinHeap<Integer, String> heap;      // heap for building the tree
    private KVBinaryNode<Integer, String> root;   // root node for traversal
    private HashMap<String, String> codeTable; // huffman letter<->code table
    private Dictionary<String, Integer> codeFreq; // huffman letter<->frequency table

    public PS05Huffman(String letters, int[] weights) {
        // prepare the Min-Heap as explained at lecture time:
        this.initMinHeap(letters, weights);
        // use the Min-Heap to build the Huffman tree, as explained at lecture time:
        this.buildTree();
        this.codeTable = new HashMap<>();
        this.buildCodeTable();
        this.summary();
    }

    private void initMinHeap(String letters, int[] weights) {
        this.codeFreq = new Hashtable<String, Integer>();
        for (int i = 0; i < letters.length(); i ++)
            this.codeFreq.put(letters.substring(i, i + 1), weights[i]);
        int maxNum = letters.length();
        // KVBinaryNode<Integer, String>[] nodes = (KVBinaryNode<Integer, String>[]) new Object[maxNum];
        @SuppressWarnings("unchecked")
        KVBinaryNode<Integer, String>[] nodes = new KVBinaryNode[maxNum];
        for (int i = 0; i < maxNum; i ++) {
            nodes[i] = new KVBinaryNode<Integer, String>(weights[i], letters.substring(i, i + 1));
        }
        this.heap = new KVMinHeap<Integer, String>(maxNum, maxNum, nodes);
        this.heap.display();
    }

    // TODO for Problem Set 05 Task B - Functionality 1: create the Huffman tree.
    //
    private void buildTree() {
        // Problem Set 05 Task B TODO: implement this method!
        //
        // 1. follow the algorithm shown at lecture time,
        //    to build a Huffman tree from the elements found in the starting Min-Heap
        //
        // 2. when the Huffman tree is built,
        //    verify its total weight as explained at lecture time.
        while (heap.length()>=2) {
            heap.display();
            KVBinaryNode small1 = heap.removeMin();
            heap.display();
            KVBinaryNode small2 = heap.removeMin();
            heap.display();
            KVBinaryNode<Integer, String> temp = new KVBinaryNode<>((int) small1.getKey() + (int) small2.getKey(), "");
            root = temp;
            temp.setLeftNode(small1);
            temp.setRightNode(small2);
            heap.insert(root);
            heap.display();
        }

        System.out.println("Huffman tree built. Root weight = " + this.root.getKey());
    }

    public void summary() {
        if (this.codeTable.isEmpty()) {
            System.out.println("Summary can't be provided. The Huffman Code Table is empty!");
            return;
        }
        // display the code & compute the sum of weighted path lengths
        Enumeration<String> keys = this.codeFreq.keys();
        int sumOfWeightedPath = 0;
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println("Letter: " + key + " " + this.codeTable.get(key));
            sumOfWeightedPath += this.codeTable.get(key).length() * this.codeFreq.get(key);
        }
        System.out.println("Total letters: " + this.root.getKey());
        System.out.println("Sum of weighted path lengths: " + sumOfWeightedPath);
        System.out.println("Average code length: " + sumOfWeightedPath * 1.0 / this.root.getKey());
    }

    // TODO for Problem Set 05 Task B - Functionality 2: get the Huffman code by traversing the tree.
    //
    // Each leaf node is a letter, and the corresponding path is the code.
    // For simplicity, represent Huffman codes by using strings of "0" and "1", not bits.
    private void buildCodeTable() {
        // Problem Set 05 Task B TODO: implement this method!
        //
        // hint: if you need to use recursion, it will be easier to:
        //
        //       first, write a separate (recursive) private helper method,
        //          which can call itself because recursive.
        //
        //       then, invoke that recursive method from here.

        if(root == null) return;
        traverse(root,"");


    }

    private void traverse( KVBinaryNode<Integer,String> node, String prefix) {
        // TODO:  Fill in this method
        // base case
        if(node.getValue() != "")
        codeTable.put(node.getValue(), prefix);
        if(node==null) return;
        //else traverse the left then right

        if(node.getLeftNode() != null)
        traverse(node.getLeftNode(), prefix + "0");
        if(node.getRightNode() != null)
        traverse(node.getRightNode(),prefix + "1");

    }

    // TODO for Problem Set 05 Task B - Functionality 3: encode a text string into Huffman coding.
    //
    public String encode(String inStr) {
        String outCode = "";
        //
        // Problem Set 05 Task B TODO: implement this method!
        //
        // 1. encoding should first check if the encoding table is available
        //    and has the necessary content. Otherwise, it should just print
        //    an error message and return the empty string.
        //
        // 2. use the encoding table to create a Huffman encoding
        //    of the string received in the inStr parameter,
        //    and return a string containing the Huffman-encoding.
        //
        for(int i = 0; i < inStr.length();i++){
            if(codeTable.containsKey(String.valueOf(inStr.charAt(i)+"")))
                outCode += codeTable.get(inStr.charAt(i)+"");
            else
                System.out.println("Key not found in the codeTable.");
        }
        return outCode;
    }

        // TODO for Problem Set 05 Task B - Functionality 4: decode a Huffman code into a text string.
    //
    public String decode(String inCode) {
        String outStr = "";
        //
        // Problem Set 05 Task B TODO: implement this method!
        //
        // 1. decoding should first check if the Huffman tree is available
        //    and has the necessary content. Otherwise, it should just print
        //    an error message and return the empty string.
        //
        // 2. decode the string received in the inCode parameter,
        //    using the Huffman tree.
        //

        String str = inCode;
        KVBinaryNode<Integer,String> temp = root;
        for(int i = 0 ; i <inCode.length();i++ ){

                if (inCode.charAt(i) == '0') {
                    temp = temp.getLeftNode();
                }
                else if(inCode.charAt(i) == '1'){
                    temp =temp.getRightNode();
                }
                if(temp.isNodeLeaf()){
                    outStr += temp.getValue();
                    temp = root;
                }

        }

        return outStr;
    }

    public static void main(String[] argv) {
        // given a set of letters and their relative weights:
        String letters = "ZKMCDLUE";
        int[] weights = {2, 7, 24, 32, 37, 42, 42, 120};

        // instantiate the Huffman encoding/decoding class:
        PS05Huffman<Integer, String> tree = new PS05Huffman<Integer, String>(letters, weights);

        //testing:
        System.out.println("DEED" + " encoded as " + tree.encode("DEED"));
        System.out.println("MUCK" + " encoded as " + tree.encode("MUCK"));
        System.out.println("KUCM" + " encoded as " + tree.encode("KUCM"));
        System.out.println("EEDD" + " encoded as " + tree.encode("EEDD"));

        System.out.println("10000100 is decoded as " + tree.decode("10000100"));
        System.out.println("111111101110111101 is decoded as " + tree.decode("111111101110111101"));
        System.out.println("111101110111011111 is decoded as " + tree.decode("111101110111011111"));
        System.out.println("00100100 is decoded as " + tree.decode("00100100"));

    }

} // public class PS05Huffman

