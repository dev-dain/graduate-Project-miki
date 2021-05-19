let facemesh;
let video;
let predictions = [];
let lipStickColor;

let colorSet;
let colorAlpha;

function preload(){
  lipStickColor = loadImage("../img/color.png");
  
}
function setup() {
  createCanvas(640, 640);
  video = createCapture(VIDEO);
  video.size(width, height);
  colorSet = [0, 255, 0];
  colorAlpha = 30;
  facemesh = ml5.facemesh(video, modelReady);

  // This sets up an event that fills the global variable "predictions"
  // with an array every time new predictions are made
  facemesh.on("predict", results => {
    predictions = results;
  });

  // Hide the video element, and just show the canvas
  video.hide();
}

function modelReady() {
  console.log("Model ready!");
}

function draw() {
  image(video, 0, 0, 640, 480);
   image(lipStickColor, 0, 480, 320, 150);
  // We can call both functions to draw all keypoints
  drawKeypoints();
}

// A function to draw ellipses over the detected keypoints
function drawKeypoints() {
  for (let i = 0; i < predictions.length; i += 1) {
    const keypoints = predictions[i].scaledMesh;
    // print(keypoints[0][0]);
    
    noStroke();
    // fill(255, 0, 0, 30);//칼라값 color(R, G, B, Alpha)
    fill(colorSet[0], colorSet[1], colorSet[2], colorAlpha);
    beginShape();//Upper 
    vertex(keypoints[61][0], keypoints[61][1]);
    vertex(keypoints[185][0], keypoints[185][1]);
    vertex(keypoints[40][0], keypoints[40][1]);
    vertex(keypoints[39][0], keypoints[39][1]);
    vertex(keypoints[37][0], keypoints[37][1]);
    vertex(keypoints[0][0], keypoints[0][1]);
    vertex(keypoints[267][0], keypoints[267][1]);
    vertex(keypoints[269][0], keypoints[269][1]);
    vertex(keypoints[270][0], keypoints[270][1]);
    vertex(keypoints[409][0], keypoints[409][1]);
    vertex(keypoints[291][0], keypoints[291][1]);
    
    vertex(keypoints[308][0], keypoints[308][1]);
    vertex(keypoints[415][0], keypoints[415][1]);
    vertex(keypoints[310][0], keypoints[310][1]);
    vertex(keypoints[311][0], keypoints[311][1]);
    vertex(keypoints[312][0], keypoints[312][1]);
    vertex(keypoints[13][0], keypoints[13][1]);
    vertex(keypoints[82][0], keypoints[82][1]);
    vertex(keypoints[81][0], keypoints[81][1]);
    vertex(keypoints[80][0], keypoints[80][1]);
    vertex(keypoints[191][0], keypoints[191][1]);
    vertex(keypoints[78][0], keypoints[78][1]);
    endShape(CLOSE);
    
    beginShape(); //Lower 아랫입술
    vertex(keypoints[78][0], keypoints[78][1]);
    vertex(keypoints[95][0], keypoints[95][1]);
    vertex(keypoints[88][0], keypoints[88][1]);
    vertex(keypoints[178][0], keypoints[178][1]);
    vertex(keypoints[87][0], keypoints[87][1]);
    vertex(keypoints[14][0], keypoints[14][1]);
    vertex(keypoints[317][0], keypoints[317][1]);
    vertex(keypoints[402][0], keypoints[402][1]);
    vertex(keypoints[318][0], keypoints[318][1]);
    vertex(keypoints[324][0], keypoints[324][1]);
    vertex(keypoints[308][0], keypoints[308][1]);
    
    vertex(keypoints[375][0], keypoints[375][1]);
    vertex(keypoints[321][0], keypoints[321][1]);
    vertex(keypoints[405][0], keypoints[405][1]);
    vertex(keypoints[314][0], keypoints[314][1]);
    vertex(keypoints[17][0], keypoints[17][1]);
    vertex(keypoints[84][0], keypoints[84][1]);
    vertex(keypoints[181][0], keypoints[181][1]);
    vertex(keypoints[91][0], keypoints[91][1]);
    vertex(keypoints[146][0], keypoints[146][1]);
    endShape(CLOSE);
    // ellipse(keypoints[205][0], keypoints[205][1], 30, 30);
    // ellipse(keypoints[425][0], keypoints[425][1], 30, 30);
    // 205
    // 425
    // // Draw facial keypoints.
    // for (let j = 0; j < keypoints.length; j += 1) {
    //   const [x, y] = keypoints[j];
    //   // print(keypoints[0][0]);
    //   // fill(255, 0, 0);
    //   // ellipse(x, y, 5, 5);
    // }
  }
}

function mousePressed(){
  colorSet = get(mouseX, mouseY);
  print(colorSet);
  colorAlpha = 30;
  
}
/*
silhouette: [
    10,  338, 297, 332, 284, 251, 389, 356, 454, 323, 361, 288,
    397, 365, 379, 378, 400, 377, 152, 148, 176, 149, 150, 136,
    172, 58,  132, 93,  234, 127, 162, 21,  54,  103, 67,  109
  ],

  lipsUpperOuter: [61, 185, 40, 39, 37, 0, 267, 269, 270, 409, 291],
  lipsLowerOuter: [146, 91, 181, 84, 17, 314, 405, 321, 375, 291],
  lipsUpperInner: [78, 191, 80, 81, 82, 13, 312, 311, 310, 415, 308],
  lipsLowerInner: [78, 95, 88, 178, 87, 14, 317, 402, 318, 324, 308],

  rightEyeUpper0: [246, 161, 160, 159, 158, 157, 173],
  rightEyeLower0: [33, 7, 163, 144, 145, 153, 154, 155, 133],
  rightEyeUpper1: [247, 30, 29, 27, 28, 56, 190],
  rightEyeLower1: [130, 25, 110, 24, 23, 22, 26, 112, 243],
  rightEyeUpper2: [113, 225, 224, 223, 222, 221, 189],
  rightEyeLower2: [226, 31, 228, 229, 230, 231, 232, 233, 244],
  rightEyeLower3: [143, 111, 117, 118, 119, 120, 121, 128, 245],

  rightEyebrowUpper: [156, 70, 63, 105, 66, 107, 55, 193],
  rightEyebrowLower: [35, 124, 46, 53, 52, 65],

  leftEyeUpper0: [466, 388, 387, 386, 385, 384, 398],
  leftEyeLower0: [263, 249, 390, 373, 374, 380, 381, 382, 362],
  leftEyeUpper1: [467, 260, 259, 257, 258, 286, 414],
  leftEyeLower1: [359, 255, 339, 254, 253, 252, 256, 341, 463],
  leftEyeUpper2: [342, 445, 444, 443, 442, 441, 413],
  leftEyeLower2: [446, 261, 448, 449, 450, 451, 452, 453, 464],
  leftEyeLower3: [372, 340, 346, 347, 348, 349, 350, 357, 465],

  leftEyebrowUpper: [383, 300, 293, 334, 296, 336, 285, 417],
  leftEyebrowLower: [265, 353, 276, 283, 282, 295],

  midwayBetweenEyes: [168],

  noseTip: [1],
  noseBottom: [2],
  noseRightCorner: [98],
  noseLeftCorner: [327],

  rightCheek: [205],
  leftCheek: [425]
  */