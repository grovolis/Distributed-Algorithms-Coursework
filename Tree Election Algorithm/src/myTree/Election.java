package myTree;

import java.util.TreeSet;
import java.util.TreeMap;

public final class Election extends SuperNode  {	
	
	private boolean wakeMsgAck = false; //wake up message flag
	
	private boolean sileNeighAck = false; //silent neighbour flag
	
	private int wakeMsgCount = 0; // wake up message count
	
	private TreeSet<Integer> wakeUpMsg = new TreeSet<Integer>(); //stores wake up messages
	private int minID;	//minimum leader ID
	private TreeMap<Integer, Integer> msg = new TreeMap<Integer, Integer>(); //list with the ID of the sender and the minimum Leader id
	
	
	
	public Election(int nID, TreeSet<Integer> neighList) //invokes constructor from SuperNode class to initialise variables

	{
		super(nID, neighList);
		minID = nID;		
	}
	private void getWakeUpMsg(int i) //message sender's ID is added to the message list
	{
		wakeUpMsg.add(i);
	} 
	
	private void sendWakeMsg(int i) //send wake up message
	{
		Election eleObj = (Election) SuperNode.chooseNode(i); //create an object and assign node ID
        ((Election)eleObj).getWakeUpMsg(returnID()); //add message sender id
        System.out.println("Node "+ returnID() +" has sent a wake-up message to "+ i);
	}
	
	public int returnMinID() //leaders' smaller ID
	{
		return minID;
	}	
	
	public void sendWakeMessage() //starts sending wake up messages to all neighbours
	{
		sileNeighAck = true;
		System.out.println("Node "+returnID()+" wakes up and sends message to every neighbour");
		for(int i : returnNeighlist().keySet())
		{
			sendWakeMsg(i); // sends wakeup message to all neighbours
		}
	}
	
    private void addMessage(int i, int x) //adds message to the map set, i is the id of the sender, x is the minimum token 
	{
		msg.put(i, x);
	}
	
	private void receive() //receives messages from neighbours and updates minID value
	{
		
		for(int i : msg.keySet())
		{	
			if (countSilNeigh() <= 1) break; // silent neighbour found
			
			if (returnNeighlist().containsKey(i))
			{
				minID=Math.min(msg.get(i), minID); //find the minimum id
				returnNeighlist().put(i, true); //message received - update value
				System.out.println("Node "+returnID()+" has received a message from "+i);
			}			
		}
		if (countSilNeigh() > 1)
		{
			msg.clear();
		}
		
	}
	private void sendMessage( int i)
	{
        Election eleObj = (Election) SuperNode.chooseNode(i); //creates object and assign node ID
        ((Election) eleObj).addMessage(returnID(), minID); //sends message to the neighbour's message list
        System.out.println("Node "+ returnID()+ " sends a message to "+i);
        msgAck(); //message acknowledgement
	}
	public boolean decision() // decision method
	{		
		if(countSilNeigh() == 0) // if there is not silent neighbour then algorithm has finished
		{
			System.out.println("Node "+returnID()+" is complete");
			return false;
		}
		if (!wakeMsgAck) //if wake up message is not received
		{
			for (int i : wakeUpMsg) //sends wake up message to all neighbours
			{
				if (returnNeighlist().containsKey(i)) //when wake up is received
				{
					wakeMsgCount++; // increase wake up message count
				}
			}
			
			wakeUpMsg.clear();
			
			if(wakeMsgCount > 0 && sileNeighAck == false) //if wake up messages have been received and silent neighbour is not identified
			{
				sendWakeMessage(); //send wake up message to all neighbours
				
			}
			
			if (wakeMsgCount >= returnNeighlist().size()) //when all wake up messages have been received
			{
				System.out.println("Node "+returnID()+" has heard back from all of its neighbours and now starts executing the Wave Algorithm");
				wakeMsgAck = true; //wake up messages received - acknowledged
			}
		}
		
		if (wakeMsgAck) // if wake message is sent
		{	
			receive(); //receive wake up messages
			
			if (countSilNeigh() == 1) //if silent neighbour is found
			{
				if (returnMsg() == false) //message not received
			{
				setSilentNeigh();	//define silent neighbour		
				sendMessage(returnSileNeigh()); //send message to silent neighbour			
			}
			if (msg.containsKey(returnSileNeigh())) //if message contains silent neighbour ID
			{
				minID=Math.min(minID, msg.get(returnSileNeigh())); //compare leader Id with the sender ID
				returnNeighlist().put(returnSileNeigh(),true); //initiator receives message from silent neighbour
				System.out.println("Node "+returnID()+" has received a message from "+returnSileNeigh());
				if (returnID() == minID) // leader is found
				{
					System.out.println("Node " +returnID()+" is the leader");
				}
				else //otherwise
				{
					System.out.println("Node "+returnID()+" loses");
				}	
				
				for (int i : returnNeighlist().keySet())
				{
					if (i != returnSileNeigh())sendMessage(i); // nodes have decided
				}
				return true;			
			} //otherwise if nodes are still waiting for the silent neighbour method returns false
			System.out.println("Node "+returnID()+ " is waiting for its silent neighbour to make contact");
			}
		}
		return false;
	}
}
	
		
		
		


