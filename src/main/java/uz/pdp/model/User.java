package uz.pdp.model;

import uz.pdp.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

public class User {
    //Properties
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private double balance;
    private Role role;
    private List<Cloth> myCart;
    private LocalDateTime dateTime;
    private String firstName;
    private String secondName;
    private String username;
    private String phoneNumber;


    ///for bot
    public User(int id, double balance, Role role, List<Cloth> myCart, LocalDateTime dateTime, String firstName, String secondName, String username, String phoneNumber) {
        this.id = id;
        this.balance = balance;
        this.role = role;
        this.myCart = myCart;
        this.dateTime = dateTime;
        this.firstName = firstName;
        this.secondName = secondName;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public User(int id) {
        this.id = id;
    }

    public User() {
    }

    //for user
    public User(int id, String name, String email, String password, boolean isActive, double balance, Role role, List<Cloth> myCart, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.balance = balance;
        this.role = role;
        this.myCart = myCart;
        this.dateTime = dateTime;
    }

    public User(int id, String name, String email, String password, boolean isActive, double balance, Role role, List<Cloth> myCart) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.balance = balance;
        this.role = role;
        this.myCart = myCart;
    }

    //For admin
    public User(int id, String name, String email, String password, boolean isActive, double balance, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.balance = balance;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Cloth> getMyCart() {
        return myCart;
    }

    public void setMyCart(List<Cloth> myCart) {
        this.myCart = myCart;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", balance=" + balance +
                ", role=" + role +
                ", myCart=" + myCart +
                ", DateTime=" + dateTime +
                '}';
    }
}
