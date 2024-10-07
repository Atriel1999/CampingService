package com.camping.jpa.list.controller;

import java.time.LocalDate;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

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
	}
	
//	@PostMapping("/camping-edit")
//	public String uploadCamping(Model model, ) {
//		
//		return "/camping-single";
//	}
	
	@PostMapping("/camping-edit")
	public String upload(@RequestParam(name = "file", required = false) List<MultipartFile> file, 
						@ModelAttribute("campinglist") CampingList campinglist) {
		log.info("uploaded file " + file);
		log.info("dbg1 camping data: " + campinglist);
		
		List<CampingImage> CampingImageList = new ArrayList<CampingImage>();
		
		// 파일 저장 로직
		for(MultipartFile upfile : file) {
			if(upfile.getSize() == 0) {
				continue;
			}
			String renamedFileName = service.saveFile(upfile); // 실제 파일 저장되는 로직
			log.info("dbg2: renamefile: " + renamedFileName);
			if(renamedFileName != null) {
				CampingImage Imagefile = new CampingImage();
				Imagefile.setCiimage(renamedFileName);
				CampingImageList.add(Imagefile);
			}
		}
		
		
		
		return "/camping/camping-list";
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
        	//List<User> tempList2 = ;
        	camp.setUsername(tempList.get(0).getUserlist().username);

        }

        System.out.println("test1: " + list);
		model.addAttribute("list",list);

		
//		List<User> writter = service.findByMemberlist_Campinglist_Cid(1);
//		if (writter == null) {
//			return "redirect:error";
//		}
//		log.info("listdbg2 :" + writter);
//		model.addAttribute("writter", writter);

		return "/camping/camping-list";
	}
	
	@GetMapping("/camping-single")
	public String campingSingleLoading(Model model, @RequestParam(value = "cid") int cid, @RequestParam(value = "siteid") int siteid) {
		CampingList camp = service.findByCid(cid);
		CampingSite site = service.findBySiteid(siteid);
		
		
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
		
		model.addAttribute("camp", camp);
		model.addAttribute("organizer", organizer);
		model.addAttribute("participant", participant);
		model.addAttribute("site", site);
		model.addAttribute("diff", diff);
		model.addAttribute("day1", day1);
		model.addAttribute("day2", day2);
		return "camping/camping-single";
	}
}
