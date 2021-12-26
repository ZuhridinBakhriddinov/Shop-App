package uz.pdp.serviceCustomerService;

import com.google.gson.Gson;
import uz.pdp.DataBase;
import uz.pdp.Utill;
import uz.pdp.model.Cloth;
import uz.pdp.model.PayType;
import uz.pdp.model.Transaction;
import uz.pdp.model.User;
import uz.pdp.model.enums.Size;
import uz.pdp.serviceCustomerService.interfaces.clientCartService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public class ClientCartServiceImpl implements clientCartService {
    Scanner scannerStr = new Scanner(System.in);
    Scanner scannerInt = new Scanner(System.in);


    @Override
    public void myCartMenu(User user) {
        while (true) {
            System.out.println(Utill.YELLOW_BOLD_BRIGHT + "1.cloth List\n2.check Out\n3.Check Average Price\n4.Check Average Price By Size\n5.Back"+Utill.RESET);
            int option = scannerInt.nextInt();
            switch (option) {
                case 1:
                    clothList(user);
                    break;
                case 2:
                    checkOut(user);
                    break;
                case 3:
                    checkAveragePrice(user);
                    break;
                case 4:
                    checkAveragePriceBySize(user);
                    break;

                case 5:
                    return;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }

    @Override
    public void clothList(User user) {
        if (isCartEmpty(user)) {
            System.out.println("List is empty!!!");
            return;
        }

        for (Cloth cloth : user.getMyCart()) {
            System.out.println(cloth);
        }
    }

    @Override
    public void checkOut(User user) {
        Cloth selectedCloth = null;
        PayType selectedPayType = null;

        if (isCartEmpty(user)) {
            System.out.println("List is empty!!!");
            return;
        }
        clothList(user);

        System.out.println(Utill.CYAN_BACKGROUND+"Enter cloth id to buy: (0=> back) "+Utill.RESET);
        int inputClothId = scannerInt.nextInt();
        if (inputClothId == 0) {
            return;
        }
        ///////////////////////////////////////////////////////////////////////////////////

        for (Cloth cloth : user.getMyCart()) {
            if (cloth.getId() == inputClothId) {
                selectedCloth = cloth;
            }
        }

        for (PayType payType : DataBase.payTypeList) {
            System.out.println(Utill.YELLOW_BACKGROUND_BRIGHT+payType+Utill.RESET);
        }

        System.out.println(Utill.CYAN_UNDERLINED+"Enter payType id to buy: "+Utill.RESET);
        int inputPayTypeId = scannerInt.nextInt();


        for (PayType payType : DataBase.payTypeList) {
            if (payType.getId() == inputPayTypeId) {
                selectedPayType = payType;
            }
        }

        //Transaction process

        if (((Objects.requireNonNull(selectedCloth).getPrice() * Objects.requireNonNull(selectedPayType).getCommissionFee()) / 100 + selectedCloth.getPrice()) * selectedCloth.getQuantity() > user.getBalance()) {
            System.out.println("you do not have enough money!!!");
            return;
        } else {

            user.setBalance(user.getBalance() - (((selectedCloth.getPrice() * selectedPayType.getCommissionFee()) / 100 + selectedCloth.getPrice()) * selectedCloth.getQuantity()));
            DataBase.admin.setBalance(DataBase.admin.getBalance() + selectedCloth.getPrice());
            for (Cloth cloth : DataBase.clothList) {
                if (cloth.getId() == selectedCloth.getId()) {
                    cloth.setQuantity(cloth.getQuantity() - selectedCloth.getQuantity());
                    if (cloth.getQuantity() == 0) {
                        DataBase.clothList.remove(cloth);
                    }
                    break;
                }
            }


        }
        try (Writer writer = new FileWriter("src/main/resources/cloth.json")) {
            Gson gson=new Gson();
            String clothList = gson.toJson(DataBase.clothList);
            writer.write(clothList);

        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(Utill.GREEN_BOLD_BRIGHT+"Successfully bought!!!"+Utill.RESET);
        System.out.println(Utill.BLUE_BOLD_BRIGHT+"Thank you for purchase, please get a check in menu"+Utill.RESET);
        Transaction transaction = new Transaction((int) (Math.random() * 10000), user.getId(), user.getName(), selectedCloth.getName(), selectedCloth.getQuantity(), selectedCloth.getPrice(), selectedPayType.getName(), LocalDateTime.now());
        DataBase.transactionList.add(transaction);
        user.getMyCart().remove(selectedCloth);

    }

    @Override
    public void checkAveragePrice(User user) {

        int count = 0;
        int sumPrice = 0;
        //Checking is empty
        if (isCartEmpty(user)) {
            System.out.println(Utill.RED_BOLD_BRIGHT+"List is empty!!!"+Utill.RESET);
            return;
        }

        for (Cloth cloth : user.getMyCart()) {
            count++;
            sumPrice += cloth.getPrice();
        }

        try {
            System.out.println("Average price is: " + (sumPrice / count));
        } catch (ArithmeticException e) {
            System.out.println("List s empty");
        }

    }

    @Override
    public void checkAveragePriceBySize(User user) {

        Size size = null;
        int count = 0;
        int sumPrice = 0;
        //Checking is empty
        if (isCartEmpty(user)) {
            System.out.println("List is empty!!!");
            return;
        }

        System.out.print("Available sizes: ");
        for (Size value : Size.values()) {
            System.out.print(value + " ");
        }
        System.out.println("Enter size of cloth: ");
        String inputSize = scannerStr.nextLine();
        try {
            size = Size.valueOf(inputSize);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong id!!!");

            System.out.print("Available sizes: ");
            for (Size value : Size.values()) {
                System.out.print(value);
            }
            return;
        }


        for (Cloth cloth : user.getMyCart()) {
            if (cloth.getSize() == size) {
                count++;
                sumPrice += cloth.getPrice();
            }
        }

        try {
            System.out.println("Average price is: " + (sumPrice / count));
        } catch (ArithmeticException e) {
            System.out.println("List is empty");
        }

    }

    public boolean isCartEmpty(User user) {
        if (user.getMyCart().size() == 0) {
            return true;
        }
        return false;
    }
}
