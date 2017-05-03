package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;

/**Label that displays a players current total- and roundscore.
 * Implements GameListener.**/
public class PlayerScoreLabel extends JLabel implements GameListener {

    private final Player player;
    private static final int FONT_SIZE = 25;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 80;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public PlayerScoreLabel(final Player player){
	this.player = player;
        setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
	setHorizontalAlignment(SwingConstants.CENTER);
	updateRoundScore();
    }

    @Override public Dimension getPreferredSize() {
            	return preferredSize;
                }

    private void updateRoundScore(){
	StringBuilder playerScoreLabelBuilder = new StringBuilder();
	int playerRoundScore = player.getRoundScore();
	int playerTotalScore = player.getTotalScore();
	playerScoreLabelBuilder.append("<html><center>Round Score: ");
	playerScoreLabelBuilder.append(playerRoundScore);
	playerScoreLabelBuilder.append("<br><br>Total Score: ");
	playerScoreLabelBuilder.append(playerTotalScore);
	playerScoreLabelBuilder.append("</center></html>");
	String playerScoreLabel = playerScoreLabelBuilder.toString();
	setText(playerScoreLabel);
    }

    @Override
    public void gameChanged() {
        updateRoundScore();
    }

}

