package base.stopwatch.chronometer;

/**
 * Created by filipeximenes on 2/18/15.
 */
public class ChronometerInterfaces {

    //communication from ForCoachesChronometer to ChronoList
    public interface UpdateChronosOnTickListener{
        void updateChronosOnTickListener(long time);
    }

    //communication from ChronoList to ForCoachesChronometer
    public interface GetChronoInformation{
        boolean started();
        long getBase();
        long getChronoElapsedTime();
    }

    //communication from ForCoachesChronometerBar to ChronoList
    public interface CommandChronoListFromBar{
        void clearAllChronos();
        void toggleLapOrTotal();
    }
}
