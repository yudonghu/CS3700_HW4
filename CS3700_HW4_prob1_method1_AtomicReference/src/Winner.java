import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Winner implements Runnable{

    ArrayList<Player> playersList;
    Player lostPlayer;
    AtomicReference<String> currentAction;
    int totalPlayerAmount;
    public Winner(ArrayList<Player> playersList,AtomicReference<String> currentAction,Player lostPlayer){
        this.playersList = playersList;
        this.currentAction = currentAction;
        this.lostPlayer = lostPlayer;
        this.totalPlayerAmount = playersList.size();
    }
    @Override
    public void run() {
        while(playersList.size()>1){
            if(currentAction.get().equals("Waiting For Result")){
                lostPlayer = findLowestScore();
                if(playersList.remove(lostPlayer)){
                    System.out.println("Current Round:"+(totalPlayerAmount-playersList.size()) +":Player "+lostPlayer.name+" has the lowest Score: "+lostPlayer.score+". Eliminated.");
                }

                currentAction.set("New Game Started");
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

