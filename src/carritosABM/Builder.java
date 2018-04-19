package carritosABM;

import java.util.ArrayList;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
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
					 new repast.simphony.space.continuous.BouncyBorders(),
					 40,
					 50);
			 
			 // Generar agentes
			 Parameters params = RunEnvironment.getInstance().getParameters();
			 ArrayList<Carro> todoLosCarros = new  ArrayList<Carro>();
			 for(int i=0;i<params.getInteger("numCarrosAgresivo");i++) {
				 Carro esteCarro = new Agresivo1(pista);
				 context.add(esteCarro);
				 todoLosCarros.add(esteCarro);
			 }
			 for(int i=0;i<params.getInteger("numCarrosEvasivo");i++) {
				 Carro esteCarro = new Evasivo1(pista);
				 context.add(esteCarro);
				 todoLosCarros.add(esteCarro);
			 }
			 
			 // Generamos la red de amigos
			 double probabilidadAmigos = params.getDouble("probAmigos");
			 for(Carro buscador:todoLosCarros) {
				 for(Carro candidato:todoLosCarros) {
					 double randomNumber = RandomHelper.nextDoubleFromTo(0, 1);
					 if(randomNumber<probabilidadAmigos && buscador.red.containsKey(candidato)==false && buscador!=candidato) {
						 buscador.red.put(candidato, 1);
						 candidato.red.put(buscador, 1);
					 }
				 }
			 }
			 
				 
			 
			 
			 
			 
			 
			 
			 
			 
			 Carro.acceleracion = params.getDouble("aceleracion");
			 Carro.vmax			= params.getDouble("vmax");
			 Carro.maxangle		= params.getDouble("anguloMax");
			 
			 return context;
			 
			 
		}
		
		
		
		
		
		
		
		
		
}
