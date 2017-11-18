import processing.core.PApplet;

public class Bullet extends RenderObject {
    int dir;
    int speed = 160 / 30;

    Bullet(PApplet pApplet, int x, int y, int dir) {
        super(pApplet);
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    @Override
    public void update() {
        if (dir == Constants.DIR_LEFT) {
            x -= speed;
        } else if (dir == Constants.DIR_RIGHT) {
            x += speed;
        } else if (dir == Constants.DIR_DOWN) {
            y += speed;
        } else if (dir == Constants.DIR_UP) {
            y-= speed;
        }
    }
}
