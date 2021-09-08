// searchResult에서 보내는지, category에서 보내는지에 따라 
// 어떻게 fetch를 해야할지가 구분됨.
// state 추가가 필요
const sortContainerFunc = (sortContainer: HTMLDivElement, state: string): void => {
  for (let i = 0; i < sortContainer.children.length; i++) {
    sortContainer.children[i].addEventListener('click', () => {
      for (let j = 0; j < sortContainer.children.length; j++) {
        if (sortContainer.children[j].classList.contains('selected-btn')) {
          if (i === j) return;
          sortContainer.children[j].classList.remove('selected-btn');
        }
      }
      localStorage.setItem('search-sort-way', sortContainer.children[i].className.split('-')
      [sortContainer.children[i].className.split('-').length - 1]);
      sortWay = localStorage.getItem('search-sort-way') as string;
      sortContainer.children[i].classList.add('selected-btn');

      localStorage.setItem('page', '1');

      tbody.innerHTML = '';
      if (state === 'search') {
        fetchData('1', keyword, sortWay);
      } else {
        fetchData('1', category, sortWay);
      }
    });
  }
}