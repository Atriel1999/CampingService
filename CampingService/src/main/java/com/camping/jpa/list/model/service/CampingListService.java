package com.camping.jpa.list.model.service;

import java.io.IOException;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.ArrayList;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.camping.jpa.kakao.model.repository.PaymentApproveRepository;
import com.camping.jpa.kakao.model.vo.PaymentApprove;
import com.camping.jpa.list.controller.CampingListController;
import com.camping.jpa.list.model.repository.CampingImageRepository;
import com.camping.jpa.list.model.repository.CampingListRepository;
import com.camping.jpa.list.model.repository.CampingMemberRepository;
import com.camping.jpa.list.model.repository.CampingSiteRepository;
import com.camping.jpa.list.model.repository.UserRepository;
import com.camping.jpa.list.model.vo.CampingImage;
import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingMember;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CampingListService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private CampingListRepository repo2;
	@Autowired
	private final CampingSiteRepository repo3;
	@Autowired
	private CampingImageRepository repo4;
	@Autowired
	private CampingMemberRepository repo5;
	@Autowired
	private PaymentApproveRepository repo6;
	
	public List<CampingList> findByTop5(){
		return repo2.findByTop5();
	}
	
	public List<PaymentApprove> findPaymentList(int cid) {
		return repo6.findPaymentList(cid);
	}
	
	public CampingMember insertMember(CampingMember member) {
		return repo5.save(member);
	}
	
	
	public CampingList insertCamping(CampingList camp) {
		return repo2.save(camp);
	}
	
	public int setStatusCamping(int cid, int status) {
		return repo2.setStatusCamping(cid, status);
	}
	
	public List<CampingList> findByOrderByCstartDesc(String searchValue, int ctype) {
		
		if(ctype == 0) {
			return repo2.findByOrderByCstartDesc(searchValue);
		}
			
		return repo2.findByOrderByCstartDesc(searchValue, ctype);
	}
	
	public CampingList findByCid(int cid) {
		return repo2.findByCid(cid);
	}
	
	public CampingList findByCname(String cname) {
		return repo2.findByCname(cname);
	}
	
	public CampingSite findBySiteid(int siteid) {
		return repo3.findBySiteid(siteid);
	}
	
	public void InsertCampingList(CampingList camp) {
		repo2.save(camp);
	}
	
	public List<CampingImage> findImageByCid(int cid){
		return repo4.findByCid(cid);
	}
	
	public int InsertCampingImage(List<CampingImage> campingImage){
		
		int result = 1;
		for(CampingImage Image : campingImage) {
			CampingImage saveImage = repo4.save(Image);
			if(saveImage.getCiimage().isEmpty()) {
				result = result * 0;
				log.info("listdbg5 fail! with :" + Image);
			}
		}
		log.info("listdbg4 campimage:" + campingImage);
		return result;
	}
	
	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucketName}")
	private String bucketName;
	
	public List<String> uploadFile(MultipartHttpServletRequest request, HashMap<String, Object> parameter){
		
		log.info("request " + request + ", parameter: " + parameter);
		// Getting uploaded files from the request object
        Map<String, MultipartFile> fileMap = request.getFileMap();

        //파일 정보 저장용도
        List<String> LinkList = new ArrayList<>();

        
        // Iterate through the map
        for (MultipartFile multipartFile : fileMap.values()) {
        	
        	log.info("miltipartFile" + multipartFile);
        	
        	String fileLink = upload(multipartFile);
                      
        	log.info("dbg22, filelink: " + fileLink);
        	LinkList.add(fileLink);
        }
              
		
		return LinkList;
	}
        
    /*fileMap에서 파일 정보 추출*/
    private HashMap<String, Object> getUploadedFileInfo(MultipartFile multipartFile){

        HashMap<String, Object> fileInfo = new HashMap<String, Object>();
        fileInfo.put("fileName",multipartFile.getOriginalFilename());
        fileInfo.put("fileSize",multipartFile.getSize());
        fileInfo.put("fileContentType",multipartFile.getContentType());

        return fileInfo;
    }
	
	
	public String upload(MultipartFile image) {
		  //입력받은 이미지 파일이 빈 파일인지 검증
		  if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
			  throw new ArithmeticException("log4j error: EMPTY FILE EXCEPTION");
		  }
		  //uploadImage를 호출하여 S3에 저장된 이미지의 public url을 반환한다.
		  return this.uploadImage(image);
	}
	
	private String uploadImage(MultipartFile image) {
		this.validateImageFileExtention(image.getOriginalFilename());
		log.info("originname: " + image.getOriginalFilename());
		try {
		  return this.uploadImageToS3(image);
		} catch (IOException e) {
			throw new ArithmeticException("log4j error: IO_EXCEPTION_ON_IMAGE_UPLOAD");
		}
	}
	
	private void validateImageFileExtention(String filename) {
		int lastDotIndex = filename.lastIndexOf(".");
		if (lastDotIndex == -1) {
			throw new ArithmeticException("log4j error: NO FILE EXIST");
		}

		String extention = filename.substring(lastDotIndex + 1).toLowerCase();
		List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

		if (!allowedExtentionList.contains(extention)) {
			throw new ArithmeticException("log4j error: INVAILD FILE EXTESION");
		}
	}
	
	private String uploadImageToS3(MultipartFile image) throws IOException {
	  String originalFilename = image.getOriginalFilename(); //원본 파일 명
	  String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

	  String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

	  InputStream is = image.getInputStream();
	  byte[] bytes = IOUtils.toByteArray(is); //image를 byte[]로 변환

	  ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
	  metadata.setContentType("image/" + extention);
	  metadata.setContentLength(bytes.length);
	  
	  //S3에 요청할 때 사용할 byteInputStream 생성
	  ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes); 

	  try{
	    //S3로 putObject 할 때 사용할 요청 객체
	    //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
	    PutObjectRequest putObjectRequest =
	        new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
	            .withCannedAcl(CannedAccessControlList.PublicRead);
	            
	    //실제로 S3에 이미지 데이터를 넣는 부분이다.
	    PutObjectResult result = amazonS3.putObject(putObjectRequest); // put image to S3
	    
	    log.info("insert result: " + result);
	  }catch (Exception e){
		  throw new ArithmeticException("log4j error: PUT_OBJECT_EXCEPTION");
	  }finally {
	    byteArrayInputStream.close();
	    is.close();
	  }
	  return amazonS3.getUrl(bucketName, s3FileName).toString();
	}
	
	
	
	
//	public List<User> findByMemberlist_Campinglist_Cid(int campinglistCid) {
//		return repo.findByMemberlist_Campinglist_Cid(campinglistCid);
//	}
}
