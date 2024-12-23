package sem1_2.decorator;

import sem1_2.model.Task;

public class DelayTaskRunner extends AbstractTaskRunner {
    public DelayTaskRunner(TaskRunner taskR) {
        super(taskR);
    }

    @Override
    public void executeOneTask(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        super.executeOneTask();
    }
}
