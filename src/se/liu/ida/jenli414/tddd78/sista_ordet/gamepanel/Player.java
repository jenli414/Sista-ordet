package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

/**Class for player-related information and methods.
 * Keeps track of player score, whether currently active
 * as well as if has last word.**/
public class Player {

    private String name;
    private static final int NAME_LENGTH_LIMIT = 15;
    private final int id;
    private int roundScore = 0;
    private int totalScore = 0;
    private boolean active = false;
    private boolean lastWord = false;

    public Player(String name, final int id) {
        assert(name.length() <= NAME_LENGTH_LIMIT);
	this.name = name;
        this.id = id;
    }

    public void addTurnScore(int turnScore){
        roundScore += turnScore;
    }

    public void addLastWordScore(int lastWordScore) {
	totalScore += lastWordScore;
    }

    public void addTotalScore(){
        totalScore += roundScore;
        roundScore = 0;
    }

    public void resetScores() {
	roundScore = 0;
	totalScore = 0;
    }

    public String getName() {
	return name;
    }

    public String getId() {
            return Integer.toString(id);
        }

    public int getRoundScore() {
	return roundScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setName(final String name) {
	assert(name.length() <= NAME_LENGTH_LIMIT);
	this.name = name;
    }

    public void setActive(boolean active) {
            this.active = active;
        }

    public void setLastWord(boolean lastWord) {
	this.lastWord = lastWord;
    }

    public boolean hasLastWord() {
	return lastWord;
    }

    public boolean isActive() {
        return active;
    }

    public static int getNameLengthLimit() {
        return NAME_LENGTH_LIMIT;
    }
}
