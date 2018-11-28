
import java.util.*;
/**

	@author Evan Chen

	HeapMutator UI that creates and inputs user key strokes to
	control print steps of heapify and heap sort applications on heap
	data structure.
*/
public class HeapMutator{

	private Heap heap;

	/**
		Default constructor, creates heap and launches menu
	*/
	public HeapMutator(){
		heap = new Heap(randArray(32,10,99));
		heap.verbose();
		menu(new Scanner(System.in));
	}

	/**
		@param size: int of ar size
		@param min: int of min inclusive element
		@param max: int of max inclusive element
		@return int[]: int[] of size int with no repeats
	*/
	public int[] randArray(int size, int min, int max){
		int[] ar = new int[size];
		HashSet<Integer> dup = new HashSet<Integer>(); 
		int index = 1;
		while(index<ar.length){
			int rand = min + (int)(Math.random()*(max-min+1));
			if(!dup.contains(rand)){
				dup.add(rand);
				ar[index++] = rand;
			}
		}
		return ar;
	}

	/**
		@param scan: Scanner object to hold user input
	*/
	public void menu(Scanner scan){
		System.out.println(" - Please Press Any Key To Continue - ");
		scan.next();
		heap.toMaxHeap();
		System.out.println(" - Max Heapify Has Completed - ");
		System.out.println(" - Please Press Any Key To Continue - ");
		scan.next();
		heap.heapSort();
		heap.verboseArr();
		System.out.println(" - Heap Sort Has Completed - ");
		System.out.println(" - Please Press Any Key To Quit - ");
		scan.next();
	}


	public static void main(String[] args){
		new HeapMutator();
	}
}