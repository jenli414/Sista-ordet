package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import net.miginfocom.swing.MigLayout;
import se.liu.ida.jenli414.tddd78.sista_ordet.MainFrame;
import se.liu.ida.jenli414.tddd78.sista_ordet.State;
import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**Panel that contains the graphical parts of the current game.
 * PlayerLabels are added according to how many players are in use in game.
 * Implements GameListener to detect when the game is over.**/
public class GamePanel extends JPanel implements GameListener {

    private final MainFrame mainFrame;
    private final WordInputListener wordInputListener =
	    			new WordInputListener(this);
    private final Game game;
    private final JLabel gameInfoLabel = new JLabel();
    private static final int GAME_INFO_PANEL_FONT_SIZE = 25;
    private final JButton enterWordButton = new JButton("Enter");
    private static final int ENTER_WORD_BUTTON_FONT_SIZE = 25;
    private final JFormattedTextField wordInputComponent =
				new JFormattedTextField("Enter word...");
    private static final int WORD_INPUT_COMPONENT_FONT_SIZE = 28;
    private final List<Player> players;
    private final List<PlayerNameLabel> playerNameLabels =
	    new ArrayList<>(4);
    private final List<PlayerScoreLabel> playerScoreLabels =
	    new ArrayList<>(4);
    private Collection<JLabel> labelsInUse = new ArrayList<>(8);

    public GamePanel(final MainFrame mainFrame, final Game game) {
	super(new MigLayout("", "[1280, center]",
			"[90][130][65][75][35][60][70][95][70][50][60][40]"));
	this.mainFrame = mainFrame;
	this.game = game;
	players = game.getPlayers();
	game.addGameListener(this);
	makePlayerLabels();
	//GameInfoLabel
	gameInfoLabel.setFont(new Font("Calibri", Font.PLAIN,
				       GAME_INFO_PANEL_FONT_SIZE));
	gameInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
	add(gameInfoLabel, "cell 0 0");
	//CurrentWordLabel
	add(new CurrentWordLabel(game), "cell 0 1");
	//RoundAndTurnLabel
	add(new RoundAndTurnLabel(game), "cell 0 2");
	//WordInputComponent
	wordInputComponent.setFont(new Font("Calibri", Font.PLAIN,
					    WORD_INPUT_COMPONENT_FONT_SIZE));
	wordInputComponent.setPreferredSize(new Dimension(550, 50));
	wordInputComponent.addActionListener(wordInputListener);
	wordInputComponent.addFocusListener(wordInputListener);
	add(wordInputComponent, "cell 0 3");
	//InputErrorLabel
	add(new InputErrorLabel(game), "cell 0 4");
	//EnterWordButton
	enterWordButton.setFont(new Font("Calibri", Font.PLAIN,
					 ENTER_WORD_BUTTON_FONT_SIZE));
	enterWordButton.setHorizontalAlignment(SwingConstants.CENTER);
	enterWordButton.setPreferredSize(new Dimension(110, 40));
	enterWordButton.addActionListener(wordInputListener);
	enterWordButton.addFocusListener(wordInputListener);
	add(enterWordButton, "cell 0 5");
	//PlayerNameLabel "cell 0 6, gap left 25, gap right 25"*
	//PlayerScoreLabel "cell 0 7, gap left 25, gap right 25"*
	/*Player Labels are added once we know how many we need.
	(With adjustToCurrentGame() upon game start)*/
	//LastWordLabel
	add(new LastWordLabel(game), "cell 0 8, wrap 20");
	//TimeLeftComponent
	add(new TimeLeftComponent(game), "cell 0 9");
	//TimeLeftLabel
	add(new TimeLeftLabel(game), "cell 0 10");
	//
	wordInputListener.updateComponents();
    }

    public void adjustToCurrentGame() {
	reset();
	updateGameInfoLabel();
	for (int i = 0; i < game.getPlayersInUse(); i++) {
	    playerNameLabels.get(i).setPlayerName();
	    add(playerNameLabels.get(i),
		"cell 0 6, gap left 25, gap right 25");
	    add(playerScoreLabels.get(i),
		"cell 0 7, gap left 25, gap right 25");
	    labelsInUse.add(playerNameLabels.get(i));
	    labelsInUse.add(playerScoreLabels.get(i));
	}
    }

    private void updateGameInfoLabel() {
	StringBuilder gameInfoLabelStringBuilder = new StringBuilder();
	gameInfoLabelStringBuilder.append("<html><center>Category: ");
	gameInfoLabelStringBuilder.append(game.getCategoryName());
	gameInfoLabelStringBuilder.append("<br>Difficulty: ");
	gameInfoLabelStringBuilder.append(game.getDifficultyName());
	gameInfoLabelStringBuilder.append("</center></html>");
	gameInfoLabel.setText(gameInfoLabelStringBuilder.toString());
    }

    public void enterWordInput() {
	game.setWordInput(wordInputComponent.getText());
	game.checkWordInput();
	wordInputComponent.setText(null);
    }

    private void reset() {
	for (JLabel label : labelsInUse) {
	    remove(label);
	}
	labelsInUse.clear();
    }

    private void makePlayerLabels() {
	for (int i = 0; i < 4; i++) {
	    PlayerNameLabel playerNameLabel =
		    new PlayerNameLabel(game, players.get(i));
	    playerNameLabels.add(playerNameLabel);
	    PlayerScoreLabel playerScoreLabel =
		    new PlayerScoreLabel(players.get(i));
	    game.addGameListener(playerScoreLabel);
	    playerScoreLabels.add(playerScoreLabel);
	}
    }

    @Override public void gameChanged() {
	if (game.isGameOver()) {
	    mainFrame.setState(State.NEWGAME);
	}
    }

    public JFormattedTextField getWordInputComponent() {
	return wordInputComponent;
    }

    public JButton getEnterWordButton() {
	return enterWordButton;
    }
}
