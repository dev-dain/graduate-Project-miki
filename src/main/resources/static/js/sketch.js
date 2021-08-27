let facemesh;
let video;
let predictions = [];
let face;
let sil;

function setup() {
  createCanvas(1000, 1200);
  video = createCapture(VIDEO);
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

  drawKeypoints();
}

let Lip = false;
let cheek = false;
let brow = false;

function drawKeypoints() {
  // background(0, 0, 0, 150);
  for (let i = 0; i < predictions.length; i += 1) {
    sil = predictions[i].annotations;
    noStroke();

    if(is_L == true){
        /* fill lip position */
        fill(L_R,L_G,L_B,alpha*100); // alpha 값이 0.x로 넘어오기때문에 * 100 필요
        face.lips();
        if (is_C == true){
            fill(C_R, C_G, C_B, alpha*20); //투명도 10
            face.leftCheeck();  //볼터치
            face.rightCheeck();
            if (is_B == true){
                fill(B_R, B_G, B_B, alpha*100);
                face.leftEyebrow();  //눈썹
                face.rightEyebrow();
            }
        }
        else if (is_B == true){
            fill(B_R, B_G, B_B, alpha*100);
            face.leftEyebrow();  //눈썹
            face.rightEyebrow();
        }

    }
    else if (is_C == true){
        fill(C_R, C_G, C_B, alpha*20); //투명도 10
        face.leftCheeck();  //볼터치
        face.rightCheeck();
        if (is_B == true){
            fill(B_R, B_G, B_B, alpha*100);
            face.leftEyebrow();  //눈썹
            face.rightEyebrow();
        }
    }
    else if (is_B == true){
        fill(B_R, B_G, B_B, alpha*100);
        face.leftEyebrow();  //눈썹
        face.rightEyebrow();
    }

  }
}
