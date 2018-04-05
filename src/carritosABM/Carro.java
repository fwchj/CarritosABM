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
	HashMap<Carro,Integer> red;
	
	public ContinuousSpace pista;
	
	
	public Carro(ContinuousSpace space) {
		this.pista = space;
	}
	
	@ScheduledMethod(start=1,interval=1,priority=100,shuffle=true)
	abstract void stepMove() ;
	
	

}
