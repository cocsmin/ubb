package sem1_2.model;

import sem1_2.factory.SortingStrategy;

import java.util.Arrays;

public class SortingTask extends Task {
    private final SortingStrategy sortingStrategy;
    private AbstractSorter sorterAbstract;
    private final int[] lista;

    public SortingTask(String taskID, String descriere, int[] lista, SortingStrategy sortingStrategy) {
        super(taskID, descriere);
        this.lista = lista;
        this.sortingStrategy = sortingStrategy;

        switch (sortingStrategy) {
            case BUBBLESORT -> sorterAbstract = new BubbleSort();
            case QUICKSORT -> sorterAbstract = new QuickSort();
            default -> {}
        }
    }

    public void execute(){
        sorterAbstract.sort(lista);
        System.out.println(Arrays.toString(lista) + "\n");
    }

}
