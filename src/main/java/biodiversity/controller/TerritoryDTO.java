package biodiversity.controller;

import biodiversity.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TerritoryDTO {

    private final int height = Constants.TERRITORY_HEIGHT_DEFAULT;
    private final int width = Constants.TERRITORY_WIDTH_DEFAULT;
    private int numberOfSpecies;
    private int fertility;
    private int fertilityDiversity;
    private List<SpeciesDTO> speciesDTOs;

    public TerritoryDTO() {
        speciesDTOs = new ArrayList<>();
    }

    public void addSpeciesDTO(SpeciesDTO speciesDTO){
        speciesDTOs.add(speciesDTO);
    }

    public int getOrdinalNumberOfSpeciesToBeCreated(){
        return speciesDTOs.size()+1;
    }

    @Override
    public String toString() {
        return "TerritoryDTO{" +
                "numberOfSpecies=" + numberOfSpecies +
                ", basicFertility=" + fertility +
                ", fertilityDiversity=" + fertilityDiversity +
                ", speciesDTOs=" + speciesDTOs +
                '}';
    }
}
