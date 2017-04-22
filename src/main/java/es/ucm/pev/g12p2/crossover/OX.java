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
public class OX extends Crossover {

    public OX() {
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint) {
        List<Chromosome> children = new LinkedList();
        //copiamos los padres
        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();

        int crossPoint2 = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
        
        while (crossPoint == crossPoint2){
            crossPoint2 = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
        }
        
        if(crossPoint > crossPoint2){
            int aux = crossPoint;
            crossPoint = crossPoint2;
            crossPoint2 = aux;
        }
        Map<Integer, Boolean> usedValuesChild1 = new HashMap(); 
        Map<Integer, Boolean> usedValuesChild2 = new HashMap();
        //intercambiamos los alelos pertenecientes a [crossPoint,  crosPoint2]
        for(int i=crossPoint; i<=crossPoint2; i++){
            child1.getGene(i).setAllele(0,parent2.getGene(i).getAllele(0));
            usedValuesChild1.put((int)child1.getGene(i).getAllele(0), true);
            
            child2.getGene(i).setAllele(0,parent1.getGene(i).getAllele(0));
            usedValuesChild2.put((int)child2.getGene(i).getAllele(0), true);
        }
        
        child1 = doOX(child1, child2, crossPoint, crossPoint2, usedValuesChild1);
        child2 = doOX(child2, child1, crossPoint, crossPoint2, usedValuesChild2);
       
        child1.evaluate();
        child2.evaluate();
        children.add(child1);
        children.add(child2);

        return children;
    }
    
    public Chromosome doOX(Chromosome child1, Chromosome child2, int crossPoint, int crossPoint2, Map usedValuesChild1){
        int positionsSavedChild1=crossPoint2-crossPoint+1;
        int currentI=0;
        for(int i=crossPoint2+1; i!=crossPoint; i++){
            i=i%child1.getLength();
            if(crossPoint2+1 == i){
                currentI=i;
            }
            boolean endLoop = false;
            for(int j=crossPoint; j<=crossPoint2 && !endLoop; j++)
            {
                currentI = currentI % child1.getLength();
                if((child1.getGene(i).getAllele(0) == child1.getGene(j).getAllele(0) 
                        || usedValuesChild1.containsKey((int)child1.getGene(currentI).getAllele(0)))
                        && currentI != crossPoint)
                {
                    while((child1.getGene(currentI).getAllele(0) == child1.getGene(j).getAllele(0) 
                            || usedValuesChild1.containsKey((int)child1.getGene(currentI).getAllele(0)))
                            && currentI != crossPoint)
                    {
                        currentI++;
                        currentI = currentI % child1.getLength();
                    }
                    if(!usedValuesChild1.containsKey((int)child1.getGene(currentI).getAllele(0))){
                        child1.getGene(i).setAllele(0, child1.getGene(currentI).getAllele(0));
                        usedValuesChild1.put((int)child1.getGene(i).getAllele(0), true);
                        endLoop = true;
                        positionsSavedChild1++;
                    }
                }else if(!usedValuesChild1.containsKey((int)child1.getGene(currentI).getAllele(0))){
                    usedValuesChild1.put((int)child1.getGene(i).getAllele(0), true);
                    endLoop = true;
                    positionsSavedChild1++;
                }
            }
        }

        for(int i=crossPoint; i<=crossPoint2 && positionsSavedChild1 != child1.getLength(); i++){
            
            if(!usedValuesChild1.containsKey((int)child2.getGene(i).getAllele(0))){
                int correctPosition = crossPoint - child1.getLength() + positionsSavedChild1;
                if(correctPosition<0){
                    correctPosition = child1.getLength()+ correctPosition;
                }
                child1.getGene(correctPosition).setAllele(0, child2.getGene(i).getAllele(0));
                usedValuesChild1.put((int)child1.getGene(correctPosition).getAllele(0), true);
                positionsSavedChild1++;
            }
        }
        return child1.copy();
    }

}

 