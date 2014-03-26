package domino.splash;

import java.util.Random;
import javax.swing.JProgressBar;

public class HiloProgreso extends Thread {

    JProgressBar progreso;

    public HiloProgreso(JProgressBar progreso1) {
        super();
        this.progreso = progreso1;
    }

    @Override
    public void run() {
        Random ale = new Random();
        int delay = 20;
        int tmp = ale.nextInt(100);

        for (int i = 0; i <= 100; i++) {
            progreso.setValue(i);
            pausa(delay);

            if (i > tmp && i < 90) {
                i = i + ale.nextInt(10);
                delay = delay - ale.nextInt(20);

            }            
            finish(i);
        }
    }

    public void finish(int i) {
        if (progreso.getMaximum() == i) {
            Splash.finished = true;
        }
    }

    public void pausa(int mlSeg) {
        try {
            // pausa para el splash
            Thread.sleep(mlSeg);
        } catch (Exception e) {
        }
    }
}
