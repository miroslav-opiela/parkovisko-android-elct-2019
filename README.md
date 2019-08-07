# Android

### Materiál k 4. dňu (po skončení kurzu)

**Prosím vyplnte [dotazník](https://forms.gle/vykMAMK7XdegQo1b9) so spätnou väzbou pre mňa ako lektora.**

Odkazy a materiály pribudnú neskôr.


### Materiál po 3. dni
---
Materiál so zdrojovými kódmi a návodmi: <http://ics.upjs.sk/~opiela/vma/> Kurz bol realizovaný na jar 2018. Niektoré veci môžu byť preto neaktuálne.

Návrh UI pre aplikáciu parkoviska: <http://rmsoft.sk/parkovisko/> 

[Oficiálna dokumentácia Androidu](https://developer.android.com/guide) obsahuje okrem popisov jednotlivých tried aj návody pokrývajúce rôzne tématické oblasti, ale aj best practices pre vašu aplikáciu napr. z pohľadu bezpečnosti, výkonu alebo publikovania v obchode Google Play

---
* <https://guides.codepath.com/android/using-dialogfragment>
	* `DialogFragment` použítý na informáciu o cene parkovania. Návod obsahuje aj spôsob ako posunúť dáta z fragmentu smerom k aktivite pomocou listenera.

* <https://stackoverflow.com/questions/4878159/whats-the-best-way-to-share-data-between-activities>
	* rôzne spôsoby ako zdieľať dáta medzi dvoma aktivitami. Vyskúšali sme si prenos dát zabalených v intente a ukladanie do súboru. 

* <https://developer.android.com/training/data-storage/files>
	* súbory v androide - interné a externé úložisko. Interné nie je priamo dostupné pre používateľa a ostatné aplikácie. Pri práci s externým úložiskom je potrebné vypýtať si povolenie a overiť, či je dostupné.

* <https://developer.android.com/training/permissions/requesting>
	* od Android 6.0 sa povolenia nevyžadujú pri inštalácii, ale až počas behu aplikácie. Normálne povolenia stačí definovať v manifeste, [nebezpečné povolenia](https://developer.android.com/guide/topics/permissions/overview#permission-groups) je potrebné vypýtať si špeciálnym spôsobom. Odporúčam prečítať si rady ([best practices](https://developer.android.com/training/permissions/usage-notes)) ako pracovať s povoleniami a ako to vysvetliť používateľovi.

* <https://developer.android.com/guide/components/activities/activity-lifecycle>
	* životný cyklus aktivity. Na lepšie pochopenie je fajn [pridať logy](https://developer.android.com/studio/debug/am-logcat) a pozrieť si v logcat, kedy sa ktorá metóda zavolá.

* <https://developer.android.com/guide/topics/ui/menus>
	* o tom ako vytvoriť menu

* <https://stackoverflow.com/questions/24510219/what-is-the-difference-between-min-sdk-version-target-sdk-version-vs-compile-sd>
	* rozdiel medzi min, compile a target verziami Androidu resp. SDK

* <https://developer.android.com/jetpack/androidx>
	* AndroidX - novinky v support library na podporu spätnej kompatibility (aby novinky z nových verzií Androidu fungovali aj na starších zariadeniach) ale poskytuje to oveľa viac