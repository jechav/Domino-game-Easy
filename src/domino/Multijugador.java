/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domino;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
 * 
 */
public class Multijugador extends JPanel implements ActionListener{
    Image mu ;
    public int op;
    public boolean SwVisible;
     Image esc,esc2;
    public boolean EVisible = true;
    int escy =600,escx = 9;
    /*********Construcctor de la clase Jpane mult*********/
    public Multijugador() {
 MovingAdapter ma = new MovingAdapter();

        this.addMouseMotionListener(ma);
        this.addMouseListener(ma);

        this.addKeyListener(new TAdapter());

        this.setFocusable(true);
        this.setDoubleBuffered(true);

         mu = new ImageIcon("src/domino/Menu/Imagenes/mu.jpg").getImage();
         esc = new ImageIcon("src/domino/Menu/Imagenes/esca.png").getImage();
         esc2 =  new ImageIcon("src/domino/Menu/Imagenes/esca2.png").getImage();
        SwVisible = true;
    }

    /****************DIBUJA EN LA PANTALLA, LLAMADA POR actionPerformed ****/
     @Override
    public void paint(Graphics g) {

        super.paint(g);

     Graphics2D g2d = (Graphics2D) g;
     Image fondo;
     fondo = new ImageIcon(this.getClass().getResource("Imagenes/NoImplet.png")).getImage(); 
     g2d.drawImage(fondo, 0, 0, this);    

     // g2d.drawImage(mu, 0, 0, this);        

        if(EVisible){
             g2d.drawImage(esc,escx,escy,this);

        }else{
          g2d.drawImage(esc2,escx-9,escy-9,this);
        }
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
            x = e.getX();
            y = e.getY();


            if (isHit(esc, x, y, escx, escy)) {
                EVisible = false;
                repaint();

            } else {

                EVisible = true;
                repaint();
        }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();

            if (isHit(esc2, x, y, escx, escy)) {

                SwVisible = false;
                 }
        }
        @Override
        public void mouseDragged(MouseEvent e) {


        }
    }

    /************HILO QUE CONTROLA EL TIEMPO Y EL DIBUJADO CADA SEGUNDO********/
  
    public boolean isHit(Image im, int x, int y, int xPi, int yPi) {

        Rectangle rec = new Rectangle(xPi, yPi, im.getWidth(this), im.getHeight(this));

        if (rec.contains(x, y)) {
            return true;
        } else {
            return false;
        }
    }
}

