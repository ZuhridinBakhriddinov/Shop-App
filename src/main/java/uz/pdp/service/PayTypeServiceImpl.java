package uz.pdp.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.pdp.DataBase;
import uz.pdp.model.PayType;
import uz.pdp.model.User;
import uz.pdp.service.interfaces.PayTypeService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PayTypeServiceImpl implements PayTypeService {
    Scanner scannerStr = new Scanner(System.in);
    Scanner scannerInt = new Scanner(System.in);

    public static void main(String[] args) {


        downloadExcelFile();


    }

    private static void downloadExcelFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/payType.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            sheet.setDefaultRowHeightInPoints(50);
            sheet.setDefaultColumnWidth(50);
            XSSFRow row = sheet.createRow(0);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("Name");
            row.createCell(2).setCellValue("CommissionFee");
            row.getCell(0).setCellStyle(cellStyle);
            row.getCell(1).setCellStyle(cellStyle);
            row.getCell(2).setCellStyle(cellStyle);
            try (Reader reader = new FileReader("src/main/resources/payType.json")) {
                Gson gson = new Gson();
                PayType[] payTypes = gson.fromJson(reader, PayType[].class);
                for (int i = 0; i < payTypes.length; i++) {
                    PayType payType = payTypes[i];
                    XSSFRow row1 = sheet.createRow(i + 1);
                    row1.createCell(0).setCellValue(payType.getId());
                    row1.getCell(0).setCellStyle(cellStyle);
                    row1.createCell(1).setCellValue(payType.getName());
                    row1.getCell(1).setCellStyle(cellStyle);
                    row1.createCell(2).setCellValue(payType.getCommissionFee());
                    row1.getCell(2).setCellStyle(cellStyle);


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
    public void payTypeMenu(User user) {
        while (true) {
            System.out.println("1.show Pay Type List\n2.add PayType\n3.update PayType\n4.delete PayType\n5=> Download xlsx file\n0.Back");
            int option = scannerInt.nextInt();
            switch (option) {
                case 1:
                    showPayTypeList();
                    break;
                case 2:
                    addPayType();
                    break;
                case 3:
                    updatePayType();
                    break;
                case 4:
                    deletePayType();
                    break;
                case 5:
                    downloadExcelFile();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong option!!!");
                    break;
            }
        }

    }


    @Override
    public void showPayTypeList() {

        for (PayType payType : DataBase.payTypeList) {
            System.out.println("name: " + payType.getName() + "|| id: " + payType.getId() + "|| commissionFee: " + payType.getCommissionFee() + "\n");
        }


    }

    @Override
    public void addPayType() {
        int id = (int) (Math.random() * 10000);

        System.out.println("Enter name of the pay type: ");
        String name = scannerStr.nextLine();

        System.out.println("Enter commission fee of the pay type: ");
        double commissionFee = scannerInt.nextDouble();

        PayType payType = new PayType(id, name, commissionFee);
        DataBase.payTypeList.add(payType);
        System.out.println("Successfully added");

        try (Writer writer = new FileWriter("src/main/resources/payType.json")
        ) {
            String s1 = new Gson().toJson(DataBase.payTypeList);
            writer.write(s1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePayType() {
        if (DataBase.payTypeList.size() == 0) {
            System.out.println("List is empty!!!");
            return;
        }
        showPayTypeList();

        System.out.println("Enter payType id to Update: ");
        int inputId = scannerInt.nextInt();


        for (PayType payType : DataBase.payTypeList) {
            if (payType.getId() == inputId) {
                System.out.println("input new name: ");
                String name = scannerStr.nextLine();
                payType.setName(name);
                System.out.println("Enter new Commission Fee (in percentage): ");
                double newCommissionFee = scannerInt.nextDouble();

                payType.setCommissionFee(newCommissionFee);
                System.out.println("Successful update!!!");


            }
        }
        try (Writer writer = new FileWriter("src/main/resources/payType.json")) {
            String s = new Gson().toJson(DataBase.payTypeList);
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deletePayType() {
        if (DataBase.payTypeList.size() == 0) {
            System.out.println("List is empty!!!");
            return;
        }
        showPayTypeList();

        System.out.println("Enter payType id to delete: ");
        int inputId = scannerInt.nextInt();


            for (PayType payType : DataBase.payTypeList) {
                if (payType.getId() == inputId) {
                    DataBase.payTypeList.remove(payType);
                    break;
                }
            }
        try (Writer writer = new FileWriter("src/main/resources/payType.json")) {
            Gson gson = new Gson();
            String s1 = gson.toJson(DataBase.payTypeList);

            writer.write(s1);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    }

