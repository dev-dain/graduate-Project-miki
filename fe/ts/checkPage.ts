const checkPage = (state: string): void => {
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
      if (state === 'search') {
        fetchData((Number(localStorage.getItem('page')) - 1).toString(), keyword, sortWay);
      } else {
        fetchData((Number(localStorage.getItem('page')) - 1).toString(), num, sortWay);
      }
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
      if (state === 'search') {
        fetchData((Number(localStorage.getItem('page')) + 1).toString(), keyword, sortWay);
      } else {
        fetchData((Number(localStorage.getItem('page')) + 1).toString(), num, sortWay);
      }
      localStorage.setItem('page', (Number(localStorage.getItem('page')) + 1).toString());
    }
  });

  itemContainer.appendChild(itemNextBtn);
}