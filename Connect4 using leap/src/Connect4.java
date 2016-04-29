import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Connect4 extends JPanel implements Runnable{

    JFrame frame = new JFrame("Connect 4");
    int timer = 0;
    int[][] board;
    int player = 1;
    boolean didWin = false;
    int playerThatWon = 0;

   /* public static void main(String args[]){    
        (new Thread(new Connect4())).run();
    }*/

    public void run(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500,500));
        frame.setLocationRelativeTo(null);
        board = new int[6][7];
        for(int r = 0; r<board.length; r++){
            for(int c = 0; c<board[r].length; c++) {
                board[r][c]=0;
            }
        }
        this.setFont(new Font("serif", Font.TRUETYPE_FONT,26));

       /* this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                if(!didWin) {
                    if(e.getY()>150) {
                        if (e.getX() > 75 && e.getX() < 125) {
                            addToColumn(0, player);
                        }
                        if (e.getX() > 125 && e.getX() < 175) {
                            addToColumn(1, player);
                        }
                        if (e.getX() > 175 && e.getX() < 225) {
                            addToColumn(2, player);
                        }
                        if (e.getX() > 225 && e.getX() < 275) {
                            addToColumn(3, player);
                        }
                        if (e.getX() > 275 && e.getX() < 325) {
                            addToColumn(4, player);
                        }
                        if (e.getX() > 325 && e.getX() < 375) {
                            addToColumn(5, player);
                        }
                        if (e.getX() > 375 && e.getX() < 425) {
                            addToColumn(6, player);
                        }
                    }
                }
                Rectangle newButton = new Rectangle(100,100,300,40);
                if(newButton.contains(e.getPoint())){
                    for(int r = 0; r<board.length; r++){
                        for(int c = 0; c<board[r].length; c++) {
                            board[r][c]=0;
                        }
                    }
                    didWin = false;
                    player = 1;
                    playerThatWon = 0;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });*/

        frame.add(this);
        this.setPreferredSize(frame.getSize());
        frame.pack();
        frame.setVisible(true);
        while(true){
            if(timer%15==0){
                this.repaint();
            }
            try{Thread.sleep(1);timer++;}catch (InterruptedException e){}
        }
    }

    public void addToColumn(int column, int p){
        int count = 0;
        for(int i = 0; i<board.length; i++){
            if(isAvailable(i,column)){
                count = i;
                i = board.length;
            }
        }
        System.out.println(count);
        board[count][column] = p;
        didWin = isWin(p);
        if (player == 1) {
            player = 2;
        }
        else if(player == 2){
            player = 1;
        }
    }
    public boolean isAvailable(int r, int c){
        if(board[r][c]==0) {
            return true;
        }
        else return false;
    }

    public boolean isWin(int p){
        int count = 0;
        int winNum = 4;
        
        //horizontal 4        
        for(int i = 0;i<board.length; i++){
            for(int u = 0; u<board[i].length; u++){
                if(board[i][u] == p) count+=1;
                else count = 0;
                if (count >= winNum) {
                    playerThatWon = p;
                    return true;
                }
            }
        }
        count = 0;
        
        //vertical 4
        for(int u = 0;u<board[0].length; u++){
            for(int i = 0; i<board.length; i++){
                if(board[i][u] == p) count+=1;
                else count = 0;
                if (count >= winNum) {
                    playerThatWon = p;
                    return true;
                }
            }
        }
        count = 0;
        
        //Diagnol 4
        //For Loop Row first, then Columns
        for(int i = 0;i<board.length; i++) {
            for (int u = 0; u < board[i].length; u++) {
                if(i<board.length-(winNum-1)){
                    //CAN CHECK UP
                    if(u<board[0].length-(winNum-1)){
                        //CAN CHECK RIGHT
                        if(board[i][u]==p && board[i+1][u+1]==p && board[i+2][u+2]==p && board[i+3][u+3]==p){
                            playerThatWon = p;
                            return true;
                        }
                    }
                    if(u>winNum-1){
                        //CAN CHECK LEFT
                        if(board[i][u]==p && board[i+1][u-1]==p && board[i+2][u-2]==p && board[i+3][u-3]==p){
                            playerThatWon = p;
                            return true;
                        }
                    }
                }
                if(i>winNum-1){
                    //CAN CHECK DOWN
                    if(u<board[0].length-(winNum-1)){
                        //CAN CHECK RIGHT
                        if(board[i][u]==p && board[i-1][u+1]==p && board[i-2][u+2]==p && board[i-3][u+3]==p){
                            playerThatWon = p;
                            return true;
                        }
                    }
                    if(u>winNum-1){
                        //CAN CHECK LEFT
                        if(board[i][u]==p && board[i-1][u-1]==p && board[i-2][u-2]==p && board[i-3][u-3]==p){
                            playerThatWon = p;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        for(int r = 0; r<board.length; r++){
            for(int c = 0; c<board[r].length; c++){
                g.setColor(Color.BLACK);
                g.drawRect(75+c*50,400-r*50,50,50);
                if(board[r][c] == 1){
                    g.setColor(Color.RED);
                    g.fillOval(80 + c * 50, 405-r * 50, 40, 40);
                }
                if(board[r][c] == 2){
                    g.setColor(Color.YELLOW);
                    g.fillOval(80 + c * 50, 405-r * 50, 40, 40);
                }
            }
        }
        g.setColor(Color.BLACK);
        g.drawString("PLAYER 1", 50, 50);
        g.drawString("PLAYER 2",300,50);
        if(player==1){
            g.setColor(Color.RED);
            g.fillOval(180,30,20,20);
        }
        if(player==2){
            g.setColor(Color.YELLOW);
            g.fillOval(270,30,20,20);
        }
        if(didWin){
            if(playerThatWon==1){
                g.setColor(Color.GREEN);
                g.drawString("WIN", 70,80);
            }
            if(playerThatWon==2){
                g.setColor(Color.GREEN);
                g.drawString("WIN", 320,80);
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect(100, 100, 300, 40);
        g.drawString("NEW GAME",180,130);
    }
}
