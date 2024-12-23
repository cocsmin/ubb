using Model;
using Decorator;
using Enum;

namespace Tests
{
    public class PrinterTaskRunnerTest
    {
        public static void RunPrinterTaskRunnerTest(Strategy strategy, MessageTask[] messageTasks)
        {
            var strategyTaskRunner = new StrategyTaskRunner(strategy);
            var printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);
            printerTaskRunner.AddTask(messageTasks[0]);
            printerTaskRunner.AddTask(messageTasks[1]);
            printerTaskRunner.AddTask(messageTasks[2]);
            printerTaskRunner.ExecuteAll();
        }
    }
}
