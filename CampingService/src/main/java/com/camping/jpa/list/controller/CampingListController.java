package com.camping.jpa.list.controller;

import java.time.LocalDate;


import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.camping.jpa.kakao.model.vo.PaymentApprove;
import com.camping.jpa.list.model.service.CampingListService;
import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingMember;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;
import com.camping.jpa.review.model.service.CampingReviewService;
import com.camping.jpa.review.model.vo.CampingReview;
import com.camping.jpa.list.model.vo.CampingImage;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@Transactional
@RequestMapping("/camping")
public class CampingListController {
	@Autowired
	private CampingListService service;
	
	@Autowired
	private CampingReviewService serviceReview;
	
	@GetMapping("/camping-start")
	public String updateCampingStartView(Model model) {
		
		return "/camping/camping-start";
	}
	
	@PostMapping("/camping-start")
	public String uploadCampingStart(Model model, @RequestParam(value = "cname") String cname, @RequestParam(value = "siteid") int siteid
					,@RequestParam(value = "cstart") String cstart, @RequestParam(value = "cend") String cend
					, @RequestParam(value = "ccomment") String ccomment, @RequestParam(value = "ctype") int ctype,
					@SessionAttribute(name="loginMember", required = false) User loginMember
			) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=null;
		Date date2=null;
		try {
			date1 = formatter.parse(cstart);
			date2 = formatter.parse(cend);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		CampingList campinglist = new CampingList();
		CampingMember campingmember = new CampingMember();
		
		campinglist.setCname(cname);
		campinglist.setSiteid(siteid);
		campinglist.setCstart(date1);
		campinglist.setCend(date2);
		campinglist.setCcomment(ccomment);
		campinglist.setCtype(ctype);
		
		CampingList temp = service.findByCname(cname);
		
		if(loginMember == null) {
			model.addAttribute("msg", "로그인을 해주세요");
			model.addAttribute("location", "/camping/camping-start");
			return "/common/msg";
		}
		
		if(temp!=null) {
			model.addAttribute("msg", "같은 이름의 캠핑이 이미 존재합니다");
			model.addAttribute("location", "/camping/camping-start");
			return "/common/msg";
		}
		
		campinglist = service.insertCamping(campinglist);
		
		temp = service.findByCname(cname);
		String cid = Integer.toString(temp.getCid());
		
		CampingList tempCamp = new CampingList();
		
		tempCamp.setCid(temp.getCid());
		campingmember.setCampinglist(tempCamp);
		campingmember.setUserlist(loginMember);
		campingmember.setCaauth(2);
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.format(date);
		campingmember.setJoindate(date);
		
		service.insertMember(campingmember);
		
		log.info("dbg1111 camping: " + campinglist);
		
		model.addAttribute("msg", "캠핑정보가 등록 되었습니다.");
		model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
		return "/common/msg";
	}
	
	
//	@PostMapping("/camping-start")
//	public String uploadCampingStart(Model model, @ModelAttribute("campinglist") CampingList campinglist) {
//		
//		log.info("dbg1111 camping: " + campinglist);
//		return "";
//	}
	
	
	@GetMapping("/camping-edit")
	public String updateView(Model model,
			@SessionAttribute(name="loginMember", required = false) User loginMember,
			@RequestParam(value = "cid") int cid, @RequestParam(value = "siteid") int siteid) {
		CampingList camp = service.findByCid(cid);
		CampingSite site = service.findBySiteid(siteid);
		
		User organizer = camp.getCampingmember().get(0).getUserlist();
		
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
		
		int result = service.InsertCampingImage(CampingImageList);

		
		int siteid= campinglist.getSiteid();
		model.addAttribute("msg", "캠핑정보가 등록 되었습니다.");
		model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
		return "/common/msg";
	}
	
	@PostMapping("/endCamping")
	public String endCamping(Model model, @SessionAttribute(name="loginMember") User loginMember, 
			@RequestParam(value = "cid") int cid, @RequestParam(value = "siteid") int siteid) {
		
		log.info("성공: " + loginMember + ", " + cid + ", "+ siteid);
		int result = service.setStatusCamping(cid, 1);
		
		if(result == 1) {
			model.addAttribute("msg", "캠핑을 종료했습니다");
			model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
			return "/common/msg";
		} else {
			model.addAttribute("msg", "캠핑종료 실패");
			model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
			return "/common/msg";
		}
	}
	
	@PostMapping("/restartCamping")
	public String restartCamping(Model model, @SessionAttribute(name="loginMember") User loginMember, 
			@RequestParam(value = "cid") int cid, @RequestParam(value = "siteid") int siteid) {
		
		log.info("성공: " + loginMember + ", " + cid + ", "+ siteid);
		int result = service.setStatusCamping(cid, 0);
		
		if(result == 1) {
			model.addAttribute("msg", "캠핑을 재시작했습니다");
			model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
			return "/common/msg";
		} else {
			model.addAttribute("msg", "캠핑재시작 실패");
			model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
			return "/common/msg";
		}
	}
	
	@PostMapping("/registerCamping")
	public String registerCamping(Model model, @SessionAttribute(name="loginMember") User loginMember, 
			@RequestParam(value = "cid") int cid, @RequestParam(value = "userno") String userno, @RequestParam(value = "siteid") int siteid) {
		
		CampingMember member = new CampingMember();
		
		CampingList tempCamp = new CampingList();
		User tempUser = new User();
		
		tempCamp.setCid(cid);
		tempUser.setUserno(userno);
		
		member.setCampinglist(tempCamp);
		member.setUserlist(tempUser);
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.format(date);
		member.setJoindate(date);
		member.setCaauth(2);
		
		CampingMember result = service.insertMember(member);
		
		if(result != null) {
			model.addAttribute("msg", "캠핑에 가입했습니다");
			model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
		} else {
			model.addAttribute("msg", "캠핑에 가입에 실패했습니다");
			model.addAttribute("location", "/camping/camping-single?cid=" + cid + "&siteid=" + siteid);
		}
		
		return "/common/msg";
	}
	

		
	@GetMapping("/camping-list")
	public String campingListLoading(Model model, @RequestParam(value = "searchValue", defaultValue = "") String searchValue, 
			@RequestParam(value = "sortBy", defaultValue ="0", required = false) int sortBy) {
		
		log.info("dbg11223 searchValue=" + searchValue + ", sort=" + sortBy);


		List<CampingList> getlist = service.findByOrderByCstartDesc(searchValue, sortBy);
		
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
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("sortBy", sortBy);

		return "/camping/camping-list";
	}
	
	@ResponseBody
	@PostMapping(value ="/search.action")
	public Object searchCampingName(@RequestParam("searchValue") String searchValue) {

		List<CampingSite> siteList = service.findBySiteidTop5(searchValue);
		
		return siteList;
		
	}
	
	@GetMapping("/camping-single")
	public String campingSingleLoading(Model model, @RequestParam(value = "cid") int cid, 
			@RequestParam(value = "siteid") int siteid, @SessionAttribute(name="loginMember", required = false) User loginMember) {
		CampingList camp = service.findByCid(cid);
		CampingSite site = service.findBySiteid(siteid);
		List<CampingReview> review = serviceReview.findByCid(cid);
		
		List<CampingImage> image = service.findImageByCid(cid); 
		List<PaymentApprove> payment = service.findPaymentList(cid);
		
		log.info("paydata: " + payment);
		
		
		if(cid == 0) {
			return "redirect:error";
		}
		
		User organizer = new User();;
		List<User> participant = new ArrayList<>();
		
		log.info("dbg111111: " + camp.getCampingmember());
		
		if(!camp.getCampingmember().isEmpty()) {
			organizer = camp.getCampingmember().get(0).getUserlist();
			int size = camp.getCampingmember().size();
			
			log.info("dbg111111 size: " + size);
			int auth = 0;
			
			if(loginMember != null) {
				log.info("work");
				for(int i = 0; i < size; i++) {
					
					log.info("dbg test111: " + camp.getCampingmember().get(i).getUserlist().getUserno() + " = " + loginMember.getUserno());
					if(camp.getCampingmember().get(i).getUserlist().getUserno().equals(loginMember.getUserno())) {
						log.info("auth success");
						auth = 1;
						model.addAttribute("auth", auth);
					} else {
						model.addAttribute("auth", auth);
					}
				}
			} else {
				model.addAttribute("auth", auth);
			}
			
			if(size > 1) {
				for(int i=0; i< size; i++) {
					if(!organizer.getUserno().equals(camp.getCampingmember().get(i).getUserlist().getUserno())) {
						participant.add(camp.getCampingmember().get(i).getUserlist());
					}
				}
			}
		} else {
			organizer.setUsername("테스트");
			participant = null;
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
		
		if(!payment.isEmpty()) {
			model.addAttribute("payment",payment.get(0));
		} else {
			model.addAttribute("payment",null);
		}

//		log.info("test2: " + camp + "\ntest3: " + day1 + ", " + day2 + "\ntest4: " + site);
//		log.info("dbg345: " + loginMember);
		log.info("dbg1 review:" + review + ", cid=" + cid);
		
		int reviewWritten = 0;
		if(loginMember != null) {
			for(CampingReview reviewdata : review) {
				if(reviewdata.getCruserno().equals(loginMember.getUserno())) {
					reviewWritten = 1;
				}
			}
		}
		
		model.addAttribute("camp", camp);
		model.addAttribute("organizer", organizer);
		model.addAttribute("participant", participant);
		model.addAttribute("site", site);
		model.addAttribute("image", image);
		model.addAttribute("diff", diff);
		model.addAttribute("day1", day1);
		model.addAttribute("day2", day2);
		model.addAttribute("user", loginMember);
		model.addAttribute("review", review);
		model.addAttribute("reviewWritten", reviewWritten);

		return "camping/camping-single";
	}
}
