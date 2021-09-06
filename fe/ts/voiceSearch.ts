const searchInput: HTMLInputElement = document.getElementById('speech') as HTMLInputElement;
const goPrevBtn: HTMLButtonElement = document.querySelector('.go-prev') as HTMLButtonElement;
const retryBtn: HTMLButtonElement = document.querySelector('.retry') as HTMLButtonElement;
const positiveBtn: HTMLButtonElement = document.querySelector('.positive') as HTMLButtonElement;
const modalContainer: HTMLDivElement = document.querySelector('.modal-container') as HTMLDivElement;

goPrevBtn.addEventListener('click', (): void => { history.back(); });
retryBtn.addEventListener('click', (): void => { searchInput.value = ''; });

positiveBtn.addEventListener('click', () => {
  if (!searchInput.value) {
      modalContainer.appendChild(createModal(modalContainer, 'voice'));
      modalContainer.classList.add('display');
  } else {
    location.href = `/searchVoice/${searchInput.value}`;
  }
});

const wordContainer = document.querySelector('.word-container') as HTMLDivElement;
const popItemList: popItem[] = [];

interface popItem {
  itemId: string,
  itemName: string,
  rank: string 
};

fetch(`/dev/popularity`)
  .then(res => res.text())
  .then(data => {
    const itemList: popItem[] = (JSON.parse(data))['Item'];
    itemList.forEach(function (item) {
      const wordBox: HTMLDivElement = document.createElement('div') as HTMLDivElement;
      wordBox.classList.add('word-box');
    
      const rankIndex: HTMLDivElement = document.createElement('div') as HTMLDivElement;
      rankIndex.classList.add('rank-index');
      rankIndex.textContent = item.rank;
      const itemTitle: HTMLDivElement = document.createElement('div') as HTMLDivElement;
      itemTitle.textContent = item.itemName;
    
      wordBox.appendChild(rankIndex);
      wordBox.appendChild(itemTitle);
      wordBox.addEventListener('click', (): void => {
        location.href = `/item/${item.itemId}`;
      });
    
      wordContainer.appendChild(wordBox);    
    });
  })
  .catch(e => console.error(e));