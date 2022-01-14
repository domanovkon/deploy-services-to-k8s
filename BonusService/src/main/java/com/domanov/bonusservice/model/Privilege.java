package com.domanov.bonusservice.model;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "privilege", schema = "privileges")
@Check(constraints = "status IN ('BRONZE', 'SILVER', 'GOLD')")
public class Privilege implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "username", length = 80, unique = true, nullable = false)
    private String username;

    @ColumnDefault("'BRONZE'")
    @Column(name = "status", length = 80, nullable = false)
    private String status;

    @Column(name = "balance")
    private Integer balance;

//    @OneToMany(mappedBy = "privilege", cascade = CascadeType.ALL)
//    @OneToMany
//    @JoinTable(name = "privilege_history", joinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
//    private List<PrivilegeHistory> privilegeHistory ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}

