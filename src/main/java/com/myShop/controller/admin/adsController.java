package com.myShop.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myShop.entities.ad;
import com.myShop.services.adService;

@RequestMapping("/admin/ads")
@Controller
public class adsController {
	
	@Autowired
	adService adService;
	public static String uploadDirectory = "C:\\Users\\saeed\\eclipse-workspace\\2019\\myShopProject\\src\\main\\resources\\static\\images\\uploads\\adsFullWidth";
	//public static String uploadDirectory = "C:\\Users\\saeed\\Desktop\\uploads\\adsFullWidth";
	//public static String uploadDirectory = "C:/Users/saeed/eclipse-workspace/2019/myShopProject/src/main/resources/static/images/uploads/ad";
	
	@GetMapping("/home/fullwidth")
	public String newAdForm(Model model) {
		model.addAttribute("adsPhoto", new ad());
		return "adForm";
	}
	
	@PostMapping("/home/fullwidth")
	public String saveAd(@RequestParam("files") MultipartFile[] files ,@RequestParam("title") String title,
						@RequestParam("photoText") String photoText,@RequestParam("description") String description) {
		List<ad> adsPhotos = new ArrayList<ad>();
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file : files) {
			ad ad = new ad();
			String fileName = getRandomString();
			fileName =  fileName+"-"+file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
			//Path fileNameAndPath = Paths.get(uploadDirectory,file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				byte [] byteArr=file.getBytes();
				//InputStream inputStream = new ByteArrayInputStream(byteArr);
				ad.setFiles(byteArr);
				Files.write(fileNameAndPath, file.getBytes());
				ad.setTitle(title);
				ad.setName(fileName);
				ad.setDescription(description);
				ad.setPhotoText(photoText);
				adsPhotos.add(ad);
				adService.saveAd(ad);
			}catch(IOException e) {
				System.out.println("we have an error : "+e);
			}	
		}
		return "redirect:/admin/ads/home/fullwidth";
	}
	
	
	public String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String Key = salt.toString();
        return Key;

    }
}
