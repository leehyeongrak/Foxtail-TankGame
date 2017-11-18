import processing.core.PApplet;

public class Player extends RenderObject{
    final static int MOVE_LEFT = 51;
    final static int MOVE_RIGHT = 52;
    final static int MOVE_UP = 51;
    final static int MOVE_DOWN = 52;
    final static int STAY_LEFT = 60;
    final static int STAY_RIGHT = 60;
    final static int STAY_UP = 60;
    final static int STAY_DOWN = 60;

    public Player(PApplet p) {
        super(p);

        this.allocMode(MOVE_UP, "SourceImage", new int[] {
                1, 2, 3, 4, 5, 6, 7, 8
        });
        this.allocMode(STAY_UP, "SourceImage", new int[] {
                1
        });
    }

    @Override
    public void setMode(int mode) {
        super.setMode(mode);
    }
}
