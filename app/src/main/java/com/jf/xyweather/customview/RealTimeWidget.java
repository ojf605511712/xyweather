package com.jf.xyweather.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jf.xyweather.R;

/**
 * Created by JF on 2016/6/24.
 * A short-term-forecast widget in the "CityWeatherFragment"
 */
public class RealTimeWidget extends FrameLayout
        implements Animation.AnimationListener, Runnable{

    private TextView weatherTypeTv;//TextView to show weather type(such as sunny or cloudy)
    private TextView temperatureTv;//TextView to show real-time temperature

    /*viewGroup include the "windOrBodyFeelingTv" and "humilityOrAirPressureTv"
      viewGroup has an animation*/
    private ViewGroup viewGroup;
    private TextView windOrBodyFeelingTv;
    private TextView humilityOrAirPressureTv;

    private String wind;
    private float humility;
    private float bodyFeelingTemperature;
    private float airPressure;

    private AlphaAnimation becomeTransparent;
    private AlphaAnimation becomeNoTransparent;

    private String showWhichInfo = SHOW_BODY_FELLING_AND_AIR_PRESSURE;
    private static final String SHOW_WIND_AND_HUMILITY = "windAndHumility";
    private static final String SHOW_BODY_FELLING_AND_AIR_PRESSURE = "bodyFellingAndAirPressure";

    public RealTimeWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_real_time_weather, this);

        weatherTypeTv = (TextView) findViewById(R.id.tv_custom_view_real_time_weather_weather_type);
        temperatureTv = (TextView) findViewById(R.id.tv_layout_real_time_weather_temperature);

        viewGroup = (ViewGroup)findViewById(R.id.rl_layout_real_time_weather);
        windOrBodyFeelingTv = (TextView) findViewById(R.id.tv_layout_real_time_weather_wind_or_body_feeling_temperature);
        humilityOrAirPressureTv = (TextView)findViewById(R.id.tv_layout_real_time_weather_humility_or_air_pressure);
    }

    /**
     * set the weather type
     * @param weatherType weather type
     */
    public void setWeatherType(String weatherType){
        weatherTypeTv.setText(weatherType);
    }

    /**
     * set the temperature
     * @param temperature temperature
     */
    public void setTemperature(double temperature){
        temperatureTv.setText((int)temperature+"°");
    }

    /**
     * set the wind, humility,body feeling temperature and air pressure
     * @param wind
     * @param humility
     * @param bodyFeelingTemperature
     * @param airPressure
     */
    public void setRealTimeWeather(String wind, float humility, float bodyFeelingTemperature, float airPressure){
        this.wind = wind;
        this.humility = humility;
        this.bodyFeelingTemperature = bodyFeelingTemperature;
        this.airPressure = airPressure;
//        setWindText(wind);
//        setHumilityText(humility);
        if(becomeTransparent == null){
            setWindText(wind);
            setHumilityText(humility);
            initAnimation();
            viewGroup.startAnimation(becomeTransparent);
        }
    }

    private void setWindText(String wind){
        windOrBodyFeelingTv.setText(wind+"级");
    }
    private void setHumilityText(float humility){
        humilityOrAirPressureTv.setText((int)humility+"%");
    }
    private void setBodyFeelingText(float bodyFeelingTemperature){
        windOrBodyFeelingTv.setText((int)bodyFeelingTemperature+"℃");
    }
    private void setAirPressureText(float airPressure){
        humilityOrAirPressureTv.setText((int)airPressure+"");
    }

    //initial the animation for the viewGroup
    private void initAnimation(){
        //initial an animation to make viewGroup become transparent
        becomeTransparent = new AlphaAnimation(1f, 0f);
        becomeTransparent.setDuration(2000);
        becomeTransparent.setAnimationListener(this);

        //initial an animation to make viewGroup become no transparent
        becomeNoTransparent = new AlphaAnimation(0f, 1f);
        becomeNoTransparent.setDuration(1000);
        becomeNoTransparent.setAnimationListener(this);
    }

    /*override the method of "AnimationListener"_start*/
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(becomeTransparent == animation){
            //if the viewGroup become transparent,determined which information should be showed when it become no transparent again
            if(showWhichInfo.equals(SHOW_WIND_AND_HUMILITY)){
                setWindText(wind);
                setHumilityText(humility);
                showWhichInfo = SHOW_BODY_FELLING_AND_AIR_PRESSURE;
            }else{
                setBodyFeelingText(bodyFeelingTemperature);
                setAirPressureText(airPressure);
                showWhichInfo = SHOW_WIND_AND_HUMILITY;
            }
            viewGroup.startAnimation(becomeNoTransparent);
        }else{
            //if the viewGroup become no transparent,post a runnable to make it become transparent after four seconds
            postDelayed(this, 2000);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    /*override the method of "AnimationListener"_end*/

    @Override
    public void run() {
        viewGroup.startAnimation(becomeTransparent);
    }
}