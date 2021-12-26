package uz.pdp.service.interfaces;

import uz.pdp.model.User;

public interface PayTypeService {

    void payTypeMenu(User user);

    void showPayTypeList();

    void addPayType();

    void updatePayType();

    void deletePayType();

}
