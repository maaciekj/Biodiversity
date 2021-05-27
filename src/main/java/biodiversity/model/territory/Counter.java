package biodiversity.model.territory;

public class Counter {
    
    private int iterationsNumber;

    private long lastTime;

    public void addIteration(){
        iterationsNumber++;
    }

    public int getIterationNumber(){
        return iterationsNumber;
    }

    public void setTime(){
        lastTime = System.currentTimeMillis();
    }

    public int getTime (){
        return (int) (System.currentTimeMillis()-lastTime);
    }

}
