import processing.core.PApplet;

public class Tank extends RenderObject {
    final static int MOVE_LEFT = 51;
    final static int MOVE_RIGHT = 52;
    final static int MOVE_UP = 51;
    final static int MOVE_DOWN = 52;
    final static int STAY_LEFT = 60;
    final static int STAY_RIGHT = 60;
    final static int STAY_UP = 60;
    final static int STAY_DOWN = 60;

    int mode = STAY_LEFT;

    int velocity = 5;

    Tank(PApplet pApplet) {
        super(pApplet);
        this.x = 0;
        this.y = 0;
    }

    @Override
    public void update() {
        String message = "#MOVE#";
        if (mode == MOVE_LEFT) {
            message = message + "{LEFT}";
            this.x -= velocity;
        } else if (mode == MOVE_RIGHT) {
            message = message + "{RIGHT}";
            this.x += velocity;
        } else if (mode == MOVE_DOWN) {
            message = message + "{DOWN}";
            this.y -= velocity;
        } else if (mode == MOVE_UP) {
            message = message + "{UP}";
            this.y += velocity;
        }
        //os 이용해서  서버로 보내야함
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

}
