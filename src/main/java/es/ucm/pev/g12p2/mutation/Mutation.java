/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.mutation;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.List;

/**
 *
 * @author PoVALE Team
 */
public abstract class Mutation {
    protected double probabilityOfMutation;
    protected int populationSize;
    protected int numberOfMutations;
    
    public Mutation(double probabilityOfMutation, int populationSize){
        this.probabilityOfMutation = probabilityOfMutation;
        this.populationSize = populationSize;
        this.numberOfMutations=0;
    }
    
    public abstract List<Chromosome> mutate(List<Chromosome> population);
    
    public int getNumberOfMutations(){
        return numberOfMutations;
    }
}
