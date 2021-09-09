// titleContainer의 title 정하기
const h1Title: HTMLHeadingElement = document.querySelector('title-header') as HTMLHeadingElement;
// h1Title.textContent = bigName;

// 문서의 title 정하기
const docTitle: HTMLTitleElement = document.getElementsByTagName('title')[0];
// docTitle.textContent = `카테고리 : ${bigName}`;

const maxNum: string = (Math.ceil(Number(count) / 9)).toString();

localStorage.setItem('categoryMax', maxNum);
console.log(localStorage.getItem('categoryMax'));

let sortWay: string = localStorage.getItem('sort-way') || 'id';

const goPrevBtn: HTMLButtonElement = document.querySelector('.go-prev') as HTMLButtonElement;
const micBtn: HTMLButtonElement = document.querySelector('.turn-on-mic') as HTMLButtonElement;
const cartBtn: HTMLButtonElement = document.querySelector('.go-cart') as HTMLButtonElement;

goPrevBtn.addEventListener('click', (): void => { history.back(); });
micBtn.addEventListener('click', (): void => { location.href = '/searchVoice' });
cartBtn.addEventListener('click', () => {
    location.href = `/cartList/${localStorage.getItem('store_id')}`;
});

// sortContainerFunc.js import 필요
const sortContainer: HTMLDivElement = document.querySelector('.sort-container') as HTMLDivElement;
sortContainerFunc(sortContainer, '');

const modalContainer: HTMLDivElement = document.querySelector('.modal-container') as HTMLDivElement;
const itemContainer: HTMLDivElement = document.querySelector('.item-container') as HTMLDivElement;
const tbody: HTMLTableSectionElement = document.querySelector('tbody') as HTMLTableSectionElement;

checkPage('category');

interface itemInfo {
    itemDiscountPrice: string,
    itemId: string,
    itemImage: string,
    itemName: string,
    itemPrice: string,
    itemTestable: string
  };


// 중카테고리, 소카테고리 이름과 번호 가져오는 fetch
const fetchData = (pageNum: string, category: string, sortWay: string): Void => {
    console.log(sortWay);
    let itemObjList: itemInfo[] = [];
  
    fetch(`/dev/category/${category}/itemList?page=${pageNum}&sort=${sortWay}`)
      .then(res => res.text())
      .then(data => {
          console.log(data);
        itemObjList = (JSON.parse(data))['ItemList'];
        tbody.innerHTML = '';
        itemObjList.forEach(function (item) {
          tbody.appendChild(createItemCard(item));
        });
        console.log(itemObjList);
      })
      .catch(e => console.error(e));
}

const fetchCategory = (pageNum: string, category: string, sortWay: string): void => {
    console.log(sortWay);
    let categoryList: itemInfo[] = [];
  
    fetch(`/dev/category/${category}`)
      .then(res => res.text())
      .then(data => {
        categoryList = (JSON.parse(data))['category'];
        console.log(categoryList);
        fetchData(pageNum, category, sortWay);
      })
      .catch(e => console.error(e));
}

fetchCategory('1', num, localStorage.getItem('sort-way') as string);



const midCatBar: HTMLDivElement = document.querySelector('.mid-category') as HTMLDivElement;
const smallCat: HTMLDivElement = document.querySelector('.small-category') as HTMLDivElement;
const smallCatBar: HTMLDivElement = document.querySelector('.cat-button-container') as HTMLDivElement;

// // 중카테고리 요소 생성해서 mid-category 자식으로 추가
curCategory.forEach(function (mid: CategoryInfo) {
    const midBtn = document.createElement('button');
    midBtn.classList.add('mid-btn', 'cat');
    midBtn.setAttribute('data-id', mid.categoryId);
    midBtn.textContent = mid['categoryName'];
    midBtn.addEventListener('click', () => {
        console.log(mid.categoryId);
        const catId: string = mid.categoryId;
        localStorage.setItem('page', '1');
        fetchData('1', mid.categoryId, sortWay);
    });

    midCatBar.appendChild(midBtn);
});


const midCatList = document.querySelectorAll('.mid-btn');

for (let i = 0; i < midCatList.length; i++) {
    // 중 카테고리의 각 요소에 onclick 이벤트 핸들러 붙이기
    midCatList[i].addEventListener('click', async () => {
        num = midCatList[i].getAttribute('data-id') as string;
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
        const res = await fetch(`/dev/category/${num}`);
        const data = await res.text();
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
        } else {
            smallCat.style.display = 'none';
        }
    });
}


const foldBtn: HTMLButtonElement = document.querySelector('.fold-btn') as HTMLButtonElement;
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