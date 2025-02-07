// Implementación de serialización con reflexión

using System;
using System.Collections.Generic;
using System.Reflection;

[AttributeUsage(AttributeTargets.Class)]
public class AutoSerializable : Attribute {}

public static class Serializer {
    public static Dictionary<string, object> ToDictionary<T>(T obj) {
        Dictionary<string, object> dict = new Dictionary<string, object>();
        Type type = typeof(T);

        if (!Attribute.IsDefined(type, typeof(AutoSerializable))) {
            throw new Exception("Clase no marcada como AutoSerializable");
        }

        foreach (PropertyInfo prop in type.GetProperties()) {
            dict[prop.Name] = prop.GetValue(obj)!;
        }
        return dict;
    }

    public static T FromDictionary<T>(Dictionary<string, object> dict) where T : new() {
        T obj = new T();
        Type type = typeof(T);

        if (!Attribute.IsDefined(type, typeof(AutoSerializable))) {
            throw new Exception("Clase no marcada como AutoSerializable");
        }

        foreach (PropertyInfo prop in type.GetProperties()) {
            if (dict.ContainsKey(prop.Name)) {
                prop.SetValue(obj, Convert.ChangeType(dict[prop.Name], prop.PropertyType));
            }
        }
        return obj;
    }
}

[AutoSerializable]
public class Persona {
    public string? Ci { get; set; }
    public string? Nombre { get; set; }
    public string? Apellido { get; set; }
    public int Edad { get; set; }

    public Persona() {}

    public Persona(string ci, string nombre, string apellido, int edad) {
        Ci = ci;
        Nombre = nombre;
        Apellido = apellido;
        Edad = edad;
    }

    public void Mostrar() {
        Console.WriteLine($"Nombre: {Nombre}, Edad: {Edad}");
    }

    static void Main() {
        Persona p1 = new Persona("123", "Juan", "Perez", 30);
        var dict = Serializer.ToDictionary(p1);
        Console.WriteLine(string.Join(", ", dict));

        Persona p2 = Serializer.FromDictionary<Persona>(dict);
        p2.Mostrar();
    }
}
