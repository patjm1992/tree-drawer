import java.util.ArrayList;

public class Node {

	String element;
	int location, depth;
	Node parent;
	float x, y, mod;
	ArrayList<Node> children;
	
	public Node(String element) {
		this.element = element;
		children = new ArrayList<Node>();
	}
	
	public Node addChild(String element) {
		Node newNode = new Node(element);
		children.add(newNode);
		newNode.parent = this;
		newNode.location = children.indexOf(newNode);
		return newNode;
	}

	public Node getPreviousSibling() {
		return this.parent.children.get(location - 1);
	}
	
	public boolean hasSiblings() {
		return (this.parent.children.size() > 1);
	}
	
	public ArrayList<Node> getLeftSiblings() {
		ArrayList<Node> leftSiblings = new ArrayList<Node>();
		ArrayList<Node> container = this.parent.children;
		if (this.isLeftMost()) {
			return null;
		} else {
			for(int i = 0; i < this.location; i++) {
				leftSiblings.add(container.get(i));
			}
			return leftSiblings;
		}	
	}

	/**
	 * Recursively finds the contour of a node for each level
	 * (The maximum x value among the node's children if it is a leftmost node, 
	 * or the minimum x value among the node's children if it is
	 * not leftmost).This is used later to deal with subtrees that have overlapping 
	 * nodes.
	 */
	public float getContour(Node node) {
		float contour = 0;
		if (this.isLeftMost()) {	// Will be getting the 'right contour'
			for(Node c : node.children) {
				contour = Math.max(node.x, getContour(c));
			}
		}
	
		if (!this.isLeftMost()) {	// Will be getting the 'left contour'
			for(Node c : node.children) {
				contour = Math.min(node.x, getContour(c));
			}
		}
		return contour;
	}
	
	public boolean isLeftMost() {
		if (this.parent == null)
			return true;
		else
			return this.parent.children.get(0).equals(this);
	}
	
	public boolean isRightMost() {
		ArrayList<Node> container = this.parent.children;	
		return container.get(container.size() - 1).equals(this);
	}
	
	public Node findNode(String elem) {
		if (this.element.equals(elem)) {
			return this;
		} else {
			for(Node c : this.children) {
				if (c.element.equals(elem)) {
					return c;
				}
			}
		} 
		return null;
	}
	
	/**
	 * Return the count of the number of siblings at this level
	 * that are not leftmost or rightmost.
	 */
	public int getMidSibsCt() {
		int ct = 0;
		for(Node c : this.parent.children) {
			if (!c.isLeftMost() && !c.isRightMost()) {
				ct++;
			}
		}
		return ct;
	}
	
	public boolean isLeaf() {
		return children.isEmpty();
	}
	
	public String toString() {
		return this.element;
	}
	
}
