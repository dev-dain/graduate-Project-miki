const mic = document.querySelector('.turn-on-mic');
const goPrevBtn = document.querySelector('.go-prev');

goPrevBtn.addEventListener('click', () => history.back());
mic.addEventListener('click', () => location.href = './searchVoice' );


const curLocation = document.querySelector('.cur-location');
//////////////////////
// 미아사거리점으로 일단 고정
//////////////////////
const curStoreName = '미아사거리점';
curLocation.innerHTML = `
    <span class="emphasize">${curStoreName}</span> 과 가까운<br/>
    이웃 매장들을 보여 드려요.
`;

const storeListContainer = document.querySelector('.store-list-container');

let storeList = [];
// fetch('/store?store_id=23')
//     .then(res => res.json())
//     .then(res => res.forEach(store => storeList.push(store)))
//     .catch(e => console.error(e));

storeList = [
    {
        store_id: '20',
        store_name: '성신여대점',
        // 도로명 주소도 필요하겠네요 store_location이 필요함
        store_location: '서울시 성북구 돈암로 64',
        store_number: '02-783-0529',
        store_latitude: '37.592628',
        store_longtitude: '127.017762'
    },
    {
        store_id: '35',
        store_name: '고대점',
        store_location: '서울시 성북구 안암로3길 25',
        store_number: '02-705-6920',
        store_latitude: '37.585419',
        store_longtitude: '127.029142'
    },
    {
        // 서울에 있는 매장을 더 추가해야겠네요
        store_id: '36',
        store_name: '경희플라자점',
        store_location: '서울시 동대문구 회기동 경희대학교병원 A동 105호',
        store_number: '02-683-0962',
        store_latitude: '37.588503',
        store_longtitude: '127.021068'
    }
];

const getStoreInfo = (index, store) => {
    const storeInfo = document.createElement('div');
    storeInfo.classList += `store-info-${index}`;
    // storeInfo.setAttribute(id, store.store_id);

    const leftInfo = document.createElement('div');
    leftInfo.classList += `left-info-${index}`;
    const rightInfo = document.createElement('div');
    rightInfo.classList += `right-info-${index}`;

    const storeName = document.createElement('p');
    storeName.classList += `store-info-name-${index}`;
    storeName.textContent = store.store_name;
    const storeLocation = document.createElement('p');
    storeLocation.textContent = store.store_location;
    const storeTime = document.createElement('p');
    storeTime.textContent = '10:00 ~ 22:00';
    const storeTel = document.createElement('p');
    storeTel.textContent = store.store_number;

    leftInfo.appendChild(storeName);
    leftInfo.appendChild(storeLocation);
    leftInfo.appendChild(storeTime);
    leftInfo.appendChild(storeTel);

    storeInfo.appendChild(leftInfo);
    storeInfo.appendChild(rightInfo);

    storeInfo.addEventListener('click', () => {
        const bounds = new kakao.maps.LatLngBounds();
        bounds.extend(new kakao.maps.LatLng(store.store_latitude, store.store_longtitude));
        map.setBounds(bounds);
    });

    return storeInfo;
};

const storeInfoList = [];
for (let i = 0; i < storeList.length; i++) {
    storeInfoList.push(getStoreInfo(i, storeList[i]));
}
storeInfoList.forEach(storeInfo => storeListContainer.appendChild(storeInfo));