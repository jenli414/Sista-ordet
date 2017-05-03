package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import java.util.Collection;
import java.util.LinkedList;

/**Enum class for avaliable word categories plus
 * their table- and display names.**/
public enum Category {

    /**Enum for category "Countries"**/
    COUNTRIES("Country", "Countries"),

    /**Enum for category "Cities"**/
    CITIES("Cities", "Cities");

    private final String table;
    private final String displayName;

    Category(final String table, final String displayName){
        this.table = table;
        this.displayName = displayName;
    }

    public String getTable(){
        return this.table;
    }

    public String getDisplayName() {
	return this.displayName;
    }

    public static String [] getCategoryNames() {
	Collection<String> categoryNames = new LinkedList<>();
	for (Category category : Category.values()) {
	    categoryNames.add(category.displayName);
	}
	return categoryNames.toArray(new String [categoryNames.size()]);
    }
}
