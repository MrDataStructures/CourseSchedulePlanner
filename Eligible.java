package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {

    public static void DFS(String prereq, ArrayList<String> alreadyTaken, HashMap<String, ArrayList<String>> adjList){
        if(!alreadyTaken.contains(prereq)){
            alreadyTaken.add(prereq);
            for(String taken:adjList.get(prereq)){
                DFS(taken, alreadyTaken, adjList);
            }
        }
    }

    public static ArrayList<String> alreadyTaken(String fileName, HashMap<String, ArrayList<String>> adjacencyList){
        ArrayList<String> alreadyTaken = new ArrayList<String>();
        StdIn.setFile(fileName);
        int classesCount = StdIn.readInt();
        for(int i=0;i<classesCount;i++){
            String course = StdIn.readString();
            alreadyTaken.add(course);    // for each prereq for course, do a recursice call on it that will add prereq and call that same method on **its** prereq. add it to the arraylist as long as it has not already been added
            for(String prereq:adjacencyList.get(course)){
                DFS(prereq, alreadyTaken, adjacencyList);
            }
        }
        return alreadyTaken;
    }
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
        // WRITE YOUR CODE HERE
        StdIn.setFile(args[0]);
        AdjList myList = new AdjList();
        HashMap<String, ArrayList<String>> adjacencyList = myList.adjacencyList;
        ArrayList<String> alreadyTaken = alreadyTaken(args[1], adjacencyList);
        StdOut.setFile(args[2]);
        ArrayList<String> coursesNotTaken = new ArrayList<String>();
        for(String classroom:adjacencyList.keySet()){
            if(!alreadyTaken.contains(classroom)){
                coursesNotTaken.add(classroom);
            }
        }
        ArrayList<String> eligible = new ArrayList<String>();
        for(String courses:coursesNotTaken){
            boolean ableToTake = true;
            for(String preReq:adjacencyList.get(courses)){
                if(!alreadyTaken.contains(preReq)){
                    ableToTake = false;
                }
            }
            if(ableToTake==true){
                eligible.add(courses);
            }
        }
        for(String canTake : eligible){
            StdOut.println(canTake);
        }
    }
}   

