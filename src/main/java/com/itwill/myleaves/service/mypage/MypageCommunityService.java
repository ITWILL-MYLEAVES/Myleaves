package com.itwill.myleaves.service.mypage;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itwill.myleaves.repository.community.Community;
import com.itwill.myleaves.repository.community.CommunityRepository;
import com.itwill.myleaves.repository.community_comment.CommunityComment;
import com.itwill.myleaves.repository.community_comment.CommunityCommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MypageCommunityService {
	private final CommunityRepository communityRepository;
	private final CommunityCommentRepository communityCommentRepository;

	// 마이페이지 내가 쓴 게시글 목록  
	public Page<Community> read(String userId, Pageable pageable) {
		log.info("my_posts Service read()");
		Page<Community> list = communityRepository.findByUserId(userId, pageable);

		return list;
	}
	
	
	// 마이페이지 내가 쓴 댓글 검색 (댓글 페이지 / 1)
	public Page<CommunityComment> readComments(String userId, Pageable pageable) {
		log.info("my_comments Service read()");
		Page<CommunityComment> list = communityCommentRepository.findByUserId(userId, pageable);
		return list;
	}
	
	// 마이페이지 내가 쓴 댓글로 게시글 검색(댓글 페이지 / 2)
		public Community readByCommunityId(Long communityId){
			Community community = communityRepository.findByCommunityId(communityId);
			return community;
		}

	
//	// 마이페이지 게시글 검색 기능 	
//	public Page<Community> search(CommunitySearchDto dto, Pageable pageable) {
//		log.info("search(dto)={}", dto);
//		Page<Community> list = null;
//		switch (dto.getType()) {
//		case "t":
//			list = communityRepository.findByUserIdAndTitle(dto.getUserId(), dto.getKeyword(), pageable);
//			break;
//		case "c":
//			list = communityRepository.findByContentAndUserId(dto.getKeyword(), dto.getUserId(), pageable);
//			break;
//		}
//		
////		log.info("lise size = {}", list.size());
//		return list;
//	}
	
//	public List<CommunityComment> readByUserId(String userId, CommunitySearchDto dto) {
//		
//		List<CommunityComment> list = null;
//		switch(dto.getType()) {
//		case "t":
//			communityRepository.findByUserIdAndTitle(userId, dto.getKeyword());
//			break;
//		case "c":	
//			communityCommentRepository.findByContentAndUserId(userId, dto.getKeyword());
//		}
//		return list;
//	}
	
}
