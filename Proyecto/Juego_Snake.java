import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class Juego_Snake {

    private static final Logger LOG = Logger.getLogger(Juego_Snake.class.getName());

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
        });
    }

}
