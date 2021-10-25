# Nachdenkzettel - GIT

## 1. Was genau sind die Gründe um Gitlab zu verwenden?
Versionskontrolle, Sicherung von vorangegangenen Änderungen, strukturierte Zusammenarbeit




## 2. Welche Daten gehören (nicht) ins Repo?

### Rein
- .java Files
- .xml Files
- Bilddateien für Gameprojekte
- Musikdateien für Projekte
- .project Files (Endung je nach Tool)
- UML Modelle
- Zeichnungen
- Notizen
- Dokumentation
- Kapitel eines Buches
- Eine Bachelorarbeit
  
### Nicht rein
- Configurationsfiles
- Passwörter für Cloud-Services
- Passwörter für lokale Services (Self-hosted)
- logfiles
- Messdaten vom Profiling

### kommt auf den Kontext an:
- .json Files


## 3. Was soll der Mist mit den Stages (dass add/commit nur lokal wirken)?
Beim lokalen Commit werden Veränderungen noch abgeglichen bevor diese gemerged werden.
Man kann lokal an versionen arbeiten, die noch unvollständig sind


## 4. Würden Sie in einer Firma Gitlab selber hosten oder GIT als Service im Netz? Begründung.
Selber hosten, dann hat man vollständige Kontrolle über die Daten.
Im Fall eines open source Projekts kann man auch git als Service nutzen.

Andere Perspektive: je nach Kompetezen, Budget und Aufgaben in der Firma kann es auch Sinn machen, git as a
service zu nutzen, um Overhead und Infrastrukturersparnisse zu haben.



## 5. Verwenden Sie Branches im Projekt oder arbeiten alle Teammitglieder auf dem Master Branch?
   ## Zeigen Sie Vor- und Nachteile der Verfahren
Für Branching: man hat einen Main, auf den nur getestete Features kommen.
man hat eine funktionierende Version des Projekts und man zerschießt sich das Projekt nicht dauernd mit Merge-Konflikten

Gegen Branching: für kleine Projekte zu großer Aufwand
   

## 6. Wie veröffentlichen Sie Ihre Änderungen auf dem globalen Repository? Wie oft checken Sie Ihre
##  Änderungen im globalen Repo ein? Was ist besser: Nach jeder Änderung, nach einigen Änderungen,
##  wenn Sie ein zusammenhängendes Stück fertig haben, wenn Sie eine Änderung machen die viele
##  Kollegen betrifft, einmal am Tag, einmal die Woche. Wieso?
Committen kann mehrfach passieren, um eine Sichererung zu haben. Pushen thematisch und zusammenhängend und wenn es
läuft, damit man den anderen nix zerschießt. Man muss die Abhängigkeiten innerhalb des Projektes berücksichtigen
und eine sinnvolle Balance zwischen Datensicherung und Nachvollziehbarkeit / Schaden für andere finden.

   
## 7. Eine Version eines Files im Repo sieht so aus:  „There are two versions of GitLab: Community Edition (CE) and Enterprise Edition (EE)“
## Ihre Version die Sie hochladen hat an dieser Stelle:
## „There are two versions of GitLab: CE and EE.“
## Wie löst Gitlab diesen Konflikt?
Abhängig davon, ob die hochzuladende Datei auf dem aktuellen Repo basiert. Rebase oder Merge-Aufforderung von git


## 8. Hausaufgabe für jedes Team: Es muss eine chain-story (Kettengeschichte) erzählt werden bei der 
## ALLE Teammitglieder jeweils immer einen Satz erfinden und dann einchecken. Ich will von ALLEN
## Teammitgliedern Sätze im Repo finden! Danach sagen Sie uns wie es ging. Haben Sie über externe
## Kanäle kommuniziert (Slack?). Anderweitig abgesprochen?
Einfach über git? Und issues :D
