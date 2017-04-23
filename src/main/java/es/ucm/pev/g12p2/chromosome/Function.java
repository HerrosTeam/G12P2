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

    private final int numBuildings;
    private int[][] distanceData;
    private int[][] flowData;
    private String data;

    public Function(double tolerance, int numBuildings, Boolean maximize, String data) {
        super(1, numBuildings, tolerance, maximize);
        
        this.numBuildings = numBuildings;
        int geneLength = 1;
        this.chromosomeLength = numBuildings*geneLength;
        this.fenotype = new LinkedList();
        this.tolerance = tolerance;
        this.genes = new LinkedList();
        for(int i=0; i<numBuildings; i++){
            this.genes.add(i, new IntegerGene(geneLength, xmin, xmax));
        }
        this.data = data;
        
        DistanceFlowData distflow = new DistanceFlowData();
        
        switch(data){
            case "ajuste.dat":
                this.distanceData = distflow.getDistanceAjuste();
                this.flowData = distflow.getFlowAjuste();
                break;
            case "datos12.dat":
                this.distanceData = distflow.getDistanceDatos12();
                this.flowData = distflow.getFlowDatos12();
                break;
            case "datos15.dat":
                this.distanceData = distflow.getDistanceDatos15();
                this.flowData = distflow.getFlowDatos15();
                break;
            case "datos30.dat":
                this.distanceData = distflow.getDistanceDatos30();
                this.flowData = distflow.getFlowDatos30();
                break;
        }
    } 
    
    public double function(List<Integer> x){
        int sum=0;
        
        for(int i=0; i<numBuildings; i++){
            for(int j=0; j<numBuildings; j++){
                sum += this.flowData[i][j] * this.distanceData[x.get(i)][x.get(j)];
            }
        }
      return sum;
    }
    
    @Override
    public void evaluate() { 
        fenotype();
        this.fitness = function(this.fenotype); 
    }

    @Override
    public void fenotype() {
        for (int i=0; i<numBuildings; i++){
            int result = getFenotype(this.genes.get(i));
            this.fenotype.add(i, result);
        }
    }
    
    private int getFenotype(Gene gene){
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
        Chromosome c=new Function(this.tolerance, this.numBuildings, this.maximize, this.data);	
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