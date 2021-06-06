package biodiversity.controller;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BehaviorDTO {

    private String feedingStrategy;
    private String replicationStrategy;

}
