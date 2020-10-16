package com.algorithms;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Base {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("test"); 
		System.setProperty("org.graphstream.ui", "swing");
		Graph graph = new SingleGraph("Random");
	    Generator gen = new RandomGenerator(2);
	    gen.addSink(graph);
	    gen.begin();
	    for(int i=0; i<100; i++)
	        gen.nextEvents();
	    gen.end();
	    graph.display();
	    
	}

}
