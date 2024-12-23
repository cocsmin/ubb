using Model;

namespace Decorator
{
    public interface ITaskRunner
    {
        void ExecuteOneTask();
        void ExecuteAll();
        void AddTask(Tasks t);
        bool HasTask();
    }
}
