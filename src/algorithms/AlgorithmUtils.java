
package algorithms;

import structure.Pair;
import structure.State;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class AlgorithmUtils {


    public static Stack<Integer> dfs(State first_state){

        HashSet <Integer> visited = new HashSet<>();
        Stack <State> stack = new Stack<>();
        HashMap <State,State> parent = new HashMap<>();
        HashMap <State,Integer> parentMove = new HashMap<>();

        stack.add(first_state);
        parent.put(first_state,null);
        parentMove.put(first_state,-1);
        State node; State goal = null; boolean flag = true;

        while(!stack.isEmpty() && flag){
            node = stack.pop();
            visited.add(node.hashCode());

            int i = 0;
            for(State s: node.nexStates()){
                i++;
                if((s != null)&&(!visited.contains(s.hashCode()))){
                    stack.add(s);
                    parent.put(s,node);
                    parentMove.put(s,i);
                    if(s.winning()){
                        goal = s; flag = false;
                        break;
                    }
                }
            }
        }

        Stack <Integer> path = new Stack<>();

        node = goal;
        while(node != null){
            path.add(parentMove.get(node));
            node = parent.get(node);
        }

        return path;

    }

    public static void bfs(){

    }

}
