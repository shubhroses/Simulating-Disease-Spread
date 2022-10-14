import java.util.ArrayList;

public class barabasiGraph {
	ArrayList<ArrayList<Integer>> graph;

	public barabasiGraph(int V, int m) {
		this.graph = createGraph(V, m);
		System.out.println("Successfully initialized Graph");
	}
	
	static int getTotal(ArrayList<ArrayList<Integer>> am){
	    int total = 0;
	    for (int i = 0; i < am.size(); i++){
	        total += am.get(i).size();
	    }
	    return total;
	}
	
	static void addEdge(ArrayList<ArrayList<Integer>> am, int s, int d) {
	    am.get(s).add(d);
	    am.get(d).add(s);
	}
	
	static ArrayList<Double> getProb(ArrayList<ArrayList<Integer>> am, int total, int V){
	    ArrayList<Double> prob = new ArrayList<Double>(V);
	    for (int i = 0; i < am.size(); i++){
	        double num = am.get(i).size();
	        double p = num/total;
	        prob.add(p);
	    }
	    return prob;
	}
	
	public static int chooseElement(ArrayList<Double> prob){
	    double p = Math.random();
	    double cumProb = 0.0;
	    for(int i = 0; i < prob.size(); i++){
	        cumProb += prob.get(i);
	        if(p <= cumProb){
	            return i;
	        }
	    }
	    return 0;
	}
	
	static ArrayList<ArrayList<Integer>> createGraph(int V, int m){
	    ArrayList<ArrayList<Integer>> am = new ArrayList<ArrayList<Integer>>(V);
	    for (int i = 0; i < V; i++){
	      am.add(new ArrayList<Integer>());
	    }
	    for (int i = 0; i < V; i++){
	        for (int j = 0; j < m; j ++){
	            int total = getTotal(am);
	            ArrayList<Double> prob = getProb(am, total, V);
	            int node = chooseElement(prob);
	            if (am.get(i).contains(node) || i == node){
	                continue;
	            }
	            addEdge(am, i, node);
	        }
	    }
	    return am;
	}
}
