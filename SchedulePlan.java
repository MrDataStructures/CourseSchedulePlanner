package prereqchecker;

import java.util.*;

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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {

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
        StdIn.readString();
        int classesCount = StdIn.readInt();
        for(int i=0;i<classesCount;i++){
            String course = StdIn.readString();
            alreadyTaken.add(course);    
            for(String prereq:adjacencyList.get(course)){
                DFS(prereq, alreadyTaken, adjacencyList);
            }
        }
        return alreadyTaken;
    }

    public static void newDFS(String prereq, ArrayList<String> taken, ArrayList<String> needTake, HashMap<String, ArrayList<String>> adjList){
        if(!taken.contains(prereq)){
            if(!needTake.contains(prereq)) needTake.add(prereq);
            for(String prereqs:adjList.get(prereq)){
                newDFS(prereqs, taken, needTake, adjList);
            }
        }
    }

    public static ArrayList<String> haveToTake(HashMap<String, ArrayList<String>> adjList, String filename, ArrayList<String> taken){
        StdIn.setFile(filename);
        String target = StdIn.readString();
        ArrayList<String> needTake = new ArrayList<String>();
        for(String course: adjList.get(target)){
            if(!alreadyTaken(filename, adjList).contains(course)){
                if(!needTake.contains(course)) needTake.add(course);
                for(String prereq:adjList.get(course)){
                    newDFS(prereq, taken, needTake, adjList);
                }
            }
        }
        return needTake;
    }


    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }
        // WRITE YOUR CODE HERE
        StdIn.setFile(args[0]);
        StdOut.setFile(args[2]);
        AdjList myList = new AdjList();
        HashMap<String, ArrayList<String>> adjacencyList = myList.adjacencyList;
        ArrayList<String> tookAlready = alreadyTaken(args[1], adjacencyList);
        ArrayList<String> needToTake = haveToTake(adjacencyList, args[1], tookAlready);
        for(String bob: needToTake){
            StdOut.println(bob);
        }
    }
}
