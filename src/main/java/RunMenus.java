import adoptives.AdoptiveRepository;
import adoptives.AdoptiveService;
import hamsters.HamsterRepository;
import hamsters.HamsterService;
import hosts.HostRepository;
import hosts.HostService;

public class RunMenus {

    private HamsterRepository hamsterRepository;
    private AdoptiveRepository adoptiveRepository;
    private HostRepository hostRepository;

    private HamsterService hamsterService;
    private HostService hostService;
    private AdoptiveService adoptiveService;

    public RunMenus() {
    }

    public RunMenus(HamsterRepository hamsterRepository, AdoptiveRepository adoptiveRepository, HostRepository hostRepository, HamsterService hamsterService, HostService hostService, AdoptiveService adoptiveService) {
        this.hamsterRepository = hamsterRepository;
        this.adoptiveRepository = adoptiveRepository;
        this.hostRepository = hostRepository;
        this.hamsterService = hamsterService;
        this.hostService = hostService;
        this.adoptiveService = adoptiveService;
    }

    public void selectMainMenuOption(int optionNumber) {
        switch (optionNumber) {
            case 1:
             //   selectHamsterMenuOption();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                 break;
            case 5:
                 break;
            case 6:

                break;
            default:

                break;
        }
    }

    public void selectHamsterMenuOption(int optionNumber) {
        switch (optionNumber) {
            case 1:
                System.out.println();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:

                break;
            default:

                break;
        }
    }
}
