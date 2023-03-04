package hamsters;

import additionalClasses.Validator;

import java.util.Set;

public class HamsterService {

    private HamsterRepository hamsterRepository;
    private Validator validator = new Validator();

    public HamsterService(HamsterRepository hamsterRepository) {
        this.hamsterRepository = hamsterRepository;
    }

    //új hörcsög felvétele az adatbázisba + ideiglenes befogadó beállítása;
    //ha ugyanaz a név, adjon figylmeztetést
    
    public void saveHamster(Hamster hamster) {
        validator.checkName(hamster.getName()); //elég hosszú-e
        Set<Hamster> hamstersWithSameName= hamsterRepository.findHamstersByName(hamster.getName());
        if (hamstersWithSameName.size() != 0) {
            throw new IllegalArgumentException("A választott név már létezik az adatbázisban.\nFolytatod?");
        }

        hamsterRepository.saveHamster(hamster);
    }



    //hörcsög keresése id alapján ( getSingleResult)

    //hörcsög keresése név alapján (getResultList)

    //hörcsög keresése faj alapján



}
