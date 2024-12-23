namespace Model
{
    public class QuickSort : AbstractSorter
    {
        public override void Sort(int[] numere)
        {
            QuickSortMethod(numere, 0, numere.Length - 1);
        }

        private void QuickSortMethod(int[] numere, int low, int high)
        {
            if (low < high)
            {
                int index = Partition(numere, low, high);
                QuickSortMethod(numere, low, index - 1);
                QuickSortMethod(numere, index + 1, high);
            }
        }

        private int Partition(int[] numere, int low, int high)
        {
            int pivot = numere[high];
            int i = low - 1;
            for (int j = low; j < high; j++)
                if (numere[j] <= pivot)
                {
                    i++;
                    int temp = numere[i];
                    numere[i] = numere[j];
                    numere[j] = temp;
                }
            int temp2 = numere[i + 1];
            numere[i + 1] = numere[high];
            numere[high] = temp2;
            return i + 1;
        }
    }
}
