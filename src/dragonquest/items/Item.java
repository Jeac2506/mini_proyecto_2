package dragonquest.items;

import dragonquest.personajes.Personaje;

<<<<<<< HEAD
=======
/**
 * Item - clase base para objetos utilizables.
 * - Cada ítem define su efecto en usar(usuario, objetivo).
 */
>>>>>>> Lopez
public abstract class Item {
    protected String nombre;

    public Item(String nombre) {
        this.nombre = nombre;
    }

<<<<<<< HEAD
    public String getNombre() {
        return nombre;
    }

    //metodo que define que hace el item
    public abstract void usar(Personaje usuario, Personaje objetivo);
    
=======
    public String getNombre() { return nombre; }

    /**
     * Aplica el efecto del ítem.
     * @param usuario quien usa el ítem (quien lo consume).
     * @param objetivo personaje sobre quien se aplica el efecto.
     * @return true si el uso tuvo efecto (y se debe consumir del inventario).
     */
    public abstract boolean usar(Personaje usuario, Personaje objetivo);
>>>>>>> Lopez
}
