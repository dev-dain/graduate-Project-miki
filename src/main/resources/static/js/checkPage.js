const checkPage = (state) => {
    const checkFirstPage = (page) => (page <= 1);
    const checkLastPage = (page) => {
        if (state === 'search') {
            return page >= Number(localStorage.getItem('searchMax'));
        }
        return page >= Number(localStorage.getItem('categoryMax'));
    };
    const modalContainer = document.querySelector('.modal-container');
    const itemPrevBtn = document.createElement('button');
    itemPrevBtn.classList.add('item-btn', 'item-prev-btn');
    itemPrevBtn.textContent = '<';
    const itemNextBtn = document.createElement('button');
    itemNextBtn.classList.add('item-btn', 'item-next-btn');
    itemNextBtn.textContent = '>';
    itemPrevBtn.addEventListener('click', () => {
        if (checkFirstPage(Number(localStorage.getItem('page')))) {
            console.log('First Page!');
            modalContainer.appendChild(createModal(modalContainer, 'first'));
            modalContainer.classList.add('display');
        }
        else {
            tbody.innerHTML = '';
            if (state === 'search') {
                fetchData((Number(localStorage.getItem('page')) - 1).toString(), keyword, sortWay);
            }
            else {
                fetchData((Number(localStorage.getItem('page')) - 1).toString(), num, sortWay);
            }
            localStorage.setItem('page', (Number(localStorage.getItem('page')) - 1).toString());
            console.log(localStorage.getItem('page'));
        }
    });
    itemContainer.insertBefore(itemPrevBtn, itemContainer.firstElementChild);
    itemNextBtn.addEventListener('click', () => {
        if (checkLastPage(Number(localStorage.getItem('page')))) {
            console.log('Last Page!');
            modalContainer.appendChild(createModal(modalContainer, 'last'));
            modalContainer.classList.add('display');
        }
        else {
            tbody.innerHTML = '';
            console.log((Number(localStorage.getItem('page')) + 1).toString());
            if (state === 'search') {
                fetchData((Number(localStorage.getItem('page')) + 1).toString(), keyword, sortWay);
            }
            else {
                fetchData((Number(localStorage.getItem('page')) + 1).toString(), num, sortWay);
            }
            localStorage.setItem('page', (Number(localStorage.getItem('page')) + 1).toString());
            console.log(localStorage.getItem('page'));
        }
    });
    itemContainer.appendChild(itemNextBtn);
};
