/** 수빈
 * store detail 수량 변경 버튼
 */

document.addEventListener('DOMContentLoaded', () => {
	
	const form = document.querySelector('form#storeForm');
	const btnMinus = document.querySelector('button#btnMinus');
	const btnPlus = document.querySelector('button#btnPlus');
	const itemPrice = document.querySelector('#itemPrice').innerText;
	const totalPrice = document.querySelector('#totalPrice');
	const inven = document.querySelector('input#inven').value;
	const addWishBtn = document.querySelector('#addWishBtn');
	const deleteWishBtn = document.querySelector('#deleteWishBtn');
	const addCartBtn = document.querySelector('button#addCartBtn');
	const userId = document.querySelector('span#userId').innerText; // 현재 로그인 아이디
	const itemId = document.querySelector('span#itemId').innerText; // 상품 아이디

//	let itemPriceFormat = itemPrice.toLocaleString();
//	console.log(itemPrice.toLocaleString());
//	totalPrice.innerHTML = `${itemPriceFormat}원`; // 맨 처음 총 상품금액(price * 1)
	
	const cntPlus = () => {
		const cnt = document.querySelector('input#cnt');
		if(cnt.value === inven) { // 선택 수량이 남은 재고와 같을 때 +를 누르면
			alert('최대 수량 입니다.');
			return;
		}
		
		cnt.innerText = ''; // 상품 개수 input 비우고
		cnt.value++; // 더한 상품 개수 값 채워주기
		let multiply = (itemPrice * cnt.value).toLocaleString();
		totalPrice.innerHTML = `${multiply}원`;
	};
	btnPlus.addEventListener('click', cntPlus);
	
	const cntMinus = () => {
		const cnt = document.querySelector('input#cnt');
		if(cnt.value === '1') { // 수량 1일 때 -버튼을 누르면
			alert('최소 수량 입니다.');
			return;
		}
		
		cnt.innerText = ''; // 상품개수 input 비우고
		cnt.value--; // 더한 상품 개수 값 채워주기
		let multiply = (itemPrice * cnt.value).toLocaleString();
		totalPrice.innerHTML = `${multiply}원`;
	};
	btnMinus.addEventListener('click', cntMinus);
	
	const addCart = () => {
		
		const cnt = document.querySelector('input#cnt').value;
		console.log(userId, itemId, cnt);
		const check = confirm('장바구니에 추가하시겠습니까?');
		if(check) {
			const data = {userId, itemId, cnt};
			const reqUrl = '/api/cart';
			axios.post(reqUrl, data)
			.then((response) => {
				console.log(response);
				alert('장바구니에 추가되었습니다.');
			})
			.catch((error) => {
				console.log(error);
			});
		}
		// TODO: 카트 테이블에 있는지 검사해야됨
	};
	addCartBtn.addEventListener('click', addCart);
	
	// 관심상품 추가
	const addWish = () => {
		console.log(userId, itemId);
			const data = {userId, itemId};
			const reqUrl = '/api/storeWish';
			axios.post(reqUrl, data)
			.then((response) => {
				console.log(response);
				alert('관심상품에 추가되었습니다.');
				location.reload(); // 화면 새로고침
			})
			.catch((error) => {
				console.log(error);
			});
	};
	if(addWishBtn != null) {
		addWishBtn.addEventListener('click', addWish);
	}
	
	// 관심상품 삭제
	const deleteWish = (e) => {
		const data = {itemId, userId};
		const reqUrl = `/api/storeWish/${userId}/${itemId}`;
		axios
		.delete(reqUrl, data)
		.then((response) => {
			console.log(response);
			location.reload();
		})
		.catch((error) => {
			console.log(error);
		});
	
	};
	if(deleteWishBtn != null) {
		deleteWishBtn.addEventListener('click', deleteWish);
	}
	
});