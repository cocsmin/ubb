package sem1_2.teste;

import java.time.LocalDateTime;
import sem1_2.model.MessageTask;

public class MessageTaskTest
{
    public static MessageTask[] getMessageTasks()
    {
        MessageTask task1 =
                new MessageTask("1", "mesaj", "Buna", "Ana", "Dana",
                        LocalDateTime.of(2024,2,7,16,20));
        MessageTask task2 =
                new MessageTask("2", "mesaj", "Pa", "Anca", "Diana",
                        LocalDateTime.of(2023,3,8,17,30));
        MessageTask task3 =
                new MessageTask("3", "mesaj", " La revedere", "Alexia", "Daria",
                        LocalDateTime.of(2022,4,9,18,40));
        MessageTask task4 =
                new MessageTask("4", "mesaj", "Buna ziua", "Alessia", "Damaris",
                        LocalDateTime.of(2021,5,10,19,50));
        return new MessageTask[]{task1, task2, task3, task4};
    }

    public static void testMessageTask() {
        MessageTask[] messageTasks = getMessageTasks();
        for (MessageTask messageTask : messageTasks) {
            messageTask.execute();
        }
    }

}
