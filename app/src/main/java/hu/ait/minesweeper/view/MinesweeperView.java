package hu.ait.minesweeper.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.minesweeper.MainActivity;
import hu.ait.minesweeper.R;
import hu.ait.minesweeper.model.MinesweeperModel;

public class MinesweeperView extends View {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintText;
    private Paint paintText2;
    private Paint paintText3;
    private Paint paintText4;
    private Bitmap bitmapBg;
    private Bitmap bitmapFlag;
    private Bitmap bitmapMine;

    public static int result=2;
    public static int start=0;


    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.YELLOW);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintText= new Paint();
        paintText.setColor(Color.BLACK);

        paintText2 = new Paint();
        paintText2.setColor(Color.RED);

        paintText3 = new Paint();
        paintText3.setColor(Color.MAGENTA);

        paintText4 = new Paint();
        paintText4.setColor(Color.RED);

        bitmapBg = BitmapFactory.decodeResource(getResources(),R.drawable.grass_background);
        bitmapMine = BitmapFactory.decodeResource(getResources(),R.drawable.mine);
        bitmapFlag = BitmapFactory.decodeResource(getResources(),R.drawable.flag);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmapBg = Bitmap.createScaledBitmap(bitmapBg, getWidth(), getHeight(), false);
        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag, getWidth()/5, getHeight()/5, false);
        bitmapMine = Bitmap.createScaledBitmap(bitmapMine, getWidth()/5, getHeight()/5, false);

        paintText.setTextSize(getHeight()/10);
        paintText2.setTextSize(getHeight()/10);
        paintText3.setTextSize(getHeight()/10);
        paintText4.setTextSize(getHeight()/10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(),
                paintBg);

        canvas.drawBitmap(
                bitmapBg,
                0,0,
                null);


        drawGameGrid(canvas);

        drawPlayers(canvas);

    }

    private void drawPlayers(Canvas canvas) {

        if (result == 0){

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++){
                    if (MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.MINE ||
                            MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.FLAG){
                        canvas.drawBitmap(bitmapMine, i * getWidth() / 5, j * getWidth() / 5, null);
                    }
                    else if (MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.EMPTY) {
                    }
                    else {
                        canvas.drawText(
                                String.valueOf(MinesweeperModel.getInstance().getField(i, j)), i * getWidth() / 5,
                                j * getWidth() / 5 + getHeight()/10, paintText);
                    }
                }
            }
            ((MainActivity)getContext()).DisplayMessage("Sorry, you lost. Please Click on 'New Game' for another game.");
            result = 2;
            ((MainActivity)getContext()).StopTimer();
            start = 0;

        }
        else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++){
                    if (MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.FLAG){
                        canvas.drawBitmap(bitmapFlag, i * getWidth() / 5, j * getWidth() / 5, null);
                        //canvas.drawText("F", i * getWidth() / 5, j * getWidth() / 5 + getHeight()/10, paintText2);
                    }

                    else if (MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.EMPTY ||
                            MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.MINE){
                    }

                    else {
                        canvas.drawText(
                                String.valueOf(MinesweeperModel.getInstance().getField(i, j)), i * getWidth() / 5,
                                j * getWidth() / 5 + getHeight()/10, paintText);
                    }
                }
            }
            if (result == 1) {
                ((MainActivity)getContext()).DisplayMessage("Congratulations, you won! Please Click on 'New Game' for another game.");
                result = 2;
                ((MainActivity)getContext()).StopTimer();
                start = 0;
            }
        }
    }



    private void drawGameGrid(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // four horizontal lines
        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        // four vertical lines
        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5, getHeight(),
                paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (start == 0){
                start = 1;
                ((MainActivity)getContext()).StartTimer();
            }

            if (MinesweeperModel.flag == 1){

                int tX = ((int)event.getX()) / (getWidth()/5);
                int tY = ((int)event.getY()) / (getHeight()/5);

                if (MinesweeperModel.getInstance().getField(tX, tY) == MinesweeperModel.EMPTY){
                    //game ends
                    result = 0;
                    invalidate();
                }

                else if (MinesweeperModel.getInstance().getField(tX, tY) == MinesweeperModel.MINE){
                    MinesweeperModel.getInstance().setField(tX, tY, MinesweeperModel.FLAG);
                    if (CheckHasWon()){
                        //game won
                        result = 1;
                        invalidate();
                    }
                    else {
                        result = 2;
                        invalidate();
                    }

                }

                else {
                    result = 2;
                }

            }

            else {

                int tX = ((int)event.getX()) / (getWidth()/5);
                int tY = ((int)event.getY()) / (getHeight()/5);

                if (MinesweeperModel.getInstance().getField(tX, tY) == MinesweeperModel.MINE){
                    //game ends
                    result = 0;
                    invalidate();
                }

                else if (MinesweeperModel.getInstance().getField(tX, tY) == MinesweeperModel.EMPTY) {
                    MinesweeperModel.getInstance().setField(tX, tY, MinesweeperModel.getInstance().CheckNeighborMines(tX, tY));
                    result = 2;
                    invalidate();
                }

                else {
                    result = 2;
                }
            }
        }

        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void resetGame() {
        MinesweeperModel.getInstance().resetModel();
        invalidate();
    }

    public boolean CheckHasWon(){

        int counter=0;
        for (int i=0; i<5; i=i+2){
            int x = MinesweeperModel.MinesArray[i];
            int y = MinesweeperModel.MinesArray[i+1];
            if (MinesweeperModel.model[x][y] == MinesweeperModel.FLAG)
                counter++;
        }
        if (counter == 3)
            return true;
        else
            return false;
    }

}