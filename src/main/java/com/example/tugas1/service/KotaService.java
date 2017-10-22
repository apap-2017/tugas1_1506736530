package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KotaModel;

public interface KotaService
{
	List<KotaModel> getKotaList ();
	String getNama (int id);
}
