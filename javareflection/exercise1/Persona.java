// Implementación de serialización sin reflexión

package javareflection.exercise1;
import java.util.HashMap;
import java.util.Map;

class Persona {
    private String ci;
    private String nombre;
    private String apellido;
    private int edad;

    public Persona(String ci, String nombre, String apellido, int edad) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public Map<String, Object> toMap() {
        // La longitud en líneas de código de este método depende del número
        // de atributos de la clase
        Map<String, Object> map = new HashMap<>();
        map.put("ci", this.ci);
        map.put("nombre", this.nombre);
        map.put("apellido", this.apellido);
        map.put("edad", this.edad);
        return map;
    }

    public static Persona fromMap(Map<String, Object> map) {
        // Lo mismo sucede aquí
        return new Persona(
                (String) map.get("ci"),
                (String) map.get("nombre"), 
                (String) map.get("apellido"),
                (int) map.get("edad"));
    }

    public void mostrar() {
        System.out.println("CI: " + ci + ", Nombre: " + nombre + ", Apellido: " + apellido +  ", Edad: " + edad);
    }

    public static void main(String[] args) {
        Persona p1 = new Persona("123", "Juan", "Perez", 30);
        Map<String, Object> dict = p1.toMap();
        System.out.println(dict);

        Persona p2 = Persona.fromMap(dict);
        p2.mostrar();
    }
}
