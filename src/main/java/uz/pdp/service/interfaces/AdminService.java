package uz.pdp.service.interfaces;

import uz.pdp.model.User;

public interface AdminService {

    void adminMenu(User user);

    String transactionHistory();

}
