package org.example.Fx;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class Animation {
    private long lastUpdate = 0;
    private int columnIndex = 0;
    public Animation(){}

    public void anim(ImageView spriteView,int numColumns,long now,int rowIndex,int spriteWidth,int spriteHeight){
        if (now - lastUpdate >= 100_000_000) {
            columnIndex++;
            if (columnIndex >= numColumns) {
                columnIndex = 0;
            }
            int offsetX = columnIndex * spriteWidth;
            int offsetY = rowIndex * spriteHeight;
            spriteView.setViewport(new Rectangle2D(offsetX, offsetY, spriteWidth, spriteHeight));
            lastUpdate = now;
        }

    }

    public void play(ImageView spriteView,int numColumns,int rowIndex,int spriteWidth,int spriteHeight){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                anim(spriteView, numColumns,now,rowIndex,spriteWidth,spriteHeight);
            }
        };
        timer.start();
    }
}
