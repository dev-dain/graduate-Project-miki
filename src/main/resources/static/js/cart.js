const selectAllBtn = document.querySelector('.select-all-btn');
let isSelectAll = true;

selectAllBtn.addEventListener('click', () => {
  isSelectAll = !isSelectAll;
  selectAllBtn.innerHTML = (isSelectAll) ? '&#10004;' : '';
});