import java.util.Scanner;
import java.util.ArrayList;
/*
	@author Evan Chen
	This class is an sandbox that tests the algorthim "HappyNumber",
	the sum of the square of each respective digit returns Happy if all converge to 1 in
	recursive cycle, not Happy if repeats cycled nums.
*/
public class HappyNumber{


	//default constructor
	public HappyNumber(){
		happyCycle(query(),null);
		System.out.println();
	}

	/*
		@param int num: number to cycle for recursive
			   ArrayList<Integer> recurr: dp memo list for backtracking
		Print happy or not happy based on convergence to one or recall through recurr
		list.
	*/
	public void happyCycle(int num, ArrayList<Integer> recurr){
		System.out.print(num + ", ");
		if(num==1){
			System.out.print(" - Happy");
			return;
		}else if(recurr != null && recurr.contains(num)){
			System.out.print(" - Not Happy");
			return;
		}else{ 
			String[] ar = (""+num).split("");
			int next = 0;
			for(String s : ar){
				int i = Integer.parseInt(s);
				next+=(i*i);
			}
			if(recurr==null){
				recurr = new ArrayList<Integer>();
			}else{
				recurr.add(num);
			}
			happyCycle(next,recurr);
		}
	}


	/*
		@return int: the scan input of user
	*/
	public int query(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input a number.");
		try{
			int input = scan.nextInt();
			return input;
		}catch(Exception e){
			System.out.println("Invalid input.");
			return query();
		}
	}
	
	//JVM initializer
	public static void main(String[] args){
		new HappyNumber();
	}

}