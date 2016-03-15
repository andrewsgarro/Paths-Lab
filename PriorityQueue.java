import java.util.ArrayList;

//
// PRIORITYQUEUE.JAVA
// A priority queue class supporting sundry operations needed for
// Dijkstra's algorithm.
//

class PriorityQueue<T> {

	private ArrayList<Handle<T>> queue = new ArrayList<>();

	// constructor
	//
	public PriorityQueue()
	{
		//do i need anything here? i think no right?
	}

	// Return true iff the queue is empty.
	//
	public boolean isEmpty()
	{
		if (queue.isEmpty()) return true;
		else return false;
	}

	// Insert a pair (key, value) into the queue, and return
	// a Handle to this pair so that we can find it later in
	// constant time.
	//
	Handle<T> insert(int key, T value)	
	{
		Handle<T> newHandle = new Handle<T>(key, value, queue.size());
		queue.add(newHandle);
		bubbleUp(queue, newHandle.queuePos);
		return newHandle;
	}

	// Return the smallest key in the queue.
	//
	public int min()
	{
		return queue.get(0).key;
	}

	// Extract the (key, value) pair associated with the smallest
	// key in the queue and return its "value" object.
	//
	public T extractMin()
	{
		T value = queue.get(0).value;
		swap(0, queue.size() - 1); // swap root with last elt
		queue.remove(queue.size() - 1);	//remove what was originally the root and is now the last elt
		heapify(queue, 0);	
		return value;
	}


	// Look at the (key, value) pair referenced by Handle h.
	// If that pair is no longer in the queue, or its key
	// is <= newkey, do nothing and return false.  Otherwise,
	// replace "key" by "newkey", fixup the queue, and return
	// true.
	//
	public boolean decreaseKey(Handle<T> h, int newkey)
	{
		if (queue.contains(h) && h.key > newkey) {
			h.key = newkey;
			bubbleUp(queue, h.queuePos);
			return true;
		} else return false;
	}

	// Get the key of the (key, value) pair associated with a 
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public int handleGetKey(Handle<T> h)	//do we assume there are no 2 identical handles?, also does my handle (having the position) mess this up?
	{
		if (queue.contains(h)) {
			return h.key;
		}
		else return -1;	//is this right? // how do i return undefined if it says "int"?
	}

	// Get the value object of the (key, value) pair associated with a 
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public T handleGetValue(Handle<T> h)
	{
		if (queue.contains(h)) {
			return h.value;	//why do i need to cast here?
		}
		return null;
	}
	
	private void heapify(ArrayList<Handle<T>> queue, int startVal) {	//startVal is list, not heap number
		

		int heapStart = startVal + 1; 

		int childA = heapStart * 2;
		int childB = heapStart * 2 + 1;

		int testChild;

		if ((childA > queue.size()) && (childB > queue.size())) {	//make sure heapStart isn't a leaf
			return;
		} else if (childB > queue.size()) {	//if it only has 1 child, then that child is testChild
			testChild = childA - 1;
		} else if (childA > queue.size()) {
			testChild = childB - 1;
		} else if (queue.get(childA - 1).key <= queue.get(childB - 1).key) {	//if have two leaves, find which one is smaller
			testChild = childA - 1;
		} else {
			testChild = childB - 1;
		}
		
		if (queue.get(startVal).key > queue.get(testChild).key) {
			swap(startVal, testChild);
			heapify(queue, testChild);
		} else {
			return; 
		}
	}

	private void bubbleUp(ArrayList<Handle<T>> queue, int startVal) {

		int parent = (int) ((startVal + 1)/2) - 1;

		if ((startVal != 0) && (queue.get(startVal).key < queue.get(parent).key)) {
			swap(startVal, parent);
			bubbleUp(queue, parent);
		} else return;

	}

	private void swap(int a, int b) {	//input based on List pos

		int aPos = queue.get(a).queuePos;
		int bPos = queue.get(b).queuePos;	
		queue.get(a).updatePos(bPos);
		queue.get(b).updatePos(aPos);

		Handle<T> temp = queue.get(a);	
		queue.set((a),  queue.get(b));
		queue.set(b, temp);
	}

	// Print every element of the queue in the order in which it appears
	// in the implementation (i.e. the array representing the heap).
	public String toString()
	{
		String arrayString = new String();
		for (int i = 0; i < queue.size(); i++) {
			arrayString += (queue.get(i).key + ", ");	//add
		}
		return arrayString;
	}
}
