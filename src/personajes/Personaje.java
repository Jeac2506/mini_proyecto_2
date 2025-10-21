package personajes;

import habilidades.Habilidad;
import estados.EstadoAlterado;
import java.util.*;

/**
 * Clase base abstracta que representa a cualquier personaje del juego.
 * 
 * Esta clase es el corazón del sistema de combate.
 * Todos los personajes (héroes y enemigos) heredan de ella.
 * 
 * Contiene:
 * - Atributos básicos (HP, MP, ataque, defensa, velocidad)
 * - Sistema de estados alterados (VENENO, PARALIZADO, DORMIDO, etc.)
 * - Métodos para recibir daño, curar, procesar efectos de estado
 * - Métodos auxiliares de visualización de estado (barra HP/MP)
 */
public abstract class Personaje {
    // -------------------------
    // 🔹 Atributos principales
    // -------------------------
    protected String nombre;
    protected int hpMax, mpMax;          // puntos máximos de vida y magia
    protected int ataque, defensa;       // atributos de combate
    protected int velocidad;             // determina el orden de turnos
    protected int hpActual, mpActual;    // valores actuales durante la batalla
    public List<Habilidad> habilidades; // lista de habilidades que puede usar

    // Estado alterado actual y su duración restante
    protected EstadoAlterado estado = EstadoAlterado.NORMAL;
    protected int estadoDuracion = 0;

    protected static final Random RAND = new Random();

    // ------------------------------------------------
    // 🔹 Constructor
    // ------------------------------------------------
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

    // ------------------------------------------------
    // 🔹 Métodos generales de combate
    // ------------------------------------------------

    /** Devuelve true si el personaje sigue con vida. */
    public boolean estaVivo() {
        return hpActual > 0;
    }

    /** Aplica daño al personaje teniendo en cuenta su defensa. */
    public void recibirDaño(int cantidad) {
        int daño = Math.max(1, cantidad - defensa); // Mínimo 1 de daño
        hpActual -= daño;
        if (hpActual < 0) hpActual = 0;
        System.out.println(nombre + " recibe " + daño + " puntos de daño. (HP: " + hpActual + "/" + hpMax + ")");
        
        // Si muere, mostrar mensaje dramático
        if (hpActual == 0) {
            System.out.println("💀 ¡" + nombre + " ha caído en combate!");
        }
    }

    /** Cura puntos de vida al personaje. */
    public void curar(int cantidad) {
        int hpAnterior = hpActual;
        hpActual = Math.min(hpMax, hpActual + cantidad);
        int curado = hpActual - hpAnterior;
        System.out.println(nombre + " recupera " + curado + " HP. (HP: " + hpActual + "/" + hpMax + ")");
    }

    /** Restaura puntos de magia al personaje. */
    public void restaurarMP(int cantidad) {
        int mpAnterior = mpActual;
        mpActual = Math.min(mpMax, mpActual + cantidad);
        int restaurado = mpActual - mpAnterior;
        System.out.println(nombre + " recupera " + restaurado + " MP. (MP: " + mpActual + "/" + mpMax + ")");
    }

    /**
     * Aplica un nuevo estado alterado si el personaje está libre de efectos.
     * Si ya tiene un estado, no se aplica otro nuevo.
     */
    public void aplicarEstado(EstadoAlterado nuevo) {
        if (estado != EstadoAlterado.NORMAL) {
            System.out.println(nombre + " ya tiene un estado (" + estado + "). No se aplica " + nuevo + ".");
            return;
        }
        estado = nuevo;

        // Duración base según tipo de estado
        switch (nuevo) {
            case VENENO -> estadoDuracion = 3;
            case PARALIZADO -> estadoDuracion = 3;
            case DORMIDO -> estadoDuracion = 2;
            default -> estadoDuracion = 0;
        }
        System.out.println("⚠️ " + nombre + " sufre el estado " + nuevo + " durante " + estadoDuracion + " turnos.");
    }

    /**
     * Procesa los efectos del estado alterado actual antes de actuar.
     * 
     * @return true si puede actuar, false si pierde el turno.
     */
    public boolean procesarEstadosAntesDeActuar() {
        if (!estaVivo()) return false;

        switch (estado) {
            case VENENO -> {
                int daño = Math.max(1, hpMax / 10);
                hpActual -= daño;
                if (hpActual < 0) hpActual = 0;
                System.out.println("🟢 " + nombre + " sufre " + daño + " de daño por veneno. (HP: " + hpActual + ")");
            }
            case DORMIDO -> {
                if (RAND.nextInt(100) < 40) {
                    System.out.println("😴 " + nombre + " se despierta.");
                    estado = EstadoAlterado.NORMAL;
                    estadoDuracion = 0;
                    return true;
                } else {
                    System.out.println("💤 " + nombre + " está dormido y no puede actuar este turno.");
                    reducirDuracionEstado();
                    return false;
                }
            }
            case PARALIZADO -> {
                if (RAND.nextInt(100) < 30) {
                    System.out.println("⚡ " + nombre + " está paralizado y no puede moverse este turno.");
                    reducirDuracionEstado();
                    return false;
                } else {
                    System.out.println("💪 " + nombre + " logra moverse a pesar de la parálisis!");
                }
            }
            default -> {}
        }
        reducirDuracionEstado();
        return true;
    }

    /** Reduce la duración del estado activo y lo elimina si ya terminó. */
    private void reducirDuracionEstado() {
        if (estado != EstadoAlterado.NORMAL) {
            estadoDuracion--;
            if (estadoDuracion <= 0) {
                System.out.println("✅ " + nombre + " ya no está afectado por " + estado + ".");
                estado = EstadoAlterado.NORMAL;
                estadoDuracion = 0;
            }
        }
    }

    /** Devuelve true si puede realizar acciones en este turno. */
    public boolean puedeActuar() {
        return estado == EstadoAlterado.NORMAL || estado == EstadoAlterado.VENENO;
    }

    // ------------------------------------------------
    // 🔹 Métodos de apoyo a ítems y habilidades
    // ------------------------------------------------

    /**
     * 🔹 Permite eliminar un estado alterado específico.
     * Usado por ítems como el Antídoto o habilidades curativas.
     * 
     * @param e Estado a eliminar (ej: VENENO, PARALIZADO, etc.)
     * @return true si el estado fue removido exitosamente.
     */
    public boolean quitarEstado(EstadoAlterado e) {
        if (estado == e) {
            System.out.println("✨ " + nombre + " se recupera del estado " + e + ".");
            estado = EstadoAlterado.NORMAL;
            estadoDuracion = 0;
            return true;
        }
        return false;
    }

    /**
     * 🔹 Permite verificar si el personaje está bajo cierto estado alterado.
     * 
     * @param e Estado a verificar
     * @return true si está afectado por ese estado, false si no.
     */
    public boolean estaEnEstado(EstadoAlterado e) {
        return estado == e;
    }

    /**
     * 🔹 Método para establecer el estado directamente (usado por habilidades especiales)
     */
    public void setEstado(EstadoAlterado nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /**
     * 🔹 Método para establecer la duración del estado
     */
    public void setEstadoDuracion(int duracion) {
        this.estadoDuracion = duracion;
    }

    // ------------------------------------------------
    // 🔹 Métodos abstractos y utilitarios
    // ------------------------------------------------

    /** Método abstracto: se implementa en Heroe y Enemigo. */
    public abstract void tomarTurno(List<Personaje> aliados, List<Personaje> enemigos, Scanner sc);

    public int getVelocidad() { return velocidad; }
    public String getNombre() { return nombre; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getHpActual() { return hpActual; }
    public int getHpMax() { return hpMax; }
    public int getMpActual() { return mpActual; }
    public int getMpMax() { return mpMax; }
    public EstadoAlterado getEstado() { return estado; }
    public int getEstadoDuracion() { return estadoDuracion; }
    
    public void agregarHabilidad(Habilidad h) { 
        habilidades.add(h);
        System.out.println("📚 " + nombre + " aprende: " + h.getNombre());
    }

    // ------------------------------------------------
    // 🔹 Visualización del estado en consola
    // ------------------------------------------------

    /** Muestra barras de HP y MP junto al estado actual del personaje. */
    public void mostrarEstado() {
        String barraHP = generarBarra(hpActual, hpMax, 20, "♥");
        String barraMP = generarBarra(mpActual, mpMax, 10, "★");
        
        String estadoStr = estado == EstadoAlterado.NORMAL ? "Normal" : estado.toString();
        String icono = this instanceof Heroe ? "⚔️" : "👾";
        
        System.out.printf("%s %-10s | HP: %-3d/%-3d %-22s | MP: %-3d/%-3d %-12s | Estado: %-10s\n",
                icono, nombre, hpActual, hpMax, barraHP, mpActual, mpMax, barraMP, estadoStr);
    }
        // Agregar estos métodos a la clase Personaje existente:

    /**
    * Consume MP del personaje. Retorna true si tenía suficiente MP.
    */
    public boolean consumirMP(int cantidad) {
        if (mpActual >= cantidad) {
        mpActual -= cantidad;
        return true;
        }
        return false;
    }

    /**
    * Setter para HP actual (útil para efectos especiales)
     */
    public void setHpActual(int hp) {
        this.hpActual = Math.max(0, Math.min(hp, hpMax));
    }

    /**
    * Setter para MP actual (útil para efectos especiales)
    */
    public void setMpActual(int mp) {
        this.mpActual = Math.max(0, Math.min(mp, mpMax));
    }

    /**
    * Obtiene la lista de habilidades (útil para la GUI)
    */
    public List<Habilidad> getHabilidades() {
        return new ArrayList<>(habilidades);
    }

    /** Genera una barra visual proporcional al valor actual. */
    private String generarBarra(int actual, int max, int largo, String simbolo) {
        int relleno = 0;
        if (max > 0) relleno = (int) ((double) actual / max * largo);
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < largo; i++) {
            if (i < relleno) barra.append("█");
            else barra.append("·");
        }
        barra.append("]");
        
        // Colorear según el porcentaje
        double porcentaje = max > 0 ? (double) actual / max : 0;
        if (porcentaje > 0.5) {
            return barra.toString(); // Verde (normal)
        } else if (porcentaje > 0.25) {
            return barra.toString(); // Amarillo (precaución)
        } else {
            return barra.toString(); // Rojo (crítico)
        }
    }
}