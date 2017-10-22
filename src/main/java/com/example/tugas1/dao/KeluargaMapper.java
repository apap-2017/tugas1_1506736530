package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	 @Select("select kg.id, kg.nomor_kk from keluarga kg where kg.nomor_kk = #{nkk}")
	    @Results(value = {
	    		@Result(property="nomor_kk", column="nomor_kk"),
	    		@Result(property="anggotaKeluarga",
	    		column="id", javaType = List.class, many=@Many(select="selectAnggotaKeluarga"))
	    })
	KeluargaModel findKeluargaByNKK (@Param("nkk") String nkk);
	 
	@Select("select id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, "
			+ "is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, "
			+ "status_dalam_keluarga, golongan_darah, is_wafat from penduduk where id_keluarga = #{id}")
	List<PendudukModel> selectAnggotaKeluarga (@Param("id") int id);
	
	@Select("select kg.alamat, kg.rt, kg.rw, kl.nama_kelurahan, "
	 		+ "kc.nama_kecamatan, "
	 		+ "k.nama_kota from keluarga kg,"
			 + " kota k, kecamatan kc, kelurahan kl where kg.nomor_kk = #{nkk} "
			 + " and kg.id_kelurahan = kl.id"
			 + " and kl.id_kecamatan = kc.id and kc.id_kota = k.id")
	    @Results(value = {
	    		@Result(property="alamat", column="alamat"),
	    		@Result(property="rt", column="rt"),
	    		@Result(property="rw", column="rw"), 
	    		@Result(property="kelurahan", column="nama_kelurahan"),
	    		@Result(property="kecamatan", column="nama_kecamatan"),
	    		@Result(property="kota", column="nama_kota")
	    })
	AlamatModel findAlamatKeluargaByNKK (@Param("nkk") String nkk);
	
	@Insert("INSERT INTO keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) "
			+ "VALUES (#{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void addKeluarga (@Param("nomor_kk") String nomor_kk, @Param("alamat") String alamat, @Param("rt") String rt, 
			@Param("rw") String rw, @Param("id_kelurahan") String id_kelurahan, @Param("is_tidak_berlaku") int is_tidak_berlaku);
	
	@Select("select kode_kecamatan from kecamatan where nama_kecamatan = #{nama_kecamatan}")
	String getKodeKecamatanByNamaKecamatan(String nama_kecamatan);
	
	@Select("select count(*) from penduduk where nik like #{nkk}")
	int countNKK(String nkk);
	
	@Select("select id from kelurahan where nama_kelurahan = #{nama_kelurahan}")
	String getIdKelurahanByNamaKelurahan(String nama_kelurahan);
	
	@Select("select id from keluarga where nomor_kk = #{nkk}")
	int selectId(String nkk);
	
	@Select("select is_tidak_berlaku from keluarga where nomor_kk = #{nkk}")
	int selectIs_tidak_berlaku(String nkk);
	
	@Update("UPDATE keluarga SET nomor_kk = #{nkk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan}, "
			+ "is_tidak_berlaku = #{is_tidak_berlaku}  WHERE id = #{id}")
    void updateKeluarga(@Param("id") int id, @Param("nkk") String nkk, @Param("alamat") String alamat, @Param("rt") String rt,  
    		@Param("rw") String rw, @Param("id_kelurahan") String id_kelurahan, @Param("is_tidak_berlaku") int is_tidak_berlaku);

}
