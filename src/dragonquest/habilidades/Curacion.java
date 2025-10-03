package dragonquest.habilidades;

import java.util.List;
import java.util.Scanner;
import dragonquest.personajes.Personaje;

public class Curacion extends Habilidad {
    private int poder;

    public Curacion(String nombre, int costoMP, int poder) {
        super(nombre, costoMP);
        this.poder = poder;
    }

    @Override
    public void ejecutar(Personaje usuario, List<Personaje> enemigos, List<Personaje> aliados, Scanner sc) {
        Personaje objetivo = seleccionarObjetivo(aliados, sc);
        if (objetivo != null) {
            System.out.println(usuario.getNombre() + " usa " + nombre + " en " + objetivo.getNombre());
            objetivo.curar(poder);
        }
    }

    private Personaje seleccionarObjetivo(List<Personaje> personajes, Scanner sc) {
        List<Personaje> vivos = new java.util.ArrayList<>();
        for (Personaje p : personajes) {
            if (p.estaVivo()) vivos.add(p);
        }
        if (vivos.isEmpty()) return null;

        System.out.println("Elige objetivo para " + nombre + ":");
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
