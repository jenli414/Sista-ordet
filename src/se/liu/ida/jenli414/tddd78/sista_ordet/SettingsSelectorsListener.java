package se.liu.ida.jenli414.tddd78.sista_ordet;

import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Category;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Rounds;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Difficulty;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Game;

import java.awt.event.*;

/**Listener for the selectors in SettingsPanel that will set the corresponding
 * variable in game (difficuly/category/rounds) to the selected item.*/
public class SettingsSelectorsListener implements ActionListener {

    private final SettingsPanel settingsPanel;
    private final Game game;

    public SettingsSelectorsListener(final SettingsPanel settingsPanel,
				     final Game game) {
	this.settingsPanel = settingsPanel;
	this.game = game;
    }

    @Override public void actionPerformed(final ActionEvent e) {
	String command = e.getActionCommand();
	switch (command) {
	    case "Category":
		String selectedCategoryName = settingsPanel.
			getCategorySelector().getSelectedItem().toString();
		String selectedCategoryEnumName =
		    selectedCategoryName.replaceAll("\\s","").toUpperCase();
		game.setCategory(
			Category.valueOf(selectedCategoryEnumName));
		break;
	    case "Difficulty":
		String selectedDifficultyName = settingsPanel.
			getDifficultySelector().getSelectedItem().toString();
		String selectedDifficultyEnumName =
		    selectedDifficultyName.replaceAll("\\s","").toUpperCase();
		game.setDifficulty(
		Difficulty.valueOf(selectedDifficultyEnumName.toUpperCase()));
		break;
	    case "Rounds":
		String selectedRoundsName =
		settingsPanel.getRoundsSelector().getSelectedItem().toString();
		game.setRounds(Rounds.getRounds(selectedRoundsName));
		break;
	}
    }
}
