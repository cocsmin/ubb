using System;

namespace Model
{
    public class MessageTask : Tasks
    {
        private string message;
        private string from;
        private string to;
        private DateTime date;
        private static readonly string dateFormat = "yyyy-MM-dd HH:mm";

        public MessageTask(string taskId, string description, string message, string from, string to, DateTime date)
            : base(taskId, description)
        {
            this.message = message;
            this.from = from;
            this.to = to;
            this.date = date;
        }

        public override void Execute()
        {
            Console.WriteLine($"{message} from: {from} to: {to} {date.ToString(dateFormat)}: ");
        }

        public override string ToString()
        {
            return $"id = {Id} | description = {Description} | message = {message} | from = {from} | to = {to} | date = {date.ToString(dateFormat)}";
        }
    }
}
