package structure;

import players.Muggle;
import players.Player;
import players.Wizard;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Game {

    private static final Scanner input = new Scanner(System.in);
    private State current_state;
    private State initial_state;
    private Player player;

    public Game(){
        readFromConsole();
        choosePlayer();
    }

    public void readFromConsole(){

        //read the size
        int width = 0; int length = 0;
        do{
            length = input.nextInt();
            width = input.nextInt();
        }
        while(length<=0 || width <=0);

        //read the console

        String [][] board = new String[width][length];
        boolean flag = true;
        input.nextLine();

        while(flag){
            for(int i = 0 ; i<width;i++){
                String row = input.nextLine();
                String [] cells = row.split(" ");
                if(cells.length!=length){
                    System.out.println("each row must be of size: "+width);
                    flag = false;
                    break;
                }
                boolean validChar = true;
                for(int j = 0;j<length;j++){
                    if(cells[j].length() == 1){
                        char c = cells[j].charAt(0);
                        if(!(Character.isAlphabetic(c)||(c == 'o') || (c == '*'))){
                            validChar = false;
                        }
                    } else if (cells[j].length() == 2) {
                        char c1 = cells[j].charAt(0);
                        char c2 = cells[j].charAt(1);

                        if((Character.isAlphabetic(c1) && Character.isAlphabetic(c2)) && !((Character.isUpperCase(c1) && Character.isLowerCase(c2)) || (Character.isLowerCase(c1) && Character.isUpperCase(c2)))){
                                validChar = false;
                        }

                    } else{
                        validChar = false;
                    }
                    board[i][j] = cells[j];
                }
                if(!validChar){
                    System.out.println("you entered an invalid string, try again");
                    flag = false;
                    break;
                }
            }
            if(!flag){
                flag = true; continue;
            }
            flag = false;
        }

        current_state = new State(length,width, board);
        initial_state = new State(length,width,board);

    }

    public void choosePlayer(){

        boolean flag = true;
        while(flag) {
            System.out.println("choose your player\n1) Muggle\n2) Wizard");
            int choose = input.nextInt();
            if(choose == 1){
                flag = false;
                player = new Muggle();
            }
            else if(choose == 2){
                flag = false;
                player = new Wizard();
            }
            else{
                System.out.println("bad input, try again, ");
            }
            player.play(this);
        }

    }

    public boolean winning(){
        return current_state.winning();
    }


    public void readFromFile(){

    }


    public State getInitial_state() {
        return initial_state;
    }

    public State getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(State current_state) {
        this.current_state = current_state;
    }

    public void setInitial_state(State initial_state) {
        this.initial_state = initial_state;
    }

    public int letterSwitcher(String letter){
        return switch (letter) {
            case "w" -> 1;
            case "a" -> 0;
            case "s" -> 3;
            case "d" -> 2;
            case "r" -> throw new IllegalStateException ();
            default -> throw new InputMismatchException("the value u entered is not correct");
        };
    }
}
