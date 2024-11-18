package structure;

import players.Muggle;
import players.Player;
import players.Wizard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Game {

    private static final Scanner input = new Scanner(System.in);
    private State current_state;
    private State initial_state;
    private Player player;

    public Game(){
        readFromFile("src/levels.txt");
        choosePlayer();
    }

    public void readFromConsole(){

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


    public void readFromFile(String file_name) {
        HashMap<String, String[]> levels = loadLevels(file_name);
        Scanner input = new Scanner(System.in);
        String selectedLevel;

        while (true) {
            System.out.println("Available Levels:");
            for (String level : levels.keySet()) {
                System.out.println(level);
            }
            System.out.print("Select a level (input only the number): ");
            selectedLevel = input.nextLine().trim();
            selectedLevel = "Level "+selectedLevel;

            if (levels.containsKey(selectedLevel)) {
                String[] levelData = levels.get(selectedLevel);

                try {
                    int length = Integer.parseInt(levelData[0]);
                    int width = Integer.parseInt(levelData[1]);

                    String[][] board = new String[width][length];
                    for (int i = 0; i < width; i++) {
                        String[] cells = levelData[i + 2].split(" ");
                        board[i] = cells;
                    }

                    // Initialize the game state
                    initial_state = new State(length, width, board);
                    current_state = new State(length, width, board);

                    break;
                } catch (Exception e) {
                    System.err.println("Error loading level: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid level, please try again.");
            }
        }
    }

    private static HashMap<String,String[]> loadLevels(String filename) {

        HashMap<String, String[]> levels = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String currentLevel = null;
            String[] levelData = new String[100]; // Assuming maximum 100 lines per level
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                if (line.endsWith(":")) {
                    // Store previous level if exists
                    if (currentLevel != null) {
                        levels.put(currentLevel, levelData);
                    }
                    // Start new level
                    currentLevel = line.substring(0, line.length() - 1).trim();
                    levelData = new String[100];
                    lineCount = 0;
                } else {
                    levelData[lineCount++] = line.trim();
                }
            }
            // Store last level
            if (currentLevel != null) {
                levels.put(currentLevel, levelData);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return levels;
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
