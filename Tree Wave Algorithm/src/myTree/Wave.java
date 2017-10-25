package myTree;


import java.util.TreeSet;



public final class Wave extends SuperNode {
	private TreeSet<Integer> nodeMsg =new TreeSet<Integer>(); //stores messages
	
	public Wave(int nID, TreeSet<Integer> neighList) //invokes constructor from SuperNode class
	{
		super(nID, neighList);
	}
	

	private void receiveMsg() //acknowledge message until silent neighbour shows up
	{
		
		for (int i : nodeMsg)
		{
			if (countSilNeigh() <= 1) //already found the Silent neighbour
			{
				break;	
			}
    		if (returnNeighlist().containsKey(i)) // when node receives a message
			{
				returnNeighlist().put(i, true); // node id + message received
				System.out.println("Node "+ returnID() +" receives message from Node "+i);
			}	
		}
		if (countSilNeigh() > 1 )
		{
			nodeMsg.clear();
		}
	}
	
	private void addMsgID(int i) //adds message sender's ID to the message list
	{
		nodeMsg.add(i);
	}
	
	private void sendMsg()
	{		
		Wave nodeObj = (Wave) SuperNode.chooseNode(returnSileNeigh()); // object variable that stores the ID of the Silent Neighbour apo8ikeuw to id tou SilNei
		((Wave)nodeObj).addMsgID(returnID()); //adds ID to the neighbour's message list
		
		System.out.println("A message is sent from Node " + returnID() + " to Node "+ returnSileNeigh());
		msgAck(); //message acknowledgement
	}

	public boolean decision() //informs all nodes of the decision
	{
		
		if (countSilNeigh()==0) //if all a node has 0 silent neighbours then the node has finished
		{
			System.out.println("Node "+returnID()+" is complete");
			return false;
		}
		
		receiveMsg(); //calls method receive
		
		if (countSilNeigh()==1) //when silent neighbour is 1
		{
			if (returnMsg()==false) //if message not received from Silent neighbour
			{
				setSilentNeigh(); //calls silent neighbour method
				sendMsg(); //sends message to Silent neighbour
			}
			
			if (nodeMsg.contains(returnSileNeigh())) //if message contains silent neighbour ID
			{
				returnNeighlist().put(returnSileNeigh(), true); //silent neighbour id message reception
				System.out.println("Node "+returnID()+" has received a message from Node "+returnSileNeigh());
				return true;
			}				
			System.out.println("Node "+returnID()+ " is waiting for its silent neighbour to make contact");
						
		}
		return false;
	}

}

