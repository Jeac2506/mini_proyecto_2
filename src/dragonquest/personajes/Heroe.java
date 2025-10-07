package dragonquest.personajes;


import dragonquest.habilidades.Habilidad;
import dragonquest.items.Eter;
import dragonquest.items.Item;
import dragonquest.items.Pocion;

import java.util.*;

public class Heroe extends Personaje {

    public List<Item> inventario;

    public Heroe(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(nombre, hp, mp, ataque, defensa, velocidad);

        inventario = new ArrayList<>();
        inventario.add(new Pocion());
        inventario.add(new Eter());
    }

    @Override
    public void tomarTurno(List<Personaje> aliados, List<Personaje> enemigos, Scanner sc) {
        if (!estaVivo()) return;
        boolean puede = procesarEstadosAntesDeActuar();
        if (!puede) return;

        System.out.println("\nTurno de " + nombre + " (HP: " + hpActual + " MP: " + mpActual + ")");
        System.out.println("1. Atacar");
        System.out.println("2. Usar habilidad");
        System.out.println("3. Usar item");
        System.out.print("Elige acción: ");
        int opcion = sc.nextInt();

        switch (opcion) {
            case 1 -> atacar(enemigos);
            case 2 -> usarHabilidad(enemigos, sc);
            case 3 -> {
                if (inventario.isEmpty()) {
                    System.out.println("No tienes ítems disponibles.");
                } else {
                    System.out.println("Elige un ítem:");
                    for (int i = 0; i < inventario.size(); i++) {
                        System.out.println((i + 1) + ". " + inventario.get(i).getNombre());
                    }

                    int eleccion = 0;
                    while (eleccion < 1 || eleccion > inventario.size()) {
                        System.out.print("Opción: ");
                        try {
                            eleccion = Integer.parseInt(sc.nextLine());
                        } catch (Exception e) {
                            eleccion = 0;
                        }
                    }
                    
                    Item itemSeleccionado = inventario.get(eleccion -1);

                    if (itemSeleccionado instanceof Pocion || itemSeleccionado instanceof Eter) {
                        itemSeleccionado.usar(this, this);
                    }

                    else {
                        System.out.println("¿A quien quieres usar el item?");
                        for (int i = 0; i < aliados.size(); i++) {
                            System.out.println((i + 1) + ". " + aliados.get(i).getNombre());
                        }

                        int objetivoIdx = 0;
                        while (objetivoIdx < 1 || objetivoIdx > aliados.size()); {
                            System.out.println("Opcion: ");
                            try {
                                objetivoIdx = Integer.parseInt(sc.nextLine());

                            } catch (Exception e) {
                                objetivoIdx = 0;
                            }
                        }

                        Personaje objetivo = aliados.get(objetivoIdx - 1);
                        itemSeleccionado.usar(this, objetivo);
                        inventario.remove(itemSeleccionado);
                    }
                }
                break;
            }
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
