const storeId: string = localStorage.getItem('store_id') as string;
const storeName: string = localStorage.getItem('store_name') as string;

const storeNameP: HTMLParagraphElement = document.querySelector('.store-name') as HTMLParagraphElement;
storeNameP.textContent = storeName;
storeNameP.addEventListener('click', () => location.href = `/admin-check?store_id=${storeId}`);

const clockH3: HTMLHeadingElement = document.querySelector('.clock') as HTMLHeadingElement;
const clockFunc = (): void => {
    let today: Date = new Date();
    let h: number | string = (today.getHours() > 12) ?
            today.getHours() - 12 : today.getHours();
    h = (h < 10) ? '0' + h : h;
    let m: number | string = (today.getMinutes() < 10) ?
            '0' + today.getMinutes() : today.getMinutes();
    let amOrPm: string = (today.getHours() < 12) ? 'AM' : 'PM';
    clockH3.innerHTML = `${amOrPm} ${h}:${m}`;
}

clockFunc();
setInterval(clockFunc, 1000);

if (!localStorage.getItem('store_id')) {
  localStorage.setItem('store_id', '1');
}