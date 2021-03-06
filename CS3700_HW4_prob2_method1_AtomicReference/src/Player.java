import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Player implements Runnable {
    int score;
    String name;
    ArrayList<Player> playersList;
    String gesture;
    Player playerLost;
    boolean gestureMade;
    boolean comparingDone;
    boolean readyForNewGame;
    AtomicReference<String> currentAction;
    AtomicBoolean countingScoreStarted;
    public Player(String name, ArrayList<Player> playersList, AtomicReference<String> currentAction, AtomicBoolean countingScoreStarted,Player playerLost){
        this.name = name;
        this.playersList = playersList;
        this.currentAction = currentAction;
        this.playerLost = playerLost;
        this.gesture = makeAGesture();
        this.score = 0;
        this.gestureMade = false;
        this.comparingDone = false;
        this.readyForNewGame = true;
        this.countingScoreStarted = countingScoreStarted;
    }

    @Override
    public void run() {
        int totalPlayerAmount = playersList.size();
        while(playersList.size()>1){
            if(playerLost== null ||(playerLost!=null&& playerLost != this) ){



                if(currentAction.get().equals("New Game Started")){
                    currentAction.set("Preparing For New Game");

                }else if(currentAction.get().equals("Preparing For New Game")){
                    if(readyForNewGame == false){
                        gestureMade = false;
                        comparingDone = false;
                        readyForNewGame = true;
                        countingScoreStarted.set(false);
                        score = 0;
                    }
                    int playerReadyForGame = 0;
                    for(int i = 0;i<playersList.size();i++){
                        if(playersList.get(i).readyForNewGame == true){
                            playerReadyForGame++;
                        }
                    }
                    if(playerReadyForGame == playersList.size()){
                        currentAction.set("Making Gesture");
                    }
                }else if(currentAction.get().equals("Making Gesture")){
                    if(gestureMade == false){
                        this.gesture = makeAGesture();
                        gestureMade =true;
                    }
                    int playerReadyAmount = 0;
                    for(int i = 0;i<playersList.size();i++){
                        if(playersList.get(i).gestureMade == true){
                            playerReadyAmount++;
                        }
                    }
                    if(playerReadyAmount == playersList.size()){
                        currentAction.set("Comparing Gesture");
                    }
                }else if(currentAction.get().equals("Comparing Gesture")){
                    if(comparingDone == false){
                        compareGesture();
                        comparingDone = true;
                        readyForNewGame = false;
                    }
                    int playerReadyForResult = 0;
                    for(int i = 0;i < playersList.size();i++){
                        if(playersList.get(i).comparingDone == true){
                            playerReadyForResult++;
                        }
                    }
                    if(playerReadyForResult == playersList.size()){
                        currentAction.set("Waiting For Result");

                    }
                }else if(countingScoreStarted.compareAndExchange(false,true)&&currentAction.get().equals("Waiting For Result")){

                    currentAction.set("Counting Score");
                    playerLost = findLowestScore();
                    if(playersList.remove(playerLost)){
                        System.out.println("Current Round:"+(totalPlayerAmount-playersList.size()) +":Player "+playerLost.name+" has the lowest Score: "+playerLost.score+". Eliminated.");
                    }
                    currentAction.set("New Game Started");
                    //System.out.println("Current Round:"+(totalPlayerAmount-playersList.size()) +":Player "+playerLost.name+" has the lowest Score: "+playerLost.score+". Eliminated.");
                }else if(currentAction.get().equals("Counting Score")){
                    //System.out.println("waiting");
                    //do nothing , for test purpose
                }else{
                    //do nothing , for test purpose

                }

            }


        }


        System.out.println("Player "+this.name+" quited.");
    }
    public void compareGesture(){
        for(int i = 0; i < playersList.size();i++){
            String opponentsGesture = playersList.get(i).gesture;
            //System.out.println(opponentsGesture);
            if( opponentsGesture.equals("scissors")  && this.gesture.equals("rock")||
                    opponentsGesture.equals("paper")  && this.gesture.equals("scissors")||
                    opponentsGesture.equals("rock")  && this.gesture.equals("paper")){//win
                this.score++;
            }else if( opponentsGesture.equals("rock")  && this.gesture.equals("scissors")||
                    opponentsGesture.equals("scissors")  && this.gesture.equals("paper")||
                    opponentsGesture.equals("paper")  && this.gesture.equals("rock")){//lose
                this.score--;
            }else if( opponentsGesture.equals(opponentsGesture) ){//tie
                //do nothing
            }else{
                //nor win, not lose, nor tie, something wrong
                System.out.println(this.name + " something wrong,opponent:"+i+" "+opponentsGesture+" my:"+gesture);
            }
        }

    }

    public String makeAGesture(){
        Random rand = new Random();
        int tempInt = rand.nextInt(3);
        if(tempInt == 0){
            return "rock";
        } else if(tempInt == 1){
            return "scissors";
        }else{
            return "paper";
        }
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
