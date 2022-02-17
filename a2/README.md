# A2 Grapher
Zuomiao Hu (z228hu 20815304)
 
## Setup
* macOS 12.1
* IntelliJ IDEA 2021.3.1 (Ultimate Edition)
* kotlin.jvm 1.6.10
* javafxplugin 0.0.10
* Java SDK 16.0.2 (Amazon Corretto)
* JavaFX 17.0.1

## Enhancement 
* Added a delete button to delete selected dataset. After deletion, the dataset will be default to "increasing". The "increasing" dataset will be the default dataset and is not allowed to be deleted. All the other ones can be deleted.
* Added a choice box so that user can choose to change the graph to the special theme (dark mode), that has a black background and light yellow axis and labels
* Added a dropdown to allow the users to change the bar colours of the bar chart.
	- Options are {"Rainbow", "Light Sky Blue", "Chocolate", "Light Pink", "Light Salmon", "Lime Green", "Black - White"}, each leads to the color it denotes.
	- The "Black - White" option shows light yellow bars in the dark mode, and black bars in the default mode.
