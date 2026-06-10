package edu.uf.interactable.covid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.uf.compartments.Voxel;
import edu.uf.interactable.Afumigatus;
import edu.uf.interactable.Cell;
import edu.uf.interactable.covid.IL6;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.Molecule;
import edu.uf.interactable.TGFb;
import edu.uf.interactable.covid.TNFa;
import edu.uf.intracellularState.BooleanNetwork;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.intracellularState.Phenotypes;
import edu.uf.time.Clock;
import edu.uf.utils.Constants;
import edu.uf.utils.Rand;
import edu.uf.utils.Util;

public class Macrophage extends edu.uf.interactable.Macrophage{

	public static final int M0 = 0;
	public static final int M1 = 1;
	public static final int M2 = 2;
	public static final int AM = 3;
	public static final int IM = 4;
	
    private static String chemokine = null;
    
    private static int totalCells = 0;
    private static double totalIron = 0; 
    
    public static final int FPN = 28;
    
    private int maxMoveStep;
    private boolean engaged;
    private int viralLoad;
    private int phenotype;
	private boolean ifn;
	private double mul = 1;

	private boolean sirolimus;
	
	public boolean isSirolimus() {
		return sirolimus;
	}



	public void setSirolimus(boolean sirolimus) {
		this.sirolimus = sirolimus;
	}

	public double getMul() {
		return mul;
	}

	public void setMul(double mul) {
		this.mul = mul;
	}


	public boolean isIfn() {
		return ifn;
	}

	public void setIfn(boolean ifn) {
		this.ifn = ifn;
	}

	public Macrophage(int phenotype) {
    	super(0.0);
        Macrophage.totalCells = Macrophage.totalCells + 1;
        this.setState(Macrophage.FREE);
        this.maxMoveStep = -1; 
        this.engaged = false; //### CHANGE HERE!!!
        this.clock = Clock.createClock(3);
        this.phenotype = phenotype;
    }
    
	public int getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(int phenotype) {
		this.phenotype = phenotype;
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
		Macrophage.chemokine = chemokine;
	}

	public static int getTotalCells() {
		return totalCells;
	}

	public static void setTotalCells(int totalCells) {
		Macrophage.totalCells = totalCells;
	}

	public static double getTotalIron() {
		return totalIron;
	}

	public static void setTotalIron(double totalIron) {
		Macrophage.totalIron = totalIron;
	}

	public boolean isEngaged() {
		return engaged;
	}

	public void setEngaged(boolean engaged) {
		this.engaged = engaged;
	}

    public int getMaxMoveSteps() { 
        return 0;
    }

    protected boolean templateInteract(Interactable interactable, int x, int y, int z) {
//    	EukaryoteSignalingNetwork.TNF_e = TNF_e.MOL_IDX;
//    	EukaryoteSignalingNetwork.IL10_e = IL10_e.MOL_IDX;
    	boolean isInfected = this.getViralLoad() > 0;
    	if (interactable instanceof IL6) {
            Molecule interact = (Molecule) interactable;
//            System.out.println(interact.getTotalMolecule(0));
//            System.out.println(((this.phenotype == M1 ) || (this.phenotype == AM )));

        	if((this.phenotype == M1 && isInfected) || (this.phenotype == AM && isInfected))
            	interact.inc(Constants.MA_IL6_QTTY*mul, 0, x, y, z);
            
        	return true;
    	}
    	if (interactable instanceof TNFa) {
            Molecule interact = (Molecule) interactable;
//            System.out.println(interact.getTotalMolecule(0));
//            System.out.println(((this.phenotype == M1 ) || (this.phenotype == AM )));
        	if((this.phenotype == M1 && isInfected) || (this.phenotype == AM && isInfected)) {
            	interact.inc(Constants.MA_TNF_QTTY*mul, 0, x, y, z);
//        		System.out.println(Constants.MA_TNF_QTTY*mul);
        	}
        	
//        	System.out.print(this.phenotype);
//        	System.out.println(((this.phenotype < AM) && (Util.activationFunction(interact.get(0, x, y, z), Constants.Kd_TNF, this.getClock()))));
        	if ((this.phenotype < AM) && (Util.activationFunction(interact.get(0, x, y, z), Constants.Kd_TNF, this.getClock())))
	        	this.phenotype = M1;
//        		System.out.println("blah");
            return true;
    	}
    	if (interactable instanceof IL10) {
            Molecule interact = (Molecule) interactable;
//            if(isInfected)System.out.println(this.phenotype );
        	if((this.phenotype == M2 && isInfected) || (this.phenotype == IM) || (this.phenotype == AM && isInfected))
            	interact.inc(Constants.MA_IL10_QTTY*mul, 0, x, y, z);
//        		System.out.println(Constants.MA_IL10_QTTY*mul);
//        	
//        	System.out.println(interact.getTotalMolecule(0));
        	if ((this.phenotype < AM) && (Util.activationFunction(interact.get(0, x, y, z), Constants.Kd_IL10, this.getClock())))
        		this.phenotype = M2;
            return true;
    	}
        return interactable.interact(this, x, y, z);
    }
    
    public void move(Voxel oldVoxel, int steps) {}


    public void updateStatus() {
    	if(this.getStatus() == DEAD)return;
//    	this.processBooleanNetwork();
    	
    	
    	double repRate = ifn ? Constants.SarsCoV2_IFN_REP_RATE : Constants.SarsCoV2_REP_RATE;
    	
    	repRate *= mul;
    	
    	if( (this.phenotype == M1 || this.phenotype == AM)  && this.viralLoad > 0) {//https://doi.org/10.1038/s41392-022-01106-8
			double avg = this.viralLoad*repRate*(1-this.viralLoad/Constants.MAX_VIRAL_LOAD);
			int qtty = Util.createVirus(avg, Constants.SarsCoV2_EPS, this.viralLoad);
			this.viralLoad += qtty;
			((SarsCoV2) SarsCoV2.getMolecule()).incInternalLoad(qtty);
		}
    	
    	this.interactNeighbours();
    	if(this.inPhenotype(new int[] {Phenotypes.APOPTOTIC})) 
			this.die();
    	this.setSirolimus(false);
    }	
    
    private void interactNeighbours() {
		NetworkingCell.interactNeighbours(this);
	}    

    public void incIronPool(double qtty) {
        this.setIronPool(this.getIronPool() + qtty);
        Macrophage.totalIron = Macrophage.totalIron + qtty;
    }

    public void die() {
        if(this.getStatus() != Macrophage.DEAD) {
            this.setStatus(Macrophage.DEAD);
            Macrophage.totalCells = Macrophage.totalCells - 1;
        }
    }

    public String attractedBy() {
        return Macrophage.chemokine;
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
//			public static final int size = 24;
//			//public static final int NUM_RECEPTORS = 12;
//			
//			public static final int IFNGR = 0;
//			public static final int IFNB = 1;
//			public static final int CSF2Ra = 2;
//			public static final int IL1R = 3;
//			public static final int IL1B = 4;
//			public static final int TLR4 = 5;
//			public static final int FCGR = 6;
//			public static final int IL4Ra = 7;
//			public static final int IL10R = 8;
//			public static final int IL10_out = 9;
//			public static final int STAT1 = 10;
//			public static final int SOCS1 = 11;
//			public static final int STAT3 = 12;
//			public static final int STAT5 = 13;
//			public static final int IRF4 = 14;
//			public static final int NFkB = 15;
//			public static final int PPARG = 16;
//			public static final int KLF4 = 17;
//			public static final int STAT6 = 18;
//			public static final int JMJD3 = 19;
//			public static final int IRF3 = 20;
//			public static final int ERK = 21;
//			public static final int IL12_out = 22;
//			public static final int TNFR = 23;
////			public static final int Dectin = 24;
////			public static final int ALK5 = 25;
////			public static final int SMAD2 = 26;
////			public static final int PtSR = 27;
//			
//			//public static final int FNP = 28; //IN THE OUTER CLASS!
//			
//			{
//				this.inputs = new int[47]; //new int[NUM_RECEPTORS];
////				this.booleanNetwork = new int[size];
//				this.booleanNetwork = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
////				this.booleanNetwork = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
//			}
//			
//			
//			
//			
//			@Override
//			public void processBooleanNetwork() {
//				if(Macrophage.this.getClock().toc(BN_CLOCK, (int) Constants.INV_UNIT_T)) { //convet minutes in iterations
//					int k = 0;
//					List<Integer> array = new ArrayList<>(size);
//					for(int i = 0; i < size; i++)
//						array.add(i);
//					while(true) {
//						if(k++ > Constants.MAX_BN_ITERATIONS)break;
//						Collections.shuffle(array, new Random());
//						for(int i : array) {
//							switch(i) {
//								case 0:
//									this.booleanNetwork[IFNGR] = this.booleanNetwork[IFNB] | e(this.inputs, IFNG_e);
//									break;
//								case 1:
//									this.booleanNetwork[IFNB] = this.booleanNetwork[IRF3];
//									break;
//								case 2:
//									this.booleanNetwork[CSF2Ra] = e(this.inputs, GM_CSF_e);
//									break;
//								case 3:
//									this.booleanNetwork[IL1R] = this.booleanNetwork[IL1B] | e(this.inputs, IL1B_e);
//									break;
//								case 4:
//									this.booleanNetwork[IL1B] = this.booleanNetwork[NFkB];
//									break;
//								case 5:
//									/*this.booleanNetwork[TLR4] = (e(this.inputs, LPS_e) | (e(this.inputs, Heme_e) & 
//											(-e(this.inputs, Hpx_e) + 1)) | e(this.inputs, DAMP_e) | e(this.inputs, VIRUS_e)) & 
//											(-this.booleanNetwork[FCGR] + 1);*/
//									this.booleanNetwork[TLR4] = (o(this.inputs, TLR4_o) | //e(this.inputs, LPS_e) | 
//											(e(this.inputs, Heme_e) & (-e(this.inputs, Hpx_e) + 1))) & (-this.booleanNetwork[FCGR] + 1);
//									break;
//								case 6:
//									this.booleanNetwork[FCGR] = (e(this.inputs, IC_e) & e(this.inputs, LPS_e)) | (e(this.inputs, IC_e) & e(this.inputs, IL1B_e));
//									break;
//								case 7:
//									this.booleanNetwork[IL4Ra] = e(this.inputs, IL4_e);
//									break;
//								case 8:
//									this.booleanNetwork[IL10R] = e(this.inputs, IL10_e) | this.booleanNetwork[IL10_out];
//									break;
//								case 9: 
//									this.booleanNetwork[IL10_out] = this.booleanNetwork[PPARG] | this.booleanNetwork[STAT6] | this.booleanNetwork[JMJD3] | 
//										this.booleanNetwork[STAT3] | this.booleanNetwork[ERK] ;
//									break;
//								case 10:
//									this.booleanNetwork[STAT1] = this.booleanNetwork[IFNGR] & (-(this.booleanNetwork[SOCS1] | this.booleanNetwork[STAT3]) + 1);
//									break;
//								case 11:
//									this.booleanNetwork[SOCS1] = this.booleanNetwork[STAT6];
//									break;
//								case 12:
//									this.booleanNetwork[STAT3] = (this.booleanNetwork[IL10R]) & (
//											-(this.booleanNetwork[FCGR] | this.booleanNetwork[PPARG]) + 1
//									);
//									break;
//								case 13:
//									this.booleanNetwork[STAT5] = this.booleanNetwork[CSF2Ra] & (-(this.booleanNetwork[STAT3] | this.booleanNetwork[IRF4]) + 1);
//									break;
//								case 14:
//									this.booleanNetwork[IRF4] = this.booleanNetwork[JMJD3];
//									break;
//								case 15:
//									this.booleanNetwork[NFkB] = (this.booleanNetwork[IL1R] | this.booleanNetwork[TLR4] | 
//											 this.booleanNetwork[TNFR]) & (
//											-(this.booleanNetwork[STAT3] | this.booleanNetwork[FCGR] | this.booleanNetwork[PPARG] | this.booleanNetwork[KLF4])
//									+ 1);
//									break;
//								case 16:
//									this.booleanNetwork[PPARG] = this.booleanNetwork[IL4Ra];
//									break;
//								case 17:
//									this.booleanNetwork[KLF4] = this.booleanNetwork[STAT6];
//									break;
//								case 18:
//									this.booleanNetwork[STAT6] = this.booleanNetwork[IL4Ra];
//									break;
//								case 19:
//									this.booleanNetwork[JMJD3] = this.booleanNetwork[IL4Ra];
//									break;
//								case 20:
//									this.booleanNetwork[IRF3] = this.booleanNetwork[TLR4];
//									break;
//								case 21:
//									this.booleanNetwork[ERK] = this.booleanNetwork[FCGR];// | this.booleanNetwork[Dectin];
//									break;
//								case 22:
//									this.booleanNetwork[IL12_out] = this.booleanNetwork[STAT1] | this.booleanNetwork[STAT5] | this.booleanNetwork[NFkB];
//									break;
//								case 23:
//									this.booleanNetwork[TNFR] = e(this.inputs, TNFa_e);
//									break;
//								default:
//									System.err.println("No such interaction " + i + "!");
//									break;
//							}
//						}
//					}
//					
////					System.out.println(Arrays.toString(this.booleanNetwork));
////					System.out.println((this.booleanNetwork[IL10R]));
//					
//					for(int i = 0; i < NUM_RECEPTORS; i++)
//						this.inputs[i] = 0;
//					
//					Macrophage.this.clearPhenotype();
//					
//					if(this.booleanNetwork[NFkB] == 1 || this.booleanNetwork[STAT1] == 1 || this.booleanNetwork[STAT5] == 1)
//						Macrophage.this.addPhenotype(Phenotypes.ACTIVE); //this is M1
//					else if(this.booleanNetwork[STAT6] == 1)
//						Macrophage.this.addPhenotype(Phenotypes.ALT_ACTIVE); //this is M2a
//					else if(this.booleanNetwork[ERK] == 1)
//						Macrophage.this.addPhenotype(Phenotypes.MIX_ACTIVE); //this is M2b
//					else if(this.booleanNetwork[STAT3] == 1)
//						Macrophage.this.addPhenotype(Phenotypes.INACTIVE); //this is M2c
//					else
//						Macrophage.this.addPhenotype(Phenotypes.RESTING);
////					System.out.println(Macrophage.this.inPhenotype(new int[]{Phenotypes.ALT_ACTIVE, Phenotypes.INACTIVE}));
//					
//				}
//				
//			}
//			
//		};
//		
//		return network;
		return null;
	}
	
}
