/**
 * 수빈
 * 장바구니 페이지
 * 수량 버튼, 삭제 버튼, 결제하기 버튼, 금액 계산
 */

document.addEventListener('DOMContentLoaded', () => {
	
	// 로그인 한 아이디
	const userId = document.querySelector('span#userId').innerText;
	
	
	// 장바구니 삭제 버튼
	const deleteItem = (e) => {
		const check = confirm('장바구니에서 삭제하겠습니까?');
		if(!check) {
			return;
		}
		
		const itemId = e.target.getAttribute('data-id');
		const reqUrl = `/api/cart/${userId}/${itemId}`;
		const data = {itemId, userId};
		axios
		.delete(reqUrl, data)
		.then((response) => {
			console.log(response);	
			location.reload(); // 화면 새로고침
		})
		.catch((error) => console.log(error));
	};
	const btnDeletes = document.querySelectorAll('button#cartDeleteBtn');
	for(let btn of btnDeletes) {
		btn.addEventListener('click', deleteItem);
	}
	
	
	// 상품 합계 함수
	function sumPrice() {
		// 상품 합계 span
		const totalItemPrice = document.querySelector('span#totalItemPrice');
		const d = document.querySelector('span#deliveryCharge'); // 배송비
		const deliveryCharge = Number(d.innerText);
		const totalPriceDeliveryCharge = document.querySelector('span#totalPriceDeliveryCharge'); // 찐찐
		sum = 0;
		const cntPrices = document.querySelectorAll('span[name="cntPrice"]');
		for(let x of cntPrices) {
			const y = Number(x.innerText);
			sum += y;
		}
		total = deliveryCharge + sum;
		totalItemPrice.innerHTML = sum;
		
		// 가격 수량 포맷 출력
		const showTotalItem = document.querySelector('#showTotalItemPrice');
		let showItem = (parseInt(totalItemPrice.innerHTML)).toLocaleString();
		showTotalItem.innerHTML = `${showItem}원`;
		
		totalPriceDeliveryCharge.innerHTML = total;
		
		// 가격 수량 포맷 출력
		const showTotalPrice = document.querySelector('#showTotalPriceDeliveryCharge');
		let showTotal = (parseInt(totalPriceDeliveryCharge.innerHTML)).toLocaleString();
		showTotalPrice.innerHTML = `${showTotal}원`;
	};
	sumPrice();
	
	
	// 장바구니 수량 -
	const cntMinus = (e) => {
		const itemId = e.target.getAttribute('data-btnMinus');
		const cntInputId = `cnt_${itemId}`;
		const cntInput = document.getElementById(cntInputId);
		const price = document.querySelector(`#price_${itemId}`).innerText; // 상품 가격
		const cntPrice = document.querySelector(`#cntPrice_${itemId}`); // 가격 * 수량
		
		if(cntInput.value === '1') {
			alert('최소 수량 입니다.');
			return;
		}
		
		cntInput.innerText = ''; // 상품 개수 input 비우고
		cntInput.innerHTML = cntInput.value--;
		const cnt = cntInput.value;
		
		cntPrice.innerHTML = price * cnt;
		console.log(cntPrice.innerText);
		
		sumPrice();
		
		const reqUrl = `/api/cart/update/${userId}/${itemId}`;
		const data = {userId, itemId, cnt};
		
		axios.put(reqUrl, data)
		.then((response) => {
			console.log(response);
		})
		.catch((error) => console.log(error));
		
		// 가격 수량 포맷 출력
		const showPriceCnt = document.querySelector(`#showPriceCnt_${itemId}`);
		let show = (parseInt(cntPrice.innerHTML)).toLocaleString();
		showPriceCnt.innerHTML = `${show}원`;
	};
	
	const minus = document.querySelectorAll('button#btnMinus');
	for(let btn of minus) {
		btn.addEventListener('click', cntMinus);
	}
	
	
	// 장바구니 수량 +
	const cntPlus = (e) => {
		const itemId = e.target.getAttribute('data-btnPlus');
		const cntInputId = `cnt_${itemId}`;
		const cntInput = document.getElementById(cntInputId);
		const price = document.querySelector(`#price_${itemId}`).innerText; // 상품 가격
		const cntPrice = document.querySelector(`#cntPrice_${itemId}`); // 가격 * 수량
		const inven = document.querySelector(`#inven_${itemId}`).innerText; // 상품 재고
		
		if(cntInput.value === inven) {
			alert('최대 수량 입니다.');
			return;
		}
		
		cntInput.innerText = ''; // 상품 개수 input 비우고
		cntInput.innerHTML = cntInput.value++;
		const cnt = cntInput.value;
		
		cntPrice.innerHTML = price * cnt;
		console.log(cntPrice.innerText);
		
		sumPrice();
		
		const reqUrl = `/api/cart/update/${userId}/${itemId}`;
		const data = {userId, itemId, cnt};
		
		axios.put(reqUrl, data)
		.then((response) => {
			console.log(response);
		})
		.catch((error) => console.log(error));
		
		// 가격 수량 포맷 출력
		const showPriceCnt = document.querySelector(`#showPriceCnt_${itemId}`);
		let show = (parseInt(cntPrice.innerHTML)).toLocaleString();
		showPriceCnt.innerHTML = `${show}원`;
	};
	
	const plus = document.querySelectorAll('button#btnPlus');
	for(let btn of plus) {
		btn.addEventListener('click', cntPlus);
	}
	
	
});
