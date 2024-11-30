
package algorithms;

import structure.Pair;
import structure.State;

import java.util.*;

public class AlgorithmUtils {


    public static int visited_log = 0;


    private static HashSet <Integer> rdfs_visited = new HashSet<>();
    private static HashMap <Integer,Integer> rdfs_parent = new HashMap<>();
    private static HashMap <Integer,Integer> rdfs_parentMove = new HashMap<>();
    private static Integer rdfs_goal = null;
    private static boolean rdfs_flag = false;


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


    public static void rec_dfs(State state){
        if(rdfs_flag) return;
        visited_log++;
        rdfs_visited.add(state.hashCode());
        int i = 0;
        for(State n : state.nexStates()){
            if((n != null)&&(n.is_valid)&&(!rdfs_visited.contains(n.hashCode()))){
                rdfs_parent.put(n.hashCode(),state.hashCode());
                rdfs_parentMove.put(n.hashCode(),i);
                if(n.winning()){
                    rdfs_goal = n.hashCode();
                    rdfs_flag = true;
                    return;
                }
                rec_dfs(n);
            }
            i++;
        }
    }

    public static Stack<Integer> r_dfs(State first_state){
        rdfs_parent.put(first_state.hashCode(),null);
        rdfs_parentMove.put(first_state.hashCode(),-1);
        rec_dfs(first_state);
        Stack <Integer> path = new Stack<>();
        Integer n = rdfs_goal;
        while(n != null){
            path.add(rdfs_parentMove.get(n));
            n = rdfs_parent.get(n);
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
        visited.add(first_state.hashCode());
        State node; Integer goal = null; boolean flag = true;

        while(!queue.isEmpty() && flag){
            node = queue.poll();
            int i = 0;
            for(State s: node.nexStates()){
                if((s != null)&&(s.is_valid)&&(!visited.contains(s.hashCode()))){
                    queue.add(s);
                    visited.add(s.hashCode());
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


    public static Stack<Integer> uniform_cost_search(State first_state){

        //implementing for cost 1 for each step

        PriorityQueue <Pair<Integer,State>> pq = new PriorityQueue<>();
        HashMap <Integer,Integer> parent = new HashMap<>();
        HashMap <Integer,Integer> parentMove = new HashMap<>();
        HashMap <Integer,Integer> cost = new HashMap<>();

        pq.add(new Pair<>(0,first_state));
        cost.put(first_state.hashCode(),0);
        parent.put(first_state.hashCode(),null);
        parentMove.put(first_state.hashCode(),-1);

        State node; Integer goal = null; boolean flag = true; Integer w = 0;

        while (!pq.isEmpty() && flag){
            visited_log++;
            Pair <Integer,State> p = pq.poll();
            node = p.second; w = p.first;
            if(cost.get(node.hashCode()) < w) continue;
            int i = 0;
            for(State s : node.nexStates()){
                if((s != null)&&(s.is_valid) && (cost.getOrDefault(s.hashCode(),Integer.MAX_VALUE) > w + 1)){
                    pq.add(new Pair<>(w+1,s));
                    cost.put(s.hashCode(),w+1);
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
