/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class ERX extends Crossover {

    public ERX() {
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint) {
        List<Chromosome> children = new LinkedList();

        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();
        int currentAllele = 0;
        int crossPoint2 = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
        
        while (crossPoint == crossPoint2){
            crossPoint2 = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
        }
        
        if(crossPoint > crossPoint2){
            int aux = crossPoint;
            crossPoint = crossPoint2;
            crossPoint2 = aux;
        }
        
        for(int i=0; i<parent1.getGenes().size(); i++){
            for(int j=0; j<parent1.getGene(i).getLength(); j++){
                if (currentAllele >= crossPoint && currentAllele <= crossPoint2) {
                child1.getGene(i).setAllele(j,
                        parent2.getGene(i).getAllele(j));
                child2.getGene(i).setAllele(j,
                        parent1.getGene(i).getAllele(j));
                }
                currentAllele++;
            }

        }
 
        child1.evaluate();
        child2.evaluate();
        children.add(child1);
        children.add(child2);

        return children;
    }

}

 