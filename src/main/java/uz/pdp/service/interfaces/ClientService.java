package uz.pdp.service.interfaces;

import uz.pdp.model.User;

public interface ClientService {

    void clientMenu(User user);

    void showClientList();

    void blockClient();

    void unblockClient();

}
