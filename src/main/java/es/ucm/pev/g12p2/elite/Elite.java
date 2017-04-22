/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.elite;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PoVALE Team
 */
public class Elite {
    
    private List<Chromosome> currentPopulation;
    private int eliteSize;
    private boolean maximizar;
    
    public Elite(int eliteSize) {
        this.eliteSize = eliteSize;
    }
    
    public List<Chromosome> getElite(List<Chromosome> currentPopulation){
        List<Chromosome> elite = new LinkedList();
        this.currentPopulation=currentPopulation;
        this.quickSort(0, this.currentPopulation.size()-1); //ordered from lowest to highest fitness
        for(int i=0; i<this.eliteSize; i++){
            if(maximizar){
                elite.add(i, this.currentPopulation.get(this.currentPopulation.size()-1-i));
            }else{
                elite.add(i, this.currentPopulation.get(i));
            }
        }
        return elite;
    }
    
    public void quickSort(int izq, int der) {

      Chromosome pivote=currentPopulation.get(izq).copy(); // tomamos primer elemento como pivote
      int i=izq; // i realiza la búsqueda de izquierda a derecha
      int j=der; // j realiza la búsqueda de derecha a izquierda
      Chromosome aux;

      while(i<j){            // mientras no se crucen las búsquedas
         while(currentPopulation.get(i).getFitness()<=pivote.getFitness() && i<j) i++; // busca elemento mayor que pivote
         while(currentPopulation.get(j).getFitness()>pivote.getFitness()) j--;         // busca elemento menor que pivote
         if (i<j) {                      // si no se han cruzado                      
             aux= currentPopulation.get(i).copy();                  // los intercambia
             currentPopulation.set(i, currentPopulation.get(j).copy());
             currentPopulation.set(j,aux.copy());
         }
       }
       currentPopulation.set(izq, currentPopulation.get(j).copy());
       currentPopulation.set(j,pivote.copy());
       if(izq<j-1)
          quickSort(izq,j-1); // ordenamos subarray izquierdo
       if(j+1 <der)
          quickSort(j+1,der); // ordenamos subarray derecho
    }
    
    public List<Chromosome> includeEliteRepWorst(List<Chromosome> population, List<Chromosome> eliteChromosomes) {

        this.currentPopulation = population;
        this.quickSort(0, this.currentPopulation.size()-1); //ordered from lowest to highest fitness
        for(int i=0; i<this.eliteSize; i++){
            if(maximizar){
                this.currentPopulation.set(i,eliteChromosomes.get(i));
            }else{
                this.currentPopulation.set(this.currentPopulation.size()-1-i, eliteChromosomes.get(i));
            }
        }
        return this.currentPopulation;
    }
    
    public void initializeMax(boolean max){
        this.maximizar = max;
    }
}
