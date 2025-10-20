package dragonquest.combate;

import dragonquest.personajes.*;
import dragonquest.items.*;
import java.util.*;

/**
 * BatallaGUI - versión adaptada de Batalla para usarse con interfaz Swing.
 * No modifica la lógica original, solo elimina dependencias de Scanner
 * y devuelve cadenas de texto para mostrar en la GUI.
 */
public class BatallaGUI {

    private List<Personaje> heroes;
    private List<Personaje> enemigos;
    private InventarioGrupo inventario;

    public BatallaGUI() {
        heroes = new ArrayList<>();
        enemigos = new ArrayList<>();
        inventario = new InventarioGrupo();
        inicializar();
        System.out.println("✅ Se inicializaron " + heroes.size() + " héroes y " + enemigos.size() + " enemigos.");

    }

    /**
     * Inicializa los mismos héroes y enemigos que en Batalla original.
     */
    private void inicializar() {
        // Héroes
        Heroe heroe = new Heroe("Héroe", 100, 50, 20, 10, 15);
        heroe.agregarHabilidad(new dragonquest.habilidades.DanioMagico("Bola de Fuego", 10, 30));
        heroe.agregarHabilidad(new dragonquest.habilidades.Curacion("Curar", 8, 25));

        Heroe yangus = new Heroe("Yangus", 120, 30, 25, 15, 10);
        yangus.agregarHabilidad(new dragonquest.habilidades.GolpeCritico("Hachazo Brutal", 5));
        yangus.agregarHabilidad(new dragonquest.habilidades.Aturdimiento("Golpe Aturdidor", 8));

        Heroe jessica = new Heroe("Jessica", 80, 70, 18, 8, 20);
        jessica.agregarHabilidad(new dragonquest.habilidades.DanioMagico("Rayo", 12, 35));
        jessica.agregarHabilidad(new dragonquest.habilidades.Veneno("Toxina", 8));

        Heroe angelo = new Heroe("Angelo", 90, 60, 22, 12, 18);
        angelo.agregarHabilidad(new dragonquest.habilidades.Curacion("Bendición", 10, 30));

        heroes.add(heroe);
        heroes.add(yangus);
        heroes.add(jessica);
        heroes.add(angelo);

        // Enemigos
        Enemigo dragoon = new Enemigo("Dragoon", 110, 20, 25, 10, 17, "agresivo");
        Enemigo fantasma = new Enemigo("Fantasma", 85, 40, 18, 8, 21, "estratégico");
        Enemigo golem = new Enemigo("Golem de Piedra", 150, 10, 20, 18, 5, "defensivo");
        Enemigo slime = new Enemigo("Slime Metálico", 50, 30, 15, 25, 30, "evasivo");

        enemigos.add(dragoon);
        enemigos.add(fantasma);
        enemigos.add(golem);
        enemigos.add(slime);

        // Inventario compartido
        inventario.agregarItem(new PocionCuracion("Poción pequeña", 30), 5);
        inventario.agregarItem(new PocionCuracion("Poción media", 60), 3);
        inventario.agregarItem(new Antidoto("Antídoto"), 3);
        inventario.agregarItem(new PocionMagia("Éter", 20), 2);
    }

    // ========================
    // Métodos llamados desde la GUI
    // ========================

    public String jugadorAtaca() {
        Heroe heroe = (Heroe) heroes.get(0); // Por ahora, el héroe principal actúa
        List<Personaje> enemigosVivos = obtenerVivos(enemigos);
        if (enemigosVivos.isEmpty()) return "No quedan enemigos.";

        Personaje objetivo = enemigosVivos.get(new Random().nextInt(enemigosVivos.size()));
        objetivo.recibirDaño(heroe.getAtaque());
        return heroe.getNombre() + " atacó a " + objetivo.getNombre() + " causando daño.";
    }

    public String jugadorUsaHabilidad() {
        Heroe heroe = (Heroe) heroes.get(0); // ← cambio aquí
        if (heroe.getHabilidades().isEmpty()) return "No tienes habilidades disponibles.";

        dragonquest.habilidades.Habilidad h = heroe.getHabilidades().get(0);
        List<Personaje> enemigosVivos = obtenerVivos(enemigos);
        if (enemigosVivos.isEmpty()) return "No quedan enemigos.";

        Personaje objetivo = enemigosVivos.get(new Random().nextInt(enemigosVivos.size()));
        h.ejecutar(heroe, objetivo);
        return heroe.getNombre() + " usó " + h.getNombre() + " sobre " + objetivo.getNombre() + ".";
    }



    public String jugadorUsaItem() {
        Heroe heroe = (Heroe) heroes.get(0); // ← cambio aquí
        if (inventario.estaVacio()) return "El inventario está vacío.";

        List<Item> items = inventario.listarItemsUnicos();
        Item elegido = items.get(0);
        List<Personaje> aliadosVivos = obtenerVivos(heroes);
        Personaje objetivo = aliadosVivos.get(new Random().nextInt(aliadosVivos.size()));

        boolean usado = inventario.usarItem(elegido, heroe, objetivo);
        if (usado)
            return heroe.getNombre() + " usó " + elegido.getNombre() + " sobre " + objetivo.getNombre() + ".";
        else
            return "No se pudo usar el ítem.";
    }


    // ========================
    // Métodos de apoyo
    // ========================

    private List<Personaje> obtenerVivos(List<Personaje> lista) {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje p : lista) if (p.estaVivo()) vivos.add(p);
        return vivos;
    }

    public List<Personaje> getHeroes() { return heroes; }
    public List<Personaje> getEnemigos() { return enemigos; }
}
