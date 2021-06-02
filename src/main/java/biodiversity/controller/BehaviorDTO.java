package biodiversity.controller;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BehaviorDTO {

    private String feedingStrategy;
    private String replicationStrategy;
    private List<String> specialFeatures;

}
