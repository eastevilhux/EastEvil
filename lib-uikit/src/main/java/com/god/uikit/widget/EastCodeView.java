package com.god.uikit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.god.uikit.R;


@SuppressLint("AppCompatCustomView")
public class EastCodeView extends EditText {

    private int mHeight = 120;
    private int mWidth = 50;
    private int mLineWidth = 1;
    private int mLineColor = Color.parseColor("#ff666666");
    private int mFillColor = Color.WHITE;

    private int mFocusLineWidth = mLineWidth;
    private int mFocusLineColor = Color.BLACK;
    private int mFocusFillColor = Color.WHITE;

    private int mEmployLineWidth = mLineWidth;
    private int mEmployLineColor = mLineColor;
    private int mEmployFillColor = mFillColor;

    private int mTextColor = Color.BLACK;
    private int mTextSize = 64;

    private int borderRadius = 0;
    private int circleColor = mTextColor;
    private int circleRadius;

    private boolean isConceal;
    private boolean isPassword = false;
    private String mReplaceString;
    private Drawable mReplaceDrawable;

    private Paint paintText; //文字画笔
    private Paint borderPaint; //边框
    private Paint fillPaint;

    private int startX = 0;//开始坐标
    private int count = 6; //几个输入框
    private Paint circlePaint;
    private boolean isContinuous; //是否连续
    private int intervalWidth; //间隔距离

    private int position = 0;//当前输入的位置

    private boolean isContinuousRepeatChar;//是否过过滤连续重复字符默认不过滤
    private boolean isContinuousChar;//是否过过滤连续数字与连续字符
    private boolean isInvokingKeyboard;//是否使用系统键盘

    private InputOverListener mInputOverListener;
    private float[] radiiLeft;          //必须为8个值, 对应四个角, 俩俩一堆为xy.;
    private float[] radiiRight;          //必须为8个值, 对应四个角, 俩俩一堆为xy.
    private float[] radiAll;          //必须为8个值, 对应四个角, 俩俩一堆为xy.


    /**
     * 获取左边圆角
     *
     * @return
     */
    private float[] getLeftRadius() {
        if (radiiLeft == null) {
            radiiLeft = new float[]{
                    borderRadius,
                    borderRadius,
                    0f,
                    0f,
                    0f,
                    0f,
                    borderRadius,
                    borderRadius
            };
        }
        return radiiLeft;
    }


    /**
     * 获取左边圆角
     *
     * @return
     */
    private float[] getRightRadius() {
        if (radiiRight == null) {
            radiiRight = new float[]{
                    0f,
                    0f,
                    borderRadius,
                    borderRadius,
                    borderRadius,
                    borderRadius,
                    0f,
                    0f
            };
        }
        return radiiRight;
    }

    /**
     * 获取圆角
     *
     * @return
     */
    private float[] getAllRadius() {
        if (radiAll == null) {
            radiAll = new float[]{
                    borderRadius,
                    borderRadius,
                    borderRadius,
                    borderRadius,
                    borderRadius,
                    borderRadius,
                    borderRadius,
                    borderRadius
            };
        }
        return radiAll;
    }

    private ContinuousRepeatCharInputFilter mContinuousRepeatCharInputFilter = null;

    public EastCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.EastCodeView);

        mHeight = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_height, mHeight);
        mWidth = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_width, mWidth);
        mLineWidth = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_lineWidth, mLineWidth);
        mLineColor = typedArray.getColor(R.styleable.EastCodeView_lineColor, mLineColor);
        mFillColor = typedArray.getColor(R.styleable.EastCodeView_fillColor, mFillColor);

        mFocusLineWidth = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_focusLineWidth, mLineWidth);
        mFocusLineColor = typedArray.getColor(R.styleable.EastCodeView_focusLColor, mFocusLineColor);
        mFocusFillColor = typedArray.getColor(R.styleable.EastCodeView_focusFillColor, mFocusFillColor);

        mEmployLineWidth = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_employLineWidth, mLineWidth);
        mEmployLineColor = typedArray.getColor(R.styleable.EastCodeView_employLColor, mLineColor);
        mEmployFillColor = typedArray.getColor(R.styleable.EastCodeView_employFillColor, mFillColor);

        mTextColor = typedArray.getColor(R.styleable.EastCodeView_textColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_textSize, mTextSize);

        isConceal = typedArray.getBoolean(R.styleable.EastCodeView_conceal, false);
        mReplaceString = typedArray.getString(R.styleable.EastCodeView_replaceString);
        mReplaceDrawable = typedArray.getDrawable(R.styleable.EastCodeView_replaceDrawable);

        borderRadius = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_borderRadius, borderRadius);
        circleColor = typedArray.getColor(R.styleable.EastCodeView_circleColor, mTextColor);
        circleRadius = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_circleRadius, mHeight / 8);
        count = typedArray.getInt(R.styleable.EastCodeView_count, 6);
        isContinuous = typedArray.getBoolean(R.styleable.EastCodeView_isContinuous, true);
        intervalWidth = typedArray.getDimensionPixelSize(R.styleable.EastCodeView_intervalWidth, 40);
        isContinuousRepeatChar = typedArray.getBoolean(R.styleable.EastCodeView_isContinuousRepeatChar, false);
        isContinuousChar = typedArray.getBoolean(R.styleable.EastCodeView_isContinuousChar, false);
        isInvokingKeyboard = typedArray.getBoolean(R.styleable.EastCodeView_isInvokingKeyboard, true);

        isPassword = typedArray.getBoolean(R.styleable.EastCodeView_isPassword,false);

        setBackgroundColor(Color.TRANSPARENT);

        initPaint();

        if (circleRadius >= mHeight) {
            circleRadius = mHeight * 2 / 5;
        }

        if (isContinuousChar && isContinuousChar == isContinuousRepeatChar) {
            mContinuousRepeatCharInputFilter = new ContinuousRepeatCharInputFilter(isContinuousChar);
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(count), mContinuousRepeatCharInputFilter});
        } else  {
            mContinuousRepeatCharInputFilter = new ContinuousRepeatCharInputFilter(isContinuousChar, isContinuousRepeatChar);
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(count), mContinuousRepeatCharInputFilter});
        }
        setEnabled(isInvokingKeyboard);

    }

    @Override
    public void setCursorVisible(boolean visible) {
        super.setCursorVisible(false);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isContinuous) {
            startX = (w - count * mWidth) / 2;
        } else {
            startX = (w - count * mWidth - (count - 1) * intervalWidth) / 2;
        }

    }


    private void initPaint() {

        //文字画笔
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setAntiAlias(true);
        paintText.setTextSize(mTextSize);
        paintText.setColor(mTextColor);

        //边框画笔
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStrokeWidth(mLineWidth);
        borderPaint.setColor(mLineColor);
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);

        //填充画笔
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(mFillColor);
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPeripheryBorder(canvas, position);
        if (isConceal) {
            drawConceal(canvas);
        } else {
            drawText(canvas);
        }
    }

    /**
     * 修改显示文字还是遮盖
     *
     * @param conceal
     */
    public void setConceal(boolean conceal) {
        isConceal = conceal;
        invalidate();
    }

    enum InputStatus {
        No_Input, Have_Input, To_Input;
    }

    public void setCodeWidth(int width){
        this.mWidth = width;
    }

    public void setCodeHeight(int height){
        this.mHeight = height;
    }

    /**
     * @return
     */
    private Paint getBorderPaint(InputStatus status) {
        if (InputStatus.No_Input == status) {
            borderPaint.setStrokeWidth(mLineWidth);
            borderPaint.setColor(mLineColor);
        } else if (InputStatus.To_Input == status) {
            borderPaint.setStrokeWidth(mFocusLineWidth);
            borderPaint.setColor(mFocusLineColor);
        } else if (InputStatus.Have_Input == status) {
            borderPaint.setStrokeWidth(mEmployLineWidth);
            borderPaint.setColor(mEmployLineColor);
        }
        return borderPaint;
    }

    private Paint getFillPaint(InputStatus status) {
        if (InputStatus.No_Input == status) {
            fillPaint.setColor(mFillColor);
        } else if (InputStatus.To_Input == status) {
            fillPaint.setColor(mFocusFillColor);
        } else if (InputStatus.Have_Input == status) {
            fillPaint.setColor(mEmployFillColor);
        }

        return fillPaint;
    }


    private Paint getPaintCircle() {
        if (circlePaint == null) {
            circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setAntiAlias(true);
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setColor(circleColor);
        }

        return circlePaint;
    }


    /**
     * 绘制隐藏图标
     *
     * @param canvas
     */
    public void drawConceal(Canvas canvas) {

        if (mReplaceDrawable != null) {
            drawDrawableConceal(canvas);
        } else if (!TextUtils.isEmpty(mReplaceString)) {
            drawReplaceText(canvas);
        } else {
            drawCircle(canvas);
        }

    }

    private void drawDrawableConceal(Canvas canvas) {
        char[] chars = getText().toString().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int left = startX + i * mWidth + (isContinuous ? 0 : i * intervalWidth);
            int top = 1;
            drawDrawable(left, top, canvas);
        }
    }

    /**
     * 绘制占位图片
     *
     * @param starX
     * @param startY
     * @param canvas
     */
    private void drawDrawable(int starX, int startY, Canvas canvas) {
        Rect replaceDrawableRect;
        // 计算图标绘制的坐标
        int intrinsicWidth = mReplaceDrawable.getIntrinsicWidth();
        int intrinsicHeight = mReplaceDrawable.getIntrinsicHeight();
        int drawWidth = intrinsicWidth;
        int drawHeight = intrinsicHeight;

        // 限制图标的大小，防止图标超出按键
        if (drawWidth > mWidth) {
            drawWidth = mWidth / 3;
            drawHeight = drawWidth * intrinsicHeight / intrinsicWidth;
        }
        if (drawHeight > mHeight) {
            drawHeight = mWidth / 3;
            drawWidth = drawHeight * intrinsicWidth / intrinsicHeight;
        }

        // 获取图标绘制的坐标
        int left = starX + (mWidth - drawWidth) / 2;
        int top = startY + (mHeight - drawHeight) / 2;
        replaceDrawableRect = new Rect(left, top,
                left + drawWidth, top + drawHeight);

        // 绘制的图标
        mReplaceDrawable.setBounds(replaceDrawableRect.left,
                replaceDrawableRect.top, replaceDrawableRect.right,
                replaceDrawableRect.bottom);
        mReplaceDrawable.draw(canvas);
    }

    /**
     * 绘制圆
     *
     * @param canvas
     */
    public void drawCircle(Canvas canvas) {
        char[] chars = getText().toString().toCharArray();

        for (int i = 0, n = chars.length; i < n; i++) {
            //绘制默认占位符
            canvas.drawCircle(
                    (startX + i * mWidth + mWidth / 2 + (isContinuous ? 0 : i * intervalWidth)),
                    (mHeight / 2),
                    circleRadius,
                    getPaintCircle()
            );
        }
    }


    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas) {
        char[] chars = getText().toString().toCharArray();
        String txt = "";
        for (int i = 0, n = chars.length; i < n; i++) {
            //绘制输入状态
            Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
            int baseLineY = (int) (mWidth / 2 - fontMetrics.top / 2 - fontMetrics.bottom / 2);
            if(isPassword){
                txt = "*";
            }else{
                txt = String.valueOf(chars[i]);
            }
            canvas.drawText(
                    txt,
                    (startX + i * mWidth + mWidth / 2 + (isContinuous ? 0 : i * intervalWidth)),
                    baseLineY,
                    paintText
            );

        }

    }


    /**
     * 绘制遮盖文字
     */
    private void drawReplaceText(Canvas canvas) {
        char[] chars = getText().toString().toCharArray();

        for (int i = 0, n = chars.length; i < n; i++) {
            //绘制输入状态
            Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
            int baseLineY = (int) (mHeight / 2 - fontMetrics.top / 2 - fontMetrics.bottom / 2);
            canvas.drawText(
                    String.valueOf(mReplaceString),
                    (startX + i * mWidth + mWidth / 2 + (isContinuous ? 0 : i * intervalWidth)),
                    baseLineY,
                    paintText
            );

        }

    }


    /**
     * 背景框绘制
     *
     * @param canvas
     * @param position
     */
    private void drawPeripheryBorder(Canvas canvas, int position) {

        for (int i = 0; i < count; i++) {
            float left = startX + i * mWidth;
            float top = 0;
            float right = left + mWidth;
            float bottom = mHeight;
            if (!isContinuous) {
                left = left + i * intervalWidth;
                right = left + mWidth;
            }

            if (i < position) {

                if (i == 0) {
                    PeripheryBorder(canvas, left, top, right, bottom, 1, InputStatus.Have_Input);
                } else if (i == count - 1) {
                    PeripheryBorder(canvas, left, top, right, bottom, 2, InputStatus.Have_Input);
                } else {
                    PeripheryBorder(canvas, left, top, right, bottom, 3, InputStatus.Have_Input);
                }

            } else if (i > position) {

                if (i == 0) {
                    PeripheryBorder(canvas, left, top, right, bottom, 1, InputStatus.No_Input);
                } else if (i == count - 1) {
                    PeripheryBorder(canvas, left, top, right, bottom, 2, InputStatus.No_Input);
                } else {
                    PeripheryBorder(canvas, left, top, right, bottom, 3, InputStatus.No_Input);
                }


            }
        }

        //结束时绘制选中框避免别覆盖
        if (position < count) {

            float left = startX + position * mWidth;
            float top = 0;
            float right = left + mWidth;
            float bottom = mHeight;
            if (!isContinuous) {
                left = left + position * intervalWidth;
                right = left + mWidth;
            }

            if (position == 0) {
                PeripheryBorder(canvas, left, top, right, bottom, 1, InputStatus.To_Input);
            } else if (position == count - 1) {
                PeripheryBorder(canvas, left, top, right, bottom, 2, InputStatus.To_Input);
            } else {
                PeripheryBorder(canvas, left - mFocusLineWidth / 2, top, right + mFocusLineWidth / 2, bottom, 3, InputStatus.To_Input);
            }
        }
    }


    /**
     * 绘制背景
     *
     * @param canvas       画布
     * @param left         左边坐标
     * @param top          头坐标
     * @param right        右坐标
     * @param bottom       下坐标
     * @param locationType 1.左边，2.右边 3.中间
     * @param status       表示当前位置
     */
    public void PeripheryBorder(Canvas canvas, float left, float top, float right, float bottom, int locationType, InputStatus status) {

        int lineWidth = status == InputStatus.No_Input ? mLineWidth : (status == InputStatus.Have_Input ? mEmployLineWidth : mFocusLineWidth);
        if (locationType == 1) {

            Path FillPathRoundRect = new Path();
            FillPathRoundRect.addRoundRect(
                    new RectF(left, top + lineWidth, right, bottom - lineWidth),
                    (isContinuous ? getLeftRadius() : getAllRadius()),
                    Path.Direction.CCW
            );
            canvas.drawPath(FillPathRoundRect, getFillPaint(status));

            Path borderPathRoundRect = new Path();
            borderPathRoundRect.addRoundRect(
                    new RectF(left, top + lineWidth / 2, right, bottom - lineWidth / 2),
                    isContinuous ? getLeftRadius() : getAllRadius(),
                    Path.Direction.CCW
            );
            canvas.drawPath(borderPathRoundRect, getBorderPaint(status));


        } else if (locationType == 2) {

            Path FillPathRoundRect = new Path();
            FillPathRoundRect.addRoundRect(
                    new RectF(left, top + lineWidth, right, bottom - lineWidth),
                    isContinuous ? getRightRadius() : getAllRadius(),
                    Path.Direction.CCW
            );
            canvas.drawPath(FillPathRoundRect, getFillPaint(status));

            Path borderPathRoundRect = new Path();
            borderPathRoundRect.addRoundRect(
                    new RectF(left, top + lineWidth / 2, right, bottom - lineWidth / 2),
                    isContinuous ? getRightRadius() : getAllRadius(),
                    Path.Direction.CCW
            );
            canvas.drawPath(borderPathRoundRect, getBorderPaint(status));


        } else if (locationType == 3) {

            if (isContinuous) {
                canvas.drawRect(
                        new RectF(left, top + lineWidth, right, bottom - lineWidth), getFillPaint(status)
                );
                canvas.drawRect(new RectF(left, top + lineWidth / 2, right, bottom - lineWidth / 2), getBorderPaint(status));

            } else {

                Path FillPathRoundRect = new Path();
                FillPathRoundRect.addRoundRect(
                        new RectF(left, top + lineWidth, right, bottom - lineWidth),
                        getAllRadius(),
                        Path.Direction.CCW
                );
                canvas.drawPath(FillPathRoundRect, getFillPaint(status));

                Path borderPathRoundRect = new Path();
                borderPathRoundRect.addRoundRect(
                        new RectF(left, top + lineWidth / 2, right, bottom - lineWidth / 2),
                        getAllRadius(),
                        Path.Direction.CCW
                );
                canvas.drawPath(borderPathRoundRect, getBorderPaint(status));


            }

        }

    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        position = start + lengthAfter;
        Log.d("test", text.toString());
        if (!TextUtils.isEmpty(text) && text.toString().length() == count) {

            if (mInputOverListener != null) {
                mInputOverListener.InputOver(text.toString());
            }
        }
        invalidate();

    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setmInputOverListener(InputOverListener mInputOverListener) {
        this.mInputOverListener = mInputOverListener;
        if (mContinuousRepeatCharInputFilter != null) {
            mContinuousRepeatCharInputFilter.setInputOverListener(mInputOverListener);
        }
    }

    public interface InputOverListener {
        void InputOver(String string);

        void InputHint(String string);
    }

}
