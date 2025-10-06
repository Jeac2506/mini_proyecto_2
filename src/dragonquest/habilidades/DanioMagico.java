package dragonquest.habilidades;

import dragonquest.personajes.Personaje;

public class DanioMagico extends Habilidad {
    private int poder;

    public DanioMagico(String nombre, int costoMP, int poder) {
        super(nombre, costoMP);
        this.poder = poder;
    }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        System.out.println(usuario.getNombre() + " lanza " + nombre + " sobre " + objetivo.getNombre());
        objetivo.recibirDaño(poder);
    }
}
