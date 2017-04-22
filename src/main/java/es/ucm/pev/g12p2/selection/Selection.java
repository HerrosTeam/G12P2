/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.selection;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.List;

/**
 *
 * @author Herros Team
 */
public abstract class Selection {
    
    public abstract List<Chromosome> select(List<Chromosome> population);
    
   
}
