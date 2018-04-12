package carritosABM;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;

public class Agresivo1 extends Carro {

	public Agresivo1(ContinuousSpace space) { 
		super(space);
	}

	@Override
	public void decideMove() {
		
		// Velocidad
		this.velocidad = Math.min(this.velocidad+acceleracion, vmax);
		
		// Orientacion
		
			// Buscar el vecino mas cercano
			Carro vecino  = buscarVecinoMasCercano();
			
			// Definimos el vecino mas cercano como la victima
			this.victima = vecino;
			
			
			double angulo = SpatialMath.calcAngleFor2DMovement(pista,pista.getLocation(this) ,pista.getLocation(vecino));
			angulo = Math.toDegrees(angulo);
			
			double diff = Math.abs(this.orientacion - angulo);
			
			// Evitar angulos de mas de 180 grados
			if(diff>180) { 
				diff = 360-diff;
			}
			
			// Ver si es factible llegar al angulo deseado
			if(diff<=Carro.maxangle) {
				this.orientacion = angulo;
			}
			else { 
				double diff2 = angulo - this.orientacion;
				
				if( diff2>180 | (diff2<0 & diff2>-180)) { // girar a la derecha
					this.orientacion -= Carro.maxangle;
				}
				else { // girar a la izquierda 
					this.orientacion += Carro.maxangle;
				}
				
			}
			
			if(this.orientacion<0) {
				this.orientacion +=360;
			}
			else if(this.orientacion>360) { 
				this.orientacion -=360;
			}
			
	}
	
	
	
}






