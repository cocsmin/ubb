using Model;

namespace Container
{
    public class QueueContainer : AbstractContainer
    {
        public QueueContainer()
        {
            taskuri = new Tasks[10];
            size = 0;
        }

        public override Tasks Remove()
        {
            if (!IsEmpty())
            {
                Tasks task = taskuri[0];
                for (int i = 0; i < size - 1; i++)
                    taskuri[i] = taskuri[i + 1];
                taskuri[--size] = null;
                return task;
            }
            return null;
        }
    }
}
