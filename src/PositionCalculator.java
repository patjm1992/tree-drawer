/**
 * This class finds an (x, y) for each node. As the y-coord is simply the
 * depth, 99.9% of this class is focused on calculating the x-coords.
 */
public class PositionCalculator {

	Tree tree;
	Node root;
	float siblingSeparation;
	
	public PositionCalculator(float siblingSeparation) {
		
		// For now, a sample tree is hard coded in
		// This tree is taken from http://www.drdobbs.com/positioning-nodes-for-general-trees/184402320?pgno=4 
		// It's a good example as it has several levels
		tree = new Tree("O");
		root = tree.root;
		tree.addNode("E", root);
		tree.addNode("F", root);
		tree.addNode("N", root);
		tree.addNode("A", tree.getNode("E"));
		tree.addNode("D", tree.getNode("E"));
		tree.addNode("B", tree.getNode("D"));
		tree.addNode("C", tree.getNode("D"));
		tree.addNode("G", tree.getNode("N"));
		tree.addNode("M", tree.getNode("N"));
		tree.addNode("H", tree.getNode("M"));
		tree.addNode("I", tree.getNode("M"));
		tree.addNode("J", tree.getNode("M"));
		tree.addNode("K", tree.getNode("M"));
		tree.addNode("L", tree.getNode("M"));
		
		this.siblingSeparation = siblingSeparation;
		
		// Call the methods that find the x-coords
		this.firstPass(root);
		this.secondPass(root, 0);
		
	}
	
	/**
	 * A pre-order traversal. Does three things:
	 * - Assigns each node an initial x-position relative to it's siblings
	 * - Centers parents over children
	 * - Deals with overlapping nodes by finding left and right tree contours and shifting over
	 */
	private void firstPass(Node node) {
		for(Node n : node.children) {
				firstPass(n);
		}
		
		// If node is a parent and leftmost amongst it's children
		if (!node.isLeaf() && node.isLeftMost()) {
			// If it has multiple children, set it's x to the midpoint of them.
			if (node.children.size() > 1) {	
				node.x = getMidPoint(node);
				node.mod = 0;
			} else {	// Otherwise set it's x to the x of it's only child
				node.x = node.children.get(0).x;
				node.mod = 0;
			}
		// If a node is a parent but has siblings to the left
		} else if (!node.isLeaf() && !node.isLeftMost()) {
			node.x = node.getPreviousSibling().x + siblingSeparation;
			node.mod = node.x - getMidPoint(node);			 
		// Set x-values for leaf nodes	
		} else if (node.isLeaf()) {
			if (node.isLeftMost()) {
				node.x = 0;
				node.mod = 0;
			} else {
				node.x = node.getPreviousSibling().x + siblingSeparation;
				node.mod = 0;
			}
		}
		
		// Dealing with overlapping nodes
		if (!node.isLeaf() && node.parent != null) {
			if (!node.isLeftMost()) {
				float rtContour = node.getContour(node);
				for (Node sib : node.getLeftSiblings()) {
					float shift = 0;
					float ltContour = sib.getContour(sib);
					if (rtContour - ltContour < 1) {	// If there is a conflict
						shift = 1 - (rtContour - ltContour);
						node.x += shift;
						node.mod += shift;
					}
				}
			}
		}
		
		
		
//		We shifted node N because it conflicted with E, and now we need to take that
//		distance and apply it evenly across all sibling nodes that are between the two conflicting 
//		nodes. To do that, we divide the distance shifted by the number of nodes between our 
//		two conflicting nodes + 1, and shift each of the middle nodes over by this value.
//		In our case we shifted the node by 2.5, and there is only 1 node (F) between nodes E and 
//		N, so we need to shift F over by 2.5 / (1 + 1), which results in 1.25.
		
	
		
		
	}
	
	/**
	 * Pre-Order traversal to calculate all the final x-values. This is done by
	 * summing up all of the current node's ancestor's mod values.
	 */
	private void secondPass(Node node, int shift) {
		node.x += shift;
		shift  += node.mod;
		
		for(Node n : node.children) {
			secondPass(n, shift);
		}
	}
	
	/**
	 * Get the midpoint between the Node's leftmost and rightmost children.
	 */
	private float getMidPoint(Node node) {
		float leftX = node.children.get(0).x;
		float rightX = node.children.get(node.children.size() - 1).x;
		return (leftX + rightX) / 2;		
	}
	
	/**
	 * Prints node information for debugging purposes.
	 */
	public void postOrderPrint(Node node) {
		for(Node n : node.children) {
			postOrderPrint(n);
		}
		System.out.println("*** " + node.toString() + " ***");
		System.out.println("X:   " + node.x);
		System.out.println("MOD: " + node.mod);
		System.out.println("");
	}
	
}
