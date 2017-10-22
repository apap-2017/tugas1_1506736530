package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.PendudukMapper;
import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
    @Autowired
    private PendudukMapper pendudukMapper;

	@Override
	public PendudukModel getPenduduk(String NIK) {
		log.info("select penduduk with nik {}", NIK);
		return pendudukMapper.getPenduduk(NIK);
	}

	@Override
	public AlamatModel getAlamatPenduduk(String NIK) {
		log.info("select alamat penduduk with nik {}", NIK);
		return pendudukMapper.getAlamatWithNIK(NIK);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info("penduduk added dengan nik {}", penduduk.getNik());
		pendudukMapper.addPenduduk(penduduk);
	}

	@Override
	public AlamatModel getAlamatKeluarga(int id_keluarga) {
		log.info("select for alamat keluarga by id = {}", id_keluarga);
		return pendudukMapper.getAlamatWithID(id_keluarga);
	}

	@Override
	public int countNIK(String nik) {
		log.info("counting penduduk with "+ nik + " nik");
		log.info(""+pendudukMapper.countPendudukWithSameNIK(nik));
		return pendudukMapper.countPendudukWithSameNIK(nik);
	}

	@Override
	public void updatePenduduk(int id, String nik, String nama, String tempat_lahir, String tanggal_lahir, 
			int jenis_kelamin, int is_wni, int id_keluarga, String agama, String pekerjaan, String status_perkawinan, 
			String status_dalam_keluarga, String golongan_darah) {
		log.info("update penduduk dengan id " + id);
		pendudukMapper.updatePenduduk(id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, 
				status_dalam_keluarga, golongan_darah);
	}

	@Override
	public int getId(String nik) {
		log.info("get id of penduduk with nik {}", nik);
		return pendudukMapper.selectId(nik);
	}

	@Override
	public void setPendudukIs_wafat(String nik) {
		log.info("Set penduduk with nik {} is wafat value to 1", nik);
		pendudukMapper.setPendudukIs_wafat(nik);
	}

	@Override
	public int countAnggotaKeluargaHidup(String id_keluarga) {
		log.info("count how many penduduk with the same id keluarga {} that are still hidup", id_keluarga);
		return pendudukMapper.countAnggotaKeluargaHidup(id_keluarga);
	}

	@Override
	public void setKeluargaPendudukIs_tidak_berlaku(String id_keluarga) {
		log.info("make keluarga with id {} is_tidak_berlaku to 1", id_keluarga);
		pendudukMapper.setKeluargaPendudukIs_tidak_berlaku(id_keluarga);
	}

	@Override
	public void updateNik(int id, String nik) {
		log.info("update nik penduduk with id {} new nik {}", id, nik);
		pendudukMapper.updateNik(id, nik);
	}

	@Override
	public List<PendudukModel> getPendudukListByIdKelurahan(int id) {
		log.info("select all penduduk with id kelurahan of {}", id);
		return pendudukMapper.selectPendudukByIdKelurahan(id);
	}
}
