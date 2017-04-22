/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.chromosome;


import es.ucm.pev.g12p2.chromosome.gene.Gene;
import es.ucm.pev.g12p2.chromosome.gene.IntegerGene;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author usuario_local
 */
public abstract class Chromosome {
    protected List<Gene> genes;
    
    protected List<Double> fenotype;
    protected double fitness; //funcion de evaluacion fitness
    protected double score; //puntuacion relativa (aptitud/suma)
    protected double scoreAccumulated; //puntuacion acumulada para seleccion
    private String tipo; //tipo de cromosoma
    
    //extremos del intervalo para los valores del dominio
    protected int xmin, xmax;
    protected int chromosomeLength; //longitud del cromosoma
    protected double tolerance;
    protected double adaptation;
    protected double escalation;
    protected Boolean maximize;
    public Chromosome(int min, int max, double tolerance, Boolean maximize) {
        this.xmin = min;
        this.xmax = max;
        this.tolerance = tolerance;
        this.maximize=maximize;
    }
   
    
    public List<? extends Gene> getGenes(){
        return this.genes;
    }
    
    public Gene getGene(int pos){
        return this.genes.get(pos);
    }
    
    public void addGene(int pos, int allele){
        Gene gene = new IntegerGene(1,1,9);
        gene.initializeGene(allele);
        this.genes.add(pos, gene);
    }
    
    public int getLength(){
        return this.chromosomeLength;
    }
    
    public double getFitness(){
        return fitness;
    }
    
   public abstract void evaluate();
   
   public abstract void  fenotype();// fenotype =...

    public void inicializeChromosome(){
        //Random randomNumber = new Random();
        Map <Integer, Integer> locations = new HashMap();
        int concreteLocation;
        for(int i=0; i<chromosomeLength; i++){
            concreteLocation = ThreadLocalRandom.current().nextInt(0, this.chromosomeLength)+1;
            while(locations.containsValue(concreteLocation)) {
                concreteLocation = ThreadLocalRandom.current().nextInt(0, this.chromosomeLength)+1;
            }
            locations.put(i, concreteLocation);
            this.genes.get(i).initializeGene(concreteLocation);
        }
        this.evaluate();
    }

    public double getScoreAccumulated() {
        return this.scoreAccumulated;
    }
  
    public abstract double getAdaptation(double cmax, double fmin);

    public void setEscalation(double escalation) {
        this.escalation = escalation;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setAccumulatedScore(double accumulatedScore) {
        this.scoreAccumulated = accumulatedScore;
    }

    public double getEscalation() {
        return this.escalation;
    }
    
    public double getAdaptation(){
        return this.adaptation;
    }

    public void setAdaptation(double adaptation) {
        this.adaptation = adaptation;
    }
    
    public abstract Chromosome copy();
}
