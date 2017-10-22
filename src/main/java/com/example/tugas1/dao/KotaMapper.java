package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("select id, nama_kota from kota")
	List<KotaModel> selectKota ();
	
	@Select("select nama_kota from kota where id=#{id}")
	String selectNamaKota(@Param("id") int id);
}
