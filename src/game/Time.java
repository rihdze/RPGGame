package game;

public class Time {
    private int updateSinceStart;

    public Time() {
        this.updateSinceStart = 0;
    }

    public int getUpdatesFromSeconds(double seconds){
        return (int)Math.round(seconds * GameLoop.UPDATES_PER_SECOND);
    }

    public void update(){
        updateSinceStart++;
    }
    public String getFormattedTime(){
        StringBuilder stringBuilder = new StringBuilder();
        int minutes = updateSinceStart / GameLoop.UPDATES_PER_SECOND / 60;
        int seconds = updateSinceStart / GameLoop.UPDATES_PER_SECOND % 60;

        if(minutes < 10){
            stringBuilder.append(0);
        }
        stringBuilder.append(minutes);
        stringBuilder.append(":");
        if(seconds < 10){
            stringBuilder.append("0");
        }
        stringBuilder.append(seconds);

        return stringBuilder.toString();
    }

    public double getSeconds(){
        return updateSinceStart / GameLoop.UPDATES_PER_SECOND % 60;
    }

    public void reset(){
        updateSinceStart = 0;
    }


    public boolean secondsDividableBy(double seconds) {

        return updateSinceStart % getUpdatesFromSeconds(seconds) == 0;
    }
}
