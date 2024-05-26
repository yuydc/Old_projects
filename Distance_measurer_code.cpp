const int trigPin = 9;
const int echoPin = 10;
const float sesHizi = 0.0343;
void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
}
void loop() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  long sure = pulseIn(echoPin, HIGH);
  long mesafe = sure * sesHizi / 2;
  Serial.print("Mesafe: ");
  Serial.print(mesafe);
  Serial.println(" cm");
  delay(100);
}
