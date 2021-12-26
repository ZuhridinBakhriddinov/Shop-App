package uz.pdp.service;

import uz.pdp.DataBase;
import uz.pdp.model.User;
import uz.pdp.model.enums.Role;
import uz.pdp.service.interfaces.ClientService;

import java.util.Scanner;

public class ClientServiceForAdminImpl implements ClientService {
    Scanner scannerStr = new Scanner(System.in);
    Scanner scannerInt = new Scanner(System.in);

    @Override
    public void clientMenu(User user) {
        while (true){
            System.out.println("1.show Client List\n2.block Client\n3.unblock Client\n4.Back");
            int option = scannerInt.nextInt();
            switch (option){
                case 1:
                    showClientList();
                    break;
                case 2:
                    blockClient();
                    break;
                case 3:
                    unblockClient();
                    break;

                case 4:
                    return;
                default:
                    System.out.println("Wrong option!!!");
                    break;
            }
        }
    }

    @Override
    public void showClientList() {
        if (DataBase.userList.size() == 0){
            System.out.println("List is empty!!!");
            return;
        }
        for (User user : DataBase.userList) {
            if (user.getRole().equals(Role.CUSTOMER))    System.out.println(user);
     }
    }

    @Override
    public void blockClient() {
        if (DataBase.userList.size() == 0){
            System.out.println("List is empty!!!");
            return;
        }
        showClientList();

        System.out.println("Enter client ID to block: ");
        int inputId = scannerInt.nextInt();

        for (User user : DataBase.userList) {
            if (user.getId() == inputId){
                user.setActive(false);
                System.out.println("Successfully deleted!!!");
                break;
            }
        }

    }

    @Override
    public void unblockClient() {
        if (DataBase.userList.size() == 0){
            System.out.println("List is empty!!!");
            return;
        }
        showClientList();

        System.out.println("Enter client ID to block: ");
        int inputId = scannerInt.nextInt();

        for (User user : DataBase.userList) {
            if (user.getId() == inputId) {
                user.setActive(true);
                System.out.println("Successfully unblocked!!!");
                break;
            }
        }
    }
}
