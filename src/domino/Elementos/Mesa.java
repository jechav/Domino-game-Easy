/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domino.Elementos;

import domino.UnJugador;
import javax.swing.JOptionPane;

/**
 *
 * @author Jochechavez
 */
//Clase nodo que contiene la Ficha y el apuntador
//Esta clase es la lista de nodos que representa las fichas que se van jugando
public class Mesa {

    public Nodo ptr;    //Nodo primero
    public Nodo ultmo;  //Nodo Ultimo
    /*******Nodo Auxiliar, es el que tendrá la Ficha con la cara de la primera y la ultima de la lista, disponibles*****/
    public Nodo aux;
    /*******direccion  0: Izquierdda 1: abajo   2: derecha*********/
    private int direccionCabe = 0;
    /*******direccion 0: derecha 1: arriba   2: izquierda*******/
    private int direccionCola = 0;
    

    public Mesa() {     //Constructor de la clase
        ptr = null;
        ultmo = null;
        aux = null;
    }

    /****Inserta la ficha en la Izquierda de la mesa********/
    public void insertarcabeza(Ficha ficha) {

        //crea un nuevo nodo y la Ficha que se insetará
        Nodo nuevo = new Nodo();
        nuevo.info = ficha;

        //Conprueba que la lista está vacía
        if (ptr == null) {
            nuevo.sig = null;
            ptr = nuevo;
            ultmo = nuevo;

            //Auxiliar toma el valor de la Ficha entrante pues es la primera
            aux = nuevo;

            if (ficha.C1 != ficha.C2) {
                nuevo.info.Imagen = ficha.Ima2;
            } else {
                nuevo.info.Imagen = ficha.Ima;
            }

            //elegimos las coordenadas para la primera Ficha
            nuevo.info.x = (UnJugador.W - nuevo.info.Imagen.getWidth(null)) / 2;
            nuevo.info.y = (UnJugador.H - nuevo.info.Imagen.getHeight(null)) / 2;

        } else {
            //Si la Ficha tiene una cara igual a la de la lista disponible
            //La otra cara corresponderá a la nueva disponible
            if (aux.info.C1 == ficha.C1) {
                aux.info.C1 = ficha.C2;
                //Invertimos para q al imprimirlos nos mantenga el orden y si no es  doble asigana la inversa
                if (ficha.C1 != ficha.C2) {
                    nuevo.info.Imagen = ficha.Ima3;
                } else {
                    nuevo.info.Imagen = ficha.Ima;
                }

            } else {
                aux.info.C1 = ficha.C1;
                //Invertimos para q al imprimirlos nos mantenga el orden y si no es  doble asigana la inversa
                if (ficha.C1 != ficha.C2) {
                    nuevo.info.Imagen = ficha.Ima2;
                } else {
                    nuevo.info.Imagen = ficha.Ima;
                }
            }

            /////////////////////////////////////////////////
            /////////ESCOGEMOS LA COORDENADAS////////////////
            if (direccionCabe == 0) {
                nuevo.info.x = ptr.info.x - nuevo.info.Imagen.getWidth(null);
                nuevo.info.y = (UnJugador.H - nuevo.info.Imagen.getHeight(null)) / 2;
                /***********Si necesita cambiar de direccion hacia abajo*********/
                if (nuevo.info.x < 100) {
                    direccionCabe = 1;
                }
            }
            //////////Coordenadas Hacia la Derecha/////////
            if (direccionCabe == 2) {
                if (aux.info.C1 == ficha.C1) {
                    nuevo.info.Imagen = ficha.Ima3;
                } else {
                    nuevo.info.Imagen = ficha.Ima2;
                }
                nuevo.info.x = ptr.info.x + ptr.info.Imagen.getWidth(null);
                nuevo.info.y = ptr.info.y;

            }
            ////////Coordenadas hacia Abajo////////
            if (direccionCabe == 1) {
                if (aux.info.C1 == ficha.C1) {
                    nuevo.info.Imagen = ficha.Ima4;
                } else {
                    nuevo.info.Imagen = ficha.Ima;
                }
                nuevo.info.x = ptr.info.x;
                nuevo.info.y = (ptr.info.Imagen.getHeight(null) + ptr.info.y);

                /***********Si necesita cambiar de direccion hacia La Derecha*********/
                if (nuevo.info.y + nuevo.info.Imagen.getHeight(null) > 590) {
                    direccionCabe = 2;
                    if (aux.info.C1 == ficha.C1) {
                        nuevo.info.Imagen = ficha.Ima3;
                    } else {
                        nuevo.info.Imagen = ficha.Ima2;
                    }
                    nuevo.info.x = ptr.info.x + ptr.info.Imagen.getWidth(null);
                    nuevo.info.y = ptr.info.y + (ptr.info.Imagen.getHeight(null) / 2);

                }
            }

            /////////////////////////////
            //Reasignamos apuntadores
            nuevo.sig = ptr;
            ptr = nuevo;
        }
    }

    /****Inserta la ficha en la Derecha de la mesa********/
    public void insertcola(Ficha ficha) {
        Nodo nuevo;
        nuevo = new Nodo();
        nuevo.info = ficha;

        //si la cara 1 de la Ficha nueva es igual a la disponible entonces la nueva
        //Disponible será la cara 2
        if (aux.info.C2 == ficha.C1) {
            //Invertimos para q al imprimirlos nos mantenga el orden y si no es  doble asigana la inversa
            if (ficha.C1 != ficha.C2) {
                nuevo.info.Imagen = nuevo.info.Ima2;
            } else {
                nuevo.info.Imagen = ficha.Ima;
            }
            aux.info.C2 = ficha.C2;
        } else {
            //Invertimos para q al imprimirlos nos mantenga el orden y si no es  doble asigana la inversa
            if (ficha.C1 != ficha.C2) {
                nuevo.info.Imagen = nuevo.info.Ima3;
            } else {
                nuevo.info.Imagen = ficha.Ima;
            }
            aux.info.C2 = ficha.C1;
        }
        /////////////////////////////////////////////////
        /////////ESCOGEMOS LA COORDENADAS////////////////
        if (direccionCola == 2) {
            //////////Coordenadas Hacia la Izquierda/////////
            if (aux.info.C2 == ficha.C1) {
                nuevo.info.Imagen = ficha.Ima2;
            } else {
                nuevo.info.Imagen = ficha.Ima3;
            }
            nuevo.info.x = ultmo.info.x - nuevo.info.Imagen.getWidth(null);
            nuevo.info.y = ultmo.info.y;
        }
        if (direccionCola == 1) {
            ////////Coordenadas hacia Ariiba////////
            if (aux.info.C2 == ficha.C1) {
                nuevo.info.Imagen = ficha.Ima;
            } else {
                nuevo.info.Imagen = ficha.Ima4;
            }
            nuevo.info.x = ultmo.info.x;
            nuevo.info.y = ultmo.info.y - nuevo.info.Imagen.getHeight(null);
            
            /***********Si necesita cambiar de direccion hacia La Izquierda*********/
            if (nuevo.info.y < 110) {
                direccionCola = 2;
                if (aux.info.C2 == ficha.C1) {
                    nuevo.info.Imagen = ficha.Ima2;
                } else {
                    nuevo.info.Imagen = ficha.Ima3;
                }
                nuevo.info.x = ultmo.info.x - nuevo.info.Imagen.getWidth(null);
                nuevo.info.y = ultmo.info.y;

            }
        }
        if (direccionCola == 0) {
            nuevo.info.x = ultmo.info.x + ultmo.info.Imagen.getWidth(null);
            nuevo.info.y = (UnJugador.H - nuevo.info.Imagen.getHeight(null)) / 2;
            /***********Si necesita cambiar de direccion hacia Arriba*********/
            if (nuevo.info.x + nuevo.info.Imagen.getWidth(null) > 1100) {
                
                direccionCola = 1;
                if (aux.info.C2 == ficha.C1) {
                    nuevo.info.Imagen = ficha.Ima;
                } else {
                    nuevo.info.Imagen = ficha.Ima4;
                }
                if (ultmo.info.C1 == ultmo.info.C2) {
                    nuevo.info.x = ultmo.info.x;
                    nuevo.info.y = ultmo.info.y - nuevo.info.Imagen.getHeight(null);
                } else {
                    nuevo.info.x = ultmo.info.x + (ultmo.info.Imagen.getWidth(null) / 2);
                    nuevo.info.y = ultmo.info.y - nuevo.info.Imagen.getHeight(null);
                }
            }
        }



        ///////////////////////////////
        //Reasignamos Apuntadoress
        ultmo.sig = nuevo;
        nuevo.sig = null;
        ultmo = nuevo;

    }

    /***Returna EL nodo auxiliar, el que tiene las pocisiones 
     * de la cabeza y la cola.*/
    public Nodo getAux() {

        return aux;
    }

    /***Returna El Nodo cabeza de la mesa.*/
    public Nodo getcabeza() {
        return ptr;
    }
}
