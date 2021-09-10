const BestItemObj = {};
const BestItemList = [];
let b_item_id = '';
const ItemIdList = [];

fetch(`/dev/bestSeller`)
    .then(res => res.text())
    .then(data => {
        console.log(data);
        data = JSON.parse(data);
        try {
            for (let i = 0; i < data.Item.length; i++) {
                b_item_id = data.Item[i].itemId;
                ItemIdList.push(b_item_id);
                BestItemObj[b_item_id] = {
                    name: data.Item[i].itemName,
                    item_img: data.Item[i].itemImage,
                    origin_price: data.Item[i].itemPrice,
                    discount_price: data.Item[i].itemDiscountPrice,
                    is_testable: data.Item[i].itemTestable
                };

                document.getElementById(`${i}-img`).src = BestItemObj[b_item_id].item_img;
                document.getElementById(`${i}-img`).alt = BestItemObj[b_item_id].name;

                document.getElementById(`${i}-name`).addEventListener('click', function () {
                    goItemPage(ItemIdList[i]);
                });

                document.getElementById(`${i}-title`).textContent = BestItemObj[b_item_id].name;
                document.getElementById(`${i}-title`).addEventListener('click', function () {
                    goItemPage(ItemIdList[i]);
                });

                if (BestItemObj[b_item_id].origin_price != BestItemObj[b_item_id].discount_price) {
                    document.getElementById(`${i}-origin-price`).textContent = BestItemObj[b_item_id].origin_price;
                }
                document.getElementById(`${i}-discount-price`).textContent = `${BestItemObj[b_item_id].discount_price} 원`;

                if (BestItemObj[b_item_id].is_testable == 'Y') {
                    document.getElementById(`${i}-go-title`).addEventListener('click', function () {
                        goItemPage(ItemIdList[i]);
                    });
                }
                else {
                    document.getElementById(`${i}-go-test`).style.display = "none";
                }

            }

        } catch (error) {
            console.error(error);
        }
    })
const goItemPage = id => location.href = `/item/${id}`;


const mdItemObj = {};
const mdItemList = [];
let item_id = '';

fetch(`/dev/mdItem`)
    .then(res => res.text())
    .then(data => {
        r_data = JSON.parse(data);
        itemCount = r_data['Item'].length;

        for (let i = 0; i < itemCount; i++) {
            item_id = r_data.Item[i].itemId;
            mdItemList.push(item_id);
            mdItemObj[item_id] = {
                name: r_data.Item[i].itemName,
                item_img: r_data.Item[i].itemImage,
                origin_price: r_data.Item[i].itemPrice,
                discount_price: r_data.Item[i].itemDiscountPrice,
                is_testable: r_data.Item[i].itemTestable
            };
        }

        console.log(mdItemObj);

        /* (다인언니....) 페이징 처리 부분을 어떻게 해결해야할지 모르겠씸다.. help...*/
        const goPrevBtn = document.querySelector('.go-prev');
        const turnOnMicBtn = document.querySelector('.turn-on-mic');
        const goCartBtn = document.querySelector('.go-cart');
        goPrevBtn.addEventListener('click', () => history.back());
        turnOnMicBtn.addEventListener('click', () => location.href = '/searchVoice');
        goCartBtn.addEventListener('click', () => location.href = `/cartList/${localStorage.getItem('store_id')}`);

        const createMdItemCard = (item_id, state) => {
            const itemCard = document.createElement('div');
            itemCard.classList.add('md');
            itemCard.classList.add('item-card');
            // if (state !== 'hidden') {
            //     itemCard.classList.add(state);
            // }

            const cardImg = document.createElement('div');
            cardImg.classList.add('card-img');
            cardImg.addEventListener('click', item_id => goItemPage(item_id));
            const thumbnailImg = document.createElement('img');
            thumbnailImg.classList.add('thumbnail-img');
            thumbnailImg.src = mdItemObj[item_id].item_img;
            thumbnailImg.alt = mdItemObj[item_id].name;
            cardImg.appendChild(thumbnailImg);
            itemCard.appendChild(cardImg);

            const hrDiv = document.createElement('div');
            const divideImgInfoHr = document.createElement('hr');
            divideImgInfoHr.classList.add('divide-img-info');
            hrDiv.appendChild(divideImgInfoHr);
            itemCard.appendChild(hrDiv);

            const itemTitle = document.createElement('div');
            itemTitle.classList.add('item-title');
            itemTitle.addEventListener('click', item_id => goItemPage(item_id));
            itemTitle.textContent = mdItemObj[item_id].name;
            itemCard.appendChild(itemTitle);

            if (mdItemObj[item_id].discount_price !== mdItemObj[item_id].origin_price) {
                const originPrice = document.createElement('div');
                originPrice.classList.add('origin-price');
                originPrice.textContent = `${mdItemObj[item_id].origin_price} 원`;
                itemCard.appendChild(originPrice);
            }

            const discountPrice = document.createElement('div');
            discountPrice.classList.add('discount-price');
            discountPrice.textContent = `${mdItemObj[item_id].discount_price} 원`;
            itemCard.appendChild(discountPrice);

            if (mdItemObj[item_id].is_testable === 'Y') {
                const goTestDiv = document.createElement('div');
                const goTestBtn = document.createElement('button');
                goTestBtn.classList.add('go-test');
                goTestBtn.addEventListener('click', item_id => goItemPage(id));
                goTestBtn.textContent = '테스트';
                goTestDiv.appendChild(goTestBtn);
                itemCard.appendChild(goTestDiv);
            }

            return itemCard;
        }


        const mdItemCardObj = {};
        const mdItemCardList = [];
        for (let i = 0; i < mdItemList.length; i++) {
            mdItemCardObj[mdItemList[i]] = {
                card: createMdItemCard(mdItemList[i], 'hidden'),
                index: i,
                state: 'hidden'
            };
            mdItemCardList.push(createMdItemCard(mdItemList[i], 'hidden'));
        }
        console.log(mdItemCardObj);

        const goItemPage = id => location.href = `/item/${id}`;

        const mdGoPrevBtn = document.querySelector('.md-go-prev-btn');
        const mdGoNextBtn = document.querySelector('.md-go-next-btn');
        const mdCarousel = document.querySelector('.md-carousel');

        const sideCard = (card, state) => {
            if (card.classList.contains('center')) {
                card.classList.remove('center');
            }
            card.classList.add(state);
            card.classList.add('side');
            return card;
        };

        const centerCard = card => {
            if (card.classList.contains('left')) {
                card.classList.remove('left');
            } else if (card.classList.contains('right')) {
                card.classList.remove('right');
            }

            if (card.classList.contains('side')) {
                card.classList.remove('side');
            }

            card.classList.add('center');
            return card;
        }

        mdItemCardList[0] = sideCard(mdItemCardList[0], 'left');
        mdItemCardList[1] = centerCard(mdItemCardList[1]);
        mdItemCardList[2] = sideCard(mdItemCardList[2], 'right');

        mdCarousel.appendChild(mdItemCardList[0]);
        mdCarousel.appendChild(mdItemCardList[1]);
        mdCarousel.appendChild(mdItemCardList[2]);

        let displayIdx = 1;
        let leftIdx, centerIdx, rightIdx;

        const moveMdCarousel = (display_idx, num) => {
            displayIdx = (display_idx + num) % itemCount;
            if (displayIdx < 0) {
                displayIdx = displayIdx + itemCount;
            }
            console.log(displayIdx);
            leftIdx = ((displayIdx - 1) < 0) ? displayIdx - 1 + itemCount : displayIdx - 1;
            centerIdx = displayIdx;
            rightIdx = (displayIdx + 1) % itemCount;
            mdCarousel.innerHTML = '';

            mdItemCardList[leftIdx] = sideCard(mdItemCardList[leftIdx], 'left');
            mdItemCardList[centerIdx] = centerCard(mdItemCardList[centerIdx]);
            mdItemCardList[rightIdx] = sideCard(mdItemCardList[rightIdx], 'right');

            mdCarousel.appendChild(mdItemCardList[leftIdx]);
            mdCarousel.appendChild(mdItemCardList[centerIdx]);
            mdCarousel.appendChild(mdItemCardList[rightIdx]);
        };

        mdGoPrevBtn.addEventListener('click', () => moveMdCarousel(displayIdx, -1));
        mdGoNextBtn.addEventListener('click', () => moveMdCarousel(displayIdx, 1));
    })