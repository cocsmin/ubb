namespace csharp_avalonia.domain;

public class Entity<T>
{
    protected T id;

    public T Id
    {
        get => id;
        set => id = value;
    }

    public override bool Equals(object obj)
    {
        if (ReferenceEquals(this, obj))
            return true;
        if (obj == null || GetType() != obj.GetType())
            return false;

        return Id != null && Id.Equals(((Entity<T>)obj).Id);
    }

    public override int GetHashCode()
    {
        return id?.GetHashCode() ?? 0;
    }

    public override string ToString()
    {
        return $"Entity{{id={id}}}";
    }
}