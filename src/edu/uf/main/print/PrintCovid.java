package edu.uf.main.print;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import edu.uf.compartments.Voxel;
import edu.uf.interactable.covid.IL10;
import edu.uf.interactable.covid.IL6;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.Lactoferrin;
import edu.uf.interactable.MCP1;
import edu.uf.interactable.MIP2;
import edu.uf.interactable.Molecule;
import edu.uf.interactable.covid.DC;
import edu.uf.interactable.Neutrophil;
import edu.uf.interactable.covid.Pneumocyte;
import edu.uf.interactable.TGFb;
import edu.uf.interactable.covid.TNFa;
import edu.uf.interactable.covid.IFN1;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.interactable.covid.NK;
import edu.uf.interactable.covid.SarsCoV2;
import edu.uf.intracellularState.Phenotypes;

public class PrintCovid extends PrintStat{ 

	//IFN		IL6		IL10		MCP1	TNF
    //17921, 103231		316227		95480	885
	
	@Override
	public void printStatistics(int k, File file) {
//		if(k%15!=0 && k != 1) return;	
//		if((k+1)%15!=0 && k != 1)return;
		  Molecule ifn = IFN1.getMolecule();
		  Molecule tnfa = TNFa.getMolecule();
		  Molecule il10 = IL10.getMolecule();
		  Molecule il6 = IL6.getMolecule();
          
		count();
		double avogrado = 6.02e23;
		String str = k + "\t" + 
//				((int) (avogrado*1e-4*((SarsCoV2)SarsCoV2.getMolecule()).getTotalMolecule(0))) + "\t" + //5.95656e+23  6.02e+26    9.40625e+27
				((((SarsCoV2)SarsCoV2.getMolecule()).getInternalLoad())) + "\t" + //1.505e+25*
	            (ifn == null ? 0.0 : ifn.getTotalMolecule(0)) + "\t" + // * 21725 * 1e3 * 1e12
	            (tnfa == null ? 0.0 : tnfa.getTotalMolecule(0)) + "\t" + // * 25644 * 1e3 * 1e12
	            (il10 == null ? 0.0 : il10.getTotalMolecule(0)) + "\t" + // * 20517 * 1e4 * 1e12
				(il6 == null ? 0.0 : il6.getTotalMolecule(0)) + "\t" + // * 23718 * 1e3 * 1e12
	            Pneumocyte.getTotalCells() + "\t" + 
//	            DC.getTotalCells() + "\t" +
	            Macrophage.getTotalCells() + "\t" +
//	        Neutrophil.getTotalCells() + "\t" + 
	            NK.getTotalCells()  + "\t" + M0 + "\t" + AM + "\t" + IM + "\t" + M1 + "\t" + M2 + "\t" + 
	            infected  + "\t" + resistant  + "\t" + susceptible;

		if(file == null) {
			System.out.println(str);
		}else {
			try {
		
				if(getPrintWriter() == null) 
					setPrintWriter(new PrintWriter(file)); 
				getPrintWriter().println(str);
			}catch(FileNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	public Voxel[][][] grid;
	int infected = 0;
	int M1 = 0;
	int M2 = 0;
	int resistant = 0;
	int susceptible = 0;
	int M0 = 0;
	int AM = 0;
	int IM = 0;

	
	void count() {

		infected = 0;
		M0 = 0;
		AM = 0;
		IM = 0;
		M1 = 0;
		M2 = 0;
		resistant = 0;
		susceptible = 0;
		
		for(Voxel[][] VV : grid) {
			for(Voxel[] V : VV) {
				for(Voxel v : V) {
					for(Entry<Integer, Interactable> entry : v.getInteractables().entrySet()) {
						Interactable cell = entry.getValue();
						
						if(cell instanceof DC) {
							DC p = (DC) cell;
							if(p.getViralLoad() > 0)infected++;
						}
						if(cell instanceof Pneumocyte) {
							Pneumocyte p = (Pneumocyte) cell;
							if(p.getViralLoad() > 0)infected++;
							if(p.getPhenotype() == Pneumocyte.RESISTANT)resistant++;
							if(p.getPhenotype() == Pneumocyte.SUSCEPTIBLE)susceptible++;
						}
						if(cell instanceof Macrophage) {
							Macrophage p = (Macrophage) cell;
							if(p.getViralLoad() > 0)infected++;
							if(p.getPhenotype() == Macrophage.M0)M0++;
							if(p.getPhenotype() == Macrophage.AM)AM++;
							if(p.getPhenotype() == Macrophage.IM)IM++;
							if(p.getPhenotype() == Macrophage.M1)M1++;
							if(p.getPhenotype() == Macrophage.M2)M2++;
						}
					}
				}
			}
		}
	}

}
