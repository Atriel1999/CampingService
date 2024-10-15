package com.camping.jpa.list.controller;

import java.time.LocalDate;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import com.camping.jpa.list.model.service.CampingListService;
import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingMember;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;
import com.camping.jpa.list.model.vo.CampingImage;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/camping")
public class CampingListController {
	@Autowired
	private CampingListService service;
	
	@GetMapping("/camping-edit")
	public String updateView(Model model,
			@SessionAttribute(name="loginMember", required = false) User loginMember,
			@RequestParam(value = "cid") int cid, @RequestParam(value = "siteid") int siteid) {
		CampingList camp = service.findByCid(cid);
		CampingSite site = service.findBySiteid(siteid);
		
		User organizer = camp.getCampingmember().get(1).getUserlist();
		
		int size = camp.getCampingmember().size();
		List<User> participant = new ArrayList<>();
		if(size > 2) {
			for(int i=0; i< size-2; i++) {
				participant.add(camp.getCampingmember().get(i+2).getUserlist());
			}
		}
		
		model.addAttribute("camp",camp);
		model.addAttribute("site",site);
		model.addAttribute("organizer", organizer);
		model.addAttribute("participant", participant);
		
		return "/camping/camping-edit";
		
//		model.addAttribute("msg", "캠핑정보가 등록 되었습니다.");
//		model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
//		return "common/msg";
	}
	
//	@PostMapping("/camping-edit")
//	public String uploadCamping(Model model, ) {
//		
//		return "/camping-single";
//	}
	
	@PostMapping("/camping-edit")
	public String upload(Model model, MultipartHttpServletRequest request, @RequestParam HashMap<String, Object> parameter,
						@ModelAttribute("campinglist") CampingList campinglist) {

		List<String> LinkList = new ArrayList<>();
		List<CampingImage> CampingImageList = new ArrayList<CampingImage>();
		
		
		int cid = 0;
		if(campinglist.getCid() == 0) {
			cid =  Integer.parseInt((String.valueOf(parameter.get("param_1"))));
		}
		else {
			cid = campinglist.getCid();
			service.InsertCampingList(campinglist);
		}
		
		LinkList = service.uploadFile(request, parameter);
		
		for (String Link : LinkList) {
			CampingImage Imagefile = new CampingImage();
			Imagefile.setCid(cid);
			Imagefile.setCiimage(Link);
			CampingImageList.add(Imagefile);
		}
		
		log.info("listdbg66 Campimagelist: " + CampingImageList);
		int result = service.InsertCampingImage(CampingImageList);

		
		int siteid= campinglist.getSiteid();
		model.addAttribute("msg", "캠핑정보가 등록 되었습니다.");
		model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
		return "/common/msg";

		

	}
	
	
		
	@GetMapping("/camping-list")
	public String campingListLoading(Model model) {


		List<CampingList> getlist = service.findByOrderByCstartDesc();
		
		List<CampingList> list = getlist.stream().distinct().collect(Collectors.toList()); //왠진모르꼤는데 2번조회되서 중복제거

		if (list == null) {
			return "redirect:error";
		}
		
        ListIterator<CampingList> iterator = list.listIterator();

        while(iterator.hasNext()){
        	CampingList camp = iterator.next();
        	List<CampingMember> tempList = camp.getCampingmember();
        	CampingSite site = service.findBySiteid(camp.getSiteid());
        	
        	String imageLink = site.getSiteimage();
        	if(imageLink.length() == 0) {
        		camp.setCpm("/img/temp.jpg");
        	} else {
        		camp.setCpm(site.getSiteimage());
        	}
        	
        	camp.setUsername(tempList.get(0).getUserlist().username);

        }

        System.out.println("test1: " + list);
		model.addAttribute("list",list);

		return "/camping/camping-list";
	}
	
	@GetMapping("/camping-single")
	public String campingSingleLoading(Model model, @RequestParam(value = "cid") int cid, @RequestParam(value = "siteid") int siteid, @SessionAttribute(name="loginMember", required = false) User loginMember) {
		CampingList camp = service.findByCid(cid);
		CampingSite site = service.findBySiteid(siteid);
		List<CampingImage> image = service.findImageByCid(cid); 
		
		
		if(cid == 0) {
			return "redirect:error";
		}
		
		User organizer = camp.getCampingmember().get(1).getUserlist();
		int size = camp.getCampingmember().size();
		List<User> participant = new ArrayList<>();
		if(size > 2) {
			for(int i=0; i< size-2; i++) {
				participant.add(camp.getCampingmember().get(i+2).getUserlist());
			}
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate date1 = camp.getCstart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate date2 = camp.getCend().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		long diff = ChronoUnit.DAYS.between(date1, date2);
		
		int dayOfWeek1 = date1.getDayOfWeek().getValue();
		int dayOfWeek2 = date2.getDayOfWeek().getValue();
		
		String day1 = "";
		String day2 = "";
		switch(dayOfWeek1) {
			case 1:
				day1 = "월요일";
				break;
			case 2:
				day1 = "화요일";
				break;
			case 3:
				day1 = "수요일";
				break;
			case 4:
				day1 = "목요일";
				break;
			case 5:
				day1 = "금요일";
				break;
			case 6:
				day1 = "토요일";
				break;
			case 7:
				day1 = "일요일";
				break;
			default:
				break;	
		}
		
		switch(dayOfWeek2) {
			case 1:
				day2 = "월요일";
				break;
			case 2:
				day2 = "화요일";
				break;
			case 3:
				day2 = "수요일";
				break;
			case 4:
				day2 = "목요일";
				break;
			case 5:
				day2 = "금요일";
				break;
			case 6:
				day2 = "토요일";
				break;
			case 7:
				day2 = "일요일";
				break;
			default:
				break;	
		}

		log.info("test2: " + camp + "\ntest3: " + day1 + ", " + day2 + "\ntest4: " + site);
		log.info("dbg345: " + loginMember);
		
		model.addAttribute("camp", camp);
		model.addAttribute("organizer", organizer);
		model.addAttribute("participant", participant);
		model.addAttribute("site", site);
		model.addAttribute("image", image);
		model.addAttribute("diff", diff);
		model.addAttribute("day1", day1);
		model.addAttribute("day2", day2);
		model.addAttribute("user", loginMember);
		return "camping/camping-single";
	}
}
