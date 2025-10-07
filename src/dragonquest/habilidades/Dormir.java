package dragonquest.habilidades;

import dragonquest.personajes.Personaje;
import dragonquest.estados.EstadoAlterado;

public class Dormir extends Habilidad {
    public Dormir(String nombre, int costoMP) {
        super(nombre, costoMP);
    }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        System.out.println(usuario.getNombre() + " hace dormir a " + objetivo.getNombre());
        objetivo.aplicarEstado(EstadoAlterado.DORMIDO);
    }
}
