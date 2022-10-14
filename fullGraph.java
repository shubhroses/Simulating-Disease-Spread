import java.util.ArrayList;

public class fullGraph {
	ArrayList<ArrayList<Integer>> graph;

	public fullGraph(int V) {
		this.graph = createGraph(V);
	}
	public static ArrayList<ArrayList<Integer>> createGraph(int V){
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>(V);
        for (int i = 0; i < V; i++){
            graph.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                graph.get(i).add(j);
            }
        }
        return graph;
	}
}
