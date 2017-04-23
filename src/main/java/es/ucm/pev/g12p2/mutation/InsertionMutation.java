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
public class InsertionMutation extends Mutation{

    private int numInsertions;
    
    public InsertionMutation(double probabilityOfMutation, int populationSize, int numInsertions) {
        super(probabilityOfMutation, populationSize);
        this.numInsertions = numInsertions;
    }

    @Override
    public List<Chromosome> mutate(List<Chromosome> population) {
        
        boolean mutated;
        double probability;
        for(int i=0; i<this.populationSize; i++){
            mutated=false;
            probability=ThreadLocalRandom.current().nextDouble(0, 1);
            if(probability<this.probabilityOfMutation){
                population.set(i, insert(population.get(i)));
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
    
    public Chromosome insert(Chromosome c){
        int originPoint, originValue, destinationPoint;
        for(int i=0; i<this.numInsertions; i++){
            originPoint = ThreadLocalRandom.current().nextInt(0, c.getLength());
            destinationPoint = ThreadLocalRandom.current().nextInt(0, c.getLength());
 
            while (originPoint == destinationPoint){
                destinationPoint = ThreadLocalRandom.current().nextInt(0, c.getLength());
            }
            
            originValue = (int) c.getGene(originPoint).getAllele(0);
            for(int j=originPoint; j!=destinationPoint; j--){
                if(j==0){
                    c.getGene(j).setAllele(0, c.getGene(c.getLength()-1).getAllele(0));
                    j=c.getLength();
                }else{
                    c.getGene(j).setAllele(0, c.getGene(j-1).getAllele(0));
                }
            }
            c.getGene(destinationPoint).setAllele(0, originValue);
        }
        return c.copy();
    }
    
}
