import processing.core.PApplet;
import processing.core.PConstants;

public class Map extends RenderObject {
//    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];
    int blockWidth = Constants.WINDOW_WIDTH / Constants.MAP_ROW;
    int blockHeight = Constants.WINDOW_HEIGHT / Constants.MAP_COL;

    final static int MAP_ROAD = 0;
    final static int MAP_BLOCK = 1;
    private int type;
    private int x;
    private int y;
    public Map(PApplet p, int type, int x, int y) {
        super(p);
        this.mode = type;
        this.allocMode(MAP_ROAD, "SourceImage", new int[]{
                0
        });
        this.allocMode(MAP_BLOCK, "SourceImage", new int[] {
                30
        });
        this.x = x;
        this.y = y;


    }


    @Override
    public void setMode(int mode) {
        super.setMode(mode);
    }

    private int tick;
    public void render() {
        tick ++;
        p.image(renderImages.get(mode)[tick / 10 % renderImages.get(mode).length], x*Constants.OBJECT_SIZE , y*Constants.OBJECT_SIZE );
    }

}
