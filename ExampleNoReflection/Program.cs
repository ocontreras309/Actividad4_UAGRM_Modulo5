// Implementación de serialización sin reflexión

using System;
using System.Collections.Generic;

class Persona {
    public string? Ci { get; set; }
    public string? Nombre { get; set; }
    public String? Apellido { get; set; }
    public int Edad { get; set; }

    public Persona(string ci, string nombre, string apellido, int edad) {
        Ci = ci;
        Nombre = nombre;
        Apellido = apellido;
        Edad = edad;
    }

    public Dictionary<string, object> ToDictionary() {
        return new Dictionary<string, object> {
            { "Ci", Ci! },
            { "Nombre", Nombre! },
            { "Apellido", Apellido! },
            { "Edad", Edad! }
        };
    }

    public static Persona FromDictionary(Dictionary<string, object> dict) {
        return new Persona(
            (string)dict["Ci"],
            (string)dict["Nombre"], 
            (string)dict["Apellido"], 
            (int)dict["Edad"]);
    }

    public void Mostrar() {
        Console.WriteLine($"Ci: {Ci}, Nombre: {Nombre}, Apellido: {Apellido} Edad: {Edad}");
    }

    static void Main() {
        Persona p1 = new Persona("123", "Juan", "Perez", 30);
        var dict = p1.ToDictionary();
        Console.WriteLine(string.Join(", ", dict));

        Persona p2 = Persona.FromDictionary(dict);
        p2.Mostrar();
    }
}
