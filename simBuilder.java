import java.util.ArrayList;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;


public class simBuilder implements ContextBuilder<Object> {

	@Override
	public Context build(Context<Object> context) {
		context.setId("5990Final");
		
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>(
				"infection network", context, true);
		netBuilder.buildNetwork();

		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace(
				"space", context, new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.WrapAroundBorders(), 50,
				50);

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Object> grid = gridFactory.createGrid("grid", context,
				new GridBuilderParameters<Object>(new WrapAroundBorders(),
						new SimpleGridAdder<Object>(), true, 50, 50));

		Parameters params = RunEnvironment.getInstance().getParameters();
	
		
		int infectiousCount = params.getInteger("infectious_count");
		int susceptibleCount = params.getInteger("susceptible_count");
		
		int totalCount = infectiousCount + susceptibleCount;
		
		
		
		boolean useBarabasi = params.getBoolean("use_barabasi");
		boolean useWatts = params.getBoolean("use_watts");
		
		
		int barabasi_m = params.getInteger("barabasi_m");
		int watts_degree = params.getInteger("watts_degree");
		double watts_beta = params.getDouble("watts_beta");
		
		
		barabasiGraph graphB = new barabasiGraph(totalCount, barabasi_m);
	
		wattsGraph graphW = new wattsGraph(totalCount, watts_degree, watts_beta);
	
		fullGraph graphF = new fullGraph(totalCount);
		
		//barabasiGraph graph = new barabasiGraph(totalCount, 1);
		//wattsGraph graph = new wattsGraph(totalCount, 8, 0.5);
		//fullGraph graph = new fullGraph(totalCount);
		
		
		ArrayList<ArrayList<Integer>> graphAdjList = graphF.graph;
		if(useBarabasi) {
			graphAdjList = graphB.graph;
		}
		else if(useWatts) {
			graphAdjList = graphW.graph;
		}
		
		
		
		/*
		ArrayList<ArrayList<Integer>> graphAdjList = new ArrayList<ArrayList<Integer>>();
        for(int i = 1; i < totalCount; i++){
            graphAdjList.add(new ArrayList<Integer>());
        }
        graphAdjList.add(new ArrayList<Integer>());
        graphAdjList.get(0).add(1);
        graphAdjList.get(0).add(2);

		*/
		
		int curInd = 0;
		for(int i = 0; i < infectiousCount; i ++) {
			context.add(new infectious(space, grid, graphAdjList, curInd));
			curInd += 1;
		}
		
		
		for (int i = 0; i < susceptibleCount; i ++) {
			context.add(new susceptible(space, grid, graphAdjList, curInd));
			curInd += 1;
		}
		
		
		
		for (Object obj : context.getObjects(Object.class)) {
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int) pt.getX(), (int) pt.getY());
		}

		return context;
	}

}
