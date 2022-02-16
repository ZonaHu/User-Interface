import RandomGen.Companion.getRandomSequence
import kotlin.collections.ArrayList
import kotlin.random.Random

class Model {

    // all views of this model
    private val views: ArrayList<IView> = ArrayList()
    // store the datasets
    private var datasets: MutableMap<String, DataSet?> = mutableMapOf()
    // counter for new datasets
    private var cnt = 0
    // current title default initialized to "Increasing"
    private var curSelect = "Increasing"

    init {
        datasets = listOf("Increasing", "Large", "Middle", "Single", "Range", "Percentage").associateWith {
            createTestDataSet(it)
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

    fun getDropDownMenu(): ArrayList<String> {
        return ArrayList(datasets.keys)
    }

    // model's last message describing operation
    var message: String = "datasets"
        private set

    // method that the Controller uses to tell the Model to change state
    fun setDataset(selectionModel: String) {
        // set the newly selected dataset
        curSelect = selectionModel
        notifyObservers()
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

    fun getCurSelect(): String{
        return curSelect
    }

    fun updateTitle(title: String) {
        datasets[curSelect]?.title = title
        notifyObservers()
    }

    fun updateX(xAxis: String) {
        datasets[curSelect]?.xAxis = xAxis
        notifyObservers()
    }

    fun updateY(yAxis: String) {
        datasets[curSelect]?.yAxis = yAxis
        notifyObservers()
    }

}
