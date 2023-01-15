import java.io.*;
import java.util.Scanner;
import java.util.*;
//import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {


    Scanner a = new Scanner(System.in);

    public void display_products(List<Products> ProductList) {
        int i;
        if(ProductList.isEmpty()){
            System.out.println("\n\t No products to display!!! Enter 4 to load all the details from CSV");
        }
        else {
            for (Products p : ProductList) {
                p.displayProducts();
            }
        }
    }

    public void display_individual_product(int x, List<Products> ProductList) {
        int i;
        for(i = 0; i<ProductList.size(); i++){
            if(ProductList.get(i).getId() == x){
                ProductList.get(i).displayProducts();
                break;
            }
        }
        if(i == ProductList.size()){
            System.out.println("\n\tProduct with the specified ID does not exist!!!");
        }
    }

    public int decideDiscount(String userType, int numOfProducts){
        int discount = 0;
        if (userType.equalsIgnoreCase("wholesaler")) {
            if (numOfProducts < 10) {
                discount = 0;
            } else if (numOfProducts >= 10 && numOfProducts < 50) {
                discount = 10;
            } else if (numOfProducts >= 50 && numOfProducts < 100) {
                discount = 20;
            } else {
                discount = 30;
            }
        } else if (userType.equalsIgnoreCase("retailer")) {
            if (numOfProducts < 10) {
                discount = 0;
            } else if (numOfProducts >= 10 && numOfProducts < 50) {
                discount = 5;
            } else if (numOfProducts >= 50 && numOfProducts < 100) {
                discount = 10;
            } else {
                discount = 15;
            }
        }
        return discount;
    }

    public void generateBill(int clientId,String clientName ,Products pro,int quantity, int discountPercentage,float gstPercent) throws IOException {

        int billAmount, grandBillAmount;
        float gstAmount;
        try {
            Document my_pdf_report = new Document();


            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("/Users/harish/IdeaProjects/ProductManagement/invoices/"+clientName+".pdf"));

            my_pdf_report.open();
            PdfPTable my_report_table = new PdfPTable(2);
            PdfPCell table_cell;
            my_report_table.setSpacingAfter(10);
            my_report_table.setSpacingBefore(4);
            my_report_table.getRowHeight(10);

            table_cell = new PdfPCell(new Phrase("CLIENT ID :"));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(Integer.toString(clientId)));
            my_report_table.addCell(table_cell);


            table_cell = new PdfPCell(new Phrase("CLIENT NAME:"));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(clientName));
            my_report_table.addCell(table_cell);


            table_cell = new PdfPCell(new Phrase("PRODUCT ID:"));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(Integer.toString(pro.getId())));
            my_report_table.addCell(table_cell);


            table_cell = new PdfPCell(new Phrase("PRODUCT NAME:"));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(pro.getName()));
            my_report_table.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("QUANTITY"));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(Integer.toString(quantity)));
            my_report_table.addCell(table_cell);


            billAmount = quantity * pro.getPrice();
            table_cell = new PdfPCell(new Phrase("PRICE PER UNIT "));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(Integer.toString(pro.getPrice())));
            my_report_table.addCell(table_cell);

            billAmount = quantity * pro.getPrice();
            table_cell = new PdfPCell(new Phrase("BILL AMOUNT "));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(Integer.toString(billAmount)));
            my_report_table.addCell(table_cell);


            table_cell = new PdfPCell(new Phrase("DISCOUNT PERCENTAGE "));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(Integer.toString(discountPercentage) + "%"));
            my_report_table.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("GST PERCENTAGE: "));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(gstPercent+"%"));
            my_report_table.addCell(table_cell);

            gstAmount = (billAmount * gstPercent)/100;
            table_cell = new PdfPCell(new Phrase("GST AMOUNT: "));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(Float.toString(gstAmount)));
            my_report_table.addCell(table_cell);

            grandBillAmount = billAmount + (int)gstAmount;
            table_cell = new PdfPCell(new Phrase("Grand Total Amount: "));
            my_report_table.addCell(table_cell);
            table_cell = new PdfPCell(new Phrase(Float.toString(grandBillAmount)));
            my_report_table.addCell(table_cell);


            my_pdf_report.add(my_report_table);
            System.out.println("\n\tBill generated!!!");

            my_pdf_report.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void retailerPurchase(Retailer r, List<Wholesaler> WholesalerList) throws IOException {
        List<Wholesaler> wholesalerTempList;
        List<Products> productTempList;
        List<Products> productListForNewWholesaler = new ArrayList<>();
        List<Products> productListForExistingWholesaler = new ArrayList<>();
        int i, j, stock, price, w_id, p_id, discount;
        System.out.println("\n\tEnter Wholesaler ID:");
        w_id = a.nextInt();
        for (Wholesaler w : WholesalerList) {
            if (w.getId() == w_id) {
                productTempList = w.getProductDetails();
                display_products(productTempList);
                System.out.println("\n\tEnter product ID to purchase from Wholesaler: ");
                p_id = a.nextInt();
                System.out.println("\n\tEnter stock: ");
                stock = a.nextInt();
                System.out.println("\n\tEnter new price");
                price = a.nextInt();
                for (Products p : productTempList) {
                    if (p.getId() == p_id) {
                        if (p.getStock() > stock) {
                            Products pNew = new Products(p.getId(), p.getName(), stock, price);
                            wholesalerTempList = r.getWholesalerDetails();
                            System.out.println("\n\t wholesaler temp list"+ wholesalerTempList.size());
                            for (i = 0; i < wholesalerTempList.size(); i++) {
                                if (wholesalerTempList.get(i).getId() == w_id) {
                                    productListForExistingWholesaler = wholesalerTempList.get(i).getProductDetails();
                                    System.out.println("\n\t Products of existing wholesaler - "+productListForExistingWholesaler.size());
                                    for (j = 0; j < productListForExistingWholesaler.size(); j++) {
                                        if (productListForExistingWholesaler.get(j).getId() == p_id) {
                                            productListForExistingWholesaler.get(j).setPrice(price);
                                            productListForExistingWholesaler.get(j).setStock(stock + productListForExistingWholesaler.get(j).getStock());
                                            p.setStock(p.getStock() - stock);
                                            System.out.println("\n\t Adding stocks for Existing products from wholesaler");
                                            discount = decideDiscount("retailer", stock);
                                            generateBill(r.getId(), r.getName(), p, stock, discount, 18);
                                            break;
                                        }
                                    }
                                    if (j == productListForExistingWholesaler.size() || productListForExistingWholesaler.isEmpty()) {
                                        productListForExistingWholesaler.add(pNew);
                                        p.setStock(p.getStock() - stock);
                                        System.out.println("\n\t Adding new Products for wholesaler");
                                        discount = decideDiscount("retailer", stock);
                                        generateBill(r.getId(), r.getName(), p, stock, discount, 18);
                                        break;
                                    }
                                    break;
                                }
                            }
                            if (i == wholesalerTempList.size() || wholesalerTempList.isEmpty()) {
                                productListForNewWholesaler.add(pNew);
                                Wholesaler wNew = new Wholesaler(w.getId(), w.getName(), productListForNewWholesaler);
                                wholesalerTempList.add(wNew);
                                p.setStock(p.getStock() - stock);
                                System.out.println("\n\t Adding new wholesaler list and loading with products");
                                discount = decideDiscount("retailer", stock);
                                generateBill(r.getId(),r.getName(), p, stock, discount, 18);
                                break;
                            }
                        } else {
                            System.out.println("\n\tTry lesser quantity!");
                        }
                    }
                }

            }
        }
    }


    public void wholesalerPurchase(Wholesaler w, List<Products> pList) throws Exception {
        List<Products> productsOfWholesaler = w.getProductDetails();
        Products pTemp;
        int p_choice, p_stock, p_price, discount;
        System.out.println("\n\t Enter Product ID:");
        p_choice = a.nextInt();
        System.out.println("\n\t Enter stock:");
        p_stock = a.nextInt();
        System.out.println("\n\t Enter new price");
        p_price = a.nextInt();
        if (productsOfWholesaler.isEmpty()) {
            for (Products p : pList) {
                if (p.getId() == p_choice) {
                    if (p.getStock() > p_stock) {
                        pTemp = new Products(p.getId(), p.getName(), p_stock, p_price);
                        productsOfWholesaler.add(pTemp);
                        p.setStock(p.getStock() - p_stock);
                        discount = decideDiscount("Wholesaler", p_stock);
                        generateBill(w.getId(),w.getName(), p, p_stock, discount, 18);
                        break;
                    } else {
                        System.out.println("\n\tTry lesser quantity!");
                        break;
                    }
                }
            }
        } else {
            for (Products p : pList) {
                if (p.getId() == p_choice) {
                    if (p.getStock() > p_stock) {
                        int i;
                        for (i = 0; i < productsOfWholesaler.size(); i++) {
                            if (productsOfWholesaler.get(i).getId() == p_choice) {
                                productsOfWholesaler.get(i).setStock(p_stock + productsOfWholesaler.get(i).getStock());
                                productsOfWholesaler.get(i).setPrice(p_price);
                                p.setStock(p.getStock() - p_stock);
                                discount = decideDiscount("Wholesaler", p_stock);
                                generateBill(w.getId(),w.getName(), p, p_stock, discount, 18);
                                break;
                            }
                        }
                        if (i == productsOfWholesaler.size()) {
                            pTemp = new Products(p.getId(), p.getName(), p_stock, p_price);
                            productsOfWholesaler.add(pTemp);
                            p.setStock(p.getStock() - p_stock);
                            discount = decideDiscount("Wholesaler", p_stock);
                            generateBill(w.getId(),w.getName(), p, p_stock, discount, 18);
                        }
                    }
                }
            }
        }
    }

    public void loadAllDetails(List<Products> ProductList, List<Wholesaler> WholesalerList, List<Retailer> RetailerList) throws Exception {
        Scanner ps = new Scanner(new File("/Users/harish/IdeaProjects/ProductManagement/src/Products.csv"));
        Scanner ws = new Scanner(new File("/Users/harish/IdeaProjects/ProductManagement/src/Wholesaler.csv"));
        Scanner rs = new Scanner(new File("/Users/harish/IdeaProjects/ProductManagement/src/Retailer.csv"));

        while (ps.hasNext())  //returns a boolean value
        {
            String curr_line = ps.next();
            String[] entities = curr_line.split(",");
            Products p = new Products(Integer.parseInt(entities[0]), entities[1], Integer.parseInt(entities[2]), Integer.parseInt(entities[3]));
            ProductList.add(p);
        }
        ps.close();

        while (ws.hasNext())  //returns a boolean value
        {
            String curr_line = ws.next();
            String[] entities = curr_line.split(",");
            List<Products> productTemp = new ArrayList<>();
            Wholesaler w = new Wholesaler(Integer.parseInt(entities[0]), entities[1], productTemp);
            WholesalerList.add(w);
        }
        ws.close();

        while (rs.hasNext())  //returns a boolean value
        {
            String curr_line = rs.next();
            String[] entities = curr_line.split(",");
            List<Wholesaler> wholesalerTemp = new ArrayList<>();
            Retailer r = new Retailer(Integer.parseInt(entities[0]), entities[1], wholesalerTemp);
            RetailerList.add(r);
        }
        rs.close();
        System.out.println("Loaded successfully!!!");
    }

    public static void main(String[] args) throws Exception {
        Main obj = new Main();
        int i;
        List<Products> ProductList = new ArrayList<>();
        List<Wholesaler> WholesalerList = new ArrayList<>();
        List<Retailer> RetailerList = new ArrayList<>();
        int login_choice;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("\n\t\tProduct Management system");
            System.out.println("\n\t 1. Product");
            System.out.println("\n\t 2. Wholesaler");
            System.out.println("\n\t 3. Retailer");
            System.out.println("\n\t 4. Load All Details");
            System.out.println("\n\t 5. Exit");

            login_choice = s.nextInt();

            switch (login_choice) {
                case 1:
                    int p_id;
                    System.out.println("\n");

                    System.out.println("\n\t 1. Display Products");
                    System.out.println("\n\t 2. Display Individual product");
                    System.out.println("\n\t 3. Exit Product module");
                    login_choice = s.nextInt();
                    switch (login_choice) {
                        case 1:
                            obj.display_products(ProductList);
                            break;
                        case 2:
                            System.out.println("\n\t Enter the product id");
                            p_id = s.nextInt();
                            obj.display_individual_product(p_id, ProductList);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("\n\tEnter valid input!!!");
                    }
                    break;

                case 2:
                    int w_id;
                    System.out.println("\n\t 1. Display Products");
                    System.out.println("\n\t 2. Purchase Product");
                    System.out.println("\n\t 3. Display Products of particular wholesaler");
                    System.out.println("\n\t 3. Exit");

                    login_choice = s.nextInt();

                    switch (login_choice) {
                        case 1:
                            obj.display_products(ProductList);
                            System.out.println(ProductList.size());
                            System.out.println(WholesalerList.size());
                            System.out.println(RetailerList.size());
                            break;
                        case 2:
                            System.out.println("\n\tEnter Wholesaler ID:");
                            w_id = s.nextInt();
//                            for (Wholesaler w : WholesalerList) {
                            for(i = 0; i< WholesalerList.size(); i++){
                                if (WholesalerList.get(i).getId() == w_id) {
                                    obj.wholesalerPurchase(WholesalerList.get(i), ProductList);
                                    break;
                                }
                            }
                            if(i == WholesalerList.size()){
                                System.out.println("\n\tThe Wholesaler with the specified ID does not exist!!!");
                            }
                            break;
                        case 3:
                            System.out.println("\n\tEnter Wholesaler ID");
                            w_id = s.nextInt();
                            for (Wholesaler w : WholesalerList) {
                                if (w.getId() == w_id) {
                                    obj.display_products(w.getProductDetails());
                                    break;
                                }
                            }
                            break;
                         default:
                             System.out.println("\n\tEnter valid input!!!");
                    }
                    break;

                case 3:
                    int r_id;
                    List<Wholesaler> wholesalerTempList;
                    List<Products> productTempList;
                    System.out.println("\n\t1. Enter Wholesaler Product details");
                    System.out.println("\n\t2. Purchase product from wholesaler");
                    System.out.println("\n\t3. Display products of retailer");
                    System.out.println("\n\t4. Exit");
                    login_choice = s.nextInt();

                    switch (login_choice) {
                        case 1:
                            System.out.println("\n\tEnter Wholesaler ID:");
                            w_id = s.nextInt();
                            for (Wholesaler w : WholesalerList) {
                                if (w.getId() == w_id) {
                                    obj.display_products(w.getProductDetails());
                                    break;
                                }
                            }
                            break;
                        case 2:
                            System.out.println("\n\tEnter Retailer ID:");
                            r_id = s.nextInt();
                            for (Retailer r : RetailerList) {
                                if (r.getId() == r_id) {

                                    obj.retailerPurchase(r, WholesalerList);

                                }
                            }
                            break;

                        case 3:
                            System.out.println("\n\tEnter Retailer id: ");
                            r_id = s.nextInt();
                            for(Retailer r: RetailerList){
                                if(r_id == r.getId()){
                                    wholesalerTempList = r.getWholesalerDetails();
                                    for(Wholesaler wr: wholesalerTempList){
                                        wr.display();
                                    }
                                    System.out.println("\n\tEnter Wholesaler ID");
                                    w_id = s.nextInt();
                                    for(Wholesaler wr: wholesalerTempList){
                                        if(wr.getId() == w_id){
                                            productTempList = wr.getProductDetails();
                                            for(Products pr: productTempList){
                                                pr.displayProducts();
                                            }
                                        }
                                    }

                                }
                            }
                            break;
                        }
                        break;


                case 4:
                    obj.loadAllDetails(ProductList, WholesalerList, RetailerList);
                    break;

                case 5:
                    System.exit(0);

                default:
                    System.out.println("\n\tEnter valid input!!!");

            }


        }
    }
}

