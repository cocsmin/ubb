using Model;

namespace Container
{
    public class StackContainer : AbstractContainer
    {
        public StackContainer()
        {
            taskuri = new Tasks[10];
            size = 0;
        }

        public override Tasks Remove()
        {
            if (!IsEmpty())
            {
                Tasks task = taskuri[--size];
                taskuri[size] = null;
                return task;
            }
            return null;
        }
    }
}
