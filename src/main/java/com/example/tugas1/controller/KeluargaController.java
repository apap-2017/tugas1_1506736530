package com.example.tugas1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.KeluargaService;
import com.example.tugas1.service.PendudukService;

@Controller
public class KeluargaController {
	@Autowired
    KeluargaService keluargaService;
	@Autowired
    PendudukService pendudukService;
	
	@RequestMapping("/keluarga/tambah")
    public String tambahKeluarga()
    {
        return "form-tambah-keluarga";
    }
	
	@RequestMapping("/keluarga")
	public String findKeluarga (@RequestParam(value = "nkk", required = false, defaultValue="0") String nkk, Model model)
	{
		KeluargaModel keluarga = keluargaService.getKeluarga(nkk);
		
		if(keluarga == null){
			return "not-found";
		} else{
			AlamatModel alamat = keluargaService.getAlamatKeluarga(nkk);
			
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("alamat", alamat);
						
			return "show-keluarga";
		}
	}
	
	@RequestMapping(value = "/keluarga/tambah/", method = RequestMethod.POST)
    public String addKeluarga (Model model,
            @RequestParam(value = "alamat", required = false) String alamat,
            @RequestParam(value = "rt", required = false) String rt,
            @RequestParam(value = "rw", required = false) String rw,
            @RequestParam(value = "nama_kelurahan", required = false) String nama_kelurahan,
            @RequestParam(value = "nama_kecamatan", required = false) String nama_kecamatan,
            @RequestParam(value = "nama_kota", required = false) String nama_kota)
    {
		
		String kode_kecamatan = keluargaService.getKodeKecamatan(nama_kecamatan);
		String id_kelurahan = keluargaService.getIDKelurahan(nama_kelurahan);
		String nkk = "" + kode_kecamatan;
		nkk = nkk.substring(0, nkk.length()-1);
		nkk = nkkGenerator(nkk);
		
		model.addAttribute("nkk", nkk);
		model.addAttribute("flag", true);
		
		keluargaService.addKeluarga(nkk, alamat, rt, rw, id_kelurahan, 0);
        return "form-tambah-keluarga";
    }
	
	public String nkkGenerator(String nkk) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		
		String dateKeluaran = dateFormat.format(date);
		dateKeluaran = dateKeluaran.substring(dateKeluaran.length()-2,dateKeluaran.length())  + dateKeluaran.substring(5,7) + dateKeluaran.substring(2,4);

		nkk += dateKeluaran;
		
		int urutan = keluargaService.countNKK("%"+nkk+"%") + 1;
		nkk += String.format("%04d", urutan);
		
		urutan = keluargaService.countNKK("%"+nkk+"%");
		if(urutan >= 1){
			nkk = "" + (Integer.parseInt(nkk) + 1);
		}
		return nkk;
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String updatePendudukSubmit(Model model, PendudukModel penduduk, @RequestParam(value = "alamat", required = false) String alamat_baru,
    		@RequestParam(value = "rt", required = false) String rt,
    		@RequestParam(value = "rw", required = false) String rw,
    		@RequestParam(value = "nama_kelurahan", required = false) String nama_kelurahan,
    		@RequestParam(value = "nama_kecamatan", required = false) String nama_kecamatan,
    		@RequestParam(value = "nama_kota", required = false) String nama_kota, 
    		@RequestParam(value = "nama_kecamatan_lama", required = false) String nama_kecamatan_lama,
    		@RequestParam(value = "nama_kelurahan_lama", required = false) String nama_kelurahan_lama,
    		@RequestParam(value = "nama_kota_lama", required = false) String nama_kota_lama,
    		@RequestParam(value = "nomor_kk_lama", required = false) String nomor_kk_lama,
    		@RequestParam(value = "is_tidak_berlaku", required = false) int is_tidak_berlaku){
    	
		if(!nama_kelurahan.equalsIgnoreCase(nama_kelurahan_lama) || !nama_kecamatan.equalsIgnoreCase(nama_kecamatan_lama) || 
				!nama_kota.equalsIgnoreCase(nama_kota_lama)){
			int id = keluargaService.selectId(nomor_kk_lama);
			List<PendudukModel> anggotaKeluarga = keluargaService.selectAnggotaKeluarga(id);
			String nkk = "";
			
			nkk += "" + keluargaService.getKodeKecamatan(nama_kecamatan).substring(0, 6);
			
			for(int i=0; i<anggotaKeluarga.size(); i++){
				String nik = anggotaKeluarga.get(i).getNik();
				nik = nik.substring(6, 12);
				nik = nkk + nik;
				
				int urutan = pendudukService.countNIK("%"+nik+"%") + 1;
				nik += String.format("%04d", urutan);
				pendudukService.updateNik(anggotaKeluarga.get(i).getId(), nik);
			}
			
			nkk = nkk.substring(0, nkk.length()-1);
			nkk = nkkGenerator(nkk);
			
			String id_kelurahan = keluargaService.getIDKelurahan(nama_kelurahan);
			model.addAttribute("nomor_kk", nomor_kk_lama);
			
			keluargaService.updateKeluarga(id, nkk, alamat_baru, rt, rw, id_kelurahan, is_tidak_berlaku);
			
			model.addAttribute("nomor_kk", nkk);
    		model.addAttribute("alamat", keluargaService.getAlamatKeluarga(nkk));
    		model.addAttribute("is_tidak_berlaku", is_tidak_berlaku);
    		model.addAttribute("flag",true);

	    	return "form-ubah-keluarga";
		} else{
			int id = keluargaService.selectId(nomor_kk_lama);
			String id_kelurahan = keluargaService.getIDKelurahan(nama_kelurahan);
			model.addAttribute("nomor_kk", nomor_kk_lama);
			String nkk = nomor_kk_lama;
			keluargaService.updateKeluarga(id, nkk, alamat_baru, rt, rw, id_kelurahan, is_tidak_berlaku);
			model.addAttribute("nomor_kk", nkk);
    		model.addAttribute("alamat", keluargaService.getAlamatKeluarga(nkk));
    		model.addAttribute("is_tidak_berlaku", is_tidak_berlaku);
    		model.addAttribute("flag",true);
	    	return "form-ubah-keluarga";
		}
    }
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.GET)
    public String updatePenduduk(Model model, @PathVariable(value = "nkk") String nkk){
    	if((keluargaService.getKeluarga(nkk)) != null){
    		model.addAttribute("nomor_kk", nkk);
    		model.addAttribute("alamat", keluargaService.getAlamatKeluarga(nkk));
    		model.addAttribute("is_tidak_berlaku", keluargaService.selectIs_tidak_berlaku(nkk));
    		return "form-ubah-keluarga";
    	} else{
    		return "not-found";
    	
    	}
    }
	
	
}
