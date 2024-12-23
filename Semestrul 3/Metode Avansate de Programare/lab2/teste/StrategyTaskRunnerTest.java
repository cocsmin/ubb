package sem1_2.teste;

import sem1_2.model.MessageTask;
import sem1_2.decorator.StrategyTaskRunner;
import sem1_2.decorator.TaskRunner;
import sem1_2.factory.Strategy;


public class StrategyTaskRunnerTest
{
    public static void strategyTaskRunnerTest(Strategy strategy, MessageTask[] messageTasks)
    {
        TaskRunner taskRunner = new StrategyTaskRunner(strategy);
        taskRunner.addTask(messageTasks[0]);
        taskRunner.addTask(messageTasks[1]);
        taskRunner.addTask(messageTasks[2]);
        taskRunner.executeAll();
    }
}
