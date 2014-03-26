/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domino.Menu;

import java.awt.Desktop;
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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Jochechavez
 */
public class Acerca_de extends JPanel implements ActionListener{
    Image fon;
    Image esc,esc2,s,c;
    public int op;
    public boolean SwVisible;
    public boolean EVisible = true;
    public boolean es = true;
    int escy =600,escx = 9;
    /*********Construcctor de la clase Jpane Booard*********/
    public Acerca_de() {

        MovingAdapter ma = new MovingAdapter();

        this.addMouseMotionListener(ma);
        this.addMouseListener(ma);

        this.addKeyListener(new TAdapter());

        this.setFocusable(true);
        this.setDoubleBuffered(true);
       fon = new ImageIcon(this.getClass().getResource("Imagenes/fon.jpg")).getImage();
       esc = new ImageIcon(this.getClass().getResource("Imagenes/esc.jpg")).getImage();
       s = new ImageIcon(this.getClass().getResource("Imagenes/Boton1.png")).getImage();
       c = new ImageIcon(this.getClass().getResource("Imagenes/Boton2.png")).getImage();
       esc2 =  new ImageIcon(this.getClass().getResource("Imagenes/esc2.png")).getImage();
       SwVisible = true;
     }

    /****************DIBUJA EN LA PANTALLA, LLAMADA POR actionPerformed ****/


    @Override
    public void paint(Graphics g) {

        super.paint(g);
        
     Graphics2D g2d = (Graphics2D) g;
       
        g2d.drawImage(fon, 0, 0, this);


        if(EVisible){
             g2d.drawImage(esc,escx,escy,this);
       
        }else{
          g2d.drawImage(esc2,escx-9,escy-9,this);
        }

         if (es){
           g2d.drawImage(s,1050,15,this);

        }else{
          g2d.drawImage(c,1050,15,this);
        }

        if(!SwVisible){
            return;

        }
}


     
       
       

    

    /*****************EJCUCION DE JUEGO*************/
    public void actionPerformed(ActionEvent e) {


    }


    /************para retornar al menu principal********/
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
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
            if(isHit(esc,x ,y, escx,escy ))  {
            EVisible = false;
             repaint();

            }else{

             EVisible = true;
             repaint();

            }

          if(isHit(s,x, y,1050 ,15 ))  {
            es = false;
             repaint();

            }else{

             es = true;
             repaint();


          }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            if(isHit(esc2, x ,y, escx,escy)){
               SwVisible = false;



            }
    if(isHit(c, x ,y,1050,15)){
                try {
                    try {
                        Desktop.getDesktop().browse(new URI("E:/Documentos/NetBeansProjects/domino/src/domino/Video/intro.wmv"));
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(Acerca_de.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Acerca_de.class.getName()).log(Level.SEVERE, null, ex);
                }

        }
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

