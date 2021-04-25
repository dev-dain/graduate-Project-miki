/////////////////////
// 이런 식의 객체가 필요함
/////////////////////
const item = {
  item_id: 1,
  item_imgList: [
    'https://mimg.lalavla.com/resources/images/prdimg/202102/23/10005110_20210223121750.jpg',
    'https://mimg.lalavla.com/resources/images/prdimg/202102/23/10005110_20210223121808.jpg',
    'https://mimg.lalavla.com/resources/images/prdimg/202102/23/10005110_20210223121833.jpg',
    'https://mimg.lalavla.com/resources/images/prdimg/202102/23/10005110_20210223121903.jpg',
    'https://mimg.lalavla.com/resources/images/prdimg/202102/23/10005110_20210223121927.jpg',
    'https://mimg.lalavla.com/resources/images/prdimg/202102/23/10005110_20210223121939.jpg'
  ],
  item_name: '포렌코즈 베어 섀도우 팔레트',
  item_price: '20,000원',
  item_discountPrice: '12,600원',
  item_isTestable: true,
  // 옵션이 없는 상품의 경우 optionList에는 상품 이름과 같은 옵션 1개만 담김
  item_optionList: [
    {
      option_id: 1,
      option_name: '01 페일',
      option_price: '20,000원',
      option_discountPrice: '12,600원',
      option_isSoldout: false
    },
    {
      option_id: 2,
      option_name: '02 블링크',
      option_price: '20,000원',
      option_discountPrice: '12,600원',
      option_isSoldout: false
    },
    {
      option_id: 3,
      option_name: '03 미디움',
      option_price: '20,000원',
      option_discountPrice: '12,600원',
      option_isSoldout: false
    },
    {
      option_id: 4,
      option_name: '04 레이크',
      option_price: '20,000원',
      option_discountPrice: '12,600원',
      option_isSoldout: true
    },
    {
      option_id: 5,
      option_name: '05 우디',
      option_price: '20,000원',
      option_discountPrice: '12,600원',
      option_isSoldout: false
    },
    {
      option_id: 6,
      option_name: '06 뮤트',
      option_price: '20,000원',
      option_discountPrice: '12,600원',
      option_isSoldout: true
    },
  ]
};


const docTitle = document.getElementsByTagName('title')[0];
docTitle.textContent = `상품 : ${item.item_name}`;


const title = document.querySelector('.title');
title.textContent = item.item_name;


localStorage.setItem(item.item_id, 0);
const detailContainer = document.querySelector('.detail-container');
const btnContainer = document.querySelector('.btn-container');
const imgContainer = document.querySelector('.img-container');

const getImg = (num, name) => {
  const detailImg = document.createElement('img');
  detailImg.src = item.item_imgList[num];
  detailImg.alt = name;
  detailImg.classList += `detail-img-${num}`;

  return detailImg;
}

createBtnContainer(item.item_id, item.item_isTestable).forEach(el => 
  btnContainer.appendChild(el));

imgContainer.appendChild(getImg(localStorage.getItem(item.item_id), item.item_name));




const goPrevBtn = document.querySelector('.go-prev')
const goItemPrevBtn = document.querySelector('.item-prev-btn');
const goItemNextBtn = document.querySelector('.item-next-btn');
const footer = document.querySelector('footer');
const modalContainer = document.querySelector('.modal-container');

const checkFirstPage = page => (page <= 0);
const checkLastPage = (page, length) => (Number(page) + 1 >= length);

goPrevBtn.addEventListener('click', () => { history.back(); });



goItemPrevBtn.addEventListener('click', () => {
  if (checkFirstPage(localStorage.getItem(item.item_id))) {
    console.log('First Page!');
    alert('제일 첫 페이지입니다.');
  } else {
    localStorage.setItem(item.item_id, (localStorage.getItem(item.item_id)) - 1);
    console.log(localStorage.getItem(item.item_id));
    imgContainer.innerHTML = '';
    imgContainer.appendChild(getImg(localStorage.getItem(item.item_id), item.item_name));
  }
});

goItemNextBtn.addEventListener('click', () => {
  if (checkLastPage(localStorage.getItem(item.item_id), item.item_imgList.length)) {
    console.log('Last Page');
    alert('제일 마지막 페이지입니다.');
  } else {
    localStorage.setItem(item.item_id, Number(localStorage.getItem(item.item_id)) + 1);
    console.log(localStorage.getItem(item.item_id));
    imgContainer.innerHTML = '';
    imgContainer.appendChild(getImg(localStorage.getItem(item.item_id), item.item_name));
  }
});

footer.addEventListener('click', () => {
  modalContainer.innerHTML = '';
  modalContainer.style.display = 'flex';

  const modal = (item.item_optionList.length <= 1) 
    ? singleOptionModal(item) 
    : multipleOptionModal(item);
  modalContainer.appendChild(modal);
});