import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

interface OnReceived {
    void onReceive(String packet);
}

class ReaderThread extends Thread {
    private DataInputStream dis;
    private OnReceived onReceived;

    void setOnReceived(OnReceived onReceived) {
        this.onReceived = onReceived;
    }


    public ReaderThread(InputStream is) {
        this.dis = new DataInputStream(is);
    }

    @Override
    public void run() {
        byte[] data = new byte[1024];
        try {
            while (true) {
                int packetLen = dis.readInt();
                readn(dis, data, packetLen);
                String message = new String(data, 0, packetLen);
                if (onReceived != null) {
                    onReceived.onReceive(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
}