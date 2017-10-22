package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("select id, nama_kecamatan from kecamatan where id_kota = #{kt}")
	List<KecamatanModel> getKecamatanList(String kt);
	
	@Select("select nama_kecamatan from kecamatan where id=#{id}")
	String getNama(@Param("id") int id);
}
