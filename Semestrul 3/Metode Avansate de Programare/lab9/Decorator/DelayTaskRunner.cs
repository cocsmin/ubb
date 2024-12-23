using System;
using System.Threading;

namespace Decorator
{
    public class DelayTaskRunner : AbstractTaskRunner
    {
        public DelayTaskRunner(ITaskRunner taskRunner)
            : base(taskRunner)
        {
        }

        public override void ExecuteOneTask()
        {
            base.ExecuteOneTask();
            try
            {
                Thread.Sleep(1500);
            }
            catch (ThreadInterruptedException e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
