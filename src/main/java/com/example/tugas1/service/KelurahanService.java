package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KelurahanModel;

public interface KelurahanService
{
	List<KelurahanModel> getKelurahanList (String kc);
	String getNama(int id);
}
