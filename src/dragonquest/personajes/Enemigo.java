package dragonquest.personajes;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import dragonquest.estados.EstadoAlterado;

public class Enemigo extends Personaje {
    private String comportamiento; // "agresivo", "defensivo", etc.

    public Enemigo(String nombre, int hp, int mp, int ataque, int defensa, int velocidad, String comportamiento) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
        this.comportamiento = comportamiento;
    }

    @Override
    public void tomarTurno(List<Personaje> aliados, List<Personaje> enemigos, Scanner sc) {
        if (!estaVivo()) {
            return;
        }
        if (estado == EstadoAlterado.PARALIZADO) {
            System.out.println(nombre + " está paralizado y pierde el turno.");
            return;
        }
        if (estado == EstadoAlterado.DORMIDO) {
            System.out.println(nombre + " está dormido y pierde el turno.");
            return;
        }

        System.out.println("\nTurno de " + nombre + " (Enemigo - " + comportamiento + ")");
        // Lógica simple basada en comportamiento
        switch (comportamiento) {
            case "agresivo":
                atacar(aliados);
                break;
            case "defensivo":
                defender();
                break;
            default:
                atacar(aliados);
                break;
        }
    }

    private void atacar(List<Personaje> enemigos) {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje p : enemigos) {
            if (p.estaVivo()) vivos.add(p);
        }
        if (vivos.isEmpty()) return;
        // Atacar al héroe con menos HP
        Personaje objetivo = vivos.get(0);
        for (Personaje p : vivos) {
            if (p.hpActual < objetivo.hpActual) objetivo = p;
        }
        System.out.println(nombre + " ataca a " + objetivo.getNombre());
        objetivo.recibirDaño(ataque);
    }

    private void defender() {
        System.out.println(nombre + " se defiende y aumenta su defensa temporalmente.");
        defensa += 3; // Ejemplo simple
    }
}
