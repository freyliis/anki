<ul>
<li>This program is written with gradle.</li>
<li>To build: <i>./gradlew build</i></li>
It has two possible ways of running:
<li><i>./gradlew run</i> gets defaultDeck.json file available in the project dir and runs</li>
or
<li><i>./gradlew run -DinputPath=file1.json -DoutputPath=file2.json</i> gets inputFile as input and outputFile as output</li>
Remarks
<li>Input and output files are JSON, format you can find in defaultDeck.json</li>
<li>It is not possible to play twice the same day.</li>
<li>It is not possible to play when there are no questions.</li>
</ul>