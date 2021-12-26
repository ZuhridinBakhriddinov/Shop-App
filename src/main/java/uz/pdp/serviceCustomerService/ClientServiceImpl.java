package uz.pdp.serviceCustomerService;

import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.pdp.DataBase;
import uz.pdp.Utill;
import uz.pdp.model.Cloth;
import uz.pdp.model.Transaction;
import uz.pdp.model.User;
import uz.pdp.serviceCustomerService.interfaces.ClientService;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.in;
import static uz.pdp.DataBase.clothList;
import static uz.pdp.Utill.RED;
import static uz.pdp.Utill.print;

public class ClientServiceImpl implements ClientService {
    Scanner scannerStr = new Scanner(in);
    Scanner scannerInt = new Scanner(in);
    ClientCartServiceImpl clientCartService = new ClientCartServiceImpl();

    @Override
    public void customerMenu(User user) {
        while (true) {
            System.out.println(Utill.BLUE_BOLD + "1.Add cloth to cart\n2.My Cart\n3.show balance\n4.fill balance\n5.Show my purchase history\n6.TransactionHistory_Excel_file\n7.Check(pdf)\n0.Back" + Utill.RESET);


            scannerInt = new Scanner(in);


            int option = scannerInt.nextInt();
            switch (option) {
                case 1:
                    buyCloth(user);
                    break;
                case 2:
                    clientCartService.myCartMenu(user);

                    break;
                case 3:
                    showBalance(user);
                    break;
                case 4:
                    fillBalance(user);
                    break;

                case 5:
                    showMyPurchaseHistory(user);
                    break;
                case 6:
                    downloadEXcelTransactionHistory();
                case 7:
                    downloadPdf_check(user);

                    break;
                case 0:
                    return;
                default:
            }
        }
    }

    private void downloadPdf_check(User user) {
        try (PdfWriter writer = new PdfWriter("src/main/resources/test.pdf")) {
            PdfDocument pdfDocument = new PdfDocument(writer);


            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);
            Paragraph paragraph = new Paragraph(user.getName() + " 's check paper");
            document.add(paragraph);
            float[] pointColumnWidth = {150F, 150F, 150F, 150F, 150F, 150F, 150F};
            Table table = new Table(pointColumnWidth);
            table.addCell(new Cell().add("T/R"));
            table.addCell(new Cell().add("Customer's name"));
            table.addCell(new Cell().add("PayType"));
            table.addCell(new Cell().add("ClothName"));
            table.addCell(new Cell().add("Cloth price"));
            table.addCell(new Cell().add("Cloth quantity"));
            table.addCell(new Cell().add("Purchase time"));
            Gson gson = new Gson();
            Reader reader = new FileReader("src/main/resources/transactionHistory.json");
            Transaction[] transactions = gson.fromJson(reader, Transaction[].class);
            for (int i = 0; i < transactions.length; i++) {
                if (transactions[i].getClientId() == user.getId()) {
                    Transaction transaction = transactions[i];
                    table.addCell(new Cell().add(String.valueOf(i + 1)));
                    table.addCell(new Cell().add(transaction.getClientName()));
                    table.addCell(new Cell().add(transaction.getPayTypeName()));
                    table.addCell(new Cell().add(transaction.getClothName()));
                    table.addCell(new Cell().add(String.valueOf(transaction.getClothPrice())));
                    table.addCell(new Cell().add(String.valueOf(transaction.getClothQuantity())));
                    table.addCell(new Cell().add(String.valueOf(transaction.getLocalTime())));

                }

            }
            System.out.println("Loading...");
            System.out.println("Success!!!!");

            document.add(table);
            document.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void RemoveBorder(Table table) {
        for (IElement iElement : table.getChildren()) {
            ((Cell) iElement).setBorder(Border.NO_BORDER);
        }
    }

    private void downloadEXcelTransactionHistory() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/transactionHistory.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            sheet.setDefaultRowHeightInPoints(50);
            sheet.setDefaultColumnWidth(50);
            XSSFRow row = sheet.createRow(0);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("ClientId");
            row.createCell(2).setCellValue("Client Name");
            row.createCell(3).setCellValue("Client Quantity");
            row.createCell(4).setCellValue("Cloth Price");
            row.createCell(5).setCellValue("PayType name");
            row.getCell(0).setCellStyle(cellStyle);
            row.getCell(1).setCellStyle(cellStyle);
            row.getCell(2).setCellStyle(cellStyle);
            row.getCell(3).setCellStyle(cellStyle);
            row.getCell(4).setCellStyle(cellStyle);
            row.getCell(5).setCellStyle(cellStyle);
            try (Reader reader = new FileReader("src/main/resources/transactionHistory.json")) {
                Gson gson = new Gson();
                Transaction[] transactions = gson.fromJson(reader, Transaction[].class);
                for (int i = 0; i < transactions.length; i++) {
                    Transaction transaction = transactions[i];
                    XSSFRow row1 = sheet.createRow(i + 1);
                    row1.createCell(0).setCellValue(transaction.getId());
                    row1.createCell(1).setCellValue(transaction.getClientId());
                    row1.createCell(2).setCellValue(transaction.getClientName());
                    row1.createCell(3).setCellValue(transaction.getClothQuantity());
                    row1.createCell(4).setCellValue(transaction.getClothPrice());
                    row1.createCell(5).setCellValue(transaction.getPayTypeName());

                    row1.getCell(0).setCellStyle(cellStyle);
                    row1.getCell(1).setCellStyle(cellStyle);
                    row1.getCell(2).setCellStyle(cellStyle);
                    row1.getCell(3).setCellStyle(cellStyle);
                    row1.getCell(4).setCellStyle(cellStyle);
                    row1.getCell(5).setCellStyle(cellStyle);


                }
            }


            workbook.write(fileOutputStream);
            System.out.println("Loading...");
            System.out.println("Success!!!");


        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Wrong!!!");
        }


    }

    @Override
    public void buyCloth(User user) {


        System.out.println("1.See cloth(random)\n" +
                "2.See cloth progress" + Utill.GREEN_BOLD + "(\uD83D\uDD3C)" + Utill.RESET + "\n" +
                "3.See cloth degrees " + Utill.GREEN_BOLD + "(🔽)" + Utill.RESET + "\n" +
                "4.See cloth by discount \n" +
                "5.See cloth between two price\n" +
                "6.Statistic information\n" +
                "0.Back");
        int option = new Scanner(in).nextInt();
        switch (option) {
            case 1:
                seeCloth_random(user);
                break;
            case 2:
                seeCloth_progress(user);
                break;
            case 3:
                seeCloth_degress(user);
                break;
            case 4:
                see_cloth_by_discount(user);
                break;
            case 5:
                between_two_price(user);
                break;
            case 6:
                statistic_information();
            case 0:
                return;
            default:
                print(RED, "Wrong option");
        }

        buyCloth(user);
    }

    private void statistic_information() {

        DoubleSummaryStatistics collect = clothList.stream().filter(cloth -> cloth.getPrice() > 0).collect(Collectors.summarizingDouble(Cloth::getPrice));
        System.out.println("Max: " + collect.getMax() + " Min:" + collect.getMin() + " Average: " + collect.getAverage() + " Sum:" + collect.getSum() + " Count:" + collect.getCount());


    }

    private void between_two_price(User user) {
        Cloth selectedCloth = null;
        System.out.println("input min sum");
        double scanner = new Scanner(System.in).nextInt();
        System.out.println("input max sum");
        double scanner2 = new Scanner(System.in).nextInt();
        clothList.stream().filter(cloth -> cloth.getPrice() >= scanner && cloth.getPrice() <= scanner2).forEach(System.out::println);
        quantity(user, selectedCloth);


    }

    private void see_cloth_by_discount(User user) {
        Cloth selectedCloth = null;
        Stream<Cloth> clothStream = clothList.stream().filter(cloth -> cloth.getDiscount() > 0);
        clothStream.sorted(Comparator.naturalOrder()).forEach(System.out::println);
        quantity(user, selectedCloth);

    }

    private void seeCloth_degress(User user) {
        Cloth selectedCloth = null;

        clothList.stream().sorted(Comparator.comparing(Cloth::getPrice).reversed()).forEach(System.out::println);


        quantity(user, selectedCloth);

    }

    private void seeCloth_progress(User user) {
        Cloth selectedCloth = null;

        clothList.stream().sorted(Comparator.comparing(Cloth::getPrice)).forEach(System.out::println);


        quantity(user, selectedCloth);

    }

    private void seeCloth_random(User user) {
        Cloth selectedCloth = null;
        if (clothList.size() == 0) {
            System.out.println("List is empty!!!");
            return;
        }

        for (Cloth cloth : clothList) {
            System.out.println(cloth);
        }
        quantity(user, selectedCloth);

    }

    private void quantity(User user, Cloth selectedCloth) {
        System.out.println("Enter cloth id to buy:(0=>Back) ");
        int inputClothId = scannerInt.nextInt();
        if (inputClothId == 0) {
            return;
        }
        for (Cloth cloth : clothList) {
            if (cloth.getId() == inputClothId) {
                selectedCloth = cloth;
            }
        }


        System.out.println("Enter quantity: (0=>Back)");
        int inputQuantity = scannerInt.nextInt();
        if (inputQuantity == 0) {
            return;
        }
        if (Objects.requireNonNull(selectedCloth).getQuantity() < inputQuantity) {
            System.out.println(selectedCloth.getQuantity() + " ta bor kamroq kiriting!!!");
        } else {
            Cloth cloth = new Cloth(Objects.requireNonNull(selectedCloth).getId(), selectedCloth.getName(), selectedCloth.getColor(), selectedCloth.getSize(), selectedCloth.getPrice(), selectedCloth.getDiscount(), inputQuantity);

            user.getMyCart().add(cloth);
        }
    }


    public void showMyPurchaseHistory(User user) {
        if (DataBase.transactionList.size() == 0) {
            System.out.println("List is empty!!!");
            return;
        }

        for (Transaction transaction : DataBase.transactionList) {
            if (transaction.getClientId() == user.getId()) {
                System.out.println(transaction);
            }
        }
        try (Writer writer = new FileWriter("src/main/resources/transactionHistory.json")
        ) {
            String s1 = new Gson().toJson(DataBase.transactionList);
            writer.write(s1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showBalance(User user) {
        System.out.println(user.getBalance());
    }

    @Override
    public void fillBalance(User user) {
        System.out.println("Enter amount: ");
        double amount = scannerInt.nextDouble();

        if (amount < 0) {
            System.out.println("Invalid amount!!!");
            return;
        }

        user.setBalance(user.getBalance() + amount);
        System.out.println("Successfully done!!!");

    }
}
