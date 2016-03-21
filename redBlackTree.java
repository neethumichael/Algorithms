import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class redBlackTree {

	// T_null denotes the null node with Color as black
	// T_root stores the root node of tree.
	private static Node T_null = createNullLeafNode();
	private static Node T_root;

	public enum Color {
		RED,
		BLACK
	}

	// structure of Node
	private static class Node {
		int data;
		Color color;
		Node left;
		Node right;
		Node parent;
	}

	// creates a node with color RED and value as given data.
	// Function returns the created node.
	private static Node createRedNode(int data) {
		Node n = new Node();
		n.data = data;
		n.color = Color.RED;
		n.left = T_null;
		n.right = T_null;
		return n;
	}

	// creates a Null node with color as BLACK.
	// Function returns the created node. This function
	// is called just once while initialzing T_null.
	private static Node createNullLeafNode() {
		Node leaf = new Node();
		leaf.color = Color.BLACK;
		return leaf;
	}

	// performs left rotation
	private void leftRotate(Node x){
		Node y = x.right;
		x.right = y.left;
		if(y.left !=T_null)
			y.left.parent = x;
		y.parent = x.parent;
		if(x.parent == T_null)
			T_root = y;
		else if(x == x.parent.left)
			x.parent.left =y;
		else
			x.parent.right =y;
		y.left = x;
		x.parent = y;
	}

	// performs right rotation
	private void rightRotate(Node y){
		Node x = y.left;
		y.left = x.right;
		if(x.right!=T_null)
			x.right.parent =y;
		x.parent = y.parent;
		if(y.parent == T_null)
			T_root = x;
		else if(y == y.parent.right)
			y.parent.right =x;
		else
			y.parent.left = x;
		x.right = y;
		y.parent = x;		
	}

	// insert the given node in the RB tree
	private void RB_insert(Node z){
		Node y = T_null;
		Node x  = T_root;
		while(x != T_null) {
			y = x;
			if(z.data < x.data) 
				x = x.left;
			else
				x = x.right;			
		}
		z.parent = y;
		if(y == T_null)
			T_root = z;
		else if(z.data < y.data)
			y.left =z;
		else
			y.right =z;
		z.left = T_null;
		z.right = T_null;
		z.color = Color.RED;
		RB_insert_fixup(z);
	}

	// fixes the given tree to follow RB tree properties
	private void RB_insert_fixup(Node z) {
		while(z.parent.color == Color.RED)
		{
			if(z.parent == z.parent.parent.left) {
				Node y = z.parent.parent.right;
				if(y.color == Color.RED) {
					z.parent.color = Color.BLACK;
					y.color = Color.BLACK;
					z.parent.parent.color = Color.RED;
					z = z.parent.parent;
				}
				else {
					if(z == z.parent.right) {
						z = z.parent;
						leftRotate(z);			
					}
					z.parent.color = Color.BLACK;
					z.parent.parent.color  = Color.RED;
					rightRotate(z.parent.parent);
				}
			}
			else {
				Node y = z.parent.parent.left;
				if(y.color == Color.RED) {
					z.parent.color = Color.BLACK;
					y.color = Color.BLACK;
					z.parent.parent.color = Color.RED;
					z = z.parent.parent;
				}
				else {
					if(z == z.parent.left) {
						z = z.parent;
						rightRotate(z);			
					}
					z.parent.color = Color.BLACK;
					z.parent.parent.color  = Color.RED;
					leftRotate(z.parent.parent);
				}
			}
		}
		T_root.color = Color.BLACK;
	}

	// perform inorder traveral of the RB tree and prints
	// the nodes in sorted order
	static void sort(Node root)
	{
		Stack<Node> temp = new Stack<Node>();		
		if(root==T_null) {
			System.out.println("Empty tree");
			return;
		}
		Node current = root;

		while(!temp.empty()||current!=T_null)
		{
			if(current!=T_null) {
				temp.push(current);
				current = current.left;
			}
			else			{
				Node r = temp.pop();
				if(r!=T_null)				{
					System.out.println(r.data+" "+r.color);
				}
				current = r.right;
			}
		}
	}

	// return the max node value
	private static Node max(Node root)
	{
		if (root == T_null)	{
			System.out.println("Tree does not exist");
			return T_null;
		}

		while(root.right!=T_null)		{
			root = root.right;
		}		
		return root;
	}

	// returns the minimum node value
	private static Node min(Node root)
	{
		if (root == T_null)		{
			return T_null;
		}
		while(root.left!=T_null)
			root = root.left;

		return root;
	}

	// search for a given node
	private static Node search(Node root,int n)
	{
		Node current = root;
		if(current==T_null)
			return T_null;
		while(current!=T_null)
		{
			if(n==current.data) {
				return current;
			}
			if(n<current.data) {
				if(current.left!=T_null) {
					current = current.left;
				}
				else {
					return T_null;
				}
			}
			else {
				if(current.right!=T_null) {
					current = current.right;
				}
				else {
					return T_null;
				}	    			
			}
		}
		return T_null;
	}

	// returns the current height of the tree
	public static int checkHeight(Node root) {
		if (root == T_null) {
			return 0;
		}
		int leftHeight = checkHeight(root.left);		
		int rightHeight = checkHeight(root.right);
		return Math.max(leftHeight, rightHeight) + 1;		
	}

	// find and print the inorder successor of the given node
	private static void successor(Node n)
	{
		if(n==T_null) {
			System.out.println("Node is empty/null");
		}
		if(n.right!=T_null) {
			Node suc = min(n.right);
			if(suc!=T_null) {
				System.out.println("Sucessor is "+suc.data+" with color "+suc.color);
			}
			else {
				System.out.println("No successor");
			}
			return;
		}
		while(n.parent!=T_null &&n==n.parent.right)
		{
			n = n.parent;
			n.parent = n.parent.parent;
		}
		if(n.parent==T_null) {
			System.out.println("No successor");
		}
		else {
			System.out.println("Successor is "+n.parent.data+" with color "+n.parent.color);
		}
		return;
	}

	// find and print the inorder predecessor of given node
	private static void predecessor(Node n)
	{
		if(n==T_null) {
			System.out.println("Node is empty/null");
		}
		if(n.left!=T_null) {
			Node pred = min(n.left);
			if(pred!=T_null) {
				System.out.println("Predecessor is "+pred.data+" with color "+pred.color);
			}
			else
			{
				System.out.println("No predecessor");
			}
			return;
		}
		while(n.parent!=T_null &&n==n.parent.left)
		{
			n = n.parent;
			n.parent = n.parent.parent;
		}
		if(n.parent==T_null) {
			System.out.println("No predecessor");
		}
		else {
			System.out.println("Predecessor is "+n.parent.data+" with color "+n.parent.color);
		}
		return;
	}

	public static void main(String args[])
	{
		T_root = T_null;
		T_root.parent = T_null;
		redBlackTree redBlackTree = new redBlackTree();
		try{
			while(true)
			{
				System.out.println("**********************************************************");
				System.out.println("Enter the operation you want to perform on Red Black tree");
				System.out.println(" 1.insert\n 2.Search \n 3.Min\n 4.Max \n 5.sort\n 6.Successor \n 7.Predecessor \n 8.Exit");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				int option = Integer.parseInt(br.readLine());
				switch (option)
				{
				case 1:
				{
					System.out.println("Enter an integer value you want to insert");
					int n = Integer.parseInt(br.readLine());
					redBlackTree.RB_insert(createRedNode(n));
					System.out.println("Value inserted successfully");
					System.out.println("Current height of the tree is "+checkHeight(T_root));
					break;
				}
				case 2:
				{
					System.out.println("Enter an integer value you want to search");
					int n = Integer.parseInt(br.readLine());
					Node searchItem = search(T_root,n);
					if(searchItem == T_null)
						System.out.println("Value is not present ");
					else
					{
						System.out.println("The value "+searchItem.data+" is found with color "+searchItem.color);
					}
					break;       		
				}
				case 3:
				{
					Node minNode = min(T_root);
					if(minNode == T_null) {
						System.out.println("Tree is empty");
					}
					else {
						System.out.println("The minimum value is "+minNode.data+" "+minNode.color);
					}
					break;
				}
				case 4:
				{
					Node maxNode = max(T_root);
					if(maxNode == T_null) {
						System.out.println("Tree is empty");
					}
					else {
						System.out.println("The maximum value is "+maxNode.data+" "+maxNode.color);
					}
					break;
				}
				case 5:
				{
					sort(T_root);
					System.out.println("Current root node is "+T_root.data+" "+T_root.color);
					break;
				}
				case 6:
				{
					System.out.println("Enter the node whose successor you want to find");
					int n = Integer.parseInt(br.readLine());
					Node searchItem = search(T_root,n);
					if(searchItem != T_null)
						successor(searchItem);
					else
						System.out.println("Node not present in the tree");
					break;
				}
				case 7:
				{
					System.out.println("Enter the node whose predecessor you want to find");
					int n = Integer.parseInt(br.readLine());
					Node searchItem = search(T_root,n);
					if(searchItem != T_null)
						predecessor(searchItem);
					else
						System.out.println("Node not present in the tree");
					break;
				}
				case 8:
				{
					System.exit(0);
				}

				default:
				{
					System.out.println("Invalid option.Try again");
					break;
				}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
