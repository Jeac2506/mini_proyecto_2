package habilidades;

import personajes.Personaje;

/**
 * Clase abstracta que define la interfaz de una Habilidad.
 * Implementaciones concretas realizan efectos (daño, curación, estados).
 */
public abstract class Habilidad {
    protected String nombre;
    private int costoMP;

    public Habilidad(String nombre, int costoMP) {
        this.nombre = nombre;
        this.setCostoMP(costoMP);
    }

    public int getCostoMP() {
        return costoMP;
        
    }

    public void setCostoMP(int costoMP) {
        this.costoMP = costoMP;
        
    }

    /**
     * Ejecuta la habilidad sobre un objetivo.
     * Implementaciones deciden si el objetivo es aliado o enemigo.
     */
    public abstract void ejecutar(Personaje usuario, Personaje objetivo);

    public String getNombre() { return nombre; }
}
