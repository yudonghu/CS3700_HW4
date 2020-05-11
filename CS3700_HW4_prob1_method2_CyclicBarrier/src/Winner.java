import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Winner implements Runnable{

    ArrayList<Player> playersList;
    Player lostPlayer;
    int totalPlayerAmount;
    CyclicBarrier yieldWinnerBarrier;
    public Winner(ArrayList<Player> playersList,CyclicBarrier yieldWinnerBarrier,Player lostPlayer){
        this.playersList = playersList;
        this.lostPlayer = lostPlayer;
        this.totalPlayerAmount = playersList.size();
        this.yieldWinnerBarrier = yieldWinnerBarrier;
    }
    @Override
    public void run() {
        while(playersList.size()>1){
            try {
                yieldWinnerBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            lostPlayer = findLowestScore();
            if(playersList.remove(lostPlayer)){
                System.out.println("Current Round:"+(totalPlayerAmount-playersList.size()) +":Player "+lostPlayer.name+" has the lowest Score: "+lostPlayer.score+". Eliminated.");
            }


        }
        System.out.println("After "+(totalPlayerAmount-playersList.size())+" rounds.Player "+playersList.get(0).name+" is The Final Winner");
    }

    public Player findLowestScore(){
        int lowest = playersList.get(0).score;
        int indexOfLowest = 0;
        for(int i = 1; i < playersList.size();i++){
            if(playersList.get(i).score < lowest){
                lowest = playersList.get(i).score;
                indexOfLowest = i;
            }
        }

        return playersList.get(indexOfLowest);
    }
}

