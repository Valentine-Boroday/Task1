import java.util.ArrayList;

public interface QuickSort {

    static void sort (ArrayList<Integer> arrayList) {
        sortArray(arrayList, 0, arrayList.size()-1);
    }

    static void sortArray (ArrayList<Integer> arrayList, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int divideIndex = partition(arrayList, leftIndex, rightIndex);
            sortArray(arrayList, leftIndex, divideIndex - 1);
            sortArray(arrayList, divideIndex, rightIndex);
        }
    }

    static int partition (ArrayList<Integer> arrayList, int leftIndex, int rightIndex) {
        int pivotIndex = (leftIndex + rightIndex) / 2;
        int pivot = arrayList.get(pivotIndex);

        while (leftIndex <= rightIndex) {
            while (arrayList.get(leftIndex) < pivot) {
                leftIndex++;
            }
            while (arrayList.get(rightIndex) > pivot) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                swap(arrayList, leftIndex, rightIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    static void swap (ArrayList<Integer> arrayList, int index1, int index2) {
        int temp = arrayList.get(index1);
        arrayList.set(index1, arrayList.get(index2));
        arrayList.set(index2, temp);
    }
}
