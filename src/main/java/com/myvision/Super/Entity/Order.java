package com.myvision.Super.Entity;

import lombok.Data;
import lombok.ToString;
import org.apache.groovy.util.Maps;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;


@Entity

@Data
@ToString
@Table(name = "Ordre")

public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String montant;
    private String creditcard;
    private String name;
    private int phonenumber;
    private String proffession;
    private String country;


}
