package edu.uf.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import edu.uf.Diffusion.Diffuse;
import edu.uf.Diffusion.FADIPeriodic;
import edu.uf.compartments.MacrophageRecruiter;
import edu.uf.compartments.MacrophageReplenisher;
import edu.uf.compartments.NeutrophilRecruiter;
import edu.uf.compartments.NeutrophilReplenisher;
import edu.uf.compartments.Quadrant;
import edu.uf.compartments.RecruitDC;
import edu.uf.compartments.Recruiter;
import edu.uf.compartments.Voxel;
import edu.uf.control.Exec;
import edu.uf.interactable.Afumigatus;
import edu.uf.main.initialize.Initialize;
import edu.uf.main.initialize.InitializeBaseInvitroModel;
import edu.uf.main.initialize.InitializeBaseModel;
import edu.uf.main.initialize.InitializeCoinjury;
import edu.uf.main.initialize.InitializeCovidModel;
import edu.uf.main.initialize.InitializeHemorrhageModel;
import edu.uf.main.initialize.InitializeInvitromacrophages;
import edu.uf.main.print.PrintBaseInVitro;
import edu.uf.main.print.PrintBaseModel;
import edu.uf.main.print.PrintCoinjury;
import edu.uf.main.print.PrintCovid;
import edu.uf.main.print.PrintHemorrhageModel;
import edu.uf.main.print.PrintLearnTCID50;
import edu.uf.main.print.PrintStat;
import edu.uf.main.print.PrintNull;
import edu.uf.main.run.Run;
import edu.uf.main.run.RunSingleThread;
import edu.uf.utils.Constants;

public class Main {
	
	private static void baseCovidModels()  throws Exception{
		InitializeCovidModel initialize = new InitializeCovidModel();
		Run run = new RunSingleThread();
		PrintCovid stat = new PrintCovid();
		
		int xbin = 10;
		int ybin = 10;
		int zbin = 10;
		int xquadrant = 3;
        int yquadrant = 3;
        int zquadrant = 3;
       
        
        int nNum = 400;;
        int nkNum = 250;

        String[] input = new String[]{"0", nkNum + "", "80" + "", nNum + "", "10000"};
        Constants.MIN_MA = 80;
        Constants.MIN_NK = nkNum;
        
        Constants.MAX_MA = 80;
        Constants.MAX_N = nNum;
        Constants.MAX_NK = nkNum;
//        Constants.SPACE_VOL = Constants.VOXEL_VOL * xbin * ybin * zbin;
 
        double f = 0.1;
        double pdeFactor = Constants.D/(30/Constants.TIME_STEP_SIZE); 
        double dt = 1;
        Diffuse diffusion = new FADIPeriodic(f, pdeFactor, dt);
        
        InitializeCovidModel.prM1 = 0.35; //https://doi.org/10.1038/s41392-022-01106-8
//        InitializeCovidModel.prDC = 0.5;
        
        Voxel[][][] grid = initialize.createPeriodicGrid(xbin, ybin, zbin);
        List<Quadrant> quadrants = initialize.createQuadrant(grid, xbin, ybin, zbin, xquadrant, yquadrant, zquadrant);
        initialize.initializeMolecules(grid, xbin, ybin, zbin, diffusion, false);
        initialize.initializePneumocytes(grid, xbin, ybin, zbin, 640, true, true);
//        initialize.initializeDC(grid, xbin, ybin, zbin, Integer.parseInt(input[2]));
        initialize.initializeNK(grid, xbin, ybin, zbin, 100);
//        initialize.initializeMacrophage(grid, xbin, ybin, zbin, Integer.parseInt(input[2]));
        initialize.setQuadrant(grid, xbin, ybin, zbin);
//        initialize.covidInfec(grid, xbin, ybin, zbin, diffusion, 10);
        stat.grid = grid;
        

        Recruiter[] recruiters = new Recruiter[0];


        
        run.run(
        		2160, //1440,//(int) 72*2*(30/Constants.TIME_STEP_SIZE),  
        		xbin, 
        		ybin, 
        		zbin, 
        		grid, 
        		quadrants, 
        		recruiters,
        		false,
        		null, 
        		//new File("C:\\Users\\stsiorintsoa\\Lung immunity\\1-CoviD-19\\viralgrowth.tsv"),
        		-1,
        		stat
        );
        
        stat.close();
	}



	public static void main(String[] args) throws Exception {
	
		System.out.println("jPAI-Covid19");
		long tic = System.currentTimeMillis();
//
		
		Main.baseCovidModels();	
		
		long toc = System.currentTimeMillis();
		// System.out.println((toc - tic));
	}

}
