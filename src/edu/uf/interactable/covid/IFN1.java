package edu.uf.interactable.covid;



import edu.uf.Diffusion.Diffuse;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.interactable.Molecule;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.utils.Constants;
import edu.uf.utils.Util;

public class IFN1 extends Molecule{
	public static final String NAME = "IFN_I";
	public static final int NUM_STATES = 1;
	public static final int MOL_IDX = getReceptors(); 
//	Indexes.IFN_e;
	
	private static IFN1 molecule = null;
    
    protected IFN1(double[][][][] qttys, Diffuse diffuse) {
		super(qttys, diffuse);
	}
    
    public static IFN1 getMolecule(double[][][][] values, Diffuse diffuse) {
    	if(molecule == null) {
    		molecule = new IFN1(values, diffuse);
    	}
    	return molecule;
    }
    
    public static Molecule getMolecule() {
    	return molecule;
    }
    
    public void degrade() {
    	degrade(Constants.IFN1_HALF_LIFE, 0);
    }

    public int getIndex(String str) {
        return 0;
    }

    public void computeTotalMolecule(int x, int y, int z) {
    	this.totalMoleculesAux[0] = this.totalMoleculesAux[0] + this.get(0, x, y, z);
    }

    protected boolean templateInteract(Interactable interactable, int x, int y, int z) {
    	
        if(interactable instanceof Macrophage) {
//        	System.out.println(Constants.MA_IFN_QTTY);
        	Macrophage cell = (Macrophage) interactable;
	        if (Util.activationFunction(this.get(0, x, y, z), Constants.Kd_IFNG, cell.getClock())) {
	        	if (cell.getPhenotype() == Macrophage.M2) cell.setPhenotype(Macrophage.M1);
	        	cell.setIfn(true);
	        }
//	        	cell.setPhenotype(Macrophage.M1);
//	        	
//	        System.out.println(Constants.MA_IFN_QTTY*cell.getMul());
	        
//	        System.out.println(((cell.getPhenotype() == Macrophage.M1 && cell.getViralLoad() > 0) || (cell.getPhenotype() == Macrophage.AM && cell.getViralLoad() > 0)));
	        if ((cell.getPhenotype() == Macrophage.M1 && cell.getViralLoad() > 0) || (cell.getPhenotype() == Macrophage.AM && cell.getViralLoad() > 0)){//# and interactable.state == Neutrophil.INTERACTING:
        		this.inc(Constants.MA_IFN_QTTY*cell.getMul(), 0, x, y, z);
//        		System.out.println(Constants.MA_IFN_QTTY + " " + cell.getMul());
	        }
	        
//	        System.out.println(this.getTotalMolecule(0));

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
