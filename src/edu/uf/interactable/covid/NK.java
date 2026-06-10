package edu.uf.interactable.covid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uf.compartments.Voxel;
import edu.uf.interactable.Cell;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.MIP2;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.interactable.Molecule;
import edu.uf.interactable.Phagocyte;
import edu.uf.interactable.TNFa;
import edu.uf.intracellularState.BooleanNetwork;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.intracellularState.Phenotypes;
import edu.uf.time.Clock;
import edu.uf.utils.Constants;
import edu.uf.utils.Rand;
import edu.uf.utils.Util;

public class NK extends Phagocyte{
	
	public static final int RESTING = 1;
	public static final int IFN = 2;
	public static final int ACTIVE = 3;
	public static final int ACTIVE_IFN = 4;
	
	public static final String NAME = "NK";

    private static String chemokine = MIP2.NAME;
    
    private static int totalCells = 0;
    
    private int maxMoveStep;
    private boolean engaged = false;
    private int phenotype;
    
    public NK(int phenotype) {
    	NK.totalCells = NK.totalCells + 1;
    	this.clock = Clock.createClock(3);
    	this.phenotype = phenotype;
    }
    
    public static int getTotalCells() {
		return totalCells;
	}
    
	public int getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(int phenotype) {
		this.phenotype = phenotype;
	}

	@Override
	protected BooleanNetwork createNewBooleanNetwork() {
		return null;
//		return new EukaryoteSignalingNetwork() {
//			
//			static final int size = 10;
//			static final int NUM_PHENOTYPES = 3;
//			
//			private static final int NKG2D = 0;
//			private static final int ERK = 1;
//			private static final int NFAT = 2;
//			private static final int IFNg = 3;
//			private static final int IFNR = 4;
//			private static final int IFNGR = 5;
//			private static final int TRAIL = 6;
//			private static final int GRAN = 7;
//			private static final int TNFR = 8;
//			private static final int IL6R = 9;
//			private static final int TGFR = 10;
//			
//			{
//				this.inputs = new int[47];
//				this.booleanNetwork = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//			}
//			
//
//			@Override
//			public void processBooleanNetwork() {
//				if(NK.this.getClock().toc(BN_CLOCK, Constants.HALF_HOUR/Constants.TIME_STEP_SIZE)) { //convet minutes in iterations
//					int k = 0;
//					List<Integer> array = new ArrayList<>(size);
//					for(int i = 0; i < size; i++)
//						array.add(i);
//					//System.out.println();
//					while(true) {
//						if(k++ > Constants.MAX_BN_ITERATIONS)break;
//						Collections.shuffle(array, new Random());
//						for(int i : array) {
//							switch (i) {
//							case 0:
//								this.booleanNetwork[NKG2D] = and(e(this.inputs, INF_CELL_e), not(this.booleanNetwork[TGFR], 1));
//								break;
//							case 1:
//								this.booleanNetwork[ERK] = this.booleanNetwork[NKG2D];
//								break;
//							case 2:
//								this.booleanNetwork[NFAT] = this.booleanNetwork[NKG2D];
//								break;
//							case 3:
//								this.booleanNetwork[IFNg] = and(this.booleanNetwork[NFAT], not(this.booleanNetwork[TGFR], 1));
//								break;
//							case 4:
//								this.booleanNetwork[IFNGR] = this.booleanNetwork[IFNg];
//								break;
//							case 5:
//								this.booleanNetwork[TRAIL] = this.booleanNetwork[IFNGR];
//								break;
//							case 6:
//								this.booleanNetwork[GRAN] = and(this.booleanNetwork[ERK], not(this.booleanNetwork[TGFR], 1));
//								break;
//							case 7:
//								this.booleanNetwork[IFNR] = e(this.inputs, IFN_e);
//								break;
//							case 8:
//								this.booleanNetwork[TNFR] = e(this.inputs, TNFa_e);
//								break;
//							case 9:
//								this.booleanNetwork[IL6R] = e(this.inputs, IL6_e);
//								break;
//							case 10:
//								this.booleanNetwork[TGFR] = e(this.inputs, TGFb_e);
//								break;
//							default:
//								System.err.println("No such rule " + i);
//							}
//						}
//					}
//					
//					array = new ArrayList<>();
//					
//					for(int i = 0; i < NUM_RECEPTORS; i++) 
//						this.inputs[i] = 0;
//					
//					for(int i = 0; i < NUM_PHENOTYPES; i++)
//						array.add(i);
//					
//					NK.this.clearPhenotype();
//					
//					if(this.booleanNetwork[GRAN] == 1 || this.booleanNetwork[TRAIL] == 1)
//						NK.this.addPhenotype(Phenotypes.ACTIVE);
//					if(this.booleanNetwork[IFNg] == 1)
//						NK.this.addPhenotype(Phenotypes.SECRETING);
//				}
//				
//			}
//			
//		};
	}

	@Override
	public void updateStatus() {
//		this.processBooleanNetwork();
//		engaged = false;
		maxMoveStep = -1;
//		if(Rand.getRand().randunif() < Constants.NK_HALF_LIFE)
//			this.setStatus(Cell.APOPTOTIC);
	}

	@Override
	public void move(Voxel oldVoxel, int steps) {
		if(engaged)return;
        if(steps < this.getMaxMoveSteps()) {
        	
        	Util.calcDriftProbability(oldVoxel, this);
            Voxel newVoxel = Util.getVoxel(oldVoxel, Rand.getRand().randunif());
        	
            oldVoxel.removeCell(this.getId());
            //System.out.println(oldVoxel + " " + newVoxel);
            newVoxel.setCell(this);
            steps = steps + 1;
            move(newVoxel, steps);
        }
    }

	@Override
	public void die() {
//        if(this.getStatus() != NK.DEAD) {
//            this.setStatus(NK.DEAD);
//            NK.totalCells = NK.totalCells - 1;
//        }
    }

	@Override
	public void incIronPool(double ironPool) {}

	@Override
	public int getMaxMoveSteps() { 
        if(this.maxMoveStep == -1) {
        	//this.maxMoveStep = Rand.getRand().randunif() < Constants.MA_MOVE_RATE_ACT ? 1 : 0;
        	this.maxMoveStep = Rand.getRand().randpois(Constants.MA_MOVE_RATE_ACT);
        }
            //
        return this.maxMoveStep;
    }

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected boolean templateInteract(Interactable interactable, int x, int y, int z) {
		if(interactable instanceof Pneumocyte) {
			Pneumocyte cell = (Pneumocyte) interactable; 
			
			if(this.phenotype >= NK.ACTIVE) {
				if(cell.getViralLoad() > 0 && cell.getClock().toc(Cell.IT_CLOCK, Constants.CYT_BIND_T/Constants.TIME_STEP_SIZE)) {
					if(Rand.getRand().randunif() < Constants.PR_NK_KILL  && cell.isNkKillable()) {
						cell.addPhenotype(Phenotypes.APOPTOTIC);
						cell.clearViralLoad();
					} else 
						cell.setNkKillable(false);
				}
			}
			if(this.phenotype % 2 == 0) cell.setPhenotype(Pneumocyte.RESISTANT);
			return true;
		}
		
		if(interactable instanceof Macrophage) {
			Macrophage cell = (Macrophage) interactable;
			
			if(this.phenotype >= NK.ACTIVE) {
				if(cell.getViralLoad() > 0 && cell.getClock().toc(Cell.IT_CLOCK, Constants.CYT_BIND_T/Constants.TIME_STEP_SIZE)) {
					if(Rand.getRand().randunif() < Constants.PR_NK_KILL  && cell.isNkKillable()) {
						cell.setPhenotype(Macrophage.M0);
//						System.out.println(((SarsCoV2)SarsCoV2.getMolecule()).getInternalLoad());
						cell.clearViralLoad();
//						System.out.println(((SarsCoV2)SarsCoV2.getMolecule()).getInternalLoad());
					} else 
						cell.setNkKillable(false);
				}
			}
			if(this.phenotype % 2 == 0 && cell.getPhenotype() < Macrophage.AM) cell.setPhenotype(Macrophage.M1);
			return true;
		}
		
		if(interactable instanceof IL6) {
			Molecule interact = (Molecule) interactable;
			if(this.phenotype > 2)
				if(Util.activationFunction(interact.get(0, x, y, z), Constants.Kd_IL6, this.getClock()))
					this.phenotype -= 2;
			return true;
		}
		
		
		
		if(interactable instanceof IL10) {
			Molecule interact = (Molecule) interactable;
			if(this.phenotype < 3)
				if(Util.activationFunction(interact.get(0, x, y, z), Constants.Kd_IL10, this.getClock()))
					this.phenotype += 2;
			return true;
		}
		
		if(interactable instanceof TNFa) {
			Molecule interact = (Molecule) interactable;
			if(this.phenotype % 2 != 0)
				if(Util.activationFunction(interact.get(0, x, y, z), Constants.Kd_TNF, this.getClock()))
					this.phenotype++;
			return true;
		}
		
//		if(interactable instanceof DC) {
//			DC cell = (DC) interactable;
//			if(cell.getViralLoad() > 0 && cell.getClock().toc(Cell.IT_CLOCK, Constants.CYT_BIND_T/Constants.TIME_STEP_SIZE)) {
//				if(Rand.getRand().randunif() < Constants.PR_NK_KILL  && cell.isNkKillable()) {
//					cell.addPhenotype(Phenotypes.APOPTOTIC);
//					cell.clearViralLoad();
//				} else 
//					cell.setNkKillable(false);
//			}
//			return true;
//		}
			
			return interactable.interact(this, x, y, z);
	}
	
	public String attractedBy() {
        return NK.chemokine;
    }

	@Override
	public int getMaxConidia() {
		// TODO Auto-generated method stub
		return 0;
	}

}
