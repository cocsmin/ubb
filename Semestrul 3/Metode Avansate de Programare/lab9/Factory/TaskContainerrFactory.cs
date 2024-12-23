using Container;
using Enum;

namespace Factory
{
    public class TaskContainerFactory : IFactory
    {
        private TaskContainerFactory() { }

        private static TaskContainerFactory instance = new TaskContainerFactory();

        public static TaskContainerFactory GetInstance()
        {
            return instance;
        }

        public IContainer CreateContainer(Strategy strategy)
        {
            switch (strategy)
            {
                case Strategy.LIFO:
                    return new StackContainer();
                case Strategy.FIFO:
                    return new QueueContainer();
                default:
                    return null;
            }
        }
    }
}
