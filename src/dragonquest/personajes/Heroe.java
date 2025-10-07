package dragonquest.personajes;

import java.util.List;
import java.util.Scanner;
import dragonquest.habilidades.Habilidad;
import dragonquest.estados.EstadoAlterado;

public class Heroe extends Personaje {

    public Heroe(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
    }

    @Override
    public void tomarTurno(List<Personaje> aliados, List<Personaje> enemigos, Scanner sc) {
        if (!estaVivo()) {
            System.out.println(nombre + " está fuera de combate y no puede actuar.");
            return;
        }
        if (estado == EstadoAlterado.PARALIZADO) {
            System.out.println(nombre + " está paralizado y pierde el turno.");
            return;
        }
        if (estado == EstadoAlterado.DORMIDO) {
            System.out.println(nombre + " está dormido y pierde el turno.");
            return;
        }

        System.out.println("\nTurno de " + nombre);
        mostrarEstado();
        System.out.println("Elige acción:");
        System.out.println("1. Atacar");
        System.out.println("2. Defender");
        System.out.println("3. Usar habilidad");
        System.out.println("4. Usar ítem (no implementado)");

        int opcion = 0;
        while (opcion < 1 || opcion > 4) {
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                opcion = 0;
            }
        }

        switch (opcion) {
            case 1:
                atacar(enemigos, sc);
                break;
            case 2:
                defender();
                break;
            case 3:
                usarHabilidad(enemigos, aliados, sc);
                break;
            case 4:
                System.out.println("Uso de ítems no implementado.");
                break;
        }
    }

    private void atacar(List<Personaje> enemigos, Scanner sc) {
        Personaje objetivo = seleccionarObjetivo(enemigos, sc);
        if (objetivo != null) {
            System.out.println(nombre + " ataca a " + objetivo.getNombre());
            objetivo.recibirDaño(ataque);
        }
    }

    private void defender() {
        System.out.println(nombre + " se defiende y aumenta su defensa temporalmente.");
        defensa += 5; // Ejemplo simple, se puede mejorar
    }

    private void usarHabilidad(List<Personaje> enemigos, List<Personaje> aliados, Scanner sc) {
        if (habilidades.isEmpty()) {
            System.out.println("No tienes habilidades disponibles.");
            return;
        }
        System.out.println("Elige habilidad:");
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidad h = habilidades.get(i);
            System.out.println((i + 1) + ". " + h.getNombre() + " (Costo MP: " + h.getCostoMP() + ")");
        }
        int opcion = 0;
        while (opcion < 1 || opcion > habilidades.size()) {
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                opcion = 0;
            }
        }
        Habilidad habilidad = habilidades.get(opcion - 1);
        if (mpActual < habilidad.getCostoMP()) {
            System.out.println("No tienes suficiente MP para usar esta habilidad.");
            return;
        }
        usarMP(habilidad.getCostoMP());
        habilidad.ejecutar(this, enemigos, aliados, sc);
    }

    private Personaje seleccionarObjetivo(List<Personaje> personajes, Scanner sc) {
        List<Personaje> vivos = new java.util.ArrayList<>();
        for (Personaje p : personajes) {
            if (p.estaVivo()) vivos.add(p);
        }
        if (vivos.isEmpty()) return null;

        System.out.println("Elige objetivo:");
        for (int i = 0; i < vivos.size(); i++) {
            System.out.println((i + 1) + ". " + vivos.get(i).getNombre());
        }
        int opcion = 0;
        while (opcion < 1 || opcion > vivos.size()) {
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                opcion = 0;
            }
        }
        return vivos.get(opcion - 1);
    }
}
