package dragonquest.habilidades;

import dragonquest.personajes.Personaje;
import dragonquest.estados.EstadoAlterado;

/**
 * Aturdimiento - Habilidad que aplica el estado PARALIZADO al objetivo
 * Representa un golpe que aturde temporalmente al enemigo
 */
public class Aturdimiento extends Habilidad {
    
    public Aturdimiento(String nombre, int costoMP) {
        super(nombre, costoMP);
    }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        System.out.println(usuario.getNombre() + " ejecuta " + nombre + " sobre " + objetivo.getNombre());
        System.out.println("¡El impacto aturde al objetivo!");
        objetivo.aplicarEstado(EstadoAlterado.PARALIZADO);
    }
}