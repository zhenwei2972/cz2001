package com.algorithms;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class secondShortestPath {
	static final int NO_PARENT = -1;	
	static Set<Integer> edges = new LinkedHashSet<>(); //nodes in the shortest path
	static Set<Integer> dists = new TreeSet<>(); //list of distances of paths from src to dest, sorted 
	
	//use Dijkstra’s Shortest Path Algorithm, O(n^2) Space O(n)
    static void shortestPath(int[][] adjacencyMatrix,  int src, int dest) { 
        int n = adjacencyMatrix[0].length; 
        int[] shortest = new int[n]; 
        boolean[] added = new boolean[n]; 
        for (int v = 0; v < n;v++)  { 
            shortest[v] = Integer.MAX_VALUE; 
            added[v] = false; 
        } 
        shortest[src] = 0; 
        int[] parents = new int[n]; 
        parents[src] = NO_PARENT; 

        for (int i = 1; i < n; i++)  { 
            int v1 = -1; //store temp data
            int min = Integer.MAX_VALUE; 
            for (int v = 0;  v < n;  v++) { 
                if (!added[v] &&  shortest[v] < min) { 
                    v1 = v; 
                    min = shortest[v]; 
                } 
            } 
            added[v1] = true; 
            for (int v = 0; v < n; v++)  { 
                int dist = adjacencyMatrix[v1][v];                  
                if (dist > 0 && ((min + dist) <shortest[v])){ 
                    parents[v] = v1; 
                    shortest[v] = min + dist; 
                } 
            } 
        }  
        dists.add(shortest[dest]);
        visitUtil(dest, parents); 
    } 
   
    static void visitUtil(int i,int[] parents)  { 	
        if (i == NO_PARENT)        	
            return; 
   	
        visitUtil(parents[i], parents);             
        edges.add(i);
        System.out.println(edges);
    } 

    public static void main(String[] args) { 
		/*
		 *     0
		 *  10/ \3
		 *   /   \
		 *  1--1--4
		 * 5|  8/ |2
		 *  | /   |
		 *  2--7--3
		 */   	
    	//the value of the node to itself is 0, 
    	//if not direction connection, value is 0 
        int[][] adjacencyMatrix = new int[][] {
            { 0,10, 0, 0, 3 },
            {10, 0, 5, 0, 1 },
            { 0, 5, 0, 7, 8 },
            { 0, 0, 7, 0, 2 },
            { 3, 1, 8, 2, 0 }
        };
        
        //get shortest path
        int src = 1, dest = 4;
        shortestPath(adjacencyMatrix,src,dest); 

        //get 2nd shortest by removing each edge in shortest and compare  
        int tmp = -1, s1 = -1, d1 = -1; //store temp data
        List<Integer> list = new ArrayList<Integer>(edges);        
        for (int i = 0; i < list.size()-1 ; i++) {
        	int s = list.get(i); //start node(src) per edge
        	int d = list.get(i + 1); //end node(dest) per edge
        	//System.out.println("s: " + s);
        	//System.out.println("d: " + d);
        	if (tmp != -1) {
        		adjacencyMatrix[s1][d1] = tmp;
        		adjacencyMatrix[d1][s1] = tmp;
        	}
        	tmp = adjacencyMatrix[s][d]; //print the cost
        	System.out.println(tmp);
        	s1 = s;
        	d1 = d;
	        adjacencyMatrix[s][d] = 0;
	        adjacencyMatrix[d][s] = 0;
	        shortestPath(adjacencyMatrix, src , dest);
        }
        
        list = new ArrayList<Integer>(dists); 
        System.out.println("Shortest:" + list.get(0));
        System.out.println("2nd shortest:" + list.get(1));       
    } 
}
