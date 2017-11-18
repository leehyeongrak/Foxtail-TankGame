import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;

public class RenderObject {

    protected HashMap<Integer, PImage[]> renderImages;
    protected int mode;
    protected PApplet p;

    public int x, y;
    public String id;

    public RenderObject(PApplet p) {
        renderImages = new HashMap<>();
        this.x = Constants.OBJECT_SIZE/2;
        this.y = Constants.OBJECT_SIZE/2;
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
    public void render() {
        tick ++;
        p.image(renderImages.get(mode)[tick / 10 % renderImages.get(mode).length], x - Constants.OBJECT_SIZE/2, y - Constants.OBJECT_SIZE/2);
    }




}
