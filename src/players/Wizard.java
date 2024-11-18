package players;

import algorithms.AlgorithmUtils;
import structure.Game;

import java.util.Scanner;
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

        System.out.println("choose an algorithm:\n1) DFS\n2) BFS");
        int algo = input.nextInt();

        long startTime = System.nanoTime();
        Stack <Integer> path;

        if(algo == 1){
            path = AlgorithmUtils.dfs(game.getCurrent_state());
        }
        else if (algo == 2) {
            path =  AlgorithmUtils.bfs(game.getCurrent_state());
        }
        else{
            path = new Stack<>();
        }

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
            System.out.println();
        }

        game.getCurrent_state().print();

        System.out.println("\nElapsed time: " + elapsedTime / 1000000000.0 + " s");
        System.out.println("Visited nodes: "+ AlgorithmUtils.visited_log);
        System.out.println("Total moves: " + total);


    }

}
