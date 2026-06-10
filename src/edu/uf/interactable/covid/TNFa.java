package edu.uf.interactable.covid;



import edu.uf.Diffusion.Diffuse;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.interactable.Molecule;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.utils.Constants;
import edu.uf.utils.Util;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.utils.Constants;
import edu.uf.utils.Util;

public class TNFa extends Molecule{

	public static final String NAME = "TNFa";
	public static final int NUM_STATES = 1;
	public static final int MOL_IDX = getReceptors();
	
	private static TNFa molecule = null;
    
    private TNFa(double[][][][] qttys, Diffuse diffuse) {
		super(qttys, diffuse);
	}
    
    public static TNFa getMolecule(double[][][][] values, Diffuse diffuse) {
    	if(molecule == null) {
    		molecule = new TNFa(values, diffuse);
    	}
    	return molecule;
    }
    
    public static TNFa getMolecule() {
    	return molecule;
    }
    
    public void degrade() {
    	degrade(Constants.TNF_HALF_LIFE, 0);
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
		return NAME;
	}

	@Override
	public double getThreshold() {
		return 0;
	}

	@Override
	public int getNumState() {
		return NUM_STATES;
	}
}
