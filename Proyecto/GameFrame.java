
import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {
        ventana();
    }

    private void ventana() {
        this.add(new GamePanel());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
