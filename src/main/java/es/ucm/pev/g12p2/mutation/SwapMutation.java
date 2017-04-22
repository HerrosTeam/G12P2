/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.mutation;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author PoVALE Team
 */
public class SwapMutation extends Mutation{

    public SwapMutation(double probabilityOfMutation, int populationSize) {
        super(probabilityOfMutation, populationSize);
    }

    @Override
    public List<Chromosome> mutate(List<Chromosome> population) {
        
        boolean mutated;
        double probability;
        for(int i=0; i<this.populationSize; i++){
            mutated=false;
            probability=ThreadLocalRandom.current().nextDouble(0, 1);
            if(probability<this.probabilityOfMutation){
                population.set(i, swap(population.get(i)));
                mutated=true;
            }
            if(mutated){
                population.get(i).fenotype();
                population.get(i).evaluate();
            }
        }
        return population;
    }
    
    public Chromosome swap(Chromosome c){
        int mutationPoint1, mutationPoint2, aux;
        mutationPoint1 = ThreadLocalRandom.current().nextInt(0, c.getLength());
        mutationPoint2 = ThreadLocalRandom.current().nextInt(0, c.getLength());
        while (mutationPoint1 == mutationPoint2){
            mutationPoint2 = ThreadLocalRandom.current().nextInt(0, c.getLength());
        }
        if(mutationPoint1 > mutationPoint2){
            aux = mutationPoint2;
            mutationPoint2 = mutationPoint1;
            mutationPoint1 = aux;
        }
        int auxAllele = (int)c.getGene(mutationPoint2).getAllele(0);
        c.getGene(mutationPoint2).setAllele(0, c.getGene(mutationPoint1).getAllele(0));
        c.getGene(mutationPoint1).setAllele(0, auxAllele);
        
        return c.copy();
    }
    
}
