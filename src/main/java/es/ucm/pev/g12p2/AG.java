/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import es.ucm.pev.g12p2.chromosome.Function;
import es.ucm.pev.g12p2.chromosome.gene.Gene;
import es.ucm.pev.g12p2.crossover.Crossover;
import es.ucm.pev.g12p2.elite.Elite;
import es.ucm.pev.g12p2.mutation.InversionMutation;
import es.ucm.pev.g12p2.mutation.Mutation;
import es.ucm.pev.g12p2.selection.Selection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 *
 * @author Herros_Team
 */
public class AG {

    private List<Chromosome> population;
    private int populationSize;

    private int currentGeneration;
    private int maxGenerations;

    private Chromosome bestGeneration;
    private Chromosome bestChromosome;
    private double worstFitness;
    private int bestPosition;

    private double probCrossover;
    private double probMutation;
    private double tolerance; //tolerancia de representacion

    private Crossover crossover;
    private Selection selection;
    private String function;

    private Random randomNumber;
    private Mutation mutation;

    private boolean maximizar;

    private List<Chromosome> eliteChromosomes;
    private boolean elitism;
    private int elitismPopulation;
    // Position of the best chromosome
    private Elite elite;

    private double average;

    private double evolutionaryPressure;
    private int chromosomeLength;
    private FXMLController controller;
    private double[][] graphPoints;
    
    private double[] generationAverage;
    private double[] generationBest;
    private double[] absoluteBest;
    
    private InversionMutation specialInversion;
    private double inversionPercentage;
    private int inversionInitialP;
    private int inversionFinalP;
    
    private String data;
    
    public AG(String data, int populationSize, int max_generations,
            double prob_cross, double prob_mut, double tolerance, int seed, Selection selection, Crossover crossover,
            boolean elitism, Mutation mutation, double inversionPercentage,int inversionInitialP,int inversionFinalP) {
        this.currentGeneration = 0;
        this.data = data;
        this.populationSize = populationSize;
        this.maxGenerations = max_generations;
        this.probCrossover = prob_cross;
        this.probMutation = prob_mut;
        this.tolerance = tolerance;
        this.randomNumber = (seed == 0 ? new Random() : new Random(seed));
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.graphPoints = new double[4][maxGenerations];
        this.elitism = elitism;
        if (elitism) {
            this.elitismPopulation = (int) Math.ceil(this.populationSize*0.02);
            this.elite = new Elite(this.elitismPopulation);
        }
        this.evolutionaryPressure = 1.5;
        
        switch(this.data){
            case "ajuste.dat":
                this.chromosomeLength = 5;
                break;
            case "datos12.dat":
                this.chromosomeLength = 12;
                break;
            case "datos15.dat":
                this.chromosomeLength = 15;
                break;
            case "datos30.dat":
                this.chromosomeLength = 30;
                break;
        }
        
        this.eliteChromosomes = new LinkedList<>();
        this.generationAverage = new double[maxGenerations];
        this.generationBest = new double[maxGenerations];
        this.absoluteBest = new double[maxGenerations];
        this.inversionPercentage = inversionPercentage;
        this.inversionInitialP = inversionInitialP;
        this.inversionFinalP = inversionFinalP;
        this.specialInversion = new InversionMutation(this.inversionPercentage, this.inversionInitialP, this.inversionFinalP);
    }

    public AGView executeAlgorithm() {
        this.initialize();

        this.evaluate();

        while (currentGeneration != maxGenerations) {
           
            if (elitism) {
                this.eliteChromosomes.addAll(0, this.elite.getElite(population));
            }
            
            this.selection();
            
            this.crossover();
            
            this.mutate();
            
            this.specialInversion();
            
            if (elitism) {
                this.population =this.elite.includeEliteRepWorst(this.population, this.eliteChromosomes);
                this.eliteChromosomes.clear();
            }

            this.evaluate();

            this.generationAverage[this.currentGeneration] = getGenerationAvg();
            this.generationBest[this.currentGeneration] = getGenerationBest();
            this.absoluteBest[this.currentGeneration] = getAbsoluteBest();
            
            currentGeneration++;
        }
        
        AGView viewInfo = new AGView(this.generationAverage, this.generationBest, this.absoluteBest,
            this.getAbsoluteBestIndividual(), this.getAbsoluteWorst());
        
        return viewInfo;
    }
    

    private void initialize() {
        this.population = new LinkedList();
        int chromosomePermutations=1;
        List<Integer> basicAllelesForm = new ArrayList();
        for(int i=1; i<=this.chromosomeLength; i++){
            basicAllelesForm.add(i);
            chromosomePermutations*=i;
        }
        List<List<Integer>> allPermutations = getAllPermutations(basicAllelesForm);
        int repeatedChromosomes = this.populationSize / chromosomePermutations + 1;
        
        for(int i=0; i<allPermutations.size() && this.populationSize > population.size(); i++){
            //añadir cromosoma permutado a la poblacion
            Chromosome c = createConcreteChromosome();
            c.inicializeChromosome(allPermutations.get(i));
            //falta copiarlo n veces posibles
            for(int j=0; j<repeatedChromosomes && i+j*chromosomePermutations <this.populationSize; j++){
                population.add(c.copy());
            }
        }
		
        if (elitism) {
            this.elite.initializeMax(this.maximizar);
        }
    }
    
    public List<List<Integer>> getAllPermutations(List<Integer> original) {
        if (original.isEmpty()) { 
          List<List<Integer>> result = new ArrayList<>();
          result.add(new ArrayList<>());
          return result;
        }
        Integer firstElement = original.remove(0);
        List<List<Integer>> permutationsList = new ArrayList<>();
        List<List<Integer>> permutations = getAllPermutations(original);
        for (List<Integer> smallerPermutated : permutations) {
          for (int index=0; index <= smallerPermutated.size(); index++) {
            List<Integer> temp = new ArrayList<>(smallerPermutated);
            temp.add(index, firstElement);
            permutationsList.add(temp);
          }
        }
        return permutationsList;
    }
    

    public void evaluate() {
        if(this.currentGeneration==0){
            this.bestChromosome = this.population.get(0);
            this.worstFitness = this.population.get(0).getFitness();
        }
        this.bestGeneration = this.population.get(0).copy();
        double sumFitness = 0;
        double fmin = bestGeneration.getFitness();
        double cmax = bestGeneration.getFitness();

        for (int i = 0; i < this.populationSize; i++) {
            double currentFitness = this.population.get(i).getFitness();
            sumFitness += currentFitness;

            if (maximizar) {
                if (currentFitness > this.bestGeneration.getFitness()) {
                    bestGeneration = this.population.get(i).copy();
                }
            } else if (!maximizar) {
                if (currentFitness < this.bestGeneration.getFitness()) {
                    bestGeneration = this.population.get(i).copy();
                }
                if (currentFitness > this.worstFitness) {
                    this.worstFitness = currentFitness;
                }
                
            }

            if (currentFitness < fmin) {
                fmin = currentFitness;
            }
            if (currentFitness > cmax) {
                cmax = currentFitness;
            }
        }

        if (maximizar) {
            if (bestGeneration.getFitness() > this.bestChromosome.getFitness()) {
                bestChromosome = bestGeneration.copy();
            }
        } else if (!maximizar) {
            if (bestGeneration.getFitness() < this.bestChromosome.getFitness()) {
                bestChromosome = bestGeneration.copy();
            }   
        }
        
        this.average = sumFitness / this.populationSize;
        this.adaptation(cmax, fmin);
    }

    public void adaptation(double cmax, double fmin) {
        //desplazamiento de la adaptación
        cmax *= 1.05;
        if (cmax >= 0) {
            cmax *= 1.05;
        } else {
            cmax *= 0.95;
        }
        fmin = Math.abs(fmin);

        double sumAdaptations = 0;

        for (int i = 0; i < this.populationSize; i++) {
            sumAdaptations += this.population.get(i).getAdaptation(cmax, fmin);
        }

        double avgAdaptations = sumAdaptations / this.populationSize;
        double a = ((this.evolutionaryPressure - 1) * avgAdaptations) / (this.bestGeneration.getAdaptation(cmax, fmin) - avgAdaptations);
        double b = (1 - a) * avgAdaptations;
        double sumEscalation = 0;
        for (int i = 0; i < this.populationSize; i++) {
            double escalation = (a * this.population.get(i).getAdaptation(cmax, fmin)) + b;
            this.population.get(i).setEscalation(escalation);
            sumEscalation += escalation;
        }

        double score = 0;
        double accumulatedScore = 0;

       for (int i = 0; i < this.populationSize; i++) {//calcular puntuaciones y puntuaciones acumuladas
            score = this.population.get(i).getEscalation() / sumEscalation;
            accumulatedScore += score;
            this.population.get(i).setScore(score);
            this.population.get(i).setAccumulatedScore(accumulatedScore);
        }
    }
    
    private void specialInversion(){
        for(int i=0; i<this.populationSize; i++){
            Chromosome cInversed = this.specialInversion.specialInverse(this.population.get(i));
            if (maximizar) {
                if (cInversed.getFitness() > this.population.get(i).getFitness()) {
                    this.population.set(i, cInversed.copy());
                }
            } else if (!maximizar) {
                if (cInversed.getFitness() < this.population.get(i).getFitness()) {
                    this.population.set(i, cInversed.copy());
                }
            }
        }
    }

    public void selection() {
        this.population = this.selection.select(this.population);
    }

    public void crossover() {
        int[] sel_cross = new int[this.populationSize];
        int num_sel_cross = 0;
        double prob;
        int cross_point;
        for (int i = 0; i < this.populationSize; i++) {
            prob = randomNumber.nextDouble();
            if (prob < this.probCrossover) {
                sel_cross[num_sel_cross] = i;
                num_sel_cross++;
            }
        }

        if ((num_sel_cross % 2) == 1) {
            num_sel_cross--;
        }
        cross_point = randomNumber.nextInt(this.population.get(0).getLength());
        for (int j = 0; j < num_sel_cross; j += 2) {
            Chromosome parent1 = population.get(sel_cross[j]).copy();
            Chromosome parent2 = population.get(sel_cross[j + 1]).copy();
            
            List<Chromosome> children = this.crossover.crossover(parent1, parent2, cross_point);

            population.set(sel_cross[j], children.get(0).copy());
            population.set(sel_cross[j + 1], children.get(1).copy());
        }
    }
    
    public void mutate(){
        this.population = this.mutation.mutate(this.population);
    }

    private Chromosome createConcreteChromosome() {
        this.maximizar = false;
        return new Function(this.tolerance, this.chromosomeLength, this.maximizar, this.data);
    }

    public int getGeneration() {
        return this.currentGeneration;
    }
    
    public double getAbsoluteBest(){
        return this.bestChromosome.getFitness();
    }
    
    public double getAbsoluteWorst(){
        return this.worstFitness;
    }
    
    public String getAbsoluteBestIndividual(){
        StringBuilder individual = new StringBuilder();
        this.bestChromosome.getGenes().stream().forEach((g) -> {
            individual.append(g.getAllele(0).toString());
        });
        return individual.toString();
    }
    
    public double getGenerationBest(){
        return this.bestGeneration.getFitness();
    }
    
    public double getGenerationAvg(){
        return this.average;
    }
    
}
 
