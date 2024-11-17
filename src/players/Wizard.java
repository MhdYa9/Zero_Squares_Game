package players;

import algorithms.AlgorithmUtils;
import structure.Game;

import java.util.Stack;

public class Wizard extends Player{


    private int pace;

    public Wizard(){
        super();
        pace = 250;
    }

    public Wizard(int pace){
        this.pace = pace;
    }

    @Override
    public void play(Game game){


        long startTime = System.nanoTime();

        Stack <Integer> path = AlgorithmUtils.bfs(game.getCurrent_state());

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        int total = path.size();

        // Convert to milliseconds for readability
        while(!path.isEmpty()){

            game.getCurrent_state().print();
            try {
                Thread.sleep(pace);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            game.setCurrent_state(game.getCurrent_state().move(path.pop()));
        }

        game.getCurrent_state().print();

        System.out.println("\nElapsed time: " + elapsedTime / 1000000000.0 + " s");
        System.out.println("total moves: " + total);
    }

}
