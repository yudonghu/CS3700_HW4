import java.awt.*;
import java.io.*;
import java.util.concurrent.*;

public class main {
    public static void main(String args[]) throws IOException, ExecutionException, InterruptedException {
        long startingTime = System.currentTimeMillis();
        FileReader reader= new FileReader(new File("D:\\DeclatationIndependence.txt"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        LinkedBlockingDeque<String> mainStringDeque = new  LinkedBlockingDeque<String>();

        int workerAmount = 6;

        CountDownLatch reverserCountDown = new CountDownLatch(workerAmount);
        FutureTask[] reverserTask = new FutureTask[workerAmount];
        for(int i = 0 ;i < workerAmount ;i++){
            reverserTask[i]  = new FutureTask(new FileReverser(bufferedReader,mainStringDeque,reverserCountDown));
            new Thread(reverserTask[i] ).start();
        }

//        while (!ft.isDone()){}

        FileWriter writer = new FileWriter(new File("D:\\backwards.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        FutureTask writeTask = new FutureTask(new Writer(mainStringDeque,bufferedWriter ,reverserCountDown));
        new Thread(writeTask ).start();

        long earliestStartingTime = (long)reverserTask[0].get();
        for ( int i = 1; i < workerAmount;i++){
            if((long) reverserTask[i].get() < earliestStartingTime){
                earliestStartingTime = (long) reverserTask[i].get();
            }
        }
        System.out.println("Worker Amount:"+workerAmount+". Time used "+ ((long)writeTask.get() - earliestStartingTime)+" milli-sec.");
    }
}
