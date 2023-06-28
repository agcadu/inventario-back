package com.agcadu.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "product")
public class Product  implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        private String name;

        private int price;

        private int stock;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
        private Category category;


        @Column(name = "picture", length = 10000)
        private byte[] picture;
}
