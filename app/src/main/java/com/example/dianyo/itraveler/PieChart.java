package com.example.dianyo.itraveler;

import java.text.NumberFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

//PieChart class
@SuppressLint("DrawAllocation")
public class PieChart extends View{

    private int ScrHeight;
    private int ScrWidth;

    private Paint[] arrPaintArc;
    private Paint PaintText = null;
    private Paint PaintTotal = null;
    private Paint PaintMax = null;

    private final String title[] = {"飲食","住宿","交通","娛樂","購物","其他"};

    // RGB
    private final int arrColorRgb[][] = { {148, 159, 181},  // 淺灰色
            {253, 180, 90},	// 黃色
            {52, 194, 188},	// 綠色
            {215, 124, 124},	// 桃紅色
            {39, 51, 72},		// 深藍色
            {255, 135, 195},	// 粉色
            {180, 205, 230},	// 淺藍色
            {77, 83, 97}  	// 暗灰色
    };

    public static float arrPer[] = new float[6];
    private int total = 0;

    public PieChart(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        //解决4.1版本 以下canvas.drawTextOnPath()不显示问题
        this.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        //屏幕信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScrHeight = dm.heightPixels;
        ScrWidth = dm.widthPixels;

        setMinimumHeight(ScrHeight);

        LayoutParams params = new LayoutParams(ScrHeight, ScrWidth);
        setLayoutParams(params);

        //设置边缘模糊效果
        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(1, BlurMaskFilter.Blur.INNER);

        arrPaintArc = new Paint[6];
        //Resources res = this.getResources();
        for(int i=0;i<6;i++)
        {
            arrPaintArc[i] = new Paint(Paint.ANTI_ALIAS_FLAG);	// 參數是用來消除鋸齒
            //arrPaintArc[i].setColor(res.getColor(colors[i] ));
            arrPaintArc[i].setARGB(255, arrColorRgb[i][0], arrColorRgb[i][1], arrColorRgb[i][2]); // 顏色
            arrPaintArc[i].setStyle(Paint.Style.FILL);	// 設定畫筆風格
            arrPaintArc[i].setStrokeWidth(4);			// 設定畫筆寬度
            arrPaintArc[i].setMaskFilter(PaintBGBlur);	// 設定邊緣模糊效果
        }

        // total
        PaintTotal = new Paint(Paint.ANTI_ALIAS_FLAG);
        PaintTotal.setColor(Color.BLUE);
        PaintTotal.setTextSize(100);
        PaintTotal.setTextAlign(Align.CENTER);

        // items
        PaintText = new Paint(Paint.ANTI_ALIAS_FLAG);	// 建立文字方塊
        PaintText.setColor(Color.BLACK);				// 設定文字色彩
        PaintText.setTextSize(56);						// 設定字體大小
        PaintText.setTextAlign(Align.LEFT);

        // max item
        PaintMax = new Paint(Paint.ANTI_ALIAS_FLAG);
        PaintMax.setColor(Color.RED);
        PaintMax.setTextSize(56);			// 設定字體大小
        PaintMax.setTextAlign(Align.LEFT);
        PaintMax.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void onDraw(Canvas canvas){
        // background
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE); // light blue

        // display total
        int i = 0;
        for (i=0; i<6; i++) total += arrPer[i];
        canvas.drawText("總額 : "+ total + " 元", ScrWidth / 2, 150, PaintTotal);

        // calculate percentage
        int max = 0, max_item = 0;
        for (i=0; i<6; i++) {
            if (arrPer[i] > max) {
                max = (int) arrPer[i];
                max_item = i;
            }
            arrPer[i] /= total;
            arrPer[i] *= 100;
        }

        // 設定圓心位置
        float cirX = ScrWidth / 2;
        float cirY = ScrHeight / 3 - 30;
        float radius = ScrHeight / 5 ;//150;
        // 先畫個圓確認下顯示位置
        // canvas.drawCircle(cirX,cirY,radius,PaintArcRed);

        float arcLeft = cirX - radius;
        float arcTop  = cirY - radius ;
        float arcRight = cirX + radius ;
        float arcBottom = cirY + radius ;
        RectF arcRF0 = new RectF(arcLeft ,arcTop,arcRight,arcBottom);	// 設定圓餅圖所在的矩形範圍

        Path pathArc = new Path();
        // x,y,radius,CW(clockwise)
        pathArc.addCircle(cirX,cirY,radius,Direction.CW);
        // 繪出餅圖大輪廓 此塊顏色及為第四塊暗灰色40%的餅
        canvas.drawPath(pathArc,arrPaintArc[0]);

        float CurrPer = -90f; //偏移角度
        float Percentage =  0f; //当前所占比例

        int scrOffsetW = ScrWidth / 4;
        int scrOffsetH = ScrHeight - 800;
        int scrOffsetT = 70;

        // set the digit of fractions
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);    // 2 digits
        //Resources res = this.getResources();
        for(i=0; i<5; i++) //注意循環次數
        {
            // 将百分比转换为饼图显示角度
            Percentage = 360 * (arrPer[i] / 100);
            Percentage = (float)(Math.round(Percentage *100))/100;

            // 在饼图中显示所占比例
            // 角度算法為順時針 從x軸正方向開始
            canvas.drawArc(arcRF0, CurrPer, Percentage, true, arrPaintArc[i+1]);

            // display arch
            canvas.drawRect(scrOffsetW ,scrOffsetH+15 + i * scrOffsetT,
                    scrOffsetW + 200 ,scrOffsetH - 55 + i * scrOffsetT, arrPaintArc[i+1]);
            // display percentage
            canvas.drawText(title[i] + " : " + nf.format(arrPer[i]) +"%",
                    scrOffsetW * 2,scrOffsetH + i * scrOffsetT, (i == max_item)? PaintMax : PaintText);
            // next start degree
            CurrPer += Percentage;
        }

        // the last item
        canvas.drawRect(scrOffsetW ,scrOffsetH+15 + i * scrOffsetT,
                scrOffsetW + 200 ,scrOffsetH - 55 + i * scrOffsetT, arrPaintArc[0]);

        canvas.drawText(title[5] + " : " + nf.format(arrPer[i]) +"%",
                scrOffsetW * 2,scrOffsetH + i * scrOffsetT, (i == max_item)? PaintMax : PaintText);
    }
}
