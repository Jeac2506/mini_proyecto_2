package habilidades;

import personajes.Personaje;
import estados.EstadoAlterado;

/** Aplica el estado VENENO al objetivo (daño por turno). */
public class Veneno extends Habilidad {
    public Veneno(String nombre, int costoMP) { super(nombre, costoMP); }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        System.out.println(usuario.getNombre() + " envenena a " + objetivo.getNombre());
        objetivo.aplicarEstado(EstadoAlterado.VENENO);
    }
}
