package gui;

import config.ConfiguracionJuego;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class VentanaPrincipal extends JFrame {
    private JButton btnIniciar, btnOpciones, btnSalir;
    private JLabel titulo;
    private Clip clipMusica;
    private Image fondo;
    private ConfiguracionJuego config;

    public VentanaPrincipal() {
        config = ConfiguracionJuego.obtenerInstancia();
        
        setTitle("Dragon Quest VIII - Simulador de Combate");
        Dimension dim = config.getDimensionVentana();
        setSize(dim.width, dim.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Cargar fondo
        try {
            URL ruta = getClass().getResource("/imagenes/fondo_azul.png");
            if (ruta != null) {
                fondo = new ImageIcon(ruta).getImage();
            } else {
                System.err.println("⚠️ No se encontró el fondo, usando color sólido");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error al cargar fondo: " + e.getMessage());
        }

        // Panel personalizado con fondo
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondo != null) {
                    for (int x = 0; x < getWidth(); x += fondo.getWidth(null)) {
                        for (int y = 0; y < getHeight(); y += fondo.getHeight(null)) {
                            g.drawImage(fondo, x, y, this);
                        }
                    }
                } else {
                    // Color sólido como fallback
                    g.setColor(new Color(30, 30, 80));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout());

        // Fuente retro
        Font fuente = new Font("Monospaced", Font.BOLD, 18);

        // Título del juego con efecto de sombra
        JPanel panelTitulo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                Font fuenteTitulo = new Font("Monospaced", Font.BOLD, 36);
                g2d.setFont(fuenteTitulo);
                
                String texto = "DRAGON QUEST VIII";
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(texto)) / 2;
                int y = getHeight() / 2;
                
                // Sombra
                g2d.setColor(Color.BLACK);
                g2d.drawString(texto, x + 3, y + 3);
                
                // Texto principal
                g2d.setColor(Color.YELLOW);
                g2d.drawString(texto, x, y);
            }
        };
        panelTitulo.setOpaque(false);
        panelTitulo.setPreferredSize(new Dimension(800, 150));

        // Subtítulo
        titulo = new JLabel("~ Simulador de Combate ~", SwingConstants.CENTER);
        titulo.setForeground(Color.CYAN);
        titulo.setFont(new Font("Monospaced", Font.ITALIC, 16));
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(titulo, BorderLayout.SOUTH);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));

        // Botones del menú
        btnIniciar = crearBoton("▶ INICIAR COMBATE", fuente);
        btnOpciones = crearBoton("⚙️ OPCIONES", fuente);
        btnSalir = crearBoton("✖ SALIR", fuente);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(3, 1, 0, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 150, 60, 150));
        panelBotones.add(btnIniciar);
        panelBotones.add(btnOpciones);
        panelBotones.add(btnSalir);

        // Panel inferior con información
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setOpaque(false);
        JLabel lblVersion = new JLabel("v1.0 | Proyecto Universitario");
        lblVersion.setForeground(new Color(150, 150, 150));
        lblVersion.setFont(new Font("Monospaced", Font.PLAIN, 11));
        panelInferior.add(lblVersion);

        // Agregar al panel de fondo
        panelFondo.add(panelSuperior, BorderLayout.NORTH);
        panelFondo.add(panelBotones, BorderLayout.CENTER);
        panelFondo.add(panelInferior, BorderLayout.SOUTH);
        add(panelFondo);

        // Acciones de los botones
        btnIniciar.addActionListener((ActionEvent e) -> {
            detenerMusica();
            dispose();
            new VentanaCombate().setVisible(true);
        });

        btnOpciones.addActionListener(e -> {
            VentanaOpciones ventanaOpciones = new VentanaOpciones(this);
            ventanaOpciones.setVisible(true);
            // Recargar configuración por si cambió
            config = ConfiguracionJuego.obtenerInstancia();
        });

        btnSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (respuesta == JOptionPane.YES_OPTION) {
                detenerMusica();
                System.exit(0);
            }
        });

        // Reproducir música (si está disponible)
        reproducirMusica("/sonidos/musica_menu.wav");
        
        // Mostrar tutorial si está activado
        if (config.isMostrarTutorial()) {
            mostrarTutorialInicial();
        }
    }

    /**
     * Crea un botón con el estilo retro del juego
     */
    private JButton crearBoton(String texto, Font fuente) {
        JButton boton = new JButton(texto);
        boton.setBackground(config.getColorPrimario());
        boton.setForeground(config.getColorTexto());
        boton.setFont(fuente);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(config.getColorSecundario());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(config.getColorPrimario());
            }
        });
        
        return boton;
    }

    /**
     * Reproduce música de fondo en bucle
     */
    private void reproducirMusica(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            if (url == null) {
                System.err.println("⚠️ No se encontró música: " + ruta);
                return;
            }
            
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
            clipMusica = AudioSystem.getClip();
            clipMusica.open(audioInput);
            
            // Ajustar volumen según configuración
            FloatControl volumen = (FloatControl) clipMusica.getControl(FloatControl.Type.MASTER_GAIN);
            float db = (float) (Math.log(config.getVolumenMusica() / 100.0) / Math.log(10.0) * 20.0);
            volumen.setValue(db);
            
            clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
            clipMusica.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("⚠️ Error al reproducir música: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("⚠️ Control de volumen no disponible");
        }
    }

    /**
     * Detiene la música
     */
    private void detenerMusica() {
        if (clipMusica != null && clipMusica.isRunning()) {
            clipMusica.stop();
            clipMusica.close();
        }
    }

    /**
     * Muestra un diálogo de tutorial al iniciar por primera vez
     */
    private void mostrarTutorialInicial() {
        SwingUtilities.invokeLater(() -> {
            String mensaje = """
                ¡Bienvenido a Dragon Quest VIII - Simulador de Combate!
                
                📖 CONTROLES:
                • Usa el ratón para seleccionar acciones
                • Elige entre Atacar, Habilidades o Ítems
                • Los turnos se ordenan por velocidad
                
                ⚔️ CONSEJOS:
                • Mantén a tu equipo con HP alto
                • Usa ítems para curar estados alterados
                • Observa los patrones de ataque enemigos
                
                ¡Buena suerte, aventurero!
                """;
            
            JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Tutorial - Primeros Pasos",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    public static void main(String[] args) {
        // Configurar Look & Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("⚠️ No se pudo establecer Look & Feel: " + e.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}