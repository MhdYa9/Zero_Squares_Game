
package structure;

import java.util.Objects;

public class Cell {

    private String color;
    private String goal;

    public Cell(String word) {
        this.goal = "";
        this.color = "";
        for(int i = 0; i<word.length();i++){
            if(Character.isAlphabetic(word.charAt(i))){
                if(Character.isUpperCase(word.charAt(i))){
                    this.goal += word.charAt(i);
                }
                else{
                    this.color += word.charAt(i);
                }
            }
            else {
                this.color += word.charAt(i);
            }
        }
    }

    public Cell(String color,String goal){
        this.color = color;
        this.goal = goal;
    }


    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isGoal(){
        return !this.goal.isEmpty();
    }

    public boolean isColor(){
        return !color.isEmpty() && !color.equals("*") && !color.equals("o");
    }

    public boolean isAvailable(){
        return color.equals("o") || (!goal.isEmpty() && color.isEmpty());
    }

    public boolean isBlack(){
        return color.equals("*");
    }


    public boolean isWhiteGoal(){
        return goal.equals("W");
    }

    public boolean isEqualTo(Cell other){
        return goal.equals(other.getGoal()) && color.equals(other.getColor());

    }

    @Override
    public int hashCode() {
        return Objects.hash(color, goal);
    }
}
