/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domino.Menu;

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
import javax.swing.JPanel;import java.awt.Graphics;
import javax.swing.ImageIcon;


/**
 *
 * @author Jochechavez
 */
public class Opciones extends JPanel implements ActionListener{

    public int op;
    public boolean SwVisible;

    /*********Construcctor de la clase Jpane Booard*********/
    public Opciones() {

        MovingAdapter ma = new MovingAdapter();

        this.addMouseMotionListener(ma);
        this.addMouseListener(ma);

        this.addKeyListener(new TAdapter());

        this.setFocusable(true);
        this.setDoubleBuffered(true);

        SwVisible = true;
    }

    /****************DIBUJA EN LA PANTALLA, LLAMADA POR actionPerformed ****/
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        //g2d.drawString("Opciones...", 300, 400);
        Image fondo;
        fondo = new ImageIcon(this.getClass().getResource("Imagenes/NoImplet.png")).getImage(); 
        g2d.drawImage(fondo, 0, 0, this);
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
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                SwVisible = false;
            }
        }
    }

    /*Controla la accion del Mause******************/
    class MovingAdapter extends MouseAdapter {

        private int x;
        private int y;

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();


        }

        @Override
        public void mouseDragged(MouseEvent e) {


        }
    }


    public boolean isHit(Image im, int x, int y, int xPi, int yPi) {

        Rectangle rec = new Rectangle(xPi, yPi, im.getWidth(this), im.getHeight(this));

        if (rec.contains(x, y)) {
            return true;
        } else {
            return false;
        }
    }
}

