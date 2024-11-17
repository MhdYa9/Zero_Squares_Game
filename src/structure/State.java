package structure;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class State implements Printable {

    private Cell [][] board;
    private static int size;


    public static final int L = 0;
    public static final int U = 1;
    public static final int R = 2;
    public static final int D = 3;


    public static final int [] I = {0,-1,0,1};
    public static final int [] J = {-1,0,1,0};

    public State(){

    }

    public State(int size, String [][] board){
        State.size = size;
        this.board = new Cell[size][size];
        for (int i = 0; i<size;i++){
            for (int j = 0;j<size;j++){
                this.board[i][j] = new Cell(board[i][j]);
            }
        }
    }

    public List<State> nexStates(){
        List <State> ls = new ArrayList<>();
        for(int i = 0;i<4;i++){
           State s = this.move(i).deepCopy();
           if(!s.isEqualTo(this)){
               ls.add(s);
           }
        }
        return ls;
    }


    public State move(int move) throws InputMismatchException,IllegalStateException{
        List <Pair<Integer,Integer>> colored_cells_coordinates = validCellsToMove(move);
        State nextState = this.deepCopy();

        if(move>3 || move<0) throw  new InputMismatchException("the number you entered is not valid for a movement");
        Cell [][] next_state_board = nextState.getBoard();
        do{
            for(int k = 0; k<colored_cells_coordinates.size();k++){
                Pair<Integer, Integer> coord = colored_cells_coordinates.get(k);
                int i = coord.first; int j = coord.second;
                colored_cells_coordinates.remove(coord);
                if(!nextState.in_border(i+I[move],j+J[move])){
                    throw new IllegalStateException("out of border you lost");
                }
                if(!next_state_board[i+I[move]][j+J[move]].isAvailable()){
                   continue;
                }
                next_state_board[i+I[move]][j+J[move]].setColor(next_state_board[i][j].getColor());
                next_state_board[i][j].setColor("o");
                Pair<Integer, Integer> new_coord = new Pair<>(i+I[move],j+J[move]);
                colored_cells_coordinates.add(new_coord);
                if(next_state_board[i+I[move]][j+J[move]].isGoal()){
                    if(next_state_board[i+I[move]][j+J[move]].getGoal().equalsIgnoreCase(next_state_board[i+I[move]][j+J[move]].getColor())){
                        next_state_board[i+I[move]][j+J[move]].setColor("o");
                        next_state_board[i+I[move]][j+J[move]].setGoal("");
                        colored_cells_coordinates.remove(new_coord);
                    }
                    else if(next_state_board[i+I[move]][j+J[move]].isWhiteGoal()){
                        next_state_board[i+I[move]][j+J[move]].setGoal(next_state_board[i+I[move]][j+J[move]].getColor().toUpperCase());
                    }
                }

            }
        }
        while(!colored_cells_coordinates.isEmpty());

        return nextState;

    }

    public List<Pair<Integer, Integer>> validCellsToMove(int move){
        List <Pair<Integer,Integer>> ls = new ArrayList<>();
        for(int i = 0;i <size;i++){
            for(int j = 0; j<size;j++){
                if(board[i][j].isColor()&&in_border(i+I[move],j+J[move])&&board[i+I[move]][j+J[move]].isAvailable()){
                    ls.add(new Pair<>(i,j));
                }
            }
        }
        return ls;
    }

    public List<Pair<Integer,Integer>> coloredCells(){
        List <Pair<Integer,Integer>> ls = new ArrayList<>();
        for(int i = 0;i <size;i++){
            for(int j = 0; j<size;j++){
                if(board[i][j].isColor()){
                    ls.add(new Pair<>(i,j));
                }
            }
        }
        return ls;
    }

    public boolean in_border(int i, int j){
        return i>=0 && j >=0 && i<size && j<size;
    }

    public boolean is_valid(int i, int j){
        return in_border(i,j) && !board[i][j].isBlack() && !board[i][j].isColor();
    }

    public boolean winning(){
        List <Pair<Integer,Integer>> ls = this.coloredCells();
        return ls.isEmpty();
    }

    @Override
    public void print() {
        for(int i = 0; i<size;i++){
            for (int j = 0; j<size; j++){
                if(board[i][j].isGoal() && board[i][j].isColor()){
                    System.out.print(Printable.colors[Printable.mp.get(board[i][j].getColor())] + Printable.borders[Printable.mp.get(board[i][j].getGoal().toLowerCase())]  + " ■ " + Printable.RESET);
                }
                else if(!board[i][j].isGoal()){
                    System.out.print(Printable.colors[Printable.mp.get(board[i][j].getColor())] + "   " + Printable.RESET);
                }
                else{
                    System.out.print(Printable.borders[Printable.mp.get(board[i][j].getGoal().toLowerCase())]  + " ■ " + Printable.RESET);
                }
            }
            System.out.println();
        }
    }

    public State deepCopy(){
        State clone = new State();
        clone.board = new Cell[size][size];
        for (int i = 0;i<size;i++){
            for(int j = 0;j<size;j++){
                clone.board[i][j] = new Cell(board[i][j].getColor(),board[i][j].getGoal());
            }
        }
        return clone;
    }


    public boolean isEqualTo(State s) {
        if (this == s) {
            return true;
        }
        if (s == null || getClass() != s.getClass()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for(int j = 0;j <size;j++){
                if(!board[i][j].isEqualTo(s.board[i][j])) return false;
            }
        }
        return true;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public static int getSize() {
        return size;
    }
}
