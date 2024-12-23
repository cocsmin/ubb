using System;

namespace Model
{
    public abstract class Tasks
    {
        private string id;
        private string description;

        public Tasks(string id, string description)
        {
            this.id = id;
            this.description = description;
        }

        public string Id
        {
            get { return id; }
            set { id = value; }
        }

        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        public abstract void Execute();

        public override string ToString()
        {
            return $"Task{{id='{id}', description='{description}'}}";
        }

        public override bool Equals(object obj)
        {
            if (this == obj) return true;
            if (obj == null || GetType() != obj.GetType()) return false;
            Tasks task = (Tasks)obj;
            return id == task.id && description == task.description;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(id, description);
        }
    }
}
