const createCountBtn = (num = -1) => {
  const countBtnContainer = document.createElement('div');
  
  const subCountBtn = document.createElement('button');
  const countInput = document.createElement('input');
  const addCountBtn = document.createElement('button');

  if (num === -1) {
    subCountBtn.classList += 'm-sub-count';
    countInput.classList += 'm-count';
    addCountBtn.classList += 'm-add-count';
    countInput.value = 1;
  } else {
    subCountBtn.classList += `m-sub-count-${num}`;
    countInput.classList += `m-count-${num}`;
    addCountBtn.classList += `m-add-count-${num}`;
    countInput.value = 0;
  }

  subCountBtn.textContent = '-';
  addCountBtn.textContent = '+';

  subCountBtn.addEventListener('click', () => {
    if (Number(countInput.value) === 0) {
      alert('수량을 0개 미만으로 정할 수 없습니다.');
    }
    else if (Number(countInput.value) === 1 && (num === -1)) {
      alert('수량은 1개 이상이어야 합니다.');
    } else {
      countInput.value = countInput.value - 1;
    }
  });
  addCountBtn.addEventListener('click', () => {
    ////////////////// 
    //stock quantity보다 더 할 경우에는 제지가 필요함
    ////////////////// 
    countInput.value = Number(countInput.value) + 1;
  });

  countBtnContainer.appendChild(subCountBtn);
  countBtnContainer.appendChild(countInput);
  countBtnContainer.appendChild(addCountBtn);

  return countBtnContainer;
};


const createModalBtnContainer = id => {
  const modalBtnContainer = document.createElement('div');
  const putInCartBtn = document.createElement('button');
  const cancelBtn = document.createElement('button');

  putInCartBtn.addEventListener('click', () => {
    /////////////////////////
    // item id를 알아야 장바구니에 담는 것이 가능함
    // 이 처리가 필요.. fetch POST를 보내면 될 듯. id와 count 넣어서

    // 또한, 장바구니에 몇 개나 들었는지 알아야 함
    /////////////////////////
    let count = 0;
    if (document.querySelector('.m-count')) {
      count += Number(document.querySelector('.m-count').value);
    } else {
      const optionCountList = document.querySelectorAll('[class^=m-count-]');
      optionCountList.forEach(countInput => count += Number(countInput.value));
    }

    if (!count) {
      alert('장바구니에 담을 상품의 개수를 입력해주세요.');
      return false;
    }

    alert('장바구니에 담겼습니다!');
    modalContainer.style.display = 'none';
  });
  cancelBtn.addEventListener('click', () => {
    modalContainer.style.display = 'none';
  });

  putInCartBtn.textContent = '담기';
  cancelBtn.textContent = '취소';

  modalBtnContainer.classList += 'modal-btn-container';
  putInCartBtn.classList += 'put-cart';
  cancelBtn.classList += 'cancel';

  modalBtnContainer.appendChild(putInCartBtn);
  modalBtnContainer.appendChild(cancelBtn);

  return modalBtnContainer;
};



const createModalOptionContainer = item => {
  const modalOptionContainer = document.createElement('div');
  modalOptionContainer.classList += 'modal-option-container';
  
  const modalOptionList = [];
  for (let i = 0; i < item.item_optionList.length; i++) {
    const modalOption = document.createElement('div');
    modalOption.classList += `modal-option-${i}`;

    const mOptionTitle = document.createElement('span');
    mOptionTitle.classList += `m-option-title-${i}`;
    mOptionTitle.textContent = item.item_optionList[i];
    
    const mOptionBtnContainer = createCountBtn(i);
    mOptionBtnContainer.classList += `m-option-btn-container-${i}`;

    modalOption.appendChild(mOptionTitle);
    modalOption.appendChild(mOptionBtnContainer);
    modalOptionList.push(modalOption);
  }

  modalOptionList.forEach(option => modalOptionContainer.appendChild(option));

  return modalOptionContainer;
}


const singleOptionModal = item => {
  const modal = document.createElement('div');
  modal.classList += 'modal';

  const modalHeader = document.createElement('div');
  modalHeader.classList += 'modal-header';
  modalHeader.textContent = '수량';

  const countBtnContainer = createCountBtn();
  countBtnContainer.classList += 'goods-count-container';

  const modalBtnContainer = createModalBtnContainer(item.item_id);
  
  modal.appendChild(modalHeader);
  modal.appendChild(countBtnContainer);
  modal.appendChild(modalBtnContainer);

  return modal;
};

const multipleOptionModal = item => {
  const modal = document.createElement('div');
  modal.classList += 'modal';
  modal.style.height= '600px';

  const modalHeader = document.createElement('div');
  modalHeader.classList += 'modal-header';
  modalHeader.textContent = '옵션 선택';
  
  const modalOptionContainer = createModalOptionContainer(item);
  const modalBtnContainer = createModalBtnContainer(item);

  modal.appendChild(modalHeader);
  modal.appendChild(modalOptionContainer);
  modal.appendChild(modalBtnContainer);

  return modal;
};
