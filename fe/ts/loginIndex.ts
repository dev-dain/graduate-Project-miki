if (storeId) {
  localStorage.setItem('store_id', storeId);
} else {
  storeId = localStorage.getItem('store_id');
}
if (storeName) {
  localStorage.setItem('store_name', storeName);
} else {
  storeName = localStorage.getItem('store_name');
}

location.href = '/main';