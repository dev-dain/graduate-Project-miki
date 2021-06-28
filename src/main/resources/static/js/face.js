class Face {
  silhouette() {
    beginShape();
    for (let i = 0; i < 36; i++) {
      let [x, y] = sil.silhouette[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    endShape(CLOSE);
  }
  
  // cheeck(){
  //   beginShape();
  //   for (let i = 7; i < 10; i++) {
  //     let [x, y] = sil.silhouette[i];
  //     vertex(x * (windowWidth / 640), y * (windowHeight / 480));
  //   }
  //   endShape(CLOSE);
  // }

  lips() {
    beginShape();
    for (let i = 0; i < 11; i++) {
      let [x, y] = sil.lipsUpperOuter[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    for (let i = 10; i > -1; i--) {
      let [x, y] = sil.lipsUpperInner[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    for (let i = 0; i < 11; i++) {
      let [x, y] = sil.lipsLowerInner[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    for (let i = 9; i > -1; i--) {
      let [x, y] = sil.lipsLowerOuter[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    endShape(CLOSE);
  }

  rightEyeUpper() {
    beginShape();
    for (let i = 0; i < 7; i++) {
      let [x, y] = sil.rightEyeUpper0[i];
      vertex(x * (width / 640), y * (height / 480));
    }

    for (let i = 6; i > -1; i--) {
      let [x, y] = sil.rightEyeUpper1[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    // for(let i = 0;i<7;i++){
    //     let [x, y] = sil.rightEyeUpper2[i];
    //     vertex(x, y);
    //   }
    endShape(CLOSE);
  }
  rightEyeLower() {
    beginShape();
    for (let i = 0; i < 9; i++) {
      let [x, y] = sil.rightEyeLower0[i];
      vertex(x * (width / 640), y * (height / 480));
    }

    for (let i = 8; i > -1; i--) {
      let [x, y] = sil.rightEyeLower1[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    endShape();
  }

  rightEyeLower2() {
    beginShape();
    for (let i = 0; i < 9; i++) {
      let [x, y] = sil.rightEyeLower2[i];
      vertex(x * (width / 640), y * (height / 480));
    }

    for (let i = 8; i > -1; i--) {
      let [x, y] = sil.rightEyeLower3[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    endShape();
  }

  rightEyebrow() {
    beginShape();
    for (let i = 1; i < 7; i++) {
      let [x, y] = sil.rightEyebrowUpper[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    for (let i = 5; i > 2; i--) {
      let [x, y] = sil.rightEyebrowLower[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    endShape(CLOSE);
  }
  
  rightCheeck(){
    beginShape();
    for (let i = 0; i < 9; i++) {
      let [x, y] = sil.rightEyeLower3[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    vertex(sil.rightCheek[0][0] * (width / 640), sil.leftCheek[0][1] * (height / 480));
    for (let i = 27; i < 31; i++) {
      let [x, y] = sil.silhouette[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    
    // console.log(sil.leftCheek[0]);
    endShape(CLOSE);
  }

  leftEyeUpper() {
    beginShape();
    for (let i = 0; i < 7; i++) {
      let [x, y] = sil.leftEyeUpper0[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    for (let i = 6; i > -1; i--) {
      let [x, y] = sil.leftEyeUpper1[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    // for(let i = 0;i<7;i++){
    //     let [x, y] = sil.leftEyeUpper2[i];
    //     vertex(x*(windowWidth/640), y*(windowHeight/480));
    //   }
    endShape(CLOSE);
  }
  leftEyeLower() {
    beginShape();
    for (let i = 0; i < 9; i++) {
      let [x, y] = sil.leftEyeLower0[i];
      vertex(x * (width / 640), y * (height / 480));
    }

    for (let i = 8; i > -1; i--) {
      let [x, y] = sil.leftEyeLower1[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    endShape(CLOSE);
  }

  leftEyeLower2() {
    beginShape();
    for (let i = 0; i < 9; i++) {
      let [x, y] = sil.leftEyeLower2[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    for (let i = 8; i > -1; i--) {
      let [x, y] = sil.leftEyeLower3[i];
      vertex(x * (width / 640), y * (height / 480));
    }

    endShape(CLOSE);
  }

  leftEyebrow() {
    beginShape();
    for (let i = 1; i < 7; i++) {
      let [x, y] = sil.leftEyebrowUpper[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    for (let i = 5; i > 2; i--) {
      let [x, y] = sil.leftEyebrowLower[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    endShape(CLOSE);
  }
  
  leftCheeck(){
    beginShape();
    for (let i = 0; i < 9; i++) {
      let [x, y] = sil.leftEyeLower3[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    vertex(sil.leftCheek[0][0] * (width / 640), sil.leftCheek[0][1] * (height / 480));
    for (let i = 9; i > 5; i--) {
      let [x, y] = sil.silhouette[i];
      vertex(x * (width / 640), y * (height / 480));
    }
    
    // console.log(sil.leftCheek[0]);
    endShape(CLOSE);
  }
}
/*
silhouette: Array[36]
lipsUpperOuter: Array[11]
lipsLowerOuter: Array[10]
lipsUpperInner: Array[11]
lipsLowerInner: Array[11]
rightEyeUpper0: Array[7]
rightEyeLower0: Array[9]
rightEyeUpper1: Array[7]
rightEyeLower1: Array[9]
rightEyeUpper2: Array[7]
rightEyeLower2: Array[9]
rightEyeLower3: Array[9]
rightEyebrowUpper: Array[8]
rightEyebrowLower: Array[6]
leftEyeUpper0: Array[7]
leftEyeLower0: Array[9]
leftEyeUpper1: Array[7]
leftEyeLower1: Array[9]
leftEyeUpper2: Array[7]
leftEyeLower2: Array[9]
leftEyeLower3: Array[9]
leftEyebrowUpper: Array[8]
leftEyebrowLower: Array[6]
midwayBetweenEyes: Array[1]
noseTip: Array[1]
noseBottom: Array[1]
noseRightCorner: Array[1]
noseLeftCorner: Array[1]
rightCheek: Array[1]
leftCheek: Array[1]
*/
