import com.sun.org.apache.regexp.internal.RE;
import com.sun.tools.javac.code.Type;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Program extends PApplet {
    private DataOutputStream dos;
    private DataInputStream dis;

    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];
    int blockWidth = Constants.WINDOW_WIDTH / Constants.MAP_ROW;
    int blockHeight = Constants.WINDOW_HEIGHT / Constants.MAP_COL;

    private ArrayList<RenderObject> playerObjects = new ArrayList<>();
    private ArrayList<RenderObject> bulletObjects = new ArrayList<>();
    private ArrayList<RenderObject> mapObjects = new ArrayList<>();

    Player tank;
    Bullet bullet;

    private OnReceived onReceived = new OnReceived() {
        @Override
        public void onReceive(String message) {
            ResourceManager.init(Program.this);
            ResourceManager.setImage("SourceImage", "tanks_image.png", 84, 84, 8, 4);
            System.out.println(message);
            String[] packets = message.split("#");
            switch (packets[0]) {
                case "MAP":
                    makeMap(packets[1]);
                    break;
                case "UPDATE":
                    for (int i = 0; i < playerObjects.size(); i++) {
                        RenderObject player = playerObjects.get(i);
                        if (player.id.equals(packets[1])) {
                            player.x = (int) Double.parseDouble(packets[2]);
                            player.y = (int) Double.parseDouble(packets[3]);
                        }
                    }
            }

        }
    };

    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);try {
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

        playerObjects.add(tank = new Player(this, Constants.ID, map));


//        makeMap();

    }

    @Override
    public void draw() {
        this.background(0,0,0);

        for (RenderObject r: mapObjects) r.render();

        for (RenderObject r : playerObjects) r.update();
        for (RenderObject r : playerObjects) r.render();

        ArrayList<RenderObject> bulletsTemp = bulletObjects;
        for (int i = 0; i < bulletObjects.size(); i++) {
            RenderObject b = bulletsTemp.get(i);
            b.update();
            if (b.x < 0 || b.x > Constants.WINDOW_WIDTH || b.y < 0 || b.y > Constants.WINDOW_HEIGHT) {
                bulletObjects.remove(b);
            }
        }

        for (RenderObject r :bulletObjects) r.update();
        for (RenderObject r : bulletObjects) r.render();

    }

    @Override
    public void keyPressed(KeyEvent event) {
        System.out.println("Key Pressed : "+ keyCode);
        switch (keyCode) {
                case 37:
                        tank.setMode(Player.MOVE_LEFT);
                        tank.dir = Constants.DIR_LEFT;
                    break;
                case 38:
                        tank.setMode(Player.MOVE_UP);
                        tank.dir = Constants.DIR_UP;
                    break;
                case 39:
                        tank.setMode(Player.MOVE_RIGHT);
                        tank.dir = Constants.DIR_RIGHT;
                    break;
                case 40:
                        tank.setMode(Player.MOVE_DOWN);
                        tank.dir = Constants.DIR_DOWN;
                    break;
                case 32:
                    bulletObjects.add(bullet = new Bullet(this, tank.x, tank.y, tank.dir));
                    break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
                switch (keyCode) {
                    case 37:
                        tank.setMode(Player.STAY_LEFT);
                        break;
                    case 38:
                        tank.setMode(Player.STAY_UP);
                        break;
                    case 39:
                        tank.setMode(Player.STAY_RIGHT);
                        break;
                    case 40:
                        tank.setMode(Player.STAY_DOWN);
                        break;
                }
    }


    private void makeMap(String map) {

        String[] lines = map.split("\n");
        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                this.map[y][x] = Integer.parseInt(String.valueOf(line.charAt(x)));
            }
        }
        for(int y = 0; y < Constants.MAP_ROW; y++){
            for (int x = 0; x < Constants.MAP_COL; x++){
                System.out.println(this.map[y][x]);
                mapObjects.add(new Map(this, this.map[y][x], x, y));
                System.out.println(this.map[y][x] + " ");
            }
        }
        System.out.println("finishi");
    }

//    private boolean isCollision() {
//
//        switch (keyCode){
//            case 37 :
//                    if(map[tank.y/40][(tank.x - Constants.OBJECT_SIZE/2 - 2)/40] == 1 ||
//                            map[(tank.y - Constants.OBJECT_SIZE/2 + 2)/40][(tank.x - Constants.OBJECT_SIZE/2 - 2)/40] == 1 ||
//                            map[(tank.y + Constants.OBJECT_SIZE/2 - 2)/40][(tank.x - Constants.OBJECT_SIZE/2 - 2)/40] == 1)
////                if(map[(tank.y - Constants.OBJECT_SIZE/2)/40][(tank.x - Constants.OBJECT_SIZE/2 - 1)/40] == 1||
////                    map[(tank.y - Constants.OBJECT_SIZE/2 + 1)/40][(tank.x - Constants.OBJECT_SIZE/2 - 1)/40] == 1 ||
////                    map[(tank.y + Constants.OBJECT_SIZE/2 - 1)/40][(tank.x - Constants.OBJECT_SIZE/2 - 1)/40] == 1) {
//                    {
//                        System.out.println("Collision");
//                        return true;
//                    }
//                break;
//        }
//        return false;
//    }

    public static void main(String[] args) {
        PApplet.main("Program");
    }
}

