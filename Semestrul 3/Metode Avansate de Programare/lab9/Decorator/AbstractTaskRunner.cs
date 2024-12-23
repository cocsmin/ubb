using Model;

namespace Decorator
{
    public abstract class AbstractTaskRunner : ITaskRunner
    {
        private ITaskRunner taskRunner;

        public AbstractTaskRunner(ITaskRunner taskRunner)
        {
            this.taskRunner = taskRunner;
        }

        public virtual void ExecuteOneTask()
        {
            taskRunner.ExecuteOneTask();
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
            taskRunner.AddTask(t);
        }

        public bool HasTask()
        {
            return taskRunner.HasTask();
        }
    }
}
