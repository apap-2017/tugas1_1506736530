package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KecamatanMapper;
import com.example.tugas1.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService
{
    @Autowired
    private KecamatanMapper kecamatanMapper;

	@Override
	public List<KecamatanModel> getKecmatanList(String idKota) {
		log.info("select kecamatan by id kota {}", idKota);
		return kecamatanMapper.getKecamatanList(idKota);
	}

	@Override
	public String getNama(int id) {
		log.info("select nama kecamatan by id {}", id);
		return kecamatanMapper.getNama(id);
	}
	
}
