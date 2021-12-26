package uz.pdp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.model.Cloth;
import uz.pdp.model.PayType;
import uz.pdp.model.Transaction;
import uz.pdp.model.User;
import uz.pdp.service.AdminServiceImpl;
import uz.pdp.service.UserServiceImpl;
import uz.pdp.serviceCustomerService.ClientServiceImpl;
import uz.pdp.serviceCustomerService.GuestCartServiceImpl;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.*;

import static uz.pdp.Utill.scannerInt;

public class Main {

    public static void main(String[] args) {
        try (
                Reader reader = new FileReader("src/main/resources/payType.json");
                Reader transactionHistoryReader = new FileReader("src/main/resources/transactionHistory.json");
                Reader userReader = new FileReader("src/main/resources/user.json");
                Reader clothReader=new FileReader("src/main/resources/cloth.json");

        ) {

            ///////////////////////===>>>> PayType  <======////////////////////////////////
            Gson gson = new Gson();
            List<PayType> payTypesFromJson = gson.fromJson(reader,
                    new TypeToken<List<PayType>>() {
                    }.getType());
            DataBase.payTypeList.addAll(Objects.requireNonNullElse(payTypesFromJson, new ArrayList<PayType>()));

            ///////////////////////===>>>> Transaction History  <======////////////////////////////////
            List<Transaction> transactionList = gson.fromJson(transactionHistoryReader,
                    new TypeToken<List<Transaction>>() {
                    }.getType());

            DataBase.transactionList.addAll(Objects.requireNonNullElse(transactionList, new ArrayList<Transaction>()));


            ///////////////////////===>>>> User  <======////////////////////////////////
            List<User> userList = gson.fromJson(userReader,
                    new TypeToken<List<User>>() {
                    }.getType());

            DataBase.userList.addAll(Objects.requireNonNullElse(userList, new ArrayList<User>()));
            ///////////////////////===>>>> Cloth  <======////////////////////////////////
            List<Cloth> clothList2 = gson.fromJson(clothReader,
                    new TypeToken<List<Cloth>>() {
                    }.getType());

            DataBase.clothList.addAll(Objects.requireNonNullElse(clothList2, new ArrayList<Cloth>()));



        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        UserServiceImpl userService = new UserServiceImpl();
        ClientServiceImpl clientService = new ClientServiceImpl();
        AdminServiceImpl adminService = new AdminServiceImpl();
        GuestCartServiceImpl guestCartService = new GuestCartServiceImpl();

        while (true) {


            Utill.print(Utill.BLUE, "1.Buy a cloth\n2.My cart\n3.Login\n4.Register\n5.Exit");
            try {
                while (true) {

                    scannerInt = new Scanner(System.in);

                    int option = scannerInt.nextInt();
                    switch (option) {
                        case 1:
                            guestCartService.guestBuyCloth();
                            break;
                        case 2:
                            guestCartService.guestCartMenu();
                            break;
                        case 3:
                            User user = userService.login();
                            if (user != null) {
                                switch (user.getRole()) {
                                    case CUSTOMER:
                                        clientService.customerMenu(user);
                                        break;
                                    case ADMIN:
                                        adminService.adminMenu(user);
                                        break;
                                }
                            }
                            break;
                        case 4:
                            userService.register();
                            break;

                        case 5:
                            return;

                        default:
                            System.out.println("wrong option");
                            break;
                    }
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Xato, bratan son kiriting>>>!!!");

            }


        }


    }
}
