/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domino;

import domino.Menu.Acerca_de;
import domino.Menu.Menu;
import domino.Menu.Opciones;
import domino.splash.Splash;
import domino.splash.inicio;
import java.awt.Image;
import javax.swing.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 *
 * @author Jochechavez
 */
public class Main extends JFrame {

    static String nombre = "Invitado";

    //Constructor del Objeto Main
    public Main() {

        Image Domino = new ImageIcon(this.getClass().getResource("Imagenes/Icono/imagenn.png")).getImage();
        GraphicsDevice grafica = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();


        this.setSize(1200, 700);
        this.setResizable(false);
        this.setTitle("Dominó");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //this.setUndecorated(true);//quita los bordes de la ventana para mostrar la ventana en pantalla completa

        ///Imagen del Icono
        this.setIconImage(Domino);

        /****Mostramos la ventana de inicio****/
        inicio Ini = new inicio(this, true);
        Ini.setIconImage(Domino);
        Ini.setVisible(true);

        if (Ini.mens != null && !Ini.mens.equals("")) {
            nombre = Ini.mens;
        }
        /****Mostramos el splash****/
        Splash spl = new Splash();
        spl.setIconImage(Domino);
        spl.setAlwaysOnTop(true);
        spl.setVisible(true);

        //Ciclo que espera a que el splash  carge completamente para
        //cerrarlo y continuar con la ventana de juego
        while (!Splash.finished) {
        }
        spl.setVisible(false);

        spl = null;
        Ini = null;
        /****cuando el splash termina abandona el ciclo y lo quita****/
        //grafica.setFullScreenWindow(this);//pone en pantalla completa
    }

    public static void main(String[] args) {
        boolean Salir = true;
        Main cc = new Main();
        sonido so = new sonido();
        so.fondo();
        while (Salir) {

            Menu menu = new Menu(nombre);
            cc.setContentPane(menu);
            cc.setVisible(true);
            while (menu.SwVisible) {
            }
            cc.setVisible(false);
            switch (menu.op) {
                case 1:
                    so.fondo.stop();
                    UnJugador jug = new UnJugador(nombre);
                    cc.setContentPane(jug);
                    cc.setVisible(true);
                    so.fondo.stop();
                    while (jug.Visible) {
                    }
                    so.fondo.start();
                    jug = null;
                    break;
                case 2:
                    Multijugador mult = new Multijugador();
                    cc.setContentPane(mult);
                    cc.setVisible(true);
                    while (mult.SwVisible) {
                    }
                    mult = null;
                    break;
                case 3:
                    Opciones op = new Opciones();
                    cc.setContentPane(op);
                    cc.setVisible(true);
                    while (op.SwVisible) {
                    }
                    op = null;
                    break;
                case 4:
                    Acerca_de ac = new Acerca_de();
                    cc.setContentPane(ac);
                    cc.setVisible(true);
                    while (ac.SwVisible) {
                    }
                    ac = null;
                    break;
                case 5:
                    System.exit(0);
                    break;
            }
            cc.setVisible(false);
        }
    }
}
