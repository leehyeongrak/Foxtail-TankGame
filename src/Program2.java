import processing.core.PApplet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Program2 extends PApplet {
    private OutputStream os;
    int[][] map = new int[Constants.MAP_COL][Constants.MAP_ROW];
    int blockWidth = Constants.WINDOW_WIDTH / Constants.MAP_ROW;
    int blockHeight = Constants.WINDOW_HEIGHT / Constants.MAP_COL;

    private OnReceived onReceived = new OnReceived() {
        @Override
        public void onReceive(String message) {
            System.out.println(message);
        }
    };

    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        try {
//            Socket socket = new Socket("192.168.11.2", 3007);
//            os = socket.getOutputStream();
//            ReaderThread readerThread = new ReaderThread(socket.getInputStream());
//            readerThread.setOnReceived(onReceived);
//            readerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() {
        makeMap();

    }

    @Override
    public void draw() {
        drawMap();

    }

    private void drawMap() {
        for (int y = 0; y < Constants.MAP_COL; y++) {
            for (int x = 0; x < Constants.MAP_ROW; x++) {
                if (map[y][x] == Constants.BLOCK_EMPTY) {
                    this.fill(100);
                } else if (map[y][x] == Constants.BLOCK) {
                    this.fill(200);
                }
                int startX = x * blockWidth;
                int startY = y * blockHeight;
                this.rect(startX, startY, blockWidth, blockHeight);
            }
        }
    }

    private void makeMap() {
        map = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 1, 1, 1, 0, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 1, 1, 0, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},};
    }

    public static void readn(DataInputStream is, byte[] data, int size) throws IOException {
        int left = size;
        int offset = 0;

        while (left > 0) {
            int len = is.read(data, offset, left);
            left -= len;
            offset += len;
        }
    }


    public static void main(String[] args) {
        PApplet.main("Program2");
    }
}

