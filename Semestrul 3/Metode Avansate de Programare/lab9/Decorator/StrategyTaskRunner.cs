using Container;
using Model;
using Factory;
using Enum;

namespace Decorator
{
    public class StrategyTaskRunner : ITaskRunner
    {
        private IContainer container;

        public StrategyTaskRunner(Strategy strategy)
        {
            this.container = TaskContainerFactory.GetInstance().CreateContainer(strategy);
        }

        public void ExecuteOneTask()
        {
            Tasks task = container.Remove();
            if (task != null)
                task.Execute();
        }

        public void ExecuteAll()
        {
            while (HasTask())
            {
                ExecuteOneTask();
            }
        }

        public void AddTask(Tasks t)
        {
            container.Add(t);
        }

        public bool HasTask()
        {
            return !container.IsEmpty();
        }
    }
}
