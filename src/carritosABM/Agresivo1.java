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
			
			
	}
	
	
	
}






