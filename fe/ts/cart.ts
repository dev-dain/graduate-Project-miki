const itemRowContainer: HTMLDivElement = document.querySelector('.item-row-container') as HTMLDivElement;
const itemRowList: Array<HTMLDivElement> = [];
const totalPayPrice: HTMLParagraphElement = document.querySelector('.total-pay-price') as HTMLParagraphElement;
const modalContainer: HTMLDivElement = document.querySelector('.modal-container') as HTMLDivElement;

const goTestAllBtn: HTMLButtonElement = document.querySelector('.test-all-btn') as HTMLButtonElement;
const goPaymentPartBtn: HTMLButtonElement = document.querySelector('.go-payment-part-btn') as HTMLButtonElement; 
const goPaymentAllBtn: HTMLButtonElement = document.querySelector('.go-payment-all-btn') as HTMLButtonElement;
const deleteAllBtn: HTMLButtonElement = document.querySelector('.delete-all-btn') as HTMLButtonElement;

goTestAllBtn.addEventListener('click', e => {
  e.preventDefault();
  location.href = '/testAll';
});

goPaymentPartBtn.addEventListener('click', e => {
  e.preventDefault();
  const selectedCart: number[] = [];

  selectedCartList.forEach(id => {
    selectedCart.push(fullCartList[id])
  });

  console.log(selectedCart);

  const form: HTMLFormElement = document.querySelector('.cart-form') as HTMLFormElement;
  const selectInput: HTMLInputElement = document.querySelector('.selectInput') as HTMLInputElement;
  selectInput.setAttribute('value', encodeURI(JSON.stringify(selectedCart)));
  form.submit();
});

goPaymentAllBtn.addEventListener('click', e => {
  e.preventDefault();
  location.href = '/orderAllList';
});

deleteAllBtn.addEventListener('click', e => {
  if (window.confirm("장바구니를 완전히 비우시겠습니까?")) {
    fetch(`/cart/`, {method: 'DELETE'})
      .then(res => res.json())
      .then(data => {
        console.log(data);
        modalContainer.appendChild(createModal('clear'));
        modalContainer.classList.add('display');
        setTimeout(() => location.reload(), 1500);
      })
      .catch(e => console.error(e));
  }
});


const createItemRow = (optionId: number): HTMLDivElement => {
  const itemRow: HTMLDivElement = document.createElement('div');
  itemRow.classList.add('item-row');

  const rowLeftArea: HTMLDivElement = createLeftArea(optionId);
  const rowRightArea: HTMLDivElement = createRightArea(optionId);

  itemRow.appendChild(rowLeftArea);
  itemRow.appendChild(rowRightArea);

  return itemRow;
}


const createLeftArea = (optionId: number): HTMLDivElement => {
  const price: number = itemObj[itemOptionObj[optionId].parentId].discountPrice * cartCountObj[optionId].count;

  const rowLeftArea: HTMLDivElement = document.createElement('div');
  rowLeftArea.classList.add('row-left-area');

  const itemImg: HTMLImageElement = document.createElement('img');
  itemImg.classList.add('item-thumbnail-img');
  itemImg.src = itemImgObj[itemOptionObj[optionId].parentId];
  const itemCheckBtn: HTMLButtonElement = document.createElement('button');
  itemCheckBtn.classList.add('item-check-btn');

  itemCheckBtn.addEventListener('click', e => {
    e.preventDefault();
    if (itemOptionObj[optionId].isThisCheck) {
      itemOptionObj[optionId].isThisCheck = false;
      if (itemCheckBtn.classList.contains('checked')) {
        itemCheckBtn.classList.remove('checked');
      }            
      itemCheckBtn.innerHTML = '';
      wholePrice -= price;
      totalPayPrice.textContent = `${wholePrice} 원`;
      selectedCartList = selectedCartList.filter(id => id !== Number(optionId));
    } else {
      itemOptionObj[optionId].isThisCheck = true;
      if (!itemCheckBtn.classList.contains('checked')) {
        itemCheckBtn.classList.add('checked');
      }
      itemCheckBtn.innerHTML = '&#10004;';
      wholePrice += price;
      totalPayPrice.textContent = `${wholePrice} 원`;
      selectedCartList.push(Number(optionId));
    }
    console.log(selectedCartList);
  });
  itemCheckBtn.innerHTML = '&#10004;';

  rowLeftArea.appendChild(itemImg);
  rowLeftArea.appendChild(itemCheckBtn);

  return rowLeftArea;
}


const createRightArea = (optionId: number): HTMLDivElement => {
  const rowRightArea: HTMLDivElement = document.createElement('div');
  rowRightArea.classList.add('row-right-area');

  const nameArea: HTMLDivElement = document.createElement('div');
  nameArea.classList.add('name-area');

  const nameText: HTMLParagraphElement = document.createElement('p');
  nameText.classList.add('name-text');
  nameText.textContent = itemObj[itemOptionObj[optionId].parentId].name;
  
  const deleteBtn: HTMLButtonElement = document.createElement('button');
  deleteBtn.classList.add('delete-item-btn');
  deleteBtn.addEventListener('click', e => {
    e.preventDefault();
    if (window.confirm("정말 삭제하시겠습니까?")) {
      fetch(`/cart/${cartCountObj[optionId].id}`, {method: 'DELETE'})
          .then(res => res.json())
          .then(data => console.log(data))
          .catch(e => console.error(e));
      modalContainer.appendChild(createModal('delete'));
      modalContainer.classList.add('display');
      setTimeout(() => {
        location.href = `/cartList/${localStorage.getItem('store_id')}`
      }, 2000);
    }
  });

  nameArea.appendChild(nameText);
  nameArea.appendChild(deleteBtn);

  rowRightArea.appendChild(nameArea);

  const countPriceArea: HTMLDivElement = createCountPriceArea(optionId);

  if (itemObj[itemOptionObj[optionId].parentId].isOptional === 'Y') {
    const optionArea: HTMLDivElement = document.createElement('div');
    optionArea.classList.add('option-area');

    const optionText: HTMLParagraphElement = document.createElement('p');
    optionText.classList.add('option-text');
    optionText.textContent = `선택 옵션 : ${itemOptionObj[optionId].name}`;

    optionArea.appendChild(optionText);
    rowRightArea.appendChild(optionArea);
  }

  rowRightArea.appendChild(countPriceArea);

  return rowRightArea;
}


const createCountPriceArea = (optionId: number): HTMLDivElement => {
  let originPrice: number = (itemObj[itemOptionObj[optionId].parentId].originPrice) * cartCountObj[optionId].count;
  let discountPrice: number = itemObj[itemOptionObj[optionId].parentId].discountPrice * cartCountObj[optionId].count;

  wholePrice += discountPrice;

  const countPriceArea: HTMLDivElement = document.createElement('div');
  countPriceArea.classList.add('count-price-area');

  const countDiv: HTMLDivElement = document.createElement('div');
  countDiv.classList.add('count-area');

  const subCountBtn: HTMLButtonElement = document.createElement('button');
  const countInput: HTMLInputElement = document.createElement('input');
  const addCountBtn: HTMLButtonElement = document.createElement('button');

  subCountBtn.classList.add(`m-sub-count-${optionId}`);
  countInput.classList.add(`m-count-${optionId}`);
  addCountBtn.classList.add(`m-add-count-${optionId}`);
  countInput.value = cartCountObj[optionId].count;

  subCountBtn.textContent = '-';
  addCountBtn.textContent = '+';

  const priceDiv: HTMLDivElement = document.createElement('div');
  priceDiv.classList.add('price-area');

  const originPriceText: HTMLSpanElement = document.createElement('span');
  originPriceText.classList.add('origin-price');
  originPriceText.textContent = `${originPrice} 원`;

  const discountPriceText: HTMLSpanElement = document.createElement('span');
  discountPriceText.classList.add('discount-price');
  discountPriceText.textContent = `${discountPrice} 원`;

  if (originPrice === discountPrice) {
    originPriceText.style.display = 'none';
  }

  priceDiv.appendChild(originPriceText);
  priceDiv.appendChild(discountPriceText);

  subCountBtn.addEventListener('click', e => {
    e.preventDefault();
    if (!countPriceArea.parentElement.previousSibling.children[1].textContent) {
      return;
    }
    if (Number(countInput.value) < 2) {
      modalContainer.appendChild(createModal('lack'));
      modalContainer.classList.add('display');
    } else {
      countInput.value = String(Number(countInput.value) - 1);
      cartCountObj[optionId].count = countInput.value;
      wholePrice -= itemObj[itemOptionObj[optionId].parentId].discountPrice;
      totalPayPrice.textContent = `${wholePrice} 원`;

      originPrice -= itemObj[itemOptionObj[optionId].parentId].originPrice;
      discountPrice -= itemObj[itemOptionObj[optionId].parentId].discountPrice;
      originPriceText.textContent = `${originPrice} 원`;
      discountPriceText.textContent = `${discountPrice} 원`;

      fetch(`/cart/${cartCountObj[optionId].id}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
          item_id: itemOptionObj[optionId].parentId,
          item_option_id: optionId,
          count: Number(countInput.value)
        })
      })
          .then(res => res.json())
          .then(data => console.log(data))
          .catch(e => console.error(e))
    }
  });

  addCountBtn.addEventListener('click', e => {
    e.preventDefault();

    if (!countPriceArea.parentElement.previousSibling.children[1].textContent) {
      return;
    }
    if (Number(countInput.value) >= itemOptionObj[optionId].quantity) {
      modalContainer.appendChild(createModal('excess'));
      modalContainer.classList.add('display');
    } else {
      countInput.value = String(Number(countInput.value) + 1);
      cartCountObj[optionId].count = countInput.value;
      wholePrice += itemObj[itemOptionObj[optionId].parentId].discountPrice;
      totalPayPrice.textContent = `${wholePrice} 원`;

      originPrice += itemObj[itemOptionObj[optionId].parentId].originPrice;
      discountPrice += itemObj[itemOptionObj[optionId].parentId].discountPrice;
      originPriceText.textContent = `${originPrice} 원`;
      discountPriceText.textContent = `${discountPrice} 원`;

      fetch(`/cart/${cartCountObj[optionId].id}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
          item_id: itemOptionObj[optionId].parentId,
          item_option_id: optionId,
          count: Number(countInput.value)
        })
      })
          .then(res => res.json())
          .then(data => console.log(data))
          .catch(e => console.error(e))
    }
  });

  countDiv.appendChild(subCountBtn);
  countDiv.appendChild(countInput);
  countDiv.appendChild(addCountBtn);

  countPriceArea.appendChild(countDiv);
  countPriceArea.appendChild(priceDiv);

  return countPriceArea;
}


Object.keys(itemOptionObj).forEach(k => itemRowList.push(createItemRow(k)));




const goPrevBtn: HTMLButtonElement = document.querySelector('.go-prev') as HTMLButtonElement;
goPrevBtn.addEventListener('click', () => history.back());

localStorage.setItem('cart_page', '1');
let curCartPage = 1;
const maxCartPage: number = Math.ceil(itemRowList.length / pageViewCount);

const currentPage: HTMLSpanElement = document.querySelector('.current-page') as HTMLSpanElement;
const goPrevPageBtn: HTMLButtonElement = document.querySelector('.go-prev-page-btn') as HTMLButtonElement;
const goNextPageBtn: HTMLButtonElement = document.querySelector('.go-next-page-btn') as HTMLButtonElement;

const movePage = (state: string, inc: number): void => {
  if ((curCartPage === 1 && state === 'first') ||
      (curCartPage === maxCartPage && state === 'last')) {
    modalContainer.appendChild(createModal(state));
    modalContainer.classList.add('display');
  } else {
    curCartPage += inc;
    localStorage.setItem('cart_page', curCartPage);
    currentPage.textContent = `${curCartPage} / ${maxCartPage}`;

    itemRowContainer.innerHTML = '';
    let startIndex: number = (curCartPage - 1) * 4;
    for (let i = startIndex; i < (startIndex + pageViewCount); i++) {
      itemRowContainer.appendChild(itemRowList[i]);
    }
  }
}

if (itemRowList.length) {
  currentPage.textContent = `${curCartPage} / ${maxCartPage}`;

  goPrevPageBtn.addEventListener('click', e => {
    e.preventDefault();
    movePage('first', -1);
  });

  goNextPageBtn.addEventListener('click', e => {
    e.preventDefault();
    movePage('last', 1);
  });
} else {
  goPaymentAllBtn.disabled = true;
  goPaymentPartBtn.disabled = true;
  deleteAllBtn.disabled = true;
  itemRowContainer.textContent = '장바구니가 비어 있습니다';
  itemRowContainer.style.textAlign = 'center';
  itemRowContainer.style.padding = '50px';
  itemRowContainer.style.fontSize = '2rem';
  goPrevPageBtn.style.display = 'none';
  goNextPageBtn.style.display = 'none';
}


for (let i = 0; i < pageViewCount; i++) {
  if (itemRowList[i]) {
    itemRowContainer.appendChild(itemRowList[i]);
  } else {
    break;
  }
}

totalPayPrice.textContent = `${wholePrice} 원`;


const selectAllBtn: HTMLButtonElement = document.querySelector('.select-all-btn') as HTMLButtonElement;
let isSelectAll: boolean = true;

selectAllBtn.addEventListener('click', () => {
  /* warning */
  isSelectAll = !isSelectAll;
  selectAllBtn.innerHTML = isSelectAll ? '&#10004;' : '';
  itemRowList.forEach(row => {
    console.log(row.children[0].children[1]);
    row.children[0].children[1].click();
  });
  if (isSelectAll) {
    totalPayPrice.textContent = `0 원`;
    selectAllBtn.innerHTML = '';
    selectedCartList = [];
  } else {
    totalPayPrice.textContent = `${wholePrice} 원`;
    selectAllBtn.innerHTML = '&#10004;';
    selectedCartList = cartIdList;
  }
  console.log(selectedCartList);
  isSelectAll = !isSelectAll;
});

