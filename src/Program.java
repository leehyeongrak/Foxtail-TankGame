import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.ArrayList;

public class Program extends PApplet {
    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];
    private ArrayList<RenderObject> renderObjects = new ArrayList<>();

    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    @Override
    public void setup() {
        ResourceManager.init(this);
        ResourceManager.setImage("SourceImage", "tanks_image.png", 84, 84, 8, 4);

        renderObjects.add(new Player(this));

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
//        for(int y=0; y<Constants.MAP_COL; y++){
//            for(int x=0; x<Constants.MAP_ROW; x++){
//                System.out.print(map[y][x]);
//            }
//            System.out.println("");
//        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        keyCode = event.getKeyCode();
        switch (keyCode) {
//            case 37: {
//                renderObjects.get(0).setMode(1);
//                break;
//            }
            case 38: {
                renderObjects.get(0).setMode(51);
                break;
            }
//            case 39: {
//                renderObjects.get(0).setMode(2);
//                break;
//            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keyCode = event.getKeyCode();


        if (renderObjects.get(0).getMode() == 51) {
            renderObjects.get(0).setMode(60);
        }
//        else if (renderObjects.get(0).getMode() == 2) {
//            renderObjects.get(0).setMode(4);
//        }
    }

    public static void main(String[] args) {
        PApplet.main("Program");
    }
}

