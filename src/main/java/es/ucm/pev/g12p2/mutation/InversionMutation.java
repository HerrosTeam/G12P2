/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.mutation;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import es.ucm.pev.g12p2.chromosome.Function;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static javafx.application.Application.launch;

/**
 *
 * @author PoVALE Team
 */
public class InversionMutation extends Mutation{

    private int point1, point2;
    private double probabilityOfInversion;
    public InversionMutation(double probabilityOfMutation, int populationSize) {
        super(probabilityOfMutation, populationSize);
    }
    
    public InversionMutation(double probabilityOfMutation, int populationSize, int point1, 
            int point2, double probabilityOfInversion) {
        super(probabilityOfMutation, populationSize);
        this.point1 = point1;
        this.point2 = point2;
        this.probabilityOfInversion = probabilityOfInversion;
    }

    @Override
    public List<Chromosome> mutate(List<Chromosome> population) {
        
        boolean mutated;
        double probability;
        for(int i=0; i<this.populationSize; i++){
            mutated=false;
            probability=ThreadLocalRandom.current().nextDouble(0, 1);
            if(probability<this.probabilityOfMutation){
                population.set(i, inverse(population.get(i)));
                mutated=true;
            }
            if(mutated){
                population.get(i).fenotype();
                population.get(i).evaluate();
            }
        }
        return population;
    }
    
    public Chromosome inverse(Chromosome c){
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
        int half = (mutationPoint2 + mutationPoint1) / 2;
        for(int i=mutationPoint1, cont=0; i<=half; i++, cont++){
            int auxAllele = (int)c.getGene(mutationPoint2-cont).getAllele(0);
            c.getGene(mutationPoint2-cont).setAllele(0, c.getGene(i).getAllele(0));
            c.getGene(i).setAllele(0, auxAllele);
        }
        return c.copy();
    } 
    
    
    public List<Chromosome> inversionMutate(List<Chromosome> population) {
        
        boolean mutated;
        double probability;
        for(int i=0; i<this.populationSize; i++){
            mutated=false;
            probability=ThreadLocalRandom.current().nextDouble(0, 1);
            if(probability<this.probabilityOfInversion){
                population.set(i, inverseMut(population.get(i)));
                mutated=true;
            }
            if(mutated){
                population.get(i).fenotype();
                population.get(i).evaluate();
            }
        }
        return population;
    }
    
        public Chromosome inverseMut(Chromosome c){
        int aux;
        if(this.point1 > this.point2){
            aux = this.point2;
            this.point2 = this.point1;
            this.point1 = aux;
        }
        int half = (this.point2 + this.point2) / 2;
        for(int i=this.point1, cont=0; i<=half; i++, cont++){
            int auxAllele = (int)c.getGene(this.point2-cont).getAllele(0);
            c.getGene(this.point2-cont).setAllele(0, c.getGene(i).getAllele(0));
            c.getGene(i).setAllele(0, auxAllele);
        }
        return c.copy();
    } 

}
