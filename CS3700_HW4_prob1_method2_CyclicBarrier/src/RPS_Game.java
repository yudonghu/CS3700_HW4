import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class RPS_Game{
    public  static void main(String args[]){
        int playerAmount;
        Scanner scanner = new Scanner(System.in);
        System.out.println("how many players will play? ");
        playerAmount = scanner.nextInt();

        ArrayList<Player> playersList = new ArrayList<>();
        Player playerLost = null;
        CyclicBarrier gameStartBarrier = new CyclicBarrier(playerAmount);
        CyclicBarrier preparingForGameBarrier = new CyclicBarrier(playerAmount);
        CyclicBarrier makeGestureBarrier = new CyclicBarrier(playerAmount);
        CyclicBarrier compareGestureBarrier = new CyclicBarrier(playerAmount);
        CyclicBarrier yieldWinnerBarrier = new CyclicBarrier(playerAmount+1);


        for(int i = 0 ; i < playerAmount;i++){
            playersList.add(new Player(Integer.toString(i),playersList,gameStartBarrier,preparingForGameBarrier,makeGestureBarrier,compareGestureBarrier,yieldWinnerBarrier,playerLost));
        }

        for(int i = 0 ; i < playerAmount;i++){
            new Thread(playersList.get(i)).start();
        }

        new Thread(new Winner(playersList,yieldWinnerBarrier,playerLost)).start();
    }

}
