void setup() {
  Serial.begin(9600);
  digitalWrite(13, HIGH);
  while (!Serial) {
    ;
  }
  pinMode(13, OUTPUT);
  digitalWrite(13, LOW);
  /*
  while(true) { 
    if((Serial.available()>0)) {
      if(Serial.read()==73) {
        Serial.print("1");
        digitalWrite(13, HIGH);
        delay(100);
      }
      else {
        Serial.print("0");
        digitalWrite(13, HIGH);
        delay(1000);
      }
      digitalWrite(13, LOW);
      break;
    }
  }
  */
}

void loop() {
  byte b[1024];
  if(Serial.available()>0) {
    int i=Serial.readBytes(b, 1024);
    for(int ii=0; ii<i; ii++) {
      digitalWrite(13, HIGH);
      Serial.println(b[ii]);
      delay(100);
      digitalWrite(13, LOW);
    }
  }
}
