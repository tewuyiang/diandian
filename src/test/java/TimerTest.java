import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    public static void main(String[] args) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("aaa");
            }
        }, 0, 2000);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                t.cancel();
            }
        }, 1000*10);
    }
}
