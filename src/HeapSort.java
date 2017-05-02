
import java.util.Arrays;

public class HeapSort {
    private static final int INITIAL_CAPACITY = 100;
    private static int currentlyOccupied = 0;

    public static void main(String[] args) {
        int[] array = new int[INITIAL_CAPACITY];
        System.out.println("Adding the initial 100 elements to the array...");
        generate100More(array);

        while (currentlyOccupied < 2000) {
            System.out.println("Adding another 100 elements to the array...");
            array = generate100More(array);
        }

        System.out.println("\nArray is now at full capacity (2000 elements), beginning Heap Sort on the data...");
        heapSort(array);
        System.out.println("Heap Sort complete. Resulting Array:");
        displayArrayData(array);
    }

    private static void buildHeap(final int[] array, final int heapCapacity) {
        boolean swapMade = true;

        while (swapMade) {
            swapMade = false;

            for (int i = 0; i < heapCapacity; i++) {
                int largest = i;
                if (2 * (i + 1) - 1 < heapCapacity && array[largest] < array[2 * (i + 1) - 1]) {
                    largest = 2 * (i + 1) - 1;
                }
                if (2 * (i + 1) < heapCapacity && array[largest] < array[2 * (i + 1)]) {
                    largest = 2 * (i + 1);
                }
                if (largest != i) {
                    int temp = array[i];
                    array[i] = array[largest];
                    array[largest] = temp;

                    swapMade = true;
                }
            }
        }
    }

    private static void heapSort(final int[] array) {
        int heapCapacity = currentlyOccupied;
        while (heapCapacity > 0) {
            int temp = array[0];
            array[0] = array[heapCapacity - 1];
            array[heapCapacity - 1] = temp;

            heapCapacity--;
            buildHeap(array, heapCapacity);
        }
    }

    private static int[] generate100More(int[] array) {
        int whereToStop = currentlyOccupied + 100;
        int[] latest100 = new int[100];
        for (int i = currentlyOccupied; i < whereToStop; i++) {
            if (currentlyOccupied >= array.length) {
                System.out.println("\nOverflow detected! Doubling.");
                System.out.println("Size before doubling: " + array.length);
                array = Arrays.copyOf(array, array.length * 2);
                System.out.println("Size after doubling: " + array.length + "\n");
            }

            array[i] = (int)
                    (1 + Math.random() * 2000);
            currentlyOccupied++;

            latest100[i - whereToStop + 100] = array[i];
            if (i + 1 == whereToStop) {
                System.out.println("These 100 elements were added to the array.");
                displayLatest100Elements(latest100);
            }

            buildHeap(array, currentlyOccupied);
        }
        return array;
    }

    private static void displayLatest100Elements(final int[] array) {
        StringBuilder output;
        int rowCounter = 0;
        output = new StringBuilder("================================================= ARRAY =================================================\n");
        output.append("INDEX {00} {01} {02} {03} {04} {05} {06} {07} {08} {09} {10} {11} {12} {13} {14} {15} {16} {17} {18} {19}\n");
        for (int rowNumber = 0; rowNumber < 5; rowNumber++) {
            StringBuilder rowCounterAsString = new StringBuilder(String.valueOf(rowCounter));
            int lengthOfRowCounterString = rowCounterAsString.length();
            for (int paddingCharacterIndex = 0; paddingCharacterIndex < "##".length() - lengthOfRowCounterString; paddingCharacterIndex++) {
                rowCounterAsString.insert(0, "0");
            }

            output.append("{").append(rowCounterAsString).append("}  ");
            rowCounter += 20;
            for (int columnNumber = 0; columnNumber < 20; columnNumber++) {
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

    private static void displayArrayData(final int[] array) {
        StringBuilder output;
        int rowCounter = 0;
        output = new StringBuilder("================================================= ARRAY ==================================================\n");
        output.append("INDEX  {00} {01} {02} {03} {04} {05} {06} {07} {08} {09} {10} {11} {12} {13} {14} {15} {16} {17} {18} {19}\n");
        for (int rowNumber = 0; rowNumber < 100; rowNumber++) {
            StringBuilder rowCounterAsString = new StringBuilder(String.valueOf(rowCounter));
            int lengthOfRowCounterString = rowCounterAsString.length();
            for (int paddingCharacterIndex = 0; paddingCharacterIndex < "####".length() - lengthOfRowCounterString; paddingCharacterIndex++) {
                rowCounterAsString.insert(0, "0");
            }

            output.append("{").append(rowCounterAsString).append("} ");
            rowCounter += 20;
            for (int columnNumber = 0; columnNumber < 20; columnNumber++) {
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

    private static boolean arrayIsSortedInAscendingOrder(final int[] array) {
        for (int index = 1; index < currentlyOccupied; index++) {
            if (array[index - 1] > array[index]) {
                return false;
            }
        }

        return true;
    }
}
