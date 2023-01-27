import javax.swing.*;

public class Main extends JFrame {
    private JLabel message = new JLabel();
    private int Width = 100, Height = 100, labelHeight = 50;
    private Game board = new Game();
    private JButton[][] buttons = new JButton[3][3];
    private JButton reset, AI, exit;
    private boolean playingWithAI = false;
    private void reset() {
        // reset button text
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setText(" ");
            }
        }
        board.reset(); // reset game
        message.setText(board.getMessage(0));
    }
    // constructor
    public Main() {
        setTitle("Tic-Tac-Toe");
        setSize(3*Width, 3*Height + 3*labelHeight);
        setLayout(null);


        /* ====== label ===== */
        message.setBounds(0, 0, 3*Width, labelHeight);
        add(message);
        message.setText( board.getMessage(0) );
        message.setHorizontalAlignment(SwingConstants.CENTER);
        /* ================== */

        /* ================ BUTTONS ========================== */
        // making all the buttons of GRID
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                addButton(i, j);
            }
        }

        // reset button
        reset = new JButton("reset");
        reset.addActionListener(e->{
            reset();
        });
        reset.setFocusable(false);

        // AI button
        AI = new JButton("AI  ");
        AI.addActionListener(e-> {
            playingWithAI = !playingWithAI;
            if(playingWithAI) AI.setText("AI ✓");
            else AI.setText("AI");
        });
        AI.setFocusable(false);


        // exit button
        exit = new JButton("Close");
        exit.addActionListener(e -> {
            System.exit(0);
        });
        exit.setFocusable(false);

        // positioning buttons
        reset.setBounds (0      , labelHeight + 3*Height, Width, labelHeight);
        AI.setBounds    (Width     , labelHeight + 3*Height, Width, labelHeight);
        exit .setBounds (2*Width, labelHeight + 3*Height, Width, labelHeight);

        /* ====================================== */

        add(reset);
        add(AI);
        add(exit);

        setVisible(true);
    }

    private void addButton(int i, int j) {
        buttons[i][j] = new JButton(" ");
        buttons[i][j].setFocusable(false);
        // button onclick event
        buttons[i][j].addActionListener(e->{
            if ( board.isEmpty(i,j) && board.isEditable) {  // check if u can edit
                buttons[i][j].setText( board.getPlayer() );
                board.playMove(i, j);

                int winner = board.winDet();
                message.setText(board.getMessage(winner));

                // AI's turn
                if (playingWithAI && board.playAsAI()) {

                    buttons[board.bestMove[0]][board.bestMove[1]].setText(board.getPlayer());
                    board.playMove(board.bestMove[0], board.bestMove[1]);

                    winner = board.winDet();
                    message.setText(board.getMessage(winner));

                }
            }
        });
        // set button position
        buttons[i][j].setBounds(j*Width, labelHeight + i *Height, Width, Height);
        add(buttons[i][j]); // add button to frame
    }

    public static void main(String[] args) {
        // make a object to call the constructor
        new Main();
    }
}
