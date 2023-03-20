package com.example.dicegame
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
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

    var keep1com:Boolean=false
    var keep2com:Boolean=false
    var keep3com:Boolean=false
    var keep4com:Boolean=false
    var keep5com:Boolean=false

    var ran: java.util.Random = java.util.Random()

    val usersides= arrayOf<String>("userone","userone","usertwo","userthree","userfour","userfive","usersix")
    val computersides= arrayOf<String>("comone","comone","comtwo","comthree","comfour","comfive","comsix")
    var keepcomarray=arrayOf<Boolean>(keep1com,keep2com,keep3com,keep4com,keep5com)

    var usernums= arrayOf<Int>(uran1,uran2,uran3,uran4,uran5)
    var computernums= arrayOf<Int>(cran1,cran2,cran3,cran4,cran5)

    //--scores--
    var usersum:Int=0
    var comsum:Int=0
    var target:Int=101
    var comwins:Int=0
    var userwins:Int=0

    var btnshuffle:Button?=null
    var btnscore:Button?=null
    var tvscore:TextView?=null
    var tvattempt:TextView?=null
    var btnsettarget:Button?=null
    var tvtarget:TextView?=null
    var tvwins:TextView?=null
    var modeswitch:Switch?=null

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

        modeswitch=findViewById(R.id.mode)
        btnshuffle=findViewById<Button>(R.id.shuffle)
        btnscore=findViewById<Button>(R.id.btnscore)
        tvscore=findViewById<TextView>(R.id.score_view)
        tvattempt=findViewById<TextView>(R.id.tvattempt)
        btnsettarget=findViewById(R.id.btnsettarget)
        tvtarget=findViewById(R.id.tvtarget)
        tvwins=findViewById(R.id.tvwins)

        initialize()
        hideControllers()
        dialogLost= Dialog(this)
        dialogLostBinding=layoutInflater.inflate(R.layout.popup_lost,null)
        dialogWin= Dialog(this)
        dialogWinBinding=layoutInflater.inflate(R.layout.popup_win,null)
        var dialog= Dialog(this)

        //--onclick events--
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
        modeswitch?.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                modeswitch?.text="Hard"
            }else modeswitch?.text="Easy"
        }
        btnsettarget?.setOnClickListener {
            dialog.setContentView(R.layout.popup_set_target)
            dialog.setCancelable(false)

            var tfsettarget=dialog.findViewById(R.id.tftarget) as EditText
            var btnnext=dialog.findViewById(R.id.btnplay) as Button

            btnnext.setOnClickListener {
                target=Integer.parseInt(tfsettarget.text.toString())
                tvtarget?.text="Target: "+target
                dialog.dismiss()
            }
            dialog.show()
        }
        btnshuffle?.setOnClickListener {
            btnsettarget?.isEnabled=false
            modeswitch?.isEnabled=false

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
                computerValuesGenerator()
                computerValuesGenerator()
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
                computerValuesGenerator()
                computerValuesGenerator()
                setImages()
            }
            setScore()
        }
    }

    //--set the score on the screen and checking win status
    private fun setScore(){
        attempt+=1
        tvattempt?.text="Round "+attempt
        usersum=genSum(true)
        comsum=genSum(false)

        tvscore?.text=comsum.toString()+"/"+usersum.toString()

        if (usersum>=target || comsum>=target){
            print("ckeck win")
            checkWins()
        }
        hideControllers()
        resetButtons()
        btnshuffle?.text="Throw"
        throws=0
    }

    //--generate user dices randomly
    private fun generateUserValues(){
        if (btn1Tapped==false){
            usernums[0]=ran.nextInt(6)+1
        }
        if (btn2Tapped==false){
            usernums[1]=ran.nextInt(6)+1
        }
        if (btn3Tapped==false){
            usernums[2]=ran.nextInt(6)+1
        }
        if (btn4Tapped==false){
            usernums[3]=ran.nextInt(6)+1
        }
        if (btn5Tapped==false){
            usernums[4]=ran.nextInt(6)+1
        }
    }

    //--generate computer numbers radomly
    private fun generateComputerValues(){
        for (i in 0..computernums.size-1){
            computernums[i]=ran.nextInt(6)+1
        }
    }

    //--generate computer walues for two rerolls
    private fun computerValuesGenerator(){
        if (modeswitch?.isChecked == true){
            computerValuesAdvance()
        }else computerValuesEasy()
    }

    //--generate computer values accoprding to the random stratergy
    private fun computerValuesEasy(){
        check=ran.nextBoolean()
        println("genrate random numbers for computer: "+check)
        if(check){
            println("gen ran true")
            //--keep dices randomly
            keep1com=ran.nextBoolean()
            keep2com=ran.nextBoolean()
            keep3com=ran.nextBoolean()
            keep4com=ran.nextBoolean()
            keep5com=ran.nextBoolean()

            //--reroll
            rollComputerValues()
        }else{
            println("gen ran false")
            //--no rolls
        }
    }

    //--generate computer values accorfing to an algorithm--
    private fun computerValuesAdvance(){
        if (usersum>=comsum){
            for(t in 0..computernums.size-1){
                if (computernums[t]>=4){
                    keepcomarray[t]=true
                }else keepcomarray[t]=false
            }
        }
        else {
            if ((comsum-usersum)>=10){
                for(t in 0..computernums.size-1){
                    if (computernums[t]>=5){
                        keepcomarray[t]=true
                    }else keepcomarray[t]=false
                }
            }
        }
        rollComputerValues()
    }
    //--roll the computer values--
    private fun rollComputerValues(){
        if (keepcomarray[0]==false){
            computernums[0]=ran.nextInt(5)+1
        }
        if (keepcomarray[1]==false){
            computernums[1]=ran.nextInt(5)+1
        }
        if (keepcomarray[2]==false){
            computernums[2]=ran.nextInt(5)+1
        }
        if (keepcomarray[3]==false){
            computernums[3]=ran.nextInt(5)+1
        }
        if (keepcomarray[4]==false){
            computernums[4]=ran.nextInt(5)+1}
    }

    //--check winning status and show user the popup windows
    private fun checkWins(){
        print("check wins ran")
        if (usersum>=target || comsum>=target){
            print("if passed")
            if(usersum==comsum){
                //--tie state functions--
                println("tied")
                isTie=true
            }
            else if (usersum>comsum){
                isTie=false
                //--user wins
                println("user won")
                userwins+=1
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
                comwins+=1
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

    //--setting all the images as the numbers generated
    private fun setImages(){
        u1img?.setImageResource(resources.getIdentifier(usersides[usernums[0]],"drawable","com.example.dicegame"))
        u2img?.setImageResource(resources.getIdentifier(usersides[usernums[1]],"drawable","com.example.dicegame"))
        u3img?.setImageResource(resources.getIdentifier(usersides[usernums[2]],"drawable","com.example.dicegame"))
        u4img?.setImageResource(resources.getIdentifier(usersides[usernums[3]],"drawable","com.example.dicegame"))
        u5img?.setImageResource(resources.getIdentifier(usersides[usernums[4]],"drawable","com.example.dicegame"))
        c1img?.setImageResource(resources.getIdentifier(computersides[computernums[0]],"drawable","com.example.dicegame"))
        c2img?.setImageResource(resources.getIdentifier(computersides[computernums[1]],"drawable","com.example.dicegame"))
        c3img?.setImageResource(resources.getIdentifier(computersides[computernums[2]],"drawable","com.example.dicegame"))
        c4img?.setImageResource(resources.getIdentifier(computersides[computernums[3]],"drawable","com.example.dicegame"))
        c5img?.setImageResource(resources.getIdentifier(computersides[computernums[4]],"drawable","com.example.dicegame"))
    }

    //--resetting all the buttons which have changed
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

    //--initializes all the widgets like buttons, textfields, images--
    private fun initialize(){
        u1img?.setImageResource(resources.getIdentifier(usersides[0],"drawable","com.example.dicegame"))
        u2img?.setImageResource(resources.getIdentifier(usersides[0],"drawable","com.example.dicegame"))
        u3img?.setImageResource(resources.getIdentifier(usersides[0],"drawable","com.example.dicegame"))
        u4img?.setImageResource(resources.getIdentifier(usersides[0],"drawable","com.example.dicegame"))
        u5img?.setImageResource(resources.getIdentifier(usersides[0],"drawable","com.example.dicegame"))
        c1img?.setImageResource(resources.getIdentifier(computersides[0],"drawable","com.example.dicegame"))
        c2img?.setImageResource(resources.getIdentifier(computersides[0],"drawable","com.example.dicegame"))
        c3img?.setImageResource(resources.getIdentifier(computersides[0],"drawable","com.example.dicegame"))
        c4img?.setImageResource(resources.getIdentifier(computersides[0],"drawable","com.example.dicegame"))
        c5img?.setImageResource(resources.getIdentifier(computersides[0],"drawable","com.example.dicegame"))

        for(i in 0..usernums.size-1){
            usernums[i]=1
            computernums[i]=1
        }

        tvwins?.text="H:"+userwins+"/C:"+comwins

    }

    //--get the sum of values by inserting whether user or computer
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("comwins",comwins)
        outState.putInt("userwins",userwins)
        outState.putInt("target",target)
        outState.putInt("comsum",comsum)
        outState.putInt("usersum",usersum)
        outState.putBoolean("easy/hard", modeswitch?.isChecked!!)
        outState.putIntArray("usernums",usernums.toIntArray())
        outState.putIntArray("comnums",computernums.toIntArray())
        outState.putInt("attempt",attempt)
        outState.putInt("throws",throws)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        comwins=savedInstanceState.getInt("comwins")
        userwins=savedInstanceState.getInt("userwins")
        tvwins?.text="H:"+userwins+"/C:"+comwins

        target=savedInstanceState.getInt("target")
        tvtarget?.text="Target: "+target

        comsum=savedInstanceState.getInt("comsum")
        usersum=savedInstanceState.getInt("usersum")
        tvscore?.text=comsum.toString()+"/"+usersum.toString()

        modeswitch?.isChecked=savedInstanceState.getBoolean("easy/hard")
        if (modeswitch?.isEnabled==true){
            modeswitch?.text="Hard"
        }

        usernums= savedInstanceState.getIntArray("usernums")?.toTypedArray()!!
        computernums= savedInstanceState.getIntArray("comnums")?.toTypedArray()!!
        attempt=savedInstanceState.getInt("attempt")
        tvattempt?.text="Round "+attempt

        throws=savedInstanceState.getInt("throws")
        if (throws!=0){
            showControllers()
            btnsettarget?.isEnabled=false
            modeswitch?.isEnabled=false
        }else if (attempt>1){
            btnsettarget?.isEnabled=false
            modeswitch?.isEnabled=false
        }
        if (throws==0){
            btnshuffle?.text="Re-throw"}
        else if(throws==1){
            btnshuffle?.text="Re-throw again"}
        setImages()
    }
}

/**
 *
 * ALGORITHM
 * First, the computer will check whether the user's sum is greater than or equal to the computer sum.
 * If so, computer will keep 6,5,4 dices if he hes rolled before and roll the others.
 *
 * If user is not greater than comuter sum, computer checks again whether user is behind the
 * computer sum value in 10 values, If its true, computer will again keep 5 and 6 values if has previously rolled.
 *
 * othervise he won't keep any dices. All the dices will rolled randomly
 *
 * **/