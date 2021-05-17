package biodiversity.controller;

import biodiversity.Constants;

import java.util.ArrayList;
import java.util.List;

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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getNumberOfSpecies() {
        return numberOfSpecies;
    }

    public void setNumberOfSpecies(int numberOfSpecies) {
        this.numberOfSpecies = numberOfSpecies;
    }

    public int getFertility() {
        return fertility;
    }

    public void setFertility(int basicFertility) {
        this.fertility = basicFertility;
    }

    public int getFertilityDiversity() {
        return fertilityDiversity;
    }

    public void setFertilityDiversity(int fertilityDiversity) {
        this.fertilityDiversity = fertilityDiversity;
    }

    public List<SpeciesDTO> getSpeciesDTOs() {
        return speciesDTOs;
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
