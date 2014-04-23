import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Iterator;
import java.util.LinkedList;

public class Hypothesis {

	private LinkedList<Hypothesis> subHypotheses;

	private World worldState; // The hypothesised state of the world

	public Hypothesis(World worldState) {
		this.worldState = worldState;
		this.subHypotheses = null; // Start as root node
	}

	public void expand(int nodeDegree, Robot movingRobot) {

		if (!this.isLeaf()) {
			// Trying to expand non-leaf
			throw new BadHypothesisException();
		}

		subHypotheses = new LinkedList<Hypothesis>();

		{
			// Create unconnected hypothesis first
			World unconnected = worldState.duplicate();
			Node newNode = unconnected.addNode(nodeDegree,
					unconnected.getNode(movingRobot));
			unconnected.moveRobot(movingRobot, newNode);
			subHypotheses.add(new Hypothesis(unconnected));
		}

		for (Node n : worldState.getOpenNodes(nodeDegree,
				worldState.getNode(movingRobot))) {
			World connected = worldState.duplicate();
			Node moveNode = connected.getNode(n.getId());
			Node startNode = connected.getNode(movingRobot);

			startNode.crossTo(moveNode);
			connected.moveRobot(movingRobot, moveNode);

			subHypotheses.add(new Hypothesis(connected));
		}
	}

	// meet is if r1 is supposed to see r2
	public boolean validate(Robot r1, Robot r2, boolean meet) {

		if (this.isLeaf()) {
			// Return if (r1 sees r2) is in accordance with meet
			return (worldState.getNode(r1) == worldState.getNode(r2)) == meet;
		} else {
			Iterator<Hypothesis> iter = this.subHypotheses.iterator();

			while (iter.hasNext()) {
				Hypothesis subHypothesis = iter.next();

				// Remove invalid subhypotheses
				if (!subHypothesis.validate(r1, r2, meet)) {
					iter.remove();
				}
			}

			if (this.subHypotheses.isEmpty()) {
				// Counterproof by exhaution
				return false;
			} else {

				// Must be true

				if (subHypotheses.size() == 1) {
					// Aplly equivalence relation to the subhypothesis
					this.collapseTo(subHypotheses.getFirst());
				}

				return true;
			}
		}
	}

	// Return the least connected hypothesis. Since the unconnected hypothesis
	// is generated first, query first
	public Hypothesis getUnconnectedHypothesis() {
		if (this.isLeaf()) {
			return this;
		} else {
			return subHypotheses.getFirst().getUnconnectedHypothesis();
		}
	}

	private void collapseTo(Hypothesis subHypothesis) {
		this.worldState = subHypothesis.worldState;
		this.subHypotheses = subHypothesis.subHypotheses;
	}

	private boolean isLeaf() {
		return subHypotheses == null;
	}

}
