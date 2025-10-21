package gui;

import config.ConfiguracionJuego;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * VentanaOpciones - Permite al jugador ajustar la configuración del juego
 */
public class VentanaOpciones extends JDialog {
    private ConfiguracionJuego config;
    private Image fondo;
    
    // Componentes
    private JSlider sliderMusica, sliderEfectos;
    private JComboBox<String> comboDificultad;
    private JCheckBox checkTutorial;
    private JLabel lblVolumenMusica, lblVolumenEfectos;
    
    public VentanaOpciones(JFrame padre) {
        super(padre, "Opciones", true); // Modal
        
        config = ConfiguracionJuego.obtenerInstancia();
        fondo = new ImageIcon(getClass().getResource("/imagenes/fondo_azul.png")).getImage();
        
        setSize(600, 500);
        setLocationRelativeTo(padre);
        setResizable(false);
        
        crearInterfaz();
    }
    
    private void crearInterfaz() {
        // Panel principal con fondo
        JPanel panelPrincipal = new JPanel() {
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
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("⚙️ OPCIONES", SwingConstants.CENTER);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 24));
        titulo.setForeground(Color.YELLOW);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Panel de configuraciones
        JPanel panelConfig = new JPanel();
        panelConfig.setLayout(new BoxLayout(panelConfig, BoxLayout.Y_AXIS));
        panelConfig.setOpaque(false);
        
        // ========== SECCIÓN DE AUDIO ==========
        JPanel panelAudio = crearSeccion("🔊 AUDIO");
        
        // Volumen de música
        JPanel panelVolMusica = new JPanel(new BorderLayout(10, 5));
        panelVolMusica.setOpaque(false);
        lblVolumenMusica = new JLabel("Volumen Música: " + config.getVolumenMusica() + "%");
        lblVolumenMusica.setForeground(Color.WHITE);
        lblVolumenMusica.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        sliderMusica = new JSlider(0, 100, config.getVolumenMusica());
        sliderMusica.setOpaque(false);
        sliderMusica.setForeground(Color.CYAN);
        sliderMusica.addChangeListener((ChangeEvent e) -> {
            int valor = sliderMusica.getValue();
            lblVolumenMusica.setText("Volumen Música: " + valor + "%");
            config.setVolumenMusica(valor);
        });
        
        panelVolMusica.add(lblVolumenMusica, BorderLayout.NORTH);
        panelVolMusica.add(sliderMusica, BorderLayout.CENTER);
        panelAudio.add(panelVolMusica);
        
        // Volumen de efectos
        JPanel panelVolEfectos = new JPanel(new BorderLayout(10, 5));
        panelVolEfectos.setOpaque(false);
        lblVolumenEfectos = new JLabel("Volumen Efectos: " + config.getVolumenEfectos() + "%");
        lblVolumenEfectos.setForeground(Color.WHITE);
        lblVolumenEfectos.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        sliderEfectos = new JSlider(0, 100, config.getVolumenEfectos());
        sliderEfectos.setOpaque(false);
        sliderEfectos.setForeground(Color.CYAN);
        sliderEfectos.addChangeListener((ChangeEvent e) -> {
            int valor = sliderEfectos.getValue();
            lblVolumenEfectos.setText("Volumen Efectos: " + valor + "%");
            config.setVolumenEfectos(valor);
        });
        
        panelVolEfectos.add(lblVolumenEfectos, BorderLayout.NORTH);
        panelVolEfectos.add(sliderEfectos, BorderLayout.CENTER);
        panelAudio.add(panelVolEfectos);
        
        panelConfig.add(panelAudio);
        panelConfig.add(Box.createVerticalStrut(20));
        
        // ========== SECCIÓN DE JUGABILIDAD ==========
        JPanel panelJugabilidad = crearSeccion("🎮 JUGABILIDAD");
        
        // Dificultad
        JPanel panelDificultad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDificultad.setOpaque(false);
        JLabel lblDificultad = new JLabel("Dificultad: ");
        lblDificultad.setForeground(Color.WHITE);
        lblDificultad.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        String[] dificultades = {"Fácil", "Normal", "Difícil"};
        comboDificultad = new JComboBox<>(dificultades);
        comboDificultad.setSelectedItem(config.getDificultad());
        comboDificultad.setFont(new Font("Monospaced", Font.PLAIN, 14));
        comboDificultad.addActionListener(e -> {
            config.setDificultad((String) comboDificultad.getSelectedItem());
        });
        
        panelDificultad.add(lblDificultad);
        panelDificultad.add(comboDificultad);
        panelJugabilidad.add(panelDificultad);
        
        // Tutorial
        checkTutorial = new JCheckBox("Mostrar tutorial al iniciar", config.isMostrarTutorial());
        checkTutorial.setForeground(Color.WHITE);
        checkTutorial.setFont(new Font("Monospaced", Font.PLAIN, 14));
        checkTutorial.setOpaque(false);
        checkTutorial.addActionListener(e -> {
            config.setMostrarTutorial(checkTutorial.isSelected());
        });
        panelJugabilidad.add(checkTutorial);
        
        panelConfig.add(panelJugabilidad);
        panelConfig.add(Box.createVerticalStrut(20));
        
        // ========== PANEL DE BOTONES ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);
        
        JButton btnGuardar = crearBoton("💾 GUARDAR");
        btnGuardar.addActionListener(e -> {
            config.guardarConfiguracion();
            JOptionPane.showMessageDialog(this, 
                "✅ Configuración guardada correctamente", 
                "Guardado", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton btnRestaurar = crearBoton("🔄 RESTAURAR");
        btnRestaurar.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Restaurar configuración por defecto?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                config.restaurarPorDefecto();
                actualizarComponentes();
                JOptionPane.showMessageDialog(this,
                    "✅ Configuración restaurada",
                    "Restaurado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JButton btnCerrar = crearBoton("↩️ VOLVER");
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnRestaurar);
        panelBotones.add(btnCerrar);
        
        // Ensamblar todo
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        
        JScrollPane scrollConfig = new JScrollPane(panelConfig);
        scrollConfig.setOpaque(false);
        scrollConfig.getViewport().setOpaque(false);
        scrollConfig.setBorder(null);
        panelPrincipal.add(scrollConfig, BorderLayout.CENTER);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Crea una sección con título y contenedor
     */
    private JPanel crearSeccion(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.CYAN, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblTitulo.setForeground(Color.CYAN);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        
        return panel;
    }
    
    /**
     * Crea un botón con el estilo del juego
     */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(30, 30, 80));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Monospaced", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        boton.setPreferredSize(new Dimension(150, 40));
        return boton;
    }
    
    /**
     * Actualiza los componentes con los valores actuales de la configuración
     */
    private void actualizarComponentes() {
        sliderMusica.setValue(config.getVolumenMusica());
        sliderEfectos.setValue(config.getVolumenEfectos());
        comboDificultad.setSelectedItem(config.getDificultad());
        checkTutorial.setSelected(config.isMostrarTutorial());
        
        lblVolumenMusica.setText("Volumen Música: " + config.getVolumenMusica() + "%");
        lblVolumenEfectos.setText("Volumen Efectos: " + config.getVolumenEfectos() + "%");
    }
}