import java.util.List;

public class Retailer {
    int id;
    String name;
    List <Wholesaler> wholesalerDetails;

    public Retailer() {}

    public Retailer(int id, String name, List<Wholesaler> wholesalerDetails) {
        this.id = id;
        this.name = name;
        this.wholesalerDetails = wholesalerDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Wholesaler> getWholesalerDetails() {
        return wholesalerDetails;
    }

    public void setWholesalerDetails(List<Wholesaler> wholesalerDetails) {
        this.wholesalerDetails = wholesalerDetails;
    }
}
