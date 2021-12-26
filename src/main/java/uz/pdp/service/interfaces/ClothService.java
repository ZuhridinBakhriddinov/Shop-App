package uz.pdp.service.interfaces;

import uz.pdp.model.User;

public interface ClothService {

    void clothMenu(User user);

    void showClothList();

    void addCloth();

    void updateCloth();

    void deleteCloth();


}
