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
    private DataInputStream dis;
    private User tank;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];
    int blockWidth = Constants.WINDOW_WIDTH / Constants.MAP_ROW;
    int blockHeight = Constants.WINDOW_HEIGHT / Constants.MAP_COL;

    private OnReceived onReceived = new OnReceived() {
        @Override
        public void onReceive(String message) {
            System.out.println(message);
            String[] packets = message.split("#");
            switch (packets[0]) {
                case "MAP":
                    makeMap(packets[1]);
                    break;
                case "UPDATE":
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        if (user.id.equals(packets[1])) {
                            user.x = (int) Double.parseDouble(packets[2]);
                            user.y = (int) Double.parseDouble(packets[3]);
                        }
                    }
            }

        }
    };

    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        try {
            Socket socket = new Socket("192.168.11.3", 5000);
            OutputStream os = socket.getOutputStream();
            dos = new DataOutputStream(os);
            dis = new DataInputStream(socket.getInputStream());

            String messageStr = "SET#" + Constants.ID;
            byte[] message = messageStr.getBytes();
            dos.writeInt(message.length);
            dos.write(message);

            messageStr = "MAP";
            message = messageStr.getBytes();
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
        users.add(tank);
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
        this.rect(tank.x, tank.y, 20, 20);
    }

    private void makeMap(String map) {
        String[] lines = map.split("\n");
        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                this.map[y][x] = Integer.parseInt(String.valueOf(line.charAt(x)));
            }
        }
    }


    public static void main(String[] args) {
        PApplet.main("Program2");
    }

    @Override
    public void keyPressed(KeyEvent event) {
        System.out.println("                                                             Key Pressed : " + keyCode);
        String messageStr = "";
        byte[] message;
        switch (keyCode) {
            case 37:
                tank.setMode(User.MOVE_LEFT);
                tank.dir = Constants.DIR_LEFT;
                messageStr = "MOVE#" + "LEFT";
                break;
            case 38:
                tank.setMode(User.MOVE_UP);
                tank.dir = Constants.DIR_UP;
                messageStr = "MOVE#" + "UP";
                break;
            case 39:
                tank.setMode(User.MOVE_RIGHT);
                tank.dir = Constants.DIR_RIGHT;
                messageStr = "MOVE#" + "RIGHT";
                break;
            case 40:
                tank.setMode(User.MOVE_DOWN);
                tank.dir = Constants.DIR_DOWN;
                messageStr = "MOVE#" + "DOWN";
                break;
            case 32:
                bullets.add(new Bullet(this, tank.x + 20, tank.y + 20, tank.dir));
                messageStr = "BULLET";
            case 82:
                settings();
                break;
        }
        if (messageStr.length() < 1) return;
        System.out.println("                                      OUT : " + messageStr);

        message = messageStr.getBytes();
        try {
            dos.writeInt(message.length);
            dos.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        String messageStr = "";
        switch (keyCode) {
            case 37:
            case 38:
            case 39:
            case 40:
                tank.setMode(User.STAY);
                messageStr = "BULLET";
                break;
        }
        
    }
}

