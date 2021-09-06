const createModal = (modalContainer, state, msg) => {
    const modalBG = document.createElement('div');
    modalBG.classList.add('modal-background');
    const modalBox = document.createElement('div');
    modalBox.classList.add('modal-box');
    const modalCloseBtn = document.createElement('span');
    modalCloseBtn.classList.add('modal-close-btn');
    modalCloseBtn.innerHTML = '&times;';
    modalCloseBtn.addEventListener('click', () => {
        modalContainer.innerHTML = '';
        modalContainer.classList.remove('display');
    });
    const modalContent = document.createElement('p');
    modalContent.classList.add('modal-content');
    if (state === 'excess') {
        modalContent.textContent = '재고 수량보다 많이 담을 수 없습니다.';
    }
    else if (state === 'lack') {
        modalContent.textContent = '수량은 1개 이상이어야 합니다.';
    }
    else if (state === 'first') {
        modalContent.textContent = '첫 페이지입니다.';
    }
    else if (state === 'last') {
        modalContent.textContent = '마지막 페이지입니다.';
    }
    else if (state === 'clear') {
        modalContent.textContent = '장바구니를 성공적으로 비웠습니다.';
    }
    else if (state === 'empty') {
        modalContent.textContent = 'ID / 코드를 입력해 주세요.';
    }
    else if (state === 'voice') {
        modalContent.textContent = '검색어를 말씀해 주세요. :)';
    }
    else {
        modalContent.textContent = '상품을 장바구니에서 삭제했습니다.';
    }
    if (msg) {
        modalContent.innerHTML = msg;
    }
    modalBox.appendChild(modalCloseBtn);
    modalBox.appendChild(modalContent);
    modalBG.appendChild(modalBox);
    return modalBG;
};
