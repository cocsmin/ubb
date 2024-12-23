using Container;
using Enum;

namespace Factory
{
    public interface IFactory
    {
        IContainer CreateContainer(Strategy strategy);
    }
}
