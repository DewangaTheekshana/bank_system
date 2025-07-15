package lk.jiat.app.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "select u from User u join u.customer c where c.email =:email"),
        @NamedQuery(name = "User.findAllUsers", query = "select u from User u")
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String username;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    public User() {}

    public User(Customer customer, String username, String passwordHash, UserType userType, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.customer = customer;
        this.username = username;
        this.passwordHash = passwordHash;
        this.userType = userType;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}