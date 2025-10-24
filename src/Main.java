/*No funciona Las opciones de subir y bajarle al sonido y efectos esto no tiene 
efecto alguno en la musica y sonido del juego pero si se puede interactuar con los botones
Tampoco tiene los 4 enemigos y debo cambiar los colores de los botones de iniciar salir y opciones para que sean mas visibles
Falta que me deje combatir ya que al inicio cuando preisono iniciar combate no me sale ningun error sin embargo no se muestran las opciones para atacar 
o usar habilidad y usar item y por ende tampoco el resto de opciones nevcesarias para que se desarrolle el combate correctamente
*/




import java.util.Scanner;
import combate.Batalla;

/**
 * Main - punto de entrada del programa.
 *
 * Responsabilidad:
 * - Mostrar un menú simple (Iniciar combate / Salir).
 * - Crear un único Scanner compartido para evitar cerrar System.in accidentalmente.
 * - Inicializar Batalla pasándole el Scanner para su uso durante todo el juego.
 *
 * Diseño:
 * - Mantener el control de flujo del programa aquí facilita reusar Batalla sin preocuparse
 *   por la apertura/cierre del flujo de entrada.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== DRAGON QUEST VIII - Simulación de Combate ===");
            System.out.println("1. Iniciar combate");
            System.out.println("2. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> {
                    // Se crea una instancia de Batalla pasando el scanner para que Batalla
                    // pueda leer elecciones de los héroes desde consola sin crear otro Scanner.
                    Batalla batalla = new Batalla(sc);
                    batalla.iniciarCombate();
                }
                case 2 -> System.out.println("¡Hasta luego, aventurero!");
                default -> System.out.println("Opción inválida. Intenta de nuevo.");
            }

        } while (opcion != 2);

        // Cerramos el scanner al final del programa (buenas prácticas).
        sc.close();
    }
}
