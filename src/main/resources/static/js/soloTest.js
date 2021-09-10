const item_obj = {};
const option_obj = {};
const optionIdList = [];
let first_option;
let c_arr = [0, 0, 0]; // R,G,B 값 담아두는 배열

let img_src;
let item_name;
let item_id;
let item_option_id;
let option_name;
let textNode;

let position = -1;
let alpha = 0;
let h_color;

let is_L = false;
let is_C = false;
let is_B = false;

let L_R, L_G, L_B;
let C_R, C_G, C_B;
let B_R, B_G, B_B;

// hex to RGB
let R, G, B;

const url = location.pathname.split('/')[2];
fetch(`/dev/testSingleColor?itemId=${url}`)
    .then(res => res.text())
    .then(data => {
        // console.log(data);
        data = JSON.parse(data);

        item_id = data.Item.itemId;
        img_src = data.Item.itemImage;
        item_name = data.Item.itemName;

        for (let i = 0; i <= (Object.keys(data.Item).length + 1); i++) {
            item_option_id = data.Item.testOption[i].optionId;
            optionIdList.push(item_option_id);
            option_obj[item_option_id] = data.Item.testOption[i].optionName;

            item_obj[item_option_id] = {
                parent_id: item_id,
                id: item_option_id,
                name: option_obj[item_option_id],
                face_location: data.Item.testOption[i].optionFaceLocation,
                c_code: data.Item.testOption[i].optionColor,
                alpha: data.Item.testOption[i].optionColorAlpha
            }
            if (i == 0) {
                first_option = data.Item.testOption[i].optionColor;
                console.log(first_option);
            }
        }

        c_arr = hexToRgb(first_option);
        if (item_obj[item_option_id].face_location == "L") {
            L_R = c_arr[0];
            L_G = c_arr[1];
            L_B = c_arr[2];
            is_L = true;
            console.log('L상품');

        } else if (item_obj[item_option_id].face_location == "C") {
            C_R = c_arr[0];
            C_G = c_arr[1];
            C_B = c_arr[2];
            is_C = true;
            console.log('C상품');
        } else if (item_obj[item_option_id].face_location == "C") {
            B_R = c_arr[0];
            B_G = c_arr[1];
            B_B = c_arr[2];
            is_B = true;
            console.log('B상품');
        }

        // const itemOptionCount = optionIdList.length;

        const itemContainer = document.querySelector('.item-container');
        const itemDiv = document.createElement('div');
        itemDiv.classList.add(`item-div-${item_id}`);

        const itemCheck = document.createElement('button');
        let isItemCheck = true;
        itemCheck.classList.add(`item-check-btn-${item_id}`);
        itemCheck.innerHTML = '&#10004;';
        itemCheck.addEventListener('click', () => {
            if (isItemCheck) {
                itemCheck.innerHTML = '';
                isItemCheck = false;
            } else {
                itemCheck.innerHTML = '&#10004;';
                isItemCheck = true;
            }
        });


        const pos = 'N'; // 어느 부위인지 확인할 변수


        function hexToRgb(hex) {
            var values = hex.split(''), r, g, b;

            r = parseInt(values[0].toString() + values[1].toString(), 16);
            g = parseInt(values[2].toString() + values[3].toString(), 16);
            b = parseInt(values[4].toString() + values[5].toString(), 16);

            return [r, g, b];
        }

        let getClass;
        let split_C;
        let first_c = [];

        const createItemOptionContainer = item_id => {
            const itemOptionContainer = document.createElement('div');
            itemOptionContainer.classList.add(`item-option-container-${item_id}`);

            optionIdList.forEach(optionId => {
                const option = document.createElement('button');
                option.classList.add(`item_options`);
                option.classList.add(`item-option-${item_id}-${optionId}`);
                option.textContent = item_obj[optionId].name;
                option.addEventListener('click', () => {
                    getClass = option.getAttribute('class');
                    split_C = split(getClass, '-'); // split_C[3] : optionId
                    // 컬러코드를 불러와서 sketch.js에 있는 다시 그리는 함수를 호출
                    set_color(split_C);
                    // console.log(c_arr,R,G,B);
                });
                itemOptionContainer.appendChild(option);
            });

            return itemOptionContainer;
        };


        $(document).ready(function () {
            console.log('ready 진입');
            getClass = document.getElementsByClassName(`item_options`);
            first_c = getClass[0].classList[1];
            console.log(first_c);
            split_C = split(first_c, '-');
            console.log(split_C);
            set_color(split_C);
        });


        function set_color(split_C) {
            position = item_obj[split_C[3]].face_location;
            console.log(position);
            <!--        alpha = item_obj[split_C[3]].alpha;-->
            alpha = 0.8;
            h_color = item_obj[split_C[3]].c_code;
            console.log(h_color);
            c_arr = hexToRgb(h_color);
            R = c_arr[0];
            G = c_arr[1];
            B = c_arr[2];

            switch (position) {
                case 'L':
                    is_L = true;
                    L_R = c_arr[0];
                    L_G = c_arr[1];
                    L_B = c_arr[2];
                    console.log('L 진입');
                    break;
                case 'C':
                    is_C = true;
                    C_R = c_arr[0];
                    C_G = c_arr[1];
                    C_B = c_arr[2];
                    console.log('C 진입');
                    break;
                case 'B':
                    is_B = true;
                    B_R = c_arr[0];
                    B_G = c_arr[1];
                    B_B = c_arr[2];
                    console.log('B 진입');
                    break;
            }
        }


        const itemOptionContainer = createItemOptionContainer(item_id);

        const itemImgContainer = document.createElement('div');
        itemImgContainer.classList.add(`item-img-container-${item_id}`);
        const itemImg = document.createElement('img');
        itemImg.classList.add(`item-img-${item_id}`);
        itemImg.src = img_src;

        itemImgContainer.appendChild(itemImg);

        itemImgContainer.addEventListener('click', () => {
            console.log('clicked');
            if (itemOptionContainer.classList.contains('show')) {
                itemOptionContainer.classList.remove('show');
            } else {
                itemOptionContainer.classList.add('show');
            }
        });

        const itemTitle = document.createElement('div');
        itemTitle.classList.add(`item-title-${item_id}`);
        itemTitle.textContent = item_name;

        itemDiv.appendChild(itemCheck);
        itemDiv.appendChild(itemImgContainer);
        itemDiv.appendChild(itemTitle);
        itemDiv.appendChild(itemOptionContainer);

        itemContainer.appendChild(itemDiv);

        const goPrevBtn = document.querySelector('.go-prev');
        const goCartBtn = document.querySelector('.go-cart');

        goPrevBtn.addEventListener('click', () => history.back());
        goCartBtn.addEventListener('click', () => location.href = `/cartList/${localStorage.getItem('store_id')}`);

        const addItemBtn = document.querySelector('.add-item');
        const payItemBtn = document.querySelector('.pay-item');

        addItemBtn.addEventListener('click', () => location.href = '/categoryList');
        payItemBtn.addEventListener('click', () => location.href = '/cartList');

    })

