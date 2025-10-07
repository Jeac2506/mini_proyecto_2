package dragonquest.items;

import dragonquest.personajes.Personaje;

public class Pocion extends Item {

    public Pocion() {
        super("Poción");
    }

    @Override
    public void usar(Personaje usuario, Personaje objetivo) {
        int cantidad = 40;
        objetivo.curar(cantidad);
        System.out.println(usuario.getNombre() + " usa una " + getNombre() +
                           " y cura " + cantidad + " HP a " + objetivo.getNombre() + ".");
    }
}
