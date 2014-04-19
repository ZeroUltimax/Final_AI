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
		for(Node n:this.nodes){
			result.nodes.add(new Node(n.getId(),n.getDegree()));
		}
		
		// Clone the edges into the result world
		for(Node n:this.nodes){
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

	public Node addNode(int nodeDegree, Node parentNode) {
		Node newNode =  new Node(nextNodeId(),nodeDegree);
		parentNode.setParentOf(newNode);
		nodes.add(newNode);
		return newNode;
	}
	
	private int nextNodeId(){
		return nodes.size();
	}
	
	public ArrayList<Node> getOpenNodes(int nodeDegree,Node excludeNode){		
		ArrayList<Node> result = new ArrayList<Node>();
		for(Node n:nodes){
			if(n.getDegree()==nodeDegree&&n!=excludeNode){
				result.add(n);
			}
		}
		return result;
	}

	public void moveRobot(Robot movingRobot, Node endPoint) {
		Node startPoint = this.getNode(movingRobot);
		for(Edge e:startPoint .getEdges()){
			if(e.traverse(startPoint)==endPoint){
				robotNodeId.put(movingRobot, nodes.indexOf(endPoint));
				return;
			}
		}
		throw new BadEdgeException("No existing edge to endPoint from current robot position");
	}
}
