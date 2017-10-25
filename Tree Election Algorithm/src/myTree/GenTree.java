package myTree;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import myTree.Election;

import java.util.Random;

public final class GenTree {
	
	private final int nodes; //nodes variable
	private TreeMap<Integer, TreeSet<Integer>> myTree = new TreeMap<Integer, TreeSet<Integer>>();
	//TreeMap is a map interface that assigns key and values naturally sorted
	//TreeSet is a interface that stores objects (elements) to the set 
	private static final Random rnd = new Random(); //random variable to choose nodes
	
	public GenTree(int nodesInit)	//initialise nodes variable
	{
		this.nodes=nodesInit;			
	}
	
	private void printNeighbours() //prints nodes and their neighbours
	{
		for(int i = 0; i < nodes; i++)
		{
			if (i == 0) //root node
			{
				System.out.print("Tree is initialised with Node " + i +" as the root node.\n");
			}
			System.out.println("Node " + i + " has the following neighbours: " + myTree.get(i)+"\n"); 
			//prints all created nodes and their neighbours
		}
	}
	
	public void arbitraryTree() //creates an arbitrary tree
	{
		
		myTree.put(0,new TreeSet<Integer>()); //creates root node
		for(int i = 1; i < nodes; i++)
		  {
			myTree.put(i, new TreeSet<Integer>()); //adds inserted number of nodes to the set
			myTree.get(0).add(i); // assigns all nodes as neighbours to the root	  
			myTree.get(i).add(0); // assigns root node as neighbour to every other node
		  }		
		System.out.println("This is an Arbitrary Tree of "+ nodes +" nodes:\n");
		printNeighbours(); //calls the print method		
	}
	
	public void balancedTree()
	{
		myTree.put(0, new TreeSet<Integer>()); //creates root node
		for(int i = 1; i < nodes; i++)
		{
			myTree.put(i, new TreeSet<Integer>()); // adds the inserted number of nodes to the set
			int balance = Math.abs((i-1)/3); // breaks the nodes into 3 main Children nodes that all the rest nodes derive from  
			myTree.get(i).add(balance); //gets parent node and assigns children nodes
			myTree.get(balance).add(i); // gets leaf nodes and connects them to their neighbours
	    }
		System.out.println("This is a Balanced Binary Tree:\n");	
		printNeighbours(); //calls print method
	}
	
	public void unbalancedTree() //adds some nodes and then adds arbitrary tree style for the rest
	{
		myTree.put(0, new TreeSet<Integer>());
		myTree.put(1, new TreeSet<Integer>());
		myTree.put(2, new TreeSet<Integer>());
		myTree.put(3, new TreeSet<Integer>());
		myTree.put(4, new TreeSet<Integer>());
		myTree.get(0).add(1);
		myTree.get(0).add(2);
		myTree.get(1).add(0);
		myTree.get(2).add(0);
		myTree.get(1).add(3);
		myTree.get(3).add(1);
		myTree.get(3).add(4); 
		myTree.get(4).add(3); //creates the first nodes manually	
		
		for(int i = 5; i < nodes; i++) // and then adds all the rest nodes in a arbitrary style
		  {
			int x = i-2; //splits the remaining nodes into two branches
			myTree.put(i,new TreeSet<Integer>());
			myTree.get(x).add(i);  
			myTree.get(i).add(x);
		  }	

		System.out.println("This is an Unbalanced Binary Tree:\n");
		printNeighbours(); //calls the print method
	}
		
	public void clear() // clears the current tree so a new one can be created later on
	{
		myTree.clear();
	}
	
	public void electionAlgorithm()
	{
		int processDec = 0; //processes that decide
		TreeSet<Integer> leader = new TreeSet<Integer>(); //leader set
	
		System.out.println("The Election Algorithm - Part 2:");
		
		ArrayList<Election> treeElection = new ArrayList<Election>();	//stores nodes		
		
		for (int i = 0; i < nodes; i++)
		{
			treeElection.add(new Election(i, myTree.get(i))); //adds nodes to the list
		}
		
		treeElection.get(rnd.nextInt(nodes)).sendWakeMessage(); //pick random nodes and adds their ID to the wake up message list
		
		for (int i = 0; i < nodes * 100; i++) //do N*100 iterations
		{
			LinkedHashSet<Integer> nodeSet = new LinkedHashSet<Integer>(); //linked list that maintains iteration order
			
			int rndNodes = rnd.nextInt(nodes + 1); //pick random nodes to test the algorithm
			
			while (nodeSet.size() < rndNodes)
			{
				nodeSet.add(rnd.nextInt(nodes)); //choose nodes to execute the algorithm
			}
			for (int x = 0; x < nodeSet.size(); x++) //for the number of generated nodes
			{
			if (treeElection.get(x).decision()) // if decision is made decision is made
			{
				System.out.println("Node "+treeElection.get(x).returnID()+" has reached a decision in the "+ i +" iteration");
				leader.add(treeElection.get(x).returnMinID()); //adds the leader id
				processDec++;
			}
			}
		}		
		System.out.println("Number of processes that get to decide in the algorithm: "+processDec);
		System.out.println("The 'Leader' of the algorithm has the following ID: "+leader+"\n");		
	}
}
