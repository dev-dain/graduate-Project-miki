let facemesh;
let video;
let predictions = [];
let face;
let sil;
// const R = 123;
// const G = 0;
// const B = 0;
// const A = 50;
// const pos = N; // 어느 부위인지 확인할 변수

function setup() {
  createCanvas(640, 480);
  video = createCapture(VIDEO);
  video.size(width, height);
  facemesh = ml5.facemesh(video, modelReady);
 
  facemesh.on("predict", (results) => {
    predictions = results;
  });
  video.hide();
  face = new Face();
}

function modelReady() {
  console.log("Model ready!");
}

function draw() {
  image(video, 0, 0, width, height);
  drawKeypoints(R,G,B,A,pos);
}



function drawKeypoints(R,G,B,A,pos){
  for (let i = 0; i < predictions.length; i += 1) {
    sil = predictions[i].annotations;
    noStroke();
    
    // fill(255, 255, 255, 50); //얼굴 윤곽 rgb+투명도
    // face.silhouette(); 
    switch (pos){
    case L:
      fill(R,G,B,A);
      face.lips();
    case C:
        /* fill cheek position */
    case B:
      /* fill eyebrow position */
    default :
      return 0;
  }
    
//     fill(255,0,0, 50); //입술 rgb+투명도
//     fill(R,G,B,A);
//     face.lips();
    
//     fill(0,0,255, 50); //오른쪽 눈 꺼풀 rgb+투명도
//     face.rightEyeUpper();
    
//     fill(0,0,0, 50); //오른쪽 눈 아래 rgb+투명도
//     face.rightEyeLower();
    
//     fill(0,0,255, 50); //왼쪽 눈 꺼풀 rgb+투명도
//     face.leftEyeUpper();
    
//     fill(0,0,0, 50); //왼쪽 눈 아래 rgb+투명도
//     face.leftEyeLower();
    
    //볼 좌우 연지곤지(수정필요)
    // ellipse(sil.rightCheek[0][0], sil.rightCheek[0][1], 20 , 20); 
    // ellipse(sil.leftCheek[0][0], sil.leftCheek[0][1], 20 , 20);
  }
}