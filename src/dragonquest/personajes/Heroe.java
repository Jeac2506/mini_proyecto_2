package dragonquest.personajes;

import dragonquest.habilidades.Habilidad;
import java.util.*;

public class Heroe extends Personaje {
    public Heroe(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
    }

    @Override
    public void tomarTurno(List<Personaje> aliados, List<Personaje> enemigos, Scanner sc) {
        if (!estaVivo()) return;
        boolean puede = procesarEstadosAntesDeActuar();
        if (!puede) return;

        System.out.println("\nTurno de " + nombre + " (HP: " + hpActual + " MP: " + mpActual + ")");
        System.out.println("1. Atacar");
        System.out.println("2. Usar habilidad");
        System.out.print("Elige acción: ");
        int opcion = sc.nextInt();

        switch (opcion) {
            case 1 -> atacar(enemigos);
            case 2 -> usarHabilidad(enemigos, sc);
            default -> System.out.println("Opción inválida.");
        }
    }

    private void atacar(List<Personaje> enemigos) {
        List<Personaje> vivos = enemigos.stream().filter(Personaje::estaVivo).toList();
        if (vivos.isEmpty()) return;
        Personaje objetivo = vivos.get(0);
        System.out.println(nombre + " ataca a " + objetivo.getNombre());
        objetivo.recibirDaño(ataque);
    }

    private void usarHabilidad(List<Personaje> enemigos, Scanner sc) {
        if (habilidades.isEmpty()) {
            System.out.println("No tienes habilidades disponibles.");
            return;
        }

        System.out.println("Elige una habilidad:");
        for (int i = 0; i < habilidades.size(); i++) {
            System.out.println((i + 1) + ". " + habilidades.get(i).getNombre());
        }

        int eleccion = sc.nextInt();
        if (eleccion < 1 || eleccion > habilidades.size()) return;

        Habilidad h = habilidades.get(eleccion - 1);
        Personaje objetivo = enemigos.get(0);
        h.ejecutar(this, objetivo);
    }
}
