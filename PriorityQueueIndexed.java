// Ver 0.1:  Sat, Feb 28.  Initial description.
// Ver 1.0:  Tue, Mar 03.  Added more comments.  Modified decreaseKey().

import java.lang.Comparable;

public class PriorityQueueIndexed<T extends Comparable<? super T> & PQIndex> {
    T[] queue;    
    int size;
    /** Build a priority queue with a given array q */
    @SuppressWarnings("unchecked")
	PriorityQueueIndexed(T[] q) {
    	//constructor to a array and create a priority queue out of it
    	queue=(T[])new Comparable[q.length];
    	size=q.length-1;
    	for(int i=1;i<q.length;i++)
    	{
    		queue[i]=q[i];
    		queue[i].putIndex(i);
    	}
    	buildHeap(queue);
    	
    	
    }

    /** Create an empty priority queue of given maximum size */
    @SuppressWarnings("unchecked")
	PriorityQueueIndexed(int n) {
    	//constructor to create a empty Priority Queue of the specified length
    	queue=(T[])new Comparable[n];
    	size=0;
    }

    void insert(T x) {
	add(x);
    }

    @SuppressWarnings("unchecked")
	void resize()
    {
    	//resize the queue  to double its original size
    	T[] temp = (T[])new Comparable[2*size];
    	for(int i=0;i<queue.length;i++)
    	{
    		temp[i]=queue[i];
    	}
    	queue=temp;
    }
    void add(T x) {
    	if(queue.length==size-1)
    	{// if queue is full
    		resize();
    	}
    	queue[0]=x;
    	int hole=size+1;
    	while(queue[hole/2].compareTo(x)>0)
    	{// compare the new element and find its correct place in the Priority queue
    		queue[hole]=queue[hole/2];
    		queue[hole].putIndex(hole);
    		hole=hole/2;
    		
    	}
    	
    	queue[hole]=x;
    	x.putIndex(hole);// set the index in the in the object to the location of the object in the queue
    	size++;
    }

    T remove() {
       return deleteMin();
    }

    T deleteMin() {// remove the min element and re adjust the queue 
    	T rv=queue[1];
    	queue[1]=queue[size];
    	size--;
    	percolateDown(1);
	return rv;
    }

    /** restore heap order property after the priority of x has decreased */
    void decreaseKey(T x) {
	percolateUp(x.getIndex());
    }

    T min() { //returns the min element without removing
    	if(size>0)
    	{	return queue[1];
    	
    	}
    	else
    	{
    		return null;
    	}
      }

    /** Priority of element at index i of queue has decreased.  It may violate heap order.
     *  Restore heap property */
    void percolateUp(int i) {
    	T x=queue[i];
    	int hole=i;// update the queue as per the updated pririties of the elements moves lighter elements up
    	while(hole>1 && queue[hole/2].compareTo(x)>0)
    	{
    		queue[hole]=queue[hole/2];
    		queue[hole].putIndex(hole);
    		hole=hole/2;
    		
    	}
    	
    	queue[hole]=x;
    	queue[hole].putIndex(hole);// set the new index location  in the object
    	
    }

    /** Create heap order for sub-heap rooted at node i.  Precondition: Heap order may be violated 
     *  at node i, but its children are roots of heaps that are fine.  Restore heap property */
    void percolateDown(int i) {
    	int min; // moves the heavier element down
    	if(2*i<size && queue[2*i].compareTo(queue[2*i+1])>0)// if there are 2 children to the parent check which is smaller
    	{
    		min=2*i+1;
    	}
    	else
    	{
    		min=2*i;
    	}
    	if((2*i==size)&&(queue[i].compareTo(queue[2*i])>0))// if there is just one child compare with the parent and swap
    	{
    		T temp=queue[i];
    		queue[i]=queue[2*i];
    		queue[2*i]=temp;
    		queue[i].putIndex(i);
    		queue[2*i].putIndex(2*i);
    		
    	}
    	if((2*i<size)&&(queue[i].compareTo(queue[min])>0))// for two children compare parent with min element and swap
    	{
    		T temp=queue[i];
    		queue[i]=queue[min];
    		queue[min]=temp;
    		queue[i].putIndex(i);
    		queue[min].putIndex(min);
    		percolateDown(min);
    		
    	}
    	
    }

    /** Create a heap.  Precondition: none.  Array may violate heap order in many places. */
    void buildHeap(T[] a) {
    	int last_parent=(int)(Math.floor(a.length-1)/2);// start from the last parent node and percolateDown each one of them to their correct position
    	for(int i=last_parent;i>=1;i--)
    	{
    		percolateDown(i);
    	}
    	
    }
}
