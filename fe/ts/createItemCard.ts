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