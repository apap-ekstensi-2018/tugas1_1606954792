package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.*;
import com.example.service.StudentService;
import static org.thymeleaf.util.StringUtils.*;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;
    private String kode_jalur, Nomor, npm_generate;

    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/mahasiswa/tambah")
    public String add ()
    {
        return "tambah";
    }


    @RequestMapping(value="/mahasiswa/tambah/submit", method = RequestMethod.POST)
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "nama", required = false) String nama,
            @RequestParam(value = "tempat", required = false) String tempat,
            @RequestParam(value = "tanggal", required = false) String tanggal,
            @RequestParam(value = "jenkel", required = false) int jenkel,
            @RequestParam(value = "agama", required = false) String agama,
            @RequestParam(value = "goldar", required = false) String goldar,
            @RequestParam(value = "tahun", required = false) String tahun,
            @RequestParam(value = "jalur", required = false) String jalur,
            @RequestParam(value = "idprodi", required = false) int idprodi, Model model)
    {
    	
    	ProdiModel prodi = studentDAO.selectProdi(idprodi);
    	FakultasModel fak = studentDAO.selectFakultas(prodi.getId_fakultas());
    	UniversitasModel univ = studentDAO.selectUniversitas(fak.getId_univ());
    	
    	if(jalur.equals("Undangan Olimpiade")){
            kode_jalur = "53";
        } else if(jalur.equals("Undangan Reguler/SNMPTN")){
            kode_jalur = "54";
        } else if(jalur.equals("Undangan Paralel/PPKB")){
            kode_jalur = "55";
        } else if(jalur.equals("Ujian Tulis Bersama/SBMPTN")){
            kode_jalur = "57";
        } else if(jalur.equals("Ujian Tulis Mandiri")){
            kode_jalur = "62";
        }

        Integer urutan_npm = studentDAO.getNpm(concat("%",tahun.substring(2,4),univ.getKode_univ(),prodi.getKode_prodi(),kode_jalur,"%"));
        if(urutan_npm != null){
            if (urutan_npm < 10){
                urutan_npm += 1;
                Nomor = concat("00",String.valueOf(urutan_npm));
            } else if(urutan_npm < 100){
                urutan_npm += 1;
                Nomor = concat("0", String.valueOf(urutan_npm));
            } else {
                urutan_npm += 1;
                Nomor = String.valueOf(urutan_npm);
            }
        }else{
            Nomor = "001";
        }
        String npm_generate = concat(tahun.substring(2,4),univ.getKode_univ(),prodi.getKode_prodi(),kode_jalur, Nomor);

        if(npm_generate.isEmpty()){
            return "not-found";
        }else{
        	StudentModel student = new StudentModel (npm_generate, nama, tempat,
                    tanggal, jenkel, agama, goldar, "Aktif" ,
                    tahun, jalur, idprodi);
            studentDAO.addStudent (student);
            model.addAttribute("selectStudent", npm_generate);
            return "success-add";
        }
    }


    @RequestMapping("/mahasiswa")
    public String view (Model model,
    		@RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
        	studentDAO.deleteStudent (npm);
            return "delete";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    	
    }
    
    @RequestMapping("/mahasiswa/ubah/{npm}")
    public String viewPath(Model model, 
    		@PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            List<ProdiModel> studyProgram = studentDAO.selectAllStudyProgram();
            model.addAttribute("studyProgram", studyProgram);
            model.addAttribute ("student", student);
            return "ubah";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }

    @RequestMapping(value = "/mahasiswa/ubah/{npm}", method = RequestMethod.POST)
    public String updateSubmit (@ModelAttribute("student") StudentModel student, ModelMap model)
    {
        if(student.getNpm() == null || student.getNpm() == "") {
            return "error";
        }

        ProdiModel studyProgram = studentDAO.selectProdi(student.getIdprodi());
        FakultasModel faculty = studentDAO.selectFakultas(studyProgram.getId_fakultas());
        UniversitasModel university = studentDAO.selectUniversitas(faculty.getId_univ());

        StudentModel studentValid = studentDAO.selectStudent(student.getNpm());
        if((!student.getJalur().equals(studentValid.getJalur()))  ||
            (!student.getTahun().equals(studentValid.getTahun())) ||
            (student.getIdprodi() != studentValid.getIdprodi()))
        {
            if(student.getJalur().equals("Undangan Olimpiade")){
                kode_jalur = "53";
            } else if(student.getJalur().equals("Undangan Reguler/SNMPTN")){
                kode_jalur = "54";
            } else if(student.getJalur().equals("Undangan Paralel/PPKB")){
                kode_jalur = "55";
            } else if(student.getJalur().equals("Ujian Tulis Bersama/SBMPTN")){
                kode_jalur = "57";
            } else if(student.getJalur().equals("Ujian Tulis Mandiri")){
                kode_jalur = "62";
            }

            Integer urutan_npm = studentDAO.getNpm(concat("%",student.getTahun().
                    substring(2,4),
                    university.getKode_univ(),
                    studyProgram.getKode_prodi(),
                    kode_jalur,"%"));
            if(urutan_npm != null){
                if (urutan_npm < 10){
                    urutan_npm += 1;
                    Nomor = concat("00",String.valueOf(urutan_npm));
                } else if(urutan_npm < 100){
                    urutan_npm += 1;
                    Nomor = concat("0", String.valueOf(urutan_npm));
                } else {
                    urutan_npm += 1;
                    Nomor = String.valueOf(urutan_npm);
                }
            }else{
                Nomor = "001";
            }
            npm_generate = concat(student.getTahun().substring(2,4),university.getKode_univ(),studyProgram.getKode_prodi(),
            		kode_jalur, Nomor);

            studentDAO.updateStudent(student.getNpm(), npm_generate,
                    student.getNama(), student.getTempat(),
                    student.getTanggal(), student.getJenkel(),
                    student.getAgama(), student.getGoldar(),
                    student.getStatus(), student.getTahun(),
                    student.getJalur(), student.getIdprodi());
            model.addAttribute("npm",npm_generate);
            return "success-update";

        } else {

            studentDAO.updateStudent(student.getNpm(), student.getNpm(),
                    student.getNama(), student.getTempat(),
                    student.getTanggal(), student.getJenkel(),
                    student.getAgama(), student.getGoldar(),
                    student.getStatus(), student.getTahun(),
                    student.getJalur(), student.getIdprodi());
            model.addAttribute("npm",student.getNpm());
            return "success-update";
        }


    }
    
    /*@RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit (
    		@Valid @ModelAttribute StudentModel student, BindingResult bindingresult)
	{
    	if(bindingresult.hasErrors()) {
    		return "not-found";
    	}else {
    		studentDAO.updateStudent(student);
            return "success-update";
    	}
    }*/

}
