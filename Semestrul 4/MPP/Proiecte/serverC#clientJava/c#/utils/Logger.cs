using System;

namespace csharp_avalonia.utils;

public class Logger
{
    public void Info(string message)
    {
        var date = DateTime.Now;
        Console.WriteLine("{0} {1} {2} {3} TRACE - {4}", date.Year, date.Month, date.Day, date.TimeOfDay, message);
    }

    public void Error(string message)
    {
        var date = DateTime.Now;
        Console.WriteLine("{0} {1} {2} {3} ERROR - {4}", date.Year, date.Month, date.Day, date.TimeOfDay, message);
    }
}