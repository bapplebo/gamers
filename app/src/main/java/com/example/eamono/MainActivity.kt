package com.example.eamono

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.ListFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()
        val recordList = RecordListFragment()
        transaction.add(R.id.fragment_container, recordList)
        transaction.commit();
    }
}

data class Person(val name: String, val dob: String, val phone: Int, val feeling: Int)

class RecordListFragment: ListFragment(), AdapterView.OnItemClickListener {
    private lateinit var adapter: RecordAdapter
    private lateinit var recordListview: ListView
    private lateinit var inflatedView: View
    val person = Person("John", "dob", 123456789, 10)
    private val recordList: List<Person> = listOf(person)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflatedView = inflater.inflate(R.layout.list_fragment, container, false)
        return inflatedView
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val empty = listView.findViewById<TextView>(R.id.empty)
        adapter = RecordAdapter(context!!, recordList)
        listView.onItemClickListener = this
        listView.emptyView = empty;

        val button: Button = inflatedView.findViewById(R.id.button_id)
        button.setOnClickListener {
            val fragment = AddHealthRecordFragment()
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        listAdapter = adapter
    }
}

class AddHealthRecordFragment: Fragment() {
    private lateinit var inflatedView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflatedView = inflater.inflate(R.layout.add_health_item_fragment, container, false)
        return inflatedView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button: Button = inflatedView.findViewById(R.id.cancel_button)
        button.setOnClickListener {
            val fragment = AddHealthRecordFragment()
            fragmentManager!!.popBackStack()
        }
    }
}

class RecordAdapter(private val context: Context, private val dataSource: List<Person>): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_item_person, parent, false)

        val nameView = rowView.findViewById(R.id.person_name) as TextView
        val dobView = rowView.findViewById(R.id.person_dob) as TextView
        val feelingView = rowView.findViewById(R.id.person_feeling) as TextView

        return rowView
    }
}