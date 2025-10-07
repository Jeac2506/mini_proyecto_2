package dragonquest;

import java.util.Scanner;
import dragonquest.combate.Batalla;

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
                    Batalla batalla = new Batalla(sc);
                    batalla.iniciarCombate();
                }
                case 2 -> System.out.println("¡Hasta luego, aventurero!");
                default -> System.out.println("Opción inválida. Intenta de nuevo.");
            }

        } while (opcion != 2);

        sc.close();
    }
}
