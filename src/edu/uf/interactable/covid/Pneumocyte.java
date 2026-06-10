package edu.uf.interactable.covid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
import edu.uf.utils.Constants;
import edu.uf.utils.Rand;
import edu.uf.utils.Util;


public class Pneumocyte extends edu.uf.interactable.Pneumocyte{
	
	public static final int RESISTANT = 1;
	public static final int SUSCEPTIBLE = 2;
	
	private double il10Mult = 1;
	
	private int viralLoad;
	private int phenotype;
	private double mul = 1;
	
	private boolean sirolimus;

	public Pneumocyte() {
		super();
		phenotype = SUSCEPTIBLE;
	}
	
	
	public boolean isSirolimus() {
		return sirolimus;
	}



	public void setSirolimus(boolean sirolimus) {
		this.sirolimus = sirolimus;
	}



	public int getPhenotype() {
		return phenotype;
	}

	public double getMul() {
		return mul;
	}

	public void setMul(double mul) {
		this.mul = mul;
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
	

	

	public BooleanNetwork networkFactory() {
		return null;
	}

	
//	//for multiple types of Boolean network
	public BooleanNetwork createNewBooleanNetwork() {
		return  null; 
	}

	
	protected boolean templateInteract(Interactable interactable, int x, int y, int z) {
//    	EukaryoteSignalingNetwork.IFN_e = IFN1.MOL_IDX;
        if(interactable instanceof IFN1) {
        	IFN1 mol = (IFN1) interactable;
	        if (Util.activationFunction(mol.get(0, x, y, z), Constants.Kd_IFNG, this.getClock()))
	        	this.setPhenotype(RESISTANT);
	        return true;
        }
        
        if(interactable instanceof IL10) {
        	IL10 mol = (IL10) interactable;
	        if (Util.activationFunction(mol.get(0, x, y, z), Constants.Kd_IL10, this.getClock()))
	        	this.il10Mult = 1; 
	        return true;
        }
        
        if(interactable instanceof TNFa) {
        	TNFa mol = (TNFa) interactable;
	        if (Util.activationFunction(mol.get(0, x, y, z), Constants.Kd_TNF, this.getClock()))
	        	this.il10Mult = Constants.MTORC_SarsCoV2_REP_RATE_MUL; 
	        return true;
        }
        
        return super.templateInteract(interactable, x, y, z);
    }
	
	
//	public double getIl10Mult() {
//		return il10Mult;
//	}



//	public void setIl10Mult(double il10Mult) {
//		this.il10Mult = il10Mult;
//	}



	public void updateStatus() {
		if(this.getStatus() == DEAD)return;
//		this.processBooleanNetwork();
		this.interactNeighbours();
		double repRate = 0;
		if(phenotype == SUSCEPTIBLE) repRate = Constants.SarsCoV2_REP_RATE*this.il10Mult*mul;
		else repRate = Constants.SarsCoV2_IFN_REP_RATE*this.il10Mult*mul;
		if(this.viralLoad > 0) {
			double avg = this.viralLoad*repRate*(1-this.viralLoad/Constants.MAX_VIRAL_LOAD);
			int qtty = Util.createVirus(avg, Constants.SarsCoV2_EPS, this.viralLoad);
			this.viralLoad += qtty;
			((SarsCoV2) SarsCoV2.getMolecule()).incInternalLoad(qtty);
			if(Rand.getRand().randunif() < Constants.PR_PNEUMOCYTE_CHANGE) {
				if(phenotype == SUSCEPTIBLE) this.setPhenotype(RESISTANT);
				else this.setPhenotype(SUSCEPTIBLE);
			}
		}
		
    	if(this.inPhenotype(new int[] {Phenotypes.APOPTOTIC})) 
			this.die();
    	
    	this.setSirolimus(false);
	}
	
	private void interactNeighbours() {
		NetworkingCell.interactNeighbours(this);
	}
}
