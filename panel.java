import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class panel extends JPanel implements ActionListener {
    static final int scrwidth = 400;
    static final int scrht = 400;
    static final int unitsz = 25;
    static final int gameunits = (scrwidth * scrht) / unitsz;
    static final int delay = 105;
    final int x[] = new int[gameunits];
    final int y[] = new int[gameunits];
    int bodyParts = 4;
    int pointsChecked;
    int pointX;
    int pointY;
    int dir = 'r';
    boolean running = false;
    Timer timer;
    Random random;
    

    panel() {//constructor
        random = new Random();
        this.setPreferredSize(new Dimension(scrwidth, scrht));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(new keypress());
        startgame();
    }

    public void startgame() {//method for resetting point location
        newPoint();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.orange);
            g.fillOval(pointX, pointY, 20, 20);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                }
                g.fillRect(x[i], y[i], unitsz, unitsz);
            }
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Helvetica",Font.BOLD,25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: "+pointsChecked,(scrwidth - metrics.stringWidth("SCORE: "+pointsChecked))/2,g.getFont().getSize());
        }
    else {
        gameover(g);
    }
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (dir) {
            case 'u':
                y[0] = y[0] - unitsz;
                break;
            case 'd':
                y[0] = y[0] + unitsz;
                break;
            case 'l':
                x[0] = x[0] - unitsz;
                break;
            case 'r':
                x[0] = x[0] + unitsz;
                break;
        }
    }

    public void newPoint() {
        pointX = random.nextInt((int) (scrwidth / unitsz)) * unitsz;
        pointY = random.nextInt((int) (scrht / unitsz)) * unitsz;

    }

    public void checkPoint() {//method to score when hit point
        if (x[0] == pointX && y[0] == pointY){
            bodyParts++;
            pointsChecked++;
            newPoint();
        }
        }

    public void checkCollisions() { //check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        if (x[0] < 0) { //check if head touches left border
            running = false;
        }
        if (x[0] > scrwidth) { //check if head touches right border
            running = false;
        }
        if (y[0] < 0) { //check if head touches top border
            running = false;
        }
        if (y[0] > scrht) { //check if head touches bottom border
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }
    public void gameover(Graphics g){//gameover preface
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER!",(scrwidth - metrics.stringWidth("GAME OVER!"))/2, scrht/2);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink Free",Font.BOLD,25));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("HIGH SCORE: "+pointsChecked,(scrwidth - metrics1.stringWidth("HIGH SCORE: "+pointsChecked))/2, scrht/2 + 50);
    }

        @Override
        public void actionPerformed (ActionEvent e){
            if (running) {
                move();
                checkPoint();
                checkCollisions();
            }
                repaint();
        }
        private class keypress extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e) {

                switch(e.getKeyCode()){
                    case KeyEvent.VK_RIGHT:
                        if (dir != 'l'){
                            dir = 'r';
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (dir != 'r'){
                            dir = 'l';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (dir != 'd'){
                            dir = 'u';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (dir != 'u'){
                            dir = 'd';
                        }
                        break;
                }

            }

        }
    }