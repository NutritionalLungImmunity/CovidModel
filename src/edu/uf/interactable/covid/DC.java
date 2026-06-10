package edu.uf.interactable.covid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uf.interactable.Interactable;
import edu.uf.interactable.MIP2;
import edu.uf.interactable.Molecule;
import edu.uf.interactable.Phagocyte;
import edu.uf.intracellularState.BooleanNetwork;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.intracellularState.Phenotypes;
import edu.uf.time.Clock;
import edu.uf.utils.Constants;
import edu.uf.utils.Rand;
import edu.uf.utils.Util;

public class DC extends Phagocyte{
    public static final String NAME = "DC";

    private static String chemokine = MIP2.NAME;
    
    private static int totalCells = 0;
    
    private int maxMoveStep;
    private int viralLoad;
	
    
	public DC() {
    	DC.totalCells = DC.totalCells + 1;
        this.maxMoveStep = -1; 
        this.clock = Clock.createClock(3);
    }
	
	public void incViralLoad(int virus)  {
		this.viralLoad += virus;
		((SarsCoV2) SarsCoV2.getMolecule()).incInternalLoad(virus);
	}
	
	public int getViralLoad() {
		return this.viralLoad;
	}
	
	public void clearViralLoad() {
		((SarsCoV2) SarsCoV2.getMolecule()).incInternalLoad(-viralLoad);
		this.viralLoad = 0;
	}
	
    public static String getChemokine() {
		return chemokine;
	}

	public static void setChemokine(String chemokine) {
		DC.chemokine = chemokine;
	}

	public static int getTotalCells() {
		return totalCells;
	}

	public static void setTotalCells(int totalCells) {
		DC.totalCells = totalCells;
	}

    public int getMaxMoveSteps() { 
        return 0;
    }

    protected boolean templateInteract(Interactable interactable, int x, int y, int z) {
//    	EukaryoteSignalingNetwork.IFNG_e = IFN1.MOL_IDX;
    	if(interactable instanceof IFN1  ) {
    		Molecule mol = (Molecule) interactable;
	        if (this.getViralLoad() > 0)//# and interactable.state == Neutrophil.INTERACTING:
	        	mol.inc(Constants.MA_IFN_QTTY, 0, x, y, z);
    		return true;
    	}
//    	EukaryoteSignalingNetwork.TLR4_o.add(SarsCoV2.MOL_IDX);
//    	if(interactable instanceof SarsCoV2) {
//    		Molecule mol = (Molecule) interactable;
//    		//mol.pdec(1-Constants.SarsCoV2_HALF_LIFE, 0, x, y, z);
//	        if (Util.activationFunction(mol.get(0, x, y, z)*10000, Constants.Kd_SarsCoV2, this.getClock())) {//viral particle per infectious unity
//	        	this.bind(SarsCoV2.MOL_IDX);
//	        	/*double qtty = Constants.SarsCoV2_UPTAKE_QTTY > this.get(0, x, y, z) ? this.get(0, x, y, z) : Constants.SarsCoV2_UPTAKE_QTTY;
//	        	double q = this.get(0, x, y, z);
//	        	this.dec(qtty, 0, x, y, z);*/
//	        }
//    		return true;
//    	}
        return interactable.interact(this, x, y, z);
    }


    public void updateStatus() {
    	if(this.getStatus() == DEAD)return;
    	this.interactNeighbours();
    	if(this.inPhenotype(new int[] {Phenotypes.APOPTOTIC})) 
			this.die();
    }
    
    private void interactNeighbours() {
		NetworkingCell.interactNeighbours(this);
	}

    public void die() {
        if(this.getStatus() != DC.DEAD) {
            this.setStatus(DC.DEAD);
            DC.totalCells = DC.totalCells - 1;
        }
    }

    public String attractedBy() {
        return DC.chemokine;
    }

	@Override
	public int getMaxConidia() {
		return Constants.MA_MAX_CONIDIA;
	}
	
	public String getName() {
    	return NAME;
    }

	@Override
	protected BooleanNetwork createNewBooleanNetwork() {
//		EukaryoteSignalingNetwork network = new EukaryoteSignalingNetwork() {
//
//			public static final int size = 1;
//			//public static final int NUM_RECEPTORS = 12;
//			
//
//			public static final int STAT1 = 0;
//
//			
//			//public static final int FNP = 28; //IN THE OUTER CLASS!
//			
//			{
//				this.inputs = new int[47];
//				this.booleanNetwork = new int[size];
//			}
//			
//			
//			
//			@Override
//			public void processBooleanNetwork() {
//				if(DC.this.getClock().toc(BN_CLOCK, Constants.HALF_HOUR/Constants.TIME_STEP_SIZE)) { //convet minutes in iterations
//
//					this.booleanNetwork[STAT1] = o(this.inputs, TLR4_o);
//					
//					}
////				System.out.println(Arrays.toString(this.booleanNetwork));
//					for(int i = 0; i < NUM_RECEPTORS; i++)
//						this.inputs[i] = 0;
//					
//					DC.this.clearPhenotype();
//					
//					if(this.booleanNetwork[STAT1] == 1)
//						DC.this.addPhenotype(Phenotypes.ACTIVE);
//					else
//						DC.this.addPhenotype(Phenotypes.RESTING);
//					
//				}
//				
//			
//		};
//		
//		return network;
		return null;
	}

	@Override
	public void incIronPool(double ironPool) {
		// TODO Auto-generated method stub
		
	}
}
