package biodiversity.controller;

import biodiversity.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TerritoryDTO {

    private int height = Constants.TERRITORY_HEIGHT_DEFAULT;
    private int width = Constants.TERRITORY_WIDTH_DEFAULT;
    private int numberOfSpecies;
    private int fertility;
    private int fertilityDiversity;
    private List<SpeciesDTO> speciesDTOs = new ArrayList<>();

    public TerritoryDTO(){

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TerritoryDTO)) return false;

        TerritoryDTO that = (TerritoryDTO) o;

        if (getHeight() != that.getHeight()) return false;
        if (getWidth() != that.getWidth()) return false;
        if (getNumberOfSpecies() != that.getNumberOfSpecies()) return false;
        if (getFertility() != that.getFertility()) return false;
        if (getFertilityDiversity() != that.getFertilityDiversity()) return false;
        return getSpeciesDTOs() != null ? getSpeciesDTOs().equals(that.getSpeciesDTOs()) : that.getSpeciesDTOs() == null;
    }

    @Override
    public int hashCode() {
        int result = getHeight();
        result = 31 * result + getWidth();
        result = 31 * result + getNumberOfSpecies();
        result = 31 * result + getFertility();
        result = 31 * result + getFertilityDiversity();
        result = 31 * result + (getSpeciesDTOs() != null ? getSpeciesDTOs().hashCode() : 0);
        return result;
    }
}
