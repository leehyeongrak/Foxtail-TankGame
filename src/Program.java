import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.ArrayList;

public class Program extends PApplet {
    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];
    private ArrayList<RenderObject> renderObjects = new ArrayList<>();
    Player tank;
    Bullet bullet;
    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }
    private boolean isPressed;
    @Override
    public void setup() {
        ResourceManager.init(this);
        ResourceManager.setImage("SourceImage", "tanks_image.png", 84, 84, 8, 4);
        renderObjects.add(tank = new Player(this));


//        makeMap();

    }

    @Override
    public void draw() {


        this.background(0,0,0);
        for (RenderObject r : renderObjects) r.update();
        for (RenderObject r : renderObjects) r.render();
    }

    private void makeMap() {
        map = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                        {0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                        {0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        };
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
                renderObjects.add(bullet = new Bullet(this, tank.x, tank.y, tank.dir));
                break;
//                bullets.add(new Bullet(this, tank.x + 20, tank.y + 20, tank.dir));
//            case 82:
//                settings();
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

    public static void main(String[] args) {
        PApplet.main("Program");
    }
}

