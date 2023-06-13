package usersCrud.app;

import java.util.*;

public interface UserRepository {
    users createUser(users user);
    users updateUser(users user);
    users getUser(int id);
    List<users> getAllUsers();
    void deleteUser(int id);
}

