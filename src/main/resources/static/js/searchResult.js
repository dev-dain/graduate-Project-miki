;
const resultItemTitle = document.querySelector('.result-item-title');
resultItemTitle.textContent = keyword;
;
let sortWay = localStorage.getItem('search-sort-way') || 'id';
const wrapContainer = document.getElementById('wrap');
const tbody = document.querySelector('tbody');
const fetchData = (pageNum, keyword, sortWay) => {
    console.log(sortWay);
    let itemObjList = [];
    fetch(`/dev/search?pageNum=${pageNum}&sort=${sortWay}&keyword=${keyword}`)
        .then(res => res.text())
        .then(data => {
            itemObjList = (JSON.parse(data))['Item'];
            itemObjList.forEach(function (item) {
                tbody.appendChild(createItemCard(item));
            });
            console.log(itemObjList);
        })
        .catch(e => console.error(e));
};
const micBtn = document.querySelector('.turn-on-mic');
const cartBtn = document.querySelector('.go-cart');
const goPrevBtn = document.querySelector('.go-prev');
micBtn.addEventListener('click', () => { location.href = '/searchVoice'; });
cartBtn.addEventListener('click', () => {
    location.href = `/cartList/${localStorage.getItem('store_id')}`;
});
goPrevBtn.addEventListener('click', () => { history.back(); });
const itemContainer = document.querySelector('.item-container');
if (count === '0') {
    const sortContainer = document.querySelector('.sort-container');
    sortContainer.style.display = 'none';
    const noResultBox = document.createElement('div');
    noResultBox.classList.add('no-result');
    const noResultPic = document.createElement('img');
    noResultPic.classList.add('no-result-pic');
    noResultPic.src = '/img/information.png';
    const noResultCaution = document.createElement('p');
    noResultCaution.classList.add('no-result-caution');
    noResultCaution.textContent = '현재 취급하지 않는 상품입니다. :(';
    const noResultBtnContainer = document.createElement('div');
    noResultBtnContainer.classList.add('no-result-btn-container');
    const goSearchBtn = document.querySelector('.go-search');
    const goHomeBtn = document.querySelector('.go-home');
    goSearchBtn.classList.add('no-result-btn');
    goSearchBtn.classList.add('go-search');
    goSearchBtn.textContent = '다시 검색하기';
    goSearchBtn.addEventListener('click', () => {
        location.href = '/searchVoice';
    });
    goHomeBtn.classList.add('no-result-btn');
    goHomeBtn.classList.add('go-home');
    goHomeBtn.textContent = '홈으로 가기';
    goHomeBtn.addEventListener('click', () => {
        location.href = '/main';
    });
    noResultBtnContainer.appendChild(goSearchBtn);
    noResultBtnContainer.appendChild(goHomeBtn);
    noResultBox.appendChild(noResultPic);
    noResultBox.appendChild(noResultCaution);
    noResultBox.appendChild(noResultBtnContainer);
    itemContainer.appendChild(noResultBox);
}
else {
    const sortContainer = document.querySelector('.sort-container');
    sortContainerFunc(sortContainer, 'search-');
    fetchData('1', keyword, sortWay);
    const maxNum = (Math.ceil(Number(count) / 9)).toString();
    console.log('maxNum ', maxNum);
    localStorage.setItem('searchMax', maxNum);
    console.log(localStorage.getItem('searchMax'));
    localStorage.setItem('page', '1');
    checkPage('search');
}
