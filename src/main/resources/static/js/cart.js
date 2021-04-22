const cartItemList = [
  {
    item_id: 1,
    item_img: 'http://mimg.lalavla.com/resources/images/prdimg/20210223/10005110/10005110_D_004_550.jpg',
    item_name: '포렌코즈 베어 섀도우 팔레트',
    item_price: '20,000원',
    item_discountPrice: '12,600원',
    item_optionList = [
      '포렌코즈 베어 섀도우 팔레트'
    ],
    item_selected: true
  }
];

const goPrevBtn = document.querySelector('.go-prev');
const selectAllBtn = document.querySelector('.select-all-btn');
let isSelectAll = true;

goPrevBtn.addEventListener('click', () => { history.back(); });
selectAllBtn.addEventListener('click', () => {
  isSelectAll = !isSelectAll;
  selectAllBtn.innerHTML = (isSelectAll) ? '&#10004;' : '';
  //////////////// 상품들 전체 select/unselect 로직 필요
  //
  ////////////////
});


const craeteItemRow = item => {
  
}



const itemRowContainer = document.querySelector('.item-row-container');
const itemRowList = [];
cartItemList.forEach(item => {
  itemRowList.push(createItemRow(item));
});