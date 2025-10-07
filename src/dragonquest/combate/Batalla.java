package dragonquest.combate;

import dragonquest.personajes.*;
import dragonquest.habilidades.*;
import java.util.*;

public class Batalla {
    private List<Personaje> heroes;
    private List<Personaje> enemigos;
    private Scanner sc;

    public Batalla(Scanner sc) {
        this.sc = sc;
        heroes = new ArrayList<>();
        enemigos = new ArrayList<>();
        inicializar();
    }

    private void inicializar() {
        Heroe heroe = new Heroe("Héroe", 100, 50, 20, 10, 15);
        heroe.agregarHabilidad(new DanioMagico("Bola de Fuego", 10, 30));
        heroe.agregarHabilidad(new Curacion("Curar", 8, 25));

        Heroe jessica = new Heroe("Jessica", 80, 60, 18, 8, 20);
        jessica.agregarHabilidad(new DanioMagico("Rayo", 12, 35));
        jessica.agregarHabilidad(new Veneno("Toxina", 8));

        Heroe angelo = new Heroe("Angelo", 90, 40, 22, 12, 18);
        angelo.agregarHabilidad(new Curacion("Bendición", 10, 30));
        angelo.agregarHabilidad(new Paralisis("Toque Sagrado", 10));

        heroes.add(heroe);
        heroes.add(jessica);
        heroes.add(angelo);

        Enemigo dragoon = new Enemigo("Dragoon", 110, 20, 25, 10, 17, "agresivo");
        Enemigo fantasma = new Enemigo("Fantasma", 85, 30, 18, 8, 21, "defensivo");

        enemigos.add(dragoon);
        enemigos.add(fantasma);
    }

    public void iniciarCombate() {
        System.out.println("=== ¡Comienza la batalla en el reino de Trodain! ===");


        while (true) {
            System.out.println("\n=== ESTADO ACTUAL DEL COMBATE ===");
            System.out.println("\n--- Héroes ---");
            for (Personaje h : heroes) {
                    h.mostrarEstado();
                }
            System.out.println("\n--- Enemigos ---");
            for (Personaje e : enemigos) {
                    e.mostrarEstado();
                }
            System.out.println("=================================\n");

            if (todosMuertos(enemigos)) {
                System.out.println("\n¡Victoria! Los héroes salvan el reino.");
                break;
            }
            if (todosMuertos(heroes)) {
                System.out.println("\nDerrota... El reino de Trodain cae en desgracia.");
                break;
            }

            List<Personaje> orden = new ArrayList<>();
            orden.addAll(heroes);
            orden.addAll(enemigos);
            orden.sort((a, b) -> b.getVelocidad() - a.getVelocidad());

            for (Personaje p : orden) {
                System.out.print("\033[H\033[2J");  // Limpiar consola en cada turno
                System.out.flush();  

                if (!p.estaVivo()) continue;
                if (p instanceof Heroe)
                    p.tomarTurno(heroes, enemigos, sc);
                else
                    p.tomarTurno(enemigos, heroes, sc);
            }
        }
    }

    private boolean todosMuertos(List<Personaje> lista) {
        for (Personaje p : lista) {
            if (p.estaVivo()) return false;
        }
        return true;
    }
}
