using Model;
using System;

namespace Tests
{
    public class MessageTaskTest
    {
        public static MessageTask[] GetMessageTasks()
        {
            MessageTask task1 = new MessageTask(
                "1", "mesaj", "Buna", "Ana", "Dana",
                new DateTime(2024, 2, 7, 16, 20, 0));  // Use DateTime here
            MessageTask task2 = new MessageTask(
                "2", "mesaj", "Pa", "Anca", "Diana",
                new DateTime(2023, 3, 8, 17, 30, 0));
            MessageTask task3 = new MessageTask(
                "3", "mesaj", " La revedere", "Alexia", "Daria",
                new DateTime(2022, 4, 9, 18, 40, 0));
            MessageTask task4 = new MessageTask(
                "4", "mesaj", "Buna ziua", "Alessia", "Damaris",
                new DateTime(2021, 5, 10, 19, 50, 0));
            return new MessageTask[] { task1, task2, task3, task4 };
        }

        public static void TestMessageTask()
        {
            var messageTasks = GetMessageTasks();
            foreach (var messageTask in messageTasks)
            {
                messageTask.Execute();
            }
        }
    }
}
