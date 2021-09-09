const createItemCard = (item) => {
    const itemCard = document.createElement('tr');
    itemCard.classList.add('item-card');
    const itemImgArea = document.createElement('td');
    itemImgArea.classList.add('item-img-area');
    const thumbnailImg = document.createElement('img');
    thumbnailImg.classList.add('thumbnail-img');
    thumbnailImg.src = item.itemImage;
    thumbnailImg.alt = item.itemName;
    itemImgArea.addEventListener('click', () => {
        location.href = `/item/${item.itemId}`;
    });
    itemImgArea.appendChild(thumbnailImg);
    const hrTd = document.createElement('td');
    const divideHr = document.createElement('hr');
    divideHr.classList.add('divide-img-info');
    hrTd.appendChild(divideHr);
    const itemTitleTd = document.createElement('td');
    itemTitleTd.classList.add('item-title');
    itemTitleTd.textContent = item.itemName;
    itemTitleTd.addEventListener('click', () => {
        location.href = `/item/${item.itemId}`;
    });
    itemCard.appendChild(itemImgArea);
    itemCard.appendChild(hrTd);
    itemCard.appendChild(itemTitleTd);
    if (item.itemDiscountPrice !== item.itemPrice) {
        const originPrice = document.createElement('td');
        originPrice.classList.add('origin-price');
        originPrice.textContent = `${item.itemPrice}원`;
        itemCard.appendChild(originPrice);
    }
    const discountPrice = document.createElement('td');
    discountPrice.classList.add('discount-price');
    discountPrice.textContent = `${item.itemDiscountPrice}원`;
    itemCard.appendChild(discountPrice);
    if (item.itemTestable === 'Y') {
        const testTd = document.createElement('td');
        const goTestBtn = document.createElement('button');
        goTestBtn.classList.add('go-test');
        goTestBtn.textContent = `테스트`;
        goTestBtn.addEventListener('click', () => {
            location.href = `/test/${item.itemId}`;
        });
        testTd.appendChild(goTestBtn);
        itemCard.appendChild(testTd);
    }
    return itemCard;
};
