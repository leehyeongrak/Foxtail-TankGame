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

    final static int MOVE = 1;
    final static int STOP = 0;

    String id;
    int team = 0;
    int speed = 2;
    int dir;
    int isMove = STOP;

    public boolean disconnect = false;
    public boolean willBeDestroy = false;

    User(PApplet pApplet, String id) {
        super(pApplet);
        this.id = id;
        this.mode = STAY;
    }

    User(PApplet pApplet, String id, String x, String y, String dir, String team) {
        super(pApplet);
        this.x = (int) Double.parseDouble(x);
        this.y = (int) Double.parseDouble(y);
        this.id = id;
        if (dir.equals("LEFT")) {
            this.dir = Constants.DIR_LEFT;
        } else if (dir.equals("RIGHT")) {
            this.dir = Constants.DIR_RIGHT;
        } else if (dir.equals("UP")) {
            this.dir = Constants.DIR_UP;
        } else if (dir.equals("DOWN")) {
            this.dir = Constants.DIR_DOWN;
        }
        this.team = (int) Double.parseDouble(team);
    }

    public User(PApplet applet, int team, int x, int y, String id) {
        super(applet);
        this.team = team;
        this.x = x;
        this.y = y;
        this.id = id;
    }


    @Override
    public void update() {
        if (isMove == STOP) return;
        if (dir == Constants.DIR_LEFT) {
            this.x -= speed;
        } else if (dir == Constants.DIR_RIGHT) {
            this.x += speed;
        } else if (dir == Constants.DIR_DOWN) {
            this.y += speed;
        } else if (mode == Constants.DIR_UP) {
            this.y -= speed;
        } else if (mode == STAY) {
            return;
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setDir(String dir) {
        switch (dir) {
            case "LEFT":
                this.dir = Constants.DIR_LEFT;
                break;
            case "RIGHT":
                this.dir = Constants.DIR_RIGHT;
                break;
            case "UP":
                this.dir = Constants.DIR_UP;
                break;
            case "DOWN":
                this.dir = Constants.DIR_DOWN;
                break;
        }
    }
}
