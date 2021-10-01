const url = location.pathname.split('/')[2];
const productImgList = [];
let item_id;
// 선택된 옵션의 id, option_id, count가 객체로 들어갈 리스트
let selectedOptionList = [];


fetch(`/dev/item/${url}/detail`)
    .then(res => res.text())
    .then(async data => {
        console.log(data);
        data = JSON.parse(data);

        item_id = localStorage.getItem('c_item_id');
        const item_isTestable = localStorage.getItem("c_item_isTestable");
        const item_name = localStorage.getItem("c_item_name");

        try {
            for (let i = 0; i < data.ItemDetail.length; i++) {
                productImgList.push(data.ItemDetail[i].image);
            }
        } catch (error) {
            console.error(error);
        }

        document.querySelector('.title').textContent = item_name;

        localStorage.setItem(`item-id-${item_id}`, 0);
        const productImgLen = productImgList.length;

        const btnContainer = document.querySelector('.btn-container');
        createBtnContainer(item_id, item_isTestable).forEach(el =>
            btnContainer.appendChild(el));

        const goPrevBtn = document.querySelector('.go-prev')
        const goItemPrevBtn = document.querySelector('.item-prev-btn');
        const goItemNextBtn = document.querySelector('.item-next-btn');
        const footer = document.querySelector('footer');
        const imgContainer = document.querySelector('.img-container');
        const modalContainer = document.querySelector('.modal-container');
        const modalInModal = document.querySelector('.modal-in-modal');
        const wrapContainer = document.getElementById('wrap');
        const tbody = document.querySelector('tbody');

        const checkFirstPage = page => (page <= 0);
        const checkLastPage = (page, length) => (Number(page) + 1 >= length);
        const getImg = (num, name) => {
            const detailImg = document.createElement('img');
            detailImg.src = productImgList[num];
            detailImg.alt = name;
            detailImg.classList += `detail-img-${num}`;

            return detailImg;
        }

        imgContainer.appendChild(getImg(localStorage.getItem(`item-id-${item_id}`), item_name));

        goPrevBtn.addEventListener('click', () => { history.back(); });

        goItemPrevBtn.addEventListener('click', () => {
            if (checkFirstPage(Number(localStorage.getItem(`item-id-${item_id}`)))) {
                console.log('First Page!');
                modalContainer.appendChild(createModal(modalContainer, 'first'));
                modalContainer.classList.add('display');
            } else {
                localStorage.setItem(`item-id-${item_id}`, Number((localStorage.getItem(`item-id-${item_id}`))) - 1);
                console.log(localStorage.getItem(`item-id-${item_id}`));
                imgContainer.innerHTML = '';
                imgContainer.appendChild(getImg(localStorage.getItem(`item-id-${item_id}`), item_name));
            }
        });

        goItemNextBtn.addEventListener('click', () => {
            if (checkLastPage(localStorage.getItem(`item-id-${item_id}`), productImgLen)) {
                console.log('Last Page');
                modalContainer.appendChild(createModal(modalContainer, 'last'));
                modalContainer.classList.add('display');
            } else {
                localStorage.setItem(`item-id-${item_id}`, Number(localStorage.getItem(`item-id-${item_id}`)) + 1);
                console.log(localStorage.getItem(`item-id-${item_id}`));
                imgContainer.innerHTML = '';
                imgContainer.appendChild(getImg(localStorage.getItem(`item-id-${item_id}`), item_name));
            }
        });

        footer.addEventListener('click', () => {
            modalContainer.style.display = 'flex';

            try {
                if (modalContainer.children[1].children[1].children[1].lastElementChild.children[0].hasChildNodes()) {
                    console.log('here');
                    selectedOptionList.forEach(option => {
                        console.log(option);
                        fetch(`/cart`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json;charset=utf-8'
                            },
                            body: JSON.stringify(option)
                        })
                            .then(res => res.json())
                            .then(data => {
                                console.log(data);
                                modalContainer.appendChild(createModal(modalContainer, '', '장바구니에 담겼습니다.'));
                                modalContainer.classList.add('display');
                                setTimeout(() => location.reload(), 1000);
                            })
                            .catch(e => console.error(e));
                    });
                }

                modalContainer.style.display = 'none';
                modalContainer.innerHTML = '';
            } catch (e) {
                console.error(e);
                itemOption()
                    .catch(e => console.error(e));

            }
        });

    })
