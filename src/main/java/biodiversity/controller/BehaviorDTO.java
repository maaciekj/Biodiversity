package biodiversity.controller;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BehaviorDTO {

    private String feedingStrategy;
    private String replicationStrategy;
    private List<String> specialFeatures;

    public BehaviorDTO() {
    }

    public BehaviorDTO(String feedingStrategy, String replicationStrategy) {
        this.feedingStrategy = feedingStrategy;
        this.replicationStrategy = replicationStrategy;
        specialFeatures = new ArrayList<>();
    }

}
