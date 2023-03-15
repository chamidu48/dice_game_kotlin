package com.example.dicegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class Game : AppCompatActivity() {

    var u1img:ImageView?=null
    var u2img:ImageView?=null
    var u3img:ImageView?=null
    var u4img:ImageView?=null
    var u5img:ImageView?=null
    var c1img:ImageView?=null
    var c2img:ImageView?=null
    var c3img:ImageView?=null
    var c4img:ImageView?=null
    var c5img:ImageView?=null

    var uran1:Int=1
    var uran2:Int=1
    var uran3:Int=1
    var uran4:Int=1
    var uran5:Int=1
    var cran1:Int=1
    var cran2:Int=1
    var cran3:Int=1
    var cran4:Int=1
    var cran5:Int=1

    var btnget1:Button?=null
    var btnget2:Button?=null
    var btnget3:Button?=null
    var btnget4:Button?=null
    var btnget5:Button?=null

    var ran: java.util.Random = java.util.Random()

    val sides= arrayOf<String>("one","two","three","four","five","six")
    val usernums= arrayOf<Int>(uran1,uran2,uran3,uran4,uran5)
    val computernums= arrayOf<Int>(cran1,cran2,cran3,cran4,cran5)

    //--scores--
    var user:Int=0
    var computer:Int=0
    var usersum:Int=0
    var comsum:Int=0

    var throws:Int=0
    var attempt:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        u1img=findViewById(R.id.u1)
        u2img=findViewById(R.id.u2)
        u3img=findViewById(R.id.u3)
        u4img=findViewById(R.id.u4)
        u5img=findViewById(R.id.u5)
        c1img=findViewById(R.id.c1)
        c2img=findViewById(R.id.c2)
        c3img=findViewById(R.id.c3)
        c4img=findViewById(R.id.c4)
        c5img=findViewById(R.id.c5)

        btnget1=findViewById<Button>(R.id.btnget1)
        btnget2=findViewById<Button>(R.id.btnget2)
        btnget3=findViewById<Button>(R.id.btnget3)
        btnget4=findViewById<Button>(R.id.btngett4)
        btnget5=findViewById<Button>(R.id.btnget5)

        var btnshuffle=findViewById<Button>(R.id.shuffle)
        var btnscore=findViewById<Button>(R.id.btnscore)
        var tvscore=findViewById<TextView>(R.id.score_view)
        var tvattempt=findViewById<TextView>(R.id.tvattempt)

        initialize()

        btnget1?.setOnClickListener {
            btnget1?.isEnabled=false
        }
        btnget2?.setOnClickListener {
            btnget2?.isEnabled=false
        }
        btnget3?.setOnClickListener {
            btnget3?.isEnabled=false
        }
        btnget4?.setOnClickListener {
            btnget4?.isEnabled=false
        }
        btnget5?.setOnClickListener {
            btnget5?.isEnabled=false
        }

        btnscore.isVisible=false
        btnget1?.isVisible=false
        btnget2?.isVisible=false
        btnget3?.isVisible=false
        btnget4?.isVisible=false
        btnget5?.isVisible=false

        btnshuffle.setOnClickListener {
            if(throws<2){
                generateRandomCustom()
                setImages()
                println(cran1)

                if (throws==0){btnshuffle.text="Re-throw"}
                else if(throws==1){btnshuffle.text="Re-throw again"}

                throws+=1

            }else if(throws==2){
                generateRandomCustom()
                setImages()
                user=genSum(true)
                computer=genSum(false)
                tvscore.text=user.toString()+"/"+computer.toString()
//                initialize()

                attempt+=1
                tvattempt.text="Attempt 0"+attempt

                btnshuffle.text="Throw"
                throws=0

                btnscore.isVisible=false
                btnget1?.isVisible=false
                btnget2?.isVisible=false
                btnget3?.isVisible=false
                btnget4?.isVisible=false
                btnget5?.isVisible=false
            }

            if (throws==1){
                btnscore.isVisible=true
                btnget1?.isVisible=true
                btnget2?.isVisible=true
                btnget3?.isVisible=true
                btnget4?.isVisible=true
                btnget5?.isVisible=true
            }



        }
        btnscore.setOnClickListener {
            attempt+=1
            tvattempt.text="Attempt 0"+attempt
            user=genSum(true)
            computer=genSum(false)
            tvscore.text=user.toString()+"/"+computer.toString()
//            initialize()

            btnscore.isVisible=false
            btnget1?.isVisible=false
            btnget2?.isVisible=false
            btnget3?.isVisible=false
            btnget4?.isVisible=false
            btnget5?.isVisible=false

            btnshuffle.text="Throw"

            throws=0
        }

    }

    private fun generateRandom(){
        for (i in 0..usernums.size-1){
            usernums[i]=ran.nextInt(6)
        }
        for (i in 0..computernums.size-1){
            computernums[i]=ran.nextInt(6)
        }

    }

    private fun generateRandomCustom(){

        for (i in 0..computernums.size-1){
            computernums[i]=ran.nextInt(6)
        }

        if (btnget1?.isEnabled==true){
            usernums[0]=ran.nextInt(6)
        }else btnget1?.isEnabled=true
        if (btnget2?.isEnabled==true){
            usernums[1]=ran.nextInt(6)
        }else btnget2?.isEnabled=true
        if (btnget3?.isEnabled==true){
            usernums[2]=ran.nextInt(6)
        }else btnget3?.isEnabled=true
        if (btnget4?.isEnabled==true){
            usernums[3]=ran.nextInt(6)
        }else btnget4?.isEnabled=true
        if (btnget5?.isEnabled==true){
            usernums[4]=ran.nextInt(6)
        }else btnget5?.isEnabled=true
    }

    private fun setImages(){
        u1img?.setImageResource(resources.getIdentifier(sides[usernums[0]],"drawable","com.example.dicegame"))
        u2img?.setImageResource(resources.getIdentifier(sides[usernums[1]],"drawable","com.example.dicegame"))
        u3img?.setImageResource(resources.getIdentifier(sides[usernums[2]],"drawable","com.example.dicegame"))
        u4img?.setImageResource(resources.getIdentifier(sides[usernums[3]],"drawable","com.example.dicegame"))
        u5img?.setImageResource(resources.getIdentifier(sides[usernums[4]],"drawable","com.example.dicegame"))
        c1img?.setImageResource(resources.getIdentifier(sides[computernums[0]],"drawable","com.example.dicegame"))
        c2img?.setImageResource(resources.getIdentifier(sides[computernums[1]],"drawable","com.example.dicegame"))
        c3img?.setImageResource(resources.getIdentifier(sides[computernums[2]],"drawable","com.example.dicegame"))
        c4img?.setImageResource(resources.getIdentifier(sides[computernums[3]],"drawable","com.example.dicegame"))
        c5img?.setImageResource(resources.getIdentifier(sides[computernums[4]],"drawable","com.example.dicegame"))
    }

    private fun initialize(){
        u1img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        u2img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        u3img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        u4img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        u5img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c1img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c2img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c3img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c4img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c5img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))

        for(i in 0..usernums.size-1){
            usernums[i]=1
            computernums[i]=1
        }

    }

    private fun genSum(user:Boolean):Int{
        if (user){
            for(i in 0..usernums.size-1){
                usersum=usersum+usernums[i]
            }
            return usersum
        }else{
            for(i in 0..computernums.size-1){
                comsum=comsum+computernums[i]
            }
            return comsum
        }
    }
}