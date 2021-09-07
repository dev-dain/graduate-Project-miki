let keyword, searchRes, count;
const fetchAPI = async () => {
    keyword = decodeURI(location.pathname.split('/')[2]);

    const res = await fetch(`/dev/searchResult?keyword=삐아`);
    const data = await res.text();
    searchRes = (JSON.parse(data));
    count = searchRes['size'];
    const resultItemCount = document.querySelector('.result-item-count');
    resultItemCount.textContent = count;
    const script = document.createElement('script');
    script.src = '/js/searchResult.js';
    document.body.appendChild(script);
};

fetchAPI()
    .catch(e => console.error(e));