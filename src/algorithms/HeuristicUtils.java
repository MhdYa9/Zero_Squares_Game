package algorithms;

import structure.Cell;
import structure.Pair;
import structure.State;

import java.util.*;

public class HeuristicUtils {


    public static int Heuristic(State state){
        return shortestPathHeuristic(state);
    }

    private static int ManhattanDistance(State state){

        HashMap <String, Pair<Integer,Integer>> mp = new HashMap<>();

        Cell[][] board = state.getBoard();
        for (int i =0;i<State.getWidth();i++){
            for(int j = 0;j<State.getLength();j++){
                if(board[i][j].isGoal()){
                    mp.put(board[i][j].getGoal(),new Pair<>(i,j));
                }
                if(board[i][j].isColor()){
                    mp.put(board[i][j].getColor(),new Pair<>(i,j));
                }
            }
        }

        int sum = 0;
        for (Map.Entry<String, Pair<Integer, Integer>> entry : mp.entrySet()) {
            char c = entry.getKey().charAt(0);
            if (Character.isLowerCase(c)) { // Only process colored squares
                Pair<Integer, Integer> colorPos = entry.getValue();
                Pair<Integer, Integer> goalPos = mp.getOrDefault(entry.getKey().toUpperCase(), new Pair<>(1000, 1000));

                int distance = Math.abs(colorPos.first - goalPos.first) + Math.abs(colorPos.second - goalPos.second);
                sum += distance;
            }
        }

        return sum;
    }

    //I will implement BFS to find the real shortest from every square to its goal
    //this is exactly Manhatten Distance, but it handles blocks

    private static int shortestPathHeuristic(State state) {
        int sum = 0;
        int width = State.getWidth();
        int height = State.getLength();
        int[] I = {0, -1, 0, 1};
        int[] J = {-1, 0, 1, 0};
        Cell[][] board = state.getBoard();

        for (Pair<Integer, Integer> coord : state.coloredCells()) {
            int startX = coord.first, startY = coord.second;
            // Initialize distance grid with -1 (meaning unvisited)
            int[][] dist = new int[width][height];
            for (int i = 0; i < width; i++) {
                Arrays.fill(dist[i], -1);
            }
            Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
            dist[startX][startY] = 0;
            queue.add(new Pair<>(startX, startY));

            boolean flag = false;
            while (!queue.isEmpty() && !flag) {
                Pair<Integer, Integer> current = queue.poll();
                int i = current.first, j = current.second;
                int d = dist[i][j];

                for (int k = 0; k < 4; k++) {
                    int ni = i + I[k], nj = j + J[k];
                    if (ni < 0 || ni >= width || nj < 0 || nj >= height)
                        continue;
                    if (dist[ni][nj] != -1)
                        continue;

                    dist[ni][nj] = d + 1;
                    if (board[ni][nj].getGoal() != null && board[ni][nj].getGoal().equalsIgnoreCase(board[startX][startY].getColor())) {
                        sum += dist[ni][nj];
                        flag = true;
                        break;
                    }
                    queue.add(new Pair<>(ni, nj));
                }
            }
            if (!flag) {
                sum += 1000; // Penalty if no goal is reachable
            }
        }
        return sum;
    }







}
