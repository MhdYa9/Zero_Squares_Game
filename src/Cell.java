public class Cell {

    private String color;
    private String goal;
    private boolean next_move_valid;

    public Cell(String word) {
        this.goal = "";
        this.color = "";
        this.next_move_valid = true;
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
        this.next_move_valid = true;
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

    public boolean isBlackGoal(){
        return goal.equals("B");
    }

    public boolean isWhiteGoal(){
        return goal.equals("W");
    }

    public boolean isNext_move_valid() {
        return next_move_valid;
    }

    public void setNext_move_valid(boolean next_move_valid) {
        this.next_move_valid = next_move_valid;
    }

    public boolean isEqualTo(Cell other){
        return goal.equals(other.getGoal()) && color.equals(other.getColor());

    }
}
