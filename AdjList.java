package prereqchecker;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
import java.util.*;
public class AdjList {
    HashMap<String, ArrayList<String>> adjacencyList;
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

	// WRITE YOUR CODE HERE
    StdIn.setFile(args[0]);
    StdOut.setFile(args[1]);
    AdjList finalList = new AdjList();
    finalList.outputList();
    }

    public AdjList(){
        this.adjacencyList =  new HashMap<String, ArrayList<String>>();
        populateKeys();
        populateValues();
    }

    public void populateKeys(){
        int countCourses = StdIn.readInt();
        for(int i=0;i<countCourses;i++){
            adjacencyList.put(StdIn.readString(), new ArrayList<String>());
        } 
    }

    public void populateValues(){
        int preReqs = StdIn.readInt();
        for(int i=0;i<preReqs;i++){
            adjacencyList.get(StdIn.readString()).add(0, StdIn.readString());
        } 
    }

    public void outputList(){
        for(String course : adjacencyList.keySet()){
            StdOut.print(course);
            for(String preReqs : adjacencyList.get(course)){
                StdOut.print(" " + preReqs);
            }
            StdOut.println();
        }
        
    }
}
