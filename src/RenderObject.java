import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;

public class RenderObject {

    protected HashMap<Integer, PImage[]> renderImages;
    protected int mode = 63;
    private PApplet p;

    public int x, y;


    public RenderObject(PApplet p) {
        renderImages = new HashMap<>();
        this.x = 42;
        this.y = 42;
        this.p = p;
    }

    public void setMode(int mode){
        this.mode = mode;
    }

    public int getMode(){
        return mode;
    }

    public void allocMode(int mode, String ResourceName, int[] indices){
        PImage[] resources = new PImage[indices.length];

        for(int i = 0; i < indices.length; i++){
            resources[i] = ResourceManager.getImage(ResourceName, indices[i]);

        }
        renderImages.put(mode, resources);
    }

    public void update(){

    }

    private int tick;
    public void render(){
        tick ++;

        switch (mode) {
            case 61: p.pushMatrix();
                p.translate(x, y);
                p.rotate(PApplet.radians(270));
                p.translate(-x, -y);
                p.image(renderImages.get(mode)[tick/10 % renderImages.get(mode).length], x - 42, y - 42);
                p.popMatrix();
                break;
            case 62: p.pushMatrix();
                p.translate(x, y);
                p.rotate(PApplet.radians(90));
                p.translate(-x, -y);
                p.image(renderImages.get(mode)[tick/10 % renderImages.get(mode).length], x - 42, y - 42);
                p.popMatrix();
                break;
            case 63: p.image(renderImages.get(mode)[tick/10 % renderImages.get(mode).length], x - 42, y - 42);
            break;
            case 64: p.pushMatrix();
                p.translate(x, y);
                p.rotate(PApplet.radians(180));
                p.translate(-x, -y);
                p.image(renderImages.get(mode)[tick/10 % renderImages.get(mode).length], x - 42, y - 42);
                p.popMatrix();
                break;
        }
    }



}
