const createBtnContainer = (id, isTestable) => {
  const putInCartBtn = document.createElement('button');
  putInCartBtn.classList += 'cont-btn put-in-cart';
  /////////////////////
  // 장바구니에 어떻게 담을 것인지 생각해야 함
  /////////////////////
  putInCartBtn.addEventListener('click', () => location.href('/cartList'));

  const goTestBtn = document.createElement('button');
  if (isTestable === 'Y') {
    goTestBtn.classList += 'cont-btn go-test';
    ///////////////////////
    // 어떻게 테스트하게 할지 정해야 함
    ///////////////////////
    goTestBtn.addEventListener('click', () => location.href = `/test/${id}`);
  } else {
    goTestBtn.style.display = 'none';
  }

  const viewReviewBtn = document.createElement('button');
  viewReviewBtn.classList += 'cont-btn view-review';
  ////////////////////
  // 어떻게 리뷰를 보게 할 것인지 결정해야 함
  ////////////////////
  viewReviewBtn.addEventListener('click', () => console.log('리뷰'));
  return [putInCartBtn, goTestBtn, viewReviewBtn]
}