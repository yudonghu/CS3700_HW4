import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class RPS_Game{
    public  static void main(String args[]){
        int playerAmount;
        Scanner scanner = new Scanner(System.in);
        System.out.println("how many players will play? ");
        playerAmount = scanner.nextInt();

        ArrayList<Player> playersList = new ArrayList<>();
        Player playerLost = null;
        AtomicReference<String> currentAction = new AtomicReference<>("New Game Started");

        for(int i = 0 ; i < playerAmount;i++){
            playersList.add(new Player(Integer.toString(i),playersList,currentAction, playerLost));
        }

        for(int i = 0 ; i < playerAmount;i++){
            new Thread(playersList.get(i)).start();
        }

        new Thread(new Winner(playersList,currentAction,playerLost)).start();
    }

}
