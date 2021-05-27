package biodiversity.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BehaviorDTO {

    private String feedingStrategy;
    private String replicationStrategy;
    private List<String> specialFeatures;

    public BehaviorDTO() {
    }

}
