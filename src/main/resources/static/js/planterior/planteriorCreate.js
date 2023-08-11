/**
 * planterior create page
 */
document.addEventListener('DOMContentLoaded', () => {

	// 플랜테리어 작성 취소 버튼
	const createForm = document.querySelector('#createForm');
	const btnCancel = document.querySelector('#btnCancel');
	btnCancel.addEventListener('click', () => {
		const check = confirm('작성하신 내용은 저장되지 않습니다. 취소하시겠습니까?');
		if (check) {
			history.go(-1);
		}
	})

	// 플랜테리어 작성: 영문/한문
	let formFile = document.querySelector('#formFile').value;
	const btnCreate = document.querySelector('#btnCreate');
	const filterBtns = document.querySelectorAll('.filterBtn');
	let stateContent = '';
	let conditionContent = '';
	for (const filterBtn of filterBtns) {
		filterBtn.addEventListener('click', (e) => {

			console.log('ghkrdls');
			e.preventDefault();

			stateContent = filterBtn.value;
			console.log(stateContent);

			// li 삽입할 ul 요소를 찾음
			const secondFilter = document.querySelector('#secondFilter');

			// 추가
			let htmlStr = '';
			htmlStr += `
				<li>
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="초보자용"  />
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="선물하기 좋은"  />
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="공기정화"  />
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="빛이 적어도 되는"  />
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="향기나는"  />
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="반려동물 안전한"  />
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="목대있는"  />
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="흙이 필요없는"  />
					<input type="button" class="filterSecondBtn" id="filterSecondBtn" name="conditionContent" value="덩굴로 자라는"  />
					
				</li>
				`;
			secondFilter.innerHTML = htmlStr;

			const filterSecondBtns = document.querySelectorAll('input.filterSecondBtn');
			for (let btn of filterSecondBtns) {
				btn.addEventListener('click', () => {
					conditionContent += btn.value;
					console.log(conditionContent);
				})
			}

		})
	}




	btnCreate.addEventListener('click', () => {

		const plantName = document.querySelector('#plantName').value; // 한글만 가능하게 설정
		const plantNameEnglish = document.querySelector('#plantNameEnglish').value; // 영어만 가능하게 설정
		const userId = document.querySelector('#userId').value;
		console.log('ghkrd');
		console.log(stateContent);
		console.log(conditionContent);

		// 카테고리 삽입 가능성 존재
		// 이미지 성공시 !formFile 넣기
		if (plantName === '' || plantNameEnglish === '' || stateContent === '' || conditionContent === '') {
			alert('비어있는 부분을 선택해주세요')
			return;
		}
		const check = confirm('수정할 경우, 마이페이지에서만 수정 가능합니다.')
		if (check) {

			createForm.action = ''
			createForm.method = 'post'
			createForm.submit();
		}
	})

	// 플랜테리어 카테고리(보내기) -> second filter는 댓글 처럼 innerHtml 하기


	// 플랜테리어 이미지 
	function imageUpload(input) {
		const preview = input.parentElement.querySelector('.imagePreview');
		const reader = new FileReader();
		reader.onload = function(e) {
			preview.src = e.target.result;
		};
		if (input.files && input.files[0]) {
			reader.readAsDataURL(input.files[0]);
		} else {
			preview.src = "";
		}
	}



})