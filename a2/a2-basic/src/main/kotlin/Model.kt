
class Model {

    //region View Management
    // all views of this model
    private val views: ArrayList<IView> = ArrayList()
    // store the datasets
    private var datasets: MutableMap<String, DataSet?> = mutableMapOf()

    // current title default initialized to "Increasing"
    var curSelect = "Increasing"

    init{
        datasets = listOf("Increasing", "Large", "Middle","Single","Range","Percentage").associateWith { createTestDataSet(it) }.toMutableMap()
    }

    // method that the views can use to register themselves with the Model
    // once added, they are told to update and get state from the Model
    fun addView(view: IView) {
        views.add(view)
        view.updateView()
    }

    // the model uses this method to notify all of the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private fun notifyObservers() {
        for (view in views) {
            println("Model: notify $view")
            view.updateView()
        }
    }

    // model's last message describing operation
    var message: String = "datasets"
        private set

    // method that the Controller uses to tell the Model to change state
    // in a larger application there would probably be multiple entry points like this
    fun setDataset(selectionModel: String) {
        println("Model: set dataset.")
        curSelect = selectionModel
        notifyObservers()
    }

    fun getDataSet():  DataSet? {
        return datasets[curSelect]
    }

    fun getDataSets(): MutableMap<String, DataSet?> {
        return datasets
    }

    fun modifySpinnerVal(counter: Int, newValue: Int){
        for ((index, _) in datasets[curSelect]?.data!!.withIndex()){
            if ((index+1) == counter){
                println("index: $index")
                datasets[curSelect]?.data!![index] = newValue
            }
        }
        println("New value: $newValue")
        notifyObservers()
    }

}
