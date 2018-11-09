import java.util.ArrayList;
/**
	@author Evan Chen
	This is ADT Binary Tree class containg integer primitives, balancing feature included
*/
public class BinaryTree{

	public Node root;

	/**
		@param root: int of root val
	*/
	public BinaryTree(int root){
		this.root = new Node(root);
	}
	/**
		@param root: node of root val
	*/
	public BinaryTree(Node root){
		this.root = root;
	}

	/**
		@param i: int of next insert
	*/
	public void insert(int i){
		boolean next = true;
		Node a = new Node(i);
		Node b = root;
		while(next){
			if(a.val>=b.val){
				if(b.right==null){
					b.right = a;
					a.parent = b;
					next = false;
				}
				else{
					b = b.right;
				}
			}else if(a.val<b.val){
				if(b.left==null){
					b.left = a;
					a.parent = b;
					next = false;
				}
				else{
					b = b.left;
				}
			}
		}
	}

	/**
		@param root: int of next deletion val
	*/
	public void delete(int i){

		boolean next = true;
		Node b = root;

		if(b.val==i){
			root = null;
			System.out.println(" - REMOVAL SUCCESSFUL - ");
			return;
		}

		while(next){
			if(b.val==i){
				if(b.parent.left!=null&&b.parent.left.val==i){
					b.parent.left = null;
				}else{
					b.parent.right = null;
				}
				
				System.out.println(" - REMOVAL SUCCESSFUL - ");
				next = false;
			}else{
				if(i>=b.val){
					b = b.right;
				}else if(i<b.val){
					b = b.left;
				}
				if(b==null){
					next = false;
					System.out.println(" - ELEMENT NOT FOUND - ");
				}
			}
			
		}
	}

	/**
		@param n: Node of which to start num element branch count
		@return int: number of nodes in tree from root n
	*/
	public int numElements(Node n){
		if(n.right==null&&n.left==null){
			return 1;
		}else if(n.right==null){
			return 1 + numElements(n.left);
		}else if(n.left==null){
			return 1 + numElements(n.right);

		}else{
			return 2 + numElements(n.right) + numElements(n.left);
		}
	}

	/**
		Balance tree using rotations
	*/
	public void balance(){
		//find bottom node
		while(Math.abs(lHeight(root)-rHeight(root))>1){
			cycleRotations(root);
		}
	}

	/**
		@param a: rotate based on last node a
	*/
	private void cycleRotations(Node a){
		Node c = a;
		while(!c.equals(root)){
			applyRotation(c);
			c = c.parent;
		}
		applyRotation(c);
	}

	/**
		@param c: apply rotation on node c as root
	*/
	private void applyRotation(Node c){
		if(Math.abs(lHeight(c)-rHeight(c))>1){ // rotation toogle
				if(rHeight(c)>lHeight(c)){ // R
					if(rHeight(c.right)>lHeight(c.right)){ // RR
						lRotate(c);
					}else{ // RL
						rRotate(c.right);
						lRotate(c);
					}
				}else{ // L
					if(lHeight(c.left)>rHeight(c.left)){ // LL
						rRotate(c);
					}else{
						lRotate(c.left);
						rRotate(c);
					}
				}
			}
	}

	

	/**
		@param i: what level the node is current on
	*/
	public int atLevel(int i){
		boolean next = true;
		Node b = root;
		int level = 0;
		while(next&&b!=null){
			if(b.val == i){
				return level;
			}
			if(i>=b.val){
				b = b.right;
			}else if(i<b.val){
				b = b.left;
			}
			level++;
		}
		return -1;
	}

	/**
		@param n: rotate n node left
	*/
	private void lRotate(Node n){
		//figure out if root is left or right of parent
		if(n.parent!=null){
			if(n.parent.right!=null&&n.parent.right.equals(n)){
				n.parent.right = n.right;
				n.right = n.right.left;
				n.parent.right.left = n;
			}else if(n.parent.left!=null&&n.parent.left.equals(n)){
				n.parent.left = n.right;
				n.right = n.right.left;
				n.parent.left.left = n;
			}
		}else{ // n = root
			Node a = new Node(-1); // turn node
			a.right = n;
			n.parent = a;
			n.parent.right = n.right;
			n.right = n.right.left;
			n.parent.right.left = n;
			n = a.right;
			n.parent = null;
			root = n;
			root.left.parent = root;
		}
	}

	/**
		@param n: rotate n node right
	*/
	private void rRotate(Node n){
		//figure out if root is left or right of parent
		if(n.parent!=null){
			if(n.parent.right!=null&&n.parent.right.equals(n)){
				n.parent.right = n.left;
				n.left = n.left.right;
				n.parent.right.right = n;
			}else if(n.parent.left!=null&&n.parent.left.equals(n)){
				n.parent.left = n.left;
				n.left = n.left.right;
				n.parent.left.right = n;
			}
		}else{ // n = root
			Node a = new Node(-1); // turn node
			a.right = n;
			n.parent = a;
			n.parent.left = n.left;
			n.left = n.left.right;
			n.parent.left.right = n;
			n = a.left;
			n.parent = null;
			root = n;
			root.right.parent = root;
		}
	}

	/**
		@param n: print elements of n root in order ascending
	*/
	public void verbose(Node n){
		if(n.right==null&&n.left==null){
			System.out.println(n);
			return;
		}else if(n.right==null){
			System.out.println(n);
			verbose(n.left);
		}else if(n.left==null){
			verbose(n.right);
			System.out.println(n);

		}else{
			verbose(n.right);
			System.out.println(n);
			verbose(n.left);
		}
	}

	/**
		@param n: right height of branch
		@return int: height of branch r
	*/
	private int rHeight(Node n){
		if(n.right==null){
			return 1;
		}
		return Math.max(rHeight(n.right),lHeight(n.right)) + 1;
	}

	/**
		@param n: left height of branch
		@return int: height of branch l
	*/
	private int lHeight(Node n){
		if(n.left==null){
			return 1;
		}
		return Math.max(rHeight(n.left),lHeight(n.left)) + 1;
	}

	/**
		
		@return int: height root
	*/
	public int getHeight(){
		return Math.max(rHeight(root),lHeight(root));
	}

}
/**
	This class is the node class for the binary tree
*/
class Node{
	public Node left, right, parent;
	public int val;
	public Node(int val){
		this.val = val;
	}
	public String toString(){
		return ""+val;
	}
}