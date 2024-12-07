package structure;

import behaviour.Printable;

import java.util.*;

public class State implements Printable {

    private Cell [][] board;
    private static int length;
    private static int width;
    public Integer hash = null;
    public boolean is_valid;


    public static final int [] I = {0,-1,0,1};
    public static final int [] J = {-1,0,1,0};

    public State(){

    }

    public State(int length,int width, String [][] board){
        State.length = length;
        State.width = width;
        this.board = new Cell[width][length];
        this.is_valid = true;
        for (int i = 0; i<width;i++){
            for (int j = 0;j<length;j++){
                this.board[i][j] = new Cell(board[i][j]);
            }
        }
    }

    public List<State> nexStates(){
        List <State> ls = new ArrayList<>();
        for(int i = 0;i<4;i++){
           ls.add(this.move(i));
        }
        return ls;
    }


    public State move(int move) {
        List <Pair<Integer,Integer>> colored_cells_coordinates = validCellsToMove(move);
        State nextState = this.deepCopy();

        Cell [][] next_state_board = nextState.getBoard();

            for(int k = 0; k<colored_cells_coordinates.size();k++){
                Pair<Integer, Integer> coord = colored_cells_coordinates.get(k);
                int i = coord.first; int j = coord.second;
                if(!nextState.in_border(i+I[move],j+J[move])){
                    return null;
                }
                if(!next_state_board[i+I[move]][j+J[move]].isAvailable()) {
                    continue;
                }
                next_state_board[i+I[move]][j+J[move]].setColor(next_state_board[i][j].getColor());
                next_state_board[i][j].setColor("o");
                Pair<Integer, Integer> new_coord = new Pair<>(i+I[move],j+J[move]);
//                colored_cells_coordinates.add(new_coord);
                if(next_state_board[i+I[move]][j+J[move]].isGoal()){
                    if(next_state_board[i+I[move]][j+J[move]].getGoal().equalsIgnoreCase(next_state_board[i+I[move]][j+J[move]].getColor())){
                        next_state_board[i+I[move]][j+J[move]].setColor("o");
                        next_state_board[i+I[move]][j+J[move]].setGoal("");
                        continue;
                    }
                    else if(next_state_board[i+I[move]][j+J[move]].isWhiteGoal()){
                        next_state_board[i+I[move]][j+J[move]].setGoal(next_state_board[i+I[move]][j+J[move]].getColor().toUpperCase());
                    }
                }
                colored_cells_coordinates.add(new_coord);
            }

        nextState.validate();
        return nextState;

    }

    public List<Pair<Integer, Integer>> validCellsToMove(int move){
        List <Pair<Integer,Integer>> ls = new ArrayList<>();
        for(int i = 0;i <width;i++){
            for(int j = 0; j<length;j++){
                if(board[i][j].isColor()&&board[i+I[move]][j+J[move]].isAvailable()){
                    ls.add(new Pair<>(i,j));
                }
            }
        }
        return ls;
    }

    public List<Pair<Integer,Integer>> coloredCells(){
        List <Pair<Integer,Integer>> ls = new ArrayList<>();
        for(int i = 0;i <width;i++){
            for(int j = 0; j<length;j++){
                if(board[i][j].isColor()){
                    ls.add(new Pair<>(i,j));
                }
            }
        }
        return ls;
    }



    public boolean in_border(int i, int j){
        return i>=0 && j >=0 && i<width && j<length;
    }

    public boolean winning(){
        List <Pair<Integer,Integer>> ls = this.coloredCells();
        return ls.isEmpty();
    }

    @Override
    public void print() {
        for(int i = 0; i<width;i++){
            for (int j = 0; j<length; j++){
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
        clone.board = new Cell[width][length];
        for (int i = 0;i<width;i++){
            for(int j = 0;j<length;j++){
                clone.board[i][j] = new Cell(board[i][j].getColor(),board[i][j].getGoal());
            }
        }
        return clone;
    }

    public void validate(){
        HashMap <String,Integer> mp = new HashMap<>();
        for (int i = 0; i <width ; i++) {
            for (int j = 0; j <length ; j++) {
                if(board[i][j].isColor()){
                    mp.put(board[i][j].getColor(),mp.getOrDefault(board[i][j].getColor(),0)+1);
                }
                if(board[i][j].isGoal()){
                    mp.put(board[i][j].getGoal().toLowerCase(),mp.getOrDefault(board[i][j].getGoal().toLowerCase(),0)-1);
                }
            }
        }
        is_valid = true;
        int cnt = mp.getOrDefault("w",0);
        for (Map.Entry<String, Integer> entry : mp.entrySet()) {
            //System.out.println("key: "+entry.getKey()+" value: "+entry.getValue());
            if(entry.getValue()>0){
                cnt+=entry.getValue();
            }
        }
        is_valid = (cnt == 0);
    }


    @Override
    public int hashCode() {
        if(hash != null) return hash;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(board[i][j].isGoal()) {
                   sb.append(i);sb.append(j);sb.append(board[i][j].getGoal());
                   //sb.append('|');
                }
                if (board[i][j].isColor()) {
                    sb.append(i);sb.append(j);sb.append(board[i][j].getColor());
                    //sb.append('|');
                }
            }
        }
        hash = sb.toString().hashCode();
        return hash;
    }

    public boolean isEqualTo(State s) {
       return s.hashCode() == this.hashCode();
    }


    public Cell[][] getBoard() {
        return board;
    }

    public static int getLength() {
        return length;
    }

    public static int getWidth() {
        return width;
    }
}
