package gui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class VentanaPrincipal extends JFrame {
    private JButton btnIniciar, btnSalir;
    private JLabel titulo;
    private Clip clipMusica; // Para reproducir música
    private Image fondo;

    public VentanaPrincipal() {
        setTitle("Dragon Quest - Mini Proyecto");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

    URL ruta = getClass().getResource("/imagenes/fondo_azul.png");
    System.out.println("Ruta del fondo: " + ruta);
    fondo = new ImageIcon(ruta).getImage();

        // Cargar fondo pixelado
        fondo = new ImageIcon(getClass().getResource("/imagenes/fondo_azul.png")).getImage();


        // Panel personalizado que pinta el fondo
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int x = 0; x < getWidth(); x += fondo.getWidth(null)) {
                    for (int y = 0; y < getHeight(); y += fondo.getHeight(null)) {
                        g.drawImage(fondo, x, y, this);
                    }
                }
            }
        };
        panelFondo.setLayout(new BorderLayout());

        // Fuente retro
        Font fuente = new Font("Monospaced", Font.BOLD, 18);

        // Título del juego
        titulo = new JLabel("DRAGON QUEST");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 28));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));

        // Botón "Iniciar combate"
        btnIniciar = crearBoton("▶ INICIAR COMBATE", fuente);

        // Botón "Salir"
        btnSalir = crearBoton("✖ SALIR", fuente);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(2, 1, 0, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 100, 40, 100));
        panelBotones.add(btnIniciar);
        panelBotones.add(btnSalir);

        // Agregar al panel de fondo
        panelFondo.add(titulo, BorderLayout.NORTH);
        panelFondo.add(panelBotones, BorderLayout.CENTER);
        add(panelFondo);

        // Acciones
        btnIniciar.addActionListener((ActionEvent e) -> {
            detenerMusica();
            dispose();
            new VentanaCombate().setVisible(true);
        });

        btnSalir.addActionListener(e -> {
            detenerMusica();
            System.exit(0);
        });

        reproducirMusica("/sonidos/musica_menu.wav");
    }

    // Crea un botón con el estilo retro
    private JButton crearBoton(String texto, Font fuente) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(30, 30, 80));
        boton.setForeground(Color.WHITE);
        boton.setFont(fuente);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return boton;
    }

    // Método para reproducir música en bucle
    private void reproducirMusica(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            if (url == null) {
                System.err.println("No se encontró el archivo de música: " + ruta);
                return;
            }
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
            clipMusica = AudioSystem.getClip();
            clipMusica.open(audioInput);
            clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
            clipMusica.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Detiene la música al salir
    private void detenerMusica() {
        if (clipMusica != null && clipMusica.isRunning()) {
            clipMusica.stop();
            clipMusica.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
