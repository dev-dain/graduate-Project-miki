interface Category {
  name: string,
  category: CategoryInfo[], 
  size: string
}

interface CategoryInfo {
  categoryId: string,
  categoryName: string
}

let curCategory: CategoryInfo[], searchRes: Category, count: string, bigName: string;
let num: string = decodeURI(location.pathname.split('/')[2]);

const fetchAPI = async () => {
  const res = await fetch(`/dev/category/${num}`);
  const data: string = await res.text();
  searchRes = (JSON.parse(data));
  bigName = searchRes['name'];
  count = searchRes['size'];
  curCategory = searchRes['category'];
  console.log(count, curCategory);
  const script: HTMLScriptElement = document.createElement('script');
  script.src = '/js/category.js';
  document.body.appendChild(script);
};

fetchAPI()
  .catch(e => console.error(e));