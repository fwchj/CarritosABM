package carritosABM;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;

public class Builder implements ContextBuilder<Object> {

		public Context build(Context<Object> context) {
			context.setId("carritosABM");
			
			 ContinuousSpaceFactory spaceFactory =
ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
			 ContinuousSpace<Object> pista = spaceFactory.createContinuousSpace(
					 "pista",
					 context,
					 new RandomCartesianAdder<Object>(),
					 new repast.simphony.space.continuous.WrapAroundBorders(),
					 40,
					 50);
			 
			 // Generar agentes
			 for(int i=0;i<10;i++) {
				 context.add(new Agresivo1(pista));
			 }
			 
			 return context;
			 
			 
		}
		
		
		
		
		
		
		
		
		
}
