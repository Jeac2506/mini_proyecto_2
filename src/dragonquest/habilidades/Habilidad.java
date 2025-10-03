package dragonquest.habilidades;

import java.util.List;
import java.util.Scanner;
import dragonquest.personajes.Personaje;

public abstract class Habilidad {
    protected String nombre;
    protected int costoMP;

    public Habilidad(String nombre, int costoMP) {
        this.nombre = nombre;
        this.costoMP = costoMP;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCostoMP() {
        return costoMP;
    }

    // Método para ejecutar la habilidad
    public abstract void ejecutar(Personaje usuario, List<Personaje> enemigos, List<Personaje> aliados, Scanner sc);
}
