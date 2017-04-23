/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class OrdinalCodification extends Crossover {

            
    public OrdinalCodification() {
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint) {
        List<Chromosome> children = new LinkedList();

        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();
        
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
       
	for (int i=1; i<=parent1.getLength(); i++) {
            list.add(i);
            list2.add(i);
	}
        
        
        for (int i=0; i<parent1.getLength(); i++) {
            int currentValue = (int)parent1.getGene(i).getAllele(0);
            int pos = list.indexOf(currentValue);
            child1.getGene(i).setAllele(0,pos+1);
            list.remove(pos);
            
            int currentValue2 = (int)parent2.getGene(i).getAllele(0);
            int pos2 = list2.indexOf(currentValue2);
            child2.getGene(i).setAllele(0,pos2+1);
            list2.remove(pos2);
	}
        
        Chromosome child1cross = child1.copy();
        Chromosome child2cross = child2.copy();

        for(int i=crossPoint; i<parent1.getLength(); i++){
            child1cross.getGene(i).setAllele(0,child2.getGene(i).getAllele(0));
            child2cross.getGene(i).setAllele(0,child1.getGene(i).getAllele(0));
        }
        
        child1cross.evaluate();
        child2cross.evaluate();
        children.add(child1cross);
        children.add(child2cross);

        return children;
    }
}

 