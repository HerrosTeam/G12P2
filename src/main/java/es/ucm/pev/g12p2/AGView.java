/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2;

import java.util.List;

/**
 *
 * @author Herros Team
 */
public class AGView {

    double[] generationAverage;
    double[] generationBest;
    double[] absoluteBest;
    String bestIndividual;
    double absoluteWorst;
    int numCrossovers;
    int numMutations;
    int numInversions;

    public AGView(double[] generationAverage, double[] generationBest, double[] absoluteBest, String absoluteBestIndividual, double absoluteWorst,
            int numCrossovers, int numMutations, int numInversions) {
        this.generationAverage = generationAverage;
        this.generationBest = generationBest;
        this.absoluteBest = absoluteBest;
        this.bestIndividual = absoluteBestIndividual;
        this.absoluteWorst = absoluteWorst;
        this.numCrossovers = numCrossovers;
        this.numMutations = numMutations;
        this.numInversions = numInversions;
    }

    public double[] getGenerationAverage() {
        return generationAverage;
    }

    public double[] getGenerationBest() {
        return generationBest;
    }

    public double[] getAbsoluteBest() {
        return absoluteBest;
    }

    public String getBestIndividual() {
        return bestIndividual;
    }

    public double getAbsoluteWorst() {
        return absoluteWorst;
    }

    public int getNumCrossovers() {
        return numCrossovers;
    }

    public int getNumMutations() {
        return numMutations;
    }

    public int getNumInversions() {
        return numInversions;
    }
    
    
}

        
        