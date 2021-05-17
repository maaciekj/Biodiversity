package biodiversity.model.special_events;

import biodiversity.model.territory.Counter;
import biodiversity.model.territory.NumberGeneratorRandom;
import biodiversity.model.territory.Territory;

public class SpecialEventManager {

    private Territory territory;
    private Counter counter;
    private NumberGeneratorRandom numberGenerator;

    public SpecialEventManager(Territory territory, Counter counter, NumberGeneratorRandom numberGenerator) {
        this.territory = territory;
        this.counter = counter;
        this.numberGenerator = numberGenerator;
    }

    /*
    public SpecialEvent createSpecialEvent(){
    }
     */

}
