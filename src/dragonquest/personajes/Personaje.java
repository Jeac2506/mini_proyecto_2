package dragonquest.personajes;

import java.util.*;
import dragonquest.habilidades.Habilidad;
import dragonquest.estados.EstadoAlterado;

public abstract class Personaje {
    protected String nombre;
    protected int hpMax, hpActual;
    protected int mpMax, mpActual;
    protected int ataque, defensa, velocidad;
    protected EstadoAlterado estado;
    public List<Habilidad> habilidades;

    public Personaje(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.hpMax = hp;
        this.hpActual = hp;
        this.mpMax = mp;
        this.mpActual = mp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.estado = EstadoAlterado.NORMAL;
        this.habilidades = new ArrayList<>();
    }

    public boolean estaVivo() {
        return hpActual > 0;
    }

    public void recibirDaño(int daño) {
        int dañoReal = Math.max(daño - defensa, 1);
        hpActual -= dañoReal;
        if (hpActual < 0) hpActual = 0;
        System.out.println(nombre + " recibe " + dañoReal + " de daño. HP: " + hpActual + "/" + hpMax);
    }

    public void curar(int cantidad) {
        hpActual += cantidad;
        if (hpActual > hpMax) hpActual = hpMax;
        System.out.println(nombre + " se cura " + cantidad + " puntos. HP: " + hpActual + "/" + hpMax);
    }

    public void usarMP(int cantidad) {
        mpActual -= cantidad;
        if (mpActual < 0) mpActual = 0;
    }

    public abstract void tomarTurno(List<Personaje> aliados, List<Personaje> enemigos, Scanner sc);

    public String getNombre() {
        return nombre;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public EstadoAlterado getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlterado estado) {
        this.estado = estado;
    }

    public void mostrarEstado() {
        System.out.println(nombre + " - HP: " + hpActual + "/" + hpMax + " MP: " + mpActual + "/" + mpMax + " Estado: " + estado);
    }
}
