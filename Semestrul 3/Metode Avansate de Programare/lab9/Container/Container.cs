using Model;

namespace Container
{
    public interface IContainer
    {
        Tasks Remove();
        void Add(Tasks task);
        int Size();
        bool IsEmpty();
    }
}
