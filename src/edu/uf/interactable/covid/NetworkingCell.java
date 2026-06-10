package edu.uf.interactable.covid;

import edu.uf.interactable.Cell;
import edu.uf.utils.Constants;
import edu.uf.utils.Rand;
import edu.uf.utils.Util;

public class NetworkingCell {
	public static void interactNeighbours(Pneumocyte cell) {
		for(Cell neighbour : cell.getNeighbours()) {
			double avg = cell.getViralLoad() * Constants.VIRAL_RELEASE_RATE;
        	int intViralQtty = cell.getViralLoad();
        	if(intViralQtty > 2 && Util.activationFunction(intViralQtty * Constants.ACTV_PER_PARTICLES, Constants.Kd_SarsCoV2, Constants.PNEUMOCYTE_VOL, cell.getClock())) {
        		int qtty = Rand.getRand().randpois(avg, Constants.RPOIS_THRESHOLD);
        		qtty = qtty >= intViralQtty ? intViralQtty - 1 : qtty;
        		if(neighbour instanceof Pneumocyte) {
        			((Pneumocyte) neighbour).incViralLoad(qtty);
//	        		if(cell.getPhenotype() == Pneumocyte.IFN && Rand.getRand().randunif() < Constants.PR_NEIGHBOUR_IFN) {
        			if(cell.getPhenotype() == Pneumocyte.RESISTANT && !((Pneumocyte) cell).isSirolimus() && Rand.getRand().randunif() < Constants.PR_NEIGHBOUR_IFN) {
    	        			((Pneumocyte) neighbour).setPhenotype(Pneumocyte.RESISTANT);
	            	}
        		}else if(neighbour instanceof Macrophage) {
        			((Macrophage) neighbour).incViralLoad(qtty);
//	        		if(cell.getPhenotype() == Pneumocyte.IFN && Rand.getRand().randunif() < Constants.PR_NEIGHBOUR_IFN) {
//	        			((Macrophage) neighbour).setPhenotype(Macrophage.M1);
//	            	}
        		} else if(neighbour instanceof DC) {
        			((DC) neighbour).incViralLoad(qtty);

        		}else continue;
        		cell.incViralLoad(-qtty);
        	}
//        	if(neighbour instanceof Pneumocyte && cell.inPhenotype(IFN1.getMolecule().getSecretionPhenotype()) && Rand.getRand().randunif() < Constants.PR_NEIGHBOUR_IFN) {
//        		neighbour.bind(IFN1.MOL_IDX);
//        	}
		}
	}
	
	public static void interactNeighbours(Macrophage cell) {
		for(Cell neighbour : cell.getNeighbours()) {
			double avg = cell.getViralLoad() * Constants.VIRAL_RELEASE_RATE;
        	int intViralQtty = cell.getViralLoad();
        	if(intViralQtty > 2 && Util.activationFunction(intViralQtty * Constants.ACTV_PER_PARTICLES, Constants.Kd_SarsCoV2, Constants.PNEUMOCYTE_VOL, cell.getClock())) {
        		int qtty = Rand.getRand().randpois(avg, Constants.RPOIS_THRESHOLD);
        		qtty = qtty >= intViralQtty ? intViralQtty - 1 : qtty;
        		if(neighbour instanceof Pneumocyte) {
        			((Pneumocyte) neighbour).incViralLoad(qtty);
	        		if(cell.getPhenotype() == Macrophage.M1 && !((Macrophage) cell).isSirolimus() && Rand.getRand().randunif() < Constants.PR_NEIGHBOUR_IFN) {
	        			((Pneumocyte) neighbour).setPhenotype(Pneumocyte.RESISTANT);
	            	}
        		}else if(neighbour instanceof Macrophage) {
//        			((Macrophage) neighbour).incViralLoad(qtty);
//	        		if(cell.getPhenotype() == Macrophage.M1 && Rand.getRand().randunif() < Constants.PR_NEIGHBOUR_IFN) {
//	        			((Macrophage) neighbour).setPhenotype( Macrophage.M1);
//	            	}
        		} else if(neighbour instanceof DC) {
        			((DC) neighbour).incViralLoad(qtty);

        		}else continue;
        		cell.incViralLoad(-qtty);
        	}
        
		}
	}
	
	public static void interactNeighbours(DC cell) {
		for(Cell neighbour : cell.getNeighbours()) {
			double avg = cell.getViralLoad() * Constants.VIRAL_RELEASE_RATE;
        	int intViralQtty = cell.getViralLoad();
        	if(intViralQtty > 2 && Util.activationFunction(intViralQtty * Constants.ACTV_PER_PARTICLES, Constants.Kd_SarsCoV2, Constants.PNEUMOCYTE_VOL, cell.getClock())) {
        		int qtty = Rand.getRand().randpois(avg);
        		qtty = qtty >= intViralQtty ? intViralQtty - 1 : qtty;
        		if(neighbour instanceof Pneumocyte) {
	        		if(cell.getViralLoad() > 0 && Rand.getRand().randunif() < Constants.PR_NEIGHBOUR_IFN) {
	        			((Pneumocyte) neighbour).setPhenotype(Pneumocyte.RESISTANT);
	            	}
        			((Pneumocyte) neighbour).incViralLoad(qtty);
        		}else if(neighbour instanceof Macrophage) {
        			((Macrophage) neighbour).incViralLoad(qtty);
        			if(cell.getViralLoad() > 0 && Rand.getRand().randunif() < Constants.PR_NEIGHBOUR_IFN) {
//	        			((Macrophage) neighbour).setPhenotype( Macrophage.M1);
	            	}
        		} else if(neighbour instanceof DC) {
        			((DC) neighbour).incViralLoad(qtty);

        		}else continue;
        		cell.incViralLoad(-qtty);
        	}

		}
	}
}
