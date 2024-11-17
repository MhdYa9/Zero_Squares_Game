import java.util.InputMismatchException;
import java.util.Scanner;

public class Muggle extends Player{


    public Muggle(){
        super();
    }


    @Override
    public void play(){

        Scanner input = new Scanner(System.in);
        String letter = "" ;
        while(!game.winning()){
            try {
                game.getCurrent_state().print();
                System.out.println("enter your move with letters: a - w - s - d - r for restart");
                letter = input.next();
                int move = game.letterSwitcher(letter);
                game.setCurrent_state(game.getCurrent_state().move(move));
            }
            catch (InputMismatchException e){
                System.out.println("the value you entered is not valid");
                if(e.getMessage().isEmpty()) input.next();
            }
            catch (IllegalStateException e){
                System.out.println("try again :)");
                game.setCurrent_state(game.getInitial_state().deepCopy());
            }
        }
        System.out.println("congratulations you won!");

    }
}
