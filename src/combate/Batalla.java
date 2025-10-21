package combate;

import personajes.*;
import items.*;
import java.util.*;

// Importaciones explícitas para asegurar que todas las habilidades estén disponibles
import habilidades.Aturdimiento; 
import habilidades.DanioMagico;
import habilidades.Curacion;
import habilidades.GolpeCritico;
import habilidades.Veneno;
import habilidades.Dormir;
import habilidades.Paralisis;
import habilidades.CuracionGrupal;
import habilidades.RemoverEstado;

/**
 * Batalla - controla el flujo de combate por turnos.
 *
 * Contiene:
 * - Listas de héroes y enemigos.
 * - Inventario compartido del grupo (InventarioGrupo).
 * - Lógica para inicializar personajes y poblar el inventario.
 * - Bucle principal: mostrar estado, ordenar por velocidad, ejecutar turnos.
 *
 * Diseño: Batalla recibe Scanner desde Main para manejar entradas del jugador.
 */
public class Batalla {
    private List<Personaje> heroes;
    private List<Personaje> enemigos;
    private Scanner sc;
    private InventarioGrupo inventario; // inventario compartido por el grupo

    public Batalla(Scanner sc) {
        this.sc = sc;
        heroes = new ArrayList<>();
        enemigos = new ArrayList<>();
        inventario = new InventarioGrupo(); // inventario global para la batalla
        inicializar();
    }

    /**
     * Inicializa personajes, habilidades y el inventario compartido.
     * - Aquí defines la composición inicial del grupo y los enemigos.
     * - Puedes ajustar cantidades y tipos según requerimiento.
     */
    private void inicializar() {
        // ========================================
        // HÉROES (4 aventureros de Dragon Quest VIII)
        // ========================================
        
        // Héroe - Protagonista equilibrado con magia y fuerza
        Heroe heroe = new Heroe("Héroe", 100, 50, 20, 10, 15);
        heroe.agregarHabilidad(new DanioMagico("Bola de Fuego", 10, 30));
        heroe.agregarHabilidad(new Curacion("Curar", 8, 25));
        heroe.agregarHabilidad(new DanioMagico("Gigaslash", 15, 40));
        
        // Yangus - Tanque del grupo, alto HP y ataque físico
        Heroe yangus = new Heroe("Yangus", 120, 30, 25, 15, 10);
        yangus.agregarHabilidad(new GolpeCritico("Hachazo Brutal", 5));
        yangus.agregarHabilidad(new Aturdimiento("Golpe Aturdidor", 8));
        yangus.agregarHabilidad(new Curacion("Primeros Auxilios", 10, 20));

        // Jessica - Maga con alto MP y habilidades mágicas devastadoras
        Heroe jessica = new Heroe("Jessica", 80, 70, 18, 8, 20);
        jessica.agregarHabilidad(new DanioMagico("Rayo", 12, 35));
        jessica.agregarHabilidad(new Veneno("Toxina", 8));
        jessica.agregarHabilidad(new DanioMagico("Kafrizzle", 20, 50));
        jessica.agregarHabilidad(new Dormir("Hipnosis", 10));

        // Angelo - Sanador y soporte del grupo
        Heroe angelo = new Heroe("Angelo", 90, 60, 22, 12, 18);
        angelo.agregarHabilidad(new Curacion("Bendición", 10, 30));
        angelo.agregarHabilidad(new Paralisis("Toque Sagrado", 10));
        angelo.agregarHabilidad(new CuracionGrupal("Curación Divina", 20, 25));
        angelo.agregarHabilidad(new RemoverEstado("Purificación", 8));

        heroes.add(heroe);
        heroes.add(yangus);
        heroes.add(jessica);
        heroes.add(angelo);

        // ========================================
        // ENEMIGOS (4 monstruos clásicos de Dragon Quest)
        // ========================================
        
        // Dragoon - Dragón agresivo con alto ataque
        Enemigo dragoon = new Enemigo("Dragoon", 110, 20, 25, 10, 17, "agresivo");
        dragoon.agregarHabilidad(new DanioMagico("Aliento de Fuego", 0, 25));
        
        // Fantasma - Enemigo evasivo que puede aplicar estados
        Enemigo fantasma = new Enemigo("Fantasma", 85, 40, 18, 8, 21, "estratégico");
        fantasma.agregarHabilidad(new Dormir("Pesadilla", 5));
        fantasma.agregarHabilidad(new Paralisis("Toque Espectral", 5));
        
        // Golem de Piedra - Tanque enemigo con alta defensa
        Enemigo golem = new Enemigo("Golem de Piedra", 150, 10, 20, 18, 5, "defensivo");
        golem.agregarHabilidad(new Aturdimiento("Puño de Roca", 0));
        
        // Slime Metálico - Enemigo rápido y escurridizo con defensa extrema
        Enemigo slimeMetalico = new Enemigo("Slime Metálico", 50, 30, 15, 25, 30, "evasivo");
        slimeMetalico.agregarHabilidad(new Veneno("Baba Tóxica", 3));

        enemigos.add(dragoon);
        enemigos.add(fantasma);
        enemigos.add(golem);
        enemigos.add(slimeMetalico);

        // ========================================
        // INVENTARIO COMPARTIDO
        // ========================================
        inventario.agregarItem(new PocionCuracion("Poción pequeña", 30), 5); // 5 pociones pequeñas
        inventario.agregarItem(new PocionCuracion("Poción media", 60), 3);   // 3 pociones medias
        inventario.agregarItem(new PocionCuracion("Poción grande", 100), 1); // 1 poción grande
        inventario.agregarItem(new Antidoto("Antídoto"), 3);                 // 3 antídotos
        inventario.agregarItem(new PocionMagia("Éter", 20), 2);             // 2 éteres para restaurar MP
    }

    /**
     * Bucle principal de la batalla:
     * - Muestra el estado del combate.
     * - Verifica condiciones de victoria/derrota.
     * - Ordena por velocidad y ejecuta turnos.
     * - Pasa el inventario compartido a los héroes cuando estos eligen usar ítem.
     */
    public void iniciarCombate() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║          ¡COMIENZA LA BATALLA EN EL REINO DE TRODAIN!           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println("\nLos cuatro héroes se preparan para enfrentar a las fuerzas del mal...\n");

        int turno = 1;
        while (true) {
            // Mostrar estado visual de todos los participantes
            System.out.println("\n┌─────────────────────────────────────────────────────────────────┐");
            System.out.println("│                    TURNO " + turno + " - ESTADO DEL COMBATE                │");
            System.out.println("└─────────────────────────────────────────────────────────────────┘");
            
            System.out.println("\n▸ HÉROES:");
            for (Personaje h : heroes) h.mostrarEstado();
            
            System.out.println("\n▸ ENEMIGOS:");
            for (Personaje e : enemigos) e.mostrarEstado();
            System.out.println("═══════════════════════════════════════════════════════════════════\n");

            // Comprobar final de combate
            if (todosMuertos(enemigos)) {
                System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
                System.out.println("║                           ¡VICTORIA!                             ║");
                System.out.println("║        Los héroes han salvado el reino de Trodain.              ║");
                System.out.println("╚══════════════════════════════════════════════════════════════════╝");
                break;
            }
            if (todosMuertos(heroes)) {
                System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
                System.out.println("║                           DERROTA...                             ║");
                System.out.println("║       El reino de Trodain cae ante las fuerzas del mal.         ║");
                System.out.println("╚══════════════════════════════════════════════════════════════════╝");
                break;
            }

            // Construir lista de turno y ordenar por velocidad (descendente).
            // Empates: favorecemos héroes para que actúen antes en caso de empate.
            List<Personaje> orden = new ArrayList<>();
            orden.addAll(heroes);
            orden.addAll(enemigos);
            orden.sort((a, b) -> {
                if (b.getVelocidad() != a.getVelocidad()) return b.getVelocidad() - a.getVelocidad();
                if (a instanceof Heroe && b instanceof Enemigo) return -1;
                if (a instanceof Enemigo && b instanceof Heroe) return 1;
                return 0;
            });

            // Ejecutar turnos en orden
            for (Personaje p : orden) {
                if (!p.estaVivo()) continue;

                if (p instanceof Heroe) {
                    // Pasamos también el inventario para que el héroe pueda usar ítems del grupo.
                    ((Heroe) p).tomarTurno(heroes, enemigos, sc, inventario);
                } else {
                    // Enemigos reciben (aliados, enemigos) = (enemigos, heroes)
                    p.tomarTurno(enemigos, heroes, sc);
                }
                
                // Verificar si el combate terminó durante el turno
                if (todosMuertos(enemigos) || todosMuertos(heroes)) break;
            }
            
            turno++;
        }
    }

    /** Verifica si todos en la lista están muertos. */
    private boolean todosMuertos(List<Personaje> lista) {
        for (Personaje p : lista) {
            if (p.estaVivo()) return false;
        }
        return true;
    }
}