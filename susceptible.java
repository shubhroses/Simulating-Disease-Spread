import java.util.ArrayList;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

/**
 * 
 */

/**
 * 
 *
 */
public class susceptible {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	public ArrayList<ArrayList<Integer>> graph;
	public int index;

	public susceptible(ContinuousSpace<Object> space, Grid<Object> grid, ArrayList<ArrayList<Integer>> graph, int i) {
		this.space = space;
		this.grid = grid;
		this.graph = graph;
		this.index = i;
	}
}
