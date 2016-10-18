import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
	
    Node root;
    HashMap<String, Node> nodeTable; 
    ArrayList<String> nodeLabels;

    public Tree(String element) {
    	nodeTable = new HashMap<String, Node>();
    	nodeLabels = new ArrayList<String>();
        root = new Node(element);
        root.parent = null;
        nodeTable.put(element, root);
        nodeLabels.add(element);
    }
    
    public void addNode(String element, Node parent) {
    	Node newNode = parent.addChild(element);
    	nodeTable.put(element, newNode);
    	nodeLabels.add(element);
    	setDepths();
    }
    
    public int getDepth(Node node) {
    	if (node.equals(root)) {
    		return 0;
    	}
    	else {
    		return 1 + getDepth(node.parent);
    	}
    }
    
    private void setDepths() {
    	for(String l : nodeLabels) {
    		nodeTable.get(l).depth = getDepth(nodeTable.get(l));
    	}
    }
    
    public Node getNode(String element) {
    	return nodeTable.get(element);
    }
    
    public String getParentElement(Node node) {
    	return node.parent.element;
    }
    
    public ArrayList<String> getChildrenElements(Node node) {
    	ArrayList<String> children = new ArrayList<String>();
    	for (Node c : node.children) {
    		children.add(c.element);
    	}
    	return children;
    }
    
    public void printChildren(Node node) {
    	System.out.print(node.toString() + "'s children: ");
    	for(Node n : node.children) {
    		System.out.print(n.toString() + " ");
    	}
    	System.out.println("");
    }
    
    public boolean isLeaf(Node node) {
    	return node.children.isEmpty();
    }
    
    public void postOrder(Node node){ 
        for(Node n : node.children){
            postOrder(n);
        }
        System.out.print(node.toString() + " ");
    }
    
    public void preOrder(Node node){ 
    	System.out.print(node.toString() + " ");
    	for(Node n : node.children){
            preOrder(n);
        }
    }
    
}