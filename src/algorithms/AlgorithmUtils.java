
package algorithms;

import structure.Pair;
import structure.State;

import java.util.*;

public class AlgorithmUtils {


    public static int visited_log = 0;


    public static Stack<Integer> dfs(State first_state){

        HashSet <Integer> visited = new HashSet<>();
        Stack <State> stack = new Stack<>();
        HashMap <Integer,Integer> parent = new HashMap<>();
        HashMap <Integer,Integer> parentMove = new HashMap<>();

        stack.add(first_state);
        parent.put(first_state.hashCode(),null);
        parentMove.put(first_state.hashCode(),-1);
        State node; Integer goal = null; boolean flag = true;

        while(!stack.isEmpty() && flag){
            node = stack.pop();
            visited.add(node.hashCode());
            int i = 0;

            for(State s: node.nexStates()){
                if((s != null)&&(s.is_valid)&&(!visited.contains(s.hashCode()))){
                    stack.add(s);
                    parent.put(s.hashCode(),node.hashCode());
                    parentMove.put(s.hashCode(),i);
                    if(s.winning()){
                        goal = s.hashCode(); flag = false;
                        break;
                    }
                }
                i++;
            }
        }

        visited_log = visited.size();
        Stack <Integer> path = new Stack<>();

        Integer n = goal;
        while(n != null){
            path.add(parentMove.get(n));
            n = parent.get(n);
        }
        if(!path.isEmpty()) path.pop();

        return path;

    }

    public static Stack<Integer> bfs(State first_state){
        HashSet <Integer> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        HashMap <Integer,Integer> parent = new HashMap<>();
        HashMap <Integer,Integer> parentMove = new HashMap<>();

        queue.add(first_state);
        parent.put(first_state.hashCode(),null);
        parentMove.put(first_state.hashCode(),-1);
        State node; Integer goal = null; boolean flag = true;

        while(!queue.isEmpty() && flag){
            node = queue.poll();
            visited.add(node.hashCode());
            int i = 0;
            for(State s: node.nexStates()){
                if((s != null)&&(s.is_valid)&&(!visited.contains(s.hashCode()))){
                    queue.add(s);
                    parent.put(s.hashCode(),node.hashCode());
                    parentMove.put(s.hashCode(),i);
                    if(s.winning()){
                        goal = s.hashCode(); flag = false;
                        break;
                    }
                }
                i++;
            }
        }

        visited_log = visited.size();

        Stack <Integer> path = new Stack<>();

        Integer n = goal;
        while(n != null){
            path.add(parentMove.get(n));
            n = parent.get(n);
        }
        if(!path.isEmpty()) path.pop();

        return path;
    }

}
