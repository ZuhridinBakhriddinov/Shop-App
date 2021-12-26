package uz.pdp;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.model.User;

// Zuhridin Bakhriddinov 12/21/2021 12:23 PM
public class Bot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "cloth_shop_b7_bot";
    }

    @Override
    public String getBotToken() {
        return "5090350946:AAGEtv0_io1SucHgSKTe18wtnmMzgGtACdk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        User currentUser= (User) DataBase.userList;

    }
}
