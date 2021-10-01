const itemOption = async () => {
    const res = await fetch(`/dev/item/${url}/option?storeId=${localStorage.getItem('store_id')}`);
    const opt = await res.text();
    const options = (JSON.parse(opt))['ItemOption'];
    const modalContainer = document.querySelector('.modal-container');

    console.log(options);

    const quantityList = [];
    const optionIDList = [];
    const optionNameList = [];
    let selectedList = [];

    options.forEach(option => {
        quantityList.push(option.totalCnt);
        optionIDList.push(option.optionId);
        optionNameList.push(option.optionName);
    });


    const tbody = document.querySelector('tbody');
    const optionContainer = document.querySelector('.option-container');

    const addOptionRow = i => {
        const optionRow = document.createElement('tr');
        optionRow.classList.add('option-row');

        const td = document.createElement('td');
        const tdOptionBtn = document.createElement('button');
        tdOptionBtn.classList.add('td-option');
        tdOptionBtn.textContent = optionNameList[i];
        tdOptionBtn.setAttribute('data-optionId', optionIDList[i]);

        td.appendChild(tdOptionBtn);
        optionRow.appendChild(td);

        return optionRow;
    }

    console.log(optionIDList);
    for (let i = 0; i < optionIDList.length; i++) {
        tbody.appendChild(addOptionRow(i));
    }

    const selectedOptionTr = document.createElement('tr');
    selectedOptionTr.classList.add('selected-option');
    const td = document.createElement('td');
    selectedOptionTr.appendChild(td);
    tbody.appendChild(selectedOptionTr);


    const optionRows = document.querySelectorAll('.option-row');
    const selectedOption = document.querySelector('.selected-option > td'); /* 선택된 옵션 */
    const optionSelectTr = document.querySelector('.option-select-tr'); /* 옵션 */
    const optionSelect = document.querySelector('.option-select-tr > td');
    const foldOptionBtn = document.querySelector('.fold-option-btn');
    const cancelSpan = document.querySelector('.cancel');


    const addOption = (id, option_id, quantity, name) => {
        const sOptionDiv = document.createElement('div');
        const nameSpan = document.createElement('span');
        nameSpan.textContent = name;
        sOptionDiv.classList.add(`s-option-${id}-${option_id}`);

        const modalInModal = document.querySelector('.modal-in-modal');
        const countDiv = document.createElement('div');
        const subCountBtn = document.createElement('button');
        const countInput = document.createElement('input');
        const addCountBtn = document.createElement('button');
        const cancelSpan = document.createElement('span');
        cancelSpan.classList.add('cancel-s');
        cancelSpan.innerHTML = '&times;';

        subCountBtn.classList.add(`m-sub-count-${option_id}`);
        countInput.classList.add(`m-count-${option_id}`);
        addCountBtn.classList.add(`m-add-count-${option_id}`);
        countInput.value = 1;

        subCountBtn.textContent = '-';
        addCountBtn.textContent = '+';

        selectedOptionList.push({ item_id: id, item_option_id: option_id, count: 1 });

        subCountBtn.addEventListener('click', () => {
            if (Number(countInput.value) < 2) {
                /* alert('수량은 1개 이상이어야 합니다.'); */
                modalInModal.appendChild(createModal(modalInModal, 'lack'));
                modalInModal.classList.add('display');
            } else {
                countInput.value = Number(countInput.value) - 1;
                for (let i = 0; i < selectedOptionList.length; i++) {
                    if (selectedOptionList[i].item_option_id === option_id) {
                        selectedOptionList[i].count = Number(countInput.value);
                    }
                }
            }
        });
        addCountBtn.addEventListener('click', () => {
            if (Number(countInput.value) >= quantity) {
                modalInModal.appendChild(createModal(modalInModal, 'excess'));
                modalInModal.classList.add('display');
                /* alert('재고 상품보다 많이 담을 수 없습니다.'); */
            } else {
                countInput.value = Number(countInput.value) + 1;
                for (let i = 0; i < selectedOptionList.length; i++) {
                    if (selectedOptionList[i].item_option_id === option_id) {
                        selectedOptionList[i].count = Number(countInput.value);
                    }
                }
            }
        });
        cancelSpan.addEventListener('click', () => {
            countInput.value = 0;
            selectedOption.childNodes.forEach(option => {
                if (option === sOptionDiv) {
                    selectedOption.removeChild(option);
                }
            });
            for (let i = 0; i < selectedOptionList.length; i++) {
                if (selectedOptionList[i].item_option_id === option_id) {
                    selectedOptionList.splice(i, 1);
                }
            }
        });

        countDiv.appendChild(subCountBtn);
        countDiv.appendChild(countInput);
        countDiv.appendChild(addCountBtn);
        countDiv.appendChild(cancelSpan);

        sOptionDiv.appendChild(nameSpan);
        sOptionDiv.appendChild(countDiv);

        selectedOption.appendChild(sOptionDiv);
    }

    cancelSpan.addEventListener('click', () => {
        const wrapContainer = document.getElementById('wrap');
        modalContainer.style.display = 'none';
        const tbody = document.querySelector('tbody');
        // wrapContainer.removeChild(document.getElementById('optionScript'));
        tbody.innerHTML = '';
        selectedList = [];
        selectedOptionList = [];
        selectedOption.innerHTML = '';
    });

    optionSelect.addEventListener('click', () => {
        optionRows.forEach(row => row.style.display = 'block');
        selectedOption.style.display = 'none';
        optionSelectTr.style.opacity = 0;
    });

    foldOptionBtn.addEventListener('click', () => {
        console.log('twice?');
        if (foldOptionBtn.classList.contains('rotate')) {
            optionContainer.classList.add('fold-bar');
            optionContainer.classList.remove('unfold-bar');
            setTimeout(() => {
                foldOptionBtn.classList.remove('rotate');
                optionContainer.style.display = 'none';
            }, 200);
        } else {
            optionContainer.classList.add('unfold-bar');
            optionContainer.style.display = 'table';
            optionContainer.classList.remove('fold-bar');
            foldOptionBtn.classList.add('rotate');
        }
    });


    for (let i = 0; i < quantityList.length; i++) {
        if (!quantityList[i]) {
            console.log(tbody.children[i].children[0], i);
            tbody.children[i].children[0].children[0].style.color = 'gray';
            const soldoutBtn = document.createElement('button');
            soldoutBtn.classList.add('soldout-btn');
            soldoutBtn.textContent = '품절';
            tbody.children[i].appendChild(soldoutBtn);
        } else {
            tbody.children[i].children[0].addEventListener('click', () => {
                if (!selectedList.includes(tbody.children[i].children[0].textContent.trim())) {
                    const item_name = tbody.children[i].children[0].textContent.trim();
                    selectedList.push(item_name);
                    addOption(item_id, optionIDList[i], quantityList[i], item_name);
                }

                optionRows.forEach(row => row.style.display = 'none');
                optionSelectTr.style.opacity = 1;
                selectedOption.style.display = 'block';
            });
        }
    }
}