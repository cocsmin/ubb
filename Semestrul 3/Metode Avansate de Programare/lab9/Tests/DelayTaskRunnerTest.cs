using Enum;
using Model;
using Decorator;

namespace Tests
{
    public class DelayTaskRunnerTest
    {
        public static void RunDelayTaskRunnerTest(Strategy strategy, MessageTask[] messageTasks)
        {
            var strategyTaskRunner = new StrategyTaskRunner(strategy);
            var delayTaskRunner = new DelayTaskRunner(strategyTaskRunner);
            var printerTaskRunner = new PrinterTaskRunner(delayTaskRunner);
            printerTaskRunner.AddTask(messageTasks[0]);
            printerTaskRunner.AddTask(messageTasks[1]);
            printerTaskRunner.AddTask(messageTasks[2]);
            printerTaskRunner.ExecuteAll();
        }
    }
}
