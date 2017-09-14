package workwithdatabase.models;

public class Warehouse {

    private int id;
    private String city;
    private String name;

    public Warehouse(int id, String city, String name) {
        this.id = id;
        this.city = city;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
