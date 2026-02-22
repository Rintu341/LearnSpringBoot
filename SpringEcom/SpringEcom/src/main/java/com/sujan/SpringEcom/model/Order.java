package com.sujan.SpringEcom.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Oder {
    private int orderId;
    private String customerName;
    private String email;
    private String status;
    private LocalDate orderDate;
    private List<OrderItem> items;
}
