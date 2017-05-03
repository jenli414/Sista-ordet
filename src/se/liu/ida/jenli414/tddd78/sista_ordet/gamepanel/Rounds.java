package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import java.util.Collection;
import java.util.LinkedList;

/*This may look redundant because displayName is just rounds as a String,
* but we wanted to keep this because the others selectors in settings follow
* the same pattern.*/
/**Enum class for how many rounds are going to be played.
 * Contains String displayName and int rounds.**/
public enum Rounds {

    /**Enum for roundsselection "1"**/
    ONE("1", 1),
    /**Enum for roundsselection "2"**/
    TWO("2", 2),
    /**Enum for roundsselection "3"**/
    THREE("3", 3),
    /**Enum for roundsselection "4"**/
    FOUR("4", 4),
    /**Enum for roundsselection "5"**/
    FIVE("5", 5),
    /**Enum for roundsselection "6"**/
    SIX("6", 6),
    /**Enum for roundsselection "7"**/
    SEVEN("7", 7),
    /**Enum for roundsselection "8"**/
    EIGHT("8", 8),
    /**Enum for roundsselection "9"**/
    NINE("9", 9),
    /**Enum for roundsselection "10"**/
    TEN("10", 10);

    private final String displayName;
    private final int rounds;

    Rounds(final String displayName, final int rounds){
        this.displayName = displayName;
	this.rounds = rounds;
    }

    public String getDisplayName() {
	return displayName;
    }

    public int getRounds(){
            return rounds;
        }

    public static String [] getRoundsNames() {
	Collection<String> roundsNames = new LinkedList<>();
	for (Rounds rounds : Rounds.values()) {
	    roundsNames.add(rounds.displayName);
	}
	return roundsNames.toArray(new String [roundsNames.size()]);
    }

    public static Rounds getRounds(String roundsDisplayName)
	    throws IllegalArgumentException {
	Rounds correspondingRounds = null;
	for (Rounds rounds : Rounds.values()) {
	    if (rounds.displayName.equals(roundsDisplayName)) {
		correspondingRounds = rounds;
	    }
	}
	assert(correspondingRounds != null);
	return correspondingRounds;
    }
}
