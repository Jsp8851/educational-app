package com.fypsolutions.alphalearn

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.media.MediaPlayer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import android.speech.tts.TextToSpeech
import android.util.Log


@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    //music from "Music: www.bensound.com"
    //background <a href="https://clipartxtras.com/">clipartxtras.com</a>


    var tapToWinIndex:Int = 0
    var preTTWIndex = 0
    var tapToWinText = ""
    var tts: TextToSpeech? = null
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game)
        tts = TextToSpeech(this, this)

        loadNewIndex()


        mediaPlayer = MediaPlayer.create(this, R.raw.bensound_summer)
        mediaPlayer?.isLooping = true
        mediaPlayer?.setVolume(0.3f,0.3f)
        mediaPlayer?.start()
    }

    public override fun onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        if(mediaPlayer!=null){
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
        }
        super.onDestroy()

    }

    override fun onPause() {
        if(mediaPlayer!=null){
            mediaPlayer?.stop()
        }
        super.onPause()
    }

    override fun onResume() {
        if (mediaPlayer!=null){
            mediaPlayer?.start()
        }
        super.onResume()
    }

    fun loadNewIndex(){
        tapToWinIndex = randInt(1,9)
        if(tapToWinIndex==preTTWIndex){
            tapToWinIndex = randInt(1,9)
        }
        preTTWIndex = tapToWinIndex
        setTVToWin(tapToWinIndex)

        speakOut("Now Press "+tapToWinText)
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported")
            } else {
                //btnSpeak.setEnabled(true)
                speakOut("Welcome")
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut(text:String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun resetAllTVs(){
        tv_1.setTextColor(Color.BLACK)
        tv_2.setTextColor(Color.BLACK)
        tv_3.setTextColor(Color.BLACK)
        tv_4.setTextColor(Color.BLACK)
        tv_5.setTextColor(Color.BLACK)
        tv_6.setTextColor(Color.BLACK)
        tv_7.setTextColor(Color.BLACK)
        tv_8.setTextColor(Color.BLACK)
        tv_9.setTextColor(Color.BLACK)
    }

    fun setTVToWin(index:Int){
        resetAllTVs()
        val animation = AnimationUtils.loadAnimation(this@GameActivity, R.anim.zooming)

        when(index){
            1->{
                tv_1.setTextColor(Color.RED)
                tapToWinText = tv_1.text.toString()
                tv_1.startAnimation(animation)
            }
            2->{
                tv_2.setTextColor(Color.RED)
                tapToWinText = tv_2.text.toString()
                tv_2.startAnimation(animation)
            }
            3->{
                tv_3.setTextColor(Color.RED)
                tapToWinText = tv_3.text.toString()
                tv_3.startAnimation(animation)
            }
            4->{
                tv_4.setTextColor(Color.RED)
                tapToWinText = tv_4.text.toString()
                tv_4.startAnimation(animation)
            }
            5->{
                tv_5.setTextColor(Color.RED)
                tapToWinText = tv_5.text.toString()
                tv_5.startAnimation(animation)
            }
            6->{
                tv_6.setTextColor(Color.RED)
                tapToWinText = tv_6.text.toString()
                tv_6.startAnimation(animation)
            }
            7->{
                tv_7.setTextColor(Color.RED)
                tapToWinText = tv_7.text.toString()
                tv_7.startAnimation(animation)
            }
            8->{
                tv_8.setTextColor(Color.RED)
                tapToWinText = tv_8.text.toString()
                tv_8.startAnimation(animation)
            }
            9->{
                tv_9.setTextColor(Color.RED)
                tapToWinText = tv_9.text.toString()
                tv_9.startAnimation(animation)
            }

        }
    }




    /** Called when the user taps the Send button  */
    fun onTVClick(view: View) {
        if(tapToWinIndex==0)return

        val tv=findViewById<TextView>(view.id)
       // Toast.makeText(baseContext,tv.text.toString()+","+tapToWinIndex.toString(),Toast.LENGTH_SHORT).show()
        if(tv.text.toString().contains(tapToWinText.toString())){
            tapToWinIndex=0
            val resID = resources.getIdentifier("cheer", "raw", packageName)
            val mediaPlayer = MediaPlayer.create(this, resID)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mp ->
                mp.stop()
                mp.release()
                loadNewIndex()
            }
        }
        else{
            val resID = resources.getIdentifier("no", "raw", packageName)

            val mediaPlayer = MediaPlayer.create(this, resID)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mp ->
                mp.stop()
                mp.release()
                speakOut(tv.text.toString())
            }
        }
        val animation = AnimationUtils.loadAnimation(this@GameActivity, R.anim.bounce)
        tv.startAnimation(animation)

        //Toast.makeText(this,tv.text,Toast.LENGTH_LONG).show()
    }


    fun randInt(min: Int, max: Int): Int {

        // Usually this can be a field rather than a method variable
        val rand = Random()

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        return rand.nextInt(max - min + 1) + min
    }

}
