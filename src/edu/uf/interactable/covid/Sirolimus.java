package edu.uf.interactable.covid;



import edu.uf.Diffusion.Diffuse;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.interactable.Molecule;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.utils.Constants;
import edu.uf.utils.Util;

public class Sirolimus extends Molecule{
    
	public static final String NAME = "Sirolimus";
	public static final int NUM_STATES = 1;
	public static final int MOL_IDX = getReceptors();
	
	private static Sirolimus molecule = null;    
    
    private Sirolimus(double[][][][] qttys, Diffuse diffuse) {
		super(qttys, diffuse);
	}
    
    public static Sirolimus getMolecule(double[][][][] values, Diffuse diffuse) {
    	if(molecule == null) {
    		molecule = new Sirolimus(values, diffuse);
    	}
    	return molecule;
    }
    
    public static Sirolimus getMolecule() {
    	return molecule;
    }
    
    public void degrade() {
    	
    }
    
    public void turnOver(int x, int y, int z) {
    	
    }

    protected void degrade(double p, int x) {
        
    }
    
    public int getIndex(String str) {
        return 0;
    }

    public void computeTotalMolecule(int x, int y, int z) {
    	this.totalMoleculesAux[0] = this.totalMoleculesAux[0] + this.get(0, x, y, z);
    }

    protected boolean templateInteract(Interactable interactable, int x, int y, int z) {
    		
    	if(interactable instanceof NK) {
			NK interact = (NK) interactable;
			if(interact.getPhenotype() > 2)
				if(Util.activationFunction(this.get(0, x, y, z), Constants.Kd_Sirolimus, interact.getClock()))
					interact.setPhenotype(interact.getPhenotype() - 2);
			return true;
		}
    	
    	if(interactable instanceof Pneumocyte) {
			Pneumocyte interact = (Pneumocyte) interactable;
			double activation = 1 - Util.activationFunction(this.get(0, x, y, z), Constants.Kd_Sirolimus , 1, 1);
			interact.setMul(activation); 
			interact.setSirolimus(true);
//			System.out.println(activation + " " + this.get(0, x, y, z) + " " + Constants.Kd_Sirolimus);
			
			return true;
		}
    	
    	if(interactable instanceof Macrophage) {
			Macrophage interact = (Macrophage) interactable;
			double activation = 1 - Util.activationFunction(this.get(0, x, y, z), Constants.Kd_Sirolimus , 1, 1);
//			System.out.println(activation);
			interact.setMul(activation); 
			interact.setSirolimus(true); 
			return true;
		}
    	
    	
        return interactable.interact(this, x, y, z);
    }

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public double getThreshold() {
		return -1;
	}

	@Override
	public int getNumState() {
		return NUM_STATES;
	}
}
