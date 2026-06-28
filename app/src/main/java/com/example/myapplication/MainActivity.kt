package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class MainActivity : AppCompatActivity() {

    private lateinit var txtBalance: TextView
    private lateinit var txtIncome: TextView
    private lateinit var txtExpense: TextView
    private lateinit var btnBills: Button
    private lateinit var recyclerTransactions: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var db: FirebaseFirestore

    private var listener: ListenerRegistration? = null

    private var totalIncome = 0.0
    private var totalExpense = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        txtBalance = findViewById(R.id.txtBalance)
        txtIncome = findViewById(R.id.txtIncome)
        txtExpense = findViewById(R.id.txtExpense)
        btnBills = findViewById(R.id.btnBills)
        recyclerTransactions = findViewById(R.id.recyclerTransactions)
        fabAdd = findViewById(R.id.fabAdd)

        recyclerTransactions.layoutManager = LinearLayoutManager(this)

        db = FirebaseFirestore.getInstance()

        loadTransactions()

        fabAdd.setOnClickListener {
            addSampleTransaction()
        }

        btnBills.setOnClickListener {
            Toast.makeText(this, "Bills / Notes Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadTransactions() {

        listener = db.collection("transactions")
            .addSnapshotListener { snapshots, error ->

                if (error != null) {
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                totalIncome = 0.0
                totalExpense = 0.0

                snapshots?.documents?.forEach { document ->

                    val amount = document.getDouble("amount") ?: 0.0
                    val type = document.getString("type") ?: ""

                    if (type.equals("Income", true)) {
                        totalIncome += amount
                    } else {
                        totalExpense += amount
                    }

                }

                val balance = totalIncome - totalExpense

                txtIncome.text = "₱%.2f".format(totalIncome)
                txtExpense.text = "₱%.2f".format(totalExpense)
                txtBalance.text = "₱%.2f".format(balance)

                // TODO:
                // recyclerTransactions.adapter =
                // TransactionAdapter(snapshots.toObjects(Transaction::class.java))

            }
    }

    private fun addSampleTransaction() {

        val transaction = hashMapOf(

            "title" to "Lunch",
            "amount" to 150.0,
            "type" to "Expense",
            "date" to System.currentTimeMillis()

        )

        db.collection("transactions")
            .add(transaction)
            .addOnSuccessListener {

                Toast.makeText(this, "Transaction Added", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {

                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

            }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.remove()
    }
}