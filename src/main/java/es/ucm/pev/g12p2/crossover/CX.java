/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class CX extends Crossover {

    public CX() {
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint) {
        List<Chromosome> children = new LinkedList();

        Chromosome child1 = parent2.copy();
        Chromosome child2 = parent1.copy();
        
        Map<Integer, Integer> p1 = new HashMap();
        Map<Integer, Integer> p2 = new HashMap();
        
        for(int i=0; i<parent1.getLength(); i++){
            p1.put((int)parent1.getGene(i).getAllele(0), i);
            p2.put((int)parent2.getGene(i).getAllele(0), i);
        }
        
        child1.getGene(0).setAllele(0, parent1.getGene(0).getAllele(0));
        int currentGene = (int) parent2.getGene(0).getAllele(0);
        while(currentGene != (int) parent1.getGene(0).getAllele(0)){
            int index =  p1.get((int)parent2.getGene(p2.get(currentGene)).getAllele(0));
            child1.getGene(index).setAllele(0, parent1.getGene(index).getAllele(0));
            currentGene = (int) parent2.getGene(index).getAllele(0);
        }
        
        child2.getGene(0).setAllele(0, parent2.getGene(0).getAllele(0));
        currentGene = (int) parent1.getGene(0).getAllele(0);
        
        while(currentGene != (int) parent2.getGene(0).getAllele(0)){
            int index =  p2.get((int)parent1.getGene(p1.get(currentGene)).getAllele(0));
            child2.getGene(index).setAllele(0, parent2.getGene(index).getAllele(0));
            currentGene = (int) parent1.getGene(index).getAllele(0);
        }
 
        child1.evaluate();
        child2.evaluate();
        children.add(child1);
        children.add(child2);

        return children;
    }

}

 