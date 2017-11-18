import java.io.IOException;
import java.io.InputStream;

interface OnReceived {
    void onReceive(String packet);
}

class ReaderThread extends Thread {
    private InputStream is;
    private OnReceived onReceived;

    void setOnReceived(OnReceived onReceived) {
        this.onReceived = onReceived;
    }


    public ReaderThread(InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {
        byte[] data = new byte[1024];
        try {
            while (true) {
                int len = is.read(data);
                if (len == -1) break;
                String message = new String(data, 0, len);
                if (onReceived != null) {
                    onReceived.onReceive(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}