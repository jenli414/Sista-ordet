package se.liu.ida.jenli414.tddd78.sista_ordet;

import net.miginfocom.swing.MigLayout;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Category;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Rounds;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Difficulty;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**JPanel that contains the "Settings" Menu in the program.
 * Besides adding its graphical components to itself, it will also make sure
 * that the settings that are set by default in the selectors are also set
 * in game by calling setSettings().**/
public class SettingsPanel extends JPanel {

    private final Game game;
    private final NavigationListener navigationListener;
    private final ActionListener settingsSelectorListener;
    private JComboBox<String> difficultySelector;
    private final String[] difficulties = Difficulty.getDifficultyNames();
    private JComboBox<String> categorySelector;
    private final String [] categories = Category.getCategoryNames();
    private JComboBox<String> roundsSelector;
    private final String[] rounds = Rounds.getRoundsNames();
    private static final Dimension SELECTORDIMENSION = new Dimension(250, 50);
    /**A set value for the font size used for the JComboBoxes' inner JLabels.*/
    public static final int JCOMBOBOX_FONT_SIZE = 20;
    private static final int LABEL_FONT_SIZE = 30;
    private static final int SMALL_BUTTON_FONT_SIZE = 22;

    public SettingsPanel(final Game game,
			 final NavigationListener navigationListener) {
        super(new MigLayout("","[570, center][70, left][640, left]",
			    		"[202][152][152][152][202]"));
	this.game = game;
	this.navigationListener = navigationListener;
	settingsSelectorListener = new SettingsSelectorsListener(this, game);
        //PanelHeader
	add(new PanelHeader("Settings"), "cell 0 0, span 3");
        //DifficultyLabel "cell 0 1, right"
	addLabel("Difficulty: ", "cell 0 1, right");
        //DifficultyInfoButton "cell 1 1"
	addDifficultyInfoButton();
        //DifficultySelector "cell 2 1, gap left 50"
	difficultySelector = new JComboBox<>(difficulties);
	addSelector(difficultySelector, 1, "Difficulty", "cell 2 1, gap left 50");
        //CategoryLabel "cell 0 2, right"
	addLabel("Category: ", "cell 0 2, right");
        //CategorySelector "cell 2 2, gap left 50"
	categorySelector = new JComboBox<>(categories);
	addSelector(categorySelector, 0, "Category", "cell 2 2, gap left 50");
        //RoundsLabel "cell 0 3, right"
	addLabel("Rounds: ", "cell 0 3, right");
        //RoundSelector "cell 2 3, gap left 50"
	roundsSelector = new JComboBox<>(rounds);
	addSelector(roundsSelector, 2, "Rounds", "cell 2 3, gap left 50");
	//ReturnButton "cell 0 4, span 3"
	addReturnButton();
	//
	setSettings();
    }

    private void addLabel(String text, String position) {
	JLabel difficultyLabel = new JLabel(text);
 	difficultyLabel.setFont(new Font("Calibri",
					 Font.PLAIN, LABEL_FONT_SIZE));
 	add(difficultyLabel, position);
    }

    private void addDifficultyInfoButton() {
	JButton difficultyInfoButton = new JButton("?");
	difficultyInfoButton.setFont(new Font("Calibri",
					  Font.PLAIN, SMALL_BUTTON_FONT_SIZE));
	difficultyInfoButton.setPreferredSize(new Dimension(40, 40));
	difficultyInfoButton.setActionCommand("Difficulty Info");
	difficultyInfoButton.addActionListener(navigationListener);
	add(difficultyInfoButton, "cell 1 1");
    }

    private void addSelector(JComboBox<String> selector, int selectedIndex, String actionCommand, String position) {
        selector.setFont(new Font("Calibri", Font.PLAIN,
						    JCOMBOBOX_FONT_SIZE));
        ((JLabel)selector.getRenderer()).setHorizontalAlignment(
                    				SwingConstants.CENTER);
        selector.setBackground(Color.WHITE);
        selector.setSelectedIndex(selectedIndex);
        selector.setActionCommand(actionCommand);
        selector.setPreferredSize(SELECTORDIMENSION);
	selector.addActionListener(settingsSelectorListener);
	add(selector, position);
    }

    private void addReturnButton() {
	JButton returnButton = new JButton("Return");
	returnButton.setMnemonic('R');
	returnButton.setFont(new Font("Calibri", Font.PLAIN,
						    SMALL_BUTTON_FONT_SIZE));
	returnButton.setPreferredSize(new Dimension(122, 50));
	returnButton.setActionCommand("Return");
	returnButton.addActionListener(navigationListener);
	add(returnButton, "cell 0 4, span 3");
    }

    public void setSettings() {
	String selectedDifficulty =
			    difficultySelector.getSelectedItem().toString();
	String selectedDifficultyEnumName =
		selectedDifficulty.replaceAll("\\s", "").toUpperCase();
	game.setDifficulty(Difficulty.valueOf(selectedDifficultyEnumName));
	String selectedCategory =
				categorySelector.getSelectedItem().toString();
	String selectedCategoryEnumName =
		selectedCategory.replaceAll("\\s","").toUpperCase();
	game.setCategory(Category.valueOf(selectedCategoryEnumName));
	String selectedRoundsOption =
	    			roundsSelector.getSelectedItem().toString();
	game.setRounds(Rounds.getRounds(selectedRoundsOption));
    }

    public JComboBox<String> getDifficultySelector() {
	return difficultySelector;
    }

    public JComboBox<String> getCategorySelector() {
	return categorySelector;
    }

    public JComboBox<String> getRoundsSelector() {
	return roundsSelector;
    }
}
