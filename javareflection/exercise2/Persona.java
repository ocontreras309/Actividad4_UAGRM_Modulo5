// Implementación de serialización con reflexión

package javareflection.exercise2;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

// Define a la clase como auto-serializable
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface AutoSerializable {}

class Serializer {
    public static Map<String, Object> toMap(Object obj) throws IllegalAccessException {
        // Converte el objeto en un Map
        // No requiere de mayores modificaciones

        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();

        if (!clazz.isAnnotationPresent(AutoSerializable.class)) {
            throw new RuntimeException("Clase no marcada como AutoSerializable");
        }

        for (Field campo : clazz.getDeclaredFields()) {
            campo.setAccessible(true);
            map.put(campo.getName(), campo.get(obj));
        }
        return map;
    }

    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) throws Exception {
        // Convierte un map en un objeto
        // Gracias a la reflexión, no requiere de mayores modificaciones
        // por más que se agreguen más campos a la clase anotada.

        if (!clazz.isAnnotationPresent(AutoSerializable.class)) {
            throw new RuntimeException("Clase no marcada como AutoSerializable");
        }

        T obj = clazz.getDeclaredConstructor().newInstance();
        for (Field campo : clazz.getDeclaredFields()) {
            campo.setAccessible(true);
            campo.set(obj, map.get(campo.getName()));
        }
        return obj;
    }
}

@AutoSerializable
class Persona {
    private String ci;
    private String nombre;
    private String apellido;
    private int edad;

    public Persona() {}

    public Persona(String ci, String nombre, String apellido, int edad) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public void mostrar() {
        System.out.println("CI: " + ci + ", Nombre: " + nombre + ", Apellido: " + apellido +  ", Edad: " + edad);
    }

    public static void main(String[] args) throws Exception {
        Persona p1 = new Persona("123", "Juan", "Perez", 30);
        Map<String, Object> dict = Serializer.toMap(p1);
        System.out.println(dict);

        Persona p2 = Serializer.fromMap(dict, Persona.class);
        p2.mostrar();
    }
}
