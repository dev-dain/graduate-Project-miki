let facemesh;
let video;
let predictions = [];
let face;
let sil;

function setup() {
  // createCanvas(640, 480);
  createCanvas(1600, 800);
  // createCanvas(640, 480);
  video = createCapture(VIDEO);
  // video.size(1024, 768);
  // video.size(windowWidth, windowHeight);
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
  // background(0);
  // scale(1.5);
  image(video, 0, 0, width, height);

  drawKeypoints();
}

function drawKeypoints() {
  // background(0, 0, 0, 150);
  for (let i = 0; i < predictions.length; i += 1) {
    sil = predictions[i].annotations;
    noStroke();
 
     switch (position) {
      case 'L':
        /* fill lip position */
        fill(R,G,B,alpha*100); // alpha 값이 0.x로 넘어오기때문에 * 100 필요
        face.lips();
      case 'C':
        /* fill cheek position */
        fill(R, G, B, 10); //투명도 10
        face.leftCheeck();  //볼터치
      case 'B':
        /* fill eyebrow position */
        fill(R, G, B, 10);
        face.leftEyebrow();  //눈썹
      default :
        return 0;
    }

  }
}