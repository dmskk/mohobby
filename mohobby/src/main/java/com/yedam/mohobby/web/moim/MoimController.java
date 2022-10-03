
package com.yedam.mohobby.web.moim;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.moim.MoimService;
import com.yedam.mohobby.service.moim.MoimVO;

@RestController
public class MoimController {
	@Autowired
	MoimService service;
	

	@PostMapping("/moimInsert")
	public MoimVO insertMoim(MoimVO moimVO) {
		service.moimInsert(moimVO);
		return moimVO;
	}

	//전체조회
	@RequestMapping("/allSelect")
	public List<MoimVO> getAllList(){
		return service.moimAllSelect();
	}
	
	//인기 목록 조회
	@RequestMapping("/popularSelect")
	public List<MoimVO> getPopularList(){
		return service.moimPopularSelect();
	}
	
	//소모임명 조회
	public List<MoimVO> getNameList(){
		return service.moimNameSelect();

	}
	
	//카테고리 조회
	@GetMapping("/catgSelect")
	public List<MoimVO> getCatgList(){
		return service.moimCatgSelect();
	}
}

