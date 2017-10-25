package myTree;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class SuperNode {
	
	private final int nodeID; //node id
	private boolean msgRec = false;	//msg reception
	private Integer silentNeighbour = null; //silent neighbour
	private static TreeMap<Integer, SuperNode> myNodes = new TreeMap<Integer, SuperNode>(); //construct nodes
	private TreeMap<Integer,Boolean> mapNeigh = new TreeMap<Integer,Boolean>(); //store nodes ID and message reception
	
	                  
	public SuperNode(int nID, TreeSet<Integer> neighList) //initialise store IDs and neighbours set
	{                	  
		this.nodeID = nID;
		
		for (int i : neighList)
		{
			this.mapNeigh.put(i,false);
		}
		myNodes.put(nodeID, this);
	}
	
	public static void clear()
	{
		myNodes.clear(); //clears all elements
	}
	
	public static SuperNode chooseNode(int i) //return object to its ID
	{
		return myNodes.get(i);
	}
	
	
	public int returnID() //return node ID
	{
		return nodeID;
	}
	
	public TreeMap<Integer,Boolean> returnNeighlist() //protected returns node IDs and neighbours set
	{
		return mapNeigh;
	}
	
	public boolean returnMsg() //message reception
	{
		return msgRec;
	}
	
	protected void msgAck() //message acknowledgement
	{
		msgRec=true;
	}

	public int countSilNeigh() //counts silent neighbours
	{
		if (!mapNeigh.containsValue(false)) //check if there are node IDs with negative message status
		{
			return 0;
		}
		
		int j=0; //number of silent neighbours
		
		for(int i : mapNeigh.keySet()) //calculate the number of silent nodes
		{
			if (!mapNeigh.get(i))
			{
				j++;
			}
		}
		return j; //if 1 then the silent neighbour has been found
	}
	
	protected void setSilentNeigh()
	{
		for (int i : mapNeigh.keySet()) //assigns the value that is false to the silent neighbour
		{
			if (!mapNeigh.get(i))
			{
				silentNeighbour=i;
				break;
				}
		}
	    
	}
	
	public int returnSileNeigh() //returns silent neighbour value
	{
		return silentNeighbour;
	}
	
	public abstract boolean decision(); //calls decide method
	
	
}
