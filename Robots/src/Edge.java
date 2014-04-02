public class Edge {

	private enum State {
		Unverified, Main, Crossing,
	}

	private State state;

	private WorldNode A, B;
	private int APosition, BPosition;

	public Edge(WorldNode start, int startPosition) {
		state = State.Unverified;

		A = start;
		B = null;
		APosition = startPosition;
		BPosition = -1;
	}

	private Edge(WorldNode a, int aPosition, WorldNode b, int bPosition,
			State state) {

		if (a.getId() > b.getId()) {
			WorldNode tmp = a;
			a = b;
			b = tmp;
		}

		this.state = state;
		A = a;
		B = b;
		APosition = aPosition;
		BPosition = bPosition;
	}

	public boolean isStart(WorldNode n) {
		return n == A;
	}

	static Edge fuse(Edge left, Edge right, boolean isCross)
			throws BadEdgeException {
		if (left.B != null || right.B != null) {
			throw new BadEdgeException();
		}

		return new Edge(left.A, left.APosition, right.A, right.APosition,
				isCross ? State.Crossing : State.Main);

	}

	public Edge cloneToWorld(World w) {
		Edge result = new Edge(w.getEdge(A.getId()), A.getId(), w.getEdge(B
					.getId()), B.getId(), state);


		result.A.setEdge(result, APosition);
		result.B.setEdge(result, BPosition);

		return result;

	}

}
