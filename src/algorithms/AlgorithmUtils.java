
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


    public static void r_dfs(State state,State parent){
        if(rdfs_flag) return;
        visited_log++;
        rdfs_visited.add(state.hashCode());
        if(parent!=null) rdfs_parent.put(state.hashCode(),parent.hashCode());
        int i = 0;
        for(State n : state.nexStates()){
            r_dfs(n,state);
            rdfs_parent.put(state.hashCode(),n.hashCode());
            rdfs_parentMove.put(state.hashCode(),i);
            if(state.winning()){
                rdfs_goal = state.hashCode();
                rdfs_flag = true;
                return;
            }
            i++;
        }
    }

    public static Stack<Integer> r_dfs(State first_state){
        rdfs_parent.put(first_state.hashCode(),null);
        r_dfs(first_state,null);
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


    //uniform cost search
    public static void ucs(State first_state){

    }

    /*
    *
    vector <ll> dijkstra(int node){

        priority_queue <pll> pq; //first is the distance from the source and sec is the node itself
        vector <ll> dist(n+1,infl);
        vector <bool> picked(n+1,false);
        vector <int> path(n+1);
        dist[node] = 0; pq.push({-0,node}); path[node] = -1; //dsu array to record the path

        while(!pq.empty()){
            pll u = pq.top(); pq.pop();
            if(picked[u.second]) continue; //pretty important line
            u.first*=-1; picked[u.second] = true;
    //        if(u.first>dist[u.second]){   // used if u don't want picked   array
    //            continue; //this is because if we picked a solution it's the best and any other solution will be bad
    //        }
            for(pll v : adjw[u.second]){
                if(!picked[v.vertex]){
                    if(dist[v.vertex]>dist[u.second]+v.weight){ //relaxation: if the new path is better update
                        dist[v.vertex] = dist[u.second]+v.weight;
                        path[v.vertex] = u.second; //if u update the node then update its parent
                        pq.push({-dist[v.vertex],v.vertex});
                    }
                }
            }
        }
        //printing the path
        if(dist[n] == infl){
            cout<< -1;
            //return;
        }
        int parent = n; stack <int> st;
        while(parent != -1){
            st.push(parent);
            parent = path[parent];
        }
        while(!st.empty()){
            cout<<st.top()<<" ";
            st.pop();
        }
        return dist; //return path //return destination == -1?-1:dist[destination];
    }
        * */

}
