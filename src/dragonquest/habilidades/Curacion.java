package dragonquest.habilidades;

import dragonquest.personajes.Personaje;

public class Curacion extends Habilidad {
    private int poder;

    public Curacion(String nombre, int costoMP, int poder) {
        super(nombre, costoMP);
        this.poder = poder;
    }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        System.out.println(usuario.getNombre() + " usa " + nombre);
        usuario.curar(poder);
    }
}
