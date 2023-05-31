package prereqchecker;

import java.util.*;

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

