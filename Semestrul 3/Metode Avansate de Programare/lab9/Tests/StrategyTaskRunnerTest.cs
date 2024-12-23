using Model;
using Decorator;
using Enum;

namespace Tests
{
    public class StrategyTaskRunnerTest
    {
        public static void RunStrategyTaskRunnerTest(Strategy strategy, MessageTask[] messageTasks)
        {
            var taskRunner = new StrategyTaskRunner(strategy);
            taskRunner.AddTask(messageTasks[0]);
            taskRunner.AddTask(messageTasks[1]);
            taskRunner.AddTask(messageTasks[2]);
            taskRunner.ExecuteAll();
        }
    }
}
