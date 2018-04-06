package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentMapper;
import com.example.model.FakultasModel;
import com.example.model.ProdiModel;
import com.example.model.StudentModel;
import com.example.model.UniversitasModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("select student with npm {}", npm);
        return studentMapper.selectStudent (npm);
    }


    @Override
    public List<StudentModel> selectAllStudents ()
    {
        log.info ("select all students");
        return studentMapper.selectAllStudents ();
    }


    @Override
    public void addStudent (StudentModel student)
    {
        studentMapper.addStudent (student);
    }


    @Override
    public void deleteStudent (String npm)
    {
    	log.info("student"+ npm + "deleted");    
    	studentMapper.deleteStudent(npm);
    }
    
    @Override
    public void updateStudent( String npm_lama, String npm_baru,String nama,
            String tempat_lahir,String tanggal_lahir,
            int jenis_kelamin,String agama,
            String golongan_darah,
            String status,
            String tahun_masuk,
            String jalur_masuk,
            int id_prodi)
	{
			log.info("Student " + npm_baru + "updated");
			studentMapper.updateStudent( npm_lama, npm_baru, nama, 
					tempat_lahir, tanggal_lahir, jenis_kelamin, agama, 
					golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi);
	}
    
    @Override
    public ProdiModel selectProdi(int id) {
    	log.info ("select prodi with id "+id);
    	return studentMapper.selectProdi(id);
    }
    
    @Override
    public FakultasModel selectFakultas(int id) {
    	log.info ("select fakultas with id "+id);
    	return studentMapper.selectFakultas(id);
    }
    
    @Override
    public UniversitasModel selectUniversitas(int id) {
    	log.info ("select Universitas with id "+id);
    	return studentMapper.selectUniversitas(id);
    }
    
    public Integer getNpm(String npm) {
    	log.info ("getNpm with npm "+npm);
    	return studentMapper.getNpm(npm);
    }


	@Override
	public List<ProdiModel> selectAllStudyProgram() {
		// TODO Auto-generated method stub
		log.info ("select all study programs");
        return studentMapper.selectAllStudyProgram();
	}

}
