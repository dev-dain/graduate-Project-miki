interface searchResult {
  size: string,
  keyword: string
};

const resultItemTitle: HTMLSpanElement = document.querySelector('.result-item-title') as HTMLSpanElement;
resultItemTitle.textContent = keyword;

interface itemInfo {
  itemDiscountPrice: string,
  itemId: string,
  itemImage: string,
  itemName: string,
  itemPrice: string,
  itemTestable: string
};

let sortWay: string = localStorage.getItem('search-sort-way') || 'id';
const wrapContainer: HTMLDivElement = document.getElementById('wrap') as HTMLDivElement;
const tbody: HTMLTableSectionElement = document.querySelector('tbody') as HTMLTableSectionElement;
 
const fetchData = (pageNum: string, keyword: string, sortWay: string): void => {
  console.log(sortWay);
  let itemObjList: itemInfo[] = [];

  fetch(`/dev/search?pageNum=${pageNum}&sort=${sortWay}&keyword=${keyword}`)
    .then(res => res.text())
    .then(data => {
      itemObjList = (JSON.parse(data))['Item'];
      itemObjList.forEach(function (item) {
        tbody.appendChild(createItemCard(item));
      });
      console.log(itemObjList);
    })
    .catch(e => console.error(e));
}

const createItemCard = (item: itemInfo): HTMLTableRowElement => {
  const itemCard: HTMLTableRowElement = document.createElement('tr') as HTMLTableRowElement;
  itemCard.classList.add('item-card');

  const itemImgArea: HTMLTableCellElement = document.createElement('td') as HTMLTableCellElement;
  itemImgArea.classList.add('item-img-area');

  const thumbnailImg: HTMLImageElement = document.createElement('img') as HTMLImageElement;
  thumbnailImg.classList.add('thumbnail-img');
  thumbnailImg.src = item.itemImage;
  thumbnailImg.alt = item.itemName;

  itemImgArea.addEventListener('click', (): void => {
    location.href = `/item/${item.itemId}`;
  });
  itemImgArea.appendChild(thumbnailImg);

  const hrTd: HTMLTableCellElement = document.createElement('td') as HTMLTableCellElement;
  const divideHr: HTMLHRElement = document.createElement('hr') as HTMLHRElement;
  divideHr.classList.add('divide-img-info');
  hrTd.appendChild(divideHr);

  const itemTitleTd: HTMLTableCellElement = document.createElement('td') as HTMLTableCellElement;
  itemTitleTd.classList.add('item-title');
  itemTitleTd.textContent = item.itemName;
  itemTitleTd.addEventListener('click', (): void => {
    location.href = `/item/${item.itemId}`;
  });

  itemCard.appendChild(itemImgArea);
  itemCard.appendChild(hrTd);
  itemCard.appendChild(itemTitleTd);

  if (item.itemDiscountPrice !== item.itemPrice) {
    const originPrice: HTMLTableCellElement = document.createElement('td') as HTMLTableCellElement;
    originPrice.classList.add('origin-price');
    originPrice.textContent = `${item.itemPrice}원`;
    itemCard.appendChild(originPrice);
  }
  const discountPrice: HTMLTableCellElement = document.createElement('td') as HTMLTableCellElement;
  discountPrice.classList.add('discount-price');
  discountPrice.textContent = `${item.itemDiscountPrice}원`;
  itemCard.appendChild(discountPrice);

  if (item.itemTestable === 'Y') {
    const testTd: HTMLTableCellElement = document.createElement('td') as HTMLTableCellElement;
    const goTestBtn: HTMLButtonElement = document.createElement('button') as HTMLButtonElement;
    goTestBtn.classList.add('go-test');
    goTestBtn.textContent = `테스트`;
    goTestBtn.addEventListener('click', (): void => {
      location.href = `/test/${item.itemId}`;
    });

    testTd.appendChild(goTestBtn);
    itemCard.appendChild(testTd);
  }

  return itemCard;
}


const micBtn: HTMLButtonElement = document.querySelector('.turn-on-mic') as HTMLButtonElement;
const cartBtn: HTMLButtonElement = document.querySelector('.go-cart') as HTMLButtonElement;
const goPrevBtn: HTMLButtonElement = document.querySelector('.go-prev') as HTMLButtonElement;

micBtn.addEventListener('click', (): void => { location.href = '/searchVoice' });
cartBtn.addEventListener('click', (): void => {
  location.href = `/cartList/${localStorage.getItem('store_id')}`;
});
goPrevBtn.addEventListener('click', (): void => { history.back(); });




const itemContainer: HTMLDivElement = document.querySelector('.item-container') as HTMLDivElement;

if (count === '0') {
  const sortContainer: HTMLDivElement = document.querySelector('.sort-container') as HTMLDivElement;
  sortContainer.style.display = 'none';

  const noResultBox: HTMLDivElement = document.createElement('div');
  noResultBox.classList.add('no-result');

  const noResultPic: HTMLImageElement = document.createElement('img');
  noResultPic.classList.add('no-result-pic');
  noResultPic.src = '/img/information.png';

  const noResultCaution: HTMLParagraphElement = document.createElement('p');
  noResultCaution.classList.add('no-result-caution');
  noResultCaution.textContent = '현재 취급하지 않는 상품입니다. :('

  const noResultBtnContainer: HTMLDivElement = document.createElement('div');
  noResultBtnContainer.classList.add('no-result-btn-container');

  const goSearchBtn: HTMLButtonElement = document.querySelector('.go-search') as HTMLButtonElement;
  const goHomeBtn: HTMLButtonElement = document.querySelector('.go-home') as HTMLButtonElement;
  goSearchBtn.classList.add('no-result-btn');
  goSearchBtn.classList.add('go-search');
  goSearchBtn.textContent = '다시 검색하기';
  goSearchBtn.addEventListener('click', (): void => {
    location.href = '/searchVoice';
  });

  goHomeBtn.classList.add('no-result-btn');
  goHomeBtn.classList.add('go-home');
  goHomeBtn.textContent = '홈으로 가기';
  goHomeBtn.addEventListener('click', (): void => {
    location.href = '/main';
  });

  noResultBtnContainer.appendChild(goSearchBtn);
  noResultBtnContainer.appendChild(goHomeBtn);

  noResultBox.appendChild(noResultPic);
  noResultBox.appendChild(noResultCaution);
  noResultBox.appendChild(noResultBtnContainer);

  itemContainer.appendChild(noResultBox);

} else {
  const sortContainer: HTMLDivElement = document.querySelector('.sort-container') as HTMLDivElement;
  sortContainerFunc(sortContainer, 'search');

  fetchData('1', keyword, sortWay);

  const maxNum: string = (Math.ceil(Number(count) / 9)).toString();
  console.log('maxNum ', maxNum);

  localStorage.setItem('searchMax', maxNum);
  console.log(localStorage.getItem('searchMax'));
  localStorage.setItem('page', '1');

  const checkFirstPage = (page: number): boolean => (page <= 1);
  const checkLastPage = (page: number): boolean => page >= Number(localStorage.getItem('searchMax'));

  const modalContainer: HTMLDivElement = document.querySelector('.modal-container') as HTMLDivElement;

  const itemPrevBtn: HTMLButtonElement = document.createElement('button');
  itemPrevBtn.classList.add('item-btn', 'item-prev-btn');
  itemPrevBtn.textContent = '<';
  const itemNextBtn: HTMLButtonElement = document.createElement('button');
  itemNextBtn.classList.add('item-btn', 'item-next-btn');
  itemNextBtn.textContent = '>';

  itemPrevBtn.addEventListener('click', (): void => {
    if (checkFirstPage(Number(localStorage.getItem('page')))) {
      console.log('First Page!');
      modalContainer.appendChild(createModal(modalContainer, 'first'));
      modalContainer.classList.add('display');
    } else {
      tbody.innerHTML = '';
      fetchData((Number(localStorage.getItem('page'))-1).toString(), keyword, sortWay);
      localStorage.setItem('page', (Number(localStorage.getItem('page')) - 1).toString());
    }
  });

  itemContainer.insertBefore(itemPrevBtn, itemContainer.firstElementChild);

  itemNextBtn.addEventListener('click', (): void => {
    if (checkLastPage(Number(localStorage.getItem('page')))) {
      console.log('Last Page!');
      modalContainer.appendChild(createModal(modalContainer, 'last'));
      modalContainer.classList.add('display');
    } else {
      tbody.innerHTML = '';
      fetchData((Number(localStorage.getItem('page'))+1).toString(), keyword, sortWay);
      localStorage.setItem('page', (Number(localStorage.getItem('page'))+1).toString());
    }
  });

  itemContainer.appendChild(itemNextBtn);
}