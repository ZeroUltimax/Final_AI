import java.util.ArrayList;


public class World {

	
	private ArrayList<WorldNode> nodes;

	public WorldNode getEdge(int id) {
		return null;
	}
	
	public World duplicate(){
		World result = new World();
		
		// Start by creating empty nodes
		for(WorldNode n:nodes){
			result.nodes.add(new WorldNode(n.getId(),n.getDegree()));
		}
		
		// Clone the edges into the result world
		for(WorldNode n:nodes){
			for(Edge e:n.getEdges()){
				e.cloneToWorld(result); // The new edge binds automatically
			}
		}
		
		return result;
		
	} 
}
