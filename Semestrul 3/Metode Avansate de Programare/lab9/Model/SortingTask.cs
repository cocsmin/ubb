using System;
using System.Linq;
using Enum;

namespace Model
{
    public class SortingTask : Tasks
    {
        private AbstractSorter abstractSorter;
        private int[] numere;
        private SortStrategy sortStrategy;

        public SortingTask(string id_task, string description, int[] numere, SortStrategy sortStrategy)
            : base(id_task, description)
        {
            this.numere = numere;
            this.sortStrategy = sortStrategy;

            switch (sortStrategy)
            {
                case SortStrategy.BubbleSort:
                    abstractSorter = new BubbleSort();
                    break;
                case SortStrategy.QuickSort:
                    abstractSorter = new QuickSort();
                    break;
                default:
                    break;
            }
        }

        public override void Execute()
        {
            abstractSorter.Sort(numere);
            Console.WriteLine(string.Join(", ", numere));
        }
    }
}
