package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import se.liu.ida.jenli414.tddd78.sista_ordet.SoundPlayer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;

/**Class that keeps track of everything related to the current game
 * that is about to be or is being played.**/
public class Game
{
    private final Random rnd = new Random();
    private Collection<GameListener> gameListeners = new LinkedList<>();
    private final List<Player> players = new ArrayList<>(4);
    private List<Player> playersInUse = new ArrayList<>(4);
    private List<String> playerNames = new ArrayList<>(4);
    private Collection<String> enteredWords = new ArrayList<>();
    private boolean gameOver = false;
    private Difficulty difficulty = null;
    private int turnTime;
    private Category category = null;
    private Rounds rounds = null;
    private int totalRounds;
    private final DatabaseConfirmationTool databaseConfirmationTool =
	    					new DatabaseConfirmationTool();
    private String currentWord = "Waiting for word...";
    private String wordInput = null;
    private String inputErrorMessage = "";
    private Player currentPlayer;
    private final static int TIMER_DELAY = 10;
    private final Action timeLeftTimerAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    timerTick();
	}
    };
    private final Timer timeLeftTimer =
	    			new Timer(TIMER_DELAY, timeLeftTimerAction);
    private int roundCount = 0;
    private int turnCount;
    private float timeLeft;
    private final static int TURN_SCORE = 50;
    private final static int LAST_WORD_SCORE = 50;

    public Game() {
	for (int i = 1; i < 5; i++) {
	    players.add(new Player("", i));
	    playerNames.add("");
	}
	currentPlayer = players.get(0);
	timeLeftTimer.setCoalesce(true);
    }

    public void startGame() {
	assert(difficulty != null && category != null && rounds != null);
	SoundPlayer.playStartGameSound();
	configurePlayers();
	activatePlayers();
	resetTimer();
	nextRound();
    }

    public void stopGame() {
	SoundPlayer.stopAllSounds();
	stopTimer();
    }

    public void resetGame() {
	inputErrorMessage = "";
	gameOver = false;
	roundCount = 0;
	resetPlayerNames();
	resetPlayersInUse();
    }

    private void configurePlayers() {
	int playerCount = 0;
	for (int i = 0; i < 4; i++) {
	    Player player = players.get(playerCount);
	    String playerName = playerNames.get(i);
	    if (!playerNames.get(i).isEmpty()) {
		player.setName(playerName);
		playersInUse.add(player);
		playerCount++;
	    }
	}
	setCurrentPlayer(playersInUse.get(rnd.nextInt(playersInUse.size())));
    }

    private void activatePlayers() {
	for (Player player : players) {
	    player.setActive(true);
	}
	notifyListeners();
    }

    private void resetPlayersInUse() {
	for (Player player : players) {
	    player.setLastWord(false);
	    player.resetScores();
	    player.setName("");
	}
	playersInUse.clear();
    }

    private void resetPlayerNames() {
	playerNames.clear();
	for (int i = 0; i < 4; i++) {
	    playerNames.add("");
	}
    }

    private void startTimer() {
	SoundPlayer.playTimerTickingSound();
	timeLeftTimer.start();
	notifyListeners();
    }

    private void stopTimer() {
	SoundPlayer.stopTimerTickingSound();
	timeLeftTimer.stop();
    }

    private void resetTimer() {
	timeLeft = turnTime;
	notifyListeners();
    }

    private void timerTick() {
	timeLeft -= convertMillisecondsToSeconds(TIMER_DELAY);
	if (timeLeft <= 0) {
	    stopTimer();
	    SoundPlayer.playTimesUpSound();
	    resetTimer();
	    clearInputErrorMessage();
	    currentPlayer.setActive(false);
	    nextTurn();
	}
	notifyListeners();
    }

    public void checkWordInput() {
	clearInputErrorMessage();
	String newWord = wordInput.toLowerCase();
	if (!newWord.isEmpty()) {
	    if (!enteredWords.contains(newWord)) {
		if (databaseConfirmationTool.confirmWordInDatabase(
		    category.getDisplayName(), category.getTable(), newWord)) {
		    char lastCharOfNewWord = newWord.charAt(0);
		    if (turnCount == 0) {
			SoundPlayer.playValidWordSound();
			setCurrentWord(newWord);
			nextTurn();
		    } else if (getLastCharOfCurrentWord() ==
			       lastCharOfNewWord) {
			SoundPlayer.playValidWordSound();
			setCurrentWord(newWord);
			currentPlayer.addTurnScore(TURN_SCORE);
			nextTurn();
		    } else {
			SoundPlayer.playInvalidWordSound();
			setInputErrorMessage(
				"Invalid word. Last letter doesn't match.");
		    }
		} else {
		    SoundPlayer.playInvalidWordSound();
		    setInputErrorMessage(
			    "Invalid word. Word not in Category.");
		}
	    } else {
		SoundPlayer.playInvalidWordSound();
		setInputErrorMessage(
			"Invalid word. Word has already been entered.");
	    }
	} else {
	    SoundPlayer.playInvalidWordSound();
	    setInputErrorMessage("Invalid word. No word was entered.");
	}
	notifyListeners();
    }

    /**Increases turncount, sets currentplayer to the next
     * active player among players in use and starts the timer.**/
    private void nextTurn() {
	if (activePlayers() == 1) {
	    addLastWordScore();
	    updatePlayerScores();
	    activatePlayers();
	    nextRound();
	} else {
	    turnCount++;
	    int indexTracker = 0;
	    for (int i = 1; i < 4; i++) {
		int nextPlayerIndex = playersInUse.indexOf(currentPlayer) + i;
		if (nextPlayerIndex < playersInUse.size()) {
		    if (playersInUse.get(nextPlayerIndex).isActive()) {
			setCurrentPlayer(playersInUse.get(nextPlayerIndex));
			break;
		    }
		} else {
		    if (playersInUse.get(indexTracker).isActive()) {
			setCurrentPlayer(playersInUse.get(indexTracker));
			break;
		    } else {
			indexTracker++;
		    }
		}
	    }
	    resetTimer();
	    startTimer();
	    notifyListeners();
	}
    }

    private void nextRound() {
	roundCount ++;
	if (roundCount > totalRounds) {
	    roundCount--;
	    JOptionPane.showMessageDialog(null, getWinnersMessage(
		    getGameWinners()));
	    gameOver = true;
	    notifyListeners();
	}
	else {
	    if (roundCount > 1) {
		SoundPlayer.playNewRoundSound();
	    }
	    turnCount = 0;
	    enteredWords.clear();
	    setCurrentPlayer(playersInUse.get(
		    rnd.nextInt(playersInUse.size())));
	    currentWord = "Waiting for word...";
	}
	notifyListeners();
    }

    private void addLastWordScore() {
	for (Player player : players) {
	    if (player.hasLastWord()) {
		player.setLastWord(false);
		break;
	    }
	}
	for (Player player : playersInUse) {
	    if (player.isActive()) {
		player.addLastWordScore(LAST_WORD_SCORE);
		player.setLastWord(true);
	    }
	}
	notifyListeners();
    }

    private void updatePlayerScores() {
	for (Player player : players) {
	    player.addTotalScore();
	}
	notifyListeners();
    }

    private void clearInputErrorMessage() {
	inputErrorMessage = "";
    }

    private int activePlayers() {
	int activePlayers = 0;
	for (Player player : playersInUse) {
	    if (player.isActive()) {
		activePlayers++;
	    }
	}
	return activePlayers;
    }

    private String getWinnersMessage(Iterable<Player> winners) {
	StringBuilder messageBuilder = new StringBuilder();
	messageBuilder.append("Congratulations");
	for (Player winner : winners) {
	    messageBuilder.append(", ");
	    messageBuilder.append(winner.getName());
	}
	messageBuilder.append("! You win!");
	return messageBuilder.toString();
    }

    private Iterable<Player> getGameWinners() {
	Collection<Player> winners = new LinkedList<>();
	int highestScore = -1;
	for (Player player : playersInUse) {
	    if (player.getTotalScore() > highestScore) {
		highestScore = player.getTotalScore();
		winners.clear();
		winners.add(player);
	    }
	    else if (player.getTotalScore() == highestScore) {
		winners.add(player);
	    }
	}
	assert(!winners.isEmpty());
	return winners;
    }

    public void addGameListener(GameListener gameListener) {
	gameListeners.add(gameListener);
    }

    private void notifyListeners() {
	for (GameListener gameListener : gameListeners) {
	    gameListener.gameChanged();
	}
    }

    private float convertMillisecondsToSeconds(float milliSeconds) {
	return milliSeconds / 1000;
    }

    public List<Player> getPlayers() {
	        return players;
    }

    public List<String> getPlayerNames() {
	return playerNames;
    }

    public int getPlayersInUse() {
	return playersInUse.size();
    }

    public String getCurrentWord() {
	    return currentWord;
    }

    public String getTurnString() {
	return Integer.toString(turnCount);
    }

    public String getCategoryName() {
	return category.getDisplayName();
    }

    public String getDifficultyName() {
	return difficulty.getDisplayName();
    }

    public String getRoundString() {
    	return Integer.toString(roundCount);
        }

    public String getTotalRoundsString() {
    	return rounds.getDisplayName();
    }

    private char getLastCharOfCurrentWord(){
        return currentWord.charAt(currentWord.length()-1);
    }

    public Player getCurrentPlayer() {
	return currentPlayer;
    }

    public float getTimeLeft() {
	return timeLeft;
    }

    public int getTurnTime() {
	return turnTime;
    }

    public String getInputErrorMessage() {
	return inputErrorMessage;
    }

    public void setPlayerName(int id, String name) {
	playerNames.set(id - 1, name);
    }

    public void setPlayerNames(Iterable<String> playerNames) {
	int index = 0;
	for (String playerName : playerNames) {
	    this.playerNames.add(index, playerName);
	    index++;
	}
    }

    private void setCurrentPlayer(Player player) {
	this.currentPlayer = player;
	notifyListeners();
    }

    private void setCurrentWord(String newWord) {
	assert(!newWord.isEmpty());
        currentWord = newWord.substring(0, 1).toUpperCase() +
		      newWord.substring(1);
	enteredWords.add(newWord);
        notifyListeners();
    }

    public void setCategory(Category category){
	this.category = category;
    }

    public void setDifficulty(Difficulty difficulty){
        this.difficulty = difficulty;
	this.turnTime = difficulty.getTurnTime();
    }

    public void setRounds(Rounds roundsOption) {
	rounds = roundsOption;
	totalRounds = rounds.getRounds();
    }

    public void setWordInput(String wordInput) {
	this.wordInput = wordInput;
    }

    public void setInputErrorMessage(String inputErrorMessage) {
	this.inputErrorMessage = inputErrorMessage;
	notifyListeners();
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public boolean hasEnoughPlayers() {
	int playerCount = 0;
	for (String playerName : playerNames) {
	    if (!playerName.isEmpty()) {
		playerCount ++;
	    }
	}
	return playerCount > 1;
    }
}
