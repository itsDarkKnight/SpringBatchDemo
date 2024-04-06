package com.SpringBatch.example;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Complain {
    private String ID;
    private String Name;
    private String Company;
    private String product;
    private String Submit_via;
    private String State;
}
