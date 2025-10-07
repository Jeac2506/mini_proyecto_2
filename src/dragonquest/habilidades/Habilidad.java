package dragonquest.habilidades;

import dragonquest.personajes.Personaje;

public abstract class Habilidad {
    protected String nombre;
    protected int costoMP;

    public Habilidad(String nombre, int costoMP) {
        this.nombre = nombre;
        this.costoMP = costoMP;
    }

    public abstract void ejecutar(Personaje usuario, Personaje objetivo);

    public String getNombre() { return nombre; }
}
