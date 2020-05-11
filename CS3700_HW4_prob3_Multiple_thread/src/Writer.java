import javax.annotation.processing.SupportedSourceVersion;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

public class Writer implements Callable {


    BufferedWriter bufferedWriter;
    LinkedBlockingDeque<String> stringDeque;
    CountDownLatch reverserCountDown;
    public Writer( LinkedBlockingDeque stringDeque,BufferedWriter bufferedWriter,CountDownLatch reverserCountDown){
        this.stringDeque =stringDeque;
        this.bufferedWriter = bufferedWriter;
        this.reverserCountDown = reverserCountDown;
    }

    @Override
    public Object call() throws Exception {
        long startingTime = System.currentTimeMillis();
        String s ="";
        while(stringDeque.size() > 0 || reverserCountDown.getCount() > 0){
            //System.out.println("here"+ (stringDeque.size() > 0) +" "+ (reverserCountDown.getCount() > 0) +" "+stringDeque.size());
            //bufferedWriter.write((char[]) stringDeque.poll());
            //System.out.println();
            s= stringDeque.poll() +" "+ s;
        }
        bufferedWriter.write(s);
        bufferedWriter.flush();
        bufferedWriter.close();
        return System.currentTimeMillis();
    }

}
