Forklaring af filerne i dit Git repo:
src/: Dette er din kildekode (Java-filer, Spring Boot-konfigurationer osv.). Alt, hvad der er nødvendigt for at køre din applikation, skal være i denne mappe.

.gitignore: Filer og mapper, der ikke skal trackes af Git, som f.eks. byggefiler og IDE-specifikke filer (som .idea).

.mvn/wrapper: Indeholder Maven-wrapperen, som gør det lettere at bygge dit projekt uden at skulle installere Maven separat på maskinen. Dette er nyttigt for andre, der arbejder med dit projekt.

mvnw & mvnw.cmd: Distributørfiler for Maven Wrapperen, der tillader, at Maven kan køres, selvom Maven ikke er installeret lokalt på maskinen.

pom.xml: Maven konfigurationsfilen, der definerer dit projekt og dets afhængigheder.

.gitattributes: Denne fil bruges til at definere specifikke attributter for filer i Git. Den kan bruges til at kontrollere ting som linjeafslutninger (f.eks. sørge for at LF linjeafslutning bruges på tværs af alle platforme).
