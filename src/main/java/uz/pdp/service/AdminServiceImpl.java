package uz.pdp.service;

import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.pdp.DataBase;
import uz.pdp.model.Transaction;
import uz.pdp.model.User;
import uz.pdp.service.interfaces.AdminService;

import java.io.*;
import java.util.Scanner;

public class AdminServiceImpl implements AdminService {

    Scanner scannerInt = new Scanner(System.in);
    ClothServiceImpl clothService = new ClothServiceImpl();
    ClientServiceForAdminImpl clientServiceForAdmin = new ClientServiceForAdminImpl();
    PayTypeServiceImpl payTypeService = new PayTypeServiceImpl();

    @Override
    public void adminMenu(User user) {
        while (true) {
            System.out.println("1.Cloth menu\n2.My Client menu\n3.Pay type menu\n4.Order History\n5.Download order history(xlsx)\n0.Log out");

            scannerInt = new Scanner(System.in);
            System.out.print("Select number:");

            int option = scannerInt.nextInt();
            switch (option) {
                case 1:
                    clothService.clothMenu(user);
                    break;
                case 2:
                    clientServiceForAdmin.clientMenu(user);
                    break;
                case 3:
                    payTypeService.payTypeMenu(user);
                    break;
                case 4:
                    transactionHistory();
                    break;
                case 5:
                    downloadOrderHistory();

                case 0:
                    return;
                default:
                    System.out.println("Wrong option");
                    break;
            }

        }
    }

    private void downloadOrderHistory() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/transactionHistoryFull.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            sheet.setDefaultRowHeightInPoints(50);
            sheet.setDefaultColumnWidth(50);
            XSSFRow row = sheet.createRow(0);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            row.createCell(0).setCellValue("ClientId");
            row.createCell(1).setCellValue("Client Name");
            row.createCell(2).setCellValue("Cloth Quantity");
            row.createCell(3).setCellValue("Cloth Price");
            row.createCell(4).setCellValue("PayType name");
            row.createCell(5).setCellValue("Local Time");
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

                    row1.createCell(0).setCellValue(transaction.getClientId());
                    row1.createCell(1).setCellValue(transaction.getClientName());
                    row1.createCell(2).setCellValue(transaction.getClothQuantity());
                    row1.createCell(3).setCellValue(transaction.getClothPrice());
                    row1.createCell(4).setCellValue(transaction.getPayTypeName());
                    row1.createCell(5).setCellValue(transaction.getLocalTime());

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
    public String transactionHistory() {
        String result = "";
        for (Transaction transaction : DataBase.transactionList) {
            return result + transaction;
        }
        return result;
    }
}
