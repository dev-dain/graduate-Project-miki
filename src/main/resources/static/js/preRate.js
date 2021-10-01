var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
;
let reviewData;
let itemId = decodeURI(location.pathname.split('/')[2]);
let rateCnt;
let itemName;
let isTestable;
const fetchAPI = () => __awaiter(this, void 0, void 0, function* () {
    let res = yield fetch(`/dev/item/${itemId}/review`);
    let data = yield res.text();
    reviewData = (JSON.parse(data))['Review'];
    rateCnt = reviewData.length;
    console.log(reviewData);
    res = yield fetch(`/dev/item/${itemId}`);
    data = yield res.text();
    itemName = (JSON.parse(data))['Item'].itemName;
    isTestable = (JSON.parse(data))['Item'].itemTestable;
    const titleH1 = document.querySelector('.title');
    titleH1.textContent = itemName;
    const rateCountP = document.querySelector('.rate-count');
    rateCountP.textContent = `총 ${rateCnt} 건`;
    const script = document.createElement('script');
    script.src = '/js/review.js';
    document.body.appendChild(script);
});
fetchAPI()
    .catch(e => console.error(e));
