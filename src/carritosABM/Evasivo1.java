package carritosABM;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;

	public class Evasivo1 extends Carro {

	public Evasivo1(ContinuousSpace space) { 
		super(space);
		this.agresivo = false;
		this.nameStrategy ="Evasivo1";
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
			// Agregamos 180 grados para huir del vecino
			angulo +=180;
			if(angulo>360) { // para asegurarnos de no tener mas de 360 grados
				angulo -=360;
						}
			
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
