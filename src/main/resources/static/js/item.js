const goPrevBtn = document.querySelector('.go-prev');
const cart = document.querySelector('.go-cart');

goPrevBtn.addEventListener('click', () => history.back());
cart.addEventListener('click', () => location.href = '/cart');