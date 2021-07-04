// 카테고리 받아오는 과정
const j_list = JSON.parse(JSON.stringify(List));
const list_num = ((window.location.href).split('/')).slice(-1)[0].split('?')[0];
const j_midList = j_list[list_num].MediumCategory;

// titleContainer의 title 정하기
const titleContainer = document.querySelector('.title-container');
const h1Title = document.createElement('h1');
h1Title.textContent = j_list[list_num].title;
titleContainer.appendChild(h1Title);

// 문서의 title 정하기
const docTitle = document.getElementsByTagName('title')[0];
docTitle.textContent = `카테고리 : ${h1Title.textContent}`;

const goPrevBtn = document.querySelector('.go-prev');
const mic = document.querySelector('.turn-on-mic');
const cart = document.querySelector('.go-cart');

goPrevBtn.addEventListener('click', () => { history.back(); });
mic.addEventListener('click', () => location.href = '/searchVoice');
cart.addEventListener('click', () => {
    location.href = `/cartList/${localStorage.getItem('store_id')}`;
});

const fetchData = (curCategory, sortWay) => {
    console.log(sortWay);
    fetch(`/category/${curCategory}?sort=${sortWay}`)
        .then(res => res.text())
        .then(data => {
            const itemRowContainer = document.createElement('div');
            itemRowContainer.classList.add('item-container');
            let itemContainer = data.split('<div class="item-container">')[1].split('<script>')[0];
            itemRowContainer.innerHTML = itemContainer;
            wrapContainer.removeChild(wrapContainer.lastElementChild);
            wrapContainer.appendChild(itemRowContainer);

            localStorage.setItem('categoryMax', document.querySelector('.maxNum').textContent);
            console.log('category ', localStorage.getItem('categoryMax'));

        })
        .catch(e => console.error(e));
}



const midCatBar = document.querySelector('.mid-category');
const smallCatBar = document.querySelector('.cat-button-container');
const j_smallCatList = [];

// 중카테고리 요소 생성해서 mid-category 자식으로 추가
j_midList.forEach(mid => {
    const midBtn = document.createElement('button');
    midBtn.classList += 'mid-btn cat';
    midBtn.textContent = mid.M_category_name;
    midBtn.addEventListener('click', () => {
        console.log(mid.category_id);
        curCategory = mid.category_id;
        localStorage.setItem('page', 1);
        fetchData(curCategory, sortWay);
    });

    if (mid.SmallCategory) {
        j_smallCatList.push(JSON.parse(JSON.stringify(mid.SmallCategory)));
    } else {
        j_smallCatList.push('');
    }

    midCatBar.appendChild(midBtn);
});


const midCatList = document.querySelectorAll('.mid-btn');

for (let i = 0; i < midCatList.length; i++) {
    // 중 카테고리의 각 요소에 onclick 이벤트 핸들러 붙이기
    midCatList[i].addEventListener('click', () => {

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
        if (j_smallCatList[i].length) {
            // 소 카테고리 버튼을 만들어 새로 붙이기
            j_smallCatList[i].forEach(small => {
                const smallBtn = document.createElement('button');
                smallBtn.classList += 'small-btn cat';
                smallBtn.textContent = small.S_category_name;

                // 소 카테고리 각 버튼에도 이벤트 핸들러 붙이기
                smallBtn.addEventListener('click', () => {
                    console.log(small.category_id);
                    localStorage.setItem('page', 1);
                    curCategory = small.category_id;

                    fetchData(curCategory, sortWay);


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

                smallCatBar.parentNode.style.display = 'flex';
                smallCatBar.style.display = 'flex';
            });
        } else {
            // 소 카테고리가 없는 경우, smallCatBar 자체를 보여주지 않음
            smallCatBar.parentNode.style.display = 'none';
        }
    });
}


const foldBtn = document.querySelector('.fold-btn');
const smallCatList = document.querySelectorAll('.small-btn');
const itemContainer = document.querySelector('.item-container');
const itemCardList= document.querySelectorAll('.item-card');

const sortContainer = document.querySelector('.sort-container');
const sortBtnPop = document.querySelector('.sort-btn-popularity');
const sortBtnOrder = document.querySelector('.sort-btn-order-cnt');
const sortBtnReview = document.querySelector('.sort-btn-review-cnt');

smallCatBar.parentNode.style.display = 'none';
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
    } else {
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



for (let i = 0; i < sortContainer.children.length; i++) {
    sortContainer.children[i].addEventListener('click', () => {
        for (let j = 0; j < sortContainer.children.length; j++) {
            if (sortContainer.children[j].classList.contains('selected-btn')) {
                if (i === j) return;
                sortContainer.children[j].classList.remove('selected-btn');
            }
        }
        localStorage.setItem('sort-way', sortContainer.children[i].className.split('-')
            [sortContainer.children[i].className.split('-').length - 1]);
        sortWay = localStorage.getItem('sort-way');
        sortContainer.children[i].classList.add('selected-btn');

        localStorage.setItem('page', 1);
        fetchData(curCategory, sortWay);
    });
}