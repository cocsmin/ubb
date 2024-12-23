package sem1_2;

import sem1_2.factory.*;
import sem1_2.model.*;
import sem1_2.teste.*;

public class main {
    public static void main(String[] args)
    {
        System.out.println("Test MessageTask");
        MessageTaskTest.testMessageTask();
        System.out.println("Test success");
        System.out.println("\n");

        System.out.println("Test PrinterTaskRunner LIFO");
        PrinterTaskRunnerTest.printerTaskRunnerTest(Strategy.LIFO, MessageTaskTest.getMessageTasks());
        System.out.println("Test success");
        System.out.println("\n");

        System.out.println("Test PrinterTaskRunner FIFO");
        PrinterTaskRunnerTest.printerTaskRunnerTest(Strategy.FIFO, MessageTaskTest.getMessageTasks());
        System.out.println("Test success");
        System.out.println("\n");

        System.out.println("Test StrategyTaskRunner LIFO");
        StrategyTaskRunnerTest.strategyTaskRunnerTest(Strategy.LIFO, MessageTaskTest.getMessageTasks());
        System.out.println("Test success");
        System.out.println("\n");

        System.out.println("Test StrategyTaskRunner FIFO");
        StrategyTaskRunnerTest.strategyTaskRunnerTest(Strategy.FIFO, MessageTaskTest.getMessageTasks());
        System.out.println("Test success");
        System.out.println("\n");

        System.out.println("Test DelayTaskRunner LIFO");
        DelayTaskRunnerTest.delayTaskRunnerTest(Strategy.LIFO, MessageTaskTest.getMessageTasks());
        System.out.println("Test success");
        System.out.println("\n");

        System.out.println("Test DelayTaskRunner FIFO");
        DelayTaskRunnerTest.delayTaskRunnerTest(Strategy.FIFO, MessageTaskTest.getMessageTasks());
        System.out.println("Test success");
        System.out.println("\n");

        System.out.println("Test bubblesort");
        System.out.println("Inainte de sortat: {1,9,5}");
        SortingTask task1 = new SortingTask("1", "Buna", new int[]{1, 9, 5}, SortingStrategy.BUBBLESORT);
        task1.execute();
        System.out.println("Test success");
        System.out.println("\n");

        System.out.println("Test quicksort");
        System.out.println("Inainte de sortat: {10,7,4,1}");
        SortingTask task2 = new SortingTask("2", "Pa", new int[]{10,7,4,1}, SortingStrategy.QUICKSORT);
        task2.execute();
        System.out.println("Test success");
        System.out.println("\n");
    }

}
