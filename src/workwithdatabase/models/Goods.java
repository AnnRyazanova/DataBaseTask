package workwithdatabase.models;

public final class Goods {

    private int id;
    private String nomenclature;
    private String measure;

    public Goods() {
        this(0, "", "");
    }

    public Goods(int id, String nomenclature, String measure) {
        setId(id);
        setNomenclature(nomenclature);
        setMeasure(measure);
    }

    @Override
    public String toString() {
        return nomenclature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
