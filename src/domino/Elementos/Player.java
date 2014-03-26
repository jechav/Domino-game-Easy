/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domino.Elementos;

/**
 *
 * @author Jochechavez
 */
public class Player {

    //Clase jugador, la instancia de esta clasa serán los jugadores
    public Ficha[] mano;  //Vector con las 7 fichas de juego
    public int cont;
    public String id;         //indentificador de cada jugador
    public int pintas;
    public boolean jugo;
    public boolean pasa;
    public int xN;
    public  int yN;
    //Constructor de la clase que inicializa el identifiacador de cada jugador

    public Player(String x) {
        id = x;
        cont = 0;
        mano = new Ficha[7];  
        pintas = 0;
    }

    //////Metodo que utiliza la maquina para seleccionar su Ficha a lanzar////
    public Ficha jugada(Mesa Mesa) {
        //Hace referencia a un objeto Ficha que servirá como temporal
        Ficha tempo = null;

        ///////Recorremos el Vector Para comprobar cual Ficha puede jugarse
        int sw = 1;

        //Ciclo que recorre las fichas del jugador
        for (int i = 0; i < this.mano.length; i++) {

            //Conprueba que la Mesa no tenga fichas y lo crea
            if (Mesa.getAux() == null) {
                
                Mesa.aux = new Nodo();
                tempo = new Ficha();
                Mesa.aux.info = new Ficha();

                //Ahora recorremos el ciclo buscando la Ficha de mayor suma en sus pintas para despintarse
                int sw2 = 0;
                for (int j = 0; j < this.mano.length; j++) {

                    //Verifica si hay dobles y determina el mayor
                    if (this.mano[j].C1 == this.mano[j].C2) {

                        //Si las suma de las pintas de la Ficha actual es mayor q la anterior la reemplaza
                        //Así encuentra la de mayor pintas
                        if ((this.mano[j].C1 + this.mano[j].C2) > (tempo.C1 + tempo.C2)) {
                            tempo = this.mano[j];
                        } else {
                            //Si no encuentra que es mayor, verifica que el anterior sea par y si no lo es lo reemplaza por el par
                            if (tempo.C1 != tempo.C2) {
                                tempo = this.mano[j];
                            }
                        }
                        sw2 = 1;

                    }
                    if (sw2 != 1) {
                        //Si las suma de las pintas de la Ficha actual es mayor q la anterior la reemplaza
                        //Así encuentra la de mayor pintas
                        if ((this.mano[j].C1 + this.mano[j].C2) > (tempo.C1 + tempo.C2)) {
                            tempo = this.mano[j];
                        }
                    }
                }
                return tempo;


                //SI la Mesa ya tiene fichas
            } else {

                ////Preguntamos si la Ficha se puede jugar por alguno de las
                ////puntas libres de la Mesa, por ambas caras.
                if (this.mano[i].C1 == Mesa.getAux().info.C1 || this.mano[i].C2 == Mesa.getAux().info.C1
                        || this.mano[i].C1 == Mesa.getAux().info.C2 || this.mano[i].C2 == Mesa.getAux().info.C2) {

                    //Si no habia entrado antes crea la Ficha para temp
                    //Y pone sw en 1 para que no vuelva a entrar y crear la Ficha
                    if (this.mano[i].C1 == this.mano[i].C2) {


                        if (tempo != null) {
                            if (sw == 1) {
                                tempo = new Ficha();
                                tempo = this.mano[i];
                            } else {
                                if ((this.mano[i].C1 + this.mano[i].C2) > (tempo.C1 + tempo.C2)) {
                                    tempo = this.mano[i];
                                }
                            }


                        } else {
                            tempo = new Ficha();
                            tempo = this.mano[i];
                        }
                        sw = 0;
                    }

                    if (sw == 1) {

                        tempo = new Ficha();
                        //compara para ver si la suma de las caras de las fichas anteriores
                        //es menor que las actuales para despintarse
                        if ((this.mano[i].C1 + this.mano[i].C2) > (tempo.C1 + tempo.C2)) {
                            tempo = this.mano[i];
                        }

                    }


                }
            }
        }


        //Sino entra a nunguna condición retorna tempo como null dicindo que no tiene fichas para jugar
        return tempo;
    }

    //////Metodo que actualiza el atributo pintas de cada jugador////
    public void setpintas() {
        pintas = 0;

        for (int i = 0; i < mano.length; i++) {
            pintas = pintas + (mano[i].C1 + mano[i].C2);
        }
    }

    public int getWidthAll() {
        int sum = 0;

        for (int i = 0; i < mano.length; i++) {
            sum = sum + mano[i].Ima.getWidth(null);
        }

        return sum + (20 * mano.length - 1);
    }
}
