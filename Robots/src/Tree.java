import java.util.ArrayList;
import java.util.Iterator;

public abstract class Tree<SubClass extends Tree<SubClass>> implements
		Iterable<SubClass> {

	SubClass parent;

	ArrayList<SubClass> subNodes;

	Tree() {
		this(null);
	}

	Tree(SubClass parent) {
		this.parent = parent;
		subNodes = new ArrayList<SubClass>();
	}

	ArrayList<SubClass> getSubNodes() {
		return subNodes;
	}

	class TreeIterator implements Iterator<SubClass> {

		SubClass root;
		Iterator<SubClass> listIterator;
		TreeIterator subNodeIterator;

		TreeIterator(SubClass root) {
			this.root = root;
			listIterator = null;
			subNodeIterator = null;
		}

		public SubClass next() {
			if (listIterator == null) {
				listIterator = root.getSubNodes().iterator();
				return root;
			} else {
				if (hasNext()) {
					return subNodeIterator.next();
				} else {
					return null;
				}
			}
		}

		public boolean hasNext() {
			if (root == null) {
				return false;
			} else {
				if (listIterator == null) {
					return true; // next is the root
				} else {
					while (subNodeIterator == null) {
						if (!listIterator.hasNext()) {
							return false;
						} else {
							subNodeIterator = listIterator.next().iterator();
						}

						if (!subNodeIterator.hasNext()) {
							subNodeIterator = null;
						}
					}
					return true;
				}
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	@SuppressWarnings("unchecked")
	// this will always be castable to subclass by generics definition because
	// subclass will be this' type
	public TreeIterator iterator() {
		return new TreeIterator((SubClass) this);
	}
}
