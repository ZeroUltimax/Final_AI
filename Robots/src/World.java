import java.util.ArrayList;
import java.util.TreeMap;


public class World {
	
	private ArrayList<Node> nodes;

	private TreeMap<Robot,Node> robotPositions;
	
	public Node getNode(int id) {
		return nodes.get(id);
	}
	
	public World duplicate(){
		World result = new World();
		
		// Start by creating empty nodes
		for(Node n:nodes){
			result.nodes.add(new Node(n.getId(),n.getDegree()));
		}
		
		// Clone the edges into the result world
		for(Node n:nodes){
			for(Edge e:n.getEdges()){
				e.cloneToWorld(result); // The new edge binds automatically
			}
		}
		
		return result;
		
	} 
}
