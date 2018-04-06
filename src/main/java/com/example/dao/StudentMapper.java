package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.*;

@Mapper
public interface StudentMapper
{
    @Select("select m.npm as npm, m.nama as nama, m.tempat_lahir as tempat, concat((substring(m.tanggal_lahir,9,2)),'-',"
    		+ "(substring(m.tanggal_lahir,6,2)),'-',(substring(m.tanggal_lahir,1,4))) as tanggal, p.nama_prodi as prodi, "
    		+ "f.nama_fakultas as fakultas, u.nama_univ as univ,  m.jenis_kelamin as jenkel, m.agama as agama, "
    		+ "m.golongan_darah as goldar, m.tahun_masuk as tahun, m.jalur_masuk as jalur, m.status as status "
    		+ "from mahasiswa as m, fakultas as f, program_studi p, universitas u where "
    		+ "m.id_prodi = p.id and p.id_fakultas = f.id and f.id_univ = u.id and npm = #{npm}")
    StudentModel selectStudent (@Param("npm") String npm);

    @Select("select npm, name, gpa from student")
    List<StudentModel> selectAllStudents ();

    @Insert("INSERT INTO mahasiswa (npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, "
    		+ "status, tahun_masuk, jalur_masuk, id_prodi) "
    		+ "VALUES (#{npm}, #{nama}, #{tempat}, #{tanggal}, #{jenkel}, "
    		+ "#{agama}, #{goldar}, #{status}, #{tahun}, #{jalur}, #{idprodi})")
    void addStudent (StudentModel student);
    
    @Delete("Delete from student where npm = #{npm}")
    void deleteStudent(@Param("npm") String npm);
    
    @Select("select * from program_studi where id = #{id}")
    ProdiModel selectProdi(@Param("id") int id);
    
    @Select("select * from fakultas where id = #{id}")
    FakultasModel selectFakultas(@Param("id") int id);
    
    @Select("select * from universitas where id = #{id}")
    UniversitasModel selectUniversitas(@Param("id") int id);
    
    @Select("SELECT substring(npm,10,3) FROM mahasiswa " +
            "where npm like #{npm} " +
            "order by substring(npm,10,3) desc " +
            "limit 1")
    Integer getNpm(@Param("npm") String npm);
    
    @Select("select * from program_studi")
    List<ProdiModel> selectAllStudyProgram ();
    
    @Update("UPDATE mahasiswa SET npm=#{npm_baru}, nama=#{nama},tempat_lahir=#{tempat_lahir},tanggal_lahir=#{tanggal_lahir}," +
            "jenis_kelamin=#{jenis_kelamin},agama=#{agama},golongan_darah=#{golongan_darah},status=#{status}," +
            "tahun_masuk=#{tahun_masuk},jalur_masuk=#{jalur_masuk},id_prodi=#{id_prodi} WHERE npm=#{npm_lama}")
    void updateStudent( @Param("npm_lama") String npm_lama,
                        @Param("npm_baru") String npm_baru,
                        @Param("nama") String nama,
                        @Param("tempat_lahir") String tempat_lahir,
                        @Param("tanggal_lahir") String tanggal_lahir,
                        @Param("jenis_kelamin") int jenis_kelamin,
                        @Param("agama") String agama,
                        @Param("golongan_darah") String golongan_darah,
                        @Param("status") String status,
                        @Param("tahun_masuk") String tahun_masuk,
                        @Param("jalur_masuk") String jalur_masuk,
                        @Param("id_prodi") int id_prodi);
}
