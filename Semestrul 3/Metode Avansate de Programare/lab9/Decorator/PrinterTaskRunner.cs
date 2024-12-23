using System;

namespace Decorator
{
    public class PrinterTaskRunner : AbstractTaskRunner
    {
        public PrinterTaskRunner(ITaskRunner taskRunner)
            : base(taskRunner)
        {
        }

        public override void ExecuteOneTask()
        {
            base.ExecuteOneTask();
            Console.WriteLine($"Task: {DateTime.Now}");
        }
    }
}
