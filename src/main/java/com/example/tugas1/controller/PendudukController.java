package com.example.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.KecamatanService;
import com.example.tugas1.service.KeluargaService;
import com.example.tugas1.service.KelurahanService;
import com.example.tugas1.service.KotaService;
import com.example.tugas1.service.PendudukService;

@Controller
public class PendudukController {
	


	@Autowired
    KecamatanService kecamatanService;
	@Autowired
    KeluargaService keluargaService;
	@Autowired
    KelurahanService kelurahanService;	
	@Autowired
    KotaService kotaService;
	@Autowired
    PendudukService pendudukService;
		
	@RequestMapping("/")
    public String index ()
    {
        return "index";
    }
	
	@RequestMapping("/penduduk/tambah")
    public String tambahPenduduk()
    {
        return "form-tambah-penduduk";
    }
	
	@RequestMapping(value = "/penduduk", method = RequestMethod.GET)
	public String findPenduduk (@RequestParam(value = "nik", required = false, defaultValue="0") String nik, Model model)
	{
		PendudukModel penduduk = pendudukService.getPenduduk(nik);
		
		if(penduduk == null){
			return "not-found";
		
		} else{
			AlamatModel alamat = pendudukService.getAlamatPenduduk(nik);
			String kewarganegaraan = (penduduk.getIs_wni() == 1? "WNI":"WNA");
			String statusWafat = (penduduk.getIs_wafat() == 1? "Wafat":"Hidup");

			model.addAttribute("penduduk", penduduk);
			model.addAttribute("alamat", alamat);
			model.addAttribute("kewarganegaraan",kewarganegaraan);
			model.addAttribute("wafat", statusWafat);
			
			return "show-penduduk";
		}
	}
	
	@RequestMapping(value = "/penduduk/tambah/", method = RequestMethod.POST)
    public String addPenduduk (Model model,
            @RequestParam(value = "nama", required = false) String nama,
            @RequestParam(value = "jenis_kelamin", required = false) int jenis_kelamin,
            @RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
            @RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
            @RequestParam(value = "golongan_darah", required = false) String golongan_darah,
            @RequestParam(value = "agama", required = false) String agama,
            @RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
            @RequestParam(value = "pekerjaan", required = false) String pekerjaan,
            @RequestParam(value = "is_wni", required = false) int is_wni,
            @RequestParam(value = "is_wafat", required = false) int is_wafat,
            @RequestParam(value = "id_keluarga", required = false) int id_keluarga,
            @RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga)
    {
		
		AlamatModel alamat = pendudukService.getAlamatKeluarga(id_keluarga);
		
		String nik = nikGenerator(alamat, tanggal_lahir, jenis_kelamin);
		
		model.addAttribute("nik", nik);
		model.addAttribute("flag", true);
		
		pendudukService.addPenduduk(new PendudukModel(0,nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat));
		return "form-tambah-penduduk";
    }
	
	@RequestMapping("/penduduk/ubah/{nik}")
    public String updatePenduduk(Model model, @PathVariable(value = "nik") String nik){

		PendudukModel penduduk = pendudukService.getPenduduk(nik);
		
    	if(penduduk != null){
    		model.addAttribute("penduduk", penduduk);
    		model.addAttribute("nik", penduduk.getNik());
    		return "form-ubah-penduduk";
    	} else{
    		return "not-found";
    	
    	}
    }
	
	public String nikGenerator(AlamatModel alamat, String tanggal_lahir, int jenis_kelamin) {
		String nik = Integer.toString(alamat.getKode_kecamatan());
		nik = nik.substring(0, nik.length()-1);
		
		int tanggal = Integer.parseInt(tanggal_lahir.substring(tanggal_lahir.length()-2,tanggal_lahir.length()));
		tanggal = (jenis_kelamin == 0)? tanggal : tanggal + 40;
		nik += String.format("%02d", tanggal)  + tanggal_lahir.substring(5,7) + tanggal_lahir.substring(2,4);//2017/05/14 
				
		int urutan = pendudukService.countNIK("%"+nik+"%") + 1;
		nik += String.format("%04d", urutan);
		urutan = pendudukService.countNIK("%"+nik+"%");
		if(urutan >= 1){
			nik = "" + (Integer.parseInt(nik) + 1);
		}
		
		return nik;
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String updatePendudukPOST(Model model, PendudukModel penduduk, @RequestParam(value = "id_keluarga_lama", required = false) int id_keluarga_lama,
    		@RequestParam(value = "jenis_kelamin_lama", required = false) int jenis_kelamin_lama,
    		@RequestParam(value = "tanggal_lahir_lama", required = false) String tanggal_lahir_lama){
    	
		if(id_keluarga_lama != penduduk.getId_keluarga() || penduduk.getJenis_kelamin() != jenis_kelamin_lama || 
				!penduduk.getTanggal_lahir().equalsIgnoreCase(tanggal_lahir_lama)){
			
			AlamatModel alamat = pendudukService.getAlamatKeluarga(penduduk.getId_keluarga());
			
			String nik = nikGenerator(alamat, penduduk.getTanggal_lahir(), penduduk.getJenis_kelamin());
			
			int id = pendudukService.getId(penduduk.getNik());
			model.addAttribute("nik", penduduk.getNik());
			penduduk.setNik(nik);
			
			pendudukService.updatePenduduk(id, penduduk.getNik(), penduduk.getNama(), penduduk.getTempat_lahir(), penduduk.getTanggal_lahir()
					, penduduk.getJenis_kelamin(), penduduk.getIs_wni(), penduduk.getId_keluarga(), penduduk.getAgama(), penduduk.getPekerjaan(),
					penduduk.getStatus_perkawinan(), penduduk.getStatus_dalam_keluarga(), penduduk.getGolongan_darah());
			model.addAttribute("flag",true);
			model.addAttribute("penduduk", pendudukService.getPenduduk(penduduk.getNik()));
    		model.addAttribute("nik", nik);
	    	return "form-ubah-penduduk";
		} else{
			int id = pendudukService.getId(penduduk.getNik());
			model.addAttribute("nik", penduduk.getNik());
			pendudukService.updatePenduduk(id, penduduk.getNik(), penduduk.getNama(), penduduk.getTempat_lahir(), penduduk.getTanggal_lahir()
					, penduduk.getJenis_kelamin(), penduduk.getIs_wni(), penduduk.getId_keluarga(), penduduk.getAgama(), penduduk.getPekerjaan(),
					penduduk.getStatus_perkawinan(), penduduk.getStatus_dalam_keluarga(), penduduk.getGolongan_darah());
			model.addAttribute("penduduk", pendudukService.getPenduduk(penduduk.getNik()));
    		model.addAttribute("nik", penduduk.getNik());
    		model.addAttribute("flag",true);
	    	return "form-ubah-penduduk";
		}
    }
	
	@RequestMapping(value = "/penduduk", method = RequestMethod.POST)
	public String setStatusMati (@RequestParam(value = "nik", required = false, defaultValue="0") String nik, Model model)
	{
		PendudukModel penduduk = pendudukService.getPenduduk(nik);
		
		if(penduduk != null){
			model.addAttribute("nik", nik);
			pendudukService.setPendudukIs_wafat(nik);
			model.addAttribute("flag",true);
			
			
			if(pendudukService.countAnggotaKeluargaHidup(""+penduduk.getId_keluarga()) == 0){
				pendudukService.setKeluargaPendudukIs_tidak_berlaku(""+penduduk.getId_keluarga());
			}
			
			AlamatModel alamat = pendudukService.getAlamatPenduduk(nik);
			String kewarganegaraan = (penduduk.getIs_wni() == 1? "WNI":"WNA");
			String statusWafat = (penduduk.getIs_wafat() == 1? "Wafat":"Hidup");

			model.addAttribute("penduduk", penduduk);
			model.addAttribute("alamat", alamat);
			model.addAttribute("kewarganegaraan",kewarganegaraan);
			model.addAttribute("wafat", statusWafat);
			
		
		}
		return "show-penduduk";
	}
	
	
	
	@RequestMapping("/penduduk/cari")
    public String findDataPenduduk(@RequestParam(value = "kt", required = false) String kt, @RequestParam(value = "kc", required = false) String kc,
    		@RequestParam(value = "kl", required = false) String kl, Model model)
    {
		
		if(kl != null){
			model.addAttribute("kt_nama", kotaService.getNama(Integer.parseInt(kt)));
			model.addAttribute("kc_nama", kecamatanService.getNama(Integer.parseInt(kc)));
			model.addAttribute("kl_nama", kelurahanService.getNama(Integer.parseInt(kl)));
			model.addAttribute("flag_kl", true);
			model.addAttribute("kl", kl);
			
			List<PendudukModel> list_penduduk = pendudukService.getPendudukListByIdKelurahan(Integer.parseInt(kl));
			model.addAttribute("list_penduduk", list_penduduk);
			return "filtered-penduduk";

		}
		
		List<KotaModel> list_kota = kotaService.getKotaList();
		model.addAttribute("list_kota", list_kota);
		
		if(kt != null){
			model.addAttribute("kt_nama", kotaService.getNama(Integer.parseInt(kt)));
			model.addAttribute("flag_kt", true);
			model.addAttribute("kt", kt);
			if(kc == null){
				List<KecamatanModel> list_kecamatan = kecamatanService.getKecmatanList(kt);
				model.addAttribute("list_kecamatan", list_kecamatan);
			}
			if(kc != null){
				model.addAttribute("kc_nama", kecamatanService.getNama(Integer.parseInt(kc)));
				model.addAttribute("flag_kc", true);
				model.addAttribute("kc", kc);
				if(kl == null){
					List<KelurahanModel> list_kelurahan = kelurahanService.getKelurahanList(kc);
					model.addAttribute("list_kelurahan", list_kelurahan);
				}
				
			}
		} 
		return "cari-data-penduduk";
		
    }
}
