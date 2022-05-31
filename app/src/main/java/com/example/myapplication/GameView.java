package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View{

    private final Sprite playerBird;
    private final Sprite enemyBird;
    private final Sprite missileM;

    private int viewWidth;
    private int viewHeight;

    private int points = 100;

    private final int timerInterval = 30;

    private int Protection_points = 0;
    private int Base_points = 1000;

    public int t1 = 0;

    public int y1 = 1372;

    

    public int Points_Fuil = 0;

    public int number_of_shells = 25;

    public boolean[] start = new boolean[6];

    public int x2;
    public int y2;

    public int check1;
    public int check2;

    boolean dop = false;
    public GameView(Context context) {
        super(context);

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        int w = b.getWidth()/5;
        int h = b.getHeight()/3;
        Rect firstFrame = new Rect(0, 0, w, h);
        playerBird = new Sprite(10, 0, 0, 100, firstFrame, b);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i ==0 && j == 0) {
                    continue;
                }
                if (i ==2 && j == 3) {
                    continue;
                }
                playerBird.addFrame(new Rect(j*w, i*h, j*w+w, i*w+w));
            }
        }

        b = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        w = b.getWidth()/5;
        h = b.getHeight()/3;
        firstFrame = new Rect(4*w, 0, 5*w, h);

        enemyBird = new Sprite(2000, 250, -300, 0, firstFrame, b);

        for (int i = 0; i < 3; i++) {
            for (int j = 4; j >= 0; j--) {

                if (i ==0 && j == 4) {
                    continue;
                }

                if (i ==2 && j == 0) {
                    continue;
                }

                enemyBird.addFrame(new Rect(j*w, i*h, j*w+w, i*w+w));
            }
        }
        b = BitmapFactory.decodeResource(getResources(), R.drawable.missile);
        w = b.getWidth()/5;
        h = b.getHeight()/3;
        firstFrame = new Rect(0, 0, w, h);
        missileM = new Sprite(800,1375, 0, 100, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 4; j >= 0; j--) {

                if (i ==0 && j == 4) {
                    continue;
                }

                if (i ==2 && j == 0) {
                    continue;
                }

                missileM.addFrame(new Rect(j*w, i*h, j*w+w, i*w+w));
            }
        }

        Timer t = new Timer();
        t.start();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        viewWidth = w;
        viewHeight = h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setSubpixelText(true);
        p.setAntiAlias(true);
        canvas.drawARGB(250, 255, 255, 255);
        p.setTextSize(55.0f);

        if (start[0] == false){
            p.setColor(Color.BLACK);
            canvas.drawRect(0,0,viewWidth,viewHeight,p);
            p.setColor(Color.WHITE);
            canvas.drawText("Здравия желаю! Это краткое обучение!", 70, (int)(viewHeight / 2), p);
            canvas.drawText("Вы можете его пропустить", 150, (int)(viewHeight / 2 + 50), p);
            canvas.drawText("Я бывалый боец!", viewWidth - 500, 100, p);
            for (int i = viewWidth - 500; i < viewWidth; i++){
                for (int j = 0; j < 100; j++){
                    if (x2 == i && y2 == j){
                        start[0] = true;
                        start[3] = false;
                        teleportEnemy();
                        t1 = 0;
                        Protection_points = 0;
                        points = 100;
                        Base_points = 1000;
                        y1 = 1372;
                        Points_Fuil = 0;
                        number_of_shells = 25;
                        dop = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < viewWidth; i++) {
                for (int j = 100; j < viewHeight; j++) {
                    if (x2 == i && y2 == j){
                        start[0] = true;
                        start[1] = true;
                        check1 = x2;
                        check2 = y2;
                        break;
                    }
                }
            }
            p.setColor(Color.BLACK);
        }


        if (start[1] == true) {
            missileM.draw(canvas);
            canvas.drawRect(0, 0, viewWidth, viewHeight - 351, p);
            p.setColor(Color.WHITE);
            canvas.drawText("Здесь показатели вашего снаряжения", 70, (int) (viewHeight / 2), p);
            canvas.drawText("Используйте его с умом!", 150, (int) (viewHeight / 2 + 50), p);
            p.setColor(Color.BLACK);
            canvas.drawText("F", 100, viewHeight - 300, p);
            canvas.drawText("U", 100, viewHeight - 250, p);
            canvas.drawText("E", 100, viewHeight - 200, p);
            canvas.drawText("L", 100, viewHeight - 150, p);
            canvas.drawText(Integer.toString(number_of_shells), 975, 1475, p);
            p.setStyle(Paint.Style.STROKE);
            canvas.drawRect(150, viewHeight - 345, 200, viewHeight - 150, p);
            p.setStyle(Paint.Style.FILL);
            int green = Color.GREEN;
            p.setColor(green);
            canvas.drawRect(151, y1, 199, viewHeight - 151, p);
            p.setColor(Color.BLACK);
            canvas.drawLine(0, viewHeight - 350, viewWidth, viewHeight - 350, p);
            canvas.drawLine(0, viewHeight - 351, viewWidth, viewHeight - 351, p);
            canvas.drawText("Снаряжение", (int) (viewWidth / 2 - 200), viewHeight - 300, p);
            if (x2 != check1 && y2 != check2) {
                start[1] = false;
                start[2] = true;
                check1 = x2;
                check2 = y2;
            }
        }
        if (start[2] == true){
            canvas.drawRect(200, 0, viewWidth, viewHeight, p);
            canvas.drawRect(0,viewHeight - 351, 201, viewHeight, p);
            p.setColor(Color.WHITE);
            canvas.drawText("Это ваш танк", 250, (int)(viewHeight / 2), p);
            canvas.drawText("Нажимайте выше или ниже его,", 250, (int)(viewHeight / 2 + 50), p);
            canvas.drawText("чтобы менять направление", 250, (int)(viewHeight / 2 + 100), p);
            canvas.drawText("движения", 250, (int)(viewHeight / 2 + 150), p);
            playerBird.draw(canvas);
            p.setColor(Color.BLACK);

            if (x2 != check1 && y2 != check2) {
                start[2] = false;
                start[3] = true;
                check1 = x2;
                check2 = y2;
            }
        }
        if (start[3] == true) {
            canvas.drawText("Чтобы стрелять,", 70, (int) (viewHeight / 2), p);
            canvas.drawText("нажмите на линию перед вашим танком", 70, (int) (viewHeight / 2 + 50), p);
            canvas.drawText("А теперь, в бой!", 70, (int) (viewHeight / 2 + 100), p);
            CountDownTimer countDownTimer = new CountDownTimer(5000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    teleportEnemy();
                    t1 = 0;
                    Protection_points = 0;
                    points = 100;
                    Base_points = 1000;
                    y1 = 1372;
                    Points_Fuil = 0;
                    number_of_shells = 25;
                    dop = true;
                    start[3] = false;
                }
            }.start();
        }


        if (dop == true){
            playerBird.draw(canvas);
            enemyBird.draw(canvas);
            missileM.draw(canvas);

            p.setAntiAlias(true);
            p.setTextSize(55.0f);
            p.setColor(Color.BLACK);
            canvas.drawText("XP tank: " + points, viewWidth - 320, 70, p);
            canvas.drawText(Integer.toString(Protection_points), (int) (viewWidth / 2), 70, p);
            canvas.drawText("XP base: " + Base_points, (int)(viewHeight/2 - 850), 70, p);
            canvas.drawText("F", 100, viewHeight - 300, p);
            canvas.drawText("U", 100, viewHeight - 250, p);
            canvas.drawText("E", 100, viewHeight - 200, p);
            canvas.drawText("L", 100, viewHeight - 150, p);
            canvas.drawText(Integer.toString(number_of_shells), 975, 1475, p);
            p.setStyle(Paint.Style.STROKE);
            canvas.drawRect(150, viewHeight - 345, 200, viewHeight - 150, p);
            p.setStyle(Paint.Style.FILL);
            int green = Color.GREEN;
            p.setColor(green);
            canvas.drawRect(151, y1, 199, viewHeight - 151, p);
            p.setColor(Color.BLACK);
            canvas.drawLine(0,viewHeight - 350, viewWidth, viewHeight - 350, p);
            canvas.drawLine(0,viewHeight - 351, viewWidth, viewHeight - 351, p);
            canvas.drawText("Снаряжение", (int) (viewWidth / 2 - 200), viewHeight - 300, p);

            if(t1 == 1){
                canvas.drawText("Вы набрали " + Protection_points + " очков", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2), p);
                if (points <= 0){
                    canvas.drawText("Причина поражения - ", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 50), p);
                    canvas.drawText("танк уничтожен",(int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 100), p);
                    canvas.drawText("Совет: ", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 150), p);
                    canvas.drawText("не подпускайте танки противника",(int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 200), p);

                } else if (Base_points <= 0){
                    canvas.drawText("Причина поражения - ", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 50), p);
                    canvas.drawText("база уничтожена",(int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 100), p);
                    canvas.drawText("Совет: ", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 150), p);
                    canvas.drawText("не подпускайте танки противника",(int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 200), p);
                } else if (y1 >= viewHeight - 151){
                    canvas.drawText("Причина поражения - ", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 50), p);
                    canvas.drawText("топливо закончилось", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 100), p);
                    canvas.drawText("Совет: уменьшите расход топлива", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 150), p);
                } else if (number_of_shells == 0){
                    canvas.drawText("Причина поражения - ", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 50), p);
                    canvas.drawText("снаряды закончились", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 100), p);
                    canvas.drawText("Совет: расходуйте снаряды с умом", (int) (viewWidth / 2 - 350), (int) (viewHeight / 2 + 150), p);
                }
                CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        teleportEnemy();
                    }

                    @Override
                    public void onFinish() {
                        t1 = 0;
                        Protection_points = 0;
                        points = 100;
                        Base_points = 1000;
                        y1 = 1372;
                        Points_Fuil = 0;
                        number_of_shells = 25;
                    }
                }.start();
            }

            if (points <= 0 || Base_points <= 0 || y1 >= viewHeight - 151 || number_of_shells == 0){
                t1 = 1;
            }
            if (Points_Fuil == 10){
                if (y1 <= y1 - 42){
                    y1 = y1 - 42;
                } else {
                    y1 = 1372;
                }
                Points_Fuil = 0;
            }
        }
    }

    protected void update () {
        playerBird.update(timerInterval);
        enemyBird.update(timerInterval);


        if (playerBird.getY() + playerBird.getFrameHeight() >= viewHeight - 350) {
            playerBird.setY(viewHeight - 350 - playerBird.getFrameHeight());
            playerBird.setVy(-playerBird.getVy());
        }
        else if (playerBird.getY() < 0) {
            playerBird.setY(0);
            playerBird.setVy(-playerBird.getVy());
        }

        if (enemyBird.getX() < - enemyBird.getFrameWidth()) {
            teleportEnemy();
            Base_points -= 100;
        }

        if (enemyBird.intersect(playerBird)) {
            teleportEnemy ();
            points -= 20;
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_DOWN)  {

            if (event.getY() < playerBird.getBoundingBoxRect().top) {
                playerBird.setVy(-400);
                y1 = y1 + 4;
            }
            else if (event.getY() > (playerBird.getBoundingBoxRect().bottom)) {
                playerBird.setVy(200);
                y1 = y1 + 2;
            }
            for (int i = 0; i < 300; i++){
                if (event.getY() == playerBird.getY() + i){
                    number_of_shells--;
                    fire((int) event.getY());
                    break;
                }
            }
        }
        x2 = (int) event.getX();
        y2 = (int) event.getY();
        return true;
    }

    private void teleportEnemy () {
        double i = Math.random() * (viewHeight - enemyBird.getFrameHeight());

        while (i >= viewHeight - 500){
            i = Math.random() * (viewHeight - enemyBird.getFrameHeight());
        }

        enemyBird.setX(viewWidth + Math.random() * 500);
        enemyBird.setY(i);
    }
    public int fire(int y){
        for (int i = 0; i < 200; i++){
            if (y == (int)(enemyBird.getY() + i)){
                teleportEnemy();
                Points_Fuil++;
                int luck = (int)(Math.random() * 10);
                if (luck == 2 || luck == 5){
                    number_of_shells += 5;
                }
                return Protection_points++;
            }
        }
        return Protection_points;
    }

    class Timer extends CountDownTimer {

        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            update ();
        }

        @Override
        public void onFinish() {

        }
    }
}