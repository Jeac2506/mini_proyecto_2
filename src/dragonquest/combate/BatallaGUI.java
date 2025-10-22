package dragonquest.combate;

import dragonquest.personajes.*;
import dragonquest.items.*;
import java.util.*;

public class BatallaGUI {
    private List<Personaje> heroes;
    private List<Personaje> enemigos;
    private InventarioGrupo inventario;

    private List<Personaje> ordenTurnos;  // lista ordenada de turnos
    private int indiceTurno = 0;          // posición actual en la lista
    private boolean batallaTerminada = false;

    public BatallaGUI() {
        heroes = new ArrayList<>();
        enemigos = new ArrayList<>();
        inventario = new InventarioGrupo();
        inicializar();
        construirOrdenTurnos();
    }

    private void inicializar() {
        Heroe heroe = new Heroe("Héroe", 100, 50, 20, 10, 15);
        heroe.agregarHabilidad(new dragonquest.habilidades.DanioMagico("Bola de Fuego", 10, 30));
        heroe.agregarHabilidad(new dragonquest.habilidades.Curacion("Curar", 8, 25));

        Heroe yangus = new Heroe("Yangus", 120, 30, 25, 15, 10);
        yangus.agregarHabilidad(new dragonquest.habilidades.GolpeCritico("Hachazo Brutal", 5));

        Heroe jessica = new Heroe("Jessica", 80, 70, 18, 8, 20);
        jessica.agregarHabilidad(new dragonquest.habilidades.DanioMagico("Rayo", 12, 35));

        Heroe angelo = new Heroe("Angelo", 90, 60, 22, 12, 18);
        angelo.agregarHabilidad(new dragonquest.habilidades.Curacion("Bendición", 10, 30));

        heroes.add(heroe);
        heroes.add(yangus);
        heroes.add(jessica);
        heroes.add(angelo);

        Enemigo dragoon = new Enemigo("Dragoon", 110, 20, 25, 10, 17, "agresivo");
        Enemigo fantasma = new Enemigo("Fantasma", 85, 40, 18, 8, 21, "estratégico");
        Enemigo golem = new Enemigo("Golem de Piedra", 150, 10, 20, 18, 5, "defensivo");
        Enemigo slime = new Enemigo("Slime", 50, 30, 15, 25, 30, "evasivo");

        enemigos.add(dragoon);
        enemigos.add(fantasma);
        enemigos.add(golem);
        enemigos.add(slime);
    }

    private void construirOrdenTurnos() {
        ordenTurnos = new ArrayList<>();
        ordenTurnos.addAll(heroes);
        ordenTurnos.addAll(enemigos);
        ordenTurnos.sort((a, b) -> {
            if (b.getVelocidad() != a.getVelocidad()) return b.getVelocidad() - a.getVelocidad();
            if (a instanceof Heroe && b instanceof Enemigo) return -1;
            if (a instanceof Enemigo && b instanceof Heroe) return 1;
            return 0;
        });
    }

    // ==============================
    // Turno controlado paso a paso
    // ==============================

    public String ejecutarTurnoJugador(String tipoAccion) {
        if (batallaTerminada) return "El combate ha terminado.";

        Personaje actual = ordenTurnos.get(indiceTurno);
        if (!(actual instanceof Heroe)) return "No es turno de un héroe.";

        Heroe heroe = (Heroe) actual;
        String log = "";

        switch (tipoAccion) {
            case "atacar" -> log = ejecutarAtaque(heroe);
            case "habilidad" -> log = ejecutarHabilidad(heroe);
            case "item" -> log = ejecutarItem(heroe);
        }

        avanzarTurno();
        return log;
    }

    private void avanzarTurno() {
        // avanzar al siguiente personaje
        indiceTurno++;

        if (indiceTurno >= ordenTurnos.size()) {
            indiceTurno = 0; // nuevo ciclo
        }

        // si el siguiente es enemigo, ejecuta su acción automáticamente
        Personaje actual = ordenTurnos.get(indiceTurno);
        if (actual instanceof Enemigo) {
            ejecutarTurnoEnemigo((Enemigo) actual);
            avanzarTurno(); // pasar al siguiente después de atacar
        }

        if (todosMuertos(enemigos)) batallaTerminada = true;
        if (todosMuertos(heroes)) batallaTerminada = true;
    }

    private void ejecutarTurnoEnemigo(Enemigo enemigo) {
        if (!enemigo.estaVivo()) return;
        List<Personaje> vivos = obtenerVivos(heroes);
        if (vivos.isEmpty()) return;

        Personaje objetivo = vivos.get(new Random().nextInt(vivos.size()));
        objetivo.recibirDaño(enemigo.getAtaque());
    }

    private String ejecutarAtaque(Heroe heroe) {
        List<Personaje> enemigosVivos = obtenerVivos(enemigos);
        if (enemigosVivos.isEmpty()) return "No quedan enemigos.";

        Personaje objetivo = enemigosVivos.get(new Random().nextInt(enemigosVivos.size()));
        objetivo.recibirDaño(heroe.getAtaque());
        return heroe.getNombre() + " ataca a " + objetivo.getNombre() + ".";
    }

    private String ejecutarHabilidad(Heroe heroe) {
        List<dragonquest.habilidades.Habilidad> habilidades = heroe.getHabilidades();
        if (habilidades.isEmpty()) return "No tienes habilidades.";

        dragonquest.habilidades.Habilidad h = habilidades.get(new Random().nextInt(habilidades.size()));
        List<Personaje> enemigosVivos = obtenerVivos(enemigos);
        if (enemigosVivos.isEmpty()) return "No quedan enemigos.";

        Personaje objetivo = enemigosVivos.get(new Random().nextInt(enemigosVivos.size()));
        h.ejecutar(heroe, objetivo);
        return heroe.getNombre() + " usa " + h.getNombre() + " sobre " + objetivo.getNombre() + ".";
    }

    private String ejecutarItem(Heroe heroe) {
        if (inventario.estaVacio()) return "El inventario está vacío.";

        List<Item> items = inventario.listarItemsUnicos();
        Item elegido = items.get(new Random().nextInt(items.size()));
        List<Personaje> aliadosVivos = obtenerVivos(heroes);
        Personaje objetivo = aliadosVivos.get(new Random().nextInt(aliadosVivos.size()));

        boolean usado = inventario.usarItem(elegido, heroe, objetivo);
        if (usado)
            return heroe.getNombre() + " usa " + elegido.getNombre() + " en " + objetivo.getNombre() + ".";
        else
            return "No se pudo usar el ítem.";
    }

    // ==============================
    // Métodos de soporte
    // ==============================
    private List<Personaje> obtenerVivos(List<Personaje> lista) {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje p : lista) if (p.estaVivo()) vivos.add(p);
        return vivos;
    }

    private boolean todosMuertos(List<Personaje> lista) {
        for (Personaje p : lista) {
            if (p.estaVivo()) return false;
        }
        return true;
    }

    public List<Personaje> getHeroes() { return heroes; }
    public List<Personaje> getEnemigos() { return enemigos; }
    public boolean isBatallaTerminada() { return batallaTerminada; }
    public Personaje getPersonajeEnTurno() {
    if (ordenTurnos == null || ordenTurnos.isEmpty()) return null;
    return ordenTurnos.get(indiceTurno);
}

}
