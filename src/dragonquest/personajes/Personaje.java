package dragonquest.personajes;

import dragonquest.habilidades.Habilidad;
import dragonquest.estados.EstadoAlterado;
import java.util.*;

public abstract class Personaje {
    protected String nombre;
    protected int hpMax, mpMax, ataque, defensa, velocidad;
    protected int hpActual, mpActual;
    protected List<Habilidad> habilidades;
    protected EstadoAlterado estado = EstadoAlterado.NORMAL;
    protected int estadoDuracion = 0; // cuántos turnos dura el estado
    protected static final Random RAND = new Random();

    public Personaje(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.hpMax = hp;
        this.mpMax = mp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.hpActual = hp;
        this.mpActual = mp;
        this.habilidades = new ArrayList<>();
    }

    public boolean estaVivo() {
        return hpActual > 0;
    }

    public void recibirDaño(int cantidad) {
        int daño = Math.max(0, cantidad - defensa);
        hpActual -= daño;
        if (hpActual < 0) hpActual = 0;
        System.out.println(nombre + " recibe " + daño + " puntos de daño. (HP: " + hpActual + ")");
    }

    public void curar(int cantidad) {
        hpActual += cantidad;
        if (hpActual > hpMax) hpActual = hpMax;
        System.out.println(nombre + " se cura " + cantidad + " puntos. HP: " + hpActual + "/" + hpMax);
    }

    // Aplica un estado con duración
    public void aplicarEstado(EstadoAlterado nuevo) {
        if (estado != EstadoAlterado.NORMAL) {
            System.out.println(nombre + " ya tiene un estado alterado (" + estado + ").");
            return;
        }

        estado = nuevo;
        switch (nuevo) {
            case VENENO -> estadoDuracion = 3;
            case PARALIZADO -> estadoDuracion = 3;
            case DORMIDO -> estadoDuracion = 2;
            default -> estadoDuracion = 0;
        }
        System.out.println(nombre + " sufre el estado " + nuevo + " (" + estadoDuracion + " turnos).");
    }

    // Procesa efectos al inicio del turno y devuelve si puede actuar
    public boolean procesarEstadosAntesDeActuar() {
        if (!estaVivo()) return false;

        switch (estado) {
            case VENENO -> {
                int daño = Math.max(1, hpMax / 10);
                hpActual -= daño;
                if (hpActual < 0) hpActual = 0;
                System.out.println(nombre + " sufre " + daño + " de daño por veneno. (HP: " + hpActual + ")");
            }
            case DORMIDO -> {
                if (RAND.nextInt(100) < 40) { // 40% chance de despertar
                    System.out.println(nombre + " se despierta.");
                    estado = EstadoAlterado.NORMAL;
                    estadoDuracion = 0;
                    return true;
                } else {
                    System.out.println(nombre + " está dormido y no puede actuar.");
                }
            }
            case PARALIZADO -> {
                if (RAND.nextInt(100) < 30) { // 30% chance de quedar inmóvil
                    System.out.println(nombre + " está paralizado y no puede moverse.");
                } else {
                    System.out.println(nombre + " logra moverse a pesar de la parálisis!");
                    reducirDuracionEstado();
                    return true;
                }
            }
            default -> {}
        }

        reducirDuracionEstado();
        return estado == EstadoAlterado.NORMAL;
    }

    private void reducirDuracionEstado() {
        if (estado != EstadoAlterado.NORMAL) {
            estadoDuracion--;
            if (estadoDuracion <= 0) {
                System.out.println(nombre + " ya no está afectado por " + estado + ".");
                estado = EstadoAlterado.NORMAL;
                estadoDuracion = 0;
            }
        }
    }

    public boolean puedeActuar() {
        return estado == EstadoAlterado.NORMAL;
    }

    public void recuperarMP(int cantidad) {
        mpActual = Math.min(mpMax, mpActual + cantidad);
    }

    public abstract void tomarTurno(List<Personaje> aliados, List<Personaje> enemigos, Scanner sc);

    public int getVelocidad() { return velocidad; }
    public String getNombre() { return nombre; }

    public void agregarHabilidad(Habilidad h) {
        habilidades.add(h);
    }
        // Muestra HP, MP y estado del personaje
    public void mostrarEstado() {
        String barraHP = generarBarra(hpActual, hpMax, 20);
        String barraMP = generarBarra(mpActual, mpMax, 10);

        System.out.printf("%-10s | HP: %-3d/%-3d %-22s | MP: %-3d/%-3d %-12s | Estado: %-10s\n",
                nombre, hpActual, hpMax, barraHP, mpActual, mpMax, barraMP, estado);
    }

    // Crea una barra visual de HP/MP proporcional
    private String generarBarra(int actual, int max, int largo) {
        int relleno = (int) ((double) actual / max * largo);
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < largo; i++) {
            if (i < relleno) barra.append("█");
            else barra.append(" ");
        }
        barra.append("]");
        return barra.toString();
    }

}
