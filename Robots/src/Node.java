
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

	
	public Node getParent(){
		if(this.Id==0){
			return null; // Root of the graph
		}else{
			return edges[0].traverse(this); // Traverse edge 0 to go to parent
		}
	}
}
