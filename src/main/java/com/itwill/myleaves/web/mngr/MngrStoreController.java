package com.itwill.myleaves.web.mngr;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.myleaves.dto.store.StoreCreateDto;
import com.itwill.myleaves.dto.store.StoreUpdateDto;
import com.itwill.myleaves.repository.store.Store;
import com.itwill.myleaves.service.store.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 수빈
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mngr/store")
public class MngrStoreController {
	
	private final StoreService storeService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public void mngrHome(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
//		log.info("mngrStoreList");
		
		Page<Store> list = storeService.read(pageable);
		int totalPage = list.getTotalPages()-1;
		int nowPage = list.getPageable().getPageNumber()+1; //지금 페이지 0 + 1 => 1 페이지부터 시작
		int startPage = Math.max(nowPage-4, 1);
		int endPage = Math.min(nowPage+5, list.getTotalPages());
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("stores", list);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/create")
	public void create() {
//		log.info("storeCreate:GET");
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public String create(StoreCreateDto dto) throws IOException {
//		log.info("create(dto={}):POST", dto);
		
		dto.setThumbnail(dto.getFile().getBytes());
		
		storeService.create(dto); // form에서 submit된 내용을 db에 insert
		
		return "redirect:/mngr/store/list"; // list로 redirect
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping({"/detail", "/modify"} )
	public void read(long itemId, Model model) {
//		log.info("read(itemId={})", itemId);
		
		Store store = storeService.read(itemId); // itemId로 store 테이블에서 검색
		String image = Base64.getEncoder().encodeToString(store.getThumbnail());
		
		model.addAttribute("image", image);
		model.addAttribute("store", store); // model에 저장
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/update")
	public String update(StoreUpdateDto dto) throws IOException {
		storeService.update(dto);
		return "redirect:/mngr/store/list";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/delete")
	public String delete(long itemId) {
//		log.info("delete(itemId={})", itemId);
		storeService.delete(itemId);
		return "redirect:/mngr/store/list";
	}
}
