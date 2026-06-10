package edu.uf.main.initialize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import edu.uf.Diffusion.Diffuse;
import edu.uf.compartments.Voxel;
import edu.uf.control.MultiThreadExec;
import edu.uf.interactable.Afumigatus;
import edu.uf.interactable.Cell;
import edu.uf.interactable.covid.IL10;
import edu.uf.interactable.covid.IL6;
import edu.uf.interactable.Lactoferrin;
import edu.uf.interactable.MCP1;
import edu.uf.interactable.covid.DC;
import edu.uf.interactable.covid.IFN1;
import edu.uf.interactable.covid.NK;
import edu.uf.interactable.MIP2;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.interactable.covid.Pneumocyte;
import edu.uf.interactable.TGFb;
import edu.uf.interactable.covid.TNFa;
import edu.uf.interactable.covid.SarsCoV2;
import edu.uf.interactable.covid.Sirolimus;
import edu.uf.intracellularState.EukaryoteSignalingNetwork;
import edu.uf.intracellularState.Phenotypes;
import edu.uf.utils.Constants;
import edu.uf.utils.Rand;

public class InitializeCovidModel extends InitializeBaseModel{
	public static double prM1 = 0.35;
	public static double prDC = 0.5;
	@Override
	public void initializeMolecules(Voxel[][][] grid, int xbin, int ybin, int zbin, Diffuse diffuse, boolean verbose) {
		if(verbose) {
    		System.out.println("Initializing Iron, TAFC, Lactoferrin, Transferrin, Hepcidin, IL6, TNF-a, IL10, TGF-b, MIP2, MIP1-b");
    	}
//		System.out.println(new double[1][xbin][ybin][zbin][0]);
//		IL6Complex il6 = IL6Complex.getMolecule(new double[3][xbin][ybin][zbin], diffuse);
    	TNFa tnfa = TNFa.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	IL10 il10 = IL10.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	IL6 il6 = IL6.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	//TGFb tgfb = TGFb.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	//TGFb tgfb = TGFb.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	MIP2 mip2 = MIP2.getMolecule(new double[1][xbin][ybin][zbin], null);
    	//MCP1 mcp1 = MCP1.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	IFN1 ifn1 = IFN1.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	//VEGF vegf = VEGF.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	SarsCoV2 virus = SarsCoV2.getMolecule(new double[1][xbin][ybin][zbin], null);
//    	SAMP samp = SAMP.getMolecule(new double[1][xbin][ybin][zbin], null);
    	//DAMP damp = DAMP.getMolecule(new double[1][xbin][ybin][zbin], null);
//    	Lactoferrin def = Lactoferrin.getMolecule(new double[3][xbin][ybin][zbin], diffuse);
//    	H2O2 h2o2 = H2O2.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
    	Sirolimus sir = Sirolimus.getMolecule(new double[1][xbin][ybin][zbin], null);
    	//IL6.getMolecule(new double[1][1][1][1], null); //Dummy initialization. Necessary!
    	
    	
    	for(int x = 0; x < xbin; x++) 
        	for(int y = 0; y < ybin; y++)
        		for(int z = 0; z < zbin; z++) {
//        			il10.set(100*Constants.MA_IL10_QTTY, 0, x, y, z); //try 1, 10, 100, 1000 for all
//        			tnfa.set(1000*Constants.MA_IL10_QTTY, 0, x, y, z);
        			sir.set(Constants.Sirolimus_QTTY, 0, x, y, z);
        		}
    	
    	
    	MultiThreadExec.setMolecule(ifn1);
    	//MultiThreadExec.setMolecule(vegf);
//    	MultiThreadExec.setMolecule(samp);
    	//MultiThreadExec.setMolecule(damp);
    	MultiThreadExec.setMolecule(il6);
    	MultiThreadExec.setMolecule(tnfa);
    	MultiThreadExec.setMolecule(il10);
    	//MultiThreadExec.setMolecule(tgfb);
    	MultiThreadExec.setMolecule(mip2);
    	MultiThreadExec.setMolecule(sir);
    	//MultiThreadExec.setMolecule(mcp1);
//    	MultiThreadExec.setMolecule(virus);
//    	MultiThreadExec.setMolecule(def);
//    	MultiThreadExec.setMolecule(h2o2);
    	
    	
    	Voxel.setMolecule(IFN1.NAME, ifn1);
    	//Voxel.setMolecule(VEGF.NAME, vegf);
//    	Voxel.setMolecule(SAMP.NAME, samp);
    	//Voxel.setMolecule(DAMP.NAME, damp);
    	Voxel.setMolecule(IL6.NAME, il6);
    	Voxel.setMolecule(TNFa.NAME, tnfa);
    	Voxel.setMolecule(IL10.NAME, il10);
    	//Voxel.setMolecule(TGFb.NAME, tgfb);
    	Voxel.setMolecule(MIP2.NAME, mip2);
    	Voxel.setMolecule(Sirolimus.NAME, sir);
    	//Voxel.setMolecule(MCP1.NAME, mcp1);
//    	Voxel.setMolecule(SarsCoV2.NAME, virus);
//    	Voxel.setMolecule(Lactoferrin.NAME, def);
//    	Voxel.setMolecule(H2O2.NAME, h2o2);
    	
//    	this.setSecretionPhenotypes();
		
	}
	
	public void initializeCovid(Voxel[][][] grid, int xbin, int ybin, int zbin, Diffuse diffuse, double qtty) {
		SarsCoV2 virus = SarsCoV2.getMolecule(new double[1][xbin][ybin][zbin], diffuse);
		MultiThreadExec.setMolecule(virus);
		Voxel.setMolecule(SarsCoV2.NAME, virus);
		
    	for(int x = 0; x < xbin; x++) 
        	for(int y = 0; y < ybin; y++)
        		for(int z = 0; z < zbin; z++)
        			virus.set(qtty, 0, x, y, 0);
    	
    	
    	Voxel.setMolecule(SarsCoV2.NAME, virus);
		
	}
	
	
	public void covidInfec(Voxel[][][] grid, int xbin, int ybin, int zbin, Diffuse diffuse, int qtty) {
    	
    	EukaryoteSignalingNetwork.VIRUS_e = SarsCoV2.MOL_IDX;
		int count = 0;
		while(count < qtty) {
			int x = randint(0, xbin-1);
            int y = randint(0, ybin-1);
            int z = randint(0, zbin-1);
            for(Entry<Integer, Cell> entry : grid[x][y][z].getCells().entrySet()) {
            	if(entry.getValue() instanceof Pneumocyte) {
						((Pneumocyte) entry.getValue()).incViralLoad((int)(Constants.SarsCoV2_PNEU_UPTAKE_QTTY));
            		((Pneumocyte) entry.getValue()).bind(SarsCoV2.MOL_IDX);
            		count++;
            		//System.out.println(count);
            	}
            }
		}
	}
	
	
	
	public List<Pneumocyte> initializePneumocytes(Voxel[][][] grid, int xbin, int ybin, int zbin, int numCells, boolean macrophage, boolean infect) {
		List<Pneumocyte> list = new ArrayList<>();
        //for(int x = 0; x < xbin; x++) //add conditions for empty voxels
        	//for(int y = 0; y < ybin; y++) 
        		//for(int z = 0; z < zbin; z++) {
		int infected = 0;
		
		int k = 0;
		while (k < numCells) {
            int x = randint(0, xbin-1);
            int y = randint(0, ybin-1);
            int z = randint(0, zbin-1);
            if (!grid[x][y][z].getCells().isEmpty()) continue;
            
			Pneumocyte p = new Pneumocyte();
			Macrophage m1 = null;
			Macrophage m2 = null;
			if(macrophage) {
				m1 = new Macrophage(Rand.getRand().randunif() < prM1 ? Macrophage.AM : Macrophage.IM);
				m2 = new Macrophage(Rand.getRand().randunif() < prM1 ? Macrophage.AM : Macrophage.IM);
			}
					
			if(infected++ < 64) {
				p.incViralLoad(100);
				grid[x][y][z].setCell(p);
				list.add(p);
			}
			else {
				grid[x][y][z].setCell(p);
				list.add(p);
			}
			
			if(macrophage) {
				grid[x][y][z].setCell(m1);
				grid[x][y][z].setCell(m2);
			}
//			if(d != null) grid[x][y][z].setCell(d);
            //grid[x][y][z].setCell(ec);
            k = k + 1;
            if(k%100000==0)System.out.println(k +  " pneumocytes initialized ...");
		}  
		
		int count = 0;
		
		for(int x = 0; x < xbin; x++) 
        	for(int y = 0; y < ybin; y++) 
        		for(int z = 0; z < zbin; z++) 
        			for(Entry<Integer, Cell> entry : grid[x][y][z].getCells().entrySet()) 
        				count += link(entry.getValue(), grid, x, y, z, xbin, ybin, zbin);
        		
        return list;
    }
	
	private static int link(Cell cell, Voxel[][][] grid, int x, int y, int z, int xbin, int ybin, int zbin) {
		int count = 0;
		for(int i = -1; i < 2; i++) {
			if(x+i >= 0 && x+i < xbin) {
				for(Entry<Integer, Cell> entry : grid[x+i][y][z].getCells().entrySet()) {
                	if(entry.getValue() instanceof Pneumocyte) {
                		Cell n = entry.getValue();
                		if(cell instanceof Pneumocyte && n instanceof Pneumocyte) {
                			if(cell.addNeighbour(n)) count++;
                		} else if(cell.getNeighbours().size() == 0 || n.getNeighbours().size() == 0 || Rand.getRand().randunif() < 0.5)
                			cell.addNeighbour(n);
                		
                	}
				}
			}
		}
		for(int i = -1; i < 2; i++) {
			if(y+i >= 0 && y+i < ybin) {
				for(Entry<Integer, Cell> entry : grid[x][y+i][z].getCells().entrySet()) {
					if(entry.getValue() instanceof Pneumocyte) {
	                	if(entry.getValue() instanceof Pneumocyte) {
	                		Cell n = entry.getValue();
	                		if(cell instanceof Pneumocyte && n instanceof Pneumocyte) {
	                			if(cell.addNeighbour(n)) count++;
	                		} else if(cell.getNeighbours().size() == 0 || n.getNeighbours().size() == 0 || Rand.getRand().randunif() < 0.5)
	                			cell.addNeighbour(n);
	                		
	                	}
					}
				}
			}
		}
		for(int i = -1; i < 2; i++) {
			if(z+i >= 0 && z+i < zbin) {
				for(Entry<Integer, Cell> entry : grid[x][y][z+i].getCells().entrySet()) {
                	if(entry.getValue() instanceof Pneumocyte) {
                    	if(entry.getValue() instanceof Pneumocyte) {
                    		Cell n = entry.getValue();
                    		if(cell instanceof Pneumocyte && n instanceof Pneumocyte) {
                    			if(cell.addNeighbour(n)) count++;
                    		} else if(cell.getNeighbours().size() == 0 || n.getNeighbours().size() == 0 || Rand.getRand().randunif() < 0.5)
                    			cell.addNeighbour(n);
                    		
                    	}
                	}
    			}
			}
		}
		return count;
	}
	

	@Override
	public void initializeLiver(Voxel[][][] grid, int xbin, int ybin, int zbin) {
		// TODO Auto-generated method stub
	}

	@Override
	public List initializeNeutrophils(Voxel[][][] grid, int xbin, int ybin, int zbin, int numNeut) {

    	return null;
	}
	
	 public List initializeMacrophage(Voxel[][][] grid, int  xbin, int ybin, int zbin,  int numMacrophages) {
	    	List<Macrophage> list = new ArrayList<>();    	
	        for (int i = 0; i < numMacrophages; i++) {
	            int x = randint(0, xbin-1);
	            int y = randint(0, ybin-1);
	            int z = randint(0, zbin-1);
	            Macrophage m = new Macrophage(-1);
//	            if(Rand.getRand().randunif() < Constants.PR_M1)
//	            	m.addPhenotype(Phenotypes.ACTIVE);
//	            else if(Rand.getRand().randunif() < Constants.PR_M2a)
//	            	m.addPhenotype(Phenotypes.ALT_ACTIVE);
//	            else 
//	            	m.addPhenotype(Phenotypes.INACTIVE);
	            list.add(m);
	            grid[x][y][z].setCell(m);
	        }
	        return list;
	    }

	
	public List initializeDC(Voxel[][][] grid, int xbin, int ybin, int zbin, int numNeut) {
		List<DC> list = new ArrayList<>();
    	for (int i = 0; i < numNeut; i++) {
            int x = randint(0, xbin-1);
            int y = randint(0, ybin-1);
            int z = randint(0, zbin-1);
            DC n = new DC();
            list.add(n);
            grid[x][y][z].setCell(n);
    	}
    	return list;
	}
	
	public List initializeNK(Voxel[][][] grid, int xbin, int ybin, int zbin, int numNK) {
		List<NK> list = new ArrayList<>();
    	for (int i = 0; i < numNK; i++) {
            int x = randint(0, xbin-1);
            int y = randint(0, ybin-1);
            int z = randint(0, zbin-1);
            NK n = new NK(NK.RESTING);
            list.add(n);
            grid[x][y][z].setCell(n);
    	}
    	return list;
	}

	@Override
	public void initializeErytrocytes(Voxel[][][] grid, int xbin, int ybin, int zbin) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Afumigatus> infect(int numAspergillus, Voxel[][][] grid, int xbin, int ybin, int zbin, int status,
			double initIron, double sigma, boolean verbose) {
		return null;
	}
	
	public void initializeAlveole(int numAspergillus, Voxel[][][] grid, int xbin, int ybin, int zbin) {
		
	}
	
	public void initializeEndothelialCells(int numEC, Voxel[][][] grid, int xbin, int ybin, int zbin) {


	}

	@Override
	protected void setSecretionPhenotypes() {
		IL10.getMolecule().addPhenotype(Phenotypes.ACTIVE);
		IL10.getMolecule().addPhenotype(Phenotypes.MIX_ACTIVE);
		IL10.getMolecule().addPhenotype(Phenotypes.ALT_ACTIVE);
		IL10.getMolecule().addPhenotype(Phenotypes.INACTIVE);
		MIP2.getMolecule().addPhenotype(Phenotypes.ACTIVE);
		MIP2.getMolecule().addPhenotype(Phenotypes.IRF3);
		TNFa.getMolecule().addPhenotype(Phenotypes.ACTIVE);
		TNFa.getMolecule().addPhenotype(Phenotypes.MIX_ACTIVE);
		TNFa.getMolecule().addPhenotype(Phenotypes.ALT_ACTIVE);
		TNFa.getMolecule().addPhenotype(Phenotypes.INACTIVE);
		
		IFN1.getMolecule().addPhenotype(Phenotypes.IFN);	
		IFN1.getMolecule().addPhenotype(Phenotypes.ACTIVE);
		IFN1.getMolecule().addPhenotype(Phenotypes.MIX_ACTIVE);
		IFN1.getMolecule().addPhenotype(Phenotypes.ALT_ACTIVE);
		IFN1.getMolecule().addPhenotype(Phenotypes.INACTIVE);
		IL6.getMolecule().addPhenotype(Phenotypes.ACTIVE);
		IL6.getMolecule().addPhenotype(Phenotypes.MIX_ACTIVE);
		IL6.getMolecule().addPhenotype(Phenotypes.ALT_ACTIVE);
		IL6.getMolecule().addPhenotype(Phenotypes.INACTIVE);
		
	}

}
