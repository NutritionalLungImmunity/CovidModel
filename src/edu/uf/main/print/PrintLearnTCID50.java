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
import edu.uf.interactable.covid.TNFa;
import edu.uf.interactable.covid.IFN1;
import edu.uf.interactable.covid.Pneumocyte;
import edu.uf.interactable.covid.SarsCoV2;
import edu.uf.utils.Constants;

public class PrintLearnTCID50  extends PrintStat{

	@Override
	public void printStatistics(int k, File file) {
		 
		String infected = countInfected();
		String str = k + "\t" + 
				//(Constants.AVOGRADO*((SarsCoV2)SarsCoV2.getMolecule()).getExternalLoad())/160 + "\t" +  //assuming a simulated space of 10*10*10
//				((int) (Constants.AVOGRADO*1e-4*((SarsCoV2)SarsCoV2.getMolecule()).getExternalLoad())) + "\t" + //5.95656e+23  6.02e+26    9.40625e+27
				 //5.95656e+23  6.02e+26    9.40625e+27
				(Constants.AVOGRADO*1e-4*(SarsCoV2.getMolecule().getTotalMolecule(0)) + ((SarsCoV2)SarsCoV2.getMolecule()).getInternalLoad()) + "\t" + //1.505e+25*  
				Pneumocyte.getTotalCells() + "\t" + 
//				infected + "\t" +
//				IFN1.getMolecule().getTotalMolecule(0) + "\t" + 
				IL6.getMolecule().getTotalMolecule(0); // + "\t" +
//				TNFa.getMolecule().getTotalMolecule(0);
				  
				/*	//(Constants.AVOGRADO*((SarsCoV2)SarsCoV2.getMolecule()).getExternalLoad())/160 + "\t" +  //assuming a simulated space of 10*10*10
//				((int) (Constants.AVOGRADO*1e-4*((SarsCoV2)SarsCoV2.getMolecule()).getExternalLoad())) + "\t" + //5.95656e+23  6.02e+26    9.40625e+27
				((int) (Constants.AVOGRADO*1e-4*((SarsCoV2)SarsCoV2.getMolecule()).getTotalMolecule(0))) + "\t" + //5.95656e+23  6.02e+26    9.40625e+27
				((int)(((SarsCoV2)SarsCoV2.getMolecule()).getInternalLoad())) + "\t" + //1.505e+25*  
				Pneumocyte.getTotalCells() + "\t" + 
				  infected + "\t" +
				  ((int) (IFN1.getMolecule().getTotalMolecule(0) * 21725 * 1e3 * 1e12)) + "\t" + 
	              //IFN1.getMolecule().getTotalMolecule(0) + "\t" + 
//	              ((int) (Lactoferrin.getMolecule().getTotalMolecule(0) * 78182 * 1e3 * 1e9)) + "\t" +
	              //Lactoferrin.getMolecule().getTotalMolecule(0) + "\t" + 
//	              ((int) (H2O2.getMolecule().getTotalMolecule(0) * 1e3 * 1e12));
				  ( (IL6Complex.getMolecule().getTotalMolecule(0))) + "\t" +
				((int) (TNFa.getMolecule().getTotalMolecule(0) * 1e3 *1e12));*/  
				
		
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
	
	private String countInfected() {
		int count = 0;
		double load = 0;
		for(Voxel[][] VV : grid) {
			for(Voxel[] V : VV) {
				for(Voxel v : V) {
					for(Entry<Integer, Interactable> entry : v.getInteractables().entrySet()) {
						Interactable cell = entry.getValue();
						if(cell instanceof Pneumocyte) {
							if(((Pneumocyte) cell).getViralLoad() > 0) {
								count++;
								load += ((Pneumocyte) cell).getViralLoad();
							}
						}
					}
				}
			}
		}
		return load + "\t" + count + "";
	}
	
}
