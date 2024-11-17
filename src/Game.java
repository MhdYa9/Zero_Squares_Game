import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game implements Playable {

    private State current_state;
    private State initial_state;

    public Game(){
        readFromConsole();
    }


    public void readFromConsole(){

        //read the size
        Scanner input = new Scanner(System.in);
        int size = 0;
        do{
            size = input.nextInt();
        }
        while(size<=0);

        //read the console

        String [][] board = new String[size][size];
        boolean flag = true;
        input.nextLine();

        while(flag){
            for(int i = 0 ; i<size;i++){
                String row = input.nextLine();
                String [] cells = row.split(" ");
                if(cells.length!=size){
                    System.out.println("each row must be of size: "+size);
                    flag = false;
                    break;
                }
                boolean validChar = true;
                for(int j = 0;j<size;j++){
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

        current_state = new State(size, board);
        initial_state = new State(size,board);

    }

    public boolean winning(){
        List <Pair<Integer,Integer>> ls = current_state.coloredCells();
        return ls.isEmpty();
    }


    public void readFromFile(){

    }

    @Override
    public void play() {
        mugglePlay();
    }

    public void mugglePlay(){

        Scanner input = new Scanner(System.in);
        String letter = "" ;
        while(!winning()){
            try {
                current_state.print();
                System.out.println("enter your move with letters: a - w - s - d - r for restart");
                letter = input.next();
                int move = letterSwitcher(letter);
                current_state = current_state.move2(move);
            }
            catch (InputMismatchException e){
                System.out.println("the value you entered is not valid");
                if(e.getMessage().isEmpty()) input.next();
            }
            catch (IllegalStateException e){
                System.out.println("try again :)");
                current_state = initial_state.deepCopy();
            }
        }
        System.out.println("congratulations you won!");

    }

    public void wizardPlay(){

    }

    public State getInitial_state() {
        return initial_state;
    }

    public State getCurrent_state() {
        return current_state;
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
