
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    private static final int SCREEN_WHITH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (SCREEN_WHITH * SCREEN_HEIGHT) / UNIT_SIZE;
    private static final int DELAY = 100;
    private final int x[] = new int[UNIT_SIZE];
    private final int y[] = new int[UNIT_SIZE];
    private int bodyParts = 1;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private final Random random;

    public GamePanel() {
        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WHITH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        starGame();
    }

    private void starGame() {
        newAppel();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Draw(g);
    }

    public void Draw(Graphics g) {
        if (running) {
            for (int i = 0; i < (SCREEN_HEIGHT / UNIT_SIZE); i++) {
                g.drawLine((i * UNIT_SIZE), 0, (i * UNIT_SIZE), SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WHITH, (i * UNIT_SIZE));
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            //
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 50));
            FontMetrics m = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WHITH - m.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        } else {
            GameOver(g);
        }
    }

    public void newAppel() {
        appleX = random.nextInt(SCREEN_WHITH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void Move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' ->
                y[0] -= UNIT_SIZE;
            case 'D' ->
                y[0] += UNIT_SIZE;
            case 'L' ->
                x[0] -= UNIT_SIZE;
            case 'R' ->
                x[0] += UNIT_SIZE;
        }
    }

    public void CheckAppel() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newAppel();
        }
    }

    public void CheckCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
            if (x[0] < 0) {
                running = false;
            }
            if (x[0] > SCREEN_WHITH) {
                running = false;
            }
            if (y[0] < 0) {
                running = false;
            }
            if (y[0] > SCREEN_HEIGHT) {
                running = false;
            }
            if (!running) {
                timer.stop();
            }
        }
    }

    public void GameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics m = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WHITH - m.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        //
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics f = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WHITH - f.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            Move();
            CheckAppel();
            CheckCollisions();
        }
        repaint();
    }

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT,KeyEvent.VK_A -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }
                case KeyEvent.VK_RIGHT,KeyEvent.VK_D -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }
                case KeyEvent.VK_UP,KeyEvent.VK_W -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }
                case KeyEvent.VK_DOWN,KeyEvent.VK_S -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
            }
        }

    }
}
