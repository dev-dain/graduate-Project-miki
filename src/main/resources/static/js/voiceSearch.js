const searchInput = document.getElementById('speech');
const goPrevBtn = document.querySelector('.go-prev');
const retryBtn = document.querySelector('.retry');
const positiveBtn = document.querySelector('.positive');
const modalContainer = document.querySelector('.modal-container');
goPrevBtn.addEventListener('click', () => { history.back(); });
retryBtn.addEventListener('click', () => { searchInput.value = ''; });
positiveBtn.addEventListener('click', () => {
    if (!searchInput.value) {
        modalContainer.appendChild(createModal(modalContainer, 'voice'));
        modalContainer.classList.add('display');
    }
    else {
        location.href = `/searchVoice/${searchInput.value}`;
    }
});
const wordContainer = document.querySelector('.word-container');
const popItemList = [];
;
fetch(`/dev/popularity`)
    .then(res => res.text())
    .then(data => {
        const itemList = (JSON.parse(data))['Item'];
        itemList.forEach(function (item) {
            const wordBox = document.createElement('div');
            wordBox.classList.add('word-box');
            const rankIndex = document.createElement('div');
            rankIndex.classList.add('rank-index');
            rankIndex.textContent = item.rank;
            const itemTitle = document.createElement('div');
            itemTitle.textContent = item.itemName;
            wordBox.appendChild(rankIndex);
            wordBox.appendChild(itemTitle);
            wordBox.addEventListener('click', () => {
                location.href = `/item/${item.itemId}`;
            });
            wordContainer.appendChild(wordBox);
        });
    })
    .catch(e => console.error(e));
