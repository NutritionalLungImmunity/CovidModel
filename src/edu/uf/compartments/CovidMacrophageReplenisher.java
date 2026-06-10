package edu.uf.compartments;

import java.util.Map;

import edu.uf.interactable.Cell;
import edu.uf.interactable.covid.Macrophage;
import edu.uf.utils.Constants;
import edu.uf.utils.Rand;

public class CovidMacrophageReplenisher extends Recruiter {
	
	private double prM1 = 0;
	
	public double getPrM1() {
		return prM1;
	}

	public void setPrM1(double prM1) {
		this.prM1 = prM1;
	}
	
	public Cell createCell() { 
        return new Macrophage(Rand.getRand().randunif() < prM1 ? Macrophage.M1 : Macrophage.M2);//new Macrophage(Constants.MA_INTERNAL_IRON);
    }
    
    public int getQtty() {
    	return getQtty(0);
    }
    
    public int getQtty(Quadrant q) { 
    	return getQtty(0);
    }

    protected int getQtty(double chemokine) {
        double avg = Constants.MAX_MA - Macrophage.getTotalCells();
        if (avg > 0)
            return (int) avg; 
        else {
            return 0;
        }
    }

    /*public double chemoatract(Quadrant quadrant) {
    	int x = (int) (quadrant.xMax - quadrant.xMin) / 2;
    	int y = (int) (quadrant.yMax - quadrant.yMin) / 2;
    	int z = (int) (quadrant.zMax - quadrant.zMin) / 2;
        return Util.activationFunction(
        		quadrant.chemokines.get(Macrophage.getChemokine()).get(x, y, z), 
        		Constants.Kd_MIP1B, 
        		Constants.STD_UNIT_T, 
        		Constants.VOXEL_VOL,
        		Constants.REC_BIAS
        );
    }*/

    public boolean leave() {
        return true; 
    }

    public Cell getCell(Voxel voxel) {
        //if (voxel.getCells().size() == 0)
        //    return null;
        for(Map.Entry<Integer, Cell> entry : voxel.getCells().entrySet())
        	if (entry.getValue() instanceof Macrophage)
        		return entry.getValue();
        return null;
    }
}
