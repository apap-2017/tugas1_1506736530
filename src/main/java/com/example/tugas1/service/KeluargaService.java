package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

public interface KeluargaService
{
	KeluargaModel getKeluarga(String NKK);
	AlamatModel getAlamatKeluarga(String NKK);
	String getKodeKecamatan(String nama_kecamatan);
	String getIDKelurahan(String nama_kecamatan);
	int countNKK (String nkk);
	int selectId (String nkk);
	int selectIs_tidak_berlaku (String nkk);
	void addKeluarga(String nomor_kk, String alamat, String rt, String rw, String id_kelurahan, int is_tidak_berlaku);
	void updateKeluarga(int id, String nkk, String alamat, String rt, String rw, String id_kelurahan, int is_tidak_berlaku);
	List<PendudukModel> selectAnggotaKeluarga(int id);
}
