package uz.pdp.serviceCustomerService;

import uz.pdp.DataBase;
import uz.pdp.model.Cloth;
import uz.pdp.model.User;
import uz.pdp.model.enums.Size;
import uz.pdp.service.AdminServiceImpl;
import uz.pdp.service.UserServiceImpl;
import uz.pdp.serviceCustomerService.interfaces.GuestCartService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GuestCartServiceImpl implements GuestCartService {
    Scanner scannerStr = new Scanner(System.in);
    Scanner scannerInt = new Scanner(System.in);
    UserServiceImpl userService = new UserServiceImpl();
    ClientServiceImpl clientService = new ClientServiceImpl();
    AdminServiceImpl adminService = new AdminServiceImpl();

    public void guestBuyCloth(){
        int inputId=0;
        for (Cloth cloth : DataBase.clothList) {
            System.out.println(cloth);
        }
        System.out.println("Enter cloth id to add your cart: ");

            scannerInt = new Scanner(System.in);


             inputId = scannerInt.nextInt();



        for (Cloth cloth : DataBase.clothList) {
            if (cloth.getId() == inputId){
                DataBase.temporaryCart.add(cloth);
                System.out.println("Successfully added to your cart!!!");
                break;
            }
        }
    }

        @Override
    public void guestCartMenu() {
        while (true){
            System.out.println("1.show cloth list\n2.check out\n3.Check Average Price\n4.Check Average Price By Size\n5.Back");
            int option = scannerInt.nextInt();
            switch (option){
                case 1:
                    guestClothList();
                    break;
                case 2:
                    guestCheckOut();
                    break;
                case 3:
                    guestCheckAveragePrice();
                    break;
                case 4:
                    guestCheckAveragePriceBySize();
                    break;

                case 5:return;
                default:
                    System.out.println("Wrong option!!!");
                    break;
            }
        }
    }

    @Override
    public void guestClothList() {
            if(DataBase.temporaryCart.size() == 0){
                System.out.println("List is empty!!!");
                return;
            }

        for (Cloth cloth : DataBase.temporaryCart) {
            System.out.println(cloth);
        }

    }

    @Override
    public void guestCheckOut() {
        System.out.println("Please, login or register to purchase cloth!!!");
        while (true){
            System.out.println("1.Log in\n2.Register\n3.Back");
            int option = scannerInt.nextInt();
            switch (option){
                case 1:
                    User user = userService.login();
                    if(user != null){
                        switch (user.getRole()){
                            case CUSTOMER:

                                user.getMyCart().addAll(DataBase.temporaryCart);
                                DataBase.temporaryCart.clear();

                                clientService.customerMenu(user);
                                break;
                            case ADMIN:
                                adminService.adminMenu(user);
                                break;
                        }
                    }
                    break;
                case 2:
                    userService.register();
                    break;

                case 3:
                    return;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }

    }

    @Override
    public void guestCheckAveragePrice() {

        int count = 0;
        int sumPrice = 0;

        if(DataBase.temporaryCart.size() == 0){
            System.out.println("List is empty!!!");
            return;
        }

        for (Cloth cloth : DataBase.temporaryCart) {
            count++;
            sumPrice += cloth.getPrice();
        }

        try {
            System.out.println("Average price is: " + (sumPrice/count));
        }catch (ArithmeticException e){
            System.out.println("List s empty");
        }

    }

    @Override
    public void guestCheckAveragePriceBySize() {

        Size size = null;
        int count = 0;
        int sumPrice = 0;

        if(DataBase.temporaryCart.size() == 0){
            System.out.println("List is empty!!!");
            return;
        }

        System.out.print("Available sizes: ");
        for (Size value : Size.values()) {
            System.out.print(value);
        }
        System.out.println("Enter size of cloth: ");
        String inputSize = scannerStr.nextLine();
        try {
            size = Size.valueOf(inputSize);
        }catch (IllegalArgumentException e){
            System.out.println("Wrong type of input!!!");

            System.out.print("Available sizes: ");
            for (Size value : Size.values()) {
                System.out.print(value);
            }
            return;
        }


        for (Cloth cloth : DataBase.temporaryCart) {
            if (cloth.getSize() == size){
                count++;
                sumPrice += cloth.getPrice();
            }
        }

        try {
            System.out.println("Average price is: " + (sumPrice/count));
        }catch (ArithmeticException e){
            System.out.println("List is empty");
        }

    }
}
