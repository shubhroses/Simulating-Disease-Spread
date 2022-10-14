import java.util.ArrayList;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

/**
 * 
 */

/**
 * 
 *
 */
public class infectious {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	public ArrayList<ArrayList<Integer>> graph;
	public int index;

	public infectious(ContinuousSpace<Object> space, Grid<Object> grid, ArrayList<ArrayList<Integer>> graph, int i) {
		this.space = space;
		this.grid = grid;
		this.graph = graph;
		this.index = i;
	}
	
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		if(!heal()) {
			infect();
		}
	}
	public boolean heal() {
		Parameters params = RunEnvironment.getInstance().getParameters();
		boolean res = false;
		double gamma = params.getDouble("gamma_value");;
		double chance = RandomHelper.nextDoubleFromTo(0, 1);
		if (chance < gamma) {
			GridPoint pt = grid.getLocation(this); //infectious persons location
			NdPoint spacePt = space.getLocation(this);
			Context<Object> context = ContextUtils.getContext(this);
			context.remove(this);
			recovered recov = new recovered(space, grid, this.graph, this.index);
			context.add(recov);
			space.moveTo(recov, spacePt.getX(), spacePt.getY());
			grid.moveTo(recov, pt.getX(), pt.getY());
			res = true;
		}
		return res;
	}
	public void infect() {
		Parameters params = RunEnvironment.getInstance().getParameters();
		
		GridPoint pt = grid.getLocation(this);
		double beta = params.getDouble("beta_value");
		List<susceptible> suscepts = new ArrayList<susceptible>();
		
		ArrayList<Integer> neighbors = graph.get(index);
		
		
		for (Object obj : grid.getObjects()) {
			if (obj instanceof susceptible) {
				susceptible myObj = (susceptible)obj;
				if(neighbors.contains(myObj.index)) {
					suscepts.add(myObj);
				}
			}
		}
		
		for(int i = 0; i < suscepts.size(); i++) {
			double chance = Math.random();
			if (chance <= beta) {
				susceptible obj = suscepts.get(i);
				NdPoint spacePt = space.getLocation(obj);
				Context<Object> context = ContextUtils.getContext(obj);
				context.remove(obj);

				infectious infec = new infectious(space, grid, this.graph, obj.index);
				
				context.add(infec);
				space.moveTo(infec, spacePt.getX(), spacePt.getY());
				grid.moveTo(infec, pt.getX(), pt.getY());
				Network<Object> net = (Network<Object>)context.getProjection("infection network");
				net.addEdge(this, infec);
			}
		}
	}
}
