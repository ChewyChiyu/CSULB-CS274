/**
	@author Evan Chen
	Heap data structure which holds primitive data integer. 
	Heapify and Heap sort application used
*/
public class Heap{

	private int[] pile;

	/**
		@param pile: int[] of which to heapify
	*/
	public Heap(int[] pile){
		this.pile = pile;
	}

	/**	
		@return int[]: returns pile array
	*/
	public int[] getPile(){ return pile; }


	
	/**
		Prints heapify in user readable interphase
	*/
	public void verbose(){
		System.out.println("\n");
		String spaceBar = "                              ";
		String bar = "-------------------------------";
		int node = 1;
		System.out.println(spaceBar+pile[node++]+spaceBar);
		int layer = 1;
		final int MAX_LAYER = 4;
		while(layer<=MAX_LAYER){
			spaceBar = spaceBar.substring(0,spaceBar.length()/2);
			bar = bar.substring(0,bar.length()/2);
			for(int index = 0; index < Math.pow(2,layer-1); index++){
				System.out.print(spaceBar+pile[node++]+bar+"^"+bar+pile[node++]+spaceBar);
			}
			System.out.println();
			if(layer!=MAX_LAYER){
				for(int index = 0; index < Math.pow(2,layer-1); index++){
					System.out.print(spaceBar+"|"+spaceBar+"   "+spaceBar+"|"+spaceBar);
				}
			}
			System.out.println();
			layer++;
		}
		System.out.println("\n");
	}	

	/**
		launches protocal to turn pile into max heap
	*/
	public void toMaxHeap(){
		for(int last = pile.length/2-1; last >=1; last--){
			heapify(pile,pile.length,last);
			verbose();
		}
	}

	/**
		launches protocal to turn pile ( prefered in max heap ) into sorted array increasing
	*/
	public void heapSort(){
		int length = pile.length;
		for(int l = length-1; l >= 1; l--){
			int temp = pile[1];
			pile[1] = pile[l];
			pile[l] = temp;
			heapify(pile,l,1);
			verbose();
		}
	}	

	/**
		@param pile: int[] of heap
		@param size: size of said pile range
		@param index: int of current index
	*/

	public void heapify(int[] pile, int size, int index){
		int max = index;
		int left = 2*index;
		int right = 2*index+1;
		if(left < size && pile[left] > pile[max]){
			max = left;
		}
		if(right < size && pile[right] > pile[max]){
			max = right;
		}
		if(max!=index){
			int temp = pile[index];
			pile[index] = pile[max];
			pile[max] = temp;
			heapify(pile,size,max);
		}
	}	

	/**
		prints array starting from said index 1
	*/

	public void verboseArr(){
		for(int index = 1; index < pile.length; index++){
			System.out.print(pile[index] + " ");
		}
		System.out.println();
	}
}