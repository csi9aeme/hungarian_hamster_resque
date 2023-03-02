package hamsters;

public enum Species {

    GOLDEN("szíriai aranyhörcsög"),
    HYBRID_DWARF("hibrid dzsgunráiai törpehörcsög"),
    DWARF("dzsungáriai törpehörcsög"),
    CAMPBELL("campbell törpehörcsög"),
    ROBOROVSKI("roborovszki törpehörcsög"),
    CHINESE("kínai törpehörcsög");

    private String nameOfSpecies;

    Species(String nameOfSpecies) {
        this.nameOfSpecies = nameOfSpecies;
    }

    public String getNameOfSpecies() {
        return nameOfSpecies;
    }
}
