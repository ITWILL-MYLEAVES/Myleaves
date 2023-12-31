package com.itwill.myleaves.repository.planterior;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 작업 entity class 이름 작성. sql 작성하기.
public interface PlanteriorRepository extends JpaRepository<Planterior, Long> {
	
	// userid, plantname, 영문, 썸네일
	// planterior id = sequence
	// content는 null로 들어갈 거임.
	// 일단 이미지 제외하고 시작.
	List<Planterior> findAllByOrderByPlanteriorIdDesc();
	
	Slice<Planterior> findAllByOrderByPlanteriorIdDesc(Pageable pagealbe);
	
	// mypage내가 쓴 글
	Page<Planterior> findAllByUserIdOrderByPlanteriorIdDesc(String userId, Pageable pagealbe);
	
	Planterior findAllByPlanteriorId(Long PlanteriorId);
	
	Planterior findByPlanteriorId(Long PlanteriorId);
	
	
	
	
}
