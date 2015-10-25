
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;




public class MST {
    static final int Infinity = Integer.MAX_VALUE;
	   private static int phase = 0;
	   private static long startTime, endTime, elapsedTime;

    static int PrimMST(Graph g)
    {
	int wmst = 0; // total weight of spanning tree
//	PriorityQueueIndexed<Graph.Vertex> q=new PriorityQueueIndexed<Graph.Vertex>(g.V.length);
	g.V[1].weight=0;// set weight of root to 0 
//	for(int i=1;i<g.V.length;i++)
//	{
//		q.add(g.V[i]);
//	}
	PriorityQueueIndexed<Graph.Vertex> q=new PriorityQueueIndexed<Graph.Vertex>(g.V);
	while(q.size>0)// while there are elements in the Priority Queue
	{
		Graph.Vertex node=(Graph.Vertex) q.deleteMin();// get  element with min weight
		//System.out.println("Hi "+node.name);
		node.seen=true;// mark it seen
		wmst=wmst+node.weight;// update weight of the MST
		//System.out.println("Weight: "+wmst);
		for(Graph.Edge e: node.Adj)
		{// for every node adjecent to the selected node
			Graph.Vertex othernode=e.otherEnd(node);
			if(othernode.seen!=true&&(e.Weight<othernode.weight))
			{
				othernode.parent=node; // set the parent to be the selected min node the given node is adjecent to
				othernode.weight=e.Weight;// update the weights of every node adjecent to the selected node
				q.decreaseKey(othernode);// reorder the Priority Queue as per the new weights
			}
		}
	}
	
	
	

        // Code for Prim's algorithm needs to be written

	return wmst;
    }

    public static void main(String[] args) 
    {
	Scanner in = null;
	if(args.length>0)
	{//get user input
		File input=new File(args[0]);
		try{
			in=new Scanner(input);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	else
	{
		in=new Scanner(System.in);
	}
	
	Graph g = Graph.readGraph(in);
	//g.printGraph();
	timer();
	System.out.println(PrimMST(g));
	timer();
    }
    
    public static void timer()
    {
        if(phase == 0) {
	    startTime = System.currentTimeMillis();
	    phase = 1;
	} else {
	    endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            System.out.println("Time: " + elapsedTime + " msec.");
            memory();
            phase = 0;
        }
    }

    public static void memory() {
        long memAvailable = Runtime.getRuntime().totalMemory();
        long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
        System.out.println("Memory: " + memUsed/1000000 + " MB / " + memAvailable/1000000 + " MB.");
    }
}
