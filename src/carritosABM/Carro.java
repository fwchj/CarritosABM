package carritosABM;

import java.util.HashMap;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;


public abstract class Carro {
	double 			velocidad;
	static double 	vmax;
	static double 	acceleracion;
	boolean 		agresivo;
	double 			orientacion;
	double 			puntos;
	static double 	maxangle = 45;
	HashMap<Carro,Integer> red;
	
	public ContinuousSpace pista;
	
	
	public Carro(ContinuousSpace space) {
		this.pista = space;
	}
	
	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=100)
	public void stepDecision() {
		
		this.decideMove();
		
	}

	public abstract void decideMove();
	

	
	public Carro buscarVecinoMasCercano() {
		Carro vecino = null;
		double distanciaMinima = Double.MAX_VALUE;
		for(Object o:this.pista.getObjects()) {
			// Calculamos la distancia con el agente actual
			double distancia = pista.getDistance(pista.getLocation(this),pista.getLocation(o));
			
			// Ver si es mas cercano
			if(distancia <=distanciaMinima && this!=o) { 
				distanciaMinima = distancia;
				vecino = (Carro)o;
			}
		}
		return vecino;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
