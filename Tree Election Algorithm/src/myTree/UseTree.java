package myTree;

import org.junit.Test;
import java.util.Scanner;
import myTree.GenTree;

public class UseTree {

	@Test //allows the public void method to run as a test case
	public void runTree() {
		
		Scanner reader = new Scanner(System.in);
		
		System.out.println("Enter number of nodes (Minimum allowed = 7): ");
		
		int input = reader.nextInt(); //user input for the number of nodes
		
		while (input < 7) //minimum input specified by the assignment 
		{
			System.out.println("Minimum ammount of nodes 7\r\n");
			System.out.println("Enter a new number of nodes: ");
			input = reader.nextInt();			
		} //controlling user input
		
		final int nodes = input;
		
		System.out.println("You have inserted "+nodes+" nodes\r\n");
		
		GenTree testTree = new GenTree(nodes); //generate object constructor 

		System.out.println("Choose a type of Tree you want to be created \n");
		System.out.println("(Type 1 for Arbitrary Tree, 2 for Balanced Tree and 3 for Unbalanced Tree\n");
		
		Scanner treeReader = new Scanner(System.in);
		
		int treeInput = treeReader.nextInt(); //user input - type of tree
		
		while (treeInput < 1 || treeInput > 3) //controlling user input
		{
			System.out.println("Wrong number inserted\n");
			System.out.println("(Type 1 for Arbitrary Tree, 2 for Balanced Tree and 3 for Unbalanced Tree\n");
			treeInput = treeReader.nextInt();
		}
		treeReader.close(); //closing the scanner
		reader.close();
		if (treeInput == 1) //type of tree selection
		{
			testTree.arbitraryTree();
		}
		else if (treeInput == 2)
		{
			testTree.balancedTree();
		}
		else if (treeInput == 3 )
		{
			testTree.unbalancedTree();
		}
	
		testTree.electionAlgorithm();
	}
}
