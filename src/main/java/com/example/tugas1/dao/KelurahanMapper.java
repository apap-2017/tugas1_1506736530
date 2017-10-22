package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("select id, nama_kelurahan from kelurahan where id_kecamatan = #{kc}")
	List<KelurahanModel> selectKelurahanByIdKecamatan (String kc);
	
	@Select("select nama_kelurahan from kelurahan where id=#{id}")
	String selectNamaKelurahan(@Param("id") int id);
}
