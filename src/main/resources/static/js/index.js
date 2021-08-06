"use strict";
const storeId = localStorage.getItem('store_id');
const storeName = localStorage.getItem('store_name');
const storeNameP = document.querySelector('.store-name');
storeNameP.textContent = storeName;
storeNameP.addEventListener('click', () => location.href = `/admin-check?store_id=${storeId}`);
const clockH3 = document.querySelector('.clock');
const clockFunc = () => {
    let today = new Date();
    let h = (today.getHours() > 12) ?
        today.getHours() - 12 : today.getHours();
    h = (h < 10) ? '0' + h : h;
    let m = (today.getMinutes() < 10) ?
        '0' + today.getMinutes() : today.getMinutes();
    let amOrPm = (today.getHours() < 12) ? 'AM' : 'PM';
    clockH3.innerHTML = `${amOrPm} ${h}:${m}`;
};
clockFunc();
setInterval(clockFunc, 1000);
if (!localStorage.getItem('store_id')) {
    localStorage.setItem('store_id', '1');
}
