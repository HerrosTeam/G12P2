/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.chromosome.gene;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author usuario_local
 */
public abstract class Gene {
    
    protected List<Object> allele;
    protected int geneLength;
    
    public Gene(int geneLength) {
        this.allele = new LinkedList();
        this.geneLength = geneLength;
    }

    public int getLength(){
        return geneLength;
    }
    
    public Object getAllele(int pos){
        return allele.get(pos);
    }
    
    public void setAllele(int pos, Object allele){
        this.allele.set(pos, allele);
    }
    
    public List<Object> getAlleleList(){
        return this.allele;
    }

    public abstract void initializeGene(int location);
    
    public abstract void mutate(int i);

    public abstract Gene copy();
}
