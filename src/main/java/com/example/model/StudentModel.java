package com.example.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel
{
    private String npm;
    
    @NotNull
    private String nama;
    
    @NotNull
    private String tempat;
    private String tanggal;
    private int jenkel;
    private String agama;
    private String goldar;
    private String status;
    private String tahun;
    private String jalur;
    private int idprodi;
    
}
