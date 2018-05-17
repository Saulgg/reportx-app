package com.dev.sandia.reportx

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var saveButton : Button
    lateinit var saveText : EditText
    lateinit var text2 : EditText
    lateinit var ref : DatabaseReference
    lateinit var usersList: MutableList<Users>
    lateinit var text3 : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usersList = mutableListOf()
        saveButton = findViewById(R.id.button2)
        text3 = findViewById(R.id.editText3)
        saveText = findViewById(R.id.editText)
        saveButton.setOnClickListener {
            saveData()
        }

        ref = FirebaseDatabase.getInstance().getReference("users")

        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()){
                    usersList.clear()
                    for(x in p0.children){
                        val user = x.getValue(Users::class.java)
                        usersList.add(user!!)
                    }
                }
            }

        })


    }
    private fun saveData(){
        var email = ""
        var projectId =""
        val userName = text3.text.toString().trim()
        val name = saveText.text.toString().trim()
        if(userName.isEmpty()){
            text3.error = "Por favor ingresa un nombre"
            return
        }
        if(name.isEmpty()){
            saveText.error = "Por favor ingresa un email"
            return
        }



        for (user in usersList){
            if (saveText.text.toString() == user.email){
                email = user.email
                projectId = user.projectID

            }
        }
        if(email.isEmpty()){
            Toast.makeText(applicationContext, "No se encontró el usuario :(... Asegurate de ingresar correctamente los datos", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext, "Se encontró el usuario!", Toast.LENGTH_LONG).show()
            val intent = Intent(this@MainActivity, UserActivity::class.java)
            intent.putExtra("name", userName)
            intent.putExtra("projectID", projectId)
            startActivity(intent)

        }
        //ref = FirebaseDatabase.getInstance().getReference("names")

       // val nameId = ref.push().key

       // val newValue = Name(nameId,name)
//
       // ref.child(nameId).setValue(newValue).addOnCompleteListener{
          //  Toast.makeText(applicationContext, "El nombre se guardó correctamente", Toast.LENGTH_LONG).show()
      //  }
    }
}
