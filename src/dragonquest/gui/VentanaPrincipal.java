package dragonquest.gui;

import dragonquest.combate.BatallaGUI;
import dragonquest.personajes.*;
import javax.swing.*;
import java.awt.*;

/**
 * VentanaPrincipal - Interfaz gráfica del juego Dragon Quest VIII.
 * Controla el flujo de turnos de manera alternada (Héroe -> Enemigo -> ...),
 * mostrando en pantalla de quién es el turno actual.
 */
public class VentanaPrincipal extends JFrame {

    private JTextArea areaTexto;
    private JButton btnAtacar, btnHabilidad, btnItem;
    private JPanel panelHeroes, panelEnemigos;
    private BatallaGUI batalla;

    public VentanaPrincipal(BatallaGUI batalla) {
        this.batalla = batalla;
        inicializarComponentes();
        mostrarTurnoActual();
    }

    private void inicializarComponentes() {
        setTitle("Dragon Quest VIII - Batalla");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(180, 150, 100));

        // Panel héroes (izquierda)
        panelHeroes = new JPanel();
        panelHeroes.setLayout(new BoxLayout(panelHeroes, BoxLayout.Y_AXIS));
        panelHeroes.setBackground(new Color(40, 30, 20));
        panelHeroes.setBorder(BorderFactory.createTitledBorder("Héroes"));
        add(panelHeroes, BorderLayout.WEST);

        // Panel enemigos (derecha)
        panelEnemigos = new JPanel();
        panelEnemigos.setLayout(new BoxLayout(panelEnemigos, BoxLayout.Y_AXIS));
        panelEnemigos.setBackground(new Color(40, 30, 20));
        panelEnemigos.setBorder(BorderFactory.createTitledBorder("Enemigos"));
        add(panelEnemigos, BorderLayout.EAST);

        // Área central (texto del combate)
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setFont(new Font("Serif", Font.PLAIN, 16));
        areaTexto.setBackground(new Color(50, 40, 30));
        areaTexto.setForeground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior (botones)
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 15, 15));
        panelBotones.setBackground(new Color(60, 50, 35));

        btnAtacar = new JButton("Atacar");
        btnHabilidad = new JButton("Usar Habilidad");
        btnItem = new JButton("Usar Ítem");

        Font font = new Font("Serif", Font.BOLD, 16);
        btnAtacar.setFont(font);
        btnHabilidad.setFont(font);
        btnItem.setFont(font);

        panelBotones.add(btnAtacar);
        panelBotones.add(btnHabilidad);
        panelBotones.add(btnItem);
        add(panelBotones, BorderLayout.SOUTH);

        actualizarPaneles();

        // ====== EVENTOS DE BOTONES ======
        btnAtacar.addActionListener(e -> ejecutarTurno("atacar"));
        btnHabilidad.addActionListener(e -> ejecutarTurno("habilidad"));
        btnItem.addActionListener(e -> ejecutarTurno("item"));
    }

    /** Muestra el personaje en turno actual */
    private void mostrarTurnoActual() {
        Personaje actual = batalla.getPersonajeEnTurno();
        if (actual == null) return;

        areaTexto.append("----------------------------------------------------\n");
        areaTexto.append("Turno de " + actual.getNombre() +
                " (HP: " + actual.getHpActual() + ")\n");

        // Si es héroe → permitir acción manual
        if (actual instanceof Heroe) {
            activarBotones(true);
            areaTexto.append("Es tu turno. Elige una acción.\n");
        } 
        // Si es enemigo → realiza su turno y pasa al siguiente personaje
        else {
            activarBotones(false);
            // Esperamos un momento antes de ejecutar para que se vea fluido
            new javax.swing.Timer(1000, e -> {
                String log = batalla.ejecutarTurnoJugador("enemigo");
                areaTexto.append(log + "\n");
                actualizarPaneles();
                verificarFinDeBatalla();

                // Avanzar al siguiente turno (héroe o enemigo)
                mostrarTurnoActual();
            }).setRepeats(false); // Evita que se repita infinitamente
        }
    }


    /** Ejecuta la acción del jugador en su turno */
    private void ejecutarTurno(String tipoAccion) {
        if (batalla.isBatallaTerminada()) {
            JOptionPane.showMessageDialog(this, "⚔️ La batalla ya ha terminado.", "Fin del combate", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String resultado = batalla.ejecutarTurnoJugador(tipoAccion);
        areaTexto.append(resultado + "\n\n");
        actualizarPaneles();

        verificarFinDeBatalla();
        mostrarTurnoActual();
    }

    /** Verifica si alguien ganó o perdió */
    private void verificarFinDeBatalla() {
        if (todosMuertos(batalla.getEnemigos())) {
            JOptionPane.showMessageDialog(this, "🎉 ¡Victoria! Los héroes han ganado el combate.", "Fin del combate", JOptionPane.INFORMATION_MESSAGE);
            activarBotones(false);
        } else if (todosMuertos(batalla.getHeroes())) {
            JOptionPane.showMessageDialog(this, "💀 Derrota... Los héroes han sido vencidos.", "Fin del combate", JOptionPane.ERROR_MESSAGE);
            activarBotones(false);
        }
    }

    /** Activa o desactiva los botones según turno */
    private void activarBotones(boolean activo) {
        btnAtacar.setEnabled(activo);
        btnHabilidad.setEnabled(activo);
        btnItem.setEnabled(activo);
    }

    /** Comprueba si todos los personajes de una lista están muertos */
    private boolean todosMuertos(java.util.List<Personaje> lista) {
        for (Personaje p : lista) {
            if (p.estaVivo()) return false;
        }
        return true;
    }

    /** Refresca las barras de HP */
    private void actualizarPaneles() {
        panelHeroes.removeAll();
        panelEnemigos.removeAll();

        for (var h : batalla.getHeroes()) {
            JProgressBar barraHP = new JProgressBar(0, h.getHpMax());
            barraHP.setValue(h.getHpActual());
            barraHP.setStringPainted(true);
            barraHP.setString(h.getNombre() + " HP: " + h.getHpActual());
            panelHeroes.add(barraHP);
        }

        for (var e : batalla.getEnemigos()) {
            JProgressBar barraHP = new JProgressBar(0, e.getHpMax());
            barraHP.setValue(e.getHpActual());
            barraHP.setStringPainted(true);
            barraHP.setString(e.getNombre() + " HP: " + e.getHpActual());
            panelEnemigos.add(barraHP);
        }

        panelHeroes.revalidate();
        panelEnemigos.revalidate();
        panelHeroes.repaint();
        panelEnemigos.repaint();
    }
}
