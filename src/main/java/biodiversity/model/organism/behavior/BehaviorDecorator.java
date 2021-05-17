package biodiversity.model.organism.behavior;

import biodiversity.model.organism.Organism;
import biodiversity.model.organism.actions.Action;
import biodiversity.model.special_events.SpecialEvent;

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

    @Override
    public void reactOnAction(Action action, Organism acting, Organism reacting) {
        behavior.reactOnAction(action, acting, reacting);
    }

    @Override
    public void reactOnSpecialEvent(SpecialEvent specialEvent, Organism reacting){
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
