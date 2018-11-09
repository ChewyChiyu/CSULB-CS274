import java.util.Scanner;

/**
	@author Evan Chen
	UI class for balancing binary trees
*/
public class TreeUI{
	

	private BinaryTree bt = null;

	public TreeUI(){
		menu();
	}

	public void menu(){
		System.out.println("1. Add item to tree \n2. Delete item from tree \n3. Find item \n4. Balance Tree \n5. List contents of tree \n6. Display Statistics \n7. Quit");
		switch(query(1,7)){
			case 1:
				System.out.println(" - Input integer to put into tree - ");
				if(bt == null || bt.root == null) {
					bt = new BinaryTree(query(Integer.MIN_VALUE,Integer.MAX_VALUE));
				}else{
					bt.insert(query(Integer.MIN_VALUE,Integer.MAX_VALUE));
				}
			break;
			case 2:
				System.out.println(" - Delete integer from tree - ");
				if(bt == null || bt.root == null) {
					System.out.println(" - Tree is Empty - ");
				}else{
					bt.delete(query(Integer.MIN_VALUE,Integer.MAX_VALUE));
				}
			break;
			case 3:
				System.out.println(" - Find item level in tree - ");
				if(bt == null || bt.root == null) {
					System.out.println(" - Tree is Empty - ");
				}else{
					int level = bt.atLevel(query(Integer.MIN_VALUE,Integer.MAX_VALUE));
					System.out.println("Node at level " + level);
				}
			break;
			case 4:
				System.out.println(" - Balancing tree - ");
				if(bt == null || bt.root == null)  {
					System.out.println(" - Tree is Empty - ");
				}else{
					bt.balance();
				}
			break;
			case 5:
				System.out.println(" - Printing tree - ");
				if(bt == null || bt.root == null)  {
					System.out.println(" - Tree is Empty - ");
				}else{
					bt.verbose(bt.root);
				}
			break;
			case 6:
				System.out.println(" - Printing Statistics - ");
				if(bt == null || bt.root == null) {
					System.out.println(" - Tree is Empty - ");
				}else{
					System.out.println(" - Root: " + bt.root);
					System.out.println(" - Depth: " + bt.getHeight());
					System.out.println(" - Items: " + bt.numElements(bt.root));
				}
				
			break;
			case 7:
				System.exit(0);
			break;
		}
		menu();
	}


	/**	
		@param min, max: ints of lower and upper bounds inclusive
		@return int: user query
	*/
	public int query(int min, int max){
		Scanner scan = new Scanner(System.in);
		try{
			int i = scan.nextInt();
			if(i<min||i>max){
				System.out.println(" - Invalid Input - ");
				return query(min,max);
			}else{
				return i;
			}
		}catch(Exception e){
			System.out.println(" - Invalid Input - ");
			return query(min,max);
		}
	}

	public static void main(String[] args){
		new TreeUI();
	}
}