package dragonquest;

import java.util.*;
import dragonquest.personajes.*;
import dragonquest.habilidades.*;

public class DragonQuestVIII {
    private List<Personaje> heroes;
    private List<Personaje> enemigos;
    private Scanner sc;

    public DragonQuestVIII() {
        sc = new Scanner(System.in);
        heroes = new ArrayList<>();
        enemigos = new ArrayList<>();
        inicializarPersonajes();
    }

    private void inicializarPersonajes() {
        // Crear héroes con atributos y habilidades predefinidas
        Heroe heroe = new Heroe("Héroe", 100, 50, 20, 10, 15);
        heroe.habilidades.add(new DanioMagico("Bola de Fuego", 10, 30));
        heroe.habilidades.add(new Curacion("Curar", 8, 25));

        Heroe yangus = new Heroe("Yangus", 120, 30, 25, 15, 10);
        yangus.habilidades.add(new DanioMagico("Golpe Fuerte", 5, 20));

        Heroe jessica = new Heroe("Jessica", 80, 60, 18, 8, 20);
        jessica.habilidades.add(new DanioMagico("Rayo", 12, 35));
        jessica.habilidades.add(new Curacion("Curar", 8, 25));

        Heroe angelo = new Heroe("Angelo", 90, 40, 22, 12, 18);
        angelo.habilidades.add(new DanioMagico("Bendición", 10, 0)); // Podría ser buff, no implementado

        heroes.add(heroe);
        heroes.add(yangus);
        heroes.add(jessica);
        heroes.add(angelo);

        // Crear enemigos con atributos y comportamientos
        Enemigo slime = new Enemigo("Slime", 50, 0, 10, 5, 12, "agresivo");
        Enemigo golem = new Enemigo("Golem", 150, 0, 15, 20, 5, "defensivo");
        Enemigo dragoon = new Enemigo("Dragoon", 100, 20, 25, 10, 17, "agresivo");
        Enemigo fantasma = new Enemigo("Fantasma", 80, 30, 20, 8, 22, "agresivo");

        enemigos.add(slime);
        enemigos.add(golem);
        enemigos.add(dragoon);
        enemigos.add(fantasma);
    }

    public void iniciarCombate() {
        System.out.println("¡Comienza la batalla en el reino de Trodain!");

        while (true) {
            List<Personaje> turnoOrden = new ArrayList<>();
            turnoOrden.addAll(heroes);
            turnoOrden.addAll(enemigos);

            // Ordenar por velocidad descendente, si empate, héroes primero
            turnoOrden.sort((p1, p2) -> {
                if (p2.getVelocidad() != p1.getVelocidad()) {
                    return p2.getVelocidad() - p1.getVelocidad();
                } else {
                    // Héroes antes que enemigos si velocidad igual
                    if (p1 instanceof Heroe && p2 instanceof Enemigo) return -1;
                    if (p1 instanceof Enemigo && p2 instanceof Heroe) return 1;
                    return 0;
                }
            });

            for (Personaje p : turnoOrden) {
                if (todosMuertos(enemigos)) {
                    System.out.println("\n¡Victoria! Todos los enemigos han sido derrotados.");
                    return;
                }
                if (todosMuertos(heroes)) {
                    System.out.println("\nDerrota... Todos los héroes han caído.");
                    return;
                }
                if (p instanceof Heroe) {
                    p.tomarTurno(heroes, enemigos, sc);
                } else {
                    p.tomarTurno(enemigos, heroes, sc);
                }
            }
        }
    }

    private boolean todosMuertos(List<Personaje> personajes) {
        for (Personaje p : personajes) {
            if (p.estaVivo()) return false;
        }
        return true;
    }      
    

    public static void main(String[] args) {
        DragonQuestVIII juego = new DragonQuestVIII();
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenido a Dragon Quest VIII - Simulación de Combate");
        System.out.println("1. Iniciar combate");
        System.out.println("2. Salir");

                int opcion = sc.nextInt();
                if (opcion == 1) {
                    juego.iniciarCombate();
                } else {
                    System.out.println("¡Hasta luego!");
                }
                sc.close();
            } 
        
        }
        




