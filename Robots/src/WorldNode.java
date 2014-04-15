
public class Node {

	private int worldId;

	private Edge[] edges;

	Node(int worldId, int degree) {
		this.worldId = worldId;
		edges = new Edge[degree];
	}

	public int getId() {
		return worldId;
	}

	public void setEdge(Edge e, int position) {
		edges[position] = e;
	}

	public int getDegree() {
		return edges.length;
	}

	public Edge[] getEdges() {
		return edges;
	}

}
