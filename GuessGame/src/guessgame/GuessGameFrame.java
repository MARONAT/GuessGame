package guessgame;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
public class GuessGameFrame extends JFrame {//start class 
    private static Random generator = new Random();// varables
    private int number; //my number chosen by the application
    private int guesscount;// number of guesses
    private int lastDistance; //distance between last guess and number
    private JTextField guessInputJTextField; //for guessing
    private JLabel prompt1JLabel; //first prompt to user
    private JLabel prompt2JLabel; //second prompt to user 
    private JLabel messageJLabel; //display message of game status to user
    private JButton newGameJButton; //create a new game
    private Color background; //background color of application
    public GuessGameFrame()  //constructor to set up GUI and intialize vales
    { 
        super("Guess Game");
        guesscount = 0; // intiialize number of guesses to 0
        background = Color.LIGHT_GRAY; // set background to gray
        //describe game and user prompt
        prompt1JLabel = new JLabel("I have a number between 1 and 1000");
        prompt2JLabel = new JLabel("Can guess my number?");
        guessInputJTextField = new JTextField(5); // to enter guesses
        guessInputJTextField.addActionListener(
                new ActionListener()//anonymous inner class
                {
                    @Override
                   public void actionPerformed(ActionEvent e){
                       messageJLabel.setText("please Enter Your First Guess"); //reset guess field
                       guessInputJTextField.setEditable(true); //allow guess
                       background = Color.LIGHT_GRAY; //reset background
                       theGame(); // start new game
                       repaint(); // repaint application
                   } //end method action performed
                }//end anonymous inner class
        );//end call to AddActionListener
        //set the layout
        setLayout(new FlowLayout() );
        add(prompt1JLabel); //add first prompt
        add(prompt2JLabel); //add second prompt
        add(messageJLabel); //add message jlabel
        add(guessInputJTextField); // add guessing text field
        add(newGameJButton); //add button to create new game 
        theGame(); //start new game
    } //end constructor
    // choose new random number
    public void theGame(){
        number = generator.nextInt(1000) + 1;
    }//end method
    //background color 
    @Override
    public void paint(Graphics g) {
       super.paint(g);
       getContentPane().setBackground(background);// set background 
    }// end method paint
    public void react(int guess){
        guesscount ++;
        int currentDistance = 1000; // set the initial distance
        //first guess
        if (guesscount == 1){
            lastDistance = Math.abs(guess - number);
            if (guess > number) 
                messageJLabel.setText("Too High!. Try lower number");
            else 
                messageJLabel.setText("Too Low!. Try higher number");
        }//end if
        else { 
            currentDistance = Math.abs(guess - number);
            // guess is too high
            if (guess > number){
                messageJLabel.setText("Too High!. Try lower number");
                background = (currentDistance <= lastDistance) ? Color.RED : Color.BLUE;
                lastDistance = currentDistance;
            }// end if 2
            else if (guess < number) // guess is too low
            {
                messageJLabel.setText("Too Low!. Try higher number");
                background = (currentDistance <= lastDistance) ? Color.RED : Color.BLUE;
                lastDistance = currentDistance;
            }// end else if 1
            else // guess is correct
            {
               messageJLabel.setText("Correct!"); 
               background = Color.GREEN;
               guessInputJTextField.setEditable(false);
               guesscount = 0;
            }//end else
            repaint();
        }//end else
    } //end method react
    //inner class acts on user input
      class GuessHandler implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            int guess = Integer.parseInt(guessInputJTextField.getText());
            react(guess);
        }//end method      
      }//end inner class
}//endclass
