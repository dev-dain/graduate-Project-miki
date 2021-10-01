var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
const currentPage = '';
// titleContainer의 title 정하기
const h1Title = document.querySelector('.title-header');
h1Title.textContent = bigName;
// 문서의 title 정하기
const docTitle = document.getElementsByTagName('title')[0];
docTitle.textContent = `카테고리 : ${bigName}`;
let count = 0;
let maxNum = 0;
let sortWay = localStorage.getItem('sort-way') || 'id';
const goPrevBtn = document.querySelector('.go-prev');
const micBtn = document.querySelector('.turn-on-mic');
const cartBtn = document.querySelector('.go-cart');
goPrevBtn.addEventListener('click', () => { history.back(); });
micBtn.addEventListener('click', () => { location.href = '/searchVoice'; });
cartBtn.addEventListener('click', () => {
    location.href = `/cartList/${localStorage.getItem('store_id')}`;
});
// sortContainerFunc.js import 필요
const sortContainer = document.querySelector('.sort-container');
sortContainerFunc(sortContainer, '');
const modalContainer = document.querySelector('.modal-container');
const itemContainer = document.querySelector('.item-container');
const tbody = document.querySelector('tbody');
checkPage('category');
;
// 중카테고리, 소카테고리 이름과 번호 가져오는 fetch
const fetchData = (pageNum, category, sortWay) => {
    console.log(sortWay, pageNum);
    let itemObjList = [];
    fetch(`/dev/category/${category}/itemList?pageNum=${pageNum}&sort=${sortWay}`)
        .then(res => res.text())
        .then(data => {
            console.log(data);
            itemObjList = (JSON.parse(data))['ItemList'];
            maxNum = (Math.ceil(Number((JSON.parse(data))['size']) / 9)).toString();
            localStorage.setItem('categoryMax', maxNum);
            tbody.innerHTML = '';
            itemObjList.forEach(function (item) {
                tbody.appendChild(createItemCard(item));
            });
        })
        .catch(e => console.error(e));
};
const fetchCategory = (pageNum, category, sortWay) => {
    let categoryList = [];
    fetch(`/dev/category/${category}`)
        .then(res => res.text())
        .then(data => {
            categoryList = (JSON.parse(data))['category'];
            fetchData(pageNum, category, sortWay);
        })
        .catch(e => console.error(e));
};
fetchCategory('1', num, localStorage.getItem('sort-way'));
console.log(localStorage.getItem('categoryMax'));
const midCatBar = document.querySelector('.mid-category');
const smallCat = document.querySelector('.small-category');
const smallCatBar = document.querySelector('.cat-button-container');
// // 중카테고리 요소 생성해서 mid-category 자식으로 추가
console.log('why' + JSON.stringify(curCategory));
for (let i = 0; i < curCategory.length; i++) {
    const midBtn = document.createElement('button');
    midBtn.classList.add('mid-btn', 'cat');
    midBtn.setAttribute('data-id', curCategory[i].categoryId);
    midBtn.textContent = curCategory[i]['categoryName'];
    midBtn.addEventListener('click', () => {
        console.log(curCategory[i].categoryId);
        const catId = curCategory[i].categoryId;
        localStorage.setItem('page', '1');
        fetchData('1', curCategory[i].categoryId, sortWay);
    });
    midCatBar.appendChild(midBtn);
}
const midCatList = document.querySelectorAll('.mid-btn');
for (let i = 0; i < midCatList.length; i++) {
    // 중 카테고리의 각 요소에 onclick 이벤트 핸들러 붙이기
    midCatList[i].addEventListener('click', () => __awaiter(this, void 0, void 0, function* () {
        num = midCatList[i].getAttribute('data-id');
        midCatList.forEach(otherC => {
            // 만약 다른 요소가 selected된 상황이라면 selected 지우기
            if (otherC.classList.contains('cat-selected')) {
                otherC.classList.remove('cat-selected');
                // 만약 다른 중카테고리를 보다가 새로운 중 카테고리를 누른 거라면
                // 하위 요소를 밀어버리고 다시 넣기 위해 smallCatBar 내부를 지워버림
                smallCatBar.innerHTML = '';
            }
        });
        // 선택한 클래스가 selected가 아니라면 selected로 만들기
        if (!midCatList[i].classList.contains('cat-selected')) {
            midCatList[i].classList.add('cat-selected');
        }
        // 선택한 중카테고리에 소 카테고리가 있다면
        const res = yield fetch(`/dev/category/${num}`);
        const data = yield res.text();
        curCategory = (JSON.parse(data))['category'];
        if (curCategory.length) {
            curCategory.forEach(small => {
                const smallBtn = document.createElement('button');
                smallBtn.classList.add('small-btn', 'cat');
                smallBtn.textContent = small.categoryName;
                // 소 카테고리 각 버튼에도 이벤트 핸들러 붙이기
                smallBtn.addEventListener('click', () => {
                    console.log(small.categoryId);
                    localStorage.setItem('page', '1');
                    num = small.categoryId;
                    fetchData('1', small.categoryId, sortWay);
                    for (let j = 0; j < smallCatBar.children.length; j++) {
                        if (smallCatBar.children[j].classList.contains('cat-selected')) {
                            smallCatBar.children[j].classList.remove('cat-selected');
                        }
                    }
                    if (!smallBtn.classList.contains('cat-selected')) {
                        smallBtn.classList.add('cat-selected');
                    }
                    // itemContainer.style.display = 'flex';
                });
                // 소 카테고리 바에 버튼들 붙이기
                smallCatBar.appendChild(smallBtn);
                smallCat.style.display = 'flex';
                smallCatBar.style.display = 'flex';
            });
        }
        else {
            smallCat.style.display = 'none';
        }
    }));
}
const foldBtn = document.querySelector('.fold-btn');
const smallCatList = document.querySelectorAll('.small-btn');
const itemCardList = document.querySelectorAll('.item-card');
smallCat.style.display = 'none';
// itemContainer.style.display = 'none';
foldBtn.addEventListener('click', () => {
    if (foldBtn.classList.contains('fold')) {
        if (smallCatBar.classList.contains('unfold-bar')) {
            smallCatBar.classList.remove('unfold-bar');
        }
        smallCatBar.classList.add('fold-bar');
        setTimeout(() => {
            foldBtn.classList.remove('fold');
            foldBtn.classList.add('unfold');
            foldBtn.classList.add('rotate');
            smallCatBar.style.display = 'none';
        }, 200);
    }
    else {
        if (smallCatBar.classList.contains('fold-bar')) {
            smallCatBar.classList.remove('fold-bar');
        }
        smallCatBar.classList.add('unfold-bar');
        setTimeout(() => {
            foldBtn.classList.remove('unfold');
            foldBtn.classList.remove('rotate');
            foldBtn.classList.add('fold');
            smallCatBar.style.display = 'flex';
        }, 200);
    }
});
