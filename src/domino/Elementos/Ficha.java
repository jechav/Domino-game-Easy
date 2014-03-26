/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domino.Elementos;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author Jochechavez
 */
public class Ficha {
    //Las dos caras de la Ficha de dominó
    public int C1;
    public int C2;
    public Image Imagen; //Imagen que muestra
    public Image Ima4;
    public Image Ima3;  //Imagen Horizontal de la Ficha Dere
    public Image Ima2;  //Imagen Horizontal de la Ficha Iz
    public Image Ima; ///Imagen Vertical de la Ficha
    public int x, y; // coordenasa de las fichas en el tablero

    public Rectangle getBounds() {
        return new Rectangle(x, y, Ima.getWidth(null), Ima.getHeight(null));
    }

    public boolean isHit(int x, int y) {
        if (getBounds().contains(x, y)) {
            return true;
        } else {
            return false;
        }
    }

    public void addX(int x) {
        this.x += x;
    }

    public void addY(int y) {
        this.y += y;
    }
}
