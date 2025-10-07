package dragonquest.items;

import dragonquest.personajes.Personaje;

public abstract class Item {
    protected String nombre;

    public Item(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    //metodo que define que hace el item
    public abstract void usar(Personaje usuario, Personaje objetivo);
    
}
