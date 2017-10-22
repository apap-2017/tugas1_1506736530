package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.PendudukModel;

public interface PendudukService
{
	PendudukModel getPenduduk(String NIK);
	AlamatModel getAlamatPenduduk(String NIK);
	AlamatModel getAlamatKeluarga(int id_keluarga);
	int getId(String nik);
	
	void setPendudukIs_wafat(String nik);
	void setKeluargaPendudukIs_tidak_berlaku(String id_keluarga);
	
	void addPenduduk(PendudukModel penduduk);
	
	
	void updatePenduduk(int id, String nik, String nama, String tempat_lahir, String tanggal_lahir, 
			int jenis_kelamin, int is_wni, int	 id_keluarga, String agama, String pekerjaan, String status_perkawinan, 
			String status_dalam_keluarga, String golongan_darah);
	void updateNik (int id, String nik);
	List<PendudukModel> getPendudukListByIdKelurahan(int id);
	
	int countAnggotaKeluargaHidup(String id_keluarga);
	int countNIK(String nik);
	
}
