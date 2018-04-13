
/**
 * Write a description of class Test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.io.*;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Test extends JPanel
{
    private ArrayList<State> states;
    private int currentIndex;
    private final int NUMBER_OF_STATES = 50;
    private String playerName;
    private String mode; //g if geo mode, c if cap mode
    private int statesCorrect;
    private int currentPoints;
    private int strikes;
    private boolean hintEnabled;
    private boolean endGame;
    
    public Test()
    {
        initializeStatesAndCapitals();
        playerName = "";
        mode = "";
        currentIndex = 0;
        statesCorrect = 0;
        strikes = 0;
        hintEnabled = false;
        endGame = false;
    }
    
    public void initializeStatesAndCapitals()
    {
        states = new ArrayList<State>();
        ArrayList<String> stateNames = new ArrayList<String>();
        ArrayList<String> capitalNames = new ArrayList<String>();
        try
        {
            Scanner s = new Scanner(new File("stateNames.txt"));
            while (s.hasNextLine()){
                stateNames.add(s.nextLine());
            }
            s.close();
            
            Scanner c = new Scanner(new File("capitalNames.txt"));
            while (c.hasNextLine()){
                capitalNames.add(c.nextLine());
            }
            c.close();
        }
        catch(Exception e)
        {
            
        }
        for (int i=0; i< NUMBER_OF_STATES; i++)
        {
            try
            {
                
                String tempStateName = stateNames.get(i).toUpperCase();
                //System.out.println(tempStateName + ".");
                BufferedImage sImage = ImageIO.read(new File(tempStateName + ".png"));
                //System.out.println("Found image of " + (stateNames.get(i)));
                states.add(new State(stateNames.get(i), capitalNames.get(i), sImage));
                //System.out.println("Successfully loaded image " + i);
            }
            catch(Exception e)
            {
                //System.out.println("Could not load image");
            }
            
        }
    }
    
    public void setPlayerName(String n)
    {
        playerName = n;
    }
    
    public void setMode(String m)
    {
        mode = m;
    }
    
    public String getPlayerName()
    {
        return playerName;
    }
    
    public String getMode()
    {
        return mode;
    }
    
    public int getStatesCorrect()
    {
        return statesCorrect;
    }
    
    public int getStrikes()
    {
        return strikes;
    }
    
    public void resetStrikes()
    {
        strikes = 0;
    }
    
    public int getCurrentIndex()
    {
        return currentIndex;
    }
    
    public BufferedImage getCurrentStateImage()
    {
        return states.get(currentIndex).getStateImage();
    }
    
    public int getScaledImageWidth(double percentageChange)
    {
        BufferedImage temp = states.get(currentIndex).getStateImage();
        return (int)(temp.getWidth()*percentageChange);
    }
    
    public int getScaledImageHeight(double percentageChange)
    {
        BufferedImage temp = states.get(currentIndex).getStateImage();
        return (int)(temp.getHeight()*percentageChange);
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        BufferedImage s = states.get(currentIndex).getStateImage();
        g2.drawImage(s, 0, 0, null);
    }
    
    public void drawCurrentState()
    {
        repaint();
    }
    
    /*
    public void printStatesAndCapitals()
    {
        for (int i =0; i<NUMBER_OF_STATES; i++)
        {
            System.out.println("State: " + states.get(i).getStateName() + " Capital: " + states.get(i).getCapitalName());
        }
    }
    */
    
    public void randomizeStates()
    {
        Collections.shuffle(states);
    }
    
    public String getQuestion()
    {
        if (mode.equals("g"))
        {
            return "What is this state?";
        }
        else if (mode.equals("c") && hintEnabled==false)
        {
            return "What is the capital of this state?";
        }
        else
        {
            return "What is the capital of " + states.get(currentIndex).getStateName() + "?";
        }
    }
    
    public void setHintEnabled(boolean b)
    {
        hintEnabled = b;
    }
    
    public void testCheck(String answer, String mode)
    {
        if (endGame == false)
        {
            boolean endRound = false;
            if (endRound == false && strikes<3)
            {
                State current = states.get(currentIndex);
                if (mode.equals("g"))
                {
                    if (answer.equalsIgnoreCase(current.getStateName()))
                    {
                        statesCorrect++;
                        endRound = true;
                        currentIndex++;
                        resetStrikes();
                    }
                    else
                    {
                        strikes++;
                    }
                }
                else if (mode.equals("c"))
                {
                    if (answer.equalsIgnoreCase(current.getCapitalName()))
                    {
                        statesCorrect++;
                        endRound = true;
                        currentIndex++;
                        resetStrikes();
                    }
                    else
                    {
                        strikes++;
                    }
                }
                if (strikes == 3)
                {
                    endGame = true;
                }
            }
        }
    }
    
    
    public static void main(String[] args)
    {
        Test scTest = new Test();
        scTest.randomizeStates();
        
        
        JFrame startFrame = new JFrame("States and Capitals Test Start Screen");
        startFrame.setLayout(new FlowLayout());
        startFrame.setSize(800, 150);
        //startFrame.pack();
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       
        
        
        
        JLabel enterName = new JLabel("Enter name:" );
        JTextField inputName = new JTextField(2);
        JPanel nameSection = new JPanel();
        nameSection.setLayout(new GridLayout(1,2));
        nameSection.add(enterName);
        nameSection.add(inputName);
        
        JPanel modeSection = new JPanel();
        JRadioButton geoTextBox = new JRadioButton("Geography Test", false);
        JRadioButton capTextBox = new JRadioButton("Name the Capitals Test",false);
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(geoTextBox);
        modeGroup.add(capTextBox);
        modeSection.setLayout(new GridLayout(2,1));
        modeSection.add(geoTextBox);
        modeSection.add(capTextBox);
        
        JButton beginTest = new JButton("Begin test");
        
        startFrame.add(nameSection);
        startFrame.add(modeSection);
        startFrame.add(beginTest);
        
        startFrame.setVisible(true);
        
        JFrame testFrame = new JFrame("Test");
        testFrame.setSize(1200, 600);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setVisible(false);
        

        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(1000,600));
        int imageWidth = scTest.getScaledImageWidth(0.4);
        int imageHeight = scTest.getScaledImageHeight(0.4);
        JLabel statePic = new JLabel(new ImageIcon(scTest.getCurrentStateImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT)));
        left.add(statePic);
        
        
        JPanel right = new JPanel();
        right.setLayout(new GridLayout(2,1));
        right.setPreferredSize(new Dimension(250, 600));
        
        
        //panel for text area - includes states right, strikes
        JPanel information = new JPanel();
        JLabel instructions = new JLabel("");
        instructions.setHorizontalAlignment(JLabel.CENTER);
        JLabel correctStates = new JLabel("Correct states: " + scTest.getStatesCorrect() + " Strikes: " + scTest.getStrikes());
        correctStates.setHorizontalAlignment(JLabel.CENTER);
        information.setPreferredSize(new Dimension(250, 300));
        information.setLayout(new GridLayout(2,1));
        //GridBagConstraints gbc = new GridBagConstraints();

        information.add(correctStates);
        information.add(instructions);

        
        //panel for input area - includes question, textfield, enter
        JPanel inputArea = new JPanel();
        inputArea.setLayout(new GridLayout(3,1));
        JLabel question = new JLabel(scTest.getQuestion());
        question.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField userAnswer = new JTextField();
        JButton enter = new JButton("Enter");
        JButton hintButton = new JButton("Hint Button");
        hintButton.setVisible(false);
        inputArea.add(question);
        inputArea.add(userAnswer);
        inputArea.add(enter);
        inputArea.add(hintButton);
        
        right.add(information);
        right.add(inputArea);
        
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(1,1));
        main.add(left);
        main.add(right);
        
        testFrame.add(main);
        
        beginTest.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String input = inputName.getText();
                scTest.setPlayerName(input);
                if (geoTextBox.isSelected())
                {
                    scTest.setMode("g");
                }
                else if (capTextBox.isSelected())
                {
                    scTest.setMode("c");
                }
                if(scTest.getMode().equals(""))
                {
                    JOptionPane.showMessageDialog(startFrame, "Please click a mode.");
                }
                if (scTest.getPlayerName().equals(""))
                {
                    JOptionPane.showMessageDialog(startFrame, "Please enter a name.");
                }
                if (!(scTest.getMode().equals("")) && !(scTest.getPlayerName().equals("")))
                {
                    startFrame.setVisible(false);
                    question.setText(scTest.getQuestion());
                    if (scTest.getMode().equals("g"))
                        instructions.setText("You will get 3 tries for every state.");
                    else
                        instructions.setText("You will get 3 tries for every state. " + "After two failed tries, you will be able to use a hint.");
                    testFrame.setVisible(true);
                    //correctStates.setText("Correct states: " + scTest.getStatesCorrect() +" Strikes: " + scTest.getStrikes() + " Mode: " + scTest.getMode());
                }
            }
        });
       
         hintButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            scTest.setHintEnabled(true);
            question.setText(scTest.getQuestion());
        }
        });
        
        enter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String input = userAnswer.getText();
                int previousStrikes = scTest.getStrikes();

                if(input.equals(""))
                {
                    JOptionPane.showMessageDialog(testFrame, "You didn't enter anything.");
                }
                else
                {
                    if (scTest.getMode().equals("g"))
                    {
                        scTest.testCheck(input, "g");
                    }
                    else if (scTest.getMode().equals("c"))
                    {
                        scTest.testCheck(input, "c");
                    }
                    if (previousStrikes<scTest.getStrikes())
                    {
                        JOptionPane.showMessageDialog(testFrame, "Wrong! You just got a strike!");
                    }
                    else
                    {
                        scTest.setHintEnabled(false);
                        hintButton.setVisible(false);
                    }
                    correctStates.setText("Correct states: " + scTest.getStatesCorrect() + " | " + "Strikes: " + scTest.getStrikes());
                    question.setText(scTest.getQuestion());
                    statePic.setIcon(new ImageIcon(scTest.getCurrentStateImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT)));
                    if (scTest.getStrikes()==3)
                    {
                        JOptionPane.showMessageDialog(testFrame, "You have failed the quiz.");
                        testFrame.dispose();
                    }
                    if (scTest.getMode().equals("c") && scTest.getStrikes()==2)
                    {
                        hintButton.setVisible(true);
                    }
                    if (scTest.getCurrentIndex()==50)
                    {
                        JOptionPane.showMessageDialog(testFrame, "You passed!");
                        testFrame.dispose();
                    }
                }
                userAnswer.setText("");
            }
        });
        
        
        

    }
}
