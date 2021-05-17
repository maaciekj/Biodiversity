package biodiversity.model.territory;

import java.util.Random;

public class NumberGeneratorRandom implements NumberGenerator {

    Random random = new Random();

    @Override
    public int generateRandomInt(int maxExcl) {
        return random.nextInt(maxExcl);
    }

    @Override
    public boolean generateBoolean() {
        return random.nextBoolean();
    }

    @Override
    public double generateDouble() {
        return random.nextDouble();
    }
}
