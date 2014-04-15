public class Edge {

	private enum State {
		Open, // Does not have an attached ending point
		Main, // Is part of the main branch
		Crossing // Crosses between branches
	}

	private State state;

	private Node A, B;
	private int APosition, BPosition;

	public Edge(Node start, int startPosition) {
		state = State.Open;

		A = start;
		B = null;
		APosition = startPosition;
		BPosition = -1;
	}

	private Edge(Node a, int aPosition, Node b, int bPosition,
			State state) {

		//Ensure a's ID is always lower than b's
		if (a.getId() > b.getId()) {
			Node tmp = a;
			a = b;
			b = tmp;
		}

		this.state = state;
		A = a;
		B = b;
		APosition = aPosition;
		BPosition = bPosition;
	}

	public boolean isStart(Node n) {
		return n == A;
	}

	static Edge fuse(Edge left, Edge right, boolean isCross)
			throws BadEdgeException {
		if (left.state==State.Open || right.state==State.Open) {
			// Trying to fuse edges that are not open (both left and right are taken)
			throw new BadEdgeException();
		}

		return new Edge(left.A, left.APosition, right.A, right.APosition,
				isCross ? State.Crossing : State.Main); // Set new edge as either crossing or main edge

	}

	// Clone an edge to a new world view. World w is needed so that the edge links between the nodes of that world.
	public Edge cloneToWorld(World w) {
		Edge result = new Edge(
				w.getNode(A.getId()), A.getId(), // Link to node A of w 
				w.getNode(B.getId()), B.getId(), // Link to node B of w
				state);

		result.A.setEdge(result, APosition);
		result.B.setEdge(result, BPosition);

		return result;
	}
}
