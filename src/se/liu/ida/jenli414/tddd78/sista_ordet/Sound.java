package se.liu.ida.jenli414.tddd78.sista_ordet;

/**Enum class for avaliable sounds. Contains the file path and the duration
 * which the thread that is going to run the SoundPlayer should sleep for.
 * A sound duration < 0 means that the sound should be looped.*/
public enum Sound {

    /**Enum for the "startgame.wav" sounds.**/
    STARTGAME("sounds/startgame.wav", 1),
    /**Enum for the "newround.wav" sounds.**/
    NEWROUND("sounds/newround.wav", 1),
    /**Enum for the "timerticking.wav" sounds.**/
    TIMERTICKING("sounds/timerticking.wav", -1),
    /**Enum for the "validword.wav" sounds.**/
    VALIDWORD("sounds/validword.wav", 1),
    /**Enum for the "invalidword.wav" sounds.**/
    INVALIDWORD("sounds/invalidword.wav", 1),
    /**Enum for the "timesup.wav" sounds.**/
    TIMESUP("sounds/timesup.wav", 1);

    private final String filePath;
    private final int duration;

    Sound(final String filePath, final int duration){
     this.filePath = filePath;
     this.duration = duration;
    }

    public String getFilePath() {
	return filePath;
    }

    public int getDuration() {
	return duration;
    }
}