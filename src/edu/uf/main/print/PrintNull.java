package edu.uf.main.print;

import java.io.File;
import java.util.Map.Entry;

import edu.uf.compartments.Voxel;
import edu.uf.interactable.Interactable;
import edu.uf.interactable.covid.Pneumocyte;

public class PrintNull extends PrintStat{
	public Voxel[][][] grid; 
	int infected;
	void count() {
		infected = 0;
		for(Voxel[][] VV : grid) {
			for(Voxel[] V : VV) {
				for(Voxel v : V) {
					for(Entry<Integer, Interactable> entry : v.getInteractables().entrySet()) {
						Interactable cell = entry.getValue();
						if(cell instanceof Pneumocyte) {
							Pneumocyte p = (Pneumocyte) cell;
							if(p.getViralLoad() > 0 )infected++;
						}
						
					}
				}
			}
		}
	}
	@Override
	public void printStatistics(int k, File file) {
		// TODO Auto-generated method stub
		count();
		System.out.println(infected);
	}

}
