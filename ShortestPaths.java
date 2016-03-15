import java.util.ArrayList;
import java.util.Arrays;




//
// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
class ShortestPaths {

	PriorityQueue<Vertex> q;	

	private ArrayList<Handle<Vertex>> handles;

	private ArrayList<Handle<Vertex>> parents;
	private ArrayList<Integer> distance;	
	private ArrayList<Integer> edges;

	private ArrayList<Integer> times;
	
	//Input flightlist = new Input();
	
	//
	// constructor
	//
	public ShortestPaths(Multigraph G, int startId, 
			Input input, int startTime) 
	{

		Vertex startVertex = G.get(startId);

		q = new PriorityQueue<Vertex>();

		handles = new ArrayList<Handle<Vertex>>();

		parents = new ArrayList<Handle<Vertex>>();
		distance = new ArrayList<Integer>();
		edges = new ArrayList<Integer>();	

		times = new ArrayList<Integer>();

		if (startTime == 0) Djikstras(G, startVertex, q);
		else timedDjikstras(G, startVertex, q);
	}

	//
	// returnPath()
	// Return an array containing a list of edge ID's forming
	// a shortest path from the start vertex to the specified
	// end vertex.
	//
	public int [] returnPath(int endId) 
	{ 
		ArrayList<Integer> result = new ArrayList<Integer>();
		Vertex v = handles.get(endId).value;

		while (parents.get(v.id()) != null) {
			result.add(edges.get(v.id()));
			v = parents.get((v.id())).value;
		}

		int path [] = new int [result.size()];

		for (int i = 0; i < result.size(); i++) {
			path[i] = result.get((result.size() - i - 1));
		}

		return path;
	}

	public int timedDist(Edge takeEdge, int landingTime) {
		int result = (int) Double.POSITIVE_INFINITY;
		Input.Flight a = Input.flights[takeEdge.id()];	//is it ok that i made flights static?
		System.out.println(a.name);
		if (a.startTime < landingTime + 0450) {
			result = (((a.startTime + 2400) - landingTime) / 100) + takeEdge.weight();
		} else {
			result = ((a.startTime - landingTime) / 100) + takeEdge.weight(); 
		}
		return result;
	}

	public void Djikstras(Multigraph G, Vertex s, PriorityQueue<Vertex> q) {

		int numV = G.nVertices();

		for (int i = 0; i < numV; i++) {	//populate the minfirst priority q and the handles arrayList
			Handle<Vertex> temp = q.insert((int) Double.POSITIVE_INFINITY, G.get(i));
			handles.add(temp);
			distance.add((int) Double.POSITIVE_INFINITY);
			parents.add(null);
			edges.add(null);
			//times.add(null);
		}

		q.decreaseKey(handles.get(s.id()), 0);
		distance.set(s.id(), 0);

		while(!q.isEmpty()) {

			Vertex current = q.extractMin();

			Handle<Vertex> currentHandle = handles.get(current.id());

			if (currentHandle.key == ((int) Double.POSITIVE_INFINITY)) {
				return;
			}

			Vertex.EdgeIterator edgeiter = current.adj();

			while(edgeiter.hasNext()) {	
				Edge takeEdge = edgeiter.next();
				Vertex next = takeEdge.to();
				Handle<Vertex> nextHandle = handles.get(next.id());

				int dist = distance.get(current.id()) + takeEdge.weight();
				//int dist = distance.get(current.id()) + timedDist(takeEdge, times.get(next.id()));

				if (q.decreaseKey(nextHandle, dist)) {
					distance.set(next.id(), dist);	//
					parents.set(next.id(), currentHandle);	//parents is a linked list path through the graph
					edges.set(next.id(), takeEdge.id());
					//times.set(next.id(), Input.flights[current.id()].endTime);

				}
			}
		}		
	}


	public void timedDjikstras(Multigraph G, Vertex s, PriorityQueue<Vertex> q) {

		int numV = G.nVertices();

		for (int i = 0; i < numV; i++) {	//populate the minfirst priority q and the handles arrayList
			Handle<Vertex> temp = q.insert((int) Double.POSITIVE_INFINITY, G.get(i));
			handles.add(temp);
			distance.add((int) Double.POSITIVE_INFINITY);
			parents.add(null);
			edges.add(null);
			times.add(null);
		}

		q.decreaseKey(handles.get(s.id()), 0);
		distance.set(s.id(), 0);

		while(!q.isEmpty()) {

			Vertex current = q.extractMin();

			Handle<Vertex> currentHandle = handles.get(current.id());

			if (currentHandle.key == ((int) Double.POSITIVE_INFINITY)) {
				return;
			}

			Vertex.EdgeIterator edgeiter = current.adj();

			while(edgeiter.hasNext()) {	
				Edge takeEdge = edgeiter.next();
				Vertex next = takeEdge.to();
				Handle<Vertex> nextHandle = handles.get(next.id());

				//int dist = distance.get(current.id()) + takeEdge.weight();
				System.out.println(times.size());
				System.out.println(current.id());
				System.out.println(next.id());
				System.out.println(distance.size());
				times.set(0, 0);
				System.out.println(timedDist(takeEdge, times.get(next.id())));
				int dist = distance.get(current.id()) + timedDist(takeEdge, times.get(next.id()));

				if (q.decreaseKey(nextHandle, dist)) {
					distance.set(next.id(), dist);	//
					parents.set(next.id(), currentHandle);	//parents is a linked list path through the graph
					edges.set(next.id(), takeEdge.id());
					times.set(next.id(), Input.flights[current.id()].endTime);

				}
			}
		}		
	}
}
