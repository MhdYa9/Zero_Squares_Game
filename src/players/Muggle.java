package players;

import structure.Game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Muggle extends Player{


    @Override
    public void play(Game game){

        String letter = "" ;
        System.out.println("enter your move with letters: a - w - s - d - r for restart\n");
        while(!game.winning()){
            try {
                game.getCurrent_state().print();
                System.out.println();
                letter = input.next();
                int move = game.letterSwitcher(letter);
                game.setCurrent_state(game.getCurrent_state().move(move));
                if(game.getCurrent_state() == null){
                    throw new IllegalStateException();
                }
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
