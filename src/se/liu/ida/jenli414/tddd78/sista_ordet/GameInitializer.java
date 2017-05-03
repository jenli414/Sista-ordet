package se.liu.ida.jenli414.tddd78.sista_ordet;

/**Class used to start the game by creating an instance of MainFrame.*/
final public class GameInitializer {

    private GameInitializer() {
    }

    public static void main(String[] args) {
	/*mainFrame is only used to initialize the program,
	 *therefore it's never used anywhere else.*/
	final MainFrame mainFrame = new MainFrame();
    }
}