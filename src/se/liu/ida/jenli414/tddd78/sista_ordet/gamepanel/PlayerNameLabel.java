package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.font.TextAttribute;

/**Label that displays a player name according to it's status*.
 * (*for example currentPlayer/active/inactive.)
 * Implements GameListener.**/
public class PlayerNameLabel extends JLabel implements GameListener {

    private final Game game;
    private final Player player;
    private static final int FONT_SIZE = 30;
    private final Font activeFont;
    private final Font inactiveFont;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 80;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public PlayerNameLabel(final Game game, final Player player) {
	this.game = game;
	this.player = player;
	game.addGameListener(this);
	activeFont = new Font("Calibri", Font.PLAIN, FONT_SIZE);
	Map<TextAttribute, Object> attributes = new HashMap<>();
	attributes.put(TextAttribute.FAMILY, activeFont.getFamily());
	attributes.put(TextAttribute.SIZE, activeFont.getSize());
	attributes.put(TextAttribute.STRIKETHROUGH,
		       TextAttribute.STRIKETHROUGH_ON);
	inactiveFont = new Font(attributes);
	setFont(activeFont);
	setHorizontalAlignment(SwingConstants.CENTER);
	setForeground(Color.GRAY);
    }


    private void updatePlayerNameLabel() {
	String currentPlayerId = game.getCurrentPlayer().getId();
	if (!player.isActive()) {
	    setFont(inactiveFont);
	    setForeground(Color.RED);
	} else if (currentPlayerId.equals(player.getId())) {
	    setFont(activeFont);
	    setForeground(Color.BLACK);
	} else {
	    setFont(activeFont);
	    setForeground(Color.GRAY);
	}
    }

    @Override public void gameChanged() {
	updatePlayerNameLabel();
    }

    public void setPlayerName() {
	setText(player.getName());
    }

    @Override public Dimension getPreferredSize() {
    	return preferredSize;
        }
}
