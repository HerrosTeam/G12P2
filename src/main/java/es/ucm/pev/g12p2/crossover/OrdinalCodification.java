/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.HashMap;
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
                        
        List<Integer>l1=new ArrayList<>(); 
        List<Integer>l2=new ArrayList<>(); 
	List<Integer>l11=new ArrayList<>(); 
	List<Integer>l22=new ArrayList<>();
        
	for (int i=1; i<=parent1.getLength(); i++) {
            l1.add(i+1);
            l2.add(i+1);
            l11.add(i+1);
            l22.add(i+1);
	}

        int[]p1=new int[parent1.getLength()];
     	int[]p2=new int[parent1.getLength()];
        
        Map<Integer, Integer> par1 = new HashMap();
        Map<Integer, Integer> par2 = new HashMap();
        
        for(int i=0; i<parent1.getLength(); i++){
            par1.put((int)parent1.getGene(i).getAllele(0), i);
            par2.put((int)parent2.getGene(i).getAllele(0), i);
        }

	int crosspoint = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
        
        for (int i = 0; i < parent1.getLength(); i++) {
            
            int value1 = (int)parent1.getGene(i).getAllele(0);
            p1[i]=l1.indexOf(value1);
            l1.remove(value1);
            
            int value2 = (int)parent2.getGene(i).getAllele(0);
            p2[i]=l2.indexOf(value2);
            l2.remove(value2);
            
            if(i>crosspoint){
                p1[i]=p2[i];
                p2[i]=p1[i];
            }
            
            int i1=p1[i];
            int g1=l11.get(i1);
            l11.remove(i1);
            
            //hijo1.inserta(g1, i);
            child1.getGene(i).setAllele(0, g1);
            
            int i2=p2[i];
            int g2=l22.get(i2);
            l22.remove(i2);
            
            //hijo2.inserta(g2, i);
            child2.getGene(i).setAllele(0, g2);
	}
 
        child1.evaluate();
        child2.evaluate();
        children.add(child1);
        children.add(child2);

        return children;
    }

}

 