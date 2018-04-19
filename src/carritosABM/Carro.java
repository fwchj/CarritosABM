package carritosABM;

import java.util.HashMap;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;


public abstract class Carro {
	double 			velocidad;
	static double 	vmax;
	static double 	acceleracion;
	boolean 		agresivo;
	double 			orientacion;
	double 			puntos;
	static double 	maxangle;
	Carro			victima;
	Carro 			ultimoChoque;
	int 			ultimoControlChoque;
	HashMap<Carro,Integer> red = new HashMap<Carro,Integer>();
	
	public ContinuousSpace pista;
	
	
	public Carro(ContinuousSpace space) {
		this.pista = space;
		this.orientacion = RandomHelper.nextDoubleFromTo(0, 360);
	}
	
	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=100)
	public void stepDecision() {
		
		this.decideMove();
		
	}
	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=90)
	public void stepMoverse() {
		this.pista.moveByVector(this, this.velocidad, Math.toRadians(this.orientacion),0);
		
	}
	
	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=80)
	public void crash() {
		
		if(this.ultimoControlChoque < RunEnvironment.getInstance().getCurrentSchedule().getTickCount()) {
			// Buscamos al carrito mas cercano y vemos si es un choque
			Carro vecino = buscarVecinoMasCercano();
			
			// Vemos si es un choque
			double distancia = pista.getDistance(pista.getLocation(this),pista.getLocation(vecino));
			if(distancia<1) { 
				
				// Copiamos la velocidad para su uso posterior
				double velocidadVecino 	= vecino.velocidad;
				double velocidadThis 	= this.velocidad;
				
				
				this.postCrash(vecino,velocidadVecino);
				vecino.postCrash(this, velocidadThis);
				
				
			}
		} // end if: no se hizo el analisis antes
		
		
		
		
		
	}

	private void postCrash(Carro vecino, double velocidadVecino) {
		// Puntos
		double puntosRonda = 0.0;
		if(this.agresivo == true) {
			if(this.victima!=null && vecino == this.victima) { // caso de exito: chocar con la victima
				puntosRonda = this.velocidad;
				if(vecino.victima==this) { // mitad de los puntos si los dos querian chocar
					puntosRonda = 0.5*this.velocidad;
				}
			}
			// else no es necesario, porque damos 0 puntos
			
			
			// Das la mitad de los puntos si ya choco con esa persona
			if(this.ultimoChoque!=null && vecino == this.ultimoChoque) {
				puntosRonda *=0.5; // castigo por chocar con el mismo
			}
			
			
			
		}
		else { // caso del no-agresivo/evasivo
			puntosRonda = -velocidadVecino;
		}
		
		
		// Ahora asignamos los puntos a la variable de instancia
		this.puntos += puntosRonda;
		
		//Metodo para describir lo que pasa despues de un choque (version sencilla)
		this.velocidad = 0.0;
		this.orientacion = RandomHelper.nextDoubleFromTo(0, 360);
		
		// Actualizamos el utlimo control de choques
		this.ultimoControlChoque = (int) RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
		this.ultimoChoque = vecino;
		
		
		
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
	
	
	
	public double getPuntos() {
		return this.puntos;
	}
	
	
	public String getLabel() {
		String resultado = String.format("%3.0f | %3.2f", this.orientacion,this.velocidad);
		return resultado;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
