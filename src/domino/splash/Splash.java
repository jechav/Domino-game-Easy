package domino.splash;

import java.awt.Color;

public class Splash extends javax.swing.JFrame {

    HiloProgreso hilo;
    public static boolean finished;

    public Splash() {

        //Método por defecto
        initComponents();
        iniciarSplash();
        //Creamos un objeto HiloProgreso al cual
        //le pasamos por parámetro la barra de progreso
        hilo = new HiloProgreso(progreso);
        //Iniciamos el Hilo
        hilo.start();
        //Le damos tamaño y posición a nuestro Frame
        this.setLocationRelativeTo(null);
        //Liberamos recursos
        hilo = null;
    }

    public void iniciarSplash() {
        this.getjProgressBar1().setBorderPainted(true);
        this.getjProgressBar1().setForeground(new Color(50, 50, 153, 100));
        this.getjProgressBar1().setStringPainted(true);
        this.setTitle("Starting Dominoes");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        progreso = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new java.awt.BorderLayout());

        progreso.setFont(new java.awt.Font("Tahoma", 2, 11));
        jPanel1.add(progreso, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/domino/Imagenes/launch_netbeans.png"))); // NOI18N
        jLabel1.setFocusCycleRoot(true);
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar progreso;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JProgressBar getjProgressBar1() {
        return progreso;
    }
}
