using Model;

namespace Container
{
    public abstract class AbstractContainer : IContainer
    {
        protected Tasks[] taskuri;
        protected int size;

        public void Add(Tasks task)
        {
            if (taskuri.Length == size)
            {
                Tasks[] t = new Tasks[taskuri.Length * 2];
                System.Array.Copy(taskuri, 0, t, 0, taskuri.Length);
                taskuri = t;
            }
            taskuri[size++] = task;
        }

        public int Size()
        {
            return size;
        }

        public bool IsEmpty()
        {
            return size == 0;
        }

        public abstract Tasks Remove();
    }
}
