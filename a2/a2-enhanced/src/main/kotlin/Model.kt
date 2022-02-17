import RandomGen.Companion.getRandomSequence
import kotlin.collections.ArrayList
import kotlin.random.Random

class Model {

    // all views of this model
    private val views: ArrayList<IView> = ArrayList()
    // store the datasets
    private var datasets: MutableMap<String, DataSet?> = mutableMapOf()
    // store the color sets
    private var colorSets: MutableMap<String, String?> = mutableMapOf()
    // current bar color, initialized to rainbow
    private var curColor = "Rainbow"
    // counter for new datasets
    private var cnt = 0
    // current title default initialized to "Increasing"
    private var curSelect = "Increasing"
    // current removed dataset
    private var curRM = ""
    // boolean variable denotes if the special theme is selected
    private var isSelectedTheme = false
    init {
        datasets = listOf("Increasing", "Large", "Middle", "Single", "Range", "Percentage").associateWith {
            createTestDataSet(it)
        }.toMutableMap()
        colorSets = listOf("Rainbow", "Light Sky Blue", "Chocolate", "Light Pink", "Light Salmon", "Lime Green", "Black - White").associateWith {
            createColorOption(it)
        }.toMutableMap()
    }

    // method that the views can use to register themselves with the Model
    // once added, they are told to update and get state from the Model
    fun addView(view: IView) {
        views.add(view)
        view.updateView()
    }

    // the model uses this method to notify all the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private fun notifyObservers() {
        for (view in views) {
            view.updateView()
        }
    }

    // function to set theme selected boolean
    fun setSelectedTheme(isSelected: Boolean) {
        isSelectedTheme =  isSelected
        notifyObservers()
    }

    // function to return theme selected boolean
    fun getSelectedTheme(): Boolean {
        return isSelectedTheme
    }

    // function to get the current available dataset dropdown menu
    fun getDataDropDownMenu(): ArrayList<String> {
        return ArrayList(datasets.keys)
    }

    // function to get the current available color dropdown menu
    fun getColorDropDownMenu(): ArrayList<String> {
        return ArrayList(colorSets.keys)
    }

    // method that the Controller uses to tell the Model to change state
    fun setDataset(selectionModel: String) {
        // set the newly selected dataset
        curSelect = selectionModel
        notifyObservers()
    }

    fun setColorset(selectionModel: String) {
        // set the newly selected color
        curColor = selectionModel
        notifyObservers()
    }

    fun getCurColor(): String? {
        // get current selected color
        return colorSets[curColor]
    }

    fun getDataSet(): DataSet? {
        // get current selected dataset
        return datasets[curSelect]
    }

    fun getDataSets(): MutableMap<String, DataSet?> {
        // get current available datasets
        return datasets
    }

    fun modifySpinnerVal(counter: Int, newValue: Int) {
        // function to modify the spinners in the left table
        for ((index, _) in datasets[curSelect]?.data!!.withIndex()) {
            if ((index + 1) == counter) {
                datasets[curSelect]?.data!![index] = newValue
            }
        }
        notifyObservers()
    }

    fun setNewDataset(dataPoints: Int) {
        // generates a new random dataset named “NewX”
        cnt++
        val name = "New$cnt"
        val title = getRandomSequence(3)
        val xAxis = getRandomSequence(1).replaceFirstChar { it.uppercase() }
        val yAxis = getRandomSequence(1).replaceFirstChar { it.uppercase() }
        val newData = MutableList<Int>(0) { 0 }
        for (i in 1..dataPoints) {
            newData.add(Random.nextInt(0, 101)) // randomly select from 0 to 100
        }
        datasets[name] = DataSet(title, xAxis, yAxis, newData)
        curSelect = name
        notifyObservers()
    }

    fun deleteSelectedDataSet() {
        if (curSelect != "Increasing"){
            curRM = curSelect
            datasets.remove(curSelect)
            // also need to be removed from the dropdown menu
        }
        curSelect = "Increasing"
        notifyObservers()
    }

    fun getCurRM(): String {
        return curRM
    }

    // function to get the current selected dataset name
    fun getCurSelect(): String{
        return curSelect
    }

    // function to update a given dataset's title
    fun updateTitle(title: String) {
        datasets[curSelect]?.title = title
        notifyObservers()
    }

    // function to update a given dataset's x axis label
    fun updateX(xAxis: String) {
        datasets[curSelect]?.xAxis = xAxis
        notifyObservers()
    }

    // function to update a given dataset's y axis label
    fun updateY(yAxis: String) {
        datasets[curSelect]?.yAxis = yAxis
        notifyObservers()
    }
}
