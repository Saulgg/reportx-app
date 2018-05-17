package com.dev.sandia.reportx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_ticket.*

class AddTicketActivity : AppCompatActivity() {
    lateinit var projectID: String
    lateinit var userName: String
    lateinit var ref: DatabaseReference
    lateinit var buildingList: MutableList<Buildings>
    lateinit var RoomsList: MutableList<Rooms>
    lateinit var roomtext: MutableList<String>
    lateinit var build: MutableList<String>
    lateinit var texto1: TextView
    lateinit var distintos: List<String>
    lateinit var btn : Button
    lateinit var texto2 : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ticket)
        buildingList = mutableListOf()
        texto2 = findViewById(R.id.editText2)
        RoomsList = mutableListOf()
        roomtext = mutableListOf()
        build = mutableListOf()
        userName = intent.getStringExtra("name")
        projectID = intent.getStringExtra("projectID")
        texto1 = findViewById(R.id.textView6)
        ref = FirebaseDatabase.getInstance().getReference("proyects/" + projectID)
        distintos = listOf()
        btn = findViewById(R.id.button5)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()) {
                    buildingList.clear()
                    for (x in p0.child("buildings").children) {
                        val building = x.getValue(Buildings::class.java)
                        buildingList.add(building!!)

                    }
                    for (y in p0.child("rooms").children) {
                        val room = y.getValue(Rooms::class.java)
                        RoomsList.add(room!!)

                    }

                }
                build.clear()
                for (x in buildingList) {
                    build.add(x.name)
                }
                roomtext.clear()
                for (x in RoomsList) {
                    if (x.name !in roomtext) roomtext.add(x.name)
                }
                roomtext = roomtext.toMutableList()

            }

        })
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,build)
        autoCompleteTextView.threshold=0
        autoCompleteTextView.setAdapter(adapter)

        var adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1,roomtext)
        autoCompleteTextView2.threshold=0
        autoCompleteTextView2.setAdapter(adapter2)

        var urgency = arrayOf("baja","mediana","alta","inmediata")
        var adapter3 = ArrayAdapter(this, android.R.layout.simple_list_item_1,urgency)
        autoCompleteTextView3.threshold=0
        autoCompleteTextView3.setAdapter(adapter3)
        btn.setOnClickListener{
            saveData(autoCompleteTextView.text.toString(),autoCompleteTextView2.text.toString(), texto2.text.toString(),autoCompleteTextView3.text.toString())
        }
    }
    private fun saveData(building: String,room: String, message: String, priority: String ){
        ref = FirebaseDatabase.getInstance().getReference("proyects/" + projectID).child("tickets")

        val ticketId = ref.push().key

        val newTicket = Tickets(ticketId,building,message, userName, priority, room, "Sin asignar")
         ref.child(ticketId).setValue(newTicket).addOnCompleteListener{
         Toast.makeText(applicationContext, "El ticket se guardó correctamente", Toast.LENGTH_LONG).show()
             finish()
         }
    }
}
