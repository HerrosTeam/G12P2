/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.chromosome;

import es.ucm.pev.g12p2.chromosome.gene.Gene;
import es.ucm.pev.g12p2.chromosome.gene.IntegerGene;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author usuario_local
 */
public class Function extends Chromosome{

    private int numBuildings;

    public Function(double tolerance, int numBuildings, Boolean maximize) {
        super(0, Integer.MAX_VALUE, tolerance, maximize);
        
        this.numBuildings = numBuildings;
        int geneLength = 1;
        this.chromosomeLength = numBuildings*geneLength;
        this.fenotype = new LinkedList();
        this.tolerance = tolerance;
        this.genes = new LinkedList();
        for(int i=0; i<numBuildings; i++){
            this.genes.add(i, new IntegerGene(geneLength, xmin, xmax));
        }
    } 
    
    public double function(List<Double> x){
        double sum=0;
        for(int i=0; i<numBuildings; i++){
            sum+= Math.sin(x.get(i))*Math.pow(Math.sin(((i+1)+1)* 
                    Math.pow(x.get(i), 2) / Math.PI) , 20);
        }
      return sum * -1;
    }
    
    @Override
    public void evaluate() { 
        fenotype();
        this.fitness = function(this.fenotype); 
    }

    @Override
    public void fenotype() {
        for (int i=0; i<numBuildings; i++){
            double result = getFenotype(this.genes.get(i));
            this.fenotype.add(i, result);
        }
    }
    
    private double getFenotype(Gene gene){
        IntegerGene a = (IntegerGene) gene;
        return a.getIntegerAllele();
    }

    @Override
    public double getAdaptation(double cmax, double fmin) {
        this.adaptation = cmax - this.fitness; 
        return this.adaptation;
    }

    @Override
       public Chromosome copy() {
        Chromosome c=new Function(this.tolerance, this.numBuildings, this.maximize);	
        c.genes = new LinkedList();
        for(int i=0; i<this.numBuildings; i++){
            c.genes.add(i, this.genes.get(i).copy());
        }
        c.evaluate();
         c.setScore(this.score);
        c.setAccumulatedScore(this.scoreAccumulated);
        c.setEscalation(this.escalation);
        c.setAdaptation(this.adaptation);
        return c;
    }

   
    
}
