package dragonquest.gui;

import dragonquest.combate.BatallaGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * VentanaPrincipal - Interfaz gráfica del juego Dragon Quest VIII.
 * Se conecta directamente con BatallaGUI para ejecutar las acciones
 * reales del héroe (Atacar, Usar Habilidad, Usar Ítem).
 */
public class VentanaPrincipal extends JFrame {

    private JTextArea areaTexto;
    private JButton btnAtacar, btnHabilidad, btnItem;
    private JPanel panelHeroes, panelEnemigos;
    private BatallaGUI batalla;

    public VentanaPrincipal(BatallaGUI batalla) {
        this.batalla = batalla;
        inicializarComponentes();
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

        configurarEventos();
        actualizarPaneles(); // mostrar héroes y enemigos al iniciar
    }

    private void configurarEventos() {
        btnAtacar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = batalla.jugadorAtaca();
                areaTexto.append(resultado + "\n");
                actualizarPaneles();
            }
        });

        btnHabilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = batalla.jugadorUsaHabilidad();
                areaTexto.append(resultado + "\n");
                actualizarPaneles();
            }
        });

        btnItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = batalla.jugadorUsaItem();
                areaTexto.append(resultado + "\n");
                actualizarPaneles();
            }
        });
    }

    private void actualizarPaneles() {
        // Actualizar paneles de héroes y enemigos
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
