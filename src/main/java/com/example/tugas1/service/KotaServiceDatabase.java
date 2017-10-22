package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KotaMapper;
import com.example.tugas1.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService
{
    @Autowired
    private KotaMapper kotaMapper;

	@Override
	public List<KotaModel> getKotaList() {
		log.info("select all kota");
		return kotaMapper.selectKota();
	}

	@Override
	public String getNama(int id) {
		log.info("select kota where id {}", id);
		return kotaMapper.selectNamaKota(id);
	}

	
}
