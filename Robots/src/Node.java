public class Node {

	private int Id;

	private Edge[] edges;

	Node(int Id, int degree) {
		this.Id = Id;
		this.edges = new Edge[degree];
	}

	public int getId() {
		return Id;
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

	public Node getParent() {
		if (this.Id == 0) {
			return null; // Root of the graph
		} else {
			return edges[0].traverse(this); // Traverse edge 0 to go to parent
		}
	}

	private int getOpenEdgeId() {
		for (int i = 0; i < this.edges.length; i++) {
			if (edges[i].isOpen()) {
				return i;
			}
		}
		throw new BadEdgeException("No Open edge found");
	}

	public boolean hasOpen() {
		for (Edge e : edges) {
			if (e.isOpen()) {
				return true;
			}
		}
		return false;
	}

	private void joinToNode(Node otherNode, int otherOpenId, boolean isCross) {
		int thisOpenId = this.getOpenEdgeId();

		Edge fusedEdge = Edge.fuse(this.edges[thisOpenId],
				otherNode.edges[otherOpenId], isCross);

		this.edges[thisOpenId] = fusedEdge;
		otherNode.edges[otherOpenId] = fusedEdge;
	}

	public void crossTo(Node otherNode) {
		joinToNode(otherNode, otherNode.getOpenEdgeId(), true);
	}

	public void setParentOf(Node newNode) {
		joinToNode(newNode, 0, false);
	}

}
