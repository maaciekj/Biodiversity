package biodiversity.model.territory;

import java.security.SecureRandom;

public class NumberGeneratorSecureRandom implements NumberGenerator {

    SecureRandom random = new SecureRandom();

    @Override
    public int generateRandomInt (int maxExcl){
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
