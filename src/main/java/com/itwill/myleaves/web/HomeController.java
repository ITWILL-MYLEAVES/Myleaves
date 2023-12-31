package com.itwill.myleaves.web;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.itwill.myleaves.repository.community.Community;
import com.itwill.myleaves.repository.planterior.Bookmark;
import com.itwill.myleaves.repository.planterior.Planterior;
import com.itwill.myleaves.repository.sellbuy.Sell;
import com.itwill.myleaves.repository.store.Store;
import com.itwill.myleaves.service.community.CommunityCommentService;
import com.itwill.myleaves.service.community.CommunityService;
import com.itwill.myleaves.service.home.HomeService;
import com.itwill.myleaves.service.palnterior.MypageService;
import com.itwill.myleaves.service.palnterior.PlanteriorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 정지언
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final CommunityCommentService communityCommentService;
	private final HomeService homeService;
	private final CommunityService communityService;
	private final PlanteriorService planteriorService;
	private final MypageService mypageService;
	

	/**
	 * 사용자 home page
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String home(Model model, @PageableDefault(page=0, size=14, sort="communityId", direction=Sort.Direction.DESC) Pageable pageable) {
		log.info("home");
		// 입양 목록 불러오기
		List<Sell> sellList = homeService.readSellList();
		model.addAttribute("sells", sellList);

		Map<Long, String> sellThumbnails = new HashMap<>();
		for (Sell sell : sellList) {
			sellThumbnails.put(sell.getSellId(), Base64.getEncoder().encodeToString(sell.getThumbnail()));
		}
		model.addAttribute("sellImages", sellThumbnails);

		// 스토어 목록 불러오기
		List<Store> storeList = homeService.readStoreList();
		model.addAttribute("stores", storeList);

		Map<Long, String> storeThumbnails = new HashMap<>();
		for (Store store : storeList) {
			storeThumbnails.put(store.getItemId(), Base64.getEncoder().encodeToString(store.getThumbnail()));
		}
		model.addAttribute("storeImages", storeThumbnails);

		// 커뮤니티 목록 불러오기
//		List<Community> communityList = homeService.readCommunityList();
		Page<Community> communityList = communityService.read(pageable);
		
		Map<Long, Long> commentCountMap = new HashMap<>();
		for (Community post : communityList) {
			Long commentCount = communityCommentService.countByCommunityId(post);
			commentCountMap.put(post.getCommunityId(), commentCount);
		}

		model.addAttribute("posts", communityList);
		model.addAttribute("commentCountMap", commentCountMap);

		// 플랜테리어 MD픽 목록 불러오기
		List<Planterior> planteriorList = planteriorService.read();
		List<Planterior> result = new ArrayList<>();
		List<Planterior> plist = mypageService.read();
		List<Bookmark> listMngr = mypageService.bookmarkRead("admin");

		Map<Long, String> thumbnails1 = new HashMap<>();
		for (Planterior p : planteriorList) {
			thumbnails1.put(p.getPlanteriorId(), Base64.getEncoder().encodeToString(p.getThumbnail()));
		}
		model.addAttribute("imagesMngr", thumbnails1);

		for (int i = 0; i < plist.size(); i++) {
			for (int j = 0; j < listMngr.size(); j++) {
				if (plist.get(i).getPlanteriorId() == listMngr.get(j).getPlanteriorId()) {
					result.add(plist.get(i));
				}
			}
		}
		model.addAttribute("mngrCount", result.size());
		model.addAttribute("mngrList", result);
		
		return "/main/home";
	}
}
