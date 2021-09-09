const sortContainerFunc = (sortContainer, state) => {
    for (let i = 0; i < sortContainer.children.length; i++) {
        sortContainer.children[i].addEventListener('click', () => {
            for (let j = 0; j < sortContainer.children.length; j++) {
                if (sortContainer.children[j].classList.contains('selected-btn')) {
                    if (i === j)
                        return;
                    sortContainer.children[j].classList.remove('selected-btn');
                }
            }
            localStorage.setItem(`${sortWay}sort-way`, sortContainer.children[i].className.split('-')[sortContainer.children[i].className.split('-').length - 1]);
            sortWay = localStorage.getItem(`${sortWay}sort-way`);
            sortContainer.children[i].classList.add('selected-btn');
            localStorage.setItem('page', '1');
            tbody.innerHTML = '';
            if (state === 'search-') {
                fetchData('1', keyword, sortWay);
            }
            else {
                fetchData('1', num, sortWay);
            }
        });
    }
};
