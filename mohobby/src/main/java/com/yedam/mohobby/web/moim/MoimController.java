package com.yedam.mohobby.web.moim;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.communal.CommentsVO;
import com.yedam.mohobby.service.moim.MoimBoardVO;
import com.yedam.mohobby.service.moim.MoimCommentVO;
import com.yedam.mohobby.service.moim.MoimDutchPtpVO;
import com.yedam.mohobby.service.moim.MoimDutchVO;
import com.yedam.mohobby.service.moim.MoimMemberVO;
import com.yedam.mohobby.service.moim.MoimScheduleVO;
import com.yedam.mohobby.service.moim.MoimService;
import com.yedam.mohobby.service.moim.MoimVO;
import com.yedam.mohobby.service.moim.MoimVoteItemVO;
import com.yedam.mohobby.service.moim.MoimVoteListVO;
import com.yedam.mohobby.service.user.MemberVO;

/**
 * @create 2022/10/10
 * @author 이휘동, 최현정
 * @title 소모임
 */
@RestController
@CrossOrigin(origins = "*")
public class MoimController {

	@Autowired
	MoimService service;
	
	/**
	 * @title 소모임 단건조회
	 * @param moimVO
	 */
	@GetMapping("/moimOneInfo/{moimId}")
	public MoimVO getMoimOneInfo(@PathVariable int moimId) {
		return service.getMoimOneInfo(moimId);
	}
	/**
	 * @param moimVO
	 * @title 소모임 등록
	 */
	//소모임 등록
	@PostMapping("/moimInsert")
	public void insertMoim(@RequestBody MoimVO moimVO) {
		service.moimInsert(moimVO);
	}

	/**
	 * @return List<MoimVO>
	 * @title 소모임 인기 목록 조회
	 */
	@GetMapping("/moimPopularSelect")
	public List<MoimVO> getPopularList(){
		return service.moimPopularSelect();
	}
	
	/**
	 * @return List<MoimVO>
	 * @title 소모임 멤버 모집 조회(6개씩)
	 */
	@GetMapping("/moimRecruitMember")
	public List<MoimVO> getmoimrecruitMember() {
		return service.moimrecruitMember();
	}
	
	/**
	 * @return List<MoimVO>
	 * @title 소모임명 조회
	 */
	@GetMapping("/moimNameSelect")
	public List<MoimVO> getNameList(){
		return service.moimNameSelect();
	}
	
	/**
	 * @return List<MoimVO>
	 * @title 소모임 카테고리 조회
	 */
	@GetMapping("/moimCatgSelect")
	public List<MoimVO> getCatgList(){
		return service.moimCatgSelect();
	}
	
	/**
	 * @param moimName
	 * @param moimCatg
	 * @return List<MoimVO>
	 * @title 소모임 종합검색
	 */
	@GetMapping("/moimAllSearch")
	public List<MoimVO> getAllSearch(@RequestParam("Search")String moimName, @RequestParam("Category")String moimCatg ){
		return service.moimAllSearch(moimName, moimCatg);
	}
	
	/**
	 * @return List<MoimBoardVO>
	 * @title 소모임 게시판 전체 조회
	 */
	@GetMapping("/moimBoardList")
	public List<MoimBoardVO> getAllBoardList(@RequestParam("moimId")int moimId, @RequestParam("boardType")int boardType){
		return service.moimAllBoard(moimId, boardType);
	}
	
	/**
	 * @param vo
	 * @return result
	 * @title 소모임명 중복 체크
	 */
	@PostMapping("/idCheck")
	public String moim_idcheck(@RequestBody MoimVO vo) {
		String result = "";
		int count = service.memberIdCheck(vo.getMoimName());
		if(count == 0) {
			result="YES";
		} else {
			result="NO";
		}
		return result;
	}
	
	/**
	 * @param moimId
	 * @param boardType
	 * @return List<MoimBoardVO>
	 * @title 소모임 공지사항 전체조회
	 */
	@GetMapping("/noticeList")
	public List<MoimBoardVO> getNoticeBoardList(@RequestParam("moimId")int moimId, @RequestParam("boardType") int boardType){
		return service.moimNoticeBoard(moimId, boardType);
	}

	/**
	 * @param moimId
	 * @param boardType
	 * @param boardId
	 * @return List<MoimCommentVO>
	 * @title 소모임 게시글내 댓글 전체 조회
	 */
	@GetMapping("/detailComment")
	public List<MoimCommentVO> getCommentList(@RequestParam("moimId")int moimId, @RequestParam("boardType") int boardType, @RequestParam("boardId")int boardId){
		return service.moimCommentAllList(moimId, boardType, boardId);
	}
	
	
	/**
	 * @param moimId
	 * @param boardType
	 * @param boardId
	 * @return List<MoimBoardVO>
	 * @title 소모임 게시글 단건조회
	 */
	@GetMapping("/oneBoard")
	public List<MoimBoardVO> getOneBoard(@RequestParam("moimId")int moimId, @RequestParam("boardType") int boardType, @RequestParam("boardId")int boardId){
		return service.moimOneBoard(moimId, boardType, boardId);
	}
	
	/**
	 * @param commVO
	 * @title 소모임 게시글 댓글 등록
	 */
	@PostMapping("/insertMoimBoardComment")
	public void insertMoimBoardComment(@RequestBody CommentsVO commVO) {
		service.moimCommentInsert(commVO);
	}
	
	/**
	 * @param commVO
	 * @return
	 * @title 소모임 댓글 수정
	 */
	@PutMapping("/updateComment")
	public String updateMoimBoardComment(@RequestBody CommentsVO commVO) {
		try {
	        service.moimCommentUpdate(commVO);
	        System.out.println("댓글 수정 완료");
	        return "success";
	    } catch (Exception e) {
	        System.out.println("댓글 수정 실패 : " + e.getMessage());
	        return "fail";
	    }

	}

	/**
	 * @param memberId
	 * @return List<MoimVO>
	 * @title 참여중인 소모임 리스트
	 */
	@GetMapping("/joinMoim")
	public List<MoimVO> getJoinMoim(@RequestParam("memberId")String memberId) {
		return service.joinMoim(memberId);
	}
	
	/**
	 * @param memberId
	 * @return List<MoimVO>
	 * @title 운영중인 소모임 리스트
	 */
	@GetMapping("/operateMoim")
	public List<MoimVO> getOperateMoim(@RequestParam("memberId")String memberId) {
		return service.operateMoim(memberId);
	}
	
	/**
	 * @param commId
	 * @return
	 * @title 소모임 게시글 댓글 삭제
	 */
	@DeleteMapping("/boardDeleteComm")
	public String deleteMoimBaordComment(@RequestParam ("commId") int commId) {
		try {
			service.moimCommentDelete(commId);
			return "success";
		} catch (Exception e) {
			System.out.println("댓글 삭제 실패 : " + e.getMessage());
			return "fail";
		}
	}
	
	//소모임 메인 권한별 페이지
	public String rightMoimMainTop(@RequestParam("memberId") String memberId) {
		String result = "";
		int count = service.moimMainTop(memberId);
		if(count == 0) {
			result="YES";
		} else {
			result="NO";
		}
		return result;
	}
	
	
	//소모임 생성 후 유저 권한 업데이트
	@PutMapping("/updaterole")
	public int updateComment(@RequestBody MemberVO vo) {
	    try {
	       service.moimUserUpdate(vo);
	       return 1;
	       } catch (Exception e) {
	       return 0;
	       }
	      
	}
	
	//소모임 N빵 전체조회
	@GetMapping("/moimNbbangList")
	public List<MoimDutchVO> getAllNbbangList(@RequestParam ("moimId")int moimId){
		System.out.println(moimId);
		return service.getAllDuchList(moimId);
	}
	
	//소모임 전체 멤버 리스트 조회
	@GetMapping("/moimMemberList")
	public List<MoimMemberVO> getMoimMemberList(@RequestParam ("moimId")int moimId ){
		return service.getAllMemberList(moimId);
	}
	
	//소모임 멤버 검색 조회(단건 조회)
	@GetMapping("/oneMemberSearch")
	public List<MoimMemberVO> getMoimMemberSearch(@RequestParam ("memberId")String memberId, @RequestParam("moimId")int moimId ){
		return service.getSearchMember(memberId, moimId);
	}
	
	// 소모임 가입 회원 수 조회하는 로직
	@GetMapping("/moimMemberCount/{moimId}")
	public int moimMemberCount(@PathVariable int moimId) {
		return service.moimMemberCount(moimId);
	}
	
	//소모임 투표 게시글 리스트 조회
	@GetMapping("/voteList")
	public List<MoimVoteListVO> moimVoteList(@RequestParam ("moimId") int moimId){
		return service.moimVoteAllList(moimId);
	}
	
	// 소모임 투표 아이템 리스트 조회
	@GetMapping("/voteItemList")
	public List<MoimVoteListVO> moimVoteItemList(@RequestParam("moimId") int moimId) {
		System.out.println(moimId);
		return service.moimVoteItemlist(moimId);
	}

	// 소모임 투표 아이템 선택
	@GetMapping("/voteItemSelect")
	public List<MoimVoteListVO> moimVoteItemSelect(@RequestParam("moimId") int moimId,
			@RequestParam("memberId") String memberId) {
		System.out.println(moimId);
		return service.voteItemSelect(moimId, memberId);
	}

	// 소모임 투표 결과
	@GetMapping("/voteResult")
	public List<MoimVoteItemVO> votereulst(@Param("voteId") int voteId) {
		return service.votereulst(voteId);
	}

	// 소모임 투표
	@GetMapping("/vote")
	public List<MoimVoteListVO> vote(@RequestParam("voteId") int voteId, @RequestParam("memberId") String memberId,
			@RequestParam("itemSelect") int itemSelect, @RequestParam("moimId") int moimId) {
		return service.vote(voteId, memberId, itemSelect, moimId);
	}

	// 소모임 유저 선택 투표
	@PutMapping("/selectVote")
	public int selectVote(@RequestBody MoimVoteListVO vo) {
		try {
			System.out.println(vo);
			service.UserSelectvote(vo);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	// 소모임 투표 중복선택
	@PostMapping("/selectCheck")
	public String vote_check(@RequestBody MoimVoteListVO vo) {
		String result = "";
		System.out.println("memberId = "+ vo.getMemberId()+ "voteId = " + vo.getVoteId() + "ItemSelect =" + vo.getItemSelect() + "moimId = " + vo.getMoimId());
		int count = service.voteCheck(vo);
		if(count == 0) {
			result="YES";
		} else {
			result="NO";
		}
		return result;
	}
	
	//소모임 투표 선택 검사
	@GetMapping("/selectCheckItem")
	public List<MoimVoteListVO> getselectCheckItem(@Param("moimId")int moimId, @Param("memberId")String memberId){
		return service.selectCheck(moimId, memberId);
	}
	
	//n빵 등록
	@PostMapping("/makeNbbang")
	public String insertNbbang(@RequestBody MoimDutchVO dutchVO) {
		System.out.println("dutchVO:"+dutchVO);
		try {
			service.dutchInsert(dutchVO);
			return "success";
		} catch (Exception e) {
			return "fail"+e;
		}
	}
	
	//n빵 참여자 등록
	@PostMapping("/insertPtp")
	public String insertMemberNbbang(@RequestBody MoimDutchPtpVO dutptpVO) {
		System.out.println("dutptpVO"+dutptpVO);
		try {
			service.dutchMemberInsert(dutptpVO);
			return "success";
		} catch(Exception e) {
			return "fail"+e;
		}
	}
	
	//소모임 n빵 디테일 조회
	@GetMapping("/nbbangDetail/{moimId}/{dutchId}")
	public List<MoimDutchVO> getNbbangDetail(@PathVariable int moimId, @PathVariable int dutchId) {
		MoimDutchVO moimDutchVO = new MoimDutchVO();
		moimDutchVO.setMoimId(moimId);
		moimDutchVO.setDutchId(dutchId);
		return service.nbbangSelect(moimDutchVO);
	}
	
	//소모임 투표 항목
	@PostMapping("/voteList")
	public void insertVoteList(@RequestBody MoimVoteListVO vo) {
		System.out.println(vo);
		service.insertVoteList(vo);
	}
	
	//소모임 일정 등록
	@PostMapping("/scheduleInsert")
	public String insertSchedule(@RequestBody MoimScheduleVO scheduleVO) {
		try {
			service.scheduleInsert(scheduleVO);
			return "success";
		} catch (Exception e) {
			return "fail"+e;
		}
	}
	
	//소모임 일정 조회
	@GetMapping("/selectSchedule")
	public List<MoimScheduleVO> selectSchedule(@Param("moimId")int moimId){
		System.out.println(moimId);
		return service.scheduleSelect(moimId);
	}
	
//	//소모임 가입하기
//	@PostMapping("/")
//	public 
}

