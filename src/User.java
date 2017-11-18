import processing.core.PApplet;

public class User extends RenderObject {
    final static int MOVE_LEFT = 51;
    final static int MOVE_RIGHT = 52;
    final static int MOVE_UP = 53;
    final static int MOVE_DOWN = 54;
    final static int STAY = 60;
    final static int STAY_LEFT = 61;
    final static int STAY_RIGHT = 62;
    final static int STAY_UP = 63;
    final static int STAY_DOWN = 64;


    String id;
    int speed = 2;
    int dir;

    User(PApplet pApplet, String id) {
        super(pApplet);
        this.id = id;
        this.x = 40;
        this.y = 40;
        this.mode = STAY;
    }
    User(PApplet pApplet) {
        super(pApplet);
        this.x = 40;
        this.y = 40;
        this.mode = STAY;
        id = Constants.ID;
    }
    @Override
    public void update() {
        String message = "#MOVE#";
        if (mode == MOVE_LEFT) {
            message = message + "LEFT";
            this.x -= speed;
        } else if (mode == MOVE_RIGHT) {
            message = message + "RIGHT";
            this.x += speed;
        } else if (mode == MOVE_DOWN) {
            message = message + "DOWN";
            this.y += speed;
        } else if (mode == MOVE_UP) {
            message = message + "UP";
            this.y -= speed;
        } else if (mode == STAY) {
            return;
        }
        System.out.println("                   OUT : "+message);
        //os 이용해서  서버로 보내야함
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

}
