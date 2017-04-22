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

    public AGView(double[] generationAverage, double[] generationBest, double[] absoluteBest) {
        this.generationAverage = generationAverage;
        this.generationBest = generationBest;
        this.absoluteBest = absoluteBest;
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
}

        
        