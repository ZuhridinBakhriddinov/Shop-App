package uz.pdp.serviceCustomerService.interfaces;

import uz.pdp.model.User;

public interface clientCartService {

    void myCartMenu(User user);

    void clothList(User user);

    void checkOut(User user);

    void checkAveragePrice(User user);

    void checkAveragePriceBySize(User user);



}
