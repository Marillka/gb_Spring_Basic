package context.homework.doctor;

public class Doctor {
    private String name;
    private String specialization;
    private int office;

    public String getName() {
        return name;
    }

    public int getOffice() {
        return office;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Doctor(String name, String specialization, int office) {
        this.name = name;
        this.specialization = specialization;
        this.office = office;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", office=" + office +
                '}';
    }
}
