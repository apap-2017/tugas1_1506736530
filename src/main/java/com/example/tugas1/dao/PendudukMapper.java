package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("select id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, "
			+ "status_perkawinan, status_dalam_keluarga, "
			+ "golongan_darah, is_wafat from penduduk where nik = #{nik}")
	@Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="nik", column="nik"),
    		@Result(property="nama", column="nama"), 
    		@Result(property="tempat_lahir", column="tempat_lahir"), 
    		@Result(property="tanggal_lahir", column="tanggal_lahir"),
    		@Result(property="jenis_kelamin", column="jenis_kelamin"),
    		@Result(property="is_wni", column="is_wni"),
    		@Result(property="id_keluarga", column="id_keluarga"),
    		@Result(property="agama", column="agama"),
    		@Result(property="pekerjaan", column="pekerjaan"),
    		@Result(property="status_perkawinan", column="status_perkawinan"),
    		@Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
    		@Result(property="golongan_darah", column="golongan_darah"),
    		@Result(property="is_wafat", column="is_wafat"),
    })
    PendudukModel getPenduduk (@Param("nik") String nik);
	
	 @Select("select kg.alamat, kg.rt, kg.rw, kl.nama_kelurahan, kc.nama_kecamatan, k.nama_kota "
	 		+ "from penduduk p, keluarga kg, kota k, kecamatan kc, kelurahan kl "
	 		+ "where p.nik = #{nik} "
			 + "and p.id_keluarga = kg.id "
			 + "and kg.id_kelurahan = kl.id "
			 + "and kl.id_kecamatan = kc.id "
			 + "and kc.id_kota = k.id")
	    @Results(value = {
	    		@Result(property="alamat", column="alamat"),
	    		@Result(property="rt", column="rt"),
	    		@Result(property="rw", column="rw"), 
	    		@Result(property="kelurahan", column="nama_kelurahan"),
	    		@Result(property="kecamatan", column="nama_kecamatan"),
	    		@Result(property="kota", column="nama_kota")
	    })
	AlamatModel getAlamatWithNIK (@Param("nik") String nik);
	
	 @Select("select kg.alamat, kg.rt, kg.rw, kl.nama_kelurahan, "
		 		+ "kc.nama_kecamatan, "
		 		+ "k.nama_kota, kl.kode_pos, kc.kode_kecamatan from keluarga kg,"
				 + " kota k, kecamatan kc, kelurahan kl where kg.id = #{id_keluarga} "
				 + " and kg.id_kelurahan = kl.id"
				 + " and kl.id_kecamatan = kc.id and kc.id_kota = k.id")
		    @Results(value = {
		    		@Result(property="alamat", column="alamat"),
		    		@Result(property="rt", column="rt"),
		    		@Result(property="rw", column="rw"), 
		    		@Result(property="kelurahan", column="nama_kelurahan"),
		    		@Result(property="kecamatan", column="nama_kecamatan"),
		    		@Result(property="kota", column="nama_kota"),
		    		@Result(property="kode_pos", column="kode_pos"),
		    		@Result(property="kode_kecamatan", column="kode_kecamatan")
		    })
	AlamatModel getAlamatWithID (@Param("id_keluarga") int id_keluarga);
	 
	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, "
			+ "status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{nik}, #{nama}, #{tempat_lahir}, "
			+ "#{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, "
			+ "#{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk (PendudukModel penduduk);
	 
	@Select("select count(*) from penduduk where nik like #{nik}")
	int countPendudukWithSameNIK(String nik);
	
	@Update("UPDATE penduduk SET nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, "
			+ "is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, "
			+ "status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah} WHERE id = #{id}")
    void updatePenduduk(@Param("id") int id, @Param("nik") String nik, @Param("nama") String nama, @Param("tempat_lahir") String tempat_lahir, @Param("tanggal_lahir") String tanggal_lahir, 
    		@Param("jenis_kelamin") int jenis_kelamin, @Param("is_wni") int is_wni, @Param("id_keluarga") int id_keluarga, @Param("agama") String agama, @Param("pekerjaan") String pekerjaan,
    		@Param("status_perkawinan") String status_perkawinan, @Param("status_dalam_keluarga") String status_dalam_keluarga, @Param("golongan_darah") String golongan_darah);
	
	@Select("select id from penduduk where nik = #{nik}")
	int selectId(String nik);
	
	@Update("UPDATE penduduk SET is_wafat = 1 WHERE nik = #{nik}")
	void setPendudukIs_wafat(String nik);
	
	@Update("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id = #{id_keluarga}")
	void setKeluargaPendudukIs_tidak_berlaku(String id_keluarga);
	
	@Update("UPDATE penduduk SET nik = #{nik} WHERE id = #{id}")
	void updateNik(@Param("id") int id, @Param("nik") String nik);
	
	@Select("select count(*) from penduduk where id_keluarga = #{id_keluarga} and is_wafat = 0")
	int countAnggotaKeluargaHidup (@Param("id_keluarga") String id_keluarga);
	
	@Select("select p.nik, p.nama, p.jenis_kelamin from penduduk p, keluarga kg where #{id} = kg.id_kelurahan and p.id_keluarga= kg.id")
	@Results(value = {
    		@Result(property="nik", column="nik"),
    		@Result(property="nama", column="nama"),
    		@Result(property="jenis_kelamin", column="jenis_kelamin")
    })
	List<PendudukModel> selectPendudukByIdKelurahan(@Param("id") int id);
}
