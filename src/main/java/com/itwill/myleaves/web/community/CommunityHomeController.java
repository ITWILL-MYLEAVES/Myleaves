package com.itwill.myleaves.web.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.myleaves.dto.community.CommunityCreateDto;
import com.itwill.myleaves.dto.community.CommunityDeleteDto;
import com.itwill.myleaves.dto.community.CommunitySearchDto;
import com.itwill.myleaves.dto.community.CommunityUpdateDto;
import com.itwill.myleaves.repository.community.Community;
import com.itwill.myleaves.service.community.CommunityCommentService;
import com.itwill.myleaves.service.community.CommunityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/community") 
public class CommunityHomeController {
	private final CommunityService communityService;
	private final CommunityCommentService communityCommentService;
	
	@GetMapping
	public String post(Model model, @PageableDefault(page=0, size=10, sort="communityId", direction=Sort.Direction.DESC) Pageable pageable) { 
//		log.info("community home");
		
		// 포스트 목록 검색
	    Page<Community> list = communityService.read(pageable);
	    
	    // 각 게시글에 해당하는 댓글 수를 맵에 저장
	    Map<Long, Long> commentCountMap = new HashMap<>();
	    for (Community post : list) {
	        Long commentCount = communityCommentService.countByCommunityId(post);
	        commentCountMap.put(post.getCommunityId(), commentCount);
	    }
	    model.addAttribute("posts", list);
	    model.addAttribute("commentCountMap", commentCountMap);
	    
//	    log.info("commentCountMap: {}", commentCountMap);

	    
	    int nowPage = list.getPageable().getPageNumber() + 1; // 현재페이지
        int startPage =  Math.max(nowPage - 4, 1); // 시작 페이지
        int endPage = Math.min(nowPage +5, list.getTotalPages()); // 끝 페이지
       
        
	    // Model 검색 결과를 세팅.
	    model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
       
		
		return "community/home";
	}
	
	// 커뮤니티 게시글 작성하기 
	@PreAuthorize("hasRole('MEMBER')")
	@GetMapping("/create")
	public void create() {
//		log.info("create() Get");
		
	}
	
	@PreAuthorize("hasRole('MEMBER')")
	@PostMapping("/create")
	public String create(CommunityCreateDto dto) {
//		log.info("create(dto={}) POST", dto);
		
		// form에서 submit된 내용을 DB 테이블에 insert
		communityService.create(dto);
		
		return "redirect:/community";
	}
	
	/**
	 * 커뮤니티 게시글 상세보기 
	 * @param model @id 
	 * @return String 
	 */
	@PreAuthorize("hasRole('MEMBER')")
	@GetMapping("/detail") 
	public String read(long id, Model model) {
//		log.info("read(id={})", id);
		
		// COMMUNITY 테이블에서 id에 해당하는 게시글을 검색
		Community communityId = communityService.read(id);
		
		// 결과를 model에 저장 -> 뷰로 전달 
		model.addAttribute("post", communityId);
		
	    long count = communityCommentService.countByCommunityId(communityId);
	    model.addAttribute("communityCommentCount", count);
		
		return "/community/detail";
		
	}
	
	/**
	 * 커뮤니티 게시글 수정하기 
	 * @param model @id 
	 * @return String 
	 */
	@GetMapping("/modify") 
	public String modify(long communityId, Model model) {
//		log.info("read(communityId={})", communityId);
		
		// COMMUNITY 테이블에서 id에 해당하는 게시글을 검색
		Community post = communityService.read(communityId);
		
		// 결과를 model에 저장 -> 뷰로 전달 
		model.addAttribute("post", post);
		
		return "community/modify";
		
	}
	
	
	// 커뮤니티 게시글 삭제하기
	@PreAuthorize("hasRole('MEMBER')")
	@PostMapping("/delete")
	public String delete(Long communityId) {
//		log.info("delete(communityId={})", communityId);
		
	communityService.delete(communityId);
	
	// 해당 게시글에 작성한 댓글 삭제
//	List<CommunityComment> list = communityCommentService.read(communityId);
//    for(CommunityComment comment : list) {
//    	log.info("comment={}",comment);
//    	communityCommentService.delete(comment.getCommunityCommentId());
//    }
	
	communityCommentService.deleteComment(communityId);
	
	return "redirect:/community";
	}
	
	@DeleteMapping("/deleteposts")
	public ResponseEntity<Integer> deleteByCheckbox(
			@RequestBody List<CommunityDeleteDto> selectedCommunityIds) {
//		log.info("deleteByCheckbox(selectedCommunityIds={})", selectedCommunityIds);

		for (CommunityDeleteDto communityDeleteDto : selectedCommunityIds) {
			long communityId = communityDeleteDto.getCommunityId();
//			log.info("communityId={}", communityId);

			communityService.delete(communityId);
			communityCommentService.deleteComment(communityId);

		}
		return ResponseEntity.ok(1);
	}
	
	
	@PreAuthorize("hasRole('MEMBER')")
	@PostMapping("/update")
	public String update(CommunityUpdateDto dto) { // communityId, title, content 
//		log.info("update(dto={})", dto.getCommunityId());
		
		communityService.update(dto);
		
		return "redirect:/community/detail?id="+dto.getCommunityId();
	}
	
	@GetMapping("/search")
	public String search(CommunitySearchDto dto, Model model,  @PageableDefault(page=0, size=10, sort="communityId", direction=Sort.Direction.DESC) Pageable pageable) {
//		log.info("search(dto={})", dto);
		
		Page<Community> list = communityService.search(dto, pageable);
	    int nowPage = list.getPageable().getPageNumber() + 1; // 현재페이지
        int startPage =  Math.max(nowPage - 4, 1); // 시작 페이지
        int endPage = Math.min(nowPage +5, list.getTotalPages()); // 끝 페이지
        
        // 각 게시글에 해당하는 댓글 수를 맵에 저장
	    Map<Long, Long> commentCountMap = new HashMap<>();
	    for (Community post : list) {
	        Long commentCount = communityCommentService.countByCommunityId(post);
	        commentCountMap.put(post.getCommunityId(), commentCount);
	    }
	    model.addAttribute("commentCountMap", commentCountMap);
		model.addAttribute("posts", list);
		
	    model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
		
		return "community/home";
		
	}
	
	
	
	
	
	
}
