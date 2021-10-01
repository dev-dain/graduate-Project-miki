const itemRowContainer = document.querySelector('.item-row-container');
const itemRowList = [];
const totalPayPrice = document.querySelector('.total-pay-price');
const modalContainer = document.querySelector('.modal-container');
const goPaymentPartBtn = document.querySelector('.go-payment-part-btn');
const goPaymentAllBtn = document.querySelector('.go-payment-all-btn');
const deleteAllBtn = document.querySelector('.delete-all-btn');
const goTestAllBtn = document.querySelector('.test-all-btn');
goTestAllBtn.addEventListener('click', e => {
    e.preventDefault();
    location.href = '/testAll';
});
goPaymentPartBtn.addEventListener('click', e => {
    e.preventDefault();
    const selectedCart = [];
    selectedCartList.forEach(id => {
        selectedCart.push(fullCartList[id]);
    });
    console.log(selectedCart);
    const form = document.querySelector('.cart-form');
    const selectInput = document.querySelector('.selectInput');
    selectInput.setAttribute('value', encodeURI(JSON.stringify(selectedCart)));
    form.submit();
});
goPaymentAllBtn.addEventListener('click', e => {
    e.preventDefault();
    location.href = '/orderAllList';
});
deleteAllBtn.addEventListener('click', e => {
    if (window.confirm("장바구니를 완전히 비우시겠습니까?")) {
        fetch(`/cart/`, { method: 'DELETE' })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                modalContainer.appendChild(createModal(modalContainer, 'clear'));
                modalContainer.classList.add('display');
                setTimeout(() => location.reload(), 1500);
            })
            .catch(e => console.error(e));
    }
});
const createItemRow = (optionId) => {
    const itemRow = document.createElement('div');
    itemRow.classList.add('item-row');
    const rowLeftArea = createLeftArea(optionId);
    const rowRightArea = createRightArea(optionId);
    itemRow.appendChild(rowLeftArea);
    itemRow.appendChild(rowRightArea);
    return itemRow;
};
const createLeftArea = (optionId) => {
    const price = itemObj[itemOptionObj[optionId].parentId].discountPrice * cartCountObj[optionId].count;
    const rowLeftArea = document.createElement('div');
    rowLeftArea.classList.add('row-left-area');
    const itemImg = document.createElement('img');
    itemImg.classList.add('item-thumbnail-img');
    itemImg.src = itemImgObj[itemOptionObj[optionId].parentId];
    const itemCheckBtn = document.createElement('button');
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
            selectedCartList = new Set([...selectedCartList].filter(id => id !== Number(optionId)));
        }
        else {
            itemOptionObj[optionId].isThisCheck = true;
            if (!itemCheckBtn.classList.contains('checked')) {
                itemCheckBtn.classList.add('checked');
            }
            itemCheckBtn.innerHTML = '&#10004;';
            wholePrice += price;
            totalPayPrice.textContent = `${wholePrice} 원`;
            selectedCartList.add(Number(optionId));
        }
        console.log(selectedCartList);
    });
    itemCheckBtn.innerHTML = '&#10004;';
    rowLeftArea.appendChild(itemImg);
    rowLeftArea.appendChild(itemCheckBtn);
    return rowLeftArea;
};
const createRightArea = (optionId) => {
    const rowRightArea = document.createElement('div');
    rowRightArea.classList.add('row-right-area');
    const nameArea = document.createElement('div');
    nameArea.classList.add('name-area');
    const nameText = document.createElement('p');
    nameText.classList.add('name-text');
    nameText.textContent = itemObj[itemOptionObj[optionId].parentId].name;
    const deleteBtn = document.createElement('button');
    deleteBtn.classList.add('delete-item-btn');
    deleteBtn.addEventListener('click', e => {
        e.preventDefault();
        if (window.confirm("정말 삭제하시겠습니까?")) {
            fetch(`/cart/${cartCountObj[optionId].id}`, { method: 'DELETE' })
                .then(res => res.json())
                .then(data => console.log(data))
                .catch(e => console.error(e));
            modalContainer.appendChild(createModal(modalContainer, 'delete'));
            modalContainer.classList.add('display');
            setTimeout(() => {
                location.href = `/cartList/${localStorage.getItem('store_id')}`;
            }, 2000);
        }
    });
    nameArea.appendChild(nameText);
    nameArea.appendChild(deleteBtn);
    rowRightArea.appendChild(nameArea);
    const countPriceArea = createCountPriceArea(optionId);
    if (itemObj[itemOptionObj[optionId].parentId].isOptional === 'Y') {
        const optionArea = document.createElement('div');
        optionArea.classList.add('option-area');
        const optionText = document.createElement('p');
        optionText.classList.add('option-text');
        optionText.textContent = `선택 옵션 : ${itemOptionObj[optionId].name}`;
        optionArea.appendChild(optionText);
        rowRightArea.appendChild(optionArea);
    }
    rowRightArea.appendChild(countPriceArea);
    return rowRightArea;
};
const createCountPriceArea = (optionId) => {
    let originPrice = (itemObj[itemOptionObj[optionId].parentId].originPrice) * cartCountObj[optionId].count;
    let discountPrice = itemObj[itemOptionObj[optionId].parentId].discountPrice * cartCountObj[optionId].count;
    wholePrice += discountPrice;
    const countPriceArea = document.createElement('div');
    countPriceArea.classList.add('count-price-area');
    const countDiv = document.createElement('div');
    countDiv.classList.add('count-area');
    const subCountBtn = document.createElement('button');
    const countInput = document.createElement('input');
    const addCountBtn = document.createElement('button');
    subCountBtn.classList.add(`m-sub-count-${optionId}`);
    countInput.classList.add(`m-count-${optionId}`);
    addCountBtn.classList.add(`m-add-count-${optionId}`);
    countInput.value = cartCountObj[optionId].count;
    subCountBtn.textContent = '-';
    addCountBtn.textContent = '+';
    const priceDiv = document.createElement('div');
    priceDiv.classList.add('price-area');
    const originPriceText = document.createElement('span');
    originPriceText.classList.add('origin-price');
    originPriceText.textContent = `${originPrice} 원`;
    const discountPriceText = document.createElement('span');
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
            modalContainer.appendChild(createModal(modalContainer, 'lack'));
            modalContainer.classList.add('display');
        }
        else {
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
                .catch(e => console.error(e));
        }
    });
    addCountBtn.addEventListener('click', e => {
        e.preventDefault();
        if (!countPriceArea.parentElement.previousSibling.children[1].textContent) {
            return;
        }
        if (Number(countInput.value) >= itemOptionObj[optionId].quantity) {
            modalContainer.appendChild(createModal(modalContainer, 'excess'));
            modalContainer.classList.add('display');
        }
        else {
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
                .catch(e => console.error(e));
        }
    });
    countDiv.appendChild(subCountBtn);
    countDiv.appendChild(countInput);
    countDiv.appendChild(addCountBtn);
    countPriceArea.appendChild(countDiv);
    countPriceArea.appendChild(priceDiv);
    return countPriceArea;
};
Object.keys(itemOptionObj).forEach(k => itemRowList.push(createItemRow(k)));
const goPrevBtn = document.querySelector('.go-prev');
goPrevBtn.addEventListener('click', () => history.back());
localStorage.setItem('cart_page', '1');
let curCartPage = 1;
const maxCartPage = Math.ceil(itemRowList.length / pageViewCount);
const currentPage = document.querySelector('.current-page');
const goPrevPageBtn = document.querySelector('.go-prev-page-btn');
const goNextPageBtn = document.querySelector('.go-next-page-btn');
const movePage = (state, inc) => {
    if ((curCartPage === 1 && state === 'first') ||
        (curCartPage === maxCartPage && state === 'last')) {
        modalContainer.appendChild(createModal(modalContainer, state));
        modalContainer.classList.add('display');
    }
    else {
        curCartPage += inc;
        localStorage.setItem('cart_page', curCartPage);
        currentPage.textContent = `${curCartPage} / ${maxCartPage}`;
        itemRowContainer.innerHTML = '';
        let startIndex = (curCartPage - 1) * 4;
        for (let i = startIndex; i < (startIndex + pageViewCount); i++) {
            itemRowContainer.appendChild(itemRowList[i]);
        }
    }
};
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
}
else {
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
    }
    else {
        break;
    }
}
totalPayPrice.textContent = `${wholePrice} 원`;
let allPrice = wholePrice;
const selectAllBtn = document.querySelector('.select-all-btn');
let isSelectAll = true;
selectAllBtn.addEventListener('click', () => {
    /* warning */
    isSelectAll = isSelectAll ? false : true;
    console.log(isSelectAll);
    selectAllBtn.innerHTML = isSelectAll ? '&#10004;' : '';
    itemRowList.forEach(row => {
        if (isSelectAll) {
            if (!row.children[0].children[1].classList.contains('checked')) {
                row.children[0].children[1].classList.add('checked');
            }
            for (let id of Object.keys(itemOptionObj)) {
                itemOptionObj[id].isThisCheck = true;
            }
            row.children[0].children[1].innerHTML = '&#10004';
        }
        else {
            if (row.children[0].children[1].classList.contains('checked')) {
                row.children[0].children[1].classList.remove('checked');
            }
            for (let id of Object.keys(itemOptionObj)) {
                itemOptionObj[id].isThisCheck = false;
            }
            row.children[0].children[1].innerHTML = '';
        }
    });

    if (!isSelectAll) {
        wholePrice = 0;
        totalPayPrice.textContent = `0 원`;
        selectAllBtn.innerHTML = '';
        selectedCartList.clear();
    }
    else {
        wholePrice = allPrice;
        totalPayPrice.textContent = `${allPrice} 원`;
        selectAllBtn.innerHTML = '&#10004;';
        selectedCartList = new Set(cartIdList);
    }
    console.log(selectedCartList);
});
