var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
let curCategory, searchRes, count, bigName;
let num = decodeURI(location.pathname.split('/')[2]);
const fetchAPI = () => __awaiter(void 0, void 0, void 0, function* () {
    const res = yield fetch(`/dev/category/${num}`);
    const data = yield res.text();
    searchRes = (JSON.parse(data));
    bigName = searchRes['name'];
    count = searchRes['size'];
    curCategory = searchRes['category'];
    console.log(count, curCategory);
    const script = document.createElement('script');
    script.src = '/js/category.js';
    document.body.appendChild(script);
});
fetchAPI()
    .catch(e => console.error(e));
