import processing.core.PApplet;
import processing.event.KeyEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Program2 extends PApplet {
    private DataOutputStream dos;
    private User tank;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];
    int blockWidth = Constants.WINDOW_WIDTH / Constants.MAP_ROW;
    int blockHeight = Constants.WINDOW_HEIGHT / Constants.MAP_COL;

    private OnReceived onReceived = new OnReceived() {
        @Override
        public void onReceive(String message) {
            System.out.println(message);
        }
    };

    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        try {
            Socket socket = new Socket("192.168.11.3", 5000);
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            String messageStr = "#set#" + Constants.ID;
            System.out.println(messageStr);

            byte[] message = messageStr.getBytes();
            System.out.println(message.length);
            dos.writeInt(message.length);
            dos.write(message);
            
            ReaderThread readerThread = new ReaderThread(socket.getInputStream());
            readerThread.setOnReceived(onReceived);
            readerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() {
        tank = new User(this);
        makeMap();
    }

    @Override
    public void draw() {
        this.background(0, 0, 0);
        drawMap();

        tank.update();
        drawTank();

        ArrayList<Bullet> bulletsTemp = bullets;
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bulletsTemp.get(i);
            b.update();
            if (b.x < 0 || b.x > Constants.WINDOW_WIDTH || b.y < 0 || b.y > Constants.WINDOW_HEIGHT) {
                bullets.remove(b);
            }
        }

        drawBullets();
    }

    private void drawBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            this.fill(10, 10, 200);
            this.ellipse(b.x, b.y, 20, 20);
        }
    }

    private void drawMap() {
        for (int y = 0; y < Constants.MAP_COL; y++) {
            for (int x = 0; x < Constants.MAP_ROW; x++) {
                if (map[y][x] == Constants.BLOCK_EMPTY) {
                    this.fill(100);
                } else if (map[y][x] == Constants.BLOCK) {
                    this.fill(200);
                }
                int startX = x * blockWidth;
                int startY = y * blockHeight;
                this.rect(startX, startY, blockWidth, blockHeight);
            }
        }
    }

    private void drawTank() {
        this.fill(200, 20, 20);
        this.rect(tank.x, tank.y, 40, 40);
    }

    private void makeMap() {
        map = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 1, 1, 1, 0, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 1, 1, 0, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},};
    }

    public static void readn(DataInputStream is, byte[] data, int size) throws IOException {
        int left = size;
        int offset = 0;

        while (left > 0) {
            int len = is.read(data, offset, left);
            left -= len;
            offset += len;
        }
    }


    public static void main(String[] args) {
        PApplet.main("Program2");
    }

    @Override
    public void keyPressed(KeyEvent event) {
        System.out.println("Key Pressed : "+ keyCode);
        switch (keyCode) {
            case 37:
                tank.setMode(User.MOVE_LEFT);
                tank.dir = Constants.DIR_LEFT;
                break;
            case 38:
                tank.setMode(User.MOVE_UP);
                tank.dir = Constants.DIR_UP;
                break;
            case 39:
                tank.setMode(User.MOVE_RIGHT);
                tank.dir = Constants.DIR_RIGHT;
                break;
            case 40:
                tank.setMode(User.MOVE_DOWN);
                tank.dir = Constants.DIR_DOWN;
                break;
            case 32:
                bullets.add(new Bullet(this, tank.x + 20, tank.y + 20, tank.dir));
            case 82:
                settings();
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        switch (keyCode) {
            case 37:
            case 38:
            case 39:
            case 40:
                tank.setMode(User.STAY);
                break;
        }
    }
}

