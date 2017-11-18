import processing.core.PApplet;

public class Program extends PApplet {
    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];

    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    @Override
    public void setup() {
        makeMap();

    }

    @Override
    public void draw() {
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



    public static void main(String[] args) {
        PApplet.main("Program");
    }
}

