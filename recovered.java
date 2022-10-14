import java.util.ArrayList;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

/**
 * 
 */

/**
 * @author sazap
 *
 */
public class recovered {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	public ArrayList<ArrayList<Integer>> graph;
	public int index;

	public recovered(ContinuousSpace<Object> space, Grid<Object> grid, ArrayList<ArrayList<Integer>> graph, int i) {
		this.space = space;
		this.grid = grid;
		this.graph = graph;
		this.index = i;
	}
}
