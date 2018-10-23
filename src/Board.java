import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ImageIcon;

public class Board
{
    // Array to hold board cards
    private FlippableCard cards[];
    private ActionListener memoryGameAL;
    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();
    int count = 0, boardSize1, numCards;

    public Board(int boardSize, int numBombs, ActionListener AL) {
        cards = null;
        boardSize1 = boardSize;
        numCards = boardSize * boardSize;
        // Allocate and configure the game board: an array of cards
        cards = new FlippableCard[numCards];
        memoryGameAL = AL;

        // Fill the Cards array with bombs in random locations
        for (int i = 0; i < numBombs; i++) {
            // Load the front image from the resources folder
            String imgPath = "pictures/bomb.jpg";
            ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
            Image originalImage = originalImageIcon.getImage();
            Image newImage = originalImage.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
            ImageIcon newImageIcon = new ImageIcon(newImage);

            FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
            //c.setID(imageIdx); // set card ID to its file number
            c.addActionListener(AL); // add action listener for each card
            c.hideFront(); // start them off with their back showing

            int spotToFill = (int) (Math.random() * (numCards));
            while (cards[spotToFill] != null) {
                spotToFill = (int) (Math.random() * (numCards));
            }
            cards[spotToFill] = c; // add them to the array
            c.setCustomName("bomb");
        }

        // fill in cards array remaining spots with appropriate numbers
        for (int i = 0; i < numCards; i += boardSize) {
            for (int j = 0; j < boardSize; j++) {
                int currentCard = j + i;
                if (cards[currentCard] == null) {
                    //System.out.println("i " + i + "j " + j);
                    if (j == 0 && i == 0) { // if in top left corner
                        if (cards[j + 1] != null) count++;
                        if (cards[j + 1 + boardSize] != null) count++;
                        if (cards[boardSize] != null) count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        count = 0;
                    }
                    else if (i == 0 && j == boardSize - 1) { // if in top right corner
                        if (cards[j - 1].customName() != null) count++;
                        if (cards[j - 1 + boardSize] != null) count++;
                        if (cards[j + boardSize] != null) count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        count = 0;
                    }
                    else if (i == 0) { // if top middle

                        if (cards[j - 1].customName() == "bomb") count++;
                        if (cards[j - 1 + boardSize] != null) count++;
                        if (cards[j + boardSize] != null) count++;
                        if (cards[j + 1 + boardSize] != null) count++;
                        if (cards[j + 1] != null) count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        else if (count == 4) setFour(currentCard, AL);
                        else if (count == 5) setFive(currentCard, AL);
                        count = 0;
                    }
                    else if (i == numCards - boardSize && j == 0) { // if in bottom left corner
                        if (cards[i-boardSize].customName()=="bomb") count++;
                        if (cards[i-boardSize+1].customName()=="bomb") count++;
                        if (cards[i+1]!=null) count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        count = 0;
                    }
                    else if (i == numCards - boardSize && j == boardSize - 1) { // if in bottom right corner
                        if (cards[i+j-boardSize].customName()=="bomb") count++;
                        if (cards[i+j-boardSize-1].customName()=="bomb") count++;
                        if (cards[i+j-1].customName()=="bomb") count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        count = 0;
                    }
                    else if (i == numCards - boardSize) { // if bottom row middle
                        if (cards[i+j-1].customName()=="bomb") count++;
                        if (cards[i-boardSize+j-1].customName()=="bomb") count++;
                        if (cards[i+j-boardSize].customName()=="bomb") count++;
                        if (cards[i+j-boardSize+1].customName()=="bomb") count++;
                        if (cards[i+j+1]!=null) count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        else if (count == 4) setFour(currentCard, AL);
                        else if (count == 5) setFive(currentCard, AL);
                        count = 0;
                    }
                    else if (j==0) { // if on left middle
                        if (cards[j+i-boardSize].customName()=="bomb") count++;
                        if (cards[j+i-boardSize+1].customName()=="bomb") count++;
                        if (cards[j+i+1]!=null) count++;
                        if (cards[j+i+boardSize+1]!=null) count++;
                        if (cards[j+i+boardSize]!=null) count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        else if (count == 4) setFour(currentCard, AL);
                        else if (count == 5) setFive(currentCard, AL);
                        count = 0;
                    }
                    else if (j==boardSize-1) { // if on right middle
                        if (cards[j+i-boardSize].customName()=="bomb") count++;
                        if (cards[j+i-boardSize-1].customName()=="bomb") count++;
                        if (cards[j+i-1].customName()=="bomb") count++;
                        if (cards[j+i+boardSize-1]!=null) count++;
                        if (cards[j+i+boardSize]!=null) count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        else if (count == 4) setFour(currentCard, AL);
                        else if (count == 5) setFive(currentCard, AL);
                        count = 0;
                    }
                    else { // if in middle of board (not on border)
                        if (cards[j+i-boardSize].customName()=="bomb") count++;
                        if (cards[j+i-boardSize-1].customName()=="bomb") count++;
                        if (cards[j+i-1].customName()=="bomb") count++;
                        if (cards[j+i+boardSize-1]!=null) count++;
                        if (cards[j+i+boardSize]!=null) count++;
                        if (cards[i+j-boardSize+1].customName()=="bomb") count++;
                        if (cards[i+j+1]!=null) count++;
                        if (cards[i+j+boardSize+1]!=null) count++;

                        if (count == 0) setLightGray(currentCard, i,j, AL);
                        else if (count == 1) setOne(currentCard, AL);
                        else if (count == 2) setTwo(currentCard, AL);
                        else if (count == 3) setThree(currentCard, AL);
                        else if (count == 4) setFour(currentCard, AL);
                        else if (count == 5) setFive(currentCard, AL);
                        else if (count == 6) setSix(currentCard, AL);
                        else if (count == 7) setSeven(currentCard, AL);
                        else if (count == 8) setEight(currentCard, AL);
                        count = 0;

                    }
                }
            }
        }
    }

    public void spreadGray(FlippableCard currentCard, ActionListener AL) {
        Bombs.cardsClicked++;
        System.out.println("Cards Clicked " + Bombs.cardsClicked);
        int index = currentCard.id();
        currentCard.showFront();
        int j = index % boardSize1;
        int i = index - j;
        if (i == 0 && j == 0) { // top left corner
            if (cards[j+1] != null && cards[j+1].customName()=="lightGray") cards[j+1].showFront();
            if (cards[j+1+boardSize1] != null && cards[j+1+boardSize1].customName()=="lightGray") cards[j+1+boardSize1].showFront();
            if (cards[boardSize1] != null && cards[boardSize1].customName()=="lightGray") cards[boardSize1].showFront();
        }
        if (i == 0 && j == boardSize1 - 1) { // if in top right corner
            if (cards[j-1].customName() != null && cards[j-1].customName()=="lightGray") cards[j-1].showFront();
            if (cards[j-1+boardSize1] != null && cards[j-1+boardSize1].customName()=="lightGray") cards[j-1+boardSize1].showFront();
            if (cards[j+boardSize1] != null && cards[j+boardSize1].customName()=="lightGray") cards[j+boardSize1].showFront();
        }

        if (i == 0 && j!=0 && j!=boardSize1-1) { // if top middle
            if (cards[j-1].customName()!=null && cards[j-1].customName()=="lightGray") cards[j-1].showFront();
            if (cards[j-1+boardSize1] != null && cards[j-1+boardSize1].customName()=="lightGray") cards[j-1+boardSize1].showFront();
            if (cards[j+boardSize1] != null && cards[j+boardSize1].customName()=="lightGray") cards[j+boardSize1].showFront();
            if (cards[j+1+boardSize1] != null && cards[j+1+boardSize1].customName()=="lightGray") cards[j+1+boardSize1].showFront();
            if (cards[j+1] != null && cards[j+1].customName()=="lightGray") cards[j+1].showFront();
        }

        if (i == numCards - boardSize1 && j == 0) { // if in bottom left corner
            if (cards[i-boardSize1].customName()!=null && cards[i-boardSize1].customName()=="lightGray") cards[i-boardSize1].showFront();
            if (cards[i-boardSize1+1].customName()!=null && cards[i-boardSize1+1].customName()=="lightGray") cards[i-boardSize1+1].showFront();
            if (cards[i+1] != null && cards[i+1].customName()=="lightGray") cards[i+1].showFront();
        }

        if (i == numCards - boardSize1 && j == boardSize1 - 1) { // if in bottom right corner
            if (cards[i+j-boardSize1]!=null && cards[i+j-boardSize1].customName()=="lightGray") cards[i+j-boardSize1].showFront();
            if (cards[i+j-boardSize1-1]!=null && cards[i+j-boardSize1-1].customName()=="lightGray") cards[i+j-boardSize1-1].showFront();
            if (cards[i+j-1]!=null && cards[i+j-1].customName()=="lightGray") cards[i+j-1].showFront();
        }
        if (i == numCards - boardSize1 && j!=0 && j!=boardSize1-1) { // if bottom row middle
            if (cards[i+j-1]!=null && cards[i+j-1].customName()=="lightGray") cards[i+j-1].showFront();
            if (cards[i-boardSize1+j-1]!=null && cards[i-boardSize1+j-1].customName()=="lightGray") cards[i-boardSize1+j-1].showFront();
            if (cards[i+j-boardSize1]!=null && cards[i+j-boardSize1].customName()=="lightGray") cards[i+j-boardSize1].showFront();
            if (cards[i+j-boardSize1+1]!=null && cards[i+j-boardSize1+1].customName()=="lightGray") cards[i+j-boardSize1+1].showFront();
            if (cards[i+j+1]!=null && cards[i+j+1].customName()=="lightGray") cards[i+j+1].showFront();
        }
        if (j==0 && i!=0 && i!=numCards-boardSize1) { // if on left middle
            if (cards[j+i-boardSize1]!=null && cards[j+i-boardSize1].customName()=="lightGray") cards[j+i-boardSize1].showFront();
            if (cards[j+i-boardSize1+1]!=null && cards[j+i-boardSize1+1].customName()=="lightGray") cards[j+i-boardSize1+1].showFront();
            if (cards[j+i+1] != null && cards[j+i+1].customName()=="lightGray") cards[j+i+1].showFront();
            if (cards[j+i+boardSize1+1] != null && cards[j+i+boardSize1+1].customName()=="lightGray") cards[j+i+boardSize1+1].showFront();
            if (cards[j+i+boardSize1] != null && cards[j+i+boardSize1].customName()=="lightGray") cards[j+i+boardSize1].showFront();
        }
        if (j==boardSize1-1 && i!=0 && i!=numCards-boardSize1) { // if on right middle
            if (cards[j+i-boardSize1]!=null && cards[j+i-boardSize1].customName()=="lightGray") cards[j+i-boardSize1].showFront();
            if (cards[j+i-boardSize1 - 1]!=null && cards[j+i-boardSize1-1].customName()=="lightGray") cards[j+i-boardSize1-1].showFront();
            if (cards[j+i-1]!=null && cards[j+i-1].customName()=="lightGray") cards[j+i-1].showFront();
            if (cards[j+i+boardSize1 - 1] != null && cards[j+i+boardSize1-1].customName()=="lightGray") cards[j+i+boardSize1-1].showFront();
            if (cards[j+i+boardSize1] != null && cards[j+i+boardSize1].customName()=="lightGray") cards[j+i+boardSize1].showFront();
        }
        if (i!=0 && i!=numCards-boardSize1 && j!=0 && j!=boardSize1-1){ // if in middle of board (not on border)
            if (cards[j+i-boardSize1]!=null && cards[j+i-boardSize1].customName()=="lightGray") cards[j+i-boardSize1].showFront();
            if (cards[j+i-boardSize1-1]!=null && cards[j+i-boardSize1-1].customName()=="lightGray") cards[j+i-boardSize1-1].showFront();
            if (cards[j+i-1]!=null && cards[j+i-1].customName()=="lightGray") cards[j+i-1].showFront();
            if (cards[j+i+boardSize1-1]!=null && cards[j+i+boardSize1-1].customName()=="lightGray") cards[j+i+boardSize1-1].showFront();
            if (cards[j+i+boardSize1]!=null && cards[j+i+boardSize1].customName()=="lightGray") cards[j+i+boardSize1].showFront();
            if (cards[j+i-boardSize1+1]!=null && cards[i+j-boardSize1+1].customName()=="lightGray") cards[i+j-boardSize1+1].showFront();
            if (cards[j+i+1]!=null && cards[i+j+1].customName()=="lightGray") cards[i+j+1].showFront();
            if (cards[j+i+boardSize1+1]!=null && cards[i+j+boardSize1+1].customName()=="lightGray") cards[i+j+boardSize1+1].showFront();
        }
    }

    // function used to show fronts of cards
    public void showCards(){
        for (FlippableCard c : cards) {
            c.showFront();
        }
    }

    // function used to set cells to light grey if they aren't next to a bomb
    public void setLightGray(int currentCard, int i, int j, ActionListener AL) {
        String imgPath = "pictures/lightGray.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(60,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
        c.setCustomName("lightGray");
        c.setID(i+j);
    }

    // function used to set image of cards that touch one bomb
    public void setOne(int currentCard, ActionListener AL) {
        String imgPath = "pictures/one.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
    }

    // function used to set image of cards that touch two bombs
    public void setTwo(int currentCard, ActionListener AL) {
        String imgPath = "pictures/two.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
    }

    // function used to set image of cards that touch three bombs
    public void setThree(int currentCard, ActionListener AL) {
        String imgPath = "pictures/three.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
    }

    // function used to set image of cards that touch four bombs
    public void setFour(int currentCard, ActionListener AL) {
        String imgPath = "pictures/four.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
    }

    // function used to set image of cards that touch five bombs
    public void setFive(int currentCard, ActionListener AL) {
        String imgPath = "pictures/five.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
    }

    // function used to set image of cards that touch six bombs
    public void setSix(int currentCard, ActionListener AL) {
        String imgPath = "pictures/six.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
    }

    // function used to set image of cards that touch seven bombs
    public void setSeven(int currentCard, ActionListener AL) {
        String imgPath = "pictures/seven.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
    }

    // function used to set image of cards that touch eight bombs
    public void setEight(int currentCard, ActionListener AL) {
        String imgPath = "pictures/eight.jpg";
        ImageIcon originalImageIcon = new ImageIcon(loader.getResource(imgPath));
        Image originalImage = originalImageIcon.getImage();
        Image newImage = originalImage.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        FlippableCard c = new FlippableCard(newImageIcon); // Setup one card at a time
        c.addActionListener(AL); // add action listener for each card
        c.hideFront(); // start them off with their back showing
        cards[currentCard] = c;
    }

    // function to remove action listeners from cards
    public void removeActionListeners(ActionListener AL){
        for (FlippableCard c : cards) {
            c.removeActionListener(AL);
        }
    }

    public void fillBoardView(JPanel view)
    {
        for (FlippableCard c : cards) {
            view.add(c);
        }
    }
}