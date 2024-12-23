package sem1_2.model;

public class Stack  extends AbstractContainer{
    public Stack() {
        tasks = new Task[10];
        size = 0;
    }

    @Override
    public Task remove(){
        if (!isEmpty()){
            --size;
            return tasks[size];
        }
        return null;
    }
}
