const mapContainer = document.getElementById('map')
const mapOption = {
    //접속했을 때 보이는 구역은 미아사거리역점
    /////////////////////
    center: new kakao.maps.LatLng(37.613672, 127.030438),
    level: 7
}
const map = new kakao.maps.Map(mapContainer, mapOption);

const newMarker = new kakao.maps.Marker({
    // 지도 중심좌표에 마커를 생성합니다
    position: map.getCenter()
});
// 지도에 마커를 표시합니다
newMarker.setMap(map);

const fetchData = () => {
    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
    const mapTypeControl = new kakao.maps.MapTypeControl();

    // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
    // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    const zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

    const positions = [];

    
    // 여기부터
    let stores = [
        {
            store_id: '20',
            store_name: '성신여대점',
            // 도로명 주소도 필요하겠네요
            store_location: '서울시 성북구 돈암로 64',
            store_number: '02-783-0529',
            store_latitude: '37.592628',
            store_longtitude: '127.017762'
        },
        {
            store_id: '35',
            store_name: '고대점',
            store_location: '서울시 성북구 안암로3길 25',
            store_number: '02-705-6920',
            store_latitude: '37.585419',
            store_longtitude: '127.029142'
        },
        {
            // 서울에 있는 매장을 더 추가해야겠네요
            store_id: '36',
            store_name: '경희플라자점',
            store_location: '서울시 동대문구 회기동 경희대학교병원 A동 105호',
            store_number: '02-683-0962',
            store_latitude: '37.588503',
            store_longtitude: '127.021068'
        }
    ];

    stores.map(store => {
        positions.push({
            id: store.store_id,
            name: store.store_name,
            location: store.store_location,
            tel: store.store_number,
            latlng: new kakao.maps.LatLng(store.store_latitude, store.store_longtitude),
            latitude: store.store_latitude,
            longtitude: store.store_longtitude
        });
    });
    // 여기까지

    // fetch('/stores/23')
    //     .then(res => res.json())
    //     .then(res => res.map(store => {
    //         positions.push({
    //             id: store.store_id,
    //             name: store.store_name,
    //             location: store.store_location,
    //             tel: store.store_number,
    //             latlng: new kakao.maps.LatLng(store.store_latitude, store.store_longtitude)
    //         });
    //     }))
    //     .catch(e => console.error(e));



    // 마커 이미지의 이미지 주소입니다
    const imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

    positions.map(position => {

        // 마커 이미지의 이미지 크기 입니다
        const imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        const marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: position.latlng, // 마커를 표시할 위치
            title: position.name,
            // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage, // 마커 이미지
            opacity: 0.7,
            clickable: true // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
        });
        kakao.maps.event.addListener(marker, 'mouseover', () => {
            marker.setOpacity(1.0);
        });
        kakao.maps.event.addListener(marker, 'mouseout', () => {
            marker.setOpacity(0.7);
        });
        // 마커를 지도에 표시합니다.
        marker.setMap(map);

        // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
        const iwContent = `<div style="width:auto;max-width:200px;height:auto;padding:7px">${marker.getTitle()}</div>`,
            // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // 인포윈도우를 생성합니다
        const infowindow = new kakao.maps.InfoWindow({
            content: iwContent,
            removable: iwRemoveable
        });

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', () => {
            infowindow.open(map, marker);
            const bounds = new kakao.maps.LatLngBounds();
            bounds.extend(new kakao.maps.LatLng(position.latitude, position.longtitude));
            map.setBounds(bounds);
        });
    });
}

window.onload = fetchData();