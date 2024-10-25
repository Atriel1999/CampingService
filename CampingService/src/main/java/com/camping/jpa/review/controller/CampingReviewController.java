package com.camping.jpa.review.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.camping.jpa.list.model.service.CampingListService;
import com.camping.jpa.list.model.vo.CampingImage;
import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;
import com.camping.jpa.member.model.service.MemberService;
import com.camping.jpa.review.model.service.CampingReviewService;
import com.camping.jpa.review.model.vo.CampingReview;
import com.camping.jpa.review.model.vo.CampingReviewImage;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/review")
public class CampingReviewController {
	
	@Autowired
	private CampingReviewService service;
	
	@Autowired
	private MemberService serviceMember;
	
	@Autowired
	private CampingListService serviceCamping;
	
	@GetMapping("/camping-review")
	public String updateCampingReview(Model model, @RequestParam("crid") int crid) {
		
		CampingReview review = service.findByCrid(crid);
		List<CampingReviewImage> image = service.findImageByCrid(crid);
		
		log.info("dbg1 image= " + image + ", " + crid);
		
		User writter = serviceMember.findByUserno(review.getCruserno());
		
		model.addAttribute("review", review);
		model.addAttribute("writter", writter);
		model.addAttribute("image", image);
		
		return "/review/camping-review";
	}
	
	@GetMapping("/camping-reviewedit")
	public String uploadCampingReview(Model model, @RequestParam("cid") int cid, @RequestParam("siteid") int siteid) {
		
		CampingList camp = serviceCamping.findByCid(cid);
		CampingSite site = serviceCamping.findBySiteid(siteid);
		
		log.info("camp: " + camp);
		log.info("site: " + site);
		
		model.addAttribute("camp", camp);
		model.addAttribute("site", site);
		
		return "/review/camping-reviewedit";
	}
	
	@PostMapping("/camping-reviewedit")
	public String uploadCampingImage(Model model, MultipartHttpServletRequest request, @RequestParam HashMap<String, Object> parameter,
						@SessionAttribute(name="loginMember", required = false) User loginMember) {
		
		
		CampingList tempCamp = new CampingList();
		CampingReview campingreview = new CampingReview();
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dateFormat.format(date);
		
		log.info("Dbg1112222: " + parameter);
		
		int cid = Integer.parseInt((String.valueOf(parameter.get("cid"))));
		int siteid = Integer.parseInt((String.valueOf(parameter.get("siteid"))));
		String crname = String.valueOf(parameter.get("crname"));
		String crcontent = String.valueOf(parameter.get("crcontent"));
		Double crrating = Double.parseDouble((String.valueOf(parameter.get("siteid"))));

		
		log.info("Dbg1111111: " + cid + ", " + siteid);
		
		tempCamp.setCid(cid);
		campingreview.setCrname(crname);
		campingreview.setCrcontent(crcontent);
		campingreview.setCrrating(crrating);
		campingreview.setCampinglist(tempCamp);
		campingreview.setCrcreatedate(date);
		campingreview.setCruserno(loginMember.getUserno());
		
		List<String> LinkList = new ArrayList<>();
		List<CampingReviewImage> ReviewImageList = new ArrayList<CampingReviewImage>();
		
		
		
		log.info("dbg1" + request + "\n dbg2: " + parameter);
		LinkList = serviceCamping.uploadFile(request, parameter);
		log.info("dbg3" + LinkList);
		
		if(LinkList.size() != 0) {
			campingreview = service.saveReview(campingreview);
		}
		
		for (String Link : LinkList) {
			CampingReviewImage Imagefile = new CampingReviewImage();
			Imagefile.setCrid(campingreview.getCrid());
			Imagefile.setCriimage(Link);
			ReviewImageList.add(Imagefile);
		}
		
		log.info("insert image is " + ReviewImageList);
		
		int result = service.InsertReviewImage(ReviewImageList);
		
		log.info("insert result = " + result);
		
		if(result == 1) {
			model.addAttribute("msg", "캠핑리뷰가 등록 되었습니다.");
		} else {
			model.addAttribute("msg", "캠핑리뷰등록을 실패하였습니다.");
		}
		
		model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
		
		return "/common/msg";
	}
}
