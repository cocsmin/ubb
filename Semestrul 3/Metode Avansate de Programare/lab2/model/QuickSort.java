package sem1_2.model;

public class QuickSort extends  AbstractSorter{
    private int partition(int[] numere, int start, int end) {
        int pivot = numere[end];
        int i = start - 1;

        for (int j = start; j < end; j++) {
            if (numere[j] <= pivot) {
                i++;
                int temp = numere[i];
                numere[i] = numere[j];
                numere[j] = temp;
            }
        }
        int temp = numere[i + 1];
        numere[i + 1] = numere[end];
        numere[end] = temp;

        return i + 1;
    }

    public void quickSort(int[] numere, int start, int end) {
        if (start < end) {
            int pivot = partition(numere, start, end);
            quickSort(numere, start, pivot - 1);
            quickSort(numere, pivot + 1, end);
        }
    }


    @Override
    public void sort(int[] lista) {
        quickSort(lista, 0, lista.length - 1);
    }
}
