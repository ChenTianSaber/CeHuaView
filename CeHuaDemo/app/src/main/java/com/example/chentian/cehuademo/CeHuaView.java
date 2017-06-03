package com.example.chentian.cehuademo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chentian on 2017/6/1.
 */

public class CeHuaView extends View {

    Paint paint,paintCha;
    Path pathUp,pathDown,pathCha,pathCha2;

    int width;

    float radius;
    float halfRadius;

    //这里取名字有点随便，因为不知道怎么取，可以看我博客里的图，应该就能知道意思了
    //这里y表示上半部分，yy表示下半部分，x同理
    float y345,yy345;
    float y26,yy26;
    float x2,x6;

    float chaLength;
    float chaY1,chaY2;

    public CeHuaView(Context context) {
        this(context,null);
    }

    public CeHuaView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CeHuaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pathUp = new Path();
        pathDown = new Path();
        pathCha = new Path();
        pathCha2 = new Path();

        paint = new Paint();
        paint.setColor(Color.parseColor("#ff5777"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        paintCha = new Paint();
        paintCha.setColor(Color.parseColor("#ffffff"));
        paintCha.setAntiAlias(true);
        paintCha.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,widthMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);

        radius = width/2;
        halfRadius = width/4;
        y345 = radius;
        yy345 = radius;

        y26 = halfRadius;
        yy26 = halfRadius*3;

        x2 = 0;
        x6 = radius*2;

        chaLength = radius/4;
        chaY1 = chaLength*3;
        chaY2 = chaLength*2;
    }

    public void controllAnimation(int progress,float max){
        double fraction = progress/max;

        //这里根据进度改变，慢慢的变化
        y345 = (float) (radius*2*(fraction));
        yy345 = radius*2-y345;

        if(y345<radius){
            x2 = 0;
            x6 = radius*2-x2;
        }else {
            x2 = (float) (-radius*2*((fraction-0.5)/fraction));
            x6 = radius*2-x2;

            //慢慢的画出叉
            chaY1 = (float) (radius-(chaLength*2*((fraction-0.5)/fraction)));
            chaY2 = (float) (radius-(halfRadius*2*((fraction-0.5)/fraction)));
        }

        y26 = (float) (radius*fraction);
        yy26 = (float) ((radius*2)-(radius*fraction));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这里画粘稠的效果
        //这是上半部分
        pathUp.reset();
        pathUp.moveTo(0,0);
        pathUp.cubicTo(x2,y26,0,y345,radius,y345);
        pathUp.cubicTo(radius*2,y345,x6,y26,radius*2,0);
        pathUp.lineTo(0,0);
        canvas.drawPath(pathUp,paint);

        //这里画粘稠的效果
        //这是下半部分
        pathDown.reset();
        pathDown.moveTo(0,radius*2);
        pathDown.cubicTo(x2,yy26,0,yy345,radius,yy345);
        pathDown.cubicTo(radius*2,yy345,x6,yy26,radius*2,radius*2);
        pathDown.lineTo(0,radius*2);
        canvas.drawPath(pathDown,paint);

        if(y345>radius){
            //这里画那个叉叉
            //这是上半部分
            pathCha.reset();
            pathCha.moveTo((halfRadius*3)/2,radius);
            pathCha.lineTo(halfRadius,chaY1);
            pathCha.lineTo((halfRadius*3)/2,chaY2);
            pathCha.lineTo(radius,chaY1);
            pathCha.lineTo((radius*2)-((halfRadius*3)/2),chaY2);
            pathCha.lineTo(halfRadius*3,chaY1);
            pathCha.lineTo((radius*2)-((halfRadius*3)/2),radius);
            pathCha.lineTo((halfRadius*3)/2,radius);

            //这是下半部分
            pathCha.lineTo((radius*2)-((halfRadius*3)/2),radius);
            pathCha.lineTo(halfRadius*3,radius*2-chaY1);
            pathCha.lineTo((radius*2)-((halfRadius*3)/2),radius*2-chaY2);
            pathCha.lineTo(radius,radius*2-chaY1);
            pathCha.lineTo((halfRadius*3)/2,radius*2-chaY2);
            pathCha.lineTo(halfRadius,radius*2-chaY1);
            canvas.drawPath(pathCha,paintCha);
        }
    }
}
