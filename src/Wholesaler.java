import java.util.List;


public class Wholesaler {
    int id;
    String name;
    List <Products> productDetails;

    public Wholesaler() { }

    public Wholesaler(int id, String name, List<Products> productDetails) {
        this.id = id;
        this.name = name;
        this.productDetails = productDetails;
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

    public List<Products> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<Products> productDetails) {
        this.productDetails = productDetails;
    }

    public void display(){
        System.out.println("\n\tWholesaler ID ---> "+this.id+"\tWholesaler name ---> "+this.name+"\n");
    }
}
