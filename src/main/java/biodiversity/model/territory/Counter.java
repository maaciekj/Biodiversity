package biodiversity.model.territory;

public class Counter {

    // TODO Counter counts all statistics from Territory

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

    public String getTime (){
        return "Time from last measure "+(System.currentTimeMillis()-lastTime);
    }

}
