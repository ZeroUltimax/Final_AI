import java.util.ArrayList;
import java.util.TreeMap;


public class World {
	
	private ArrayList<Node> nodes;

	private TreeMap<Robot,Integer> robotNodeId; // Id of the nodes for each robot
	
	public  World(){
		throw new UnsupportedOperationException();
	}
	
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
		
		
		// Copy the mapping for the robot
		result.robotNodeId = new TreeMap<Robot,Integer>(this.robotNodeId);
		
		return result;
		
	} 

	public Node getNode(Robot r){
		return nodes.get(robotNodeId.get(r));
	}

	public void addNode(int nodeDegree, Node parentNode) {
		throw new UnsupportedOperationException();
	}
	
	private int nextNodeId(){
		return nodes.size();
	}
}
