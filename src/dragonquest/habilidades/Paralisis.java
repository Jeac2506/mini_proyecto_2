package dragonquest.habilidades;

import dragonquest.personajes.Personaje;
import dragonquest.estados.EstadoAlterado;

/** Aplica el estado PARALIZADO al objetivo (chance de fallar turno). */
public class Paralisis extends Habilidad {
    public Paralisis(String nombre, int costoMP) { super(nombre, costoMP); }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        System.out.println(usuario.getNombre() + " paraliza a " + objetivo.getNombre());
        objetivo.aplicarEstado(EstadoAlterado.PARALIZADO);
    }
}
