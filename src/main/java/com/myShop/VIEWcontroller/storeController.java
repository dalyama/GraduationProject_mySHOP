package com.myShop.VIEWcontroller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myShop.entities.store;
import com.myShop.services.storeService;

@Controller
@RequestMapping("api/store")
public class storeController {

	@Autowired
	storeService storeService;
	@GetMapping("/management")
	public String storePanel(Model model) {
		model.addAttribute("stores", storeService.getAllStores());
		return "store-management";
	}
	
	@GetMapping("/addStore")
	public String addStoreForm(Model model , @RequestParam(value = "id", required=false) Long id) {
		if (id != null) {
			System.out.println("storeController.addStoreForm()" + id );
			model.addAttribute("store", storeService.getStore(id));
		}
		else {
			System.out.println("storeController.addStoreForm()" + id);
			
			model.addAttribute("store", new store());
		}
		return "addStore";
	}
	@GetMapping(value = "/addStore/{id}")
	public String updateStoreForm(Model model , @PathVariable("id") Long id) {
		model.addAttribute("storeStatus", "Update Store");
		model.addAttribute("store", storeService.getStore(id));
		return "addStore";
	}
	
	@PostMapping("/addStore")
	public String addStore(@Valid store store , @RequestParam(value = "id",required = false ) Long id) {
		if (id != null ) {
			System.out.println("storeController.addStore()" + id);
			store.setId(id);
		}
		storeService.saveStore(store);
		return "redirect:/api/store/management";
	}
	
	
	
}
