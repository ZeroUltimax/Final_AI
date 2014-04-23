public class Robot implements Comparable<Robot> {

	private enum State {
		Explore,
		Wait,
		Busy,
		Return
	};

	static private int maxId = 0;

	private int Id;

	private State state;

	Robot() {
		this.Id = maxId++;
		this.state = State.Explore;
	}

	public int compareTo(Robot other) {
		return Integer.compare(this.Id, other.Id);
	}

}
