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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {

    private static boolean checkCycle(String course, HashMap<String, ArrayList<String>> coursesADJ, HashMap<String, Boolean> visitList, HashMap<String, Boolean> masterVisList){
        visitList.put(course, true);
        masterVisList.put(course, true);
        for(String preReqs:coursesADJ.get(course)){
            if(masterVisList.get(preReqs)==false){
                if(checkCycle(preReqs, coursesADJ, visitList, masterVisList)==true){
                    return true;
                }
            }else if(visitList.get(preReqs)==true){
                return true;
            }
        }
        visitList.put(course, false);
        return false;
    }

    public static boolean isCyclic(String classroom, HashMap<String, ArrayList<String>> classADJ){
        HashMap<String, Boolean> visitList = new HashMap<String, Boolean>();
        HashMap<String, Boolean> masterVisList = new HashMap<String, Boolean>();
        for(String room:classADJ.keySet()){
            visitList.put(room, false);
            masterVisList.put(room,false);
        }
        for(String clas:visitList.keySet()){
            if(masterVisList.get(clas)==false){
                if(checkCycle(clas,classADJ,visitList,masterVisList)==true)return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput -file> <valid prereq OUTput file>");
            return;
        }
        // WRITE YOUR CODE HERE
        StdIn.setFile(args[0]);
        AdjList coursesADJ = new AdjList();
        StdIn.setFile(args[1]);
        String course1 = StdIn.readString();
        String course2 = StdIn.readString();
        coursesADJ.adjacencyList.get(course1).add(0, course2);
        StdOut.setFile(args[2]);
        boolean rapper = isCyclic(course1, coursesADJ.adjacencyList);
        if(rapper==false){
            StdOut.print("YES");
        }else{
            StdOut.print("NO");
        }

    }
    
}
