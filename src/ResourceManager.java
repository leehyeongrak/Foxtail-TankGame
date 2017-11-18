import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;


import java.util.HashMap;

public class ResourceManager {

//    이미지 로드가 필요하다 , 이미지를 큰 이미지 -> 잘라야 한다 자르기 위해서 필ㄹ요한 것
//            이미지를 가로 세로 개수 크기가 필요하다
//            여러 이미지를 관리 할 수 있어야 하기 때문에 ID가 필요하다

    private static final HashMap<String, PImage[]> resourceImages = new HashMap<>();
    private static PApplet pApplet;


    // 이미지를 가져 올 수 있어야 한다 -> 리소스 아이디에 따라서
    // 이미지 전체를 불러 올 수 있고 원하는 인덱스를 가져 올 수도 있따.


    public static void init(PApplet p){
        pApplet = p;
    }




    public static void setImage(String name, String path, int width, int height, int countX, int countY){
        PImage pImage = pApplet.loadImage(path);

        PImage[] pImages = new PImage[countX*countY];

        for(int i = 0 ; i < countX; i++) {
            for (int j = 0; j <countY; j++) {
                pImages[j * countX  + i] = pImage.get(width * i, height * j, width, height);
                pImages[j * countX + i].resize(Constants.OBJECT_SIZE, Constants.OBJECT_SIZE);
            }
        }



        resourceImages.put(name, pImages);
    }

    public static PImage getImage(String resourceId, int index){
        PImage pImage;
        pImage = resourceImages.get(resourceId)[index];

        return pImage;
    }

}
