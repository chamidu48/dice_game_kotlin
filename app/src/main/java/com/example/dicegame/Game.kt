package com.example.dicegame

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    var btn1Tapped:Boolean=false
    var btn2Tapped:Boolean=false
    var btn3Tapped:Boolean=false
    var btn4Tapped:Boolean=false
    var btn5Tapped:Boolean=false

    var ran: java.util.Random = java.util.Random()

    val sides= arrayOf<String>("one","one","two","three","four","five","six")
    val usernums= arrayOf<Int>(uran1,uran2,uran3,uran4,uran5)
    val computernums= arrayOf<Int>(cran1,cran2,cran3,cran4,cran5)

    //--scores--
    var usersum:Int=0
    var comsum:Int=0
    var target:Int=109

    var btnshuffle:Button?=null
    var btnscore:Button?=null
    var tvscore:TextView?=null
    var tvattempt:TextView?=null

    var throws:Int=0
    var attempt:Int=1

    var dialogLost:Dialog?=null
    var dialogLostBinding:View?=null
    var dialogWin:Dialog?=null
    var dialogWinBinding:View?=null

    //--for computer random rolls--
    var check:Boolean=true

    //--for tie state--
    var isTie:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        target=intent.getIntExtra("EXTRA_DATA",109)
        println(target)

        dialogLost= Dialog(this)
        dialogLostBinding=layoutInflater.inflate(R.layout.popup_lost,null)

        dialogWin= Dialog(this)
        dialogWinBinding=layoutInflater.inflate(R.layout.popup_win,null)

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

        btnshuffle=findViewById<Button>(R.id.shuffle)
        btnscore=findViewById<Button>(R.id.btnscore)
        tvscore=findViewById<TextView>(R.id.score_view)
        tvattempt=findViewById<TextView>(R.id.tvattempt)

        initialize()

        btnget1?.setOnClickListener {
            btn1Tapped=!btn1Tapped
            if(btn1Tapped){btnget1?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget1?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }
        btnget2?.setOnClickListener {
            btn2Tapped=!btn2Tapped
            if(btn2Tapped){btnget2?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget2?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }
        btnget3?.setOnClickListener {
            btn3Tapped=!btn3Tapped
            if(btn3Tapped){btnget3?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget3?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }
        btnget4?.setOnClickListener {
            btn4Tapped=!btn4Tapped
            if(btn4Tapped){btnget4?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget4?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }
        btnget5?.setOnClickListener {
            btn5Tapped=!btn5Tapped
            if(btn5Tapped){btnget5?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget5?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }

        hideControllers()

        btnshuffle?.setOnClickListener {
            if(isTie){
                //--if the game tied--
                println("tied")
                hideControllers()
                generateUserValues()
                generateComputerValues()
                setImages()
                setScore()
            }
            //--if the game not tied
            if(throws<2){
                generateUserValues()

                if (throws==0){
                    println(throws)
                    generateComputerValues()
                    btnshuffle?.text="Re-throw"}
                else if(throws==1){
                    btnshuffle?.text="Re-throw again"}

                setImages()
                throws+=1

                resetButtons()

            }else if(throws==2){
                println(throws)
                generateUserValues()
                generateComputerValuesRandom()
                generateComputerValuesRandom()
                setImages()
                setScore()
                throws=0
            }

            if (throws==1){
                showControllers()
            }
        }
        btnscore?.setOnClickListener {
            if(throws==2){
                generateComputerValuesRandom()
                generateComputerValuesRandom()
                setImages()
            }
            setScore()
        }
    }

    private fun setScore(){
        attempt+=1
        tvattempt?.text="Round "+attempt
        usersum=genSum(true)
        comsum=genSum(false)

        tvscore?.text=comsum.toString()+"/"+usersum.toString()

        if (usersum>=target || comsum>=target){
            checkWins()
        }
        hideControllers()
        resetButtons()
        btnshuffle?.text="Throw"
        throws=0
    }

    private fun generateUserValues(){
        if (btn1Tapped==false){
            usernums[0]=ran.nextInt(5)+1
        }
        if (btn2Tapped==false){
            usernums[1]=ran.nextInt(5)+1
        }
        if (btn3Tapped==false){
            usernums[2]=ran.nextInt(5)+1
        }
        if (btn4Tapped==false){
            usernums[3]=ran.nextInt(5)+1
        }
        if (btn5Tapped==false){
            usernums[4]=ran.nextInt(5)+1
        }
    }

    private fun generateComputerValues(){
        for (i in 0..computernums.size-1){
            computernums[i]=ran.nextInt(6)+1
        }
    }

    private fun generateComputerValuesRandom(){
        check=ran.nextBoolean()
        println("genrate random numbers for computer: "+check)
        if(check){
            println("gen ran true")
            //--keep dices randomly
            btn1Tapped=ran.nextBoolean()
            btn2Tapped=ran.nextBoolean()
            btn3Tapped=ran.nextBoolean()
            btn4Tapped=ran.nextBoolean()
            btn5Tapped=ran.nextBoolean()

            //--reroll
            if (btn1Tapped==false){
                computernums[0]=ran.nextInt(5)+1
            }
            if (btn2Tapped==false){
                computernums[1]=ran.nextInt(5)+1
            }
            if (btn3Tapped==false){
                computernums[2]=ran.nextInt(5)+1
            }
            if (btn4Tapped==false){
                computernums[3]=ran.nextInt(5)+1
            }
            if (btn5Tapped==false){
                computernums[4]=ran.nextInt(5)+1
            }
        }else{
            println("gen ran false")
            //--no rolls
        }
    }

    private fun checkWins(){
        print("check wins ran")
        if (usersum>=target || comsum>=target){
            if(usersum==comsum){
                //--tie state functions--
                println("tied")
                isTie=true
            }
            else if (usersum>comsum){
                isTie=false
                //--user wins
                println("user won")
                dialogWinBinding?.let { dialogWin?.setContentView(it) }
                dialogWin?.setCancelable(true)
                dialogWin?.setCanceledOnTouchOutside(false)
                dialogWin?.setOnCancelListener{
                    finish()
                }
                dialogWin?.show()
            } else if(comsum>usersum){
                isTie=false
                //--user lost
                println("use lost")
                dialogLostBinding?.let { dialogLost?.setContentView(it) }
                dialogLost?.setCancelable(true)
                dialogLost?.setCanceledOnTouchOutside(false)
                dialogLost?.setOnCancelListener{
                    finish()
                }
                dialogLost?.show()
            }
        }
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

    private fun resetButtons(){
        btnget1?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        btnget2?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        btnget3?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        btnget4?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        btnget5?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))

        btn1Tapped=false
        btn2Tapped=false
        btn3Tapped=false
        btn4Tapped=false
        btn5Tapped=false
    }

    private fun hideControllers(){

        println("hide")
        btnscore?.isVisible=false
        btnget1?.isVisible=false
        btnget2?.isVisible=false
        btnget3?.isVisible=false
        btnget4?.isVisible=false
        btnget5?.isVisible=false
    }

    private fun showControllers(){
        btnscore?.isVisible=true
        btnget1?.isVisible=true
        btnget2?.isVisible=true
        btnget3?.isVisible=true
        btnget4?.isVisible=true
        btnget5?.isVisible=true
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
                print(usernums[i])
                usersum=usersum+usernums[i]
            }
            return usersum
        }else{
            for(k in 0..computernums.size-1){
                print(computernums[k])
                comsum=comsum+computernums[k]
            }
            return comsum
        }
    }
}