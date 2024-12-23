package sem1_2.model;

public class BubbleSort  extends AbstractSorter{
    @Override
    public void sort(int[] lista){
        boolean sortat = true;
        for (int i = 0; i < lista.length - 1; i++) {
            sortat = false;
            for (int j = 0; j < lista.length - i - 1; j++) {
                if (lista[j] > lista[j+1]) {
                    int temp = lista[j];
                    lista[j] = lista[j+1];
                    lista[j+1] = temp;
                    sortat = true;
                }
            }
            if (!sortat) {
                break;
            }
        }
    }

}
