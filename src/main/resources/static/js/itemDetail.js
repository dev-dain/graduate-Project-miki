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

        // (Object.keys(data))
        try {
            for (let i = 0; i <= data.ItemDetail.length; i++) {
                console.log(data.ItemDetail[i].image);
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
        const wrapContainer = document.getElementById('wrap');
        const storeId = localStorage.getItem('store_id');

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
                alert('제일 첫 페이지입니다.');
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
                alert('제일 마지막 페이지입니다.');
            } else {
                localStorage.setItem(`item-id-${item_id}`, Number(localStorage.getItem(`item-id-${item_id}`)) + 1);
                console.log(localStorage.getItem(`item-id-${item_id}`));
                imgContainer.innerHTML = '';
                imgContainer.appendChild(getImg(localStorage.getItem(`item-id-${item_id}`), item_name));
            }
        });


        const script = document.createElement('script');
        script.src = '/js/itemOption.js';
        footer.addEventListener('click', () => {
            modalContainer.style.display = 'flex';

            // 옵션 선택 창이 나와 있지 않은 상태라면 script 추가
            if (wrapContainer.lastElementChild.tagName !== 'SCRIPT') {
                wrapContainer.appendChild(script);
            }
            try {
                if (modalContainer.children[1].children[1].children[1].lastElementChild.children[0].hasChildNodes()) {
                    /* 계속 쇼핑 / 장바구니 가기 모달 추가로 띄우기 필요 */
                    //뭐야..???
                    selectedOptionList.forEach(option => {
                        // console.log(option);
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
                                alert('장바구니에 담겼습니다.');
                                setTimeout(() => location.reload(), 0);
                            })
                            .catch(e => console.error(e));
                    });
                }

                modalContainer.style.display = 'none';
                modalContainer.innerHTML = '';
                wrapContainer.lastElementChild.remove();
                script.innerHTML = '';
            } catch (e) {
                console.error(e);
            }
        });

    })
