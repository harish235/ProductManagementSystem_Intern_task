import java.util.function.Function;
public class Products {
    private int id;
    private String name;
    private int stock;
    private int price;

    public Products(){}

    public Products(int id, String name, int stock, int price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void reducePrice(int x){
        this.price = this.price - x;
    }

    public void displayProducts(){
        System.out.println("\nProducts ---> id - "+this.id+"\tname - "+this.name+"\tstock - "+this.stock+"\tprice - "+this.price+"\n");

    }

}
