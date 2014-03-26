/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domino;

import domino.Elementos.*;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Jochechavez
 */
public class Domino {
    //Crea los cutro jugadores

    static Player jug1;
    static Player jug2;
    static Player jug3;
    static Player player;
    //Un Jugador y una ficha temporal Para el Turno.
    static public Player temp;
    static public Ficha Ftemp = new Ficha();
    boolean TurnoSw;
    public boolean ingame;
    String name;
    ///////////Las Fichas del juego///////////////
    private Ficha Fichas[] = new Ficha[28];
    //Crea un objeto Mesa que es donde se insertan las fichas
    private Mesa Mesa;

    public Domino(String name) {
        this.name = name;
        temp = null;
        jug1 = null;
        jug2 = null;
        jug3 = null;
        player = null;
        Ftemp = null;
        TurnoSw = false;
        ingame = false;
        Mesa = null;
        generar();
        repartir();

    }

    /************Encuantra por donde puede jugar, 
    la Inserta en la Mesa, y la elimina de la mano del
    jugador*********/
    public int juega() {

        //si la lista no tiene elementos, osea si la lista está vacía
        //Inserta el primer elemento
        if (MesaIsNull()) {
            //SetCoordenadas();
            Mesa.insertarcabeza(Ftemp);
        } else {
            ///Evalúa las dos caras de la fichas para ver si se puede insertar en la cabeza de la lista
            if (Mesa.getAux().info.C1 == Ftemp.C1 || Mesa.getAux().info.C1 == Ftemp.C2) {
                Mesa.insertarcabeza(Ftemp);
            } else {
                ///Evalúa las dos caras de la fichas para ver si se puede insertar en la Cola de la lista
                if (Mesa.getAux().info.C2 == Ftemp.C1 || Mesa.getAux().info.C2 == Ftemp.C2) {
                    Mesa.insertcola(Ftemp);
                } else {
                    ///Sino se puede insertar en la cola ni en la cabeza, la Ficha no sirve
                    //JOptionPane.showMessageDialog(null,"Ficha no sirve:  "+Ftemp.C1+","+Ftemp.C2);
                    return 1;
                }
            }
        }

        //borramos la Ficha de la mano del jugador
        ///Si el jugador tiene una sola Ficha a la mano se le asigna null, para indicar que ganó
        if (temp.mano.length != 1) {
            Ficha[] aux = new Ficha[temp.mano.length - 1];
            for (int i = 0; i < temp.mano.length; i++) {
                //si el elemento esta en la ultima pocision se devuelve el vector con los elementos copiados hasto ahora
                //Sin el elemento
                if (i == temp.mano.length - 1) {
                    temp.mano = aux;
                    return 0;
                }
                aux[i] = temp.mano[i];  //Va copiando los elementos de un vector a otro
                if (Ftemp == temp.mano[i]) {
                    for (int j = i; j < temp.mano.length - 1; j++) {
                        aux[j] = temp.mano[j + 1];//Si encuentra el elemento lo va reemplazando por el siguiente
                    }
                    temp.mano = aux;
                    return 0;
                }
            }
        } else {
            temp.mano = null;
        }
        return 0;
        //LA Ficha se insetó correctamente
    }

    /***********Comprueba el estado de de cada jugador y lo cierra si todos pasan
    Si Teorna diferente de -1 el juego se ha serrado a al valor que retornó*********/
    public int compruebaCerrado() {

        //Dos varibles para las fichas que estan a la cabeza de la Mesa
        int C1 = Mesa.getAux().info.C1;
        int C2 = Mesa.getAux().info.C2;
        boolean sw = false;

        for (int i = 0; i < jug1.mano.length; i++) {
            if (C1 == jug1.mano[i].C1 || C1 == jug1.mano[i].C2 || C2 == jug1.mano[i].C1 || C2 == jug1.mano[i].C2) {
                sw = true;
            }
        }
        for (int i = 0; i < jug2.mano.length; i++) {
            if (C1 == jug2.mano[i].C1 || C1 == jug2.mano[i].C2 || C2 == jug2.mano[i].C1 || C2 == jug2.mano[i].C2) {
                sw = true;
            }
        }
        for (int i = 0; i < jug3.mano.length; i++) {
            if (C1 == jug3.mano[i].C1 || C1 == jug3.mano[i].C2 || C2 == jug3.mano[i].C1 || C2 == jug3.mano[i].C2) {
                sw = true;
            }
        }
        for (int i = 0; i < player.mano.length; i++) {
            if (C1 == player.mano[i].C1 || C1 == player.mano[i].C2 || C2 == player.mano[i].C1 || C2 == player.mano[i].C2) {
                sw = true;
            }
        }
        if (!sw) {
            ingame = false;
            return C1;
        } else {
            return -1;
        }
    }

    /*********Comprueba si algún jugador se quedó sin fichas(o sea que apunte a NULL)
     * para declararlo Ganador
    Returna Verdadero si algún jugador ganó*********/
    public boolean compruebaGanó() {

        //Comprueba si cada jugador tiene fichas y si no;  lo cierra y lo declara ganador
        //Y cambia la varible para cerrar el juego
        if (jug1.mano == null) {
            //repaint();
            ingame = false;
            return true;
        }
        if (jug2.mano == null) {
            ingame = false;
            return true;
            //repaint();
        }
        if (jug3.mano == null) {
            ingame = false;
            return true;
            //repaint();
        }
        if (player.mano == null) {
            sonido son = new sonido();
            son.ganador();
            ingame = false;
            return true;
        }
        //Si ningun jgador apunta null
        return false;
    }

    /*********Asigan a los 4 jugadores sus 7 fichas Al azar *********/
    private void repartir() {
        //Variables para controlar la asignación de fichas
        int index = 0;

        /////////////////La asignación aleatoria de las fichas///////////////
        Random randomficha = new Random();
        int n = 28;  //numeros aleatorios
        int k = n;  //auxiliar;
        int[] numeros = new int[n];  //Vector de los numeros del 0 al 28
        int[] resultado = new int[n];
        int res;

        //Llenamos el vetor con con los numeros del 0 al 28
        for (int i = 0; i < n; i++) {
            numeros[i] = i;
        }

        ///Genera los numeros aleatorios del 0 al 27 sin repetir en un vector Resultado/////////
        for (int i = 0; i < n; i++) {
            res = randomficha.nextInt(k);
            resultado[i] = numeros[res];
            numeros[res] = numeros[k - 1];
            k--;
        }
        ////Se inicializan los contadores del vector de las fichas de cada jugador. por si se vuelve a repartir
        jug1 = new Player("Andres");
        jug2 = new Player("Cristian");
        jug3 = new Player("Joche");
        player = new Player(name);
        Mesa = new Mesa();
        ingame = true;
        TurnoSw = true;
        //Le asigna a cada jugador ua Ficha, valiendose de los numeros alearios del vector Resultado
        for (int i = 0; i < 28; i++) {
            switch (index) {
                case 0:
                    jug1.mano[jug1.cont] = new Ficha();
                    jug1.mano[jug1.cont] = Fichas[resultado[i]];
                    jug1.cont++;
                    index = 1;
                    break;

                case 1:
                    jug2.mano[jug2.cont] = new Ficha();
                    jug2.mano[jug2.cont] = Fichas[resultado[i]];
                    jug2.cont++;
                    index = 2;
                    break;

                case 2:
                    jug3.mano[jug3.cont] = new Ficha();
                    jug3.mano[jug3.cont] = Fichas[resultado[i]];
                    jug3.cont++;
                    index = 3;
                    break;

                case 3:
                    player.mano[player.cont] = new Ficha();
                    player.mano[player.cont] = Fichas[resultado[i]];
                    player.cont++;
                    index = 0;
                    break;
            }
        }
    }

    /*********Asigana a quien le toca jugar, ///al azar al principio y luego en orden*********/
    public Player turno() {

        //Se sortea el jugador que inicia
        if (TurnoSw) {
            Random ranjug = new Random();
            int r = ranjug.nextInt(4);
            switch (r) {
                case 0:
                    temp = jug1;
                    break;

                case 1:
                    temp = jug2;
                    break;

                case 2:
                    temp = jug3;
                    break;

                case 3:
                    temp = player;
                    break;
            }
            TurnoSw = !TurnoSw;
            ///Se cambia la variable para que luego siga el orden normal y no se siga sorteando
            return temp;

        } else {

            //condiciones para que continúe el orden luego de de elegir el primero
            if (temp == jug1) {
                temp = player;
                return temp;
            }
            if (temp == jug2) {
                temp = jug1;
                return temp;
            }
            if (temp == jug3) {
                temp = jug2;
                return temp;
            }
            if (temp == player) {
                temp = jug3;
                return temp;
            }
        }

        return temp;
    }

    /*********Asigana a cada Ficha los valores y las imagenes*********/
    private void generar() {

        //creamos un vector de fichas.
        for (int i = 0; i < 28; i++) {
            Fichas[i] = new Ficha();
        }

        //Llenamos las fichas del vector asignandole el valor a ambas caras
        Fichas[0].C1 = 0;
        Fichas[0].C2 = 0;
        Fichas[0].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/0-0.png")).getImage();
        Fichas[0].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/0-0.2.png")).getImage();
        Fichas[0].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/0-0.3.png")).getImage();
        Fichas[0].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/0-0.4.png")).getImage();

        Fichas[1].C1 = 1;
        Fichas[1].C2 = 0;
        Fichas[1].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-0.png")).getImage();
        Fichas[1].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-0.2.png")).getImage();
        Fichas[1].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-0.3.png")).getImage();
        Fichas[1].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-0.4.png")).getImage();

        Fichas[2].C1 = 2;
        Fichas[2].C2 = 0;
        Fichas[2].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-0.png")).getImage();
        Fichas[2].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-0.2.png")).getImage();
        Fichas[2].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-0.3.png")).getImage();
        Fichas[2].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-0.4.png")).getImage();

        Fichas[3].C1 = 3;
        Fichas[3].C2 = 0;
        Fichas[3].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-0.png")).getImage();
        Fichas[3].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-0.2.png")).getImage();
        Fichas[3].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-0.3.png")).getImage();
        Fichas[3].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-0.4.png")).getImage();

        Fichas[4].C1 = 4;
        Fichas[4].C2 = 0;
        Fichas[4].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-0.png")).getImage();
        Fichas[4].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-0.2.png")).getImage();
        Fichas[4].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-0.3.png")).getImage();
        Fichas[4].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-0.4.png")).getImage();

        Fichas[5].C1 = 5;
        Fichas[5].C2 = 0;
        Fichas[5].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-0.png")).getImage();
        Fichas[5].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-0.2.png")).getImage();
        Fichas[5].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-0.3.png")).getImage();
        Fichas[5].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-0.4.png")).getImage();

        Fichas[6].C1 = 6;
        Fichas[6].C2 = 0;
        Fichas[6].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/6-0.png")).getImage();
        Fichas[6].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/6-0.2.png")).getImage();
        Fichas[6].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/6-0.3.png")).getImage();
        Fichas[6].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/6-0.4.png")).getImage();

        Fichas[7].C1 = 1;
        Fichas[7].C2 = 1;
        Fichas[7].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-1.png")).getImage();
        Fichas[7].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-1.2.png")).getImage();
        Fichas[7].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-1.3.png")).getImage();
        Fichas[7].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-1.4.png")).getImage();

        Fichas[8].C1 = 1;
        Fichas[8].C2 = 2;
        Fichas[8].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-2.png")).getImage();
        Fichas[8].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-2.2.png")).getImage();
        Fichas[8].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-2.3.png")).getImage();
        Fichas[8].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-2.4.png")).getImage();

        Fichas[9].C1 = 1;
        Fichas[9].C2 = 3;
        Fichas[9].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-3.png")).getImage();
        Fichas[9].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-3.2.png")).getImage();
        Fichas[9].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-3.3.png")).getImage();
        Fichas[9].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-3.4.png")).getImage();

        Fichas[10].C1 = 1;
        Fichas[10].C2 = 4;
        Fichas[10].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-4.png")).getImage();
        Fichas[10].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-4.2.png")).getImage();
        Fichas[10].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-4.3.png")).getImage();
        Fichas[10].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-4.4.png")).getImage();

        Fichas[11].C1 = 1;
        Fichas[11].C2 = 5;
        Fichas[11].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-5.png")).getImage();
        Fichas[11].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-5.2.png")).getImage();
        Fichas[11].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-5.3.png")).getImage();
        Fichas[11].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-5.4.png")).getImage();

        Fichas[12].C1 = 1;
        Fichas[12].C2 = 6;
        Fichas[12].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-6.png")).getImage();
        Fichas[12].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-6.2.png")).getImage();
        Fichas[12].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-6.3.png")).getImage();
        Fichas[12].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/1-6.4.png")).getImage();

        Fichas[13].C1 = 2;
        Fichas[13].C2 = 2;
        Fichas[13].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-2.png")).getImage();
        Fichas[13].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-2.2.png")).getImage();
        Fichas[13].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-2.3.png")).getImage();
        Fichas[13].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-2.4.png")).getImage();

        Fichas[14].C1 = 2;
        Fichas[14].C2 = 3;
        Fichas[14].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-3.png")).getImage();
        Fichas[14].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-3.2.png")).getImage();
        Fichas[14].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-3.3.png")).getImage();
        Fichas[14].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-3.4.png")).getImage();

        Fichas[15].C1 = 2;
        Fichas[15].C2 = 4;
        Fichas[15].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-4.png")).getImage();
        Fichas[15].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-4.2.png")).getImage();
        Fichas[15].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-4.3.png")).getImage();
        Fichas[15].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-4.4.png")).getImage();

        Fichas[16].C1 = 2;
        Fichas[16].C2 = 5;
        Fichas[16].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-5.png")).getImage();
        Fichas[16].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-5.2.png")).getImage();
        Fichas[16].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-5.3.png")).getImage();
        Fichas[16].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-5.4.png")).getImage();

        Fichas[17].C1 = 2;
        Fichas[17].C2 = 6;
        Fichas[17].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-6.png")).getImage();
        Fichas[17].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-6.2.png")).getImage();
        Fichas[17].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-6.3.png")).getImage();
        Fichas[17].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/2-6.4.png")).getImage();

        Fichas[18].C1 = 3;
        Fichas[18].C2 = 3;
        Fichas[18].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-3.png")).getImage();
        Fichas[18].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-3.2.png")).getImage();
        Fichas[18].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-3.3.png")).getImage();
        Fichas[18].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-3.4.png")).getImage();

        Fichas[19].C1 = 3;
        Fichas[19].C2 = 4;
        Fichas[19].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-4.png")).getImage();
        Fichas[19].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-4.2.png")).getImage();
        Fichas[19].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-4.3.png")).getImage();
        Fichas[19].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-4.4.png")).getImage();

        Fichas[20].C1 = 3;
        Fichas[20].C2 = 5;
        Fichas[20].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-5.png")).getImage();
        Fichas[20].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-5.2.png")).getImage();
        Fichas[20].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-5.3.png")).getImage();
        Fichas[20].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-5.4.png")).getImage();

        Fichas[21].C1 = 3;
        Fichas[21].C2 = 6;
        Fichas[21].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-6.png")).getImage();
        Fichas[21].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-6.2.png")).getImage();
        Fichas[21].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-6.3.png")).getImage();
        Fichas[21].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/3-6.4.png")).getImage();

        Fichas[22].C1 = 4;
        Fichas[22].C2 = 4;
        Fichas[22].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-4.png")).getImage();
        Fichas[22].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-4.2.png")).getImage();
        Fichas[22].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-4.3.png")).getImage();
        Fichas[22].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-4.4.png")).getImage();

        Fichas[23].C1 = 4;
        Fichas[23].C2 = 5;
        Fichas[23].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-5.png")).getImage();
        Fichas[23].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-5.2.png")).getImage();
        Fichas[23].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-5.3.png")).getImage();
        Fichas[23].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-5.4.png")).getImage();

        Fichas[24].C1 = 4;
        Fichas[24].C2 = 6;
        Fichas[24].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-6.png")).getImage();
        Fichas[24].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-6.2.png")).getImage();
        Fichas[24].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-6.3.png")).getImage();
        Fichas[24].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/4-6.4.png")).getImage();

        Fichas[25].C1 = 5;
        Fichas[25].C2 = 5;
        Fichas[25].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-5.png")).getImage();
        Fichas[25].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-5.2.png")).getImage();
        Fichas[25].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-5.3.png")).getImage();
        Fichas[25].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-5.4.png")).getImage();

        Fichas[26].C1 = 5;
        Fichas[26].C2 = 6;
        Fichas[26].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-6.png")).getImage();
        Fichas[26].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-6.2.png")).getImage();
        Fichas[26].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-6.3.png")).getImage();
        Fichas[26].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/5-6.4.png")).getImage();

        Fichas[27].C1 = 6;
        Fichas[27].C2 = 6;
        Fichas[27].Ima = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/6-6.png")).getImage();
        Fichas[27].Ima2 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/6-6.2.png")).getImage();
        Fichas[27].Ima3 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/6-6.3.png")).getImage();
        Fichas[27].Ima4 = new ImageIcon(this.getClass().getResource("Imagenes/Fichas/6-6.4.png")).getImage();

    }

    /***Returna verdadero si la mesa aún no ha sido cerada***/
    public boolean MesaIsNull() {
        if (Mesa.getcabeza() == null) {
            return true;
        } else {
            return false;
        }

    }

    /***Returna la mesa de juego***/
    public Mesa getMesa() {
        return Mesa;
    }

    /*****Indica por donde puede jugar el participante,
     *1->cabeza, 2->cola, -1->no puede jugar, 0->por ambas puntas de la mesa****/
    public int DondeJugar() {

        if (MesaIsNull()) {
            return 1;
        } else {
            if ((Mesa.getAux().info.C1 == Ftemp.C1 || Mesa.getAux().info.C1 == Ftemp.C2) && (Mesa.getAux().info.C2 == Ftemp.C1 || Mesa.getAux().info.C2 == Ftemp.C2)) {
                return 0;
            } else {
                if (Mesa.getAux().info.C1 == Ftemp.C1 || Mesa.getAux().info.C1 == Ftemp.C2) {
                    return 1;
                } else {
                    ///Evalúa las dos caras de la fichas para ver si se puede insertar en la Cola de la lista
                    if (Mesa.getAux().info.C2 == Ftemp.C1 || Mesa.getAux().info.C2 == Ftemp.C2) {
                        return 2;
                    } else {
                        ///Sino se puede insertar en la cola ni en la cabeza, la Ficha no sirve
                        //JOptionPane.showMessageDialog(null,"Ficha no sirve:  "+Ftemp.C1+","+Ftemp.C2);
                        return -1;
                    }
                }
            }
            ///Evalúa las dos caras de la fichas para ver si se puede insertar en la cabeza de la lista

        }
    }

    /****Inserta la ficha en la mesa 
     * 
     * @param desc 
     * 1 cabeza,  2 cola.
     * -1 parametro desconocido
     * @return 
     * -1, parametro desconocido 
     */
    public int Insertar(int desc) {
        if (desc == 1) {
            Mesa.insertarcabeza(Ftemp);
        } else {
            if (desc == 2) {
                Mesa.insertcola(Ftemp);
            } else {
                return -1;
            }

        }
        //borramos la Ficha de la mano del jugador
        ///Si el jugador tiene una sola Ficha a la mano se le asigna null, para indicar que ganó
        if (temp.mano.length != 1) {
            Ficha[] aux = new Ficha[temp.mano.length - 1];
            for (int i = 0; i < temp.mano.length; i++) {
                //si el elemento esta en la ultima pocision se devuelve el vector con los elementos copiados hasto ahora
                //Sin el elemento
                if (i == temp.mano.length - 1) {
                    temp.mano = aux;
                    return 0;
                }
                aux[i] = temp.mano[i];  //Va copiando los elementos de un vector a otro
                if (Ftemp == temp.mano[i]) {
                    for (int j = i; j < temp.mano.length - 1; j++) {
                        aux[j] = temp.mano[j + 1];//Si encuentra el elemento lo va reemplazando por el siguiente
                    }
                    temp.mano = aux;
                    return 0;
                }
            }
        } else {
            temp.mano = null;
        }
        return 0;
    }
}
