package com.example.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversitasModel
{
	@NotNull
    private int id;
    private String kode_univ;
    private String nama_univ; 
}
