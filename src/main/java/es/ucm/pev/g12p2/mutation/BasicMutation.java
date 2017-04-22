/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.mutation;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author PoVALE Team
 */
public class BasicMutation extends Mutation{

    public BasicMutation(double probabilityOfMutation, int populationSize) {
        super(probabilityOfMutation, populationSize);
    }

    @Override
    public List<Chromosome> mutate(List<Chromosome> population) {
        
        boolean mutated;
        double probability;
        for(int i=0; i<this.populationSize; i++){
            mutated=false;
            for(int j=0; j<population.get(i).getGenes().size(); j++){
                for(int k=0; k<population.get(i).getGene(j).getLength(); k++){
                    probability=ThreadLocalRandom.current().nextDouble(0, 1);
                    if(probability<this.probabilityOfMutation){
                        population.get(i).getGene(j).mutate(k);
                        mutated=true;
                    }
                }
            }
            if(mutated){
                population.get(i).fenotype();
                population.get(i).evaluate();
            }
        }
        return population;
    }
    
}
