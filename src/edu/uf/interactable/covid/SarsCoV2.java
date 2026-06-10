package edu.uf.interactable.covid;

import edu.uf.Diffusion.Diffuse;
import edu.uf.interactable.Cell;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.Lactoferrin;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.interactable.Molecule;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.intracellularState.Phenotypes;
import edu.uf.utils.Constants;
import edu.uf.utils.Rand;
import edu.uf.utils.Util;

public class SarsCoV2 extends Molecule{
	public static final String NAME = "SarsCoV2";
	public static final int NUM_STATES = 1;
	public static final int MOL_IDX = getReceptors();
	private double internalLoad = 0.0;
	private double externalLoad = 0.0;
	
	private static SarsCoV2 molecule = null;
    
    public void incExternalLoad(double externalLoad) {
		this.externalLoad += externalLoad;
	}

	public double getExternalLoad() {
		return externalLoad;
	}

	public static void setMolecule(SarsCoV2 molecule) {
		SarsCoV2.molecule = molecule;
	}

	protected SarsCoV2(double[][][][] qttys, Diffuse diffuse) {
		super(qttys, diffuse);
	}
    
    public static SarsCoV2 getMolecule(double[][][][] values, Diffuse diffuse) {
    	if(molecule == null) {
    		molecule = new SarsCoV2(values, diffuse);
    	}
    	return molecule;
    }
    
    public static Molecule getMolecule() {
    	return molecule;
    }
    
    public void turnOver(int x, int y, int z) {
    	this.pdec(1-Constants.SarsCoV2_HALF_LIFE, 0, x, y, z);
    }
    
    public void degrade() {
    	degrade(Constants.SarsCoV2_HALF_LIFE, 0);
    }

    public int getIndex(String str) {
        return 0;
    }

    public void computeTotalMolecule(int x, int y, int z) {
    	this.totalMoleculesAux[0] = this.totalMoleculesAux[0] + this.get(0, x, y, z);
    }
    
    public double getInternalLoad() {
    	return this.internalLoad;
    }
    
    public void incInternalLoad(double inc) {
    	this.internalLoad += inc;
    }

    protected boolean templateInteract(Interactable interactable, int x, int y, int z) {
    	
//    	EukaryoteSignalingNetwork.TLR4_o.add(SarsCoV2.MOL_IDX);
//        if(interactable instanceof Pneumocyte) {
//        	Pneumocyte cell = (Pneumocyte) interactable;
//        	if (Util.activationFunction(this.get(0, x, y, z), Constants.Kd_SarsCoV2, cell.getClock())) {//TLR
//        		EukaryoteSignalingNetwork.VIRUS_e = -1;
//	        	cell.bind(SarsCoV2.MOL_IDX);
//        	}
//        	if (Util.activationFunction(this.get(0, x, y, z)*Constants.ACTV_PER_PARTICLES, Constants.Kd_SarsCoV2, cell.getClock())) { //ACE2
//        		EukaryoteSignalingNetwork.VIRUS_e = SarsCoV2.MOL_IDX;
//        		cell.bind(SarsCoV2.MOL_IDX);
//        		int qtty = Rand.getRand().randpois(Constants.SarsCoV2_PNEU_UPTAKE_QTTY); 
//	        	qtty = qtty > this.get(0, x, y, z) ? ((int)this.get(0, x, y, z)) : qtty;
//	        	cell.incViralLoad(qtty);
//	        	this.dec(qtty, 0, x, y, z);
//	        }
//	        /*if(cell.inPhenotype(Phenotypes.NECROTIC) && cell.getStatus() == Cell.DEAD) {
//	        	
//	        	this.inc(cell.getViralLoad(), 0, x, y, z);
//	        	cell.clearViralLoad();
//	        }else */
//        	if(cell.inPhenotype(Phenotypes.APOPTOTIC) && cell.getStatus() == Cell.DEAD) {
//	        	cell.clearViralLoad();
//	        }else {
//	        	double avg = cell.getViralLoad() * Constants.VIRAL_RELEASE_RATE;
//	        	int intViralQtty = cell.getViralLoad();
//	        	if(intViralQtty > 2) {
//	        		int qtty = Rand.getRand().randpois(avg);
//	        		qtty = qtty >= intViralQtty ? intViralQtty - 1 : qtty;
//	        		this.inc(Util.toCovidConcentration(qtty), 0, x, y, z);
////	        		System.out.println(this.getTotalMolecule(0));
//	        		cell.incViralLoad(-qtty);
//	        	}
//	        }
//	        return true;
//        }
//        if(interactable instanceof Neutrophil) {
//        	Neutrophil cell = (Neutrophil) interactable;
//        	this.pdec(1-Constants.SarsCoV2_HALF_LIFE, 0, x, y, z);
//	        if (Util.activationFunction(this.get(0, x, y, z)*10000, Constants.Kd_SarsCoV2, cell.getClock())) {
//	        	cell.bind(SarsCoV2.MOL_IDX);
//	        }
//	        if(cell.inPhenotype(this.getSecretionPhenotype()))//# and interactable.state == Neutrophil.INTERACTING:
//        		this.inc(1.0, 0, x, y, z);
//	        return true;
//        }
//        
//        if(interactable instanceof Macrophage) {
//        	
//        	Macrophage cell = (Macrophage) interactable;
//        	if (Util.activationFunction(this.get(0, x, y, z)*Constants.ACTV_PER_PARTICLES, Constants.Kd_SarsCoV2, cell.getClock())) { //ACE2
//        		int qtty = Rand.getRand().randpois(Constants.SarsCoV2_MAC_UPTAKE_QTTY); 
//	        	qtty = qtty > this.get(0, x, y, z) ? ((int)this.get(0, x, y, z)) : qtty;
//	        	cell.incViralLoad(qtty);
//	        	this.dec(qtty, 0, x, y, z);
//	        }
//
//        	if(cell.inPhenotype(Phenotypes.ACTIVE) && cell.getStatus() != Cell.DEAD) {//https://doi.org/10.1038/s41392-022-01106-8
//	        	double avg = cell.getViralLoad() * Constants.VIRAL_RELEASE_RATE;
//	        	int intViralQtty = cell.getViralLoad();
//	        	if(intViralQtty > 2) {
//	        		int qtty = Rand.getRand().randpois(avg);
//	        		qtty = qtty >= intViralQtty ? intViralQtty - 1 : qtty;
//	        		this.inc(Util.toCovidConcentration(qtty), 0, x, y, z);
//	        		cell.incViralLoad(-qtty);
//	        	}
//	        }
//            /*this.pdec(1-Constants.SarsCoV2_HALF_LIFE, 0, x, y, z);
//	        if (Util.activationFunction(this.get(0, x, y, z)*10000, Constants.Kd_SarsCoV2, cell.getClock())) {
//	        	cell.bind(SarsCoV2.MOL_IDX);
//	        	/*double qtty = Constants.SarsCoV2_UPTAKE_QTTY > this.get(0, x, y, z) ? this.get(0, x, y, z) : Constants.SarsCoV2_UPTAKE_QTTY;
//	        	double q = this.get(0, x, y, z);
//	        	this.dec(qtty, 0, x, y, z);*/
//	        //}
//	        return true;
//        }
        /*if(interactable instanceof Lactoferrin) {
        	Molecule mol = (Molecule) interactable;
        	//if(mol.get(0, x, y, z) > 0)System.out.println(mol.get(0, x, y, z) + " " + this.get(0, x, y, z) + " " + mol.get(1, x, y, z));
        	double virus = this.get(0, x, y, z);
        	double lacto = mol.get(0, x, y, z);
        	double lactoTot = (lacto + mol.get(1, x, y, z))/Constants.VOXEL_VOL;
        	double virusTot = (virus + mol.get(1, x, y, z))/Constants.VOXEL_VOL;
        	double kd = Constants.VIRAL_LAC_Kd;
        	double bound = Constants.VOXEL_VOL*(lactoTot + virusTot + kd - Math.sqrt((lactoTot + virusTot + kd)*(lactoTot + virusTot + kd) - 4*lactoTot*virusTot))/2.0;
        	//self derived and also https://doi.org/10.1016/j.jmb.2021.167225
        	double dbound = bound - mol.get(1, x, y, z);
        	dbound = dbound < virus ? dbound : virus;
        	dbound = dbound < lacto ? dbound : lacto;
        	//if(this.get(0, x, y, z) > 0)System.out.println(this.get(0, x, y, z) + " " + bound + " " + dbound);
        	
        	//if(d > 0.0)bound = (s + d + Constants.DEFENSIN_Kd - Math.sqrt((s + d + Constants.DEFENSIN_Kd)*(s + d + Constants.DEFENSIN_Kd) - 4.0*s*d))/(2.0*d); //equation from 10.1016/j.jmb.2021.167225 
        	//System.out.println(s + " " + d + " " + bound);
        	mol.dec(dbound, 0, x, y, z);
        	mol.inc(dbound,1, x, y, z);
        	this.dec(dbound, 0, x, y, z);
	        return true;
        }*/
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
