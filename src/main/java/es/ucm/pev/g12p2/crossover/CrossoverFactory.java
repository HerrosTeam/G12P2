package es.ucm.pev.g12p2.crossover;


/**
 *
 * @author Herros Team
 */
public class CrossoverFactory {
                
    public static Crossover getCrossoverAlgorithm(String crossoverAlgorithm) {
        switch (crossoverAlgorithm) {
            case "PMX":
                return new PMX();
            case "OX":
                return new OX();
            case "OX Prioridad Posicion":
                return new OXPriorityPosition();
            case "OX Prioridad Orden":
                return new OXPriorityOrder();
            case "CX":
                return new CX();
            case "ERX":
                return new ERX();
            case "Codificacion Ordinal":
                return new OrdinalCodification();
            case "OX Modificado Propio":
                return new OXModified();
            default:
                return new PMX();
        }
    }
}
