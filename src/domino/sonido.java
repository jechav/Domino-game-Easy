/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domino;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 *
 * @author Andres
 */


public class sonido {
    
    Clip fondo; 
    Clip juego; 
    Clip paso; 
    
    public void juego(){
    try {

            // Se obtiene un Clip de sonido
            juego = AudioSystem.getClip();

            // Se carga con un fichero wav                                    
            juego.open(AudioSystem.getAudioInputStream(this.getClass().getResource("Sonidos/jugo.wav")));

            // Comienza la reproducción
            juego.loop(0);
            
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    public void paso(){

  try {

            // Se obtiene un Clip de sonido
            paso = AudioSystem.getClip();

            // Se carga con un fichero wav
            paso.open(AudioSystem.getAudioInputStream(this.getClass().getResource("Sonidos/pase.wav")));
            paso.loop(0);
            // Comienza la reproducción
           

        } catch (Exception e) {
            System.out.println("" + e);
        }

}

    public void fondo(){

  try {

            // Se obtiene un Clip de sonido
            fondo = AudioSystem.getClip();            

            // Se carga con un fichero wav
            fondo.open(AudioSystem.getAudioInputStream(this.getClass().getResource("Sonidos/sea.wav")));
            fondo.loop(0);                      
            // Comienza la reproducción
           
             
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }
    public void menuItem(){

  try {

            // Se obtiene un Clip de sonido
            fondo = AudioSystem.getClip();            

            // Se carga con un fichero wav
            //fondo.open(AudioSystem.getAudioInputStream(this.getClass().getResource("Sonidos/Select.wav")));                        
            fondo.open(AudioSystem.getAudioInputStream(this.getClass().getResource("Sonidos/Select2.wav")));                        
            fondo.loop(0);                      
            // Comienza la reproducción
           
             
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }
    public void ganador(){

  try {

            // Se obtiene un Clip de sonido
            fondo = AudioSystem.getClip();            

            // Se carga con un fichero wav
            fondo.open(AudioSystem.getAudioInputStream(this.getClass().getResource("Sonidos/THRASH.WAV")));                        
            fondo.loop(0);                      
            // Comienza la reproducción
           
             
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }    
    public void Revolver(){

  try {

            // Se obtiene un Clip de sonido
            fondo = AudioSystem.getClip();            

            // Se carga con un fichero wav
            fondo.open(AudioSystem.getAudioInputStream(this.getClass().getResource("Sonidos/Revolver.wav")));                        
            fondo.loop(0);                      
            // Comienza la reproducción
           
             
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }  
}



