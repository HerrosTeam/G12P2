package es.ucm.pev.g12p2.crossover;


/**
 *
 * @author Herros Team
 */
public class CrossoverFactory {

    public static Crossover getCrossoverAlgorithm(String crossoverAlgorithm) {
        switch (crossoverAlgorithm) {
            case "Monopunto":
                return new SinglePoint();
            case "Multipunto":
                return new TwoPoint();
            case "PMX":
                return new PMX();
            case "OX":
                return new OX();
            case "OX con orden prioritario":
                return new OXPriorityOrder();
            case "OX con posiciones prioritarias":
                return new OXPriorityOrder();
            case "CX":
                return new CX();
            case "ERX":
                return new ERX();
            case "Codificación Ordinal":
                return new OrdinalCodification();
            default:
                return new SinglePoint();
        }
    }
}
