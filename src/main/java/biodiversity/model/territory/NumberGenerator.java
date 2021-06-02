package biodiversity.model.territory;

public interface NumberGenerator {

    int generateRandomInt (int maxExcl);

    boolean generateBoolean();

    double generateDouble();
}
