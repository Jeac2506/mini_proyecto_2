package dragonquest.personajes;

import java.util.*;

public class Enemigo extends Personaje {
    private String comportamiento;

    public Enemigo(String nombre, int hp, int mp, int ataque, int defensa, int velocidad, String comportamiento) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
        this.comportamiento = comportamiento;
    }

    @Override
    public void tomarTurno(List<Personaje> aliados, List<Personaje> enemigos, Scanner sc) {
        if (!estaVivo()) return;
        boolean puede = procesarEstadosAntesDeActuar();
        if (!puede) return;


        System.out.println("\nTurno de " + nombre + " (Enemigo - " + comportamiento + ")");
        Random rand = new Random();

        switch (comportamiento) {
            case "agresivo" -> atacar(enemigos);
            case "defensivo" -> {
                if (hpActual < hpMax / 2 && rand.nextBoolean()) defender();
                else atacar(enemigos);
            }
            default -> atacar(enemigos);
        }
    }

    private void atacar(List<Personaje> enemigos) {
        List<Personaje> vivos = enemigos.stream().filter(Personaje::estaVivo).toList();
        if (vivos.isEmpty()) return;
        Personaje objetivo = vivos.get(new Random().nextInt(vivos.size()));
        System.out.println(nombre + " ataca a " + objetivo.getNombre());
        objetivo.recibirDaño(ataque);
    }

    private void defender() {
        System.out.println(nombre + " se defiende, reduciendo daño recibido este turno.");
        defensa += 3;
    }
}
