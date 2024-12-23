package sem1_2.teste;

import sem1_2.decorator.DelayTaskRunner;
import sem1_2.decorator.PrinterTaskRunner;
import sem1_2.decorator.StrategyTaskRunner;
import sem1_2.factory.Strategy;
import sem1_2.model.MessageTask;

public class DelayTaskRunnerTest
{
    public static void delayTaskRunnerTest(Strategy strategy, MessageTask[] messageTasks)
    {
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(strategy);
        DelayTaskRunner delayTaskRunner = new DelayTaskRunner(strategyTaskRunner);
        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(delayTaskRunner);
        printerTaskRunner.addTask(messageTasks[0]);
        printerTaskRunner.addTask(messageTasks[1]);
        printerTaskRunner.addTask(messageTasks[2]);
        printerTaskRunner.executeAll();
    }
}
