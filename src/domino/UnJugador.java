/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domino;

import domino.Elementos.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class UnJugador extends JPanel implements ActionListener {
    //Indica La visibilidad del Jpanel actual, necesario en el Jframe

    boolean Visible;
    //Crea el objeto que controlará el juego//
    Domino domino;
    //Taimer; se encarga de llamar al método Acction Performed, y
    //este a su vez llama a paint que dibuja en pantalla
    private Timer timer;
//    private boolean TurnoSw = false;
    private boolean PauseSw = false;
    private boolean mostrarAyuda = false;
    private boolean BordeSw = false;
    private boolean SelecSw = false;
    private boolean SwDecision = false;
    //Dimenciones de la Pantalla
    public static int W;
    public static int H;
    //Imagenes del juego
    private Image rever = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/tras.png")).getImage();
    private Image reverIz = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/tras2.png")).getImage();
    private Image reverDere = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/tras3.png")).getImage();
    private Image fondo = new ImageIcon(this.getClass().getResource("Imagenes/Fondo.png")).getImage();
    private Image pausa = new ImageIcon(this.getClass().getResource("Imagenes/pause.png")).getImage();
    private Image Paso = new ImageIcon(this.getClass().getResource("Imagenes/Paso.png")).getImage();
    private Image ayuda = new ImageIcon(this.getClass().getResource("Imagenes/Ayuda.png")).getImage();
    private Image ayudaMs = new ImageIcon(this.getClass().getResource("Imagenes/ayudaMs.png")).getImage();
    private Image atras = new ImageIcon(this.getClass().getResource("Imagenes/back.png")).getImage();
    private Image Selec = new ImageIcon(this.getClass().getResource("Imagenes/Seleccion.png")).getImage();
    private Image Borde = new ImageIcon(this.getClass().getResource("Imagenes/Borde.png")).getImage();
    Font Fnames = new Font("Arial", Font.BOLD, 16);
    private int xBorde;
    private int yBorde;
    //Coordenadas de las imagenes para controlar si se les dio click
    private int xAyuda;
    private int yAyuda;
    private int xatras;
    private int yatras;
    //Cara de la ficha que cierra el juego
    private int Sierra;
    //Funete en el juego
    //Hilo que controla el reloj
    private tiempo Hora;
    sonido son = new sonido();

    /*********Construcctor de la clase Jpane Un Jugador*********/
    public UnJugador(String name) {

        MovingAdapter ma = new MovingAdapter();

        this.addMouseMotionListener(ma);
        this.addMouseListener(ma);

        this.addKeyListener(new TAdapter());

        this.setFocusable(true);
        this.setDoubleBuffered(true);

        //Creamos un objeto Domino que va a manejar el juego
        domino = new Domino(name);
        //Ponemos el atributo ingame en verdadero indicando que el juego ha empezado
        domino.ingame = true;

        //Creamo un objeto de la calse tiempo que controlará la muestra del tiempo en pantalla
        Hora = new tiempo();
        Hora.start();

        //Creamos un objeto tiempo que controla el turno y la velocidad de juego
        timer = new Timer(2100, this);
        timer.start();
        Visible = true;
        son.Revolver();

    }

    /****************DIBUJA EN LA PANTALLA, LLAMADA POR actionPerformed ****/
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (domino.ingame) {

            if (domino.MesaIsNull()) {
                SetCoordenadas();
            }

            g2d.drawImage(fondo, 0, 0, this);
            g2d.setColor(Color.BLACK);
            g2d.setFont(Fnames);
            g2d.drawImage(ayuda, xAyuda, yAyuda, this);

            DibujaManos(g2d);

            DibujaEstadoJug(g2d);

            ///Varible de pausa
            if (PauseSw) {
                g2d.drawImage(pausa, 20, 20, this);
            }
            if (mostrarAyuda) {
                g2d.drawImage(ayudaMs, xAyuda, yAyuda + ayuda.getWidth(this), this);
            }
            if (BordeSw) {
                g2d.drawImage(Borde, xBorde, yBorde, this);
            }

            if (SelecSw) {
                g2d.drawImage(Selec, W / 2, H / 2, this);
            }
            
            g2d.drawImage(atras, xatras, yatras, this);
            
            g2d.drawString("Tiempo " + Hora.hora, W - 170, 37);


            /*******************************************************/
            /*******************************************************/
            /************************SI EL JUEGO TERMINA**************/
        } else {

            Estadisticas(g2d);
            timer.stop();
        }
        repaint();
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    /*EJCUCION DE JUEGO
     *entra cada vez que lo llama el timer para primero ejecutar el turrno
     *y luejo tirar la ficah de jugador */
    public void actionPerformed(ActionEvent e) {

        //Si el atributo Temp del domino(Un jugador temporal para el tueno) esta en nulo
        //Se da el turno

        if (Domino.temp == null) {
            Domino.temp = domino.turno();
        } else {
            //Si el jugador, a quien se le dió el turno anteriormente jugó se inserta la ficha enla mesa. si este es diferente
            //del jugador Invitado
            if (Domino.temp.jugo) {
                if (Domino.temp != Domino.player) {
                    domino.juega();
                    son.juego();
                }
                Domino.temp.jugo = false;
                Domino.temp.pasa = false;
                Domino.temp = domino.turno();
            } else {
                if (Domino.temp.pasa) {
                    Domino.temp.jugo = false;
                    Domino.temp.pasa = false;
                    Domino.temp = domino.turno();
                } else {
                    //JOptionPane.showMessageDialog(this, "No debería entrar aqui, Juggador no pasó ni jugó");
                }
            }
        }

        domino.compruebaGanó();

        //Si la mesa no esta vacía, todavia esta en juego, o sea que no ha gando nadie
        if (!domino.MesaIsNull() && domino.ingame) {
            Sierra = domino.compruebaCerrado();
        }

        if (Domino.temp != Domino.player) {

            Domino.Ftemp = Domino.temp.jugada(domino.getMesa());

            if (Domino.Ftemp == null) {
                //Si la Ficha que debuelve, no existe significa que no tiene, con q jugar
                Domino.temp.pasa = true;
                son.paso();

            } else {
                Domino.temp.jugo = true;
            }
        } else {
            timer.stop();
        }
        repaint();
    }

    /*********Asigana las coordenadas de las fichas de cada jugador*******/
    private void SetCoordenadas() {


        W = getSize().width - getInsets().left - getInsets().right;
        H = getSize().height - getInsets().top - getInsets().bottom;

        xAyuda = W - 200;
        yAyuda = 22;
        xatras = W - 45;
        yatras = H - 45;

        //Separacion de las fichas con el marco de la ventana de juego
        int separacion = 5;

        /*****************  Invitado  ****************/
        try {
            Domino.player.mano[0].x = (W - Domino.player.getWidthAll()) / 2;
            Domino.player.mano[0].y = H - Domino.player.mano[0].Ima.getHeight(this) - separacion;
        } catch (Exception e) {
            //System.out.println("Valor de palyer"+Domino.player);
        }


        for (int i = 1; i < Domino.player.mano.length; i++) {
            Domino.player.mano[i].x = Domino.player.mano[i - 1].x + Domino.player.mano[i].Ima.getWidth(this) + 20;
            Domino.player.mano[i].y = H - Domino.player.mano[i].Ima.getHeight(this) - separacion;
        }
        Domino.player.xN = Domino.player.mano[Domino.player.mano.length - 1].x + (Domino.player.mano[Domino.player.mano.length - 1].Ima.getHeight(this) / 2) + 20;
        Domino.player.yN = Domino.player.mano[Domino.player.mano.length - 1].y + (Domino.player.mano[Domino.player.mano.length - 1].Ima.getHeight(this) / 2);

        /*****************juagdor 1  (ANDRES)****************/
        Domino.jug1.mano[0].x = separacion;
        Domino.jug1.mano[0].y = (H - Domino.jug1.getWidthAll()) / 2;

        for (int i = 1; i < Domino.jug1.mano.length; i++) {
            Domino.jug1.mano[i].x = separacion;
            Domino.jug1.mano[i].y = Domino.jug1.mano[i - 1].y + Domino.jug1.mano[i].Ima.getWidth(this) + 20;

        }
        Domino.jug1.xN = separacion;
        Domino.jug1.yN = Domino.jug1.mano[Domino.jug1.mano.length - 1].y + Domino.jug1.mano[Domino.jug1.mano.length - 1].Ima.getWidth(this) + 20;

        /*****************juagdor 2 (CRISTIAN)****************/
        Domino.jug2.mano[0].x = (W - Domino.jug2.getWidthAll()) / 2;
        Domino.jug2.mano[0].y = separacion;

        for (int i = 1; i < Domino.jug2.mano.length; i++) {
            Domino.jug2.mano[i].x = Domino.jug2.mano[i - 1].x + Domino.jug2.mano[i].Ima.getWidth(this) + 20;
            Domino.jug2.mano[i].y = separacion;
        }
        Domino.jug2.xN = Domino.jug2.mano[Domino.jug2.mano.length - 1].x + Domino.jug2.mano[0].Ima.getWidth(this) + 20;
        Domino.jug2.yN = separacion + (Domino.jug2.mano[0].Ima.getHeight(this)) / 2;

        /*****************juagdor 3  (JOCHE)****************/
        Domino.jug3.mano[0].x = W - Domino.jug3.mano[0].Ima.getHeight(this) - separacion;
        Domino.jug3.mano[0].y = (H - Domino.jug3.getWidthAll()) / 2;

        for (int i = 1; i < Domino.jug3.mano.length; i++) {
            Domino.jug3.mano[i].x = W - Domino.jug3.mano[0].Ima.getHeight(this) - separacion;
            Domino.jug3.mano[i].y = Domino.jug3.mano[i - 1].y + Domino.jug3.mano[i].Ima.getWidth(this) + 20;
        }
        Domino.jug3.xN = Domino.jug3.mano[Domino.jug3.mano.length - 1].x;
        Domino.jug3.yN = Domino.jug3.mano[Domino.jug3.mano.length - 1].y + Domino.jug3.mano[0].Ima.getWidth(this) + 20;

    }

    /*********Dibuja los resultados al final del juego*******/
    private void Estadisticas(Graphics2D g2d) {

        Image gme_over;
        gme_over = new ImageIcon(this.getClass().getResource("Imagenes/Game over.png")).getImage();
        Image esta;
        esta = new ImageIcon(this.getClass().getResource("Imagenes/estadistca.png")).getImage();
        if(Domino.player==null){
            g2d.drawImage(esta, 0, 0, null);            
        }else{
            g2d.drawImage(gme_over, 0, 0, null);            
        }
        //g2d.drawImage(fondo, 0, 0, this);
        /*************IMPRIMIMOS LA MESA*********
        Nodo emp = domino.getMesa().getcabeza();
        
        while (emp != null) {
        g2d.drawImage(emp.info.Ima, emp.info.x, emp.info.y + 200, this);
        
        emp = emp.sig;
        }
         **/
        g2d.setFont(new Font("Arial", Font.BOLD, 31));
        g2d.setColor(Color.RED);
        g2d.drawString("Estadísticas", 500, 40);
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        int t = 150;
        boolean sw = false;
        /*****************juagdor 1  (ANDRES)****************/
        if (Domino.jug1.mano != null) {

            g2d.drawString(Domino.jug1.id + " =  ", 40, 60);
            for (int i = 0; i < Domino.jug1.mano.length; i++) {
                g2d.drawImage(Domino.jug1.mano[i].Ima, t, 16, this);
                t += Domino.jug1.mano[i].Ima.getWidth(this);
            }
            Domino.jug1.setpintas();
            g2d.setColor(Color.WHITE);
            g2d.drawString("Pintas = " + Domino.jug1.pintas, t + Domino.jug1.mano[0].Ima.getWidth(this), 60);
            g2d.setColor(Color.BLUE);

        } else {
            sw = true;
            g2d.setColor(Color.YELLOW);
            g2d.drawString(Domino.jug1.id + " ES EL GANADOR  ", 40, 60);
            g2d.setColor(Color.BLUE);
        }
        /*****************juagdor 2 (CRISTIAN)****************/
        t = 150;
        if (Domino.jug2.mano != null) {

            g2d.drawString(Domino.jug2.id + " =  ", 40, 160);
            for (int i = 0; i < Domino.jug2.mano.length; i++) {
                g2d.drawImage(Domino.jug2.mano[i].Ima, t, 144, this);
                t += Domino.jug2.mano[i].Ima.getWidth(this);
            }
            Domino.jug2.setpintas();
            g2d.setColor(Color.WHITE);
            g2d.drawString("Pintas = " + Domino.jug2.pintas, t + Domino.jug2.mano[0].Ima.getWidth(this), 160);
            g2d.setColor(Color.BLUE);
        } else {
            sw = true;
            g2d.setColor(Color.YELLOW);
            g2d.drawString(Domino.jug2.id + " ES EL GANADOR  ", 40, 160);
            g2d.setColor(Color.BLUE);
        }
        /*****************juagdor 3  (JOCHE)****************/
        t = 150;
        if (Domino.jug3.mano != null) {

            g2d.drawString(Domino.jug3.id + " =  ", 40, 260);
            for (int i = 0; i < Domino.jug3.mano.length; i++) {
                g2d.drawImage(Domino.jug3.mano[i].Ima, t, 244, this);
                t += Domino.jug3.mano[i].Ima.getWidth(this);
            }
            Domino.jug3.setpintas();
            g2d.setColor(Color.WHITE);
            g2d.drawString("Pintas = " + Domino.jug3.pintas, t + Domino.jug3.mano[0].Ima.getWidth(this), 260);
            g2d.setColor(Color.BLUE);
        } else {
            sw = true;
            g2d.setColor(Color.YELLOW);
            g2d.drawString(Domino.jug3.id + " ES EL GANADOR  ", 40, 260);
            g2d.setColor(Color.BLUE);
        }
        /*****************Inviatdo****************/
        t = 150;
        if (Domino.player.mano != null) {

            g2d.drawString(Domino.player.id + " =  ", 40, 360);
            for (int i = 0; i < Domino.player.mano.length; i++) {
                g2d.drawImage(Domino.player.mano[i].Ima, t, 344, this);
                t += Domino.player.mano[i].Ima.getWidth(this);
            }
            Domino.player.setpintas();
            g2d.setColor(Color.WHITE);
            g2d.drawString("Pintas = " + Domino.player.pintas, t + Domino.player.mano[0].Ima.getWidth(this), 360);
            g2d.setColor(Color.BLUE);

        } else {
            sw = true;
            g2d.setColor(Color.YELLOW);
            g2d.drawString(Domino.player.id + " ES EL GANADOR  ", 40, 360);
            g2d.setColor(Color.BLUE);
        }

        if (!sw) {
            g2d.setColor(Color.YELLOW);
            g2d.drawString(" El juego se Ha cerrado a  " + Sierra, 40, 460);
            g2d.setColor(Color.BLUE);
        }
        g2d.drawImage(atras, xatras, yatras, this);

    }

    /*************DIBUJAMOS LAS FICHAS EN LAS MANOS DE TODOS LOS JUGADORES*******/
    private void DibujaManos(Graphics2D g2d) {

        /*************DIBUJAMOS LA MESA***********/
        Nodo emp = domino.getMesa().getcabeza();

        while (emp != null) {

            g2d.drawImage(emp.info.Imagen, emp.info.x, emp.info.y, this);

            emp = emp.sig;
        }
        /*************DIBUJAMOS LA MESA***********/
        /*****************juagdor 1  (ANDRES)****************/
        for (int i = 0; i < Domino.jug1.mano.length; i++) {
            g2d.drawImage(reverIz, Domino.jug1.mano[i].x, Domino.jug1.mano[i].y, this);
            //g2d.drawImage(Domino.jug1.mano[i].Ima2, Domino.jug1.mano[i].x, Domino.jug1.mano[i].y, this);
        }
        /*****************juagdor 2 (CRISTIAN)****************/
        for (int i = 0; i < Domino.jug2.mano.length; i++) {
            g2d.drawImage(rever, Domino.jug2.mano[i].x, Domino.jug2.mano[i].y, this);
            //g2d.drawImage(Domino.jug2.mano[i].Ima, Domino.jug2.mano[i].x, Domino.jug2.mano[i].y, this);
        }
        /*****************juagdor 3  (JOCHE)****************/
        for (int i = 0; i < Domino.jug3.mano.length; i++) {
            g2d.drawImage(reverDere, Domino.jug3.mano[i].x, Domino.jug3.mano[i].y, this);
            //g2d.drawImage(Domino.jug3.mano[i].Ima3, Domino.jug3.mano[i].x, Domino.jug3.mano[i].y, this);
        }
        /*****************Inviatdo****************/
        for (int i = 0; i < Domino.player.mano.length; i++) {
            g2d.drawImage(Domino.player.mano[i].Ima, Domino.player.mano[i].x, Domino.player.mano[i].y, this);
        }
    }

    /*************DIBUJAMOS EL ESTADO DE TODOS LOS JUGADORES*******/
    private void DibujaEstadoJug(Graphics2D g2d) {

        //Si es el turno del invitado //Imprime su nombre en rojo y si no, normalmente (blanco)
        if (Domino.temp == Domino.player) {
            if (Domino.player.pasa) {
                g2d.drawImage(Paso, Domino.player.xN, Domino.player.yN, this);
            } else {
                g2d.setColor(Color.RED);
                g2d.drawString(Domino.player.id, Domino.player.xN, Domino.player.yN);
                g2d.setColor(Color.BLACK);
            }
        } else {
            g2d.drawString(Domino.player.id, Domino.player.xN, Domino.player.yN); //Nombre
        }

        //Si es el turno Jugador 1 //Imprime su nombre en rojo y si no, normalmente (blanco)
        if (Domino.temp == Domino.jug1) {

            if (Domino.jug1.pasa) {

                g2d.drawImage(Paso, Domino.jug1.xN, Domino.jug1.yN, this);

            } else {
                g2d.drawImage(Domino.Ftemp.Ima2, Domino.Ftemp.x, Domino.Ftemp.y, this);
                g2d.setColor(Color.RED);
                g2d.drawString(Domino.jug1.id, Domino.jug1.xN, Domino.jug1.yN); //Nombre
                g2d.setColor(Color.BLACK);
            }
        } else {
            g2d.drawString(Domino.jug1.id, Domino.jug1.xN, Domino.jug1.yN); //Nombre
        }

        //Si es el turno Jugador 2 //Imprime su nombre en rojo y si no, normalmente (blanco)
        if (Domino.temp == Domino.jug2) {
            if (Domino.jug2.pasa) {

                g2d.drawImage(Paso, Domino.jug2.xN, Domino.jug2.yN, this);

            } else {
                g2d.drawImage(Domino.Ftemp.Ima, Domino.Ftemp.x, Domino.Ftemp.y, this);
                g2d.setColor(Color.RED);
                g2d.drawString(Domino.jug2.id, Domino.jug2.xN, Domino.jug2.yN); //Nombre
                g2d.setColor(Color.BLACK);
            }
        } else {
            g2d.drawString(Domino.jug2.id, Domino.jug2.xN, Domino.jug2.yN); //Nombre
        }

        //Si es el turno Jugador 3 //Imprime su nombre en rojo y si no, normalmente (blanco)
        if (Domino.temp == Domino.jug3) {
            if (Domino.jug3.pasa) {

                g2d.drawImage(Paso, Domino.jug3.xN, Domino.jug3.yN, this);

            } else {
                g2d.drawImage(Domino.Ftemp.Ima3, Domino.Ftemp.x, Domino.Ftemp.y, this);
                g2d.setColor(Color.RED);
                g2d.drawString(Domino.jug3.id, Domino.jug3.xN, Domino.jug3.yN); //Nombre
                g2d.setColor(Color.BLACK);
            }
        } else {
            g2d.drawString(Domino.jug3.id, Domino.jug3.xN, Domino.jug3.yN); //Nombre
        }
    }

    /************Pausa el juego********/
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int keycode = e.getKeyCode();

            if (keycode == 'p' || keycode == 'P') {
                //Si no está en pausa
                if (!PauseSw) {
                    PauseSw = true;
                    timer.stop();
                    Hora.pausa();
                    repaint();

                } else {
                    timer.start();
                    Hora.continuar();
                    PauseSw = false;
                    repaint();
                }
            } else {
                if (keycode == 'e' || keycode == 'E') {
                    System.exit(0);
                } else {
                    if (keycode == 'r' || keycode == 'R') {
                        if (!domino.ingame) {
                            domino = new Domino(Domino.player.id);
                            repaint();
                            Hora.restart();
                            timer.start();
                        }
                    }
                }
            }
            if (SwDecision) {
                //System.out.println("Entra a desición");

                if (keycode == KeyEvent.VK_LEFT) {
                    //System.out.println("Si entra a la tecla");
                    domino.Insertar(1);
                    son.juego();
                    SelecSw = false;
                    Domino.player.jugo = true;
                    BordeSw = false;
                    SelecSw = false;
                    SwDecision = false;
                    //domino.compruebaGanó();                                
                    repaint();
                    timer.restart();
                } else {
                    if (keycode == KeyEvent.VK_RIGHT) {
                        //System.out.println("Si entra a la otra tecla");
                        domino.Insertar(2);
                        son.juego();
                        SelecSw = false;
                        Domino.player.jugo = true;
                        BordeSw = false;
                        SelecSw = false;
                        SwDecision = false;
                        //domino.compruebaGanó();
                        repaint();
                        timer.restart();
                    }
                }

            }

        }
    }

    /*Controla la accion del Mause******************/
    class MovingAdapter extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if (isHit(ayuda, e.getX(), e.getY(), xAyuda, yAyuda)) {
                if (!mostrarAyuda) {
                    mostrarAyuda = true;
                    repaint();
                }


            } else {
                if (mostrarAyuda) {
                    mostrarAyuda = false;
                    repaint();
                }
                if (Domino.temp == Domino.player && domino.ingame) {
                    for (int i = 0; i < Domino.player.mano.length; i++) {
                        if (Domino.player.mano[i].isHit(x, y)) {
                            BordeSw = true;
                            xBorde = Domino.player.mano[i].x;
                            yBorde = Domino.player.mano[i].y;
                            repaint();
                            break;
                        } else {
                            BordeSw = false;
                            repaint();
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if (isHit(atras, x, y, xatras, yatras)) {                
                Visible = false;
            } else {
                boolean sw = false;
                if (Domino.temp == Domino.player && !Domino.player.jugo && !Domino.player.pasa && !PauseSw && domino.ingame) {
                    for (int i = 0; i < Domino.player.mano.length; i++) {
                        if (Domino.player.mano[i].isHit(x, y)) {
                            Domino.Ftemp = Domino.player.mano[i];
                            if ((domino.DondeJugar()==0) && (domino.getMesa().getAux().info.C1 != domino.getMesa().getAux().info.C2)) {
                                //JOptionPane.showMessageDialog(null, "Mk pa donde");
                                //sw = true;
                                SelecSw = true;
                                SwDecision = true;
                            } else {
                                if (domino.juega() == 0) {
                                    son.juego();
                                    SelecSw = false;
                                    Domino.player.jugo = true;
                                    BordeSw = false;
                                    domino.compruebaGanó();
                                    repaint();
                                    timer.restart();
                                    sw = true;
                                    break;
                                }
                            }
                            sw = true;
                            timer.restart();
                            System.out.println("aqui es");
                        }

                    }
                    //Si no dio el click encima de ninguna Ficha pasón juega la maquina
                    if (!sw) {
                        Domino.Ftemp = Domino.player.jugada(domino.getMesa());
                        //if (Domino.Ftemp == null) {
                            Domino.player.pasa = true;
                            son.paso();
                            BordeSw = false;
                            timer.restart();
                            repaint();
                        /*} else {
                            domino.juega();
                            son.juego();
                            Domino.player.jugo = true;
                            BordeSw = false;
                            repaint();
                        }*/
                    }

                }

            }                        

        }

        @Override
        public void mouseDragged(MouseEvent e) {

            /*
            int dx = e.getX() - x;
            int dy = e.getY() - y;
            
            for(int i=0; i<Domino.player.mano.length;i++){
            if(Domino.player.mano[i].isHit(x, y)){
            Domino.player.mano[i].addX(dx);
            Domino.player.mano[i].addY(dy);
            //repaint();
            }
            }
            
            x += dx;
            y += dy;
             *
             */
        }
    }

    /************HILO QUE CONTROLA EL TIEMPO Y EL DIBUJADO CADA SEGUNDO********/
    class tiempo extends Thread {

        public String hora;
        public boolean corre;
        private int min = 0, sec = 0;

        public tiempo() {
            super();
            corre = true;
        }

        @Override
        public void run() {

            while (true) {
                if (corre) {
                    sec++;
                    if (sec == 60) {
                        min++;
                        sec = 0;
                    }
                    hora = "" + min + ":" + sec;

                    if (min <= 9 && sec <= 9) {
                        hora = "0" + min + ":0" + sec;
                    } else {
                        if (min <= 9) {
                            hora = "0" + min + ":" + sec;
                        } else {
                            if (sec <= 9) {
                                hora = "" + min + ":0" + sec;
                            }
                        }

                    }
                    delay();
                }
            }
        }

        private void delay() {
            try {
                // pausa para el splash
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }

        public void pausa() {
            corre = false;
        }

        public void continuar() {
            corre = true;
        }

        public void restart() {
            min = 0;
            sec = 0;
        }
    }

    
    private boolean isHit(Image im, int x, int y, int xim, int yim) {

        Rectangle rec = new Rectangle(xim, yim, im.getWidth(this), im.getHeight(this));

        if (rec.contains(x, y)) {
            return true;
        } else {
            return false;
        }
    }
}
