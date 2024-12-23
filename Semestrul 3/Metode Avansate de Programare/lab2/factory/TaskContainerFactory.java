package sem1_2.factory;
import sem1_2.factory.Strategy;
import sem1_2.model.Container;
import sem1_2.model.Stack;
import sem1_2.model.Queue;
import java.awt.*;
import java.util.PriorityQueue;

public class TaskContainerFactory implements Factory {
    private TaskContainerFactory() {}

    private static final TaskContainerFactory instance = new TaskContainerFactory();

    public static TaskContainerFactory getInstance() {
        return instance;
    }

    @Override
    public Container createContainer(Strategy strategy){
        switch (strategy){
            case LIFO -> {return new Stack();}
            case FIFO -> {return new Queue();}
        }
        return null;
    }
}
