package usersCrud.app;

public class users {
    private int id;
    private String name;
    private String email;

    public users() {
    }

    public users(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
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

    // Additional methods
//    public void setInt(String property, int value) {
//        if (property.equals("id")) {
//            setId(value);
//        } else {
//            throw new IllegalArgumentException("Invalid property: " + property);
//        }
//    }
//
//    public int getInt(String property) {
//        if (property.equals("id")) {
//            return getId();
//        }
//        throw new IllegalArgumentException("Invalid property: " + property);
//    }

}


