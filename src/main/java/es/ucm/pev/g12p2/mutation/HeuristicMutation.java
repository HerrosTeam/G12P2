/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.mutation;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author PoVALE Team
 */
public class HeuristicMutation extends Mutation{

    private int numSelected;
    private boolean max;
    public HeuristicMutation(double probabilityOfMutation, int populationSize, int numSelected, boolean max) {
        super(probabilityOfMutation, populationSize);
        this.numSelected = numSelected;
        this.max = max;
    }

    @Override
    public List<Chromosome> mutate(List<Chromosome> population) {
        
        boolean mutated;
        double probability;
        for(int i=0; i<this.populationSize; i++){
            mutated=false;
            probability=ThreadLocalRandom.current().nextDouble(0, 1);
            if(probability<this.probabilityOfMutation){
                population.set(i, heuristicMutation(population.get(i)));
                mutated=true;
            }
            if(mutated){
                population.get(i).fenotype();
                population.get(i).evaluate();
                this.numberOfMutations++;
            }
        }
        return population;
    }
    
    public Chromosome heuristicMutation(Chromosome c){
        Map <Integer,Integer>positionsSelected = new HashMap();
        List<Integer> originalValues = new ArrayList();
        int pos;
        c.fenotype();
        c.evaluate();
        Chromosome bestChromosome = c.copy();
        for(int i=0; i <this.numSelected; i++){
            pos = ThreadLocalRandom.current().nextInt(0, c.getLength());
            while (positionsSelected.containsValue(pos)){
                pos = ThreadLocalRandom.current().nextInt(0, c.getLength());
            }
            positionsSelected.put(i, pos);
            originalValues.add((int)c.getGene(pos).getAllele(0));
        }
        
        List<List<Integer>> allPermutations = getAllPermutations(originalValues);
        
        for(int i=0; i<allPermutations.size(); i++){
            //crear cromosoma añadiendo los valores permutados en las posiciones adecuadas
            for(int j=0; j<this.numSelected; j++){
                c.getGene(positionsSelected.get(j)).setAllele(0, allPermutations.get(i).get(j));
            }
            //calcular fenotypo
            c.fenotype();
            c.evaluate();
            //ver si es el mejor
            if(max){
                if(c.getFitness()>bestChromosome.getFitness()){
                    bestChromosome = c.copy();
                }
            }
            else{
                if(c.getFitness()<bestChromosome.getFitness()){
                    bestChromosome = c.copy();
                }
            }
        }
        
        return bestChromosome.copy();
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
    
}
