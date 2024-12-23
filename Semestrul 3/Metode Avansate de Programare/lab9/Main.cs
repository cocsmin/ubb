using System;
using Enum;
using Model;
using Tests;

namespace lab9.Main
{
    public class Program
    {
        public static void Main(string[] args)
        {
            // Test MessageTask
            Console.WriteLine("Test MessageTask");
            MessageTaskTest.TestMessageTask();
            Console.WriteLine("Test success");
            Console.WriteLine("\n");

            // Test PrinterTaskRunner LIFO
            Console.WriteLine("Test PrinterTaskRunner LIFO");
            PrinterTaskRunnerTest.RunPrinterTaskRunnerTest(Strategy.LIFO, MessageTaskTest.GetMessageTasks());
            Console.WriteLine("Test success");
            Console.WriteLine("\n");

            // Test PrinterTaskRunner FIFO
            Console.WriteLine("Test PrinterTaskRunner FIFO");
            PrinterTaskRunnerTest.RunPrinterTaskRunnerTest(Strategy.FIFO, MessageTaskTest.GetMessageTasks());
            Console.WriteLine("Test success");
            Console.WriteLine("\n");

            // Test StrategyTaskRunner LIFO
            Console.WriteLine("Test StrategyTaskRunner LIFO");
            StrategyTaskRunnerTest.RunStrategyTaskRunnerTest(Strategy.LIFO, MessageTaskTest.GetMessageTasks());
            Console.WriteLine("Test success");
            Console.WriteLine("\n");

            // Test StrategyTaskRunner FIFO
            Console.WriteLine("Test StrategyTaskRunner FIFO");
            StrategyTaskRunnerTest.RunStrategyTaskRunnerTest(Strategy.FIFO, MessageTaskTest.GetMessageTasks());
            Console.WriteLine("Test success");
            Console.WriteLine("\n");

            // Test DelayTaskRunner LIFO
            Console.WriteLine("Test DelayTaskRunner LIFO");
            DelayTaskRunnerTest.RunDelayTaskRunnerTest(Strategy.LIFO, MessageTaskTest.GetMessageTasks());
            Console.WriteLine("Test success");
            Console.WriteLine("\n");

            // Test DelayTaskRunner FIFO
            Console.WriteLine("Test DelayTaskRunner FIFO");
            DelayTaskRunnerTest.RunDelayTaskRunnerTest(Strategy.FIFO, MessageTaskTest.GetMessageTasks());
            Console.WriteLine("Test success");
            Console.WriteLine("\n");

            // Test BubbleSort
            Console.WriteLine("Test bubblesort");
            Console.WriteLine("Inainte de sortat: {1,9,5}");
            SortingTask task1 = new SortingTask("1", "Buna", new int[] { 1, 9, 5 }, SortStrategy.BubbleSort);
            task1.Execute();
            Console.WriteLine("Test success");
            Console.WriteLine("\n");

            // Test QuickSort
            Console.WriteLine("Test quicksort");
            Console.WriteLine("Inainte de sortat: {10,7,4,1}");
            SortingTask task2 = new SortingTask("2", "Pa", new int[] { 10, 7, 4, 1 }, SortStrategy.QuickSort);
            task2.Execute();
            Console.WriteLine("Test success");
            Console.WriteLine("\n");
        }
    }
}
