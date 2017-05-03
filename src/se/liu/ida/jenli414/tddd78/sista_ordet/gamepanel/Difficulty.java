package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import java.util.Collection;
import java.util.LinkedList;

/**Enum class for avaliable difficulties plus their display name and
 * the associated timer time (turn time)**/
public enum Difficulty {

    /**Enum for difficulty "Beginner"**/
    BEGINNER("Beginner", 40),
    /**Enum for difficulty "Normal"**/
    NORMAL("Normal", 30),
    /**Enum for difficulty "Hardened"**/
    HARDENED("Hardened", 20),
    /**Enum for difficulty "Veteran"**/
    VETERAN("Veteran", 10),
    /**Enum for difficulty "Chuck Norris"**/
    CHUCKNORRIS("Chuck Norris", 5);

    private final String displayName;
    private final int turnTime;

    Difficulty(final String name, final int turnTime){
        this.displayName = name;
	this.turnTime = turnTime;
    }

    public String getDisplayName() {
	return this.displayName;
    }

    public int getTurnTime(){
            return this.turnTime;
        }

    public static String [] getDifficultyNames() {
	Collection<String> difficultyNames = new LinkedList<>();
	for (Difficulty difficulty : Difficulty.values()) {
	    difficultyNames.add(difficulty.displayName);
	}
	return difficultyNames.toArray(new String [difficultyNames.size()]);
    }
}
