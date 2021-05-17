package biodiversity.model.organism;

import biodiversity.model.organism.behavior.Behavior;

import java.util.ArrayList;
import java.util.List;

public class Species {

    private final char sign;

    private final Behavior behavior;

    private List<EvolutionaryLine> evolutionaryLines; // only for creating new EvolutionaryLines and statistics

    public Species(char sign, Behavior behavior) {
        this.sign = sign;
        this.behavior = behavior;
        this.evolutionaryLines = new ArrayList<>();
    }


    public void doOutsourcedFunctions(Organism organism){
        behavior.doOutsourcedFunctions(organism);
    }

    public char getSign() {
        return sign;
    }

    public void addEvolutionaryLine(EvolutionaryLine evolutionaryLine){
        evolutionaryLines.add(evolutionaryLine);
    }

    public List<EvolutionaryLine> getEvolutionaryLines() {
        return evolutionaryLines;
    }
}
