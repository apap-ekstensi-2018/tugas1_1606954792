package com.example.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdiModel
{
    @NotNull
	private int id;
    private String kode_prodi;
    private String nama_prodi;
    private int id_fakultas;
}
