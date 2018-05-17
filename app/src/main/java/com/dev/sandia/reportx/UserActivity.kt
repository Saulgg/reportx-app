package com.dev.sandia.reportx

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*

class UserActivity : AppCompatActivity() {
    lateinit var userName:String
    lateinit var projectID:String
    lateinit var ref : DatabaseReference
    lateinit var ticketList: MutableList<Tickets>
    lateinit var texto1 : TextView
    lateinit var addButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        addButton = findViewById(R.id.button3)
        texto1 = findViewById(R.id.textView)

        val rv = findViewById<RecyclerView>(R.id.recyclerView1)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        ticketList = mutableListOf()
        userName = intent.getStringExtra("name")
        projectID = intent.getStringExtra("projectID")

        ref = FirebaseDatabase.getInstance().getReference("proyects/"+projectID).child("tickets")

        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    ticketList.clear()
                    for(x in p0.children){
                        val ticket = x.getValue(Tickets::class.java)
                        ticketList.add(ticket!!)

                    }

                }
                var adapter = RecyclerAdapter(ticketList);
                rv.adapter = adapter
            }




        })
        addButton.setOnClickListener{
            changeIntent()
        }

    }
    private fun changeIntent(){
        val intent = Intent(this@UserActivity, AddTicketActivity::class.java)
        intent.putExtra("name", userName)
        intent.putExtra("projectID", projectID)
        startActivity(intent)
    }
}
