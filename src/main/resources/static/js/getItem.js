////////////
// 일단 어떻게 될지 모르니까 그냥 객체 있다치고 만들어서 테스트해보겠음
////////////
// fetch로 서버에 요청 보냈을 때 다음과 같이 응답이 오면 됨
// 리스트 안에 각 아이템 정보를 담은 객체들이 요소로 들어가있음
const itemList = [
  {
    item_id: 1,
    item_img: 'http://mimg.lalavla.com/resources/images/prdimg/20210223/10005110/10005110_D_004_550.jpg',
    item_name: '포렌코즈 베어 섀도우 팔레트',
    item_price: '20,000원',
    item_discountPrice: '12,600원',
    item_isTestable: true
  },
  {
    item_id: 2,
    item_img: 'http://mimg.lalavla.com/resources/images/prdimg/20191107/10002619/10002619_D_002_550.jpg',
    item_name: '클레오 프로아이팔레트',
    item_price: '32,000원',
    item_discountPrice: '22,400원',
    item_isTestable: true
  },
  {
    item_id: 3,
    item_img: 'http://mimg.lalavla.com/resources/images/prdimg/20200713/10005437/10005437_D_001_550.jpg',
    item_name: '트윙클팝 젤리 글리터 싱글',
    item_price: '4,900원',
    item_discountPrice: '3,900원',
    item_isTestable: false
  },
  {
    item_id: 4,
    item_img: 'http://mimg.lalavla.com/resources/images/prdimg/20181123/10002153/10002153_D_001_550.jpg',
    item_name: '아임미미 아임스틱섀도시머',
    item_price: '9,900원',
    item_discountPrice: null,
    item_isTestable: true
  }
];


const itemRowContainer = document.querySelector('.item-row-container');

const rowCount = 2; // 한 페이지에 들어갈 가로줄 개수
const columnCount = 2;  // 한 row에 들어갈 카드 개수



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



// item의 프로퍼티는 item_ prefix 기본
// id, img, name, price, discountPrice, isTestable
const createItemCard = item => {
  const itemCard = document.createElement('div');
  itemCard.classList += 'item-card';

  const itemImg = document.createElement('img');
  itemImg.src = item.item_img;
  itemImg.classList += 'thumbnail-img';
  itemImg.alt = item.item_name;

  const hr = document.createElement('hr');
  hr.classList += 'divide-img-info';

  const itemTitle = document.createElement('p');
  itemTitle.classList += 'item-title';
  itemTitle.textContent = item.item_name;
  
  const priceContainer = createPriceContainer(item.item_price, item.item_discountPrice);

  ///////////////////////
  // 테스트 가능/불가능한지도 넘겨줘야 함 (bigCategory/{category_id} 등에서)
  // 테스트 버튼을 띄울지 말지 알아야 하기 때문에
  ///////////////////////

  itemCard.appendChild(itemImg);
  itemCard.appendChild(hr);
  itemCard.appendChild(itemTitle);
  itemCard.appendChild(priceContainer);

  if (item.item_isTestable) {
    const goTestBtn = document.createElement('button');
    goTestBtn.classList += 'go-test';
    goTestBtn.textContent = '테스트';  
    itemCard.appendChild(goTestBtn);
  }

  itemCard.addEventListener('click', () => 
    location.href = `../item/${item.item_id}`
  );

  return itemCard;
}

const createItemRow = () => {
  const itemRow = document.createElement('div');
  itemRow.classList += 'item-row';
  return itemRow;
}



/*
const itemList = [];
const getItemsInfo = () => {
  ////////////////// 정확한 주소가 필요함
  fetch(`주소`)
    .then(res => res.json())
    .then(res => itemList = res);
};
*/

const itemRowList = [];
for (let i = 0; i < rowCount; i++) {
  itemRowList.push(createItemRow());
}

for (let i = 0; i < itemRowList.length; i++) {
  for (let j = 0; j < columnCount; j++) {
    ///////////////
    // 이 때 들어갈 item은 객체이며, API 응답으로 받아오는 정보를 이용함
    //////////////
    const itemCard = createItemCard(itemList[(i * 2) + j]);
    itemRowList[i].appendChild(itemCard);
  }
}

itemRowList.forEach(row => itemRowContainer.appendChild(row));