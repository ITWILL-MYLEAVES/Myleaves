package com.itwill.myleaves.web.mngr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.myleaves.repository.planterior.Bookmark;
import com.itwill.myleaves.repository.planterior.Planterior;
import com.itwill.myleaves.service.palnterior.MypageService;
import com.itwill.myleaves.service.palnterior.PlanteriorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mngr/planterior")
public class MngrPlanteriorController {

	private final MypageService mypageService;
	private final PlanteriorService planteriorService;

	// 북마크 가져오기 -> 삭제는 js가 담당함.
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public String read(Model model) {
		//log.info("read");
		String user = "admin";

		// 북마크 가져오기
		List<Bookmark> list = mypageService.bookmarkRead(user);
		//log.info("sizeb={}", list.size());

		List<Planterior> result = new ArrayList<>();
		
		for(Bookmark b: list) {
			Planterior planterior = planteriorService.read(b.getPlanteriorId());
			//log.info("확인:{}", planterior.getPlanteriorId());
			result.add(planterior);
		}

		// 보낼 list

		//log.info("size={}", result.size());

		model.addAttribute("cardList", result);
		
		return "mngr/planterior/list";
	}

}
