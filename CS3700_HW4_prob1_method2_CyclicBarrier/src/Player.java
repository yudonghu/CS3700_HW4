import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Player implements Runnable {
    int score;
    String name;
    ArrayList<Player> playersList;
    String gesture;
    Player playerLost;
    CyclicBarrier gameStartBarrier;
    CyclicBarrier preparingForGameBarrier;
    CyclicBarrier makeGestureBarrier;
    CyclicBarrier compareGestureBarrier;
    CyclicBarrier yieldWinnerBarrier;
    public Player(String name, ArrayList<Player> playersList, CyclicBarrier gameStartBarrier, CyclicBarrier preparingForGameBarrier, CyclicBarrier makeGestureBarrier, CyclicBarrier compareGestureBarrier, CyclicBarrier yieldWinnerBarrier, Player playerLost){
        this.name = name;
        this.playersList = playersList;
        this.playerLost = playerLost;
        this.gesture = makeAGesture();
        this.score = 0;
        this.gameStartBarrier=gameStartBarrier;
        this.preparingForGameBarrier=preparingForGameBarrier;
        this.makeGestureBarrier=makeGestureBarrier;
        this.compareGestureBarrier=compareGestureBarrier;
        this.yieldWinnerBarrier=yieldWinnerBarrier;
    }

    @Override
    public void run() {

        while(playersList.size()>1){

            try {
                gameStartBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
//=========================================game start

            try {
                preparingForGameBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
//======================================player make score to 0
            score = 0;
            try {
                makeGestureBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
//======================================player make Gesture
            this.gesture = makeAGesture();


            try {
                compareGestureBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
//======================================player start to compare gesture
            compareGesture();


            try {
                yieldWinnerBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
//======================================player wait for winner Thread.

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
}
