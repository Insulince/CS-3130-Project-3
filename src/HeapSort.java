
import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int currentCapacity = 100;
        int[] array = new int[currentCapacity];
        generate100More(array, currentCapacity);
        displayLatest100Elements(array, currentCapacity);
        buildHeap(array, currentCapacity);

        while (currentCapacity < 2000) {
            currentCapacity += 100;
            array = generate100More(array, currentCapacity);
            displayLatest100Elements(array, currentCapacity);
            buildHeap(array, currentCapacity);
        }

        heapSort(array, currentCapacity);
        displayArrayData(array);

        System.out.println(arrayIsSortedInAscendingOrder(array));
    }

    private static void buildHeap(final int[] array, final int currentCapacity) {
        boolean swapMade = true;

        while (swapMade) {
            swapMade = false;

            for (int i = 0; i < currentCapacity; i++) {
                int largest = i;
                if (2 * (i + 1) - 1 < currentCapacity && array[largest] < array[2 * (i + 1) - 1]) {
                    largest = 2 * (i + 1) - 1;
                }
                if (2 * (i + 1) < currentCapacity && array[largest] < array[2 * (i + 1)]) {
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

    private static void heapSort(final int[] array, int currentCapacity) {
        while (currentCapacity > 0) {
            int temp = array[0];
            array[0] = array[currentCapacity - 1];
            array[currentCapacity - 1] = temp;

            currentCapacity--;
            buildHeap(array, currentCapacity);
        }
    }

    private static int[] generate100More(int[] array, int currentCapacity) {
        if (currentCapacity > array.length) {
            System.out.println("Overflow detected! Doubling!");
            System.out.println("Size before doubling: " + array.length);
            array = Arrays.copyOf(array, array.length * 2);
            System.out.println("Size after doubling: " + array.length);
        }

        for (int i = currentCapacity - 100; i < currentCapacity; i++) {
            array[i] = (int) (1 + Math.random() * 2000);
        }
        return array;
    }

    private static void displayLatest100Elements(final int[] array, final int currentCapacity) {
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
                StringBuilder cellValueAsString = new StringBuilder(String.valueOf(array[(20 * rowNumber + columnNumber) + currentCapacity - 100]));
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
        for (int index = 1; index < 2000; index++) {
            if (array[index - 1] > array[index]) {
                return false;
            }
        }

        return true;
    }
}
