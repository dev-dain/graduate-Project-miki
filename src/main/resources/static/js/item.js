/////////////////////
// 이런 식의 객체가 필요함
/////////////////////
const item = {
  item_id: 1,
  item_img: 'http://mimg.lalavla.com/resources/images/prdimg/20210223/10005110/10005110_D_004_550.jpg',
  item_name: '포렌코즈 베어 섀도우 팔레트',
  item_price: '20,000원',
  item_discountPrice: '12,600원',
  item_isTestable: true,
  review_rate: 5.0,
  review_count: 24
};

/////////////////
// 또다른 예제 데이터
/////////////////
// const item = {
//   item_id: 4,
//   item_img: 'http://mimg.lalavla.com/resources/images/prdimg/20181123/10002153/10002153_D_001_550.jpg',
//   item_name: '아임미미 아임스틱섀도시머',
//   item_price: '9,900원',
//   item_discountPrice: null,
//   item_isTestable: false,
//   review_rate: 4.2,
//   review_count: 35
// }


const getImg = item => {
  const itemImg = document.createElement('img');
  itemImg.src = item.item_img;
  itemImg.alt = item.item_name;
  itemImg.classList += 'item-img';
  
  return itemImg;
};


const createPriceContainer = (origin, discount=0) => {
  const priceContainer = document.createElement('div');
  priceContainer.classList += 'price-container';

  const discountPrice = document.createElement('p');
  discountPrice.classList += 'discount-price';

  // 할인 상품이 아닌 경우
  if (!discount) {
    discountPrice.textContent = origin;    
  } else {
    // 할인 상품인 경우
    discountPrice.textContent = discount;

    const originPrice = document.createElement('p');
    originPrice.classList += 'origin-price';
    originPrice.textContent = origin;  

    priceContainer.appendChild(originPrice);
  }

  priceContainer.appendChild(discountPrice);
  return priceContainer;
}


const createRateContainer = (rate, count) => {
  const rateContainer = document.createElement('div');
  rateContainer.classList += 'rate-container';

  const rateSpan = document.createElement('span');
  rateSpan.textContent = `★ ${rate} (${count})`;
  rateSpan.classList += 'review-rate';

  rateContainer.appendChild(rateSpan);
  return rateContainer;
}


// const createBtnContainer = (id, isTestable) => {
//   const btnContainer = document.createElement('div');
//   btnContainer.classList += 'btn-container';

//   const putInCartBtn = document.createElement('button');
//   putInCartBtn.classList += 'cont-btn put-in-cart';
//   /////////////////////
//   // 장바구니에 어떻게 담을 것인지 생각해야 함
//   /////////////////////
//   putInCartBtn.addEventListener('click', () => console.log('장바구니에 담음'));
//   btnContainer.appendChild(putInCartBtn);

//   if (isTestable) {
//     const goTestBtn = document.createElement('button');
//     goTestBtn.classList += 'cont-btn go-test';
//     ///////////////////////
//     // 어떻게 테스트하게 할지 정해야 함
//     ///////////////////////
//     goTestBtn.addEventListener('click', () => console.log('test'));    
//     btnContainer.appendChild(goTestBtn);
//   }

//   const viewReviewBtn = document.createElement('button');
//   viewReviewBtn.classList += 'cont-btn view-review';
//   ////////////////////
//   // 어떻게 리뷰를 보게 할 것인지 결정해야 함
//   ////////////////////
//   viewReviewBtn.addEventListener('click', () => console.log('리뷰'));
//   btnContainer.appendChild(viewReviewBtn);

//   return btnContainer;
// }


const getItemInfoElList = item => {
  const itemTitle = document.createElement('h3');
  itemTitle.textContent = item.item_name;
  itemTitle.classList += 'item-title';

  const priceContainer = createPriceContainer(item.item_price, item.item_discountPrice);
  const rateContainer = createRateContainer(item.review_rate, item.review_count);
  const btnContainer = document.createElement('div');
  btnContainer.classList += 'btn-container';
  createBtnContainer(item.item_id, item.item_isTestable).forEach(el => 
    btnContainer.appendChild(el));

  return [itemTitle, priceContainer, rateContainer, btnContainer];
}


const docTitle = document.getElementsByTagName('title')[0];
docTitle.textContent = `상품 : ${item.item_name}`;

const goPrevBtn = document.querySelector('.go-prev');
const cart = document.querySelector('.go-cart');
const footer = document.querySelector('footer');


goPrevBtn.addEventListener('click', () => history.back());
cart.addEventListener('click', () => location.href = '/cart');
////////////////////////////
//item_id 아직 정해지지 않음
////////////////////////////
footer.addEventListener('click', () => location.href = `/item/${item.item_id}/detail`);


const imgContainer = document.querySelector('.img-container');
imgContainer.appendChild(getImg(item));

const infoContainer = document.querySelector('.info-container');
getItemInfoElList(item).forEach(el => infoContainer.appendChild(el));