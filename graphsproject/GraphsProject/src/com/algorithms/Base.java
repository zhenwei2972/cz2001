package com.algorithms;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Base {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("test"); 
		System.setProperty("org.graphstream.ui", "swing");
		//new Base();
		RandomGraph();
	   // BFS(graph,0,L);
		
	    
	}
	  public Base() {
		   Graph graph = new SingleGraph("tutorial 1");

           graph.setAttribute("ui.stylesheet", styleSheet);
           graph.setAutoCreate(true);
           graph.setStrict(false);
           graph.display();

           graph.addEdge("AB", "A", "B");
           graph.addEdge("BC", "B", "C");
           graph.addEdge("CA", "C", "A");
           graph.addEdge("AD", "A", "D");
           graph.addEdge("DE", "D", "E");
           graph.addEdge("DF", "D", "F");
           graph.addEdge("EF", "E", "F");

           for (Node node : graph) {
               node.setAttribute("ui.label", node.getId());
           }

           explore(graph.getNode("A"));
	        
	    }
	public static void explore(Node source) {
        Iterator<? extends Node> k = source.getBreadthFirstIterator();

        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");
         //   System.out.println(next.getId());
            sleep();   
        }
    }
	protected static void sleep() {
		try { Thread.sleep(1000); } catch (Exception e) {}
	}
	 protected static String styleSheet =
		        "node {" +
		        "	fill-color: black;" +
		        "}" +
		        "node.marked {" +
		        "	fill-color: red;" +
		        "}";

	 
	public static void RandomGraph() {
		Graph graph = new SingleGraph("Random");
	    Generator gen = new RandomGenerator(2);
	    graph.setAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.display();
	    gen.addSink(graph);
	    gen.begin();
	    for(int i=0; i<100; i++)
	        gen.nextEvents();
	    gen.end();
	    graph.display();
	    Queue<Integer> L = new LinkedList<Integer>();
		// have to check what random graph attributes are 
	    for (Node node : graph) {
            node.setAttribute("ui.label", node.getId());
        }

        explore(graph.getNode("0"));
	}
	public static void BFS(Graph G,int S, Queue<Integer> L)
	{
		
		boolean visited[] = new boolean[G.getNodeCount()];
		//mark visited node and add to queue
		visited[S]=true;
		L.add(S);
		int i=0;
		while(L.size()!=0)
		{
			int curr =0;
			//remove front element from queue save to S & print
			curr = L.poll();
			System.out.print("visited "+curr);
			Node currentNode = G.getNode(curr);
			Edge outgoingEdges =currentNode.getEdge(i);
			i++;
			Node nextNode =outgoingEdges.getOpposite(currentNode);
			
			
			System.out.println("nextNode is"+ nextNode.getId());
			
		}
		
	}

}
