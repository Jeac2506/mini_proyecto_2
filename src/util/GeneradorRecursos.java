package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * GeneradorRecursos - Genera imágenes placeholder automáticamente
 * Ejecuta esta clase una vez para crear los recursos básicos del juego
 */
public class GeneradorRecursos {
    
    public static void main(String[] args) {
        try {
            // Crear directorios
            new File("src/main/resources/imagenes").mkdirs();
            new File("src/main/resources/sonidos").mkdirs();
            
            // Generar imágenes
            generarFondoAzul();
            generarSpriteHeroe();
            generarSpriteEnemigo();
            
            System.out.println("✅ Recursos generados exitosamente!");
            System.out.println("📁 Ubicación: src/main/resources/");
            
        } catch (IOException e) {
            System.err.println("❌ Error al generar recursos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Genera un fondo azul pixelado estilo Dragon Quest
     */
    private static void generarFondoAzul() throws IOException {
        int size = 64;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        
        // Fondo base azul oscuro
        g.setColor(new Color(20, 20, 60));
        g.fillRect(0, 0, size, size);
        
        // Patrón de cuadrícula
        g.setColor(new Color(40, 40, 100));
        for (int i = 0; i < size; i += 8) {
            g.drawLine(i, 0, i, size);
            g.drawLine(0, i, size, i);
        }
        
        // Detalles decorativos (puntos aleatorios)
        g.setColor(new Color(60, 60, 140));
        for (int i = 0; i < 20; i++) {
            int x = (int)(Math.random() * size);
            int y = (int)(Math.random() * size);
            g.fillRect(x, y, 2, 2);
        }
        
        g.dispose();
        
        File output = new File("src/main/resources/imagenes/fondo_azul.png");
        ImageIO.write(img, "PNG", output);
        System.out.println("✅ Generado: fondo_azul.png");
    }
    
    /**
     * Genera un sprite simple de héroe (32x32 píxeles)
     */
    private static void generarSpriteHeroe() throws IOException {
        int size = 32;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        // Fondo transparente
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, size, size);
        g.setComposite(AlphaComposite.SrcOver);
        
        // Cuerpo (rectángulo azul)
        g.setColor(new Color(50, 100, 200));
        g.fillRect(12, 12, 8, 12);
        
        // Cabeza (círculo beige)
        g.setColor(new Color(255, 220, 180));
        g.fillOval(10, 6, 12, 12);
        
        // Brazos
        g.setColor(new Color(50, 100, 200));
        g.fillRect(8, 14, 4, 8);
        g.fillRect(20, 14, 4, 8);
        
        // Piernas
        g.fillRect(12, 24, 3, 6);
        g.fillRect(17, 24, 3, 6);
        
        // Espada (gris)
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(24, 16, 2, 10);
        g.fillRect(23, 15, 4, 2);
        
        g.dispose();
        
        File output = new File("src/main/resources/imagenes/heroe_sprite.png");
        ImageIO.write(img, "PNG", output);
        System.out.println("✅ Generado: heroe_sprite.png");
    }
    
    /**
     * Genera un sprite simple de enemigo (32x32 píxeles)
     */
    private static void generarSpriteEnemigo() throws IOException {
        int size = 32;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        // Fondo transparente
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, size, size);
        g.setComposite(AlphaComposite.SrcOver);
        
        // Cuerpo del monstruo (rojo/morado)
        g.setColor(new Color(150, 50, 150));
        g.fillOval(8, 10, 16, 16);
        
        // Ojos (amarillos brillantes)
        g.setColor(Color.YELLOW);
        g.fillOval(11, 14, 4, 4);
        g.fillOval(17, 14, 4, 4);
        
        // Pupilas
        g.setColor(Color.RED);
        g.fillOval(12, 15, 2, 2);
        g.fillOval(18, 15, 2, 2);
        
        // Boca (línea roja)
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        g.drawArc(12, 18, 8, 6, 0, -180);
        
        // Cuernos
        g.setColor(new Color(100, 30, 100));
        int[] xCuernoIzq = {10, 8, 12};
        int[] yCuernoIzq = {10, 4, 8};
        g.fillPolygon(xCuernoIzq, yCuernoIzq, 3);
        
        int[] xCuernoDer = {22, 24, 20};
        int[] yCuernoDer = {10, 4, 8};
        g.fillPolygon(xCuernoDer, yCuernoDer, 3);
        
        g.dispose();
        
        File output = new File("src/main/resources/imagenes/enemigo_sprite.png");
        ImageIO.write(img, "PNG", output);
        System.out.println("✅ Generado: enemigo_sprite.png");
    }
    
    /**
     * Genera un ícono de ítem (poción)
     */
    public static void generarIconoPocion() throws IOException {
        int size = 24;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo transparente
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, size, size);
        g.setComposite(AlphaComposite.SrcOver);
        
        // Botella (contorno)
        g.setColor(new Color(100, 100, 150));
        g.fillRoundRect(8, 10, 8, 10, 4, 4);
        
        // Contenido (rojo para poción de vida)
        g.setColor(new Color(255, 50, 50));
        g.fillRoundRect(9, 13, 6, 7, 3, 3);
        
        // Tapón
        g.setColor(new Color(139, 69, 19));
        g.fillRect(10, 8, 4, 3);
        
        g.dispose();
        
        File output = new File("src/main/resources/imagenes/pocion_icon.png");
        ImageIO.write(img, "PNG", output);
        System.out.println("✅ Generado: pocion_icon.png");
    }
}