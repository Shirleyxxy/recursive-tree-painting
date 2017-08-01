/**
 * Implements the binary search algorithm.
 * 
 * @author Xueying Xu (Shirley)
 * @version Assignment 3 (February 18, 2017) 
 **/

public class BinarySearch {
	
	/** 
	 * Searches the sorted array for the test int. 
	 * Assumes the array is sorted in increasing order (smallest at index 0).
	 * If test is found, returns its index; otherwise, returns -1.
	 **/
	public static int binarySearch( int[] sorted, int test ) {
    	
    	 // start the recursion between first and last indices
 		return binarySearch( sorted, test, 0, sorted.length -1 );
    	 
     }

	
	/** 
	 * Searches the sorted array for the test number between loIndex and hiIndex, inclusive. 
	 * Assumes the array is sorted in increasing order (smallest at index 0).
	 * If test is found, returns its index; otherwise, returns -1.
	 **/
    private static int binarySearch( int[] sorted, int test, int loIndex, int hiIndex ) {
    	 
    	// the test number is not in the sorted array
    	if (hiIndex < loIndex) {
    		 
    		 return -1;
    	 
    	 } else {
    		 
    		 int midIndex = (loIndex + hiIndex) / 2;
    		 
    		 if (sorted[midIndex] == test) {
    			
    			 return midIndex;
    		 
    		 // the test number is to the left
    		 } else if (sorted[midIndex] > test) {
    		
    			 // adjust the right bound
    			 return binarySearch(sorted, test, loIndex, midIndex-1);
    		 
    		 // the test number is to the right
    		 } else if (sorted[midIndex] < test) {
    		
    			 // adjust the left bound
    			 return binarySearch(sorted, test, midIndex+1, hiIndex);
    		 
    		 }
    		 
    		 return midIndex;
    		 
    	 }
    }

   
  public static void oddTester() {
	  
	  // Create an array of the first 100 odd numbers
	  int[] oddArray = new int[100];
	  
	  for (int i = 0; i < 100; i++) {
		  
		  oddArray[i] = 2 * i + 1;	  
	  }
	  
	  // Create an array of the tester values
	  int[] testArray = {26, 78, 100, 186, 13, 99, 101, 177};
	  
	  // Loop through the array of tester values 
	  for (int i = 0; i < testArray.length; i++) {
		  
		  int result = binarySearch( oddArray, testArray[i] );
		  
		  // Print out the result of searching
		  System.out.println("searching for " + testArray[i] + ": " + result);
		 		  
	  }
	  
  }
  
  public static void main(String[] args) {
	  
	  oddTester();
	  
  }
  
  
}

