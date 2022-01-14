package com.domanov.bonusservice.model;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "privilege_history", schema = "privileges")
@Check(constraints = "operation_type IN ('FILL_IN_BALANCE', 'DEBIT_THE_ACCOUNT')")
public class PrivilegeHistory implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    private Privilege privilege;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "ticket_uid", nullable = false, unique = true)
    private UUID ticket_uid;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "balance_diff", nullable = false)
    private Integer balanceDiff;

    @Column(name = "operation_type", length = 20, nullable = false)
    private String operationType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public UUID getTicket_uid() {
        return ticket_uid;
    }

    public void setTicket_uid(UUID ticket_uid) {
        this.ticket_uid = ticket_uid;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getBalanceDiff() {
        return balanceDiff;
    }

    public void setBalanceDiff(Integer balanceDiff) {
        this.balanceDiff = balanceDiff;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
