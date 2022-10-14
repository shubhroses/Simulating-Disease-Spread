import java.util.ArrayList;

public class wattsGraph {
	ArrayList<ArrayList<Integer>> graph;

	public wattsGraph(int V, int m, double b) {
		this.graph = getGraph(V, m, b);
		System.out.println("Successfully initialized Graph");
	}
	public static ArrayList<ArrayList<Integer>> getRing(int n, int k){
        int halfk = k/2;
        ArrayList<ArrayList<Integer>> ring = new ArrayList<ArrayList<Integer>>(n);
        for (int i = 0; i < n; i++){
            ring.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < n; i ++){
            for(int j = i+1; j < i+halfk+1; j++){
                int d = j % n;
                ring.get(i).add(d);
                ring.get(d).add(i);
            }
        }
        return ring;
    }
    public static ArrayList<ArrayList<Integer>> getGraph(int n, int k, double b){
        ArrayList<ArrayList<Integer>> ring = getRing(n, k);
        for(int s = 0; s < n; s++){
            // System.out.println(ring.get(s));
            int i = s;
            for(int d = 0; d < ring.get(s).size(); d++){
                // System.out.println(ring.get(s).get(d));
                int j = ring.get(s).get(d); //Node to remove
                if(j <= i){
                    continue;
                }
                int range = n - 1 + 1;
                int newNode = (int)(Math.random() * range);
                if(ring.get(i).contains(newNode)){
                    continue;
                }
                if( i == newNode){
                    continue;
                }
                double chance = Math.random();
                if (chance < b){
                    ring.get(i).remove(d); //Remove i-j
                    ring.get(i).add(d, newNode); //Add i-newNode
                    ring.get(newNode).add(i);    //Add newNode-i
                    int indI = ring.get(j).indexOf(i);
                    ring.get(j).remove(indI); // Remove j-i
                }
            }
        }
        return ring;
    }
}