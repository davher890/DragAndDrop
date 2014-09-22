package com.example.dragandrop;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnTouchListener, OnDragListener {
	
	View left;
	View layoutCuadrado;
	View layoutTriangulo;
	View layoutCirculo;
	
	boolean containsDragableCuadrado = false;
	boolean containsDragableTriangulo = false;
	boolean containsDragableCirculo = false;;
	
	MainActivity context = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_shapes);
		
		context = MainActivity.this;
		
	    findViewById(R.id.imagen_cuadrado).setOnTouchListener(this);
		findViewById(R.id.imagen_triangulo).setOnTouchListener(this);	    
	    findViewById(R.id.imagen_circulo).setOnTouchListener(this);
		
	    findViewById(R.id.left).setOnDragListener(this);
	    findViewById(R.id.right).setOnDragListener(this);
	    
	    findViewById(R.id.layout_cuadrado).setOnDragListener(this);
	    findViewById(R.id.layout_triangulo).setOnDragListener(this);
	    findViewById(R.id.layout_circulo).setOnDragListener(this);
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {
		
		// Dragged image
		View dragView = (View) event.getLocalState();
		
		// Drag action
		int dragAction = event.getAction();
		
		Drawable enterShape = getResources().getDrawable(R.drawable.shape_target);
		Drawable normalShape = getResources().getDrawable(R.drawable.shape);
		
		if (dragAction == DragEvent.ACTION_DRAG_ENTERED) {
			if (v.getId() == R.id.left || v.getId() == R.id.right){
				v.setBackground(enterShape);
			}
			if (v.getId() == R.id.layout_cuadrado){
				containsDragableCuadrado = true;
			}
			if (v.getId() == R.id.layout_triangulo){
	            containsDragableTriangulo = true;
				}
			if (v.getId() == R.id.layout_circulo){
	            containsDragableCirculo = true;
			}
        }
		else if (dragAction == DragEvent.ACTION_DRAG_EXITED) {
			if (v.getId() == R.id.left || v.getId() == R.id.right){
				v.setBackground(normalShape);
			}			
			if (v.getId() == R.id.layout_cuadrado){
				containsDragableCuadrado = false;
			}
			if (v.getId() == R.id.layout_triangulo){
	            containsDragableTriangulo = false;
				}
			if (v.getId() == R.id.layout_circulo){
	            containsDragableCirculo = false;
			}
        }
        else if (dragAction == DragEvent.ACTION_DRAG_ENDED) {
        	if (v.getId() == R.id.left || v.getId() == R.id.right){
        		v.setBackground(normalShape);
			}
			// Dragged image
        	if (dropEventNotHandled(event)) {
                dragView.setVisibility(View.VISIBLE);
            }
		}
        else if (dragAction == DragEvent.ACTION_DROP) {
            checkForValidMove((LinearLayout) v, dragView);
            dragView.setVisibility(View.VISIBLE);
        }
		return true;
	}
	
	private void checkForValidMove(LinearLayout v, View dragView) {
		
		if ((containsDragableCuadrado && v.getId() == R.id.layout_cuadrado && dragView.getId() == R.id.imagen_cuadrado) ||
			(containsDragableTriangulo && v.getId() == R.id.layout_triangulo  && dragView.getId() == R.id.imagen_triangulo) ||
			(containsDragableCirculo && v.getId() == R.id.layout_circulo  && dragView.getId() == R.id.imagen_circulo)){
			
			LinearLayout owner = (LinearLayout) dragView.getParent();
			owner.removeView(dragView);

			Drawable enterShape = getResources().getDrawable(R.drawable.shape_target);
			Drawable normalShape = getResources().getDrawable(R.drawable.shape);
			
			findViewById(R.id.left).setBackground(normalShape);
			findViewById(R.id.right).setBackground(normalShape);
			
			v.setBackground(enterShape);
			v.addView(dragView);
			v.setGravity(Gravity.CENTER);
		}
	}

	private boolean dropEventNotHandled(DragEvent dragEvent) {
        return !dragEvent.getResult();
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
            v.startDrag(clipData, dsb, v, 0);
            v.setVisibility(View.INVISIBLE);
            return true;
	    } else {
	        return false;
	    }	
	}
}