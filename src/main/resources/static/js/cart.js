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