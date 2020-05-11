import java.io.*;
import java.sql.Time;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FileReverser implements Callable {


    public static void main(String args[]) throws IOException {
        long starting =System.currentTimeMillis();
        String str = "";

        Scanner scanner = null;
        String s = "";
        ConcurrentLinkedDeque<String> stringStack = new ConcurrentLinkedDeque<>();
        scanner = new Scanner(new File("D:\\DeclatationIndependence.txt"));
        while (scanner.hasNext()){
            Scanner scanner1 = new Scanner(scanner.nextLine());
            while (scanner1.hasNext()){
                s = scanner1.next()+" "+s;
            }
        }
        BufferedWriter bs = new BufferedWriter(new FileWriter(new File("D:\\backwards.txt")));
        bs.write(s);
        bs.flush();
        bs.close();
        System.out.println( System.currentTimeMillis()- starting +" milli-sec used");
    }





    @Override
    public Object call() throws Exception {
        return null;
    }
}
