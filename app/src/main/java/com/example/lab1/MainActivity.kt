package com.example.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val startButton=findViewById<Button>(R.id.startButton)
        val clckTextView=findViewById<TextView>(R.id.textView)
        val numberTextView=findViewById<EditText>(R.id.nnumber)
        val resTextView=findViewById<TextView>(R.id.resultID)
        var progBar=findViewById<ProgressBar>(R.id.progressBar)

        startButton.setOnClickListener{
            if(clckTextView.text=="No click")
                clckTextView.text="Click!";
            else
                clckTextView.text="No click"
            val inputText=numberTextView.text.toString()
            val n=inputText.toIntOrNull()
            if(n==null||n<0)
                return@setOnClickListener

            lifecycleScope.launch (Dispatchers.IO){
                val result = fibonacci(n,resTextView,progBar)

                withContext(Dispatchers.Main){
                    resTextView.text="Fibonacci: $result"
                }

            }
        }
    }
    
    private suspend fun fibonacci(n: Int, rtv: TextView, prgb: ProgressBar): Long{
        if(n<=1) return 0;
        var a=0L
        var b=1L
        for(i in 2..n){
            val temp=a+b
            a=b
            b=temp
            delay(10)
            withContext(Dispatchers.Main){
                val prg=(i.toDouble()/n)
                println(prg)
                rtv.text="Current Fib: $b"
                prgb.progress= (prg*100.0).roundToInt()
            }




        }
        return b
    }
}