package biodiversity.model.organism.behavior;

import biodiversity.model.organism.Organism;

public abstract class BehaviorDecorator extends Behavior {

    private Behavior behavior;

    public BehaviorDecorator(Behavior behavior) {
        super(behavior);
        this.behavior = behavior;
    }

    @Override
    public void doOutsourcedFunctions(Organism organism) {
        behavior.doOutsourcedFunctions(organism);
        migrate(organism);
    }

    protected void migrate(Organism organism){
    }

    protected void moveTo (Organism organism, int newRow, int newCol){
        int oldRow = organism.getRow();
        int oldCol = organism.getCol();
        organism.setRow(newRow);
        organism.setCol(newCol);
        territory.updateCoordinates(organism);
        territory.removeInhabitant(oldRow, oldCol);
    }

}
