package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**Action- and FocusListener for enterButton and wordInputComponent.
 * These two objects act as one object when it comes to action- and
 * focusevents. (They do the same thing and act as if they were the
 * same object when they get or lose focus.)**/
public class WordInputListener implements ActionListener, FocusListener {

    private final GamePanel gamePanel;
    private JButton enterWordButton = null;
    private JFormattedTextField wordInputComponent = null;

    public WordInputListener(final GamePanel gamePanel) {
	this.gamePanel = gamePanel;
    }

    /**Called upon at the end of GamePanel constructor after enterWordButton
    and wordInputComponent have been created.**/
    public void updateComponents() {
	this.enterWordButton = gamePanel.getEnterWordButton();
	this.wordInputComponent = gamePanel.getWordInputComponent();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
	assert(wordInputComponent != null);
	gamePanel.enterWordInput();
    }

    @Override
    public void focusGained(FocusEvent e) {
	assert(wordInputComponent != null && enterWordButton != null);
	Component previousComponent = e.getOppositeComponent();
	if ((previousComponent == null) ||
	    		(!previousComponent.equals(wordInputComponent) &&
			!previousComponent.equals(enterWordButton))) {
	    wordInputComponent.setText(null);
	}
    }

    @Override
    public void focusLost(FocusEvent e) {
	assert(wordInputComponent != null && enterWordButton != null);
	Component nextComponent = e.getOppositeComponent();
	if ((nextComponent == null) ||
	    		(!nextComponent.equals(wordInputComponent) &&
			!nextComponent.equals(enterWordButton))) {
	    wordInputComponent.setText("Enter word...");
	}
    }
}
