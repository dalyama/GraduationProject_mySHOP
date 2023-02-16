package com.myShop.VIEWcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myShop.RESTcontroller.productRESTcontroller;
import com.myShop.RESTcontroller.stockRESTcontroller;
import com.myShop.entities.ad;
import com.myShop.entities.product;
import com.myShop.entities.userImage;
import com.myShop.repository.productRepo;
import com.myShop.entities.User;
import com.myShop.services.adService;
import com.myShop.services.categoryService;
import com.myShop.services.productService;
import com.myShop.services.userService;

@RequestMapping("/home")
@Controller
public class homePageController {

	@Autowired
	userService userService;

	@Autowired
	adService adService;

	@Autowired
	productService productService;

	@Autowired
	productRESTcontroller productRESTcontroller;

	@Autowired
	productController productController;

	@Autowired
	stockRESTcontroller stockRESTcontroller;

	@Autowired
	categoryService categoryService;
	
	@Autowired
	productRepo productRepo;

	@GetMapping("")
	public String home(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "null") String name,
			@RequestParam(defaultValue = "name") String sortBy,
			HttpSession session) {
		// Set True to showing ads

		setAdsPhotos(model, false);
		Page<product> listProducts;

		PageRequest pagerequest;
		// most expensive
		switch (sortBy) {
		case "most-expensive":
			pagerequest = PageRequest.of(page - 1, 20, Sort.by("cost").descending());
			break;
		case "cheapest":
			pagerequest = PageRequest.of(page - 1, 20, Sort.by("cost").ascending());
			break;
		case "newest":
			pagerequest = PageRequest.of(page - 1, 20, Sort.by("updateDate").descending());
			break;
		case "name":
			pagerequest = PageRequest.of(page - 1, 20, Sort.by("name").ascending());
			break;
		case "most-visited":
			pagerequest = PageRequest.of(page - 1, 20, Sort.by("category_name").ascending());
			break;
		default:
			pagerequest = PageRequest.of(page - 1, 20, Sort.by("updateDate").ascending());
			break;
		}

		model.addAttribute("sortBy", sortBy);
		if (name.equals("null")) {
			listProducts = productService.findByTotalStockGreaterThan(0, pagerequest);
		} else {
			
			listProducts = productRepo.findByNameLike("%"+name+"%", pagerequest);
			model.addAttribute("searchname", name);
		}

		model.addAttribute("products", listProducts);
		model.addAttribute("currentPage", page);
		model.addAttribute("categories", categoryService.getAllCategories());
		setAdsPhotos(model, true);
		return "index";
	}

	public void setAdsPhotos(Model model, boolean isThereAds) {
		// if isThereAds equals true then Ads Container in home page will show up
		
		if (isThereAds) {
			List<ad> ads = new ArrayList<ad>();
			ads = adService.getTop5();
			model.addAttribute("adslocal", ads);
			try {
				if (ads.size() > 0) {
					ad ad = ads.get(0);
					model.addAttribute("ad", ad);
					model.addAttribute("ad1", ad);
					if (ads.size() > 1) {
						ads.remove(0);
						model.addAttribute("ads", ads);
						model.addAttribute("ads1", ads);
					}
				}
				model.addAttribute("isThereAds", isThereAds);
			} catch (Exception e) {
				System.out.println("homePageController.setAdsPhotos()");
				System.out.println(e);
			}
		}

	}

	/* not used */
	public void setShortProfile(HttpSession session) throws InterruptedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getPrincipal().equals("anonymousUser")) {
			User user = (User) auth.getPrincipal();
			if (user.getUserImage() == null) {
				userImage ui = new userImage();
				ui.setImageTrack("anonymous-man.jpg");
				user.setUserImage(ui);
			}
			session.setAttribute("user", user);
			Thread.sleep(4000);
		}
	}

	/* not used */
	@RequestMapping("/setSession/setShortProfile")
	public ResponseEntity<Object> setShortProfile(HttpSession session, HttpServletRequest request)
			throws InterruptedException {
		setShortProfile(session);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ResponseBody
	public List<String> search(HttpServletRequest request) {
		return productService.search(request.getParameter("term"));
	}
}
