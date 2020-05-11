import java.io.*;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.*;

public class FileReverser implements Callable {

    BufferedReader buffer;
    CountDownLatch reverserCountDown;
  //  FileReader

     LinkedBlockingDeque stringDeque;
    public FileReverser(BufferedReader buffer, LinkedBlockingDeque stringDeque,CountDownLatch reverserCountDown){
        this.stringDeque = stringDeque;
        this.buffer = buffer;
        this.reverserCountDown = reverserCountDown;
    }
    @Override
    public Object call() throws Exception {
        long startingTime = System.currentTimeMillis();
//        System.out.println("started" + wordScanner.hasNext());
//        System.out.println("th " + wordScanner.next());
        String s;
        while ((s = buffer.readLine())!= null){
            Scanner wordScanner = new Scanner(s);
            while (wordScanner.hasNext()){
                stringDeque.offer(wordScanner.next());
            }
            //System.out.println(stringDeque.size());
        }
        reverserCountDown.countDown();

        return startingTime;
    }

}
