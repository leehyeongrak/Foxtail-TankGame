import processing.core.PApplet;

public class Player extends RenderObject{
    final static int MOVE_LEFT = 51;
    final static int MOVE_RIGHT = 52;
    final static int MOVE_UP = 53;
    final static int MOVE_DOWN = 54;
    final static int STAY_LEFT = 61;
    final static int STAY_RIGHT = 62;
    final static int STAY_UP = 63;
    final static int STAY_DOWN = 64;

    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];
    String id;
    int speed = 2;
    int dir = Constants.DIR_DOWN;

    public Player(PApplet p, String id, int[][] map) {
        super(p);
        this.mode = STAY_DOWN;
        this.id = id;
        this.x = 60;
        this.y = 60;
        this.map = map;

        this.allocMode(MOVE_LEFT, "SourceImage", new int[] {
                8, 7, 6, 5, 4, 3, 2, 1
        });
        this.allocMode(MOVE_RIGHT, "SourceImage", new int[] {
                8, 7, 6, 5, 4, 3, 2, 1
        });
        this.allocMode(MOVE_UP, "SourceImage", new int[] {
                8, 7, 6, 5, 4, 3, 2, 1
        });
        this.allocMode(MOVE_DOWN, "SourceImage", new int[] {
                8, 7, 6, 5, 4, 3, 2, 1
        });
        this.allocMode(STAY_LEFT, "SourceImage", new int[] {
                1
        });
        this.allocMode(STAY_RIGHT, "SourceImage", new int[] {
                1
        });
        this.allocMode(STAY_UP, "SourceImage", new int[] {
                1
        });
        this.allocMode(STAY_DOWN, "SourceImage", new int[] {
                1
        });
    }

    @Override
    public void setMode(int mode) {
        super.setMode(mode);
    }

    public void update(){
        String message = "#MOVE#";
        if (mode == MOVE_LEFT) {
            if(map[this.y/40][(this.x - Constants.OBJECT_SIZE/2 - 2)/40] == 1 ||
                    map[(this.y - Constants.OBJECT_SIZE/2 + 2)/40][(this.x - Constants.OBJECT_SIZE/2 - 2)/40] == 1 ||
                    map[(this.y + Constants.OBJECT_SIZE/2 - 2)/40][(this.x - Constants.OBJECT_SIZE/2 - 2)/40] == 1)
            {
                return;
            } else{
                message = message + "LEFT";
                this.x -= speed;
            }
        } else if (mode == MOVE_RIGHT) {
            if(map[this.y/40][(this.x + Constants.OBJECT_SIZE/2 + 2)/40] == 1 ||
                    map[(this.y - Constants.OBJECT_SIZE/2 + 2)/40][(this.x + Constants.OBJECT_SIZE/2 + 2)/40] == 1 ||
                    map[(this.y + Constants.OBJECT_SIZE/2 - 2)/40][(this.x + Constants.OBJECT_SIZE/2 + 2)/40] == 1)
            {
                return;
            } else {
                message = message + "RIGHT";
                this.x += speed;
            }
        } else if (mode == MOVE_DOWN) {
            if(map[(this.y + Constants.OBJECT_SIZE/2 + 2)/40][this.x/40] == 1 ||
                    map[(this.y + Constants.OBJECT_SIZE/2 + 2)/40][(this.x - Constants.OBJECT_SIZE/2 + 2)/40] == 1 ||
                    map[(this.y + Constants.OBJECT_SIZE/2 + 2)/40][(this.x + Constants.OBJECT_SIZE/2 - 2)/40] == 1)
            {
                return;
            } else {
                message = message + "DOWN";
                this.y += speed;
            }
        } else if (mode == MOVE_UP) {
            if(map[(this.y - Constants.OBJECT_SIZE/2 - 2)/40][this.x/40] == 1 ||
                    map[(this.y - Constants.OBJECT_SIZE/2 - 2)/40][(this.x - Constants.OBJECT_SIZE/2 + 2)/40] == 1 ||
                    map[(this.y - Constants.OBJECT_SIZE/2 - 2)/40][(this.x + Constants.OBJECT_SIZE/2 - 2)/40] == 1)
            {
                return;
            } else {
                message = message + "UP";
                this.y -= speed;
            }
        }
//        } else if (mode == STAY) {
//            return;
//        }
        System.out.println("                   OUT : "+message);
    }


    private int tick;
    @Override
    public void render() {
        tick ++;
        System.out.println(x + ", "+ y );
        switch (mode) {
            case 51:
            case 61:
                p.pushMatrix();
                p.translate(x, y);
                p.rotate(PApplet.radians(270));
                p.translate(-x, -y);
                p.image(renderImages.get(mode)[tick/10 % renderImages.get(mode).length], x - Constants.OBJECT_SIZE/2, y - Constants.OBJECT_SIZE/2);
                p.popMatrix();
                break;
            case 52:
            case 62:
                p.pushMatrix();
                p.translate(x, y);
                p.rotate(PApplet.radians(90));
                p.translate(-x, -y);
                p.image(renderImages.get(mode)[tick/10 % renderImages.get(mode).length], x - Constants.OBJECT_SIZE/2, y - Constants.OBJECT_SIZE/2);
                p.popMatrix();
                break;
            case 53:
            case 63:
                p.image(renderImages.get(mode)[tick/10 % renderImages.get(mode).length], x - Constants.OBJECT_SIZE/2, y - Constants.OBJECT_SIZE/2);
                break;
            case 54:
            case 64:
                p.pushMatrix();
                p.translate(x, y);
                p.rotate(PApplet.radians(180));
                p.translate(-x, -y);
                p.image(renderImages.get(mode)[tick/10 % renderImages.get(mode).length], x - Constants.OBJECT_SIZE/2, y - Constants.OBJECT_SIZE/2);
                p.popMatrix();
                break;

        }

    }

}
