package uz.pdp.service;

import com.google.gson.Gson;
import uz.pdp.DataBase;
import uz.pdp.model.Cloth;
import uz.pdp.model.User;
import uz.pdp.model.enums.Size;
import uz.pdp.service.interfaces.ClothService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class ClothServiceImpl implements ClothService {
    Scanner scannerStr = new Scanner(System.in);
    Scanner scannerInt = new Scanner(System.in);

    @Override
    public void clothMenu(User user) {
        while (true){
            System.out.println("1.show Cloth List\n2.add Cloth\n3.update Cloth\n4.delete Cloth\n5.back");
            int option = scannerInt.nextInt();
            switch (option){

                case 1:
                    showClothList();
                    break;
                case 2:
                    addCloth();
                    break;
                case 3:
                    updateCloth();
                    break;
                case 4:
                    deleteCloth();
                    break;

                case 5:return;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }

    @Override
    public void showClothList() {
        for (Cloth cloth : DataBase.clothList) {
            System.out.println(cloth);
        }
    }

    @Override
    public void addCloth() {
        Size size = null;
        int id = (int)(Math.random()*10000);

        System.out.println("Enter name of the Cloth: ");
        String name = scannerStr.nextLine();

        System.out.println("Enter color of the cloth: ");
        String color = scannerStr.nextLine();

        System.out.print("Available sizes: ");
        for (Size value : Size.values()) {
            System.out.print(value);
        }

        System.out.println("Enter size: ");
        String stringSize = scannerStr.nextLine();
        try {
            size = Size.valueOf(stringSize);
        }catch (IllegalArgumentException e){
            System.out.println("Wrong input type!!!");
            System.out.print("Available sizes: ");
            for (Size value : Size.values()) {
                System.out.print(value);
            }
            return;

        }

        System.out.println("Enter your price: ");
        double price = scannerInt.nextDouble();

        System.out.println("Enter discount (in percentage): ");
        double discount = scannerInt.nextDouble();

        System.out.println("Enter quantity of the product: ");
        int quantity = scannerInt.nextInt();


        Cloth cloth = new Cloth(id, name, color, size, price, discount, quantity);
        DataBase.clothList.add(cloth);
        System.out.println("Successful operation!!!");
        try (Writer writer = new FileWriter("src/main/resources/cloth.json")) {
            Gson gson=new Gson();
            String clothList = gson.toJson(DataBase.clothList);
            writer.write(clothList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCloth() {
        for (Cloth cloth : DataBase.clothList) {
            System.out.println(cloth);
        }

        System.out.println("Enter cloth id to update: ");
        int inputId = scannerInt.nextInt();

        for (Cloth cloth : DataBase.clothList) {
            if(inputId == cloth.getId()){
                System.out.println("Enter new price: ");
                double newPrice = scannerInt.nextDouble();

                System.out.println("Enter new discount (in percentage)): ");
                double discount = scannerInt.nextDouble();

                System.out.println("Enter new quantity: ");
                int newQuantity = scannerInt.nextInt();

                cloth.setPrice(newPrice);
                cloth.setQuantity(newQuantity);
                cloth.setDiscount(discount);
                System.out.println("Success");
                break;
            }
        }
        try (Writer writer = new FileWriter("src/main/resources/cloth.json")) {
            Gson gson=new Gson();
            String clothList = gson.toJson(DataBase.clothList);
            writer.write(clothList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCloth() {
        for (Cloth cloth : DataBase.clothList) {
            System.out.println(cloth);
        }

        System.out.println("Enter cloth id to update: ");
        int inputId = scannerInt.nextInt();

        for (Cloth cloth : DataBase.clothList) {
            if (inputId == cloth.getId()) {
               DataBase.clothList.remove(cloth);
                System.out.println("Success!!!");
                break;
            }
        }
        try (Writer writer = new FileWriter("src/main/resources/cloth.json")) {
            Gson gson=new Gson();
            String clothList = gson.toJson(DataBase.clothList);
            writer.write(clothList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
