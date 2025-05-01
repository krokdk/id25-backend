🛠️ Lokal opsætning af id25-backend (macOS / Apple Silicon)

Denne guide beskriver, hvordan du sætter projektet op og kører det lokalt med Java 17 og Maven.

✅ Forudsætninger

macOS med Apple Silicon (M1/M2/M3)
IntelliJ IDEA installeret (med Maven-support)
iTerm2 eller anden terminal
Homebrew installeret
⚙️ Installationstrin

1. Installer Homebrew
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
2. Installer Git og Java 17
   brew install git
   brew install openjdk@17
3. Tilføj Java 17 til din .zshrc
   echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
   echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@17"' >> ~/.zshrc
   echo 'export CPPFLAGS="-I/opt/homebrew/opt/openjdk@17/include"' >> ~/.zshrc
   source ~/.zshrc
4. Bekræft Java-version
   java -version
   mvn -v
   Du skal se Java 17 som aktiv version. Hvis du stadig ser Java 24, er JAVA_HOME ikke sat korrekt.

📁 Projektstruktur

~/dev/id25/
└── backend/
├── pom.xml
└── src/
🚀 Kørsel

A. Fra IntelliJ IDEA
Åbn ~/dev/id25/backend
IntelliJ opdager automatisk pom.xml og importerer som Maven-projekt
Gå til File → Project Structure og sæt Project SDK til Java 17
Højreklik på Id25BackendApplication.java → Run
B. Fra terminal
cd ~/dev/id25/backend
./mvnw clean install -DskipTests
./mvnw spring-boot:run
🌐 API URL

Når applikationen kører, er den tilgængelig på:

http://localhost:8080/
🧪 Kør tests

./mvnw test
Testresultater findes i:

target/surefire-reports/
🧠 Fejlfinding

TypeTag :: UNKNOWN-fejl → Maven bruger for ny JDK (Java 24). Brug Java 17.
Tjek JAVA_HOME med:
echo $JAVA_HOME
📄 Ekstra (valgfrit)

Du kan lave en symbolsk link til systemets Java-mappe, hvis nødvendigt:

sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
(kræver admin-adgang)

Sidst opdateret: 2025-05-01