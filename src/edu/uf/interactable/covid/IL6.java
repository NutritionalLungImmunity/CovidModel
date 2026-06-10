package edu.uf.interactable.covid;



import edu.uf.Diffusion.Diffuse;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.interactable.Molecule;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.utils.Constants;
import edu.uf.utils.Util;

public class IL6 extends Molecule{
    

	public static final String NAME = "IL6";
	public static final int NUM_STATES = 1;
	public static final int MOL_IDX = getReceptors();
	
	private static IL6 molecule = null;
    
    protected IL6(double[][][][] qttys, Diffuse diffuse) {
		super(qttys, diffuse);
	}
    
    public static IL6 getMolecule(double[][][][] values, Diffuse diffuse) {
    	if(molecule == null) {
    		molecule = new IL6(values, diffuse);
    	}
    	return molecule;
    }
    
    public static Molecule getMolecule() {
    	return molecule;
    }
    
    public void degrade() {
    	degrade(Constants.IL6_HALF_LIFE, 0);
    }

    public int getIndex(String str) {
        return 0;
    }

    public void computeTotalMolecule(int x, int y, int z) {
    	this.totalMoleculesAux[0] = this.totalMoleculesAux[0] + this.get(0, x, y, z);
    }

    protected boolean templateInteract(Interactable interactable, int x, int y, int z) {


        return interactable.interact(this, x, y, z);
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}

	@Override
	public double getThreshold() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getNumState() {
		return NUM_STATES;
	}

}
