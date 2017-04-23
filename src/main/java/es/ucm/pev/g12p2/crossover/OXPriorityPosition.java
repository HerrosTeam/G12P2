/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class OXPriorityPosition extends Crossover {

    public OXPriorityPosition() {
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint) {
        List<Chromosome> children = new LinkedList();
        //copiamos los padres
        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();

        //obtenemos el numero de posiciones que seseleccionaran
        int numSelected = ThreadLocalRandom.current().nextInt(1, parent1.getLength());
        Map<Integer, Integer> selectedValuesParent1 = new LinkedHashMap();
        Map<Integer, Integer> selectedValuesParent2 = new LinkedHashMap();
        
        //intercambiamos los alelos de las posiciones seleccionadas
        int randomPos;
        for(int i=0; i< numSelected; i++){
            randomPos = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
            while(selectedValuesParent1.containsValue(randomPos)){
                randomPos = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
            }
            child1.getGene(randomPos).setAllele(0,parent2.getGene(randomPos).getAllele(0));
            selectedValuesParent1.put((int)parent2.getGene(randomPos).getAllele(0), randomPos);
            child2.getGene(randomPos).setAllele(0,parent1.getGene(randomPos).getAllele(0));
            selectedValuesParent2.put((int)parent1.getGene(randomPos).getAllele(0), randomPos);
        }
        
        child1 = doOX(child1, child2, selectedValuesParent1);
        child2 = doOX(child2, child1, selectedValuesParent2);
       
        child1.evaluate();
        child2.evaluate();
        children.add(child1);
        children.add(child2);

        return children;
    }
    
    public Chromosome doOX(Chromosome child1, Chromosome child2, Map usedValuesChild){
        
        int currentI=0;
        boolean endLoop = false;
        //copiamos los alelos restantes validos del hijo1
        for(int i=0; i!=child1.getLength() && !endLoop; i++){
            
            if(!usedValuesChild.containsValue(i)){
                if(usedValuesChild.containsKey(child1.getGene(i).getAllele(0)) && currentI != child1.getLength())
                {
                    while(currentI != child1.getLength() && usedValuesChild.containsKey(child1.getGene(currentI).getAllele(0)))
                    {
                        currentI++;
                    }
                    if(currentI == child1.getLength()){
                        endLoop=true;
                    }
                    else if(!usedValuesChild.containsKey((int)child1.getGene(currentI).getAllele(0))){
                        child1.getGene(i).setAllele(0, child1.getGene(currentI).getAllele(0));
                        usedValuesChild.put((int)child1.getGene(i).getAllele(0), i);
                    }
                }else if(!usedValuesChild.containsKey((int)child1.getGene(i).getAllele(0))){
                    usedValuesChild.put((int)child1.getGene(i).getAllele(0), i);
                }
            }
        }
        
        //copiamos los restantes del hijo2
        currentI=0;    
        for(int i=0; i< child1.getLength() && usedValuesChild.size() != child1.getLength(); i++){
            if(!usedValuesChild.containsValue(i)){
                while(currentI != child1.getLength() && usedValuesChild.containsKey((int)child2.getGene(currentI).getAllele(0))){
                    currentI++;
                }
                if(currentI != child1.getLength() && !usedValuesChild.containsKey((int)child2.getGene(currentI).getAllele(0))){
                        child1.getGene(i).setAllele(0, child2.getGene(currentI).getAllele(0));
                        usedValuesChild.put((int)child1.getGene(i).getAllele(0), i);
                }
            }
        }
        return child1.copy();
    }

}

 