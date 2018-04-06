package com.example.service;

import java.util.List;

import com.example.model.*;

public interface StudentService
{
    StudentModel selectStudent (String npm);

    List<StudentModel> selectAllStudents ();

    void addStudent (StudentModel student);

    void deleteStudent (String npm);
    
    void updateStudent(String npm_lama, String npm_baru,
    		String nama,String tempat_lahir,String tanggal_lahir,
    		int jenis_kelamin,String agama,String golongan_darah,
    		String status,String tahun_masuk,String jalur_masuk,int id_prodi);
    
    ProdiModel selectProdi(int id);
    
    FakultasModel selectFakultas(int id);
    
    UniversitasModel selectUniversitas(int id);
    
    Integer getNpm(String npm);
    
    List<ProdiModel> selectAllStudyProgram();
    
}
