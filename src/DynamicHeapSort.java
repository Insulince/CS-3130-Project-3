// (Charles) Justin Reusnow • CS 3130 • Project 3

import java.util.Arrays;

public class DynamicHeapSort {
    private static final int INITIAL_CAPACITY = 100; //Starting size of the array.
    private static int currentCapacity = 0; //How many elements of the array are in use (always < or = to array.length). This is needed because we want to end up with a 2000 element array, however doubling an array of size 100 repeatedly never gets us to exactly 2000. So we end up with an array of length 3200, but the currentCapacity variable stops at 2000.

    //Main function.
    public static void main(String[] args) {
        int[] array = new int[INITIAL_CAPACITY]; //Create the array with the initial capacity.
        System.out.println("Adding the initial 100 elements to the array...");
        array = generate100More(array); //Generate the first 100 elements for the array.

        while (currentCapacity < 2000) { //While the array has less than 2000 elements in it (not size, but actually populated indices containing values between 1 and 2000 inclusive)...
            System.out.println("Adding another 100 elements to the array...");
            array = generate100More(array); //This array is not done being populated yet, so add another 100 elements.
        }

        System.out.println("\nArray is now at full capacity (2000 elements), beginning Heap Sort on the data...");
        heapSort(array); //This array is fully populated, now apply the heapsort on it.
        System.out.println("Heap Sort complete. Resulting Array:");
        displayArrayData(array); //Display the sorted array.
    }

    //This functions maintains a max heap inside the passed array.
    private static void buildOrRepairMaxHeap(final int[] array, final int heapCapacity) {
        boolean swapMade = true; //Assume a swap was made, we know at least one pass is required to determine if we have a max heap.

        while (swapMade) { //While a swap was made...
            swapMade = false; //Assume no swap will be made this pass.

            for (int currentIndex = 0; currentIndex < heapCapacity; currentIndex++) { //For every element in the array within the heap capacity (same concept as the global "currentCapacity" variable)...
                int largestIndex = currentIndex; //Assume the largest index is the current index.
                if (2 * (currentIndex + 1) - 1 < heapCapacity && array[largestIndex] < array[2 * (currentIndex + 1) - 1]) { //If the left node of the current node is in range and the current largest node's value is less than left node's value...
                    largestIndex = 2 * (currentIndex + 1) - 1; //The left node is the largest value.
                }
                if (2 * (currentIndex + 1) < heapCapacity && array[largestIndex] < array[2 * (currentIndex + 1)]) { //If the right node of the current node is in range and the current largest node's value is less than the right node's value...
                    largestIndex = 2 * (currentIndex + 1); //The right node is the largest value.
                }
                if (largestIndex != currentIndex) { //If the largest index found is not the current index (a child node is greater than the current node)...
                    int temp = array[currentIndex]; //Swap the value in the larger node with the current node.
                    array[currentIndex] = array[largestIndex];
                    array[largestIndex] = temp;

                    swapMade = true; //A swap was made, so we need to run through the array again to determine if we have a max heap.
                }
            }
        }

        //To get here, we must currently have a max heap in the variable "array".
    }

    //This function preforms the actual Heap Sort on the provided array that we assume is already a max heap.
    private static void heapSort(final int[] array) {
        int heapCapacity = currentCapacity; //Set the heap capacity to the current capacity of the array.
        while (heapCapacity > 0) { //While the size of the heap is greater than 0...
            int temp = array[0]; //Swap the first element and the last unsorted element.
            array[0] = array[heapCapacity - 1];
            array[heapCapacity - 1] = temp;

            heapCapacity--; //Shrink the size of the heap.
            buildOrRepairMaxHeap(array, heapCapacity); //Repair the heap.
        }

        //At this point, we should have a fully sorted array.
    }

    //This function is responsible for creating the 100 new integers and inserting them into the array, as well as checking for and fixing any array overflows.
    private static int[] generate100More(int[] array) {
        int whereToStop = currentCapacity + 100; //We will stop after adding 100 new integers.
        int[] latest100 = new int[100]; //Keep track of the 100 integers we are adding so we can display them. This step is required because every time we add a number we fix the heap, and this can push new numbers around which will make it harder to display them.
        for (int currentIndex = currentCapacity; currentIndex < whereToStop; currentIndex++) { //For every value between currentCapacity and currentCapacity + 100...
            if (currentCapacity >= array.length) { //If the current capacity is greater than or equal to the array's current length...
                System.out.println("\nOverflow detected! Doubling."); //Then we will have an overflow on this insertion.
                System.out.println("Size before doubling: " + array.length);
                array = Arrays.copyOf(array, array.length * 2); //Adjust the size of the array by copying it to a new array and doubling its size.
                System.out.println("Size after doubling: " + array.length + "\n");
            }

            array[currentIndex] = (int) (1 + Math.random() * 2000); //Add a new random integer between 1 and 2000 inclusive to the current index.
            currentCapacity++; //The current capacity has increased by 1.

            latest100[currentIndex - whereToStop + 100] = array[currentIndex]; //Record the new index in our latest100 array for later displaying.
            if (currentIndex + 1 == whereToStop) { //If this is the last loop through the array...
                System.out.println("These 100 elements were added to the array.");
                displayLatest100Elements(latest100); //Display the newly added elements.
            }

            buildOrRepairMaxHeap(array, currentCapacity); //A new number has been added which most likely broke the heap, so repair it.
        }

        return array;
    }

    //This function returns the 100 newly added elements.
    private static void displayLatest100Elements(final int[] array) {
        StringBuilder output;
        int rowCounter = 0;
        output = new StringBuilder("================================================= ARRAY =================================================\n");
        output.append("INDEX {00} {01} {02} {03} {04} {05} {06} {07} {08} {09} {10} {11} {12} {13} {14} {15} {16} {17} {18} {19}\n");
        for (int rowNumber = 0; rowNumber < 5; rowNumber++) { //For every row needed...
            StringBuilder rowCounterAsString = new StringBuilder(String.valueOf(rowCounter));
            int lengthOfRowCounterString = rowCounterAsString.length();
            for (int paddingCharacterIndex = 0; paddingCharacterIndex < "##".length() - lengthOfRowCounterString; paddingCharacterIndex++) {
                rowCounterAsString.insert(0, "0");
            }

            output.append("{").append(rowCounterAsString).append("}  ");
            rowCounter += 20;
            for (int columnNumber = 0; columnNumber < 20; columnNumber++) { //For every cell needed in this row...
                StringBuilder cellValueAsString = new StringBuilder(String.valueOf(array[(20 * rowNumber + columnNumber)]));
                int lengthOfCellValueString = cellValueAsString.length();
                for (int paddingCharacterIndex = 0; paddingCharacterIndex < "####".length() - lengthOfCellValueString; paddingCharacterIndex++) {
                    cellValueAsString.insert(0, " ");
                }

                output.append(cellValueAsString).append(" ");
            }

            output.append("\n");
        }
        output.append("=========================================================================================================");

        System.out.println(output.toString());
    }

    //This function returns the 2000 element array after the heap sort is complete. It operates exactly the same as the previous function, except with more elements.
    private static void displayArrayData(final int[] array) {
        StringBuilder output;
        int rowCounter = 0;
        output = new StringBuilder("================================================= ARRAY ==================================================\n");
        output.append("INDEX  {00} {01} {02} {03} {04} {05} {06} {07} {08} {09} {10} {11} {12} {13} {14} {15} {16} {17} {18} {19}\n");
        for (int rowNumber = 0; rowNumber < 100; rowNumber++) { //For every row needed...
            StringBuilder rowCounterAsString = new StringBuilder(String.valueOf(rowCounter));
            int lengthOfRowCounterString = rowCounterAsString.length();
            for (int paddingCharacterIndex = 0; paddingCharacterIndex < "####".length() - lengthOfRowCounterString; paddingCharacterIndex++) {
                rowCounterAsString.insert(0, "0");
            }

            output.append("{").append(rowCounterAsString).append("} ");
            rowCounter += 20;
            for (int columnNumber = 0; columnNumber < 20; columnNumber++) { //For every cell needed in this row...
                StringBuilder cellValueAsString = new StringBuilder(String.valueOf(array[20 * rowNumber + columnNumber]));
                int lengthOfCellValueString = cellValueAsString.length();
                for (int paddingCharacterIndex = 0; paddingCharacterIndex < "####".length() - lengthOfCellValueString; paddingCharacterIndex++) {
                    cellValueAsString.insert(0, " ");
                }

                output.append(cellValueAsString).append(" ");
            }

            output.append("\n");
        }
        output.append("==========================================================================================================");

        System.out.println(output.toString());
    }
}
