package hamsters;

public enum HamsterStatus {

    ADOPTABLE("örökbefogadható"),
    ADOPTED ("örökbefogadott"),
    UNDER_MEDICAL_TREATMENT("kezelés alatt áll, de örökbefogadható"),
    PERMANENTLY_CARED_FOR("tartós gondozott, nem örökbefogadható"),
    DECEASED("elhunyt");

    private String status;

    HamsterStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
