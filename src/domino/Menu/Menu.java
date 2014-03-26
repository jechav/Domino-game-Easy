/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domino.Menu;

import domino.sonido;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Jochechavez
 */
public class Menu extends JPanel implements ActionListener{

    
    private Image fondo;
    private Image UnJugador;
    private Image Multijugador;
    private Image Opciones;
    private Image Acerca;
    private Image Salir;
    private Image UnJugadorUn;
    private Image MultijugadorUn;
    private Image OpcionesUn;
    private Image AcercaUn;
    private Image SalirUn;
    private Image foto;
    
    public int op;
    public boolean SwVisible;
    
    private boolean UnJugadorSw;
    private boolean MultijugadorSw;
    private boolean OpcionesSw;
    private boolean AcercaSw;
    private boolean SalirSw;
    private sonido son = new sonido();
    private String nombre;
    /*********Construcctor de la clase Jpane Booard*********/
    public Menu(String nombre) {


        MovingAdapter ma = new MovingAdapter();

        this.addMouseMotionListener(ma);
        this.addMouseListener(ma);

        this.addKeyListener(new TAdapter());

        this.setFocusable(true);
        this.setDoubleBuffered(true);         
        
        this.nombre = nombre;
        fondo = new ImageIcon(this.getClass().getResource("Imagenes/MenuFondo.jpg")).getImage();
       
        UnJugador = new ImageIcon(this.getClass().getResource("Imagenes/UnJugador.png")).getImage();
        Multijugador = new ImageIcon(this.getClass().getResource("Imagenes/Multijugador.png")).getImage();
        Opciones = new ImageIcon(this.getClass().getResource("Imagenes/Opciones.png")).getImage();
        Acerca = new ImageIcon(this.getClass().getResource("Imagenes/Acerca_de.png")).getImage();
        Salir = new ImageIcon(this.getClass().getResource("Imagenes/Salir.png")).getImage();
        
        foto = new ImageIcon(this.getClass().getResource("Imagenes/FotoNadie.png")).getImage();
        
        UnJugadorUn = new ImageIcon(this.getClass().getResource("Imagenes/UnJugadorUn.png")).getImage();
        MultijugadorUn = new ImageIcon(this.getClass().getResource("Imagenes/MultijugadorUn.png")).getImage();
        OpcionesUn = new ImageIcon(this.getClass().getResource("Imagenes/OpcionesUn.png")).getImage();
        AcercaUn = new ImageIcon(this.getClass().getResource("Imagenes/Acerca_deUn.png")).getImage();
        SalirUn = new ImageIcon(this.getClass().getResource("Imagenes/SalirUn.png")).getImage();
        
        SwVisible = true;        
    }

    /****************DIBUJA EN LA PANTALLA, LLAMADA POR actionPerformed ****/
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(fondo, 0, 0, this);        

        g2d.drawImage(foto, 1064, 3, this);    
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.ROMAN_BASELINE, 20));                       
        g2d.drawString(nombre, 1072, 185);
        //Ponemos La imagenes de los Items//
        if(!UnJugadorSw){
            g2d.drawImage(UnJugadorUn, 44, 367, this);        
        }else{
            g2d.drawImage(UnJugador, 44, 367, this);        
        }
        if(!MultijugadorSw){
            g2d.drawImage(MultijugadorUn, 83, 442, this);        
        }else{
            g2d.drawImage(Multijugador, 83, 442, this);        
        }
        if(!OpcionesSw){
            g2d.drawImage(OpcionesUn, 134, 524, this);               
        }else{
            g2d.drawImage(Opciones, 134, 524, this);        
        }
        if(!AcercaSw){
            g2d.drawImage(AcercaUn, 916, 605, this);        
        }else{
            g2d.drawImage(Acerca, 916, 605, this);        
        }
        if(!SalirSw){
            g2d.drawImage(SalirUn, 167, 599, this);        
        }else{
            g2d.drawImage(Salir, 167, 599, this);        
        }                
        
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
        if(!SwVisible){
            return;
        }

    }               
    /*****************EJCUCION DE JUEGO*************/
    public void actionPerformed(ActionEvent e) {


    }


    /************Pausa el juego********/
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

   
        }
    }

    /*Controla la accion del Mause******************/
    class MovingAdapter extends MouseAdapter {

        private int x;
        private int y;

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            
            if (isHit(UnJugador, x, y, 44, 367)){
                if(!UnJugadorSw){
                    son.menuItem();
                }
                UnJugadorSw = true;
                
                //JOptionPane.showMessageDialog(null, "entro ");
                repaint();
            }else{
                UnJugadorSw = false;                
                repaint();
            }
            
            if (isHit(Multijugador, x, y, 83, 442)){
                if(!MultijugadorSw){
                    son.menuItem();
                }
                MultijugadorSw = true;
                //JOptionPane.showMessageDialog(null, "entro ");
                repaint();
            }else{
                MultijugadorSw = false;
                repaint();
            }
            
            if (isHit(Opciones, x, y, 134, 524)){
                if(!OpcionesSw){
                    son.menuItem();
                }                
                OpcionesSw = true;
                //JOptionPane.showMessageDialog(null, "entro ");
                repaint();
            }else{
                OpcionesSw = false;
                repaint();
            }
            
            if (isHit(Acerca, x, y, 916, 605)){
                if(!AcercaSw){
                    son.menuItem();
                }
                AcercaSw = true;
                //JOptionPane.showMessageDialog(null, "entro ");
                repaint();
            }else{
                AcercaSw = false;
                repaint();
            }
            
            if (isHit(Salir, x, y, 167, 599)){
                
                if(!SalirSw){
                    son.menuItem();
                }
                SalirSw = true;
                //JOptionPane.showMessageDialog(null, "entro ");
                repaint();
            }else{
                SalirSw = false;
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            if (isHit(UnJugador, x, y, 44, 367)){
                UnJugadorSw = true;                
                repaint();
                SwVisible = false;
                op = 1;
            }
            
            if (isHit(Multijugador, x, y, 83, 442)){
                MultijugadorSw = true;
                repaint();
                SwVisible = false;
                op =2;
            }
            
            if (isHit(Opciones, x, y, 134, 524)){
                OpcionesSw = true;
                repaint();
                SwVisible = false;
                op =3;
            }
            
            if (isHit(Acerca, x, y, 916, 605)){
                AcercaSw = true;
                repaint();
                SwVisible = false;
                op =4;
            }
            
            if (isHit(Salir, x, y, 167, 599)){
                SalirSw = true;
                repaint();
                SwVisible = false;
                op =5;
            }                                                                     
        }

        @Override
        public void mouseDragged(MouseEvent e) {


        }
    }
    
    private boolean isHit(Image im, int x, int y, int xPi, int yPi) {

        Rectangle rec = new Rectangle(xPi, yPi, im.getWidth(this), im.getHeight(this));

        if (rec.contains(x, y)) {
            return true;
        } else {
            return false;
        }
    }
}

