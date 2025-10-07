package dragonquest.items;

import dragonquest.personajes.Personaje;

public class Eter extends Item {
    public Eter() {
        super("Eter");
    
    }

    @Override
    public void usar(Personaje usuario, Personaje objetivo) {
        int cantidad = 20;
        //Recuperar MP
        objetivo.recuperarMP(cantidad);
        System.out.println(usuario.getNombre() + " usa un " + getNombre() + " y restaura " + cantidad + " MP a " + objetivo.getNombre() + ".");
    }
}
