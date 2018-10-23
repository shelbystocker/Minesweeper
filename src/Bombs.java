import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JOptionPane;

public class Bombs extends JFrame implements ActionListener, ChangeListener
{
    // Core game play objects
    private Board gameBoard;
    //private FlippableCard prevCard1 = null, prevCard2 = null;
    private JMenuBar menuBar;
    private JMenu game, setupMenu;
    private JMenu board3, board4, board5, board6, board7, board8, board9, board10, board11, board12;
    private JMenuItem exitGame, newGame, Beginner, Intermediate, Expert, help;
    private JSeparator sep;
    private JSlider slider3, slider4, slider5, slider6, slider7, slider8, slider9, slider10, slider11, slider12;

    // Labels to display game info
    private JLabel timerLabel;
    private JLabel bombsLabel;
    private JLabel winOrLose;

    // layout objects: Views of the board and the label area
    private JPanel boardView, labelView;

    // Record keeping counts and times
    private int gameTime = 0;
    private int boardSize = 7;
    private int numBombs = 20;
    public static int cardsClicked = 0, cardsClickedGoal;

    // Game timer: will be configured to trigger an event every second
    private Timer gameTimer;

    private ClassLoader loader = getClass().getClassLoader();
    private ImageIcon smile;
    private ImageIcon frown;

    public Bombs()
    {
        // Call the base class constructor
        super("Minesweeper");
        createMenu();
        cardsClickedGoal = (boardSize*boardSize)-numBombs;
        // Allocate the interface elements
        timerLabel = new JLabel("   Timer: " + gameTime);
        bombsLabel = new JLabel("Bombs " + numBombs);

        //ClassLoader loader = getClass().getClassLoader();
        // upload smile image and scale to correct size
        ImageIcon originalSmile = new ImageIcon(loader.getResource("pictures/smile.jpg"));
        Image originalSmileImage = originalSmile.getImage();
        Image newSmileImage = originalSmileImage.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
        smile = new ImageIcon(newSmileImage);
        winOrLose = new JLabel(smile);

        // timer action listener
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTime++;
                //System.out.println(gameTime);
                timerLabel.setText("Timer: " + gameTime);
            }
        });
        gameTimer.start(); // start timer

        // Allocate two major panels to hold interface
        labelView = new JPanel();  // used to hold labels
        boardView = new JPanel();  // used to hold game board

        // get the content pane, onto which everything is eventually added
        Container c = getContentPane();

        // Setup the game board with cards
        gameBoard = new Board(boardSize, numBombs, this);

        // Add the game board to the board layout area
        boardView.setLayout(new GridLayout(boardSize, boardSize, 0, 0));
        gameBoard.fillBoardView(boardView);

        // Add required interface elements to the "label" JPanel
        labelView.setLayout(new GridLayout(1, 3, 0, 0));
        labelView.add(timerLabel);
        labelView.add(winOrLose);
        labelView.add(bombsLabel);

        // Both panels should now be individually laid out
        // Add both panels to the container
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.CENTER);

        setSize(550, 550);
        setVisible(true);
    } // end of Bombs()

    // set for the changeListener
    public void stateChanged(ChangeEvent e){ }

    /* Handle anything that gets clicked and that uses MemoryGame as an
     * ActionListener */
    public void actionPerformed(ActionEvent e)
    {
        // Get the currently clicked card from a click event
        FlippableCard currentCard = (FlippableCard)e.getSource();
        currentCard.showFront();
        if (currentCard.customName() == "bomb") { // if the user clicked a bomb
            ImageIcon originalFrown = new ImageIcon(loader.getResource("pictures/frown.jpg"));
            Image originalFrownImage = originalFrown.getImage();
            Image newFrownImage = originalFrownImage.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
            frown = new ImageIcon(newFrownImage);
            winOrLose.setIcon(frown);
            gameTimer.stop();
            gameBoard.showCards();
            gameBoard.removeActionListeners(this);
        }
        if (currentCard.customName() == "lightGray"){ // if the user clicked an empty cell
            gameBoard.spreadGray(currentCard, this);
            currentCard.removeActionListener(this);
        }
        else {
            cardsClicked++;
            if (cardsClicked == cardsClickedGoal) {
                JFrame f = new JFrame();
                gameBoard.removeActionListeners(this);
                JOptionPane.showMessageDialog(f, "Congratulations! You won Minesweeper!");
                gameTimer.stop();
            }
        }
    }

    // set game to original settings
    private void restartGame()
    {
        gameTime = 0;
        cardsClicked=0;
        cardsClickedGoal = (boardSize*boardSize)-numBombs;
        gameTimer.start();
        timerLabel.setText("Timer: 0");
        bombsLabel.setText("Bombs: " + Integer.toString(numBombs));
        winOrLose.setIcon(smile);
        gameBoard = new Board(boardSize, numBombs, this); // generate a new board
        // Clear the boardView and have the gameBoard generate a new layout
        boardView.removeAll();
        boardView.setLayout(new GridLayout(boardSize, boardSize, 0, 0));
        gameBoard.fillBoardView(boardView);
    } // end of restartGame()

    // set menu at top
    public void createMenu(){
        // set up top menu
        menuBar = new JMenuBar();
        game = new JMenu("Game");

        exitGame = new JMenuItem("Exit"); // exit option under Game
        exitGame.setToolTipText("Exit application");
        exitGame.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        sep = new JSeparator(); // seperate exit from other options

        newGame = new JMenuItem("New"); // new game under Game
        newGame.setToolTipText("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        setupMenu = new JMenu("Setup"); // setup under Game
        setupMenu.setToolTipText("Game Specs");
        setupMenu.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        Beginner = new JMenuItem("Beginner");
        Beginner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numBombs = 4;
                boardSize = 4;
                restartGame();
            }
        });

        Intermediate = new JMenuItem("Intermediate");
        Intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numBombs = 15;
                boardSize = 8;
                restartGame();
            }
        });

        Expert = new JMenuItem("Expert");
        Expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numBombs = 40;
                boardSize = 12;
                restartGame();
            }
        });

        setupMenu.add(Beginner);
        setupMenu.add(Intermediate);
        setupMenu.add(Expert);

        board3 = new JMenu("3 x 3 Board"); // Create menu options for board size
        board4 = new JMenu("4 x 4 Board");
        board5 = new JMenu("5 x 5 Board");
        board6 = new JMenu("6 x 6 Board");
        board7 = new JMenu("7 x 7 Board");
        board8 = new JMenu("8 x 8 Board");
        board9 = new JMenu("9 x 9 Board");
        board10 = new JMenu("10 x 10 Board");
        board11 = new JMenu("11 x 11 Board");
        board12 = new JMenu("12 x 12 Board");

        setupMenu.add(board3); // add board size options to set up menu
        setupMenu.add(board4);
        setupMenu.add(board5);
        setupMenu.add(board6);
        setupMenu.add(board7);
        setupMenu.add(board8);
        setupMenu.add(board9);
        setupMenu.add(board10);
        setupMenu.add(board11);
        setupMenu.add(board12);

        // create sliders to choose number of bombs for each board size
        slider3 = new JSlider(JSlider.HORIZONTAL, 2, 4, 3);
        board3.add(slider3);
        slider3.setMinorTickSpacing(1);
        slider3.setMajorTickSpacing(2);
        slider3.setPaintTicks(true);
        slider3.setPaintLabels(true);
        slider3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider3.getValueIsAdjusting()) {
                    numBombs = slider3.getValue();
                    boardSize = 3;
                    restartGame();
                }
            }
        });

        slider4 = new JSlider(JSlider.HORIZONTAL, 2, 8, 5);
        board4.add(slider4);
        slider4.setMinorTickSpacing(2);
        slider4.setMajorTickSpacing(3);
        slider4.setPaintTicks(true);
        slider4.setPaintLabels(true);
        slider4.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider4.getValueIsAdjusting()) {
                    numBombs = slider4.getValue();
                    boardSize = 4;
                    restartGame();
                }

            }
        });

        slider5 = new JSlider(JSlider.HORIZONTAL, 2, 12, 7);
        board5.add(slider5);
        slider5.setMinorTickSpacing(2);
        slider5.setMajorTickSpacing(5);
        slider5.setPaintTicks(true);
        slider5.setPaintLabels(true);
        slider5.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider5.getValueIsAdjusting()) {
                    numBombs = slider5.getValue();
                    boardSize = 5;
                    restartGame();
                }
            }
        });

        slider6 = new JSlider(JSlider.HORIZONTAL, 2, 18, 10);
        board6.add(slider6);
        slider6.setMinorTickSpacing(2);
        slider6.setMajorTickSpacing(4);
        slider6.setPaintTicks(true);
        slider6.setPaintLabels(true);
        slider6.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider6.getValueIsAdjusting()) {
                    numBombs = slider6.getValue();
                    boardSize = 6;
                    restartGame();
                }
            }
        });

        slider7 = new JSlider(JSlider.HORIZONTAL, 2, 24, 13);
        board7.add(slider7);
        slider7.setMinorTickSpacing(2);
        slider7.setMajorTickSpacing(5);
        slider7.setPaintTicks(true);
        slider7.setPaintLabels(true);
        slider7.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider7.getValueIsAdjusting()) {
                    numBombs = slider7.getValue();
                    boardSize = 7;
                    restartGame();
                }
            }
        });

        slider8 = new JSlider(JSlider.HORIZONTAL, 2, 32, 16);
        board8.add(slider8);
        slider8.setMinorTickSpacing(4);
        slider8.setMajorTickSpacing(8);
        slider8.setPaintTicks(true);
        slider8.setPaintLabels(true);
        slider8.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider8.getValueIsAdjusting()) {
                    numBombs = slider8.getValue();
                    boardSize = 8;
                    restartGame();
                }
            }
        });

        slider9 = new JSlider(JSlider.HORIZONTAL, 2, 40, 21);
        board9.add(slider9);
        slider9.setMinorTickSpacing(5);
        slider9.setMajorTickSpacing(10);
        slider9.setPaintTicks(true);
        slider9.setPaintLabels(true);
        slider9.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider9.getValueIsAdjusting()) {
                    numBombs = slider9.getValue();
                    boardSize = 9;
                    restartGame();
                }
            }
        });

        slider10 = new JSlider(JSlider.HORIZONTAL, 2, 50, 26);
        board10.add(slider10);
        slider10.setMinorTickSpacing(6);
        slider10.setMajorTickSpacing(12);
        slider10.setPaintTicks(true);
        slider10.setPaintLabels(true);
        slider10.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider10.getValueIsAdjusting()) {
                    numBombs = slider10.getValue();
                    boardSize = 10;
                    restartGame();
                }
            }
        });

        slider11 = new JSlider(JSlider.HORIZONTAL, 2, 60, 31);
        board11.add(slider11);
        slider11.setMinorTickSpacing(6);
        slider11.setMajorTickSpacing(12);
        slider11.setPaintTicks(true);
        slider11.setPaintLabels(true);
        slider11.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider11.getValueIsAdjusting()) {
                    numBombs = slider11.getValue();
                    boardSize = 11;
                    restartGame();
                }
            }
        });

        slider12 = new JSlider(JSlider.HORIZONTAL, 2, 72, 37);
        board12.add(slider12);
        slider12.setMinorTickSpacing(7);
        slider12.setMajorTickSpacing(10);
        slider12.setPaintTicks(true);
        slider12.setPaintLabels(true);
        slider12.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider12.getValueIsAdjusting()) {
                    numBombs = slider12.getValue();
                    boardSize = 12;
                    restartGame();
                }
            }
        });

        game.add(newGame); // add menu's to game
        game.add(setupMenu);
        game.add(sep);
        game.add(exitGame);

        help = new JMenuItem("Help"); // need to make this do something ****
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f, "How to play: Click on a square and try to avoid bombs. A number represents how many bombs su" +
                        "rround that square. Blank squares do not border any bombs. \n You can resize the board by clicking Game, Setup and then " +
                        "the board size and number of bombs. \n If you adjust the board size, resize the entire game so that the squares are all visible. ");
            }
        });

        menuBar.add(game); // add game and help to menu bar
        menuBar.add(help);
        setJMenuBar(menuBar);
    }

    public static void main(String args[])
    {
        Bombs M = new Bombs();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}