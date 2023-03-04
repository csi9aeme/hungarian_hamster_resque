package hamsters;

import additionalClasses.Validator;

public class HamsterService {

    private HamsterRepository hamsterRepository;
    private Validator validator = new Validator();

    public HamsterService(HamsterRepository hamsterRepository) {
        this.hamsterRepository = hamsterRepository;
    }

    //új hörcsög felvétele az adatbázisba + ideiglenes befogadó beállítása;
    //ha ugyanaz a név, adjon figylmeztetést
    
    public void saveHamster(Hamster hamster) {
        validator.checkName(hamster.getName());

        hamsterRepository.saveHamster(hamster);
    }



    //hörcsög keresése id alapján ( getSingleResult)

    //hörcsög keresése név alapján (getResultList)

    //hörcsög keresése faj alapján



}
